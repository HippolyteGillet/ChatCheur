package view;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LogOut extends JDialog {
    private final JButton yesButton;
    private final Font font;

    {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LogOut(Color c1, Color c2, Color c3, Color c4, Color c5, Color c6) {
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
                g.setColor(Color.WHITE);
                g.setFont(font);
                g.drawString("Disconnect ?", 110, 85);
                g.setColor(c3);
                g.fillRoundRect(125, 160, 150, 60, 60, 60);
                g.fillRoundRect(125, 260, 150, 60, 60, 60);
                g.setColor(Color.WHITE);
                g.drawString("Yes", 175, 200);
                g.drawString("No", 180, 300);
            }
        };
        panel.setBackground(c2);
        panel.setLayout(null);
        getContentPane().add(panel);

        // if the user click on yes, the client will be disconnected
        yesButton = new JButton("");
        yesButton.setActionCommand("Disconnection");
        yesButton.setLocation(125, 160);
        yesButton.setBounds(125, 160, 150, 60);
        yesButton.setFont(font);
        yesButton.setForeground(null);
        yesButton.setOpaque(false);
        yesButton.setContentAreaFilled(false);
        yesButton.setBorderPainted(false);
        yesButton.setFocusable(false);

        // if the user click on no, the dialog will be closed
        JButton noButton = new JButton("");
        noButton.setBounds(125, 250, 150, 60);
        noButton.setFont(font);
        noButton.setForeground(null);
        noButton.setOpaque(false);
        noButton.setContentAreaFilled(false);
        noButton.setBorderPainted(false);
        noButton.setFocusable(false);
        noButton.addActionListener(e -> dispose());

        panel.add(yesButton);
        panel.add(noButton);

    }


    public void addAllListener(ClientController controller) {
        this.yesButton.addActionListener(controller);
    }
}
