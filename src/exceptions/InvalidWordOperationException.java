package exceptions;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-24 21:19
 **/
public class InvalidWordOperationException extends RuntimeException {
    public InvalidWordOperationException() {
    }

    public InvalidWordOperationException(String message) {
        super(message);
    }
}
