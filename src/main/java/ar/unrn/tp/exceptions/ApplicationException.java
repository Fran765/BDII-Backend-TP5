package ar.unrn.tp.exceptions;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String msj, Throwable cause) {
        super(msj, cause);
    }
}