import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: Assignment1
 * @description:
 * @author: Pinzhuo Zhao, StudentID:1043915
 * @create: 2021-04-03 00:22
 **/
public class ServerGUI extends JFrame {
    private JTextField portTextField;
    private JLabel pathLabel = new JLabel("Path：");
    private JLabel portLabel = new JLabel("Port：");
    private JTextField pathTextField = new JTextField(25);
    private JButton browseButton = new JButton("Browse"),launchButton =new JButton("Launch");
    private JTextArea response = new JTextArea(9,25);
    private Box textAreaBox;

    public JTextArea getResponse() {
        return response;
    }

    public ServerGUI() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void init() {
        portTextField = new JTextField(10);
        JPanel panel=new JPanel();
        panel.add(portLabel);
        panel.add(portTextField);
        panel.add(pathLabel);
        panel.add(pathTextField);
        panel.add(browseButton);
        browseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser =new JFileChooser();
                int val= chooser.showOpenDialog(null);
                if( val == JFileChooser.APPROVE_OPTION)
                {
                    pathTextField.setText(chooser.getSelectedFile().toString());
                }
                else
                {
                    pathTextField.setText("Please select a file");
                }
            }

        });
        launchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String path = pathTextField.getText().trim();
                String port = portTextField.getText().trim();
                TCPDictionaryServer.setPortAndPath(port,path);

            }

        });
        add(panel);
        textAreaBox = Box.createVerticalBox();
        textAreaBox.add(response);
        textAreaBox.add(launchButton);
        add(textAreaBox);

    }

    public static void main(String[] args) {
        new ServerGUI();
    }
}
