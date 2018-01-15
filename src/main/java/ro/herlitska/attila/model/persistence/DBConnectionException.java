package ro.herlitska.attila.model.persistence;

public class DBConnectionException extends Exception {
	  public DBConnectionException() {
	        super();
	    }

	    public DBConnectionException(String message, Throwable cause) {
	        super(message, cause);
	    }

	    public DBConnectionException(Throwable cause) {
	        super(cause);
	}
}
