package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LogOut extends JDialog {
    Font font;
    {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LogOut(JFrame parentFrame) {
        setBounds(650, 200, 400, 400);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // Création du JPanel et ajout à la JDialog
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(new Color(20, 48, 46));
                g.fillRoundRect(50, 40, 300, 70, 60, 60);
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("Se déconnecter ?", 75, 85);

                g.setColor(Color.WHITE);
                g.fillRoundRect(125, 160, 150, 60, 60, 60);
                g.fillRoundRect(125, 250, 150, 60, 60, 60);

                g.setColor(new Color(20, 48, 46));
                g.drawString("Oui", 170, 200);
                g.drawString("Non", 170, 290);

            }
        };
        panel.setBackground(new Color(147, 185, 175));
        panel.setLayout(null);
        getContentPane().add(panel);

        // Création des boutons
        JButton oui = new JButton("");
        oui.setLocation(125, 160);
        oui.setBounds(125, 160, 150, 60);
        oui.setFont(font);
        oui.setForeground(null);
        oui.setOpaque(false);
        oui.setContentAreaFilled(false);
        oui.setBorderPainted(false);
        oui.setFocusable(false);

        JButton non = new JButton("");
        non.setBounds(125, 250, 150, 60);
        non.setFont(font);
        non.setForeground(null);
        non.setOpaque(false);
        non.setContentAreaFilled(false);
        non.setBorderPainted(false);
        non.setFocusable(false);

        oui.addActionListener(e -> {
            dispose();
            parentFrame.dispose();
            Menu logIn;
            try {
                logIn = new Menu();
            } catch (IOException | FontFormatException ex) {
                throw new RuntimeException(ex);
            }
           logIn.setVisible(true);
        });
        non.addActionListener(e -> dispose());

        panel.add(oui);
        panel.add(non);

    }
}
