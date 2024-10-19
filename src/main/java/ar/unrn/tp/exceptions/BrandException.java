package ar.unrn.tp.exceptions;

public class BrandException extends ApplicationException{
    public BrandException(String message) {
        super(message);
    }

    public BrandException(String msj, Throwable cause) {
        super(msj, cause);
    }
}
