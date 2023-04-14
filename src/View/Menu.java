package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {
    public Menu() throws IOException, FontFormatException {
        // Création de mon conteneur JPanel
        JPanel panel = new JPanel(new BorderLayout()) {
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

        JTextField textField1 = new JTextField("Utilisateur");
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

        JTextField textField2 = new JTextField("Mot de passe");
        textField2.setHorizontalAlignment(JTextField.CENTER);
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
                    textField2.setText("");
                    textField2.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField2.getText().isEmpty()) {
                    textField2.setForeground(Color.GRAY);
                    textField2.setText("Mot de passe");
                }
            }
        });
        panel.add(textField2);

        JButton button = new JButton("Connexion");
        button.setBounds(200, 500, 300, 75);
        button.setFont(customFont);
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> {
            Home home;
            try {
                home = new Home();
            } catch (IOException | FontFormatException ex) {
                throw new RuntimeException(ex);
            }
            home.setVisible(true);
            dispose();
        });
        panel.add(button);

        JLabel label = new JLabel("Mot de passe oublié ?");
        label.setBounds(250, 435, 250, 40);
        label.setFont(customFont.deriveFont(20f));
        label.setForeground(Color.WHITE);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewPassword popup;
                try {
                    popup = new NewPassword();
                } catch (IOException | FontFormatException ex) {
                    throw new RuntimeException(ex);
                }
                popup.setVisible(true);
            }
        });
        panel.add(label);

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
