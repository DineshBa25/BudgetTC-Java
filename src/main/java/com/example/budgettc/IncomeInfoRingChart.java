package com.example.budgettc;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CenterTextMode;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;

public class IncomeInfoRingChart extends JPanel {

    public static double budgetedIncome = 0;
    public static double actualIncome = 0;

    public IncomeInfoRingChart(Double budgetedIncome1,  Double actualIncome1) {
        budgetedIncome = budgetedIncome1;
        actualIncome = actualIncome1;
        JPanel panel = createDemoPanel();
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        removeAll();
        setLayout(new GridLayout(1,1));
        add(panel);
    }

    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Attained", actualIncome);
        dataset.setValue("Left", budgetedIncome-actualIncome);
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset  the dataset.
     *
     * @return a chart.
     */
    private static ChartPanel createChart(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createRingChart(
                "Income",  // chart title
                dataset,             // data
                false,               // include legend
                false,
                false
        );

        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setSectionPaint("Attained", new Color(40, 115, 0));
        plot.setSectionPaint("Left", new Color(121, 0, 0));
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setSectionDepth(0.35);
        plot.setCircular(true);
        plot.setBackgroundPaint(new Color(0, 0, 0, 0));
        plot.setLabelGap(0.02);
        plot.setLabelBackgroundPaint(new Color(0, 0, 0, 0));
        plot.getChart().setBackgroundPaint(new Color(0, 0, 0, 0));
        plot.getChart().getTitle().setPaint(new Color(0xA9A9A9));
        plot.getChart().getTitle().setFont(new Font("Corbert", Font.BOLD, 15));
        plot.setOutlineVisible(false);
        plot.setCenterTextMode(CenterTextMode.FIXED);
        plot.setCenterTextFont(new Font("Corbert", Font.BOLD, 12));
        plot.setCenterTextColor(new Color(0xAFAFAF));
        plot.setCenterText("$" + budgettcgui.formatter.format(Double.parseDouble(budgettcgui.decimalFormatter.format(budgetedIncome-actualIncome))) + " left");
        JFreeChart l = plot.getChart();
        ChartPanel u = new ChartPanel(l);
        plot.setLabelGenerator(null);
        u.setMinimumDrawWidth(0);
        u.setMaximumDrawWidth(Integer.MAX_VALUE);
        u.setMinimumDrawHeight(0);
        u.setMaximumDrawHeight(500);

        return u;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public JPanel createDemoPanel() {
        ChartPanel chart = createChart(createDataset());
        return chart;
    }

    /*
    public static void main(String[] args) {
        RingChartDemo1 demo = new RingChartDemo1(
                "JFreeChart: RingChartDemo1.java");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }*/
}
