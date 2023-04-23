package view;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Settings extends JDialog{
    public Settings() throws IOException, FontFormatException {
        setTitle("Settings");
        setBounds(400, 80, 700, 700);
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
            }
        };
        panel.setBackground(new Color(209, 168, 242));
        panel.setLayout(null);
        panel.setVisible(true);



        getContentPane().add(panel);

    }

    public void addAllListener(ClientController clientController) {
        //buttonOk.addActionListener(clientController);
    }
}
