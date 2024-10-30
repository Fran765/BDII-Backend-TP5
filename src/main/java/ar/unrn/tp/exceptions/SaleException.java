package ar.unrn.tp.exceptions;

public class SaleException extends ApplicationException {
    public SaleException(String message) {
        super(message);
    }

    public SaleException(String msj, Throwable cause) {
        super(msj, cause);
    }
}