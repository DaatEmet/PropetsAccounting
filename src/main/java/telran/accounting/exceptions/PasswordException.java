package telran.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class PasswordException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4383448529984338022L;

	public PasswordException() {
		super("Your password must be 8+ characters, and include "
				+ "at least one lowercase letter, one uppercase letter, number and special symbol");
	}
}
