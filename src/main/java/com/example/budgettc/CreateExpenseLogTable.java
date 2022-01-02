package com.example.budgettc;

import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;

import static java.lang.System.out;

public class CreateExpenseLogTable extends JPanel {
    public CreateExpenseLogTable(Object[][] storage, String[] columnNames) {
        // super(new GridLayout(1,0));

        /*
         * Object[][] data = { {"Kathy", "Smith", "Snowboarding", new Integer(5), new
         * Boolean(false)}, {"John", "Doe", "Rowing", new Integer(3), new
         * Boolean(true)}, {"Sue", "Black", "Knitting", new Integer(2), new
         * Boolean(false)}, {"Jane", "White", "Speed reading", new Integer(20), new
         * Boolean(true)}, {"Joe", "Brown", "Pool", new Integer(10), new Boolean(false)}
         * };
         */
        //Object[][] mat= {{2,1,1,2,null},{1,2,2,1,null}};
        JTable table = new JTable(storage, columnNames){
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                    super.changeSelection(rowIndex, columnIndex, false, false);

            }
        };
        table.setModel(new DefaultTableModel(storage, columnNames));

        // table.setLocation(1000, 500);

        JButton testButton = new JButton("GO");


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
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Expense Table/Log", TitledBorder.CENTER,
                TitledBorder.TOP));
        table.getModel().addTableModelListener(e -> out.println("The expense table was changed"));

        table.getColumnModel().getColumn(1).setMaxWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                out.println("Expense Selected Row: "+table.getSelectedRow());
                budgettcgui.initializeHandler(budgettcgui.expenseList.get(table.getSelectedRow()).getExpenseHandler());
                budgettcgui.center.setResizeWeight(0.4);
                budgettcgui.center.setDividerLocation(0.4);
            }
        });
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(0,new Color(0x949494),new Color(0x949494)), "Expense Table", TitledBorder.CENTER,
                TitledBorder.TOP));
        table.setShowGrid(true);
        //table.setBackground(new Color(49, 49, 49));
        //Font ly = new Font("Corbert", Font.BOLD, 12);
        //table.setFont(ly);
        table.getTableHeader().setBackground(new Color(124, 0, 0));
        table.getTableHeader().setFont(new Font("Corbert", Font.BOLD, 15));
        //Add the scroll pane to this panel.
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
    }

}