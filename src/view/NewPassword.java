package view;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

public class NewPassword extends JDialog {

    private final JButton buttonOk;

    private final JTextField textFieldUserName;

    private final JTextField textFieldNewPassword, textFieldConfirmPassword;

    public NewPassword(Color c1, Color c2, Color c3, Color c4, Color c5, Color c6) throws IOException, FontFormatException {
        // Initialization of the JFrame
        setBounds(420, 150, 600, 600);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(25f);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(c3);
                g.fillRoundRect(100, 20, 400, 90, 80, 80);
                g.fillRoundRect(450, 480, 100, 60, 40, 40);
                g.setColor(Color.WHITE);
                g.fillRoundRect(125, 150, 350, 70, 70, 70);
                g.fillRoundRect(125, 260, 350, 70, 70, 70);
                g.fillRoundRect(125, 370, 350, 70, 70, 70);
                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(30f));
                g.drawString("Change your", 210, 55);
                g.drawString("password", 225, 95);
            }
        };
        panel.setBackground(c2);
        panel.setLayout(null);

        // User textField
        textFieldUserName = new JTextField("User");
        textFieldUserName.setHorizontalAlignment(JTextField.CENTER);
        textFieldUserName.setBounds(125, 150, 350, 70);
        textFieldUserName.setFont(customFont);
        textFieldUserName.setForeground(c5);
        textFieldUserName.setCaretColor(c5);
        textFieldUserName.setBorder(BorderFactory.createLineBorder(c2));
        textFieldUserName.setOpaque(false);
        textFieldUserName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldUserName.getText().equals("User")) {
                    textFieldUserName.setText("");
                    textFieldUserName.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldUserName.getText().isEmpty()) {
                    textFieldUserName.setForeground(Color.GRAY);
                    textFieldUserName.setText("User");
                }
            }
        });
        panel.add(textFieldUserName);

        // Password textField
        textFieldNewPassword = new JTextField("New Password");
        textFieldNewPassword.setHorizontalAlignment(JTextField.CENTER);
        textFieldNewPassword.setBounds(125, 260, 350, 70);
        textFieldNewPassword.setFont(customFont);
        textFieldNewPassword.setForeground(Color.GRAY);
        textFieldNewPassword.setCaretColor(Color.GRAY);
        textFieldNewPassword.setBorder(BorderFactory.createLineBorder(c2));
        textFieldNewPassword.setOpaque(false);
        textFieldNewPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldNewPassword.getText().equals("New Password")) {
                    textFieldNewPassword.setText("");
                    textFieldNewPassword.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldNewPassword.getText().isEmpty()) {
                    textFieldNewPassword.setForeground(Color.GRAY);
                    textFieldNewPassword.setText("New Password");
                }
            }
        });
        panel.add(textFieldNewPassword);

        // Confirm Password textField
        textFieldConfirmPassword = new JTextField("Confirm Password");
        textFieldConfirmPassword.setHorizontalAlignment(JTextField.CENTER);
        textFieldConfirmPassword.setBounds(125, 370, 350, 70);
        textFieldConfirmPassword.setFont(customFont);
        textFieldConfirmPassword.setForeground(Color.GRAY);
        textFieldConfirmPassword.setCaretColor(Color.GRAY);
        textFieldConfirmPassword.setBorder(BorderFactory.createLineBorder(c2));
        textFieldConfirmPassword.setOpaque(false);
        textFieldConfirmPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldConfirmPassword.getText().equals("Confirm Password")) {
                    textFieldConfirmPassword.setText("");
                    textFieldConfirmPassword.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldConfirmPassword.getText().isEmpty()) {
                    textFieldConfirmPassword.setForeground(Color.GRAY);
                    textFieldConfirmPassword.setText("Confirm Password");
                }
            }
        });
        panel.add(textFieldConfirmPassword);

        // Ok button to change the password
        buttonOk = new JButton("Ok !");
        buttonOk.setActionCommand("Ok");
        buttonOk.setBounds(450, 480, 100, 60);
        buttonOk.setFont(customFont.deriveFont(25f));
        buttonOk.setForeground(Color.WHITE);
        buttonOk.setBackground(c3);
        buttonOk.setBorder(BorderFactory.createLineBorder(c2));
        buttonOk.setFocusable(false);
        buttonOk.setFocusPainted(false);
        panel.add(buttonOk);

        getContentPane().add(panel);
    }

    public String getTextFieldUserName(){
        return textFieldUserName.getText();
    }

    public String getTextFieldNewPassword() {
        return textFieldNewPassword.getText();
    }

    public String getTextFieldConfirmPassword() {
        return textFieldConfirmPassword.getText();
    }

    public void setTextFieldUserName(String s){
        textFieldUserName.setText(s);
    }

    public void setTextFieldNewPassword(String s){
        textFieldNewPassword.setText(s);
    }

    public void setTextFieldConfirmPassword(String s){
        textFieldConfirmPassword.setText(s);
    }

    public void addAllListener(ClientController clientController){
        buttonOk.addActionListener(clientController);
    }
}
