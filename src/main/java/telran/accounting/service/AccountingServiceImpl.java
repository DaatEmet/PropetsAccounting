package telran.accounting.service;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import telran.accounting.dao.UserRepository;
import telran.accounting.dto.RolesDto;
import telran.accounting.dto.UpdateDto;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.exceptions.UserExistsException;
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
		if(repository.existsById(userRegisterDto.getEmail())) {
			throw new UserExistsException(userRegisterDto.getEmail());
		}
		String password = BCrypt.hashpw(userRegisterDto.getPassword(), BCrypt.gensalt());
		User user = modelMapper.map(userRegisterDto, User.class);
		user.setPassword(password);
		user.addRole(defaultUser.toUpperCase());
		user.setPassword(password);
		repository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto getUserInformation(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto updateUser(UpdateDto updateDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto removeUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
		// TODO Auto-generated method stub
		return null;
	}

}
