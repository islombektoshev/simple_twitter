package uz.owl.web.rest.errors;

public class NotFoundExceptions extends ApiException {
    public NotFoundExceptions(String error, String message) {
        super(404, error, message);
    }
    public NotFoundExceptions(String message) {
        super(404, "not_found", message);
    }
}
