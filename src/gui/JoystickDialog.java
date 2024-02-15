package gui;

import javax.swing.*;
import java.awt.event.*;

public class JoystickDialog extends JDialog {
    private JPanel contentPane;

    public JoystickDialog() {
        setTitle("Super Pac-Man Joystick");
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        pack();
        setVisible(true);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        JoystickDialog dialog = new JoystickDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
