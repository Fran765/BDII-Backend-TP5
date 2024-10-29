package ar.unrn.tp.exceptions;

public class RedisExeption extends ApplicationException{
    public RedisExeption(String message) {
        super(message);
    }

    public RedisExeption(String msj, Throwable cause) {
        super(msj, cause);
    }
}
