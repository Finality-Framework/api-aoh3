package team.rainfall.finality.api.exceptions;

public class InteractionNotFoundException extends RuntimeException {
    public InteractionNotFoundException(String message) {
        super(message);
    }
    public InteractionNotFoundException() {
        super();
    }
}
