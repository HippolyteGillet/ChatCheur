package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class NewPassword extends JDialog {
    public NewPassword() {
        setBounds(420, 150, 600, 600);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // Création du JPanel et ajout à la JDialog
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Font customFont;
                try {
                    customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(25f);
                } catch (FontFormatException | IOException e) {
                    throw new RuntimeException(e);
                }
                g.setColor(new Color(183, 90, 25));
                g.fillRoundRect(50, 40, 300, 70, 60, 60);
                g.setColor(Color.WHITE);
                g.setFont(customFont);
                g.drawString("Nouveau mot de passe", 75, 85);
            }
        };
        panel.setBackground(new Color(238, 213, 173));
        panel.setLayout(null);
        getContentPane().add(panel);
    }
}
