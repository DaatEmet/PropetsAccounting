package telran.accounting.filters;

import java.io.IOException;

import java.net.URI;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import static telran.accounting.filters.Constants.TOKEN_HEADER;

@Service
@Order(20)
public class AuthFilter implements Filter {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		String method = request.getMethod();
		if (checkPathAndMethod(path, method)) {
			try {
				HttpHeaders headers = new HttpHeaders();
				String token = request.getHeader(TOKEN_HEADER);
				headers.add(TOKEN_HEADER, token);
				RequestEntity<String> requestEntity = new RequestEntity<>(headers, HttpMethod.GET,
						new URI("http://localhost:9000/account/en/v1/token/getlogin"));
				ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
				String user = responseEntity.getBody();
				String login = path.split("/")[4];
				if (!user.equals(login)) {
					response.sendError(403);
					return;
				}
			} catch (Exception e) {
				response.sendError(400);
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkPathAndMethod(String path, String method) {
		boolean res = path.matches("/account/en/v1/\\w+@[a-z]+\\.[a-z]+/?");
		res = res || path.matches("/account/en/v1/\\w+@[a-z]+\\.[a-z]+/(favorite|activity)/\\w+/?");
		return res;
	}

}
