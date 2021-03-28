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
    private static int port =9999;
    // Identifies the user number connected
    private static int counter = 0;

    private static void serve(Socket client, MyDictionary dictionary){
        ObjectInputStream in = null;
//        BufferedWriter out = null;
        DataOutputStream out = null;
        try {
            in = new ObjectInputStream(client.getInputStream());
//            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
            out = new DataOutputStream(client.getOutputStream());
            while (true) {
                Object obj = in.readObject();
                ClientMessage message = null;
                if (obj instanceof ClientMessage) {
                    message = (ClientMessage) obj;
                }
                String requestMethod = message.getRequest();
                String result = null;
                if (requestMethod.equals("query")) {
                    result = dictionary.query(message.getWord());

                } else if (requestMethod.equals("add")) {
                    result = dictionary.add(message.getWord(), message.getMeanings());
                } else if (requestMethod.equals("delete")) {
                    result =  dictionary.remove(message.getWord());
                } else if (requestMethod.equals("update")) {
                    result = dictionary.update(message.getWord(), message.getMeanings());
                }
                out.writeUTF(result);
                out.flush();
            }

            //在这里往下，看客户端会发来怎样的一条信息，然后相应调字典类处理

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
//        String portFromInput = args[0];
//        String filePath = args[1];
//        port = Integer.parseInt(portFromInput);
        //put the IO Objects and the Socket here so we can close them in the finally block
        MyDictionary dictionary = new MyDictionary(new File("dictionary.txt"));
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            System.out.println("Remote MyDictionary Server Launched on port " + port);
            while (true){
                Socket client = socket.accept();
                counter++;
                Thread serverThread = new Thread(()->serve(client, dictionary));
                serverThread.start();
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
