package application.business.v1;

public class DuplicatedDnaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7865145247054306982L;

	public DuplicatedDnaException(String errorMessage) {
		super(errorMessage);
	}
}
