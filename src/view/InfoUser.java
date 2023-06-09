package view;

import controller.ClientController;
import model.user.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InfoUser extends JDialog {
    private final JComboBox<String> comboBox;
    private final User selectedUser;
    public InfoUser(User user, User currentUser, Color c1, Color c2, Color c3, Color c4, Color c5, Color c6) throws IOException, FontFormatException {
        // Initialize the JDialog
        selectedUser = user;
        setBounds(500, 150, 600, 600);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(25f);

        // Create the JPanel and add it to the JDialog
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(c3);
                g.fillRoundRect(100, 20, 400, 90, 80, 80);
                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(40f));
                g.drawString("Informations", 175, 75);
                g.setColor(c3);
                g.fillRoundRect(35, 190, 250, 80, 80, 80);
                g.fillRoundRect(315, 190, 250, 80, 80, 80);
                g.fillRoundRect(35, 360, 250, 80, 80, 80);
                g.fillRoundRect(315, 360, 250, 80, 80, 80);
                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(30f));
                g.drawString("Fist Name", 90, 180);
                g.drawString("Last Name", 370, 180);
                g.drawString("Pseudo", 110, 350);
                g.drawString("Access", 395, 350);

                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(25f));
                FontMetrics metrics = g.getFontMetrics(customFont.deriveFont(25f));
                int x1 = (330 - metrics.stringWidth(user.getLastName())) / 2;
                int x2 = (890 - metrics.stringWidth(user.getFirstName())) / 2;
                int x3 = (330 - metrics.stringWidth(user.getUserName())) / 2;
                int x4 = (890 - metrics.stringWidth(user.getPermission().name())) / 2;
                g.drawString(user.getLastName(), x1, 240);
                g.drawString(user.getFirstName(), x2, 240);
                g.drawString(user.getUserName(), x3, 410);
                if(!currentUser.getPermission().equals(User.Permission.ADMINISTRATOR)){
                    g.drawString(user.getPermission().name(), x4, 410);
                }
            }
        };
        panel.setBackground(c2);
        panel.setLayout(null);

        // ComboBox to change the role of the user if the current user is an administrator
        String[] options = {"ADMINISTRATOR", "MODERATOR", "USER"};
        comboBox = new JComboBox<>(options);
        comboBox.setBounds(350, 372, 200, 50);
        comboBox.setFont(customFont.deriveFont(20f));
        comboBox.setEditable(true);
        comboBox.setFocusable(false);
        comboBox.setBackground(c4);
        comboBox.setSelectedItem(user.getPermission().name());
        comboBox.setActionCommand("newRole");
        panel.add(comboBox);
        if(!currentUser.getPermission().equals(User.Permission.ADMINISTRATOR)){
            comboBox.setVisible(false);
        }
        getContentPane().add(panel);
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public User getSelectedUser(){
         return selectedUser;
    }

    public void addAllListener(ClientController clientController){
        comboBox.addActionListener(clientController);
    }

}
