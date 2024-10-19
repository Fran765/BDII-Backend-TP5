package ar.unrn.tp.exceptions;

public class CategoryException extends ApplicationException{
    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String msj, Throwable cause) {
        super(msj, cause);
    }
}
