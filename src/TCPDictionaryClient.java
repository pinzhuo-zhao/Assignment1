import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-24 17:11
 **/
public class TCPDictionaryClient {
    private static volatile String ip;
    private static volatile Integer port;

    /** the flag to check if the client is connected to the server or not **/
    private static volatile Boolean connect = false;

    /** the ClientMessage Object(contains all the information of client's request)
     *  to be sent  to the server **/
    private static volatile ClientMessage message;

    public static void main(String[] args) {
        Socket client = null;
        ObjectOutputStream out = null;
        DataInputStream in = null;
        ClientGUI gui = new ClientGUI();
        try {
            //program won't proceed before the user enters ip and port in the GUI
            while (!connect){
                if (ip != null && port != null){
                    //establish the connection with server using the ip and port provided by the user
                    client = new Socket(ip,port);
                    connect = true;
                    gui.getResponse().setText("You have connected to the server " + ip + "at port " + port);
                }
            }
            out = new ObjectOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
            while (connect) {
                //program won't proceed before the user perform any operation(query, add...etc)
                    if (message != null) {
                        out.writeObject(message);
                        out.flush();
                        message = null;
                        String result = in.readUTF();
                        System.out.println(result);
                        //print the response from the server on the text area of the GUI
                        gui.getResponse().setText(result);
                    }
            }

        } catch (UnknownHostException e) {
            gui.getResponse().setText("No server found! Please check your entries for IP and port again");
            e.printStackTrace();
        } catch (ConnectException e) {
            gui.getResponse().setText("No server found! Please check your entries for IP and port again");
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
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

    /**
     * provide a button for user to perform operations
     * @param requestMethod
     * @param word
     * @param meanings
     */
    public static void sendRequest(String requestMethod, String word, List<String> meanings){
        if (connect) {
            ClientMessage message = new ClientMessage(requestMethod, word, meanings);
            TCPDictionaryClient.message = message;
        }
    }

    /**
     * overloaded method, it doesn't take meanings as this one is for query and delete operations
     * @param requestMethod
     * @param word
     */
    public static void sendRequest(String requestMethod, String word){
        if (connect) {
            ClientMessage message = new ClientMessage(requestMethod, word);
            TCPDictionaryClient.message = message;
        }
    }

    /**
     * provide a button for user to end the session/disconnect from the server
     */
    public static void terminate(){
        connect = false;
    }

    /**
     * the button for user to enter ip and port to connect to the server
     * @param ip
     * @param port
     */
    public static void setIPAndPort(String ip, String port){
        TCPDictionaryClient.ip = ip;
        TCPDictionaryClient.port = Integer.parseInt(port);
    }
}
