package view;

import model.Log;
import model.Message;
import model.user.User;
import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Menu extends JFrame {
    private JPanel panel;

    private JTextField textField1;
    private JPasswordField textField2;
    private JButton button, mdpOublie;
    private JLabel label;

    public Menu(List<User> userList, List<Log> logList, List<Message> messageList) throws IOException, FontFormatException {
        // Création de mon conteneur JPanel
        panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(20, 48, 46));
                g.fillRoundRect(100, 40, 500, 125, 120, 120);
                g.fillRoundRect(200, 500, 300, 75, 60, 60);
                g.setColor(Color.WHITE);
                g.fillRoundRect(150, 225, 400, 80, 80, 80);
                g.fillRoundRect(150, 355, 400, 80, 80, 80);
                try {
                    Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("ALBAS.TTF"));
                    Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc"));
                    g.setFont(customFont1.deriveFont(80f));
                    g.drawString("ChatCheur", 160, 120);
                    g.setFont(customFont2.deriveFont(60f));
                } catch (IOException | FontFormatException e) {
                    e.printStackTrace();
                }
            }
        };
        panel.setLayout(null);
        add(panel);
        panel.setBackground(new Color(147, 185, 175));
        setTitle("ChatCheur");
        setSize(700, 700);
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(30f);

        textField1 = new JTextField("Utilisateur");
        textField1.setHorizontalAlignment(JTextField.CENTER);
        textField1.setBounds(150, 225, 400, 80);
        textField1.setFont(customFont);
        textField1.setForeground(Color.GRAY);
        textField1.setCaretColor(Color.GRAY);
        textField1.setBorder(BorderFactory.createLineBorder(new Color(147, 185, 175)));
        textField1.setOpaque(false);
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField1.getText().equals("Utilisateur")) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.GRAY);
                    textField1.setText("Utilisateur");
                }
            }
        });
        panel.add(textField1);

        textField2 = new JPasswordField("Mot de passe");
        textField2.setEchoChar('\u0000');
        textField2.setHorizontalAlignment(JPasswordField.CENTER);
        textField2.setBounds(150, 355, 400, 80);
        textField2.setFont(customFont);
        textField2.setForeground(Color.GRAY);
        textField2.setCaretColor(Color.GRAY);
        textField2.setBorder(BorderFactory.createLineBorder(new Color(147, 185, 175)));
        textField2.setOpaque(false);
        textField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField2.getText().equals("Mot de passe")) {
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
                    textField2.setText("Mot de passe");
                }
            }
        });
        panel.add(textField2);

        button = new JButton("Connexion");
        button.setBounds(200, 500, 300, 75);
        button.setFont(customFont);
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        panel.add(button);

        mdpOublie = new JButton("Mot de passe oublié ?");
        mdpOublie.setOpaque(false);
        mdpOublie.setContentAreaFilled(false);
        mdpOublie.setBorderPainted(false);
        mdpOublie.setActionCommand("mdpOublie");
        mdpOublie.setBounds(200, 435, 300, 40);
        mdpOublie.setFont(customFont.deriveFont(20f));
        mdpOublie.setForeground(Color.WHITE);
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

    public JButton getButton() {
        return button;
    }

    public void addAllListener (ClientController controller) {
        this.button.addActionListener(controller);
        this.mdpOublie.addActionListener(controller);
    }


    public void afficherBannissement() {
        JOptionPane.showMessageDialog(this, "Vous avez été banni du chat", "Bannissement", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMdpIncorrect() {
        JOptionPane.showMessageDialog(this, "Mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherUserUknown(){
        JOptionPane.showMessageDialog(this, "Utilisateur introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
