import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-24 17:11
 **/
public class TCPDictionaryClient {
    private static String ip = "localhost";
    private static int port =9999;
    private static volatile Boolean connect;
    private static volatile ClientMessage message;

    public static void main(String[] args) {
        Socket client = null;
        ObjectOutputStream out = null;
        DataInputStream in = null;
        ClientGUI gui = new ClientGUI();
        try {

            client = new Socket(ip,port);
            connect = true;
            out = new ObjectOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
            while (connect) {
                if (message != null){
                    out.writeObject(message);
                    out.flush();
                    message = null;
                    String result = in.readUTF();
                    System.out.println(result);
                    gui.response.setText(result);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            System.out.println("over");
            try {
                if (client != null) {
                    client.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void sendRequest(String requestMethod, String word, List<String> meanings){
        ClientMessage message = new ClientMessage(requestMethod, word, meanings);
        TCPDictionaryClient.message = message;
    }
    public static void sendRequest(String requestMethod, String word){
        ClientMessage message = new ClientMessage(requestMethod, word);
        TCPDictionaryClient.message = message;
    }

    //provide a button for user to end the session/disconnect from the server
    public static void terminate(){
        connect = false;
    }
}
