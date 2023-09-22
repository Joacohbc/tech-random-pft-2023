package validation;

public class ValidationObject {

	public static final ValidationObject VALID = new ValidationObject(true, "");

	private boolean valid;
	private String errorMessage;

	public ValidationObject(boolean valid, String errorMessage) {
		super();
		this.valid = valid;
		this.errorMessage = errorMessage;
	}

	// Constructor de caso Invalido
	public ValidationObject(String errorMessage) {
		super();
		this.valid = false;
		this.errorMessage = errorMessage;
	}

	public boolean isValid() {
		return valid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
