package BilgenKaanRemzi.GestioneEventi.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(int id) {
        super("Elemento cod id " + id + " non trovato!");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
