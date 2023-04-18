package view;

import DAO.UserDao;
import com.mysql.cj.xdevapi.Client;
import controller.ClientController;
import model.Log;
import model.Message;
import model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home extends JFrame {
    private Color circleColor = Color.GREEN;
    User currentUser;
    private Boolean inputReceived;
    private final Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(30f);
    private final Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("ALBAS.TTF"));
    int y = 0;
    private JButton logOut;
    private LogOut popup;

    public Home(List<User> userList, List<Log> logList, List<Message> messageList, String username) throws IOException, FontFormatException {
        UserDao userDao = new UserDao();
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                currentUser = user;
            }
        }
        List<String> messages = new ArrayList<>();
        for (Message message : messageList) {
            messages.add(message.getContent());
        }
        inputReceived = false;
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
                int y1 = 180;
                g.setFont(customFont1.deriveFont(25f));
                for (User user : userList) {
                    if (user.getUserName() != null) {
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
                g.setColor(Color.WHITE);
                int x = 25 + ((300 - g.getFontMetrics().stringWidth(currentUser.getUserName())) / 2);
                g.drawString(currentUser.getUserName(), x, 50);
                g.setFont(customFont2.deriveFont(25f));
                g.drawString("ChatCheur", 110, 650);
                g.setColor(new Color(226, 226, 226));
                g.setFont(customFont1.deriveFont(15f));
                int y2 = 205;
                for (User status : userList) {
                    if (status.getState() != null) {
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
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).getUserName() != null) {
                        if (userList.get(i).getAccess().equals(User.Access.BANNED)) {
                            g.setColor(new Color(100, 98, 98));
                        } else {
                            switch (userList.get(i).getState()) {
                                case ONLINE -> g.setColor(Color.GREEN);
                                case AWAY -> g.setColor(Color.ORANGE);
                                case OFFLINE -> g.setColor(Color.RED);
                            }
                        }
                        g.fillOval(310, 180 + (90 * i), 12, 12);
                    }
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
        logOut = new JButton(iconLogOut);
        logOut.setActionCommand("logOut");
        logOut.setOpaque(false);
        logOut.setContentAreaFilled(false);
        logOut.setBorderPainted(false);
        logOut.setBounds(280, 700, iconLogOut.getIconWidth(), iconLogOut.getIconHeight());
        contactPanel.add(logOut);

        //Ban Icon
        ImageIcon iconBan = new ImageIcon("IMG/ban-icon.png");
        Image imgBan = iconBan.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconBan = new ImageIcon(imgBan);

        //Unban Icon
        ImageIcon iconUnban = new ImageIcon("IMG/unban-icon.png");
        Image imgUnban = iconUnban.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconUnban = new ImageIcon(imgUnban);

        //Dessine les icones de ban et unban selon les utilisateurs et leur status (moderateur et admin)
        if (!currentUser.getPermission().equals(User.Permission.USER)) {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserName() != null) {
                    JLabel ban = new JLabel(iconUnban);
                    ban.setIcon(iconBan);
                    switch (userList.get(i).getAccess()) {
                        case ACCEPTED -> ban.setIcon(iconBan);
                        case BANNED -> ban.setIcon(iconUnban);
                    }
                    int finalI = i;
                    ImageIcon finalIconBan = iconBan;
                    ImageIcon finalIconUnban = iconUnban;
                    ban.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            //Si l'utilisateur est banni, on le débanni, sinon on le banni
                            if (userList.get(finalI).getAccess().equals(User.Access.BANNED)) {
                                int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir débannir cet utilisateur ?", "Confirmer le débannissement", JOptionPane.YES_NO_OPTION);
                                if (response == JOptionPane.YES_OPTION) {
                                    ban.setIcon(finalIconBan);
                                    userList.get(finalI).setAccess(User.Access.ACCEPTED);
                                    userDao.update(userList.get(finalI));
                                }
                            } else {
                                int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir bannir cet utilisateur ?", "Confirmer le bannissement", JOptionPane.YES_NO_OPTION);
                                if (response == JOptionPane.YES_OPTION) {
                                    ban.setIcon(finalIconUnban);
                                    userList.get(finalI).setAccess(User.Access.BANNED);
                                    userDao.update(userList.get(finalI));
                                }
                            }
                            repaint();
                        }
                    });
                    ban.setBounds(260, 170 + (90 * i), ban.getIcon().getIconWidth(), ban.getIcon().getIconHeight());
                    contactPanel.add(ban);
                }
            }
        }

        //Infos Icon
        ImageIcon iconInfos = new ImageIcon("IMG/info.png");
        Image imgInfos = iconInfos.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconInfos = new ImageIcon(imgInfos);
        for(int i = 0; i<userList.size(); i++){
            if(userList.get(i).getUserName() != null){
                JLabel infos = new JLabel(iconInfos);
                FontMetrics metrics = infos.getFontMetrics(customFont1.deriveFont(25f));
                int x = metrics.stringWidth(userList.get(i).getUserName()) + 30;
                infos.setBounds(x, 165 + (90 * i), infos.getIcon().getIconWidth(), infos.getIcon().getIconHeight());
                int finalI = i;
                infos.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InfoUser popup;
                        try {
                            popup = new InfoUser(userList.get(finalI), currentUser);
                        } catch (IOException | FontFormatException ex) {
                            throw new RuntimeException(ex);
                        }
                        popup.setVisible(true);
                    }
                });
                contactPanel.add(infos);
            }
        }

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

    public JButton getLogOut() {
        return logOut;
    }

    public void addAllListener(ClientController controller) {
        this.logOut.addActionListener(controller);
    }
}
