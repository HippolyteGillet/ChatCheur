package view;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Settings extends JDialog{

    private JTextField textField1, textField2;
    private JButton changeUsername, changePassword;

    public Settings() throws IOException, FontFormatException {
        setTitle("Settings");
        setBounds(400, 60, 700, 770);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(25f);
        // Création du JPanel et ajout à la JDialog
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(30f));
                g.fillRoundRect(150, 120, 400, 80, 60, 60);
                g.fillRoundRect(150, 480, 400, 80, 60, 60);
                g.setColor(new Color(238, 213, 173));
                g.fillRoundRect(250, 250, 200, 60, 60, 60);
                g.fillRoundRect(250, 610, 200, 60, 60, 60);

            }
        };
        panel.setBackground(new Color(140,56,6));

        JLabel label1 = new JLabel("Don't like your username? No problem!");
        label1.setFont(customFont);
        label1.setForeground(Color.WHITE);
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setBounds(0, 30, 700, 60);
        panel.add(label1);

        JLabel label2 = new JLabel("My god your password too? Okay!");
        label2.setFont(customFont);
        label2.setForeground(Color.WHITE);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setBounds(0, 390, 700, 60);
        panel.add(label2);

        textField1 = new JTextField("New username");
        textField1.setHorizontalAlignment(JTextField.CENTER);
        textField1.setBounds(150, 120, 400, 80);
        textField1.setFont(customFont);
        textField1.setForeground(new Color(100, 98, 98));
        textField1.setCaretColor(Color.GRAY);
        textField1.setBorder(null);
        textField1.setOpaque(false);
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //if (textField1.getText().equals("New username")) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                //}
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField1.getText();
            }
        });
        panel.add(textField1);

        textField2 = new JTextField("New password");
        textField2.setHorizontalAlignment(JTextField.CENTER);
        textField2.setBounds(150, 480, 400, 80);
        textField2.setFont(customFont);
        textField2.setForeground(new Color(100, 98, 98));
        textField2.setCaretColor(Color.GRAY);
        textField2.setBorder(null);
        textField2.setOpaque(false);
        textField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //if (textField2.getText().equals("New username")) {
                textField2.setText("");
                textField2.setForeground(Color.BLACK);
                //}
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField2.getText();
            }
        });
        panel.add(textField2);

        changeUsername = new JButton("Change");
        changeUsername.setFont(customFont);
        changeUsername.setForeground(Color.BLACK);
        changeUsername.setOpaque(false);
        changeUsername.setHorizontalAlignment(JButton.CENTER);
        changeUsername.setBounds(250, 250, 200, 60);
        changeUsername.setFocusPainted(false);
        changeUsername.setContentAreaFilled(false);
        changeUsername.setBorderPainted(false);
        changeUsername.setActionCommand("changeUsername");
        panel.add(changeUsername);

        changePassword = new JButton("Change");
        changePassword.setFont(customFont);
        changePassword.setForeground(Color.BLACK);
        changePassword.setOpaque(false);
        changePassword.setHorizontalAlignment(JButton.CENTER);
        changePassword.setBounds(250, 610, 200, 60);
        changePassword.setFocusPainted(false);
        changePassword.setContentAreaFilled(false);
        changePassword.setBorderPainted(false);
        changePassword.setActionCommand("changePassword");
        panel.add(changePassword);

        panel.setLayout(null);
        panel.setVisible(true);
        getContentPane().add(panel);

    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public void addAllListener(ClientController clientController) {
        if (!Objects.equals(textField1.getText(), "")) changeUsername.addActionListener(clientController);
        if (!Objects.equals(textField2.getText(), "")) changePassword.addActionListener(clientController);
    }
}
