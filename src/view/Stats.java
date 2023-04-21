package view;

import controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
    //private JButton buttonOk;

    private DefaultPieDataset pieDataset;
    private DefaultCategoryDataset barDataset;
    private JFreeChart chart;
    private CategoryPlot categoryPlot;
    private PiePlot piePlot;
    private ChartPanel chartPanel;
    //private JPanel panel = new JPanel(new GridLayout(2,1));

    public Stats() throws IOException, FontFormatException {
        setBounds(400, 80, 700, 700);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Avenir Next.ttc")).deriveFont(25f);
        // Création du JPanel et ajout à la JDialog
        JPanel panel = new JPanel(new GridLayout(2,1)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.setFont(customFont.deriveFont(30f));
            }
        };
        panel.setBackground(new Color(238, 213, 173));
        panel.setLayout(null);
        panel.setVisible(true);

        showPieChartStatut(panel);
        showPieChartType(panel);
        showChartMessage(panel);
        showChartConnection(panel);
        //showTopUser();

        /*buttonOk = new JButton("Ok !");
        buttonOk.setActionCommand("Ok2");
        buttonOk.setBounds(450, 480, 100, 60);
        buttonOk.setFont(customFont.deriveFont(25f));
        buttonOk.setForeground(new Color(225, 225, 225));
        buttonOk.setBackground(new Color(140, 56, 6));
        buttonOk.setBorder(BorderFactory.createLineBorder(new Color(238, 213, 173)));
        buttonOk.setFocusPainted(false);
        panel.add(buttonOk);*/

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 0, 700, 700);
        panel.setPreferredSize(new Dimension(700, 1850));
        getContentPane().add(scrollPane);

    }

    public void addAllListener(ClientController clientController) {
        //buttonOk.addActionListener(clientController);
    }

    public void showPieChartStatut(JPanel panel){
        pieDataset = new DefaultPieDataset();
        pieDataset.setValue("ADMIN", 4);
        pieDataset.setValue("USER", 9);
        pieDataset.setValue("MODERATOR", 10);
        //create chart
        chart = ChartFactory.createPieChart3D(
                "USERS BY STATUS",pieDataset,
                true,
                true,
                false);

        piePlot = (PiePlot) chart.getPlot();

        //change pieChart background color
        piePlot.setBackgroundPaint(new Color(147, 185, 175));
        //change blocks background pieChart
        piePlot.setSectionPaint("ADMIN",new Color( 220,76,76));
        piePlot.setSectionPaint("USER",new Color( 119,76,220));
        piePlot.setSectionPaint("MODERATOR",new Color( 242,184,50));

        chartPanel = new ChartPanel(chart);
        //panel1.removeAll();
        //chartPanel.getPreferredSize();
        chartPanel.validate();
        chartPanel.setBounds(50,10, 600, 360);
        panel.add(chartPanel);
        //chartPanel.setVisible(true);
    }

    public void showPieChartType(JPanel panel){
        pieDataset = new DefaultPieDataset();
        pieDataset.setValue("ONLINE", 4);
        pieDataset.setValue("OFFLINE", 9);
        pieDataset.setValue("BAN", 1);
        pieDataset.setValue("AWAY", 10);

        //create chart
        chart = ChartFactory.createPieChart3D(
                "USERS BY TYPE",pieDataset,
                true,
                true,
                false);

        piePlot = (PiePlot) chart.getPlot();

        //change pieChart background color
        piePlot.setBackgroundPaint(new Color(147, 185, 175));
        //change blocks background pieChart
        piePlot.setSectionPaint("ONLINE",new Color( 220,76,76));
        piePlot.setSectionPaint("OFFLINE",new Color( 119,76,220));
        piePlot.setSectionPaint("BAN",new Color( 242,184,50));
        piePlot.setSectionPaint("AWAY",new Color( 50,242,191));

        chartPanel= new ChartPanel(chart);
        //chartPanel.setPreferredSize(new Dimension(20, 8));
        chartPanel.getPreferredSize();
        //panel1.removeAll();
        chartPanel.setBounds(50, 380, 600, 360);
        panel.add(chartPanel);
        chartPanel.validate();
    }

    private void showChartMessage(JPanel panel) {
        barDataset = new DefaultCategoryDataset();
        barDataset .addValue(1,"","4");
        barDataset .addValue(2,"","6");

        //create chart
        chart = ChartFactory.createBarChart(
                "NUMBER OF MESSAGES SENT BY TIME",
                "Time",
                "Number of Messages Sent",
                barDataset ,
                PlotOrientation.VERTICAL,
                true, true, false);
        categoryPlot = chart.getCategoryPlot();

        //change bar color
        categoryPlot.setBackgroundPaint(new Color(238, 213, 173));
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(140,56,6));

        //put the chart into a panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(50, 750, 600, 360);
        panel.add(chartPanel);
        panel.validate();

    }

    private void showChartConnection(JPanel panel) {
        barDataset = new DefaultCategoryDataset();
        barDataset .addValue(8,"","4");
        barDataset .addValue(5,"","6");

        //create chart
        chart = ChartFactory.createBarChart(
                "NUMBER OF CONNECTIONS BY TIME",
                "Time",
                "Number of Connections",
                barDataset ,
                PlotOrientation.VERTICAL,
                true, true, false);
        categoryPlot = chart.getCategoryPlot();

        //change bar background
        categoryPlot.setBackgroundPaint(new Color(238, 213, 173));
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(106,151,99));

        //put the chart into a panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(50, 1120, 500, 360);
        panel.add(chartPanel);
        panel.validate();

    }

    private void showTopUser(JPanel panel) {
        barDataset = new DefaultCategoryDataset();
        barDataset .addValue(2,"Top 4","");
        barDataset .addValue(4,"Top 2","");
        barDataset .addValue(6,"Top 1","");
        barDataset .addValue(3,"Top 3","");
        barDataset .addValue(1,"Top 5","");

        //create chart
        chart = ChartFactory.createBarChart(
                "TOP USERS",
                "",
                "Number of Connections",
                barDataset ,
                PlotOrientation.VERTICAL,
                false, true, false);
        categoryPlot = chart.getCategoryPlot();

        //change bar background
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(1, new Color(0,0,99));
        renderer.setSeriesPaint(2, new Color(106,0,0));
        renderer.setSeriesPaint(3, new Color(0,200,0));
        renderer.setSeriesPaint(4, Color.BLACK);
        renderer.setSeriesPaint(5, Color.GRAY);
        //put the chart into a panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(50, 1490, 500, 360);
        panel.add(chartPanel);
        panel.validate();

    }

/*
        private void createPanelStats() {
            // TODO: place custom component creation code here
            // TODO 1. Modifier le titre de la fenetre
            this.setTitle("STATISTICS");

            //TODO 2. Modifier la taille (400*300)
            this.setSize(800,600);

            //TODO 3. taille modifiable par l'utilisateur
            this.setResizable(true);

            //TODO 4. Un click sur croix entraine fermeture de la fenetre
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //TODO 5. Centrer la fenetre par rapport à l'écran de l'ordinateur
            this.setLocationRelativeTo(null);

            //TODO 7. Relier le conteneur à la JFrame
            this.setContentPane(panel1);

            //TODO 8. Afficher la JFrame
            this.setVisible(true);
        }
*/
}
