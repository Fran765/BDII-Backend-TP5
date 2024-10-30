package ar.unrn.tp.exceptions;

public class ProductException extends ApplicationException {
    public ProductException(String message) {
        super(message);
    }

    public ProductException(String msj, Throwable cause) {
        super(msj, cause);
    }
}
