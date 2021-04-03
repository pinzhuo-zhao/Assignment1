import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-24 16:09
 **/
public class TCPDictionaryServer {
    private static volatile Integer port;
    private static volatile String filePath;
    private static Boolean launched = false;

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
                } else if (requestMethod.equals("remove")) {
                    result =  dictionary.remove(message.getWord());
                } else if (requestMethod.equals("update")) {
                    result = dictionary.update(message.getWord(), message.getMeanings());
                }
                out.writeUTF(result);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
// updated a GUI for Server, so no need for taking parameters from command line
//        String portFromInput = args[0];
//        String filePath = args[1];
//        port = Integer.parseInt(portFromInput);
        ServerGUI gui = new ServerGUI();
        ServerSocket socket = null;
        while (!launched) {
            if (port != null && filePath != null) {
                try {
                    socket = new ServerSocket(port);
                    launched = true;
                    gui.getResponse().setText("Remote Dictionary Server launched at port: " + port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
            MyDictionary dictionary = new MyDictionary(new File(filePath));
            dictionary.loadToMap();
            try {
                System.out.println("Remote Dictionary Server Launched at port " + port);
                while (true) {
                    Socket client = socket.accept();
                    Thread serverThread = new Thread(() -> serve(client, dictionary));
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

    public static void setPortAndPath(String port, String path){
        TCPDictionaryServer.port = Integer.parseInt(port);
        TCPDictionaryServer.filePath = path;

    }


}
