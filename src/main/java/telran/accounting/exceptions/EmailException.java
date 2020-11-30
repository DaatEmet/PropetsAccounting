package telran.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT)
public class EmailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 705237783599729902L;
	
	public EmailException(String email) {
		super("e-mail is not correct");
	}
}
