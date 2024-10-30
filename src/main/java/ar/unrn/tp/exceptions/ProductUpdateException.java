package ar.unrn.tp.exceptions;

public class ProductUpdateException extends ApplicationException{
    public ProductUpdateException(String message) {
        super(message);
    }

    public ProductUpdateException(String msj, Throwable cause) {
        super(msj, cause);
    }
}
