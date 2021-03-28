import java.io.Serializable;
import java.util.List;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-28 00:36
 **/
public class ClientMessage implements Serializable {
    String request;
    String word;
    List<String> meanings;

    //arbitrary serialUID to prevent issues if we have to change this class
    private static final long serialVersionUID = 1L;

    public ClientMessage(String request, String word, List<String> meanings) {
        this.request = request;
        this.word = word;
        this.meanings = meanings;
    }

    public ClientMessage(String request, String word) {
        this.request = request;
        this.word = word;
    }

    public String getRequest() {
        return request;
    }

    public String getWord() {
        return word;
    }

    public List<String> getMeanings() {
        return meanings;
    }
}
