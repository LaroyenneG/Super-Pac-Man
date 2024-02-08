package ui.chat;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatDialog extends JDialog {
    private JPanel contentPane;
    private JTextArea conversationTextArea;
    private JTextField inputTextField;
    private JLabel infoLabel;
    private JButton sendButton;

    public ChatDialog() {
        setTitle("Super Pac-Man Chat");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(sendButton);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        inputTextField.addActionListener(e -> onSend());

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setResizable(false);
    }

    public static void main(String[] args) {
        var dialog = new ChatDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onSend() {
        var text = inputTextField.getText();
        inputTextField.setText("");
        conversationTextArea.setText(conversationTextArea.getText() + "\n" + text);
    }

    private void onCancel() {
        dispose();
    }
}
