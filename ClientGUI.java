import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class ClientGUI extends JFrame implements ActionListener {

    private JTextField messageField;
    private JButton sendButton;
    private JTextArea chatArea;
    private Socket socket;
    private PrintWriter out;

    public ClientGUI() {
        setTitle("Chat Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageField = new JTextField();
        sendButton = new JButton("Send");
        chatArea = new JTextArea();
        chatArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(chatArea, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(this);

        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new ClientHandler(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String message = messageField.getText();
        out.println(message);
        messageField.setText("");
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    public static void main(String[] args) {
        new ClientGUI();
    }
}
