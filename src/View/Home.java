package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.time.LocalDateTime;

public class Home extends JFrame {
    Color circleColor = Color.GREEN;
    String[] users = {"Juliette", "Nabtan", "Gaby", "Guy"}; //Mettre les pseudos des users
    String[] status = {"Online", "Online", "Away", "Banned"}; //Mettre les status des users
    String currentUser = "HippoSwagBG"; //Pseudo (ou prenom + nom) de l'utilisateur connecté
    List<String> messages = new ArrayList<>(); // Liste des messages, vide au début mais on peut y mettre l'historique
    Boolean inputReceived = false;
    Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(30f);
    Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("ALBAS.TTF"));
    int y = 0;

    public Home() throws IOException, FontFormatException {
        JPanel conversationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (messages.size() < 12) {
                    y = 600;
                } else {
                    y = 600 + (messages.size() - 12) * 53;
                }
                g.setColor(Color.WHITE);
                g.fillRoundRect(100, y + 90, 750, 60, 50, 50);
                for (int i = messages.size() - 1; i >= 0; i--) {
                    FontMetrics metrics = g.getFontMetrics(customFont1);
                    int textWidth = metrics.stringWidth(messages.get(i));
                    int textHeight = metrics.getHeight();

                    int x = 900 - textWidth - 20;
                    //int x2 = 50;
                    int width = textWidth + 30;
                    int height = textHeight + 10;

                    if (inputReceived) {
                        g.setColor(new Color(183, 90, 25));
                        g.fillRoundRect(x, y, width, height, 50, 50);

                        g.setColor(Color.WHITE);
                        g.setFont(customFont1);
                        g.drawString(messages.get(i), x + 15, y - 5 + textHeight);

                        //Affiche l'heure d'envoi du message et à modifier pour afficher la bonne heure avec la class message
                        LocalDateTime time = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Format d'affichage pour l'heure
                        String formattedTime = time.format(formatter);
                        g.setFont(customFont1.deriveFont(15f));
                        g.setColor(new Color(100, 98, 98));
                        g.drawString(formattedTime, x + 15, y + textHeight - 45);
                    }
                    y -= 53;
                }
            }
        };
        conversationPanel.setBackground(new Color(238, 213, 173));
        conversationPanel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane(conversationPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(350, 0, 950, 800);
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        add(scrollPane);

        JTextField textField1 = new JTextField("Saisir du texte");
        textField1.setHorizontalAlignment(JTextField.CENTER);
        textField1.setBounds(100, 690, 750, 60);
        textField1.setFont(customFont1);
        textField1.setForeground(Color.GRAY);
        textField1.setCaretColor(Color.GRAY);
        textField1.setBorder(BorderFactory.createLineBorder(new Color(238, 213, 173)));
        textField1.setOpaque(false);
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField1.getText().equals("Saisir du texte")) {
                    textField1.setText("");
                    textField1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.GRAY);
                    textField1.setText("Saisir du texte");
                }
            }
        });
        textField1.addActionListener(e -> {
            if (!textField1.getText().isEmpty()) {
                inputReceived = true;
                messages.add(textField1.getText());
                textField1.setText(null);
                if (messages.size() < 12) {
                    y = 600;
                } else {
                    y = 600 + (messages.size() - 12) * 53;
                }
                textField1.setBounds(100, y + 90, 750, 60);
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                conversationPanel.setPreferredSize(new Dimension(950, y + 196));
                scrollPane.getViewport().setViewPosition(new Point(0, y));
                conversationPanel.revalidate();
                conversationPanel.repaint();
            }
        });
        conversationPanel.add(textField1);

        JPanel contactPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(147, 185, 175));
                g.fillRect(0, 0, 350, 800);
                g.setColor(new Color(20, 48, 46));
                g.fillRoundRect(25, 15, 300, 90, 75, 75);
                g.fillRoundRect(90, 620, 160, 50, 50, 50);
                int y = 150;
                for (String s : status) {
                    if (s.equals("Banned")) {
                        g.setColor(new Color(100, 98, 98));
                    } else {
                        g.setColor(new Color(20, 48, 46));
                    }
                    g.fillRoundRect(10, y, 330, 70, 20, 20);
                    y += 90;
                }
                g.setColor(Color.WHITE);
                g.setFont(customFont1.deriveFont(25f));
                int x = 25 + ((300 - g.getFontMetrics().stringWidth(currentUser)) / 2);
                g.drawString(currentUser, x, 50);
                int y1 = 180;
                for (String user : users) {
                    g.drawString(user, 20, y1);
                    y1 += 90;
                }
                g.setFont(customFont2.deriveFont(25f));
                g.drawString("ChatCheur", 110, 650);
                g.setColor(new Color(226, 226, 226));
                g.setFont(customFont1.deriveFont(15f));
                int y2 = 205;
                for (String statu : status) {
                    g.drawString(statu, 25, y2);
                    y2 += 90;
                }
            }
        };
        contactPanel.setLayout(null);
        add(contactPanel);

        JPanel circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(circleColor);
                g.fillOval(100, 75, 12, 12);
            }
        };
        circlePanel.setBounds(0, 0, 300, 300);
        circlePanel.setOpaque(false);
        contactPanel.add(circlePanel);

        JPanel userCirclePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < status.length; i++) {
                    switch (status[i]) {
                        case "Online" -> g.setColor(Color.GREEN);
                        case "Away" -> g.setColor(Color.ORANGE);
                        case "Offline" -> g.setColor(Color.RED);
                        case "Banned" -> g.setColor(new Color(100, 98, 98));
                    }
                    g.fillOval(310, 180 + (90 * i), 12, 12);
                }
            }
        };
        userCirclePanel.setBounds(0, 0, 350, 800);
        userCirclePanel.setOpaque(false);
        contactPanel.add(userCirclePanel);

        setTitle("ChatCheur");
        setSize(1300, 800);

        //Setting Icon
        ImageIcon iconSettings = new ImageIcon("IMG/Settings.png");
        Image imgSetting = iconSettings.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconSettings = new ImageIcon(imgSetting);
        JLabel settings = new JLabel(iconSettings);
        settings.setBounds(25, 700, iconSettings.getIconWidth(), iconSettings.getIconHeight());
        contactPanel.add(settings);

        //Stats Icon
        ImageIcon iconStats = new ImageIcon("IMG/Stats.png");
        Image imgStats = iconStats.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconStats = new ImageIcon(imgStats);
        JLabel stats = new JLabel(iconStats);
        stats.setBounds(90, 700, iconStats.getIconWidth(), iconStats.getIconHeight());
        contactPanel.add(stats);

        //Log out Icon
        ImageIcon iconLogOut = new ImageIcon("IMG/logOut.png");
        Image imgLogOut = iconLogOut.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconLogOut = new ImageIcon(imgLogOut);
        JLabel logOut = new JLabel(iconLogOut);
        logOut.setBounds(280, 700, iconLogOut.getIconWidth(), iconLogOut.getIconHeight());
        logOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LogOut popup;
                popup = new LogOut(Home.this);
                popup.setVisible(true);
            }
        });
        contactPanel.add(logOut);

        JLabel label = new JLabel("Online");
        label.setFont(customFont1.deriveFont(25f));
        label.setForeground(Color.WHITE);
        int x = (275 - label.getWidth()) / 2;
        label.setBounds(x, 50, 100, 60);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Objects.equals(label.getText(), "Online")) {
                    label.setText("Away");
                    circleColor = Color.ORANGE;
                } else {
                    label.setText("Online");
                    circleColor = Color.GREEN;
                }
                circlePanel.repaint();
            }
        });
        contactPanel.add(label);

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
