package ar.unrn.tp.exceptions;

public class CardException extends ApplicationException {
    public CardException(String message) {
        super(message);
    }

    public CardException(String msj, Throwable cause) {
        super(msj, cause);
    }
}
