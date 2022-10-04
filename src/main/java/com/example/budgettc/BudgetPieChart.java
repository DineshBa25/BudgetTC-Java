package com.example.budgettc;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BudgetPieChart extends budgettcgui {
   // public static JPanel budgetChartJPanel;
    public static ChartPanel budgetChartPanel;
    public static DefaultPieDataset budgetPieDataset;

    public BudgetPieChart(){
            createChart(createDataset());
    }

    private static DefaultPieDataset createDataset() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int x = 0; x < storageMatrix.size(); x++) {
            if (storageMatrix.get(x).getCategoryName() != null && storageMatrix.get(x).getAmountAllocated() != null) {
                //System.out.println(x[0].toString()+"         --          "+  Double.valueOf(x[1].toString()));
                dataset.setValue(storageMatrix.get(x).getCategoryName(), storageMatrix.get(x).getAmountAllocated());
                //out.println("Adding category: " + x[0].toString() +" to chart with value of: "+x[1].toString());
            }
        }
        return dataset;
    }

    static void createChart(DefaultPieDataset dataset) {
            JFreeChart chart = ChartFactory.createPieChart3D(
                    "",  // chart title
                    dataset,         // data
                    true,            // include legend
                    true,
                    false);

            final PiePlot3D plot = (PiePlot3D) chart.getPlot();
            Random rand = new Random();
            int upperbound = 200;
            for (Object x : createDataset().getKeys()) {
                plot.setSectionPaint((Comparable) x, new Color(upperbound, upperbound, 255));
                if (upperbound > 60)
                    upperbound -= 50;
                else
                    upperbound = 200;
            }

            plot.setSectionOutlinesVisible(true);
            plot.setDefaultSectionOutlinePaint(new Color(0, 0, 0));
            plot.setStartAngle(270);
            plot.setForegroundAlpha(1.00f);
            plot.setInteriorGap(0.02);
            plot.setCircular(true);
            plot.setOutlineVisible(false);
            plot.setBackgroundAlpha(0);
            plot.getChart().setBackgroundPaint(new java.awt.Color(0, 0, 0, 0));
            plot.setLabelBackgroundPaint(Color.DARK_GRAY);
            plot.setLabelPaint(Color.WHITE);
            plot.getChart().getTitle().setPaint(java.awt.Color.WHITE);
            plot.getChart().getTitle().setFont(new java.awt.Font("SansSerif", Font.PLAIN, 30));
            JFreeChart l = plot.getChart();
            budgetChartPanel= new ChartPanel(l);
            budgetChartPanel.setMouseWheelEnabled(true);
            budgetChartPanel.getChart().removeLegend();
        }
        public void updateBudgetChart(String BudgetCategoryNameKey,Double newValue){
            budgetPieDataset.setValue(BudgetCategoryNameKey, newValue);
        }
        public ChartPanel getBudgetChartPanel()
        {
            return budgetChartPanel;
        }
}

