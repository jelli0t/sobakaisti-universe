/**
 * 
 */
package org.sobakaisti.core.model;

/**
 * @author jelles
 *
 */
public class ResponseInfo {

	public static enum status { SUCCESS, FAILURE }
	
	private boolean successful;
	private String message;
	
	public ResponseInfo(boolean successful, String message){
		this.successful = successful;
		this.message = message;
	}
	
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}