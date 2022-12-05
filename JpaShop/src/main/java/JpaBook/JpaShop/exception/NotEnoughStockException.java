package JpaBook.JpaShop.exception;

public class NotEnoughStockException extends RuntimeException{

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    //message + cause 근원적인 exception 을 넣어주려고 override
    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

}
