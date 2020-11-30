package telran.accounting.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.accounting.dto.UpdateDto;
import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.service.AccountingService;

@RestController
@RequestMapping("/en/v1")
public class UserController {

	@Autowired
	AccountingService service;

	@PostMapping("/registration")
	public UserDto registerUser(@RequestBody UserRegisterDto userRegisterDto) {
		return service.registerUser(userRegisterDto);
	}

	@PostMapping("/login")
	public UserDto login(Principal principal) {
		return service.getUser(principal.getName());
	}

	@GetMapping("/{login}/info")
	public UserDto getUserInformation(@PathVariable String login) {
		return service.getUser(login);
	}

	@PutMapping("/{login}")
	public UserDto updateUser(@PathVariable String login, @RequestBody UpdateDto updateDto) {
		return service.updateUser(login, updateDto);
	}

	@DeleteMapping("/{login}")
	public UserDto removeUser(@PathVariable String login) {
		return service.removeUser(login);
	}

	@PutMapping("{login}/role/{role}")
	public Set<String> addRoles(@PathVariable String login, @PathVariable String role) {
		return service.addRoles(login, role);
	}

	@DeleteMapping("{login}/role/{role}")
	public Set<String> removeRoles(@PathVariable String login, @PathVariable String role) {
		return service.removeRoles(login, role);
	}

	@PutMapping("/{login}/block/{status}")
	public boolean changeUserStatus(@PathVariable String login, @PathVariable boolean status) {
		return service.changeUserStatus(login, status);
	}

	@PutMapping("/{login}/favorite/{postId}")
	public void addUserFavorite(@PathVariable String login, @PathVariable String postId) {
		service.addUserFavorite(login, postId);
	}

	@DeleteMapping("/{login}/favorite/{postId}")
	public void removeUserFavorite(@PathVariable String login, @PathVariable String postId) {
		service.removeUserFavorite(login, postId);
	}

	@PutMapping("/{login}/activity/{postId}")
	public void addUserActivity(@PathVariable String login,@PathVariable String postId) {
		service.addUserActivity(login, postId);
	}

	@DeleteMapping("/{login}/activity/{postId}")
	public void removeUserActivity(@PathVariable String login,@PathVariable String postId) {
		service.removeUserActivity(login, postId);
	}
	
	@GetMapping("/{login}")
	public Set<String> getUserData(@PathVariable String login,@RequestParam boolean dataType) {
		return service.getUserData(login, dataType);
	}

}
