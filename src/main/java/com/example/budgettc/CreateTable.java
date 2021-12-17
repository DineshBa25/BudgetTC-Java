package com.example.budgettc;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;

public class CreateTable extends JPanel {
    public CreateTable(Object[][] storage, String[] columnNames) {
        // super(new GridLayout(1,0));

        /*
         * Object[][] data = { {"Kathy", "Smith", "Snowboarding", new Integer(5), new
         * Boolean(false)}, {"John", "Doe", "Rowing", new Integer(3), new
         * Boolean(true)}, {"Sue", "Black", "Knitting", new Integer(2), new
         * Boolean(false)}, {"Jane", "White", "Speed reading", new Integer(20), new
         * Boolean(true)}, {"Joe", "Brown", "Pool", new Integer(10), new Boolean(false)}
         * };
         */
        JTable table = new JTable(storage, columnNames);
        table.setModel(new DefaultTableModel(storage, columnNames));
        // table.setLocation(1000, 500);
        TableColumn testColumn = table.getColumnModel().getColumn(2);
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Tax");
        comboBox.addItem("Utilites");
        comboBox.addItem("Transportation");
        comboBox.addItem("Housing");
        comboBox.addItem("Health Care");
        comboBox.addItem("Entertainment");
        comboBox.addItem("Debt");
        comboBox.addItem("Retirement Savings");
        comboBox.addItem("\"Other Savings\"");

        testColumn.setCellEditor(new DefaultCellEditor(comboBox));

        // final JTable table = new JTable(storage, columnNames);
        // table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        // table.setFillsViewportHeight(true);

        // table.setModel(new DefaultTableModel(new Object[3][3],new
        // String[]{"Position", "Width", "Height"}));

        // table.getModel().addTableModelListener(new MyTableModelListener(table));
        // Create the scroll pane and add the table to it.
        // panel.add(table);
        // panel.setBounds(0, 350, 1000, 1000);

        JScrollPane scrollPane = new JScrollPane(table);
        //scrollPane.setBounds(0, 350, 1000, 100);
        //table.setPreferredSize(new Dimension(1000, 1000));
       // scrollPane.setSize(1000,1000);
        table.getModel().addTableModelListener(new MyTableModelListener(table));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Budget Table", TitledBorder.CENTER,
                TitledBorder.TOP));
        // Add the scroll pane to this panel.
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
    }

}
