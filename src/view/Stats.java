package view;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import model.user.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Stats extends JDialog{

    public Stats(ArrayList<User> typeUser, ArrayList<User> typeModerator, ArrayList<User> typeAdministrator,
                 ArrayList<User> usersOnline, ArrayList<User> usersAway, ArrayList<User> usersOffline,
                 ArrayList<User> banned, ArrayList<Integer> messagesPerHour, ArrayList<Integer> connectionsPerHour,
                 ArrayList<User> topUsers) throws IOException, FontFormatException {
        setTitle("Statitics");
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
        //panel.setBackground(new Color(238, 213, 173));
        panel.setBackground(new Color(147, 185, 175));
        panel.setLayout(null);
        panel.setVisible(true);

        //----------------------Graph 1--------------------------

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("ADMIN", typeAdministrator.size());
        pieDataset.setValue("USER", typeUser.size());
        pieDataset.setValue("MODERATOR", typeModerator.size());
        //create chart
        JFreeChart chart = ChartFactory.createPieChart3D(
                "USERS BY STATUS",pieDataset,
                true,
                true,
                false);

        PiePlot piePlot = (PiePlot) chart.getPlot();

        //change pieChart background color
        piePlot.setBackgroundPaint(new Color(234, 213, 178));
        //change blocks background pieChart
        piePlot.setSectionPaint("USER",new Color(Color.WHITE.getRGB()));
        piePlot.setSectionPaint("MODERATOR",new Color(Color.GRAY.getRGB()));
        piePlot.setSectionPaint("ADMIN",new Color( 27,47,46));


        ChartPanel chartPanel = new ChartPanel(chart);
        //panel1.removeAll();
        //chartPanel.getPreferredSize();
        chartPanel.validate();
        chartPanel.setBounds(50,10, 600, 360);
        panel.add(chartPanel);
        //chartPanel.setVisible(true);

        //----------------------Graph 2--------------------------

        pieDataset = new DefaultPieDataset();
        pieDataset.setValue("ONLINE", usersOnline.size());
        pieDataset.setValue("AWAY", usersAway.size());
        pieDataset.setValue("OFFLINE", usersOffline.size());
        pieDataset.setValue("BAN", banned.size());

        //create chart
        chart = ChartFactory.createPieChart3D(
                "USERS BY TYPE",pieDataset,
                true,
                true,
                false);

        piePlot = (PiePlot) chart.getPlot();

        //change pieChart background color
        piePlot.setBackgroundPaint(new Color(234, 213, 178));
        //change blocks background pieChart
        piePlot.setSectionPaint("ONLINE",new Color(Color.WHITE.getRGB()));
        piePlot.setSectionPaint("AWAY",new Color(130, 61, 24));
        piePlot.setSectionPaint("OFFLINE",new Color( 27,47,46));
        piePlot.setSectionPaint("BAN",new Color(Color.GRAY.getRGB()));

        chartPanel= new ChartPanel(chart);
        //chartPanel.setPreferredSize(new Dimension(20, 8));
        chartPanel.getPreferredSize();
        //panel1.removeAll();
        chartPanel.setBounds(50, 400, 600, 360);
        panel.add(chartPanel);
        chartPanel.validate();

        //----------------------Graph 3--------------------------

        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        for (int i = 0; i < 24; i++) {
            barDataset .addValue(messagesPerHour.get(i), "",(i+1) + "");
        }

        //create chart
        chart = ChartFactory.createBarChart(
                "NUMBER OF MESSAGES SENT BY TIME",
                "Time (by hour)",
                "Number of Messages Sent",
                barDataset ,
                PlotOrientation.VERTICAL,
                true, true, false);
        CategoryPlot categoryPlot = chart.getCategoryPlot();

        //change bar color
        categoryPlot.setBackgroundPaint(new Color(238, 213, 173));
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color( 27,47,46));

        //put the chart into a panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(50, 790, 600, 360);
        panel.add(chartPanel);
        panel.validate();

        //----------------------Graph 4--------------------------

        barDataset = new DefaultCategoryDataset();

        for (int i = 0; i < 24; i++) {
            barDataset .addValue(connectionsPerHour.get(i), "",(i+1) + "");
        }

        //create chart
        chart = ChartFactory.createBarChart(
                "NUMBER OF CONNECTIONS BY TIME",
                "Time (by hour)",
                "Number of Connections",
                barDataset ,
                PlotOrientation.VERTICAL,
                true, true, false);
        categoryPlot = chart.getCategoryPlot();

        //change bar background
        categoryPlot.setBackgroundPaint(new Color(238, 213, 173));
        renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color( 27,47,46));

        //put the chart into a panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(50, 1180, 600, 360);
        panel.add(chartPanel);
        panel.validate();

        //--------------------Top Users------------------------

        JLabel topTitle = new JLabel("Top Users");
        topTitle.setFont(customFont.deriveFont(Font.BOLD, 32f));
        topTitle.setForeground(Color.BLACK);
        topTitle.setBounds(200, 1580, 300, 60);
        topTitle.setHorizontalAlignment(JLabel.CENTER);
        panel.add(topTitle);

        ImageIcon iconTop = new ImageIcon("IMG/Podium.png");
        Image imgTop = iconTop.getImage().getScaledInstance(530, 260, Image.SCALE_SMOOTH);
        iconTop = new ImageIcon(imgTop);
        JLabel podium = new JLabel(iconTop);
        podium.setBounds(80, 1760, iconTop.getIconWidth(), iconTop.getIconHeight());
        panel.add(podium);

        String s1 = "";
        String s2 = "";
        String s3 = "";

        for (int i = 0; i < Math.min(topUsers.size(), 3); i++) {
            if (i == 0) {
                s1 = topUsers.get(i).getUserName();
            } else if (i == 1) {
                s2 = topUsers.get(i).getUserName();
            } else if (i == 2) {
                s3 = topUsers.get(i).getUserName();
            }
        }

        JLabel top1 = new JLabel(s1);
        top1.setFont(customFont.deriveFont(25f));
        top1.setForeground(Color.WHITE);
        top1.setBounds(200, 1690, 300, 60);
        top1.setHorizontalAlignment(JLabel.CENTER);
        panel.add(top1);

        JLabel top2 = new JLabel(s2);
        top2.setFont(customFont.deriveFont(25f));
        top2.setForeground(Color.WHITE);
        top2.setBounds(70, 1785, 200, 60);
        top2.setHorizontalAlignment(JLabel.CENTER);
        panel.add(top2);

        JLabel top3 = new JLabel(s3);
        top3.setFont(customFont.deriveFont(25f));
        top3.setForeground(Color.WHITE);
        top3.setBounds(podium.getWidth() - 110, 1825, 200, 60);
        top3.setHorizontalAlignment(JLabel.CENTER);
        panel.add(top3);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 0, 700, 700);
        panel.setPreferredSize(new Dimension(700, 2160));
        getContentPane().add(scrollPane);

    }

    public void addAllListener(ClientController clientController) {
        //buttonOk.addActionListener(clientController);
    }
}
