package telran.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundExceptin extends  RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserNotFoundExceptin(String id) {
		super("User " + id + " not found!");
	}
}
