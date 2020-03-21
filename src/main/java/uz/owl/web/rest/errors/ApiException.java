package uz.owl.web.rest.errors;


public class ApiException extends RuntimeException{
    private Integer status;
    private String message;
    private String error;

    public ApiException(Integer status, String error, String message) {
        super(message);
        setMessage(message);
        setStatus(status);
        setError(error);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
