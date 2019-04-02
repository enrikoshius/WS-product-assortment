package mif.productassortment.web.rest.errors;

import java.util.Date;

public class ErrMessage {

	private Date timestamp;
	private String message;
	private String systemMessage;

	public ErrMessage(Date timestamp, String message, String sysMessage) {
		this.timestamp = timestamp;
		this.message = message;
		this.systemMessage = sysMessage;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(final String systemMessage) {
		this.systemMessage = systemMessage;
	}
}