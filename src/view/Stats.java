package view;

import DAO.ConnectionDataBaseSQL;
import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import controller.ClientController;
import model.user.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stats extends JDialog{
    private JButton buttonOk;
    public Stats() throws IOException, FontFormatException {
        setBounds(420, 150, 600, 600);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(25f);
        // Création du JPanel et ajout à la JDialog
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(140, 56, 6));
                g.fillRoundRect(100, 20, 400, 90, 80, 80);
                g.fillRoundRect(450, 480, 100, 60, 40, 40);
                g.setColor(new Color(183, 90, 25));
                g.fillRoundRect(125, 150, 350, 70, 70, 70);
                g.fillRoundRect(125, 260, 350, 70, 70, 70);
                g.fillRoundRect(125, 370, 350, 70, 70, 70);
                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(30f));
            }
        };
        panel.setBackground(new Color(238, 213, 173));
        panel.setLayout(null);

        buttonOk = new JButton("Ok !");
        buttonOk.setActionCommand("Ok");
        buttonOk.setBounds(450, 480, 100, 60);
        buttonOk.setFont(customFont.deriveFont(25f));
        buttonOk.setForeground(new Color(225,225,225));
        buttonOk.setBackground(new Color(140,56,6));
        buttonOk.setBorder(BorderFactory.createLineBorder(new Color(238, 213, 173)));
        buttonOk.setFocusPainted(false);
        panel.add(buttonOk);

    }

    public void addAllListener(ClientController clientController){
        buttonOk.addActionListener(clientController);
    }

}
