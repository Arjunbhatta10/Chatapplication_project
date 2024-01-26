import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ServerGUI extends JFrame {

    private JButton startButton;
    private JButton stopButton;
    private JTextArea logArea;
    private ServerHandler serverHandler;

    public ServerGUI() {
        setTitle("Chat Server");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");
        logArea = new JTextArea();
        logArea.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        add(logArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        setVisible(true);
    }

    private void startServer() {
        try {
            serverHandler = new ServerHandler(12345);
            new Thread(serverHandler).start();
            logArea.append("Server started.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopServer() {
        if (serverHandler != null) {
            serverHandler.stop();
            logArea.append("Server stopped.\n");
        }
    }

    public static void main(String[] args) {
        new ServerGUI();
    }
}