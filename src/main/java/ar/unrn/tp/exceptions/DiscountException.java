package ar.unrn.tp.exceptions;

public class DiscountException extends ApplicationException {
    public DiscountException(String message) {
        super(message);
    }

    public DiscountException(String msj, Throwable cause) {
        super(msj, cause);
    }
}
