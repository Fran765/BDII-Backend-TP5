package ar.unrn.tp.utils;

public class Email {

    private static final String REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final String email;

    public Email(String email) {
        if (!email.matches(REGEX)) {
            throw new RuntimeException("La dirección de correo electrónico no es válida");
        }
        this.email = email;
    }

    public String asString() {
        return email;
    }
}
