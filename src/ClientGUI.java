import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-03-28 20:13
 **/
public class ClientGUI extends JFrame {
    private Box wordAndMeaningsBox, textAreaBox, ipAndPortBox, aggregatedBox;
    private Box wordAndMeaningsLabelBox, wordAndMeaningsTextBox, responseBox, ipAndPortLabelBox, ipAndPortTextBox;
    private JTextField wordTextField, meaningsTextField, ipTextField, portTextField;
    private JTextArea response;
    private JButton queryButton, addButton, removeButton, updateButton, quitButton, clearButton, connectButton;
    public ClientGUI() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public JTextArea getResponse() {
        return response;
    }

    private void init() {
        ipTextField = new JTextField(10);
        portTextField = new JTextField(10);
        wordTextField = new JTextField(10);
        meaningsTextField = new JTextField(10);
        response = new JTextArea(10,35);
        wordAndMeaningsBox = Box.createHorizontalBox();
        textAreaBox = Box.createVerticalBox();
        ipAndPortBox = Box.createHorizontalBox();
        aggregatedBox = Box.createVerticalBox();
        queryButton = new JButton("Query");
        queryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //trim the input in case the input contains spaces
                TCPDictionaryClient.sendRequest("query", wordTextField.getText().trim().toLowerCase());
                wordTextField.setText("");
            }
        });
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String input = meaningsTextField.getText();
                String[] split = input.split("[^a-zA-Z ]+");
                List<String> meanings = new ArrayList<String>();
                for (String s : split) {
                    if (!s.trim().isEmpty()) {
                        meanings.add(s);
                    }
                }

                TCPDictionaryClient.sendRequest("add", wordTextField.getText().trim().toLowerCase(),meanings);
                wordTextField.setText("");
                meaningsTextField.setText("");

            }
        });
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this word?","Delete Confirmation",JOptionPane.YES_NO_OPTION);
                //only delete the word if the user clicked yes
                if (n == JOptionPane.YES_OPTION) {
                    TCPDictionaryClient.sendRequest("remove", wordTextField.getText().trim().toLowerCase());
                    wordTextField.setText("");
                } else if (n == JOptionPane.NO_OPTION) {
                }



            }
        });
        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = meaningsTextField.getText();
                String[] split = input.split("[^a-zA-Z ]+");
                List<String> meanings = new ArrayList<String>();
                for (String s : split) {
                    if (!s.trim().isEmpty()) {
                        meanings.add(s);
                    }
                }

                TCPDictionaryClient.sendRequest("update", wordTextField.getText().trim().toLowerCase(),meanings);
                wordTextField.setText("");
                meaningsTextField.setText("");

            }
        });
        quitButton = new JButton("Disconnect");
        quitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TCPDictionaryClient.terminate();
                response.setText("You have been disconnected from the server, now you can close the window");
            }
        });

        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipTextField.getText().trim();
                String port = portTextField.getText().trim();
                TCPDictionaryClient.setIPAndPort(ip,port);
            }
        });

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                wordTextField.setText("");
                meaningsTextField.setText("");
                response.setText("");
            }
        });
        ipAndPortLabelBox = Box.createVerticalBox();
        ipAndPortTextBox = Box.createVerticalBox();
        wordAndMeaningsLabelBox = Box.createVerticalBox();
        wordAndMeaningsTextBox = Box.createVerticalBox();
        responseBox = Box.createHorizontalBox();
        ipAndPortLabelBox.add(new JLabel("IP Address: "));
        ipAndPortLabelBox.add(new JLabel("Port: "));
        wordAndMeaningsLabelBox.add(new JLabel("Word："));
        wordAndMeaningsLabelBox.add(new JLabel("Meanings："));
        ipAndPortTextBox.add(ipTextField);
        ipAndPortTextBox.add(portTextField);
        wordAndMeaningsTextBox.add(wordTextField);
        wordAndMeaningsTextBox.add(meaningsTextField);
        responseBox.add(response);
        ipAndPortBox.add(ipAndPortLabelBox);
        ipAndPortBox.add(Box.createHorizontalStrut(215));
        ipAndPortBox.add(ipAndPortTextBox);

        wordAndMeaningsBox.add(wordAndMeaningsLabelBox);
        wordAndMeaningsBox.add(Box.createHorizontalStrut(215));
        wordAndMeaningsBox.add(wordAndMeaningsTextBox);
        textAreaBox.add(responseBox);
        aggregatedBox.add(ipAndPortBox);
        aggregatedBox.add(connectButton);
        aggregatedBox.add(quitButton);
        aggregatedBox.add(Box.createVerticalStrut(50));
        aggregatedBox.add(textAreaBox);
        aggregatedBox.add(Box.createVerticalStrut(50));
        aggregatedBox.add(wordAndMeaningsBox);

        aggregatedBox.add(queryButton);
        aggregatedBox.add(addButton);
        aggregatedBox.add(removeButton);
        aggregatedBox.add(updateButton);
        aggregatedBox. add(clearButton);

        add(aggregatedBox);



    }


}
