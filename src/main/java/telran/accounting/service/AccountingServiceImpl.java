package telran.accounting.service;

import java.util.Set;

import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.accounting.dao.UserRepository;
import telran.accounting.dto.UpdateDto;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.exceptions.EmailException;
import telran.accounting.exceptions.PasswordException;
import telran.accounting.exceptions.UserExistsException;
import telran.accounting.exceptions.UserNotFoundExceptin;
import telran.accounting.model.User;

@Service
public class AccountingServiceImpl implements AccountingService {

	@Autowired
	UserRepository repository;

	@Autowired
	ModelMapper modelMapper;

	@Value("${default.role}")
	private String defaultUser;

	@Override
	public UserDto registerUser(UserRegisterDto userRegisterDto) {
		String email = userRegisterDto.getEmail();
		String password = userRegisterDto.getPassword();
		if (repository.existsById(email)) {
			throw new UserExistsException(email);
		}
		if (!EmailValidator.getInstance().isValid(email)) {
			throw new EmailException(email);
		}
		if (!password.matches("\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$%^&+=])\\S{8,}\\z")) {
			throw new PasswordException();
		}
		password = BCrypt.hashpw(password, BCrypt.gensalt());
		User user = modelMapper.map(userRegisterDto, User.class);
		user.setPassword(password);
		user.addRole(defaultUser.toUpperCase());
		user.setPassword(password);
		repository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto getUser(String id) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		return modelMapper.map(user, UserDto.class);
	}

	@Transactional
	@Override
	public UserDto updateUser(String id, UpdateDto updateDto) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		if (updateDto.getAvatar() != null) {
			user.setAvatar(updateDto.getAvatar());
		}
		if (updateDto.getName() != null) {
			user.setName(updateDto.getName());
		}
		if (updateDto.getPhone() != null) {
			user.setPhone(updateDto.getPhone());
		}
		repository.save(user);
		return modelMapper.map(user, UserDto.class);
	}
	
	@Transactional
	@Override
	public UserDto removeUser(String id) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		repository.delete(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Transactional
	@Override
	public Set<String> addRoles(String id, String role) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		if ("moderator".equalsIgnoreCase(role) || "administrator".equalsIgnoreCase(role)) {
			user.getRoles().add(role.toUpperCase());
			repository.save(user);
		}
		return user.getRoles();
	}

	@Transactional
	@Override
	public Set<String> removeRoles(String id, String role) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		if ("moderator".equalsIgnoreCase(role) || "administrator".equalsIgnoreCase(role)) {
			user.getRoles().remove(role.toUpperCase());
			repository.save(user);
		}
		return user.getRoles();
	}

	@Transactional
	@Override
	public boolean changeUserStatus(String id, boolean blockStatus) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		user.setBlockStatus(blockStatus);
		repository.save(user);
		return true;

	}

	@Override
	public void addUserFavorite(String id, String postId) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		user.getFavorite().add(postId);
		repository.save(user);
	}

	@Override
	public void removeUserFavorite(String id, String postId) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		user.getFavorite().remove(postId);
		repository.save(user);

	}

	@Override
	public void addUserActivity(String id, String postId) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		user.getAcivity().add(postId);
		repository.save(user);
	}

	@Override
	public void removeUserActivity(String id, String postId) {
		User user = repository.findById(id).orElseThrow(() -> new UserNotFoundExceptin(id));
		user.getAcivity().remove(postId);
		repository.save(user);	}

	@Override
	public Set<String> getUserData(String id, boolean dataType) {
		// TODO Auto-generated method stub
		return null;
	}
}
