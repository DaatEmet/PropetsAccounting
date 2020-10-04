package telran.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.accounting.dto.UserDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.service.AccountingService;

@RestController
@RequestMapping("/account/en/v1")
public class UserController {
	
	@Autowired
	AccountingService service;
	
	@PostMapping("/registration")
	public UserDto registerUser(@RequestBody UserRegisterDto userRegisterDto) {
		return service.registerUser(userRegisterDto);
	}
}
