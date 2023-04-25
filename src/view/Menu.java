package view;

import controller.ClientController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {
    private final JTextField textField1;
    private final JPasswordField textField2;
    private final JButton button, mdpOublie;

    public Menu(Color c1, Color c2, Color c3, Color c4, Color c5, Color c6) throws IOException, FontFormatException {
        // Creation of the JFrame
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(c3);
                g.fillRoundRect(100, 40, 500, 125, 120, 120);
                g.fillRoundRect(200, 500, 300, 75, 60, 60);
                g.setColor(Color.WHITE);
                g.fillRoundRect(150, 225, 400, 80, 80, 80);
                g.fillRoundRect(150, 355, 400, 80, 80, 80);
                try {
                    Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("ALBAS.TTF"));
                    Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc"));
                    g.setFont(customFont1.deriveFont(80f));
                    // Logo
                    g.drawString("ChatCheur", 160, 120);
                    g.setFont(customFont2.deriveFont(60f));
                } catch (IOException | FontFormatException e) {
                    e.printStackTrace();
                }
            }
        };
        panel.setLayout(null);
        add(panel);
        panel.setBackground(c2);
        setTitle("ChatCheur");
        setSize(700, 700);
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(30f);

        // User textField
        textField1 = new JTextField("User");
        textField1.setHorizontalAlignment(JTextField.CENTER);
        textField1.setBounds(150, 225, 400, 80);
        textField1.setFont(customFont);
        textField1.setForeground(Color.GRAY);
        textField1.setCaretColor(Color.GRAY);
        textField1.setBorder(BorderFactory.createLineBorder(c2));
        textField1.setOpaque(false);
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField1.getText().equals("User")) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.GRAY);
                    textField1.setText("User");
                }
            }
        });
        panel.add(textField1);

        // Password textField
        textField2 = new JPasswordField("Password");
        textField2.setEchoChar('\u0000');
        textField2.setHorizontalAlignment(JPasswordField.CENTER);
        textField2.setBounds(150, 355, 400, 80);
        textField2.setFont(customFont);
        textField2.setForeground(Color.GRAY);
        textField2.setCaretColor(Color.GRAY);
        textField2.setBorder(BorderFactory.createLineBorder(c2));
        textField2.setOpaque(false);
        textField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField2.getText().equals("Password")) {
                    textField2.setEchoChar('*');
                    textField2.setText("");
                    textField2.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField2.getText().isEmpty()) {
                    textField2.setEchoChar('\u0000');
                    textField2.setForeground(Color.GRAY);
                    textField2.setText("Password");
                }
            }
        });
        panel.add(textField2);

        // Connexion button
        button = new JButton("Connexion");
        button.setBounds(200, 500, 300, 75);
        button.setFont(customFont);
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        panel.add(button);

        mdpOublie = new JButton("Forgot your password ?");
        mdpOublie.setOpaque(false);
        mdpOublie.setContentAreaFilled(false);
        mdpOublie.setBorderPainted(false);
        mdpOublie.setActionCommand("forgotPassword");
        mdpOublie.setBounds(200, 435, 300, 40);
        mdpOublie.setFont(customFont.deriveFont(20f));
        mdpOublie.setForeground(Color.WHITE);
        mdpOublie.setFocusable(false);
        panel.add(mdpOublie);

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getUsername() {
        return textField1.getText();
    }

    public String getPassword() {
        return textField2.getText();
    }

    public void addAllListener (ClientController controller) {
        this.button.addActionListener(controller);
        this.mdpOublie.addActionListener(controller);
    }

    public void afficherBannissement() {
        JOptionPane.showMessageDialog(this, "You have been ban by someone", "Ban", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMdpIncorrect() {
        JOptionPane.showMessageDialog(this, "Wrong password", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherUserUknown(){
        JOptionPane.showMessageDialog(this, "Inexistant User", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
