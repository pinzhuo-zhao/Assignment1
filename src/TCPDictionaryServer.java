import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-24 16:09
 **/
public class TCPDictionaryServer {
    private static int port;
    // Identifies the user number connected
    private static int counter = 0;

    private static void serve(Socket client){
        ObjectInputStream in = null;
        BufferedWriter out = null;
        try {
            in = new ObjectInputStream(client.getInputStream());
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
            in.readObject();
            //在这里往下，看客户端会发来怎样的一条信息，然后相应调字典类处理
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        String portFromInput = args[0];
        String filePath = args[1];
        port = Integer.parseInt(portFromInput);
        //put the IO Objects and the Socket here so we can close them in the finally block

        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            while (true){
                System.out.println("Remote MyDictionary Server Launched on port " + port);
                Socket client = socket.accept();
                counter++;
                Thread serverThread = new Thread(()->serve(client));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //close all the resources when the program is about to end
        finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
