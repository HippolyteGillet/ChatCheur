package view;

import DAO.UserDao;
import controller.ClientController;
import model.Log;
import model.Message;
import model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class Home extends JFrame {
    private final Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(30f);
    private final Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("ALBAS.TTF"));
    private final List<JButton> ban = new ArrayList<>();
    int y = 0;
    private User currentUser;
    private Color circleColor = Color.GREEN;
    private Boolean inputReceived;
    private final JTextField textField1;
    private final JButton sendButton;
    private final JScrollPane convScrollPane;
    private final JPanel conversationPanel;
    private JButton logOut;
    private ImageIcon iconUnban, iconBan;

    public Home(List<User> userList, List<Log> logList, List<Message> messageList, String username) throws IOException, FontFormatException {
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                currentUser = user;
            }
        }
        inputReceived = false;
        conversationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (messageList.size() < 12) {
                    y = 600;
                } else {
                    y = 600 + (messageList.size() - 12) * 53;
                }
                g.setColor(Color.WHITE);
                g.fillRoundRect(100, y + 90, 750, 60, 50, 50);
                for (int i = messageList.size() - 1; i >= 0; i--) {
                    FontMetrics metrics = g.getFontMetrics(customFont1);
                    int textWidth = metrics.stringWidth(messageList.get(i).getContent());
                    int textHeight = metrics.getHeight();

                    int x = 900 - textWidth - 20;
                    //int x2 = 50;
                    int width = textWidth + 30;
                    int height = textHeight + 10;

                    if (!messageList.isEmpty()) {
                        g.setColor(new Color(183, 90, 25));
                        g.fillRoundRect(x, y, width, height, 50, 50);

                        g.setColor(Color.WHITE);
                        g.setFont(customFont1);
                        g.drawString(messageList.get(i).getContent(), x + 15, y - 5 + textHeight);

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

        convScrollPane = new JScrollPane(conversationPanel);
        convScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        convScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        convScrollPane.setBounds(350, 0, 950, 800);
        convScrollPane.getVerticalScrollBar().setValue(convScrollPane.getVerticalScrollBar().getMaximum());
        convScrollPane.setBorder(null);
        add(convScrollPane);

        //Send Icon
        ImageIcon iconSend = new ImageIcon("IMG/send.png");
        Image imgSend = iconSend.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconSend = new ImageIcon(imgSend);
        sendButton = new JButton(iconSend);
        sendButton.setBounds(800, 705, 30, 30);
        sendButton.setBorder(null);
        sendButton.setOpaque(false);
        sendButton.setContentAreaFilled(false);
        sendButton.setActionCommand("send");
        conversationPanel.add(sendButton);

        textField1 = new JTextField("Saisir du texte");
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
                sendButton.doClick();
            }
        });
        conversationPanel.add(textField1);

        JPanel contactPanelHeader = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(147, 185, 175));
                g.fillRect(0, 0, 350, 140);
                g.setColor(new Color(20, 48, 46));
                g.fillRoundRect(25, 15, 300, 90, 75, 75);
                g.setFont(customFont1.deriveFont(25f));
                g.setColor(Color.WHITE);
                int x = 25 + ((300 - g.getFontMetrics().stringWidth(currentUser.getUserName())) / 2);
                g.drawString(currentUser.getUserName(), x, 50);
            }
        };
        contactPanelHeader.setLayout(null);
        contactPanelHeader.setBounds(0, 0, 350, 140);
        add(contactPanelHeader);

        JPanel currentUserCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(circleColor);
                g.fillOval(100, 75, 12, 12);
            }
        };
        currentUserCircle.setBounds(0, 0, 300, 300);
        currentUserCircle.setOpaque(false);
        contactPanelHeader.add(currentUserCircle);

        //Status du currentUser
        JLabel label = new JLabel();
        label.setFont(customFont1.deriveFont(25f));
        label.setForeground(Color.WHITE);
        int x = (275 - label.getWidth()) / 2;
        label.setBounds(x, 50, 100, 60);
        switch (currentUser.getState()) {
            case ONLINE -> label.setText("Online");
            case AWAY -> label.setText("Away");
        }
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (currentUser.getState()) {
                    case ONLINE -> {
                        currentUser.setState(User.State.AWAY);
                        label.setText("Away");
                        circleColor = Color.ORANGE;
                    }
                    case AWAY -> {
                        currentUser.setState(User.State.ONLINE);
                        label.setText("Online");
                        circleColor = Color.GREEN;
                    }
                }
                UserDao userDao = new UserDao();
                userDao.update(currentUser);
                repaint();
            }
        });
        contactPanelHeader.add(label);

        JPanel contactPanelContent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(147, 185, 175));
                if (userList.size() > 6) {
                    g.fillRect(0, 0, 350, 460 + (userList.size() - 6) * 90);
                } else {
                    g.fillRect(0, 0, 350, 460);
                }
                int y = 0;
                int y1 = 30;
                g.setFont(customFont1.deriveFont(25f));
                for (User user : userList) {
                    if (user.getUserName() != null && !user.equals(currentUser)) {
                        if (user.getAccess().equals(User.Access.BANNED)) {
                            g.setColor(new Color(100, 98, 98));
                        } else {
                            g.setColor(new Color(20, 48, 46));
                        }
                        g.fillRoundRect(10, y, 330, 70, 20, 20);
                        y += 90;
                        g.setColor(Color.WHITE);
                        g.drawString(user.getUserName(), 20, y1);
                        y1 += 90;
                    }
                }
                g.setColor(new Color(226, 226, 226));
                g.setFont(customFont1.deriveFont(15f));
                int y2 = 55;
                for (User status : userList) {
                    if (status.getState() != null && !status.equals(currentUser)) {
                        String statu = "";
                        switch (status.getState()) {
                            case ONLINE -> statu = "Online";
                            case OFFLINE -> statu = "Offline";
                            case AWAY -> statu = "Away";
                        }
                        g.drawString(statu, 25, y2);
                        y2 += 90;
                    }
                }
            }
        };
        contactPanelContent.setLayout(null);
        contactPanelContent.setBounds(0, 140, 350, 460);

        JScrollPane contactScrollPane = new JScrollPane(contactPanelContent);
        contactScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contactScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contactScrollPane.setBorder(null);
        contactScrollPane.setBounds(0, 140, 350, 460);
        if (userList.size() > 6) {
            contactPanelContent.setPreferredSize(new Dimension(350, 460 + (90 * (userList.size() - 6))));
        } else {
            contactPanelContent.setPreferredSize(new Dimension(350, 460));
        }
        add(contactScrollPane);

        JPanel usersCirclePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int y = 30;
                for (User user : userList) {
                    if (user.getUserName() != null && !user.equals(currentUser)) {
                        if (user.getAccess().equals(User.Access.BANNED)) {
                            g.setColor(new Color(100, 98, 98));
                        } else {
                            switch (user.getState()) {
                                case ONLINE -> g.setColor(Color.GREEN);
                                case AWAY -> g.setColor(Color.ORANGE);
                                case OFFLINE -> g.setColor(Color.RED);
                            }
                        }
                        g.fillOval(310, y, 12, 12);
                        y += 90;
                    }
                }
            }
        };
        usersCirclePanel.setBounds(0, 0, 350, 800);
        usersCirclePanel.setOpaque(false);
        contactPanelContent.add(usersCirclePanel);

        //Ban Icon
        iconBan = new ImageIcon("IMG/ban-icon.png");
        Image imgBan = iconBan.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconBan = new ImageIcon(imgBan);

        //Unban Icon
        iconUnban = new ImageIcon("IMG/unban-icon.png");
        Image imgUnban = iconUnban.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconUnban = new ImageIcon(imgUnban);

        //Dessine les icones de ban et unban selon les utilisateurs et leur status (moderateur et admin)
        if (!currentUser.getPermission().equals(User.Permission.USER)) {
            int y = 20;
            List<User> nonCurrentUsers = new ArrayList<>();
            for (User user : userList) {
                if (!user.equals(currentUser)) {
                    nonCurrentUsers.add(user);
                }
            }
            for (int i = 0; i < nonCurrentUsers.size(); i++) {
                if (nonCurrentUsers.get(i).getUserName() != null) {
                    this.ban.add(new JButton(iconUnban));
                    this.ban.get(i).setActionCommand("Ban " + i);
                    this.ban.get(i).setOpaque(false);
                    this.ban.get(i).setContentAreaFilled(false);
                    this.ban.get(i).setBorderPainted(false);
                    this.ban.get(i).setIcon(iconBan);
                    switch (nonCurrentUsers.get(i).getAccess()) {
                        case ACCEPTED -> this.ban.get(i).setIcon(iconBan);
                        case BANNED -> this.ban.get(i).setIcon(iconUnban);
                    }
                    this.ban.get(i).setBounds(260, y, this.ban.get(i).getIcon().getIconWidth(), this.ban.get(i).getIcon().getIconHeight());
                    contactPanelContent.add(this.ban.get(i));
                    y += 90;
                }
            }
        }

        //Infos Icon
        ImageIcon iconInfos = new ImageIcon("IMG/info.png");
        Image imgInfos = iconInfos.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconInfos = new ImageIcon(imgInfos);
        int y = 15;
        for (User user : userList) {
            if (user.getUserName() != null && !user.equals(currentUser)) {
                JLabel infos = new JLabel(iconInfos);
                FontMetrics metrics = infos.getFontMetrics(customFont1.deriveFont(25f));
                int xU = metrics.stringWidth(user.getUserName()) + 30;
                infos.setBounds(xU, y, infos.getIcon().getIconWidth(), infos.getIcon().getIconHeight());
                infos.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InfoUser popup;
                        try {
                            popup = new InfoUser(user, currentUser);
                        } catch (IOException | FontFormatException ex) {
                            throw new RuntimeException(ex);
                        }
                        popup.setVisible(true);
                    }
                });
                contactPanelContent.add(infos);
                y += 90;
            }

            JPanel contactPanelFooter = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(new Color(147, 185, 175));
                    g.fillRect(0, 600, 350, 200);
                    g.setColor(new Color(20, 48, 46));
                    g.fillRoundRect(90, 620, 160, 50, 50, 50);
                    g.setColor(Color.WHITE);
                    g.setFont(customFont2.deriveFont(25f));
                    g.drawString("ChatCheur", 110, 650);
                }
            };
            contactPanelFooter.setLayout(null);
            contactPanelFooter.setBounds(0, 600, 350, 200);
            add(contactPanelFooter);

            setTitle("ChatCheur");
            setSize(1300, 800);

            //Setting Icon
            ImageIcon iconSettings = new ImageIcon("IMG/Settings.png");
            Image imgSetting = iconSettings.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            iconSettings = new ImageIcon(imgSetting);
            JLabel settings = new JLabel(iconSettings);
            settings.setBounds(25, 700, iconSettings.getIconWidth(), iconSettings.getIconHeight());
            contactPanelFooter.add(settings);

            //Stats Icon
            ImageIcon iconStats = new ImageIcon("IMG/Stats.png");
            Image imgStats = iconStats.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            iconStats = new ImageIcon(imgStats);
            JLabel stats = new JLabel(iconStats);
            stats.setBounds(90, 700, iconStats.getIconWidth(), iconStats.getIconHeight());
            contactPanelFooter.add(stats);

            //Log out Icon
            ImageIcon iconLogOut = new ImageIcon("IMG/logOut.png");
            Image imgLogOut = iconLogOut.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            iconLogOut = new ImageIcon(imgLogOut);
            logOut = new JButton(iconLogOut);
            logOut.setActionCommand("logOut");
            logOut.setOpaque(false);
            logOut.setContentAreaFilled(false);
            logOut.setBorderPainted(false);
            logOut.setBounds(280, 700, iconLogOut.getIconWidth(), iconLogOut.getIconHeight());
            contactPanelFooter.add(logOut);

            setResizable(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public JButton getBan(int i) {
        return ban.get(i);
    }

    public void setIconBan(int i) {
        ban.get(i).setIcon(iconBan);
    }

    public void setIconUnban(int i) {
        ban.get(i).setIcon(iconUnban);
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setInputReceived(Boolean inputReceived) {
        this.inputReceived = inputReceived;
    }

    public JScrollPane getScrollPane() {
        return convScrollPane;
    }

    public JPanel getConversationPanel() {
        return conversationPanel;
    }

    public void addAllListener(ClientController controller) {
        this.logOut.addActionListener(controller);
        for (JButton jButton : ban) {
            jButton.addActionListener(controller);
        }
        this.sendButton.addActionListener(controller);
    }

    public Boolean getInputReceived() {
        return inputReceived;
    }
}
