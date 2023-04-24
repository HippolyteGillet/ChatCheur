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
    private JButton theme1, theme2, theme3;

    public Settings(Color c1, Color c2, Color c3, Color c4, Color c5) throws IOException, FontFormatException {
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
                g.setColor(c5);
                g.setFont(customFont.deriveFont(30f));
                g.fillRoundRect(150, 120, 400, 80, 60, 60);
                g.fillRoundRect(150, 480, 400, 80, 60, 60);
                g.fillRoundRect(75, 880, 550, 140, 60, 60);
                g.setColor(c1);
                g.fillRoundRect(250, 250, 200, 60, 60, 60);
                g.fillRoundRect(250, 610, 200, 60, 60, 60);
            }
        };
        panel.setBackground(c4);

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

        JLabel label3 = new JLabel("Set background colors here!");
        label3.setFont(customFont);
        label3.setForeground(Color.WHITE);
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setBounds(0, 750, 700, 60);
        panel.add(label3);

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

        theme1 = new JButton("");
        theme1.setFont(customFont);
        theme1.setOpaque(false);
        theme1.setHorizontalAlignment(JButton.CENTER);
        theme1.setBounds(140, 905, 100, 90);
        theme1.setFocusPainted(false);
        theme1.setContentAreaFilled(false);
        theme1.setBorderPainted(true);
        theme1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        theme1.setActionCommand("theme1");
        panel.add(theme1);

        theme2 = new JButton("");
        theme2.setFont(customFont);
        theme2.setOpaque(false);
        theme2.setHorizontalAlignment(JButton.CENTER);
        theme2.setBounds(300, 905, 100, 90);
        theme2.setFocusPainted(false);
        theme2.setContentAreaFilled(false);
        theme2.setBorderPainted(true);
        theme2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        theme2.setActionCommand("theme2");
        panel.add(theme2);

        theme3 = new JButton("");
        theme3.setFont(customFont);
        theme3.setOpaque(false);
        theme3.setHorizontalAlignment(JButton.CENTER);
        theme3.setBounds(460, 905, 100, 90);
        theme3.setFocusPainted(false);
        theme3.setContentAreaFilled(false);
        theme3.setBorderPainted(true);
        theme3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        theme3.setActionCommand("theme3");
        panel.add(theme3);

        panel.setLayout(null);
        panel.setVisible(true);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 0, 700, 700);
        panel.setPreferredSize(new Dimension(700, 2160));
        getContentPane().add(scrollPane);
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    //--------------------Add Listener-----------------------
    public void addAllListener(ClientController clientController) {
        if (!Objects.equals(textField1.getText(), "")) changeUsername.addActionListener(clientController);
        if (!Objects.equals(textField2.getText(), "")) changePassword.addActionListener(clientController);
        theme1.addActionListener(clientController);
        theme2.addActionListener(clientController);
        theme3.addActionListener(clientController);
    }
}
