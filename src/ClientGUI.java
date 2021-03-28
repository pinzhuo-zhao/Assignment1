import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-28 20:13
 **/
public class ClientGUI extends JFrame {
    Box box,boxone,boxtwo;
    Box text1,text2,text3,text4,text5;
    JTextField one,two;
    JTextArea response;
    JButton button1,button2,button3,button4,button5;
    //ButtonExit buttonexit;
    public ClientGUI() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    void init() {
        one = new JTextField(10);
        two = new JTextField(10);
        response = new JTextArea(9,35);
        box = Box.createHorizontalBox();
        boxone = Box.createVerticalBox();
        button1 = new JButton("Query");
        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //trim the input in case the input contains spaces
                TCPDictionaryClient.sendRequest("query",one.getText().trim().toLowerCase());
            }
        });
        button2 = new JButton("Add");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String input = two.getText();
                String[] split = input.split("[^a-zA-Z ]+");
                List<String> meanings = new ArrayList<String>();
                for (String s : split) {
                    if (!s.trim().isEmpty()) {
                        meanings.add(s);
                    }
                }

                TCPDictionaryClient.sendRequest("add",one.getText().trim().toLowerCase(),meanings);

            }
        });
        button3 = new JButton("Remove");
        button4 = new JButton("Update");
        button5 = new JButton("Quit");
        button5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TCPDictionaryClient.terminate();
                response.setText("You have been disconnected from the server, now you can close the window");
            }
        });
        text1 = Box.createVerticalBox();
        text2 = Box.createVerticalBox();
        text3 = Box.createHorizontalBox();
        text1.add(new JLabel("Word："));
        text1.add(new JLabel("Meanings："));

        text2.add(one);
        text2.add(two);
        text3.add(response);
        //text2.addAncestorListener(listener);
        box.add(text1);
        box.add(Box.createHorizontalStrut(215));
        box.add(text2);
        boxone.add(text3);
        add(box);
        add(boxone);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
    }

    public static void main(String[] args) {
        ClientGUI view = new ClientGUI();
    }

}
