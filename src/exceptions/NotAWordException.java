package exceptions;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-24 20:33
 **/
public class NotAWordException extends RuntimeException {
    public NotAWordException() {
    }

    public NotAWordException(String message) {
        super(message);
    }
}
