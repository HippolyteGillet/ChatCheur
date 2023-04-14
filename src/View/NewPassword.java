package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

public class NewPassword extends JDialog {
    public NewPassword() throws IOException, FontFormatException {
        setBounds(420, 150, 600, 600);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(25f);

        // Création du JPanel et ajout à la JDialog
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(140,56,6));
                g.fillRoundRect(100, 20, 400, 90, 80, 80);
                g.fillRoundRect(450, 480, 100, 60, 40, 40);
                g.setColor(new Color(183, 90, 25));
                g.fillRoundRect(125, 150, 350, 70, 70, 70);
                g.fillRoundRect(125, 260, 350, 70, 70, 70);
                g.fillRoundRect(125, 370, 350, 70, 70, 70);
                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(30f));
                g.drawString("Changer votre", 200, 55);
                g.drawString("mot de passe", 210, 95);
            }
        };
        panel.setBackground(new Color(238, 213, 173));
        panel.setLayout(null);

        JTextField textField1 = new JTextField("Utilisateur");
        textField1.setHorizontalAlignment(JTextField.CENTER);
        textField1.setBounds(125, 150, 350, 70);
        textField1.setFont(customFont);
        textField1.setForeground(new Color(225,225,225));
        textField1.setCaretColor(new Color(225,225,225));
        textField1.setBorder(BorderFactory.createLineBorder(new Color(238, 213, 173)));
        textField1.setOpaque(false);
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField1.getText().equals("Utilisateur")) {
                    textField1.setText("");
                    textField1.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(new Color(225,225,225));
                    textField1.setText("Utilisateur");
                }
            }
        });
        panel.add(textField1);

        JTextField textField2 = new JTextField("Nouveau mot de passe");
        textField2.setHorizontalAlignment(JTextField.CENTER);
        textField2.setBounds(125, 260, 350, 70);
        textField2.setFont(customFont);
        textField2.setForeground(new Color(225,225,225));
        textField2.setCaretColor(new Color(225,225,225));
        textField2.setBorder(BorderFactory.createLineBorder(new Color(238, 213, 173)));
        textField2.setOpaque(false);
        textField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField2.getText().equals("Nouveau mot de passe")) {
                    textField2.setText("");
                    textField2.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField2.getText().isEmpty()) {
                    textField2.setForeground(new Color(225,225,225));
                    textField2.setText("Nouveau mot de passe");
                }
            }
        });
        panel.add(textField2);

        JTextField textField3 = new JTextField("Confirmer mot de passe");
        textField3.setHorizontalAlignment(JTextField.CENTER);
        textField3.setBounds(125, 370, 350, 70);
        textField3.setFont(customFont);
        textField3.setForeground(new Color(225,225,225));
        textField3.setCaretColor(new Color(225,225,225));
        textField3.setBorder(BorderFactory.createLineBorder(new Color(238, 213, 173)));
        textField3.setOpaque(false);
        textField3.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField3.getText().equals("Confirmer mot de passe")) {
                    textField3.setText("");
                    textField3.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField3.getText().isEmpty()) {
                    textField3.setForeground(new Color(225,225,225));
                    textField3.setText("Confirmer mot de passe");
                }
            }
        });
        panel.add(textField3);

        JButton button = new JButton("Ok !");
        button.setBounds(450, 480, 100, 60);
        button.setFont(customFont.deriveFont(25f));
        button.setForeground(new Color(225,225,225));
        button.setBackground(new Color(140,56,6));
        button.setBorder(BorderFactory.createLineBorder(new Color(238, 213, 173)));
        button.setFocusPainted(false);
        button.addActionListener(e -> dispose());
        panel.add(button);

        getContentPane().add(panel);
    }
}
