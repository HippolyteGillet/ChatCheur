package view;

import controller.ClientController;
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
    private User currentUser = new User();
    private List<User> nonCurrentUsers;
    private List<User> userList;
    private Color circleColor = Color.GREEN;
    private final JTextField textField;
    private final List<JButton> ban = new ArrayList<>();
    private final List<JButton> info = new ArrayList<>();
    private final JButton sendButton, imageButton, statusCurrent, logOut, smileyErrorBtn, imageErrorBtn, settings, closing, stats;
    private final JScrollPane convScrollPane;
    private final JPanel conversationPanelContent;
    private final int MAXWIDTH = 500;
    private final int MAXHEIGHT = 300;
    private int y;

    public Home(List<User> userListIn, List<Message> messageList, String username, Color c1, Color c2, Color c3, Color c4, Color c5, Color c6) throws IOException, FontFormatException {

        // Set the window properties
        setTitle("ChatCheur");
        setSize(1300, 829);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Initialization of the frame
        userList = userListIn;
        for (User user : userList) {
            if (user.getUserName().equals(username)) {
                currentUser = user;
            }
        }
        nonCurrentUsers = new ArrayList<>();
        for (User user : userList) {
            if (!user.equals(currentUser)) {
                nonCurrentUsers.add(user);
            }
        }
        imageErrorBtn = new JButton("ImageIntrouvable");
        smileyErrorBtn = new JButton("SmileyIntrouvable");
        y = calculY(messageList);

        // Format to display the time and date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Panel of the conversation
        conversationPanelContent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int yDraw = y;
                // Display of the messages
                for (int i = messageList.size() - 1; i >= 0; i--) {
                    // if the message is an image
                    if (messageList.get(i).getContent().charAt(0) == '/') {
                        ImageIcon image = new ImageIcon("imageEnvoyees/" + messageList.get(i).getContent().substring(1));
                        if (image.getImage().getWidth(null) == -1) {
                            imageErrorBtn.setActionCommand("ImageIntrouvable");
                            imageErrorBtn.doClick();
                        } else {
                            imageErrorBtn.setActionCommand("send");
                            int imageIconWidth = image.getIconWidth();
                            int imageIconHeight = image.getIconHeight();
                            double ratio = (double) imageIconWidth / (double) imageIconHeight;
                            if (imageIconWidth > MAXWIDTH) {
                                imageIconWidth = MAXWIDTH;
                                imageIconHeight = (int) (imageIconWidth / ratio);
                            }
                            if (imageIconHeight > MAXHEIGHT) {
                                imageIconHeight = MAXHEIGHT;
                                imageIconWidth = (int) (imageIconHeight * ratio);
                            }
                            Image resizedImage = image.getImage().getScaledInstance(imageIconWidth, imageIconHeight, Image.SCALE_SMOOTH);
                            image = new ImageIcon(resizedImage);
                            int x;
                            if (messageList.get(i).getUser_id() != currentUser.getId()) {
                                x = 50;
                            } else {
                                x = 900 - imageIconWidth - 20;
                            }
                            g.drawImage(image.getImage(), x + 30, yDraw - (imageIconHeight) + 35, null);
                            yDraw -= imageIconHeight - 35;
                        }
                    }
                    // if the message is a smiley
                    if (messageList.get(i).getContent().charAt(0) == '$') {
                        ImageIcon image = new ImageIcon("Smileys/" + messageList.get(i).getContent().substring(1) + ".png");
                        Image smiley = image.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        image = new ImageIcon(smiley);
                        if (image.getImage().getWidth(null) == -1) {
                            smileyErrorBtn.setActionCommand("SmileyIntrouvable");
                            smileyErrorBtn.doClick();
                        } else {
                            smileyErrorBtn.setActionCommand("send");
                            int imageIconWidth = image.getIconWidth();
                            int imageIconHeight = image.getIconHeight();
                            int x;
                            if (messageList.get(i).getUser_id() != currentUser.getId()) {
                                x = 50;
                            } else {
                                x = 900 - imageIconWidth - 20;
                            }
                            g.drawImage(image.getImage(), x + 30, yDraw - (imageIconHeight) + 20, null);
                            yDraw -= imageIconHeight + 20;
                        }
                    }
                    // Calculation of the position of the message
                    int x, xTime, yTime;

                    // Calculation of the width and height of the message
                    FontMetrics metrics = g.getFontMetrics(customFont1);
                    int textWidth = metrics.stringWidth(messageList.get(i).getContent());
                    int textHeight = metrics.getHeight();

                    // If the message is from another user
                    if (messageList.get(i).getUser_id() != currentUser.getId()) {
                        x = 75;
                        xTime = 17;
                        yTime = yDraw + 5;
                        g.setColor(c4);
                    } else {
                        // If the message is an image
                        if (messageList.get(i).getContent().charAt(0) == '/') {
                            ImageIcon image = new ImageIcon("imageEnvoyees/" + messageList.get(i).getContent().substring(1));
                            int imageIconWidth = image.getIconWidth();
                            int imageIconHeight = image.getIconHeight();
                            double ratio = (double) imageIconWidth / (double) imageIconHeight;
                            if (imageIconWidth > MAXWIDTH) {
                                imageIconWidth = MAXWIDTH;
                                imageIconHeight = (int) (imageIconWidth / ratio);
                            }
                            if (imageIconHeight > MAXHEIGHT) {
                                imageIconHeight = MAXHEIGHT;
                                imageIconWidth = (int) (imageIconHeight * ratio);
                            }
                            Image resizedImage = image.getImage().getScaledInstance(imageIconWidth, imageIconHeight, Image.SCALE_SMOOTH);
                            image = new ImageIcon(resizedImage);

                            x = 900 - image.getIconWidth();
                        } else {
                            x = 900 - textWidth - 20;
                        }
                        xTime = x + 15;
                        yTime = yDraw + textHeight - 45;
                        g.setColor(c5);
                    }
                    int width = textWidth + 30;
                    int height = textHeight + 10;

                    // Next, we draw the message

                    // if the message is a text
                    if (!messageList.isEmpty()) {
                        LocalDateTime time = messageList.get(i).getLocalDateTime();
                        String formattedTime = time.format(formatter);
                        String formattedDate = time.format(formatter2);
                        if (messageList.get(i).getContent().charAt(0) != '/' && messageList.get(i).getContent().charAt(0) != '$') {
                            // if the message is too long
                            if (messageList.get(i).getContent().length() > 40) {
                                int nbLines = 0;
                                int index = messageList.get(i).getContent().lastIndexOf(" ", 40);
                                String firstLine = messageList.get(i).getContent().substring(0, index);
                                for (int j = 0; j < messageList.get(i).getContent().length(); j += firstLine.length()) {
                                    nbLines++;
                                    yDraw -= 33;
                                }
                                textWidth = metrics.stringWidth(firstLine);
                                textHeight = metrics.getHeight() * nbLines;
                                width = textWidth + 30;
                                height = textHeight;
                                textHeight /= nbLines;
                                if (messageList.get(i).getUser_id() != currentUser.getId()) {
                                    x = 75;
                                    xTime = 17;
                                    yTime = yDraw + 5;
                                    g.setColor(c4);
                                } else {
                                    x = 900 - textWidth - 20;
                                    xTime = x + 15;
                                    yTime = yDraw + textHeight - 45;
                                    g.setColor(c5);
                                }
                                g.fillRoundRect(x, yDraw, width, height, 50, 50);
                                g.setColor(Color.WHITE);
                                g.setFont(customFont1);
                                g.drawString(firstLine, x + 15, yDraw - 5 + (textHeight));
                                int[] indexTab;
                                indexTab = new int[nbLines];
                                indexTab[0] = index;
                                for (int j = 1; j < nbLines; j++) {
                                    indexTab[j] = messageList.get(i).getContent().lastIndexOf(" ", ((j + 1) * (firstLine.length())));
                                    String line;
                                    if (j == nbLines - 1) {
                                        line = messageList.get(i).getContent().substring(indexTab[j - 1] + 1);
                                    } else {
                                        line = messageList.get(i).getContent().substring(indexTab[j - 1] + 1, indexTab[j]);
                                    }
                                    g.drawString(line, x + 15, yDraw - 5 + (textHeight) + j * 35);
                                }

                            } else {
                                g.fillRoundRect(x, yDraw, width, height, 50, 50);
                                g.setColor(Color.WHITE);
                                g.setFont(customFont1);
                                g.drawString(messageList.get(i).getContent(), x + 15, yDraw - 5 + textHeight);
                            }
                        }
                        // Now we draw the time, date and username of the message
                        for (User user : userList) {
                            // If the message was sent by another user
                            if (messageList.get(i).getUser_id() == user.getId() && user.getId() != currentUser.getId()) {
                                int letterWidth = metrics.stringWidth(user.getUserName().substring(0, 1).toUpperCase());
                                int letterHeight = metrics.getHeight();
                                if (i != 0) {
                                    LocalDateTime previousTime = messageList.get(i - 1).getLocalDateTime();
                                    String formattedPreviousTime = previousTime.format(formatter);

                                    // If the previous message was sent more than a minutes ago or if the previous message was sent by another user
                                    if (!formattedPreviousTime.equals(formattedTime) || messageList.get(i - 1).getUser_id() != messageList.get(i).getUser_id()) {
                                        // We draw a circle with the first letter of the username
                                        g.setColor(Color.BLACK);
                                        g.fillOval(10, yDraw + 10, 50, 50);
                                        g.setFont(customFont1.deriveFont(25f));
                                        g.setColor(Color.WHITE);
                                        g.drawString(user.getUserName().substring(0, 1).toUpperCase(), 36 - letterWidth / 2, yDraw + 64 - letterHeight / 2);

                                        // Then we draw the username
                                        g.setFont(customFont1.deriveFont(20f));
                                        g.setColor(c6);
                                        g.drawString(user.getUserName(), x + 15, yDraw + textHeight - 46);
                                    }
                                }
                                // If it's the first message
                                else {
                                    // We draw a circle with the first letter of the username
                                    g.setColor(Color.BLACK);
                                    g.fillOval(10, yDraw + 10, 50, 50);
                                    g.setFont(customFont1.deriveFont(25f));
                                    g.setColor(Color.WHITE);
                                    g.drawString(user.getUserName().substring(0, 1).toUpperCase(), 36 - letterWidth / 2, yDraw + 64 - letterHeight / 2);

                                    // Then we draw the username
                                    g.setFont(customFont1.deriveFont(20f));
                                    g.setColor(c6);
                                    g.drawString(user.getUserName(), x + 15, yDraw + textHeight - 46);
                                }
                            }
                        }
                        if (i != 0) {
                            LocalDateTime previousTime = messageList.get(i - 1).getLocalDateTime();
                            String formattedPreviousTime = previousTime.format(formatter);
                            // If the previous message was sent less than a minute ago and if it was sent by the same user
                            if (formattedTime.equals(formattedPreviousTime) && messageList.get(i - 1).getUser_id() == messageList.get(i).getUser_id()) {
                                yDraw -= 53;
                            } else {
                                // We draw the time
                                g.setFont(customFont1.deriveFont(12f));
                                g.setColor(c6);
                                g.drawString(formattedTime, xTime, yTime);
                                yDraw -= 90;

                                // if the previous message was sent at a different date
                                if (!formattedDate.equals(previousTime.format(formatter2))) {
                                    // We draw the date
                                    g.setFont(customFont1.deriveFont(18f));
                                    g.drawString(formattedDate, 425, yDraw + 30);
                                    yDraw -= 70;
                                }
                            }
                        }
                        // if it's the first message
                        else {
                            // We draw the time and the date
                            g.setFont(customFont1.deriveFont(12f));
                            g.setColor(c6);
                            g.drawString(formattedTime, xTime, yTime);
                            g.setFont(customFont1.deriveFont(18f));
                            g.drawString(formattedDate, 425, yDraw - 30);
                            yDraw -= 90;
                        }
                    }
                }
            }
        };
        conversationPanelContent.setBackground(c1);
        conversationPanelContent.setBounds(350, 0, 950, 720);
        conversationPanelContent.setLayout(null);

        // Footer of the conversation panel
        JPanel conversationPanelFooter = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(c1);
                g.fillRect(0, 0, 950, 200);
                g.setColor(Color.WHITE);
                g.fillRoundRect(100, 10, 750, 60, 50, 50);
            }
        };
        conversationPanelFooter.setLayout(null);
        conversationPanelFooter.setBounds(350, 720, 950, 130);
        add(conversationPanelFooter);

        // Button to add an image
        ImageIcon iconAdd = new ImageIcon("IMG/image.png");
        Image imgAdd = iconAdd.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconAdd = new ImageIcon(imgAdd);
        imageButton = new JButton(iconAdd);
        imageButton.setBounds(110, 25, 30, 30);
        imageButton.setBorder(null);
        imageButton.setOpaque(false);
        imageButton.setContentAreaFilled(false);
        imageButton.setActionCommand("addImage");
        conversationPanelFooter.add(imageButton);

        // Button to send a message
        ImageIcon iconSend = new ImageIcon("IMG/send.png");
        Image imgSend = iconSend.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconSend = new ImageIcon(imgSend);
        sendButton = new JButton(iconSend);
        sendButton.setBounds(800, 25, 30, 30);
        sendButton.setBorder(null);
        sendButton.setOpaque(false);
        sendButton.setContentAreaFilled(false);
        sendButton.setActionCommand("send");
        conversationPanelFooter.add(sendButton);

        // ScrollPane of the conversation panel
        convScrollPane = new JScrollPane(conversationPanelContent);
        convScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        convScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        convScrollPane.setBounds(350, 0, 950, 720);
        convScrollPane.getVerticalScrollBar().setValue(convScrollPane.getVerticalScrollBar().getMaximum());
        conversationPanelContent.setPreferredSize(new Dimension(950, y + 60));
        convScrollPane.getViewport().setViewPosition(new Point(0, y));
        convScrollPane.setBorder(null);
        add(convScrollPane);

        // TextField to write a message
        textField = new JTextField("Write a message");
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBounds(140, 10, 660, 60);
        textField.setFont(customFont1);
        textField.setForeground(Color.GRAY);
        textField.setCaretColor(Color.GRAY);
        textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        textField.setOpaque(false);
        // When the user clicks on the text field, the text disappears
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("Write a message")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText("Write a message");
                }
            }
        });
        textField.addActionListener(e -> {
            // If the user presses enter, the message is sent
            if (!textField.getText().isEmpty() && !textField.getText().contentEquals("$")) {
                sendButton.doClick();
            }
        });
        conversationPanelFooter.add(textField);

        // Header of the contact panel
        JPanel contactPanelHeader = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(c2);
                g.fillRect(0, 0, 350, 140);
                g.setColor(c3);
                g.fillRoundRect(25, 15, 300, 90, 75, 75);
                g.setFont(customFont1.deriveFont(25f));
                g.setColor(Color.WHITE);
                // We center the username of the current user and draw it
                int x = 25 + ((300 - g.getFontMetrics().stringWidth(currentUser.getUserName())) / 2);
                g.drawString(currentUser.getUserName(), x, 50);
            }
        };
        contactPanelHeader.setLayout(null);
        contactPanelHeader.setBounds(0, 0, 350, 140);
        add(contactPanelHeader);

        // Circle to show the status of the current user
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

        // Button to change the status of the current user
        statusCurrent = new JButton("Online");
        statusCurrent.setFont(customFont1.deriveFont(25f));
        statusCurrent.setContentAreaFilled(false);
        statusCurrent.setBorder(null);
        statusCurrent.setForeground(Color.WHITE);
        statusCurrent.setOpaque(false);
        statusCurrent.setFocusable(false);
        int x = (260 - statusCurrent.getText().length()) / 2;
        statusCurrent.setBounds(x, 55, 100, 50);
        switch (currentUser.getState()) {
            case ONLINE -> statusCurrent.setText("Online");
            case AWAY -> statusCurrent.setText("Away");
        }
        statusCurrent.setActionCommand("changeUserStatus");
        contactPanelHeader.add(statusCurrent);

        // Initialisation of the ban/unban icons

        ImageIcon iconBan = new ImageIcon("IMG/ban-icon.png");
        Image imgBan = iconBan.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconBan = new ImageIcon(imgBan);
        ImageIcon iconUnban = new ImageIcon("IMG/unban-icon.png");
        Image imgUnban = iconUnban.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconUnban = new ImageIcon(imgUnban);
        for (User nonCurrentUser : nonCurrentUsers) {
            if (nonCurrentUser.getUserName() != null) {
                ban.add(new JButton(iconUnban));
            }
        }

        // Content of the contact panel
        ImageIcon finalIconBan = iconBan;
        ImageIcon finalIconUnban = iconUnban;
        JPanel contactPanelContent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(c2);
                // We draw a rectangle with a height depending on the number of users
                if (userList.size() > 6) {
                    g.fillRect(0, 0, 350, 490 + (userList.size() - 6) * 90);
                } else {
                    g.fillRect(0, 0, 350, 490);
                }
                int y = 0;
                int y1 = 30;
                g.setFont(customFont1.deriveFont(25f));

                // We draw a rectangle with the username for each user with a ban or unban icon depending on the user's access
                for (int i = 0; i < nonCurrentUsers.size(); i++) {
                    if (nonCurrentUsers.get(i).getUserName() != null) {
                        if (nonCurrentUsers.get(i).getAccess().equals(User.Access.BANNED)) {
                            g.setColor(Color.GRAY);
                            ban.get(i).setIcon(finalIconBan);
                            ban.get(i).setVisible(!currentUser.getPermission().equals(User.Permission.USER));
                        } else {
                            g.setColor(c3);
                            ban.get(i).setIcon(finalIconUnban);
                            ban.get(i).setVisible(!currentUser.getPermission().equals(User.Permission.USER));
                        }
                        g.fillRoundRect(10, y, 330, 70, 20, 20);
                        y += 90;
                        g.setColor(Color.WHITE);
                        g.drawString(nonCurrentUsers.get(i).getUserName(), 20, y1);
                        y1 += 90;
                    }
                }
                g.setColor(new Color(226, 226, 226));
                g.setFont(customFont1.deriveFont(15f));
                int y2 = 55;

                // We draw the status for each user
                for (User status : userList) {
                    if (status.getState() != null && status.getId() != currentUser.getId()) {
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
        contactPanelContent.setBounds(0, 170, 350, 490);

        // We add a scroll pane to the contact panel depending on the number of users
        JScrollPane contactScrollPane = new JScrollPane(contactPanelContent);
        contactScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contactScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contactScrollPane.setBorder(null);
        contactScrollPane.setBounds(0, 140, 350, 490);
        if (userList.size() > 6) {
            contactPanelContent.setPreferredSize(new Dimension(350, 490 + (80 * (userList.size() - 6))));
        } else {
            contactPanelContent.setPreferredSize(new Dimension(350, 490));
        }
        add(contactScrollPane);

        // We draw a circle for each user with a color depending on the user's status
        JPanel usersCirclePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int y = 30;
                for (User user : userList) {
                    if (user.getUserName() != null && user.getId() != currentUser.getId()) {
                        if (user.getAccess().equals(User.Access.BANNED)) {
                            g.setColor(Color.GRAY);
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

        // We draw a ban/unban icon for each user depending on the user's access
        int yBtn = 20;
        for (int i = 0; i < nonCurrentUsers.size(); i++) {
            if (nonCurrentUsers.get(i).getUserName() != null) {
                int idToBan = nonCurrentUsers.get(i).getId();
                ban.get(i).setActionCommand("Ban " + idToBan);
                ban.get(i).setOpaque(false);
                ban.get(i).setContentAreaFilled(false);
                ban.get(i).setBorderPainted(false);
                ban.get(i).setFocusable(false);
                switch (nonCurrentUsers.get(i).getAccess()) {
                    case ACCEPTED -> ban.get(i).setIcon(iconBan);
                    case BANNED -> ban.get(i).setIcon(iconUnban);
                }
                ban.get(i).setBounds(260, yBtn, ban.get(i).getIcon().getIconWidth(), ban.get(i).getIcon().getIconHeight());
                contactPanelContent.add(ban.get(i));
                yBtn += 90;
            }
        }

        // We draw an info button for each user to display the user's information
        ImageIcon iconInfos = new ImageIcon("IMG/info.png");
        Image imgInfos = iconInfos.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconInfos = new ImageIcon(imgInfos);
        int y = 15;
        for (int i = 0; i < nonCurrentUsers.size(); i++) {
            if (nonCurrentUsers.get(i).getUserName() != null) {
                info.add(new JButton(iconInfos));
                FontMetrics metrics = info.get(i).getFontMetrics(customFont1.deriveFont(25f));
                int xU = metrics.stringWidth(nonCurrentUsers.get(i).getUserName()) + 30;
                info.get(i).setBounds(xU, y, info.get(i).getIcon().getIconWidth() + 5, info.get(i).getIcon().getIconHeight() + 5);
                info.get(i).setOpaque(false);
                info.get(i).setContentAreaFilled(false);
                info.get(i).setBorderPainted(false);
                info.get(i).setFocusable(false);
                info.get(i).setActionCommand("Info " + nonCurrentUsers.get(i).getId());
                contactPanelContent.add(info.get(i));
                y += 90;
            }
        }

        // We draw a button to display the stats
        ImageIcon iconStats = new ImageIcon("IMG/Stats.png");
        Image imgStats = iconStats.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconStats = new ImageIcon(imgStats);
        stats = new JButton(iconStats);
        stats.setActionCommand("Stats");
        stats.setOpaque(false);
        stats.setContentAreaFilled(false);
        stats.setBorderPainted(false);
        stats.setFocusPainted(false);
        stats.setBounds(90, 730, iconStats.getIconWidth(), iconStats.getIconHeight());

        // Footer of the contact panel
        JPanel contactPanelFooter = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(c2);
                g.fillRect(0, 630, 350, 200);
                g.setColor(c3);
                g.fillRoundRect(90, 650, 160, 50, 50, 50);
                g.setColor(Color.WHITE);
                g.setFont(customFont2.deriveFont(25f));
                g.drawString("ChatCheur", 110, 680);
                // We display the stats button only if the user is an administrator
                stats.setVisible(currentUser.getPermission().equals(User.Permission.ADMINISTRATOR));
            }
        };
        contactPanelFooter.setLayout(null);
        contactPanelFooter.setBounds(0, 630, 350, 200);
        add(contactPanelFooter);
        contactPanelFooter.add(stats);

        // We draw a button to display the settings
        ImageIcon iconSettings = new ImageIcon("IMG/Settings.png");
        Image imgSetting = iconSettings.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconSettings = new ImageIcon(imgSetting);
        settings = new JButton(iconSettings);
        settings.setActionCommand("Settings");
        settings.setOpaque(false);
        settings.setContentAreaFilled(false);
        settings.setBorderPainted(false);
        settings.setFocusPainted(false);
        settings.setBounds(25, 730, iconSettings.getIconWidth(), iconSettings.getIconHeight());
        contactPanelFooter.add(settings);

        // We draw a button to log out
        ImageIcon iconLogOut = new ImageIcon("IMG/logOut.png");
        Image imgLogOut = iconLogOut.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconLogOut = new ImageIcon(imgLogOut);
        logOut = new JButton(iconLogOut);
        logOut.setActionCommand("logOut");
        logOut.setOpaque(false);
        logOut.setContentAreaFilled(false);
        logOut.setBorderPainted(false);
        logOut.setFocusPainted(false);
        logOut.setBounds(280, 730, iconLogOut.getIconWidth(), iconLogOut.getIconHeight());
        contactPanelFooter.add(logOut);

        // This button is used to set the user status to Away in case of closing the window without logging out
        closing = new JButton();
        closing.setActionCommand("closing");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closing.doClick();
            }
        });
    }

    public JTextField getTextField() {
        return textField;
    }

    public JScrollPane getScrollPane() {
        return convScrollPane;
    }

    public JPanel getconversationPanelContent() {
        return conversationPanelContent;
    }

    public JButton getStatusCurrent() {
        return statusCurrent;
    }

    public void setColorStatusCurrent(Color color) {
        circleColor = color;
    }

    public void addAllListener(ClientController controller) {
        this.logOut.addActionListener(controller);
        for (JButton jButton : ban) {
            jButton.addActionListener(controller);
        }
        for (JButton jButton : info) {
            jButton.addActionListener(controller);
        }
        this.sendButton.addActionListener(controller);
        this.imageButton.addActionListener(controller);
        this.smileyErrorBtn.addActionListener(controller);
        this.imageErrorBtn.addActionListener(controller);
        if (currentUser.getPermission() == User.Permission.ADMINISTRATOR) this.stats.addActionListener(controller);
        this.settings.addActionListener(controller);
        statusCurrent.addActionListener(controller);
        this.closing.addActionListener(controller);
    }

    public int calculY(List<Message> messageList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Format d'affichage pour l'heure
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Format d'affichage pour la date
        int y = 0;
        for (int i = 0; i < messageList.size(); i++) {
            if (i < messageList.size() - 1) {
                LocalDateTime previousTime = messageList.get(i + 1).getLocalDateTime();
                String formattedPreviousTime = previousTime.format(formatter);
                LocalDateTime time = messageList.get(i).getLocalDateTime();
                String formattedTime = time.format(formatter);
                String formattedDate = time.format(formatter2);

                if (messageList.get(i).getContent().charAt(0) == '$') {
                    y += 60;
                }

                if (formattedTime.equals(formattedPreviousTime) && messageList.get(i + 1).getUser_id() == messageList.get(i).getUser_id()) {
                    y += 53;
                } else {
                    y += 90;
                }
                if (!formattedDate.equals(previousTime.format(formatter2))) {
                    y += 70;
                }
            } else {
                y += 90;
            }
            if (messageList.get(i).getContent().charAt(0) == '/') {
                ImageIcon imageSent = new ImageIcon("imageEnvoyees/" + messageList.get(i).getContent().substring(1));
                int imageIconWidth = imageSent.getIconWidth();
                int imageIconHeight = imageSent.getIconHeight();
                double ratio = (double) imageIconWidth / (double) imageIconHeight;
                if (imageIconWidth > MAXWIDTH) {
                    imageIconWidth = MAXWIDTH;
                    imageIconHeight = (int) (imageIconWidth / ratio);
                }
                if (imageIconHeight > MAXHEIGHT) {
                    imageIconHeight = MAXHEIGHT;
                }
                y += imageIconHeight + 35;
            }
            if (messageList.get(i).getContent().length() > 40) {
                int index = messageList.get(i).getContent().lastIndexOf(" ", 40);
                String firstLine = messageList.get(i).getContent().substring(0, index);
                for (int j = 0; j < messageList.get(i).getContent().length(); j += firstLine.length()) {
                    y += 33;
                }
            }
        }
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void updateNonCurrentWithFullArray(List<User> userList) {
        this.userList = userList;
        nonCurrentUsers = new ArrayList<>();
        for (User user : userList) {
            if (user.getId() != currentUser.getId()) {
                nonCurrentUsers.add(user);
            } else {
                currentUser = user;
            }
        }
    }
}
