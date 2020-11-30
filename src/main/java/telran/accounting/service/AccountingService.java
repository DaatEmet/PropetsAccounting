package telran.accounting.service;

import java.util.Set;

import telran.accounting.dto.UpdateDto;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserRegisterDto;

public interface AccountingService {
	
	UserDto registerUser(UserRegisterDto userDto);
	UserDto getUser(String id);
	UserDto updateUser(String id, UpdateDto updateDto);
	UserDto removeUser(String id);
	Set<String> addRoles(String id, String role);
	Set<String> removeRoles(String id, String role);
	boolean changeUserStatus(String id, boolean blockStatus);
	void addUserFavorite(String id, String postId);
	void removeUserFavorite(String id, String postId);
	void addUserActivity(String id, String postId);
	void removeUserActivity(String id, String postId);
	Set<String> getUserData(String id, boolean dataType);
	
}
