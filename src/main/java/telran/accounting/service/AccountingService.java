package telran.accounting.service;

import telran.accounting.dto.RolesDto;
import telran.accounting.dto.UpdateDto;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserRegisterDto;

public interface AccountingService {
	
	UserDto registerUser(UserRegisterDto userDto);
	UserDto getUserInformation(String id);
	UserDto updateUser(UpdateDto updateDto);
	UserDto removeUser(String id);
	RolesDto changeRolesList(String login, String role, boolean isAddRole);

}
