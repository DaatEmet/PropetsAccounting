package telran.accounting.filters;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import telran.accounting.dao.UserRepository;
import telran.accounting.dto.TokenDto;
import telran.accounting.model.User;
import static telran.accounting.filters.Constants.TOKEN_HEADER;

@Service
@Order(10)
public class LoginFilter implements Filter {

	@Autowired
	UserRepository repository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpHeaders headers = new HttpHeaders();
		if (checkEndpoint(request.getServletPath(), request.getMethod())) {
			try {
				TokenDto tokenDto;
				String login;
				String token = request.getHeader(TOKEN_HEADER);
				if (token == "") {
					token = request.getHeader("Authorization");
					headers.add("Authorization", token);
					String[] credentials = validateTokenBase64(token);
					User user = repository.findById(credentials[0]).orElse(null);
					if (user == null) {
						response.sendError(401);
						return;
					}
					if (!BCrypt.checkpw(credentials[1], user.getPassword())) {
						response.sendError(403);
						return;
					}
					tokenDto = new TokenDto(user.getEmail(), token, new ArrayList<>(user.getRoles()),
							user.getBlockStatus());
					RequestEntity<TokenDto> requestEntity = new RequestEntity<>(tokenDto, HttpMethod.PUT,
							new URI("http://localhost:9000/account/en/v1/token/create"));
					ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
					token = responseEntity.getBody();
					login = user.getEmail();
					response.setHeader(TOKEN_HEADER, token);
				} else {
					headers.add(TOKEN_HEADER, token);
					if (token != null) {
						RequestEntity<String> requestEntity = new RequestEntity<>(headers, HttpMethod.GET,
								new URI("http://localhost:9000/account/en/v1/token/validation"));
						ResponseEntity<TokenDto> responseEntity = restTemplate.exchange(requestEntity, TokenDto.class);
						tokenDto = responseEntity.getBody();
						response.setHeader(TOKEN_HEADER, tokenDto.getToken());
						login = tokenDto.getEmail();
					} else {
						response.sendError(403);
						return;
					}
				}
				request = new WrapperRequest(request, login);
			} catch (Exception e) {
				e.printStackTrace();
				response.sendError(400);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private String[] validateTokenBase64(String token) {
		token = token.split(" ")[1];
		String[] credentials = new String(Base64.getDecoder().decode(token)).split(":");
		return credentials;
	}

	private boolean checkEndpoint(String path, String method) {
		return path.matches("/en/v1/login/?");
	}

	private class WrapperRequest extends HttpServletRequestWrapper {
		String user;

		public WrapperRequest(HttpServletRequest request, String user) {
			super(request);
			this.user = user;
		}

		@Override
		public Principal getUserPrincipal() {
			return new Principal() {

				@Override
				public String getName() {
					return user;
				}
			};
		}
	}

}
