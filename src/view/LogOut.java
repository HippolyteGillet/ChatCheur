package view;

import controller.ClientController;
import model.Log;
import model.Message;
import model.user.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LogOut extends JDialog {
    private JButton ouiButton;
    Font font;

    {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LogOut(JFrame parentFrame, Color c1, Color c2, Color c3, Color c4, Color c5, Color c6) {
        setBounds(650, 200, 400, 400);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // Création du JPanel et ajout à la JDialog
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(c3);
                g.fillRoundRect(50, 40, 300, 70, 60, 60);
                g.setColor(c5);
                g.setFont(font);
                g.drawString("Se déconnecter ?", 75, 85);

                g.setColor(c5);
                g.fillRoundRect(125, 160, 150, 60, 60, 60);
                g.fillRoundRect(125, 250, 150, 60, 60, 60);

                g.setColor(c3);
                g.drawString("Oui", 170, 200);
                g.drawString("Non", 170, 290);

            }
        };
        panel.setBackground(c2);
        panel.setLayout(null);
        getContentPane().add(panel);

        // Création des boutons
        ouiButton = new JButton("");
        ouiButton.setActionCommand("Disconnection");
        ouiButton.setLocation(125, 160);
        ouiButton.setBounds(125, 160, 150, 60);
        ouiButton.setFont(font);
        ouiButton.setForeground(null);
        ouiButton.setOpaque(false);
        ouiButton.setContentAreaFilled(false);
        ouiButton.setBorderPainted(false);
        ouiButton.setFocusable(false);

        JButton non = new JButton("");
        non.setBounds(125, 250, 150, 60);
        non.setFont(font);
        non.setForeground(null);
        non.setOpaque(false);
        non.setContentAreaFilled(false);
        non.setBorderPainted(false);
        non.setFocusable(false);

        non.addActionListener(e -> dispose());

        panel.add(ouiButton);
        panel.add(non);

    }


    public void addAllListener(ClientController controller) {
        this.ouiButton.addActionListener(controller);
    }
}
