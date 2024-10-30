package ar.unrn.tp.exceptions;

public class ClientException extends ApplicationException {
    public ClientException(String message) {
        super(message);
    }

    public ClientException(String msj, Throwable cause) {
        super(msj, cause);
    }
}
