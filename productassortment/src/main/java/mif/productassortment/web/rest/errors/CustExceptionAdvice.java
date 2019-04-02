package mif.productassortment.web.rest.errors;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustExceptionAdvice {

	@ExceptionHandler({InvalidRequestException.class})
	public ResponseEntity<ErrMessage> handleInvalidRequestException(final InvalidRequestException e) {
		return formError(HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<ErrMessage> handleResourceNotFoundException(final ResourceNotFoundException e) {
		return formError(HttpStatus.NOT_FOUND, e);
	}

	private ResponseEntity<ErrMessage> formError(final HttpStatus status, final Exception e) {
		final ErrMessage errorMessage = new ErrMessage(new Date(), e.getMessage(), status.getReasonPhrase());
		return ResponseEntity.status(status).body(errorMessage);
	}
}
