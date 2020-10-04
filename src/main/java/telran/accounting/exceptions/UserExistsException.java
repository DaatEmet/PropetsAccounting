package telran.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8013969124952494650L;
	
	public UserExistsException(String email) {
		super("User with this " + email + "exists");
		
	}
}
