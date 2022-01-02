package com.example.budgettc;

import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CreateSubBudgetTable extends JPanel {
    public CreateSubBudgetTable(Object[][] storage, String[] columnNames) {
        // super(new GridLayout(1,0));

        /*
         * Object[][] data = { {"Kathy", "Smith", "Snowboarding", new Integer(5), new
         * Boolean(false)}, {"John", "Doe", "Rowing", new Integer(3), new
         * Boolean(true)}, {"Sue", "Black", "Knitting", new Integer(2), new
         * Boolean(false)}, {"Jane", "White", "Speed reading", new Integer(20), new
         * Boolean(true)}, {"Joe", "Brown", "Pool", new Integer(10), new Boolean(false)}
         * };
         */
        JTable table = new JTable(storage, columnNames){
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                //Always toggle on single selection
                super.changeSelection(rowIndex, columnIndex, false, false);
            }

            public Dimension getPreferredScrollableViewportSize()
                {
                    return new Dimension(100, 200);
                }

        };
        table.setModel(new DefaultTableModel(storage, columnNames));


        JScrollPane scrollPane = new JScrollPane(table);
        //scrollPane.setBounds(0, 350, 1000, 100);
        //table.setPreferredSize(new Dimension(1000, 1000));
        // scrollPane.setSize(1000,1000);
        table.getModel().addTableModelListener(new MyTableModelListener(table));
        //table.getSelectionModel().addListSelectionListener(new RowSelectionListner(table));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(0,new Color(0x949494),new Color(0x949494)), "Sub-budget Table", TitledBorder.CENTER,
                TitledBorder.TOP));

        // Add the scroll pane to this panel.
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
        table.setShowGrid(true);
        //table.setBackground(new Color(49, 49, 49));
        //Font ly = new Font("Corbert", Font.BOLD, 12);
        //table.setFont(ly);
        table.getTableHeader().setBackground(new Color(126, 67, 0));
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        table.setDefaultRenderer(table.getColumnClass(0),dtcr);
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        table.getTableHeader().setFont(new Font("Corbert", Font.BOLD, 15));
        scrollPane.setMinimumSize(new Dimension(0,0));
        table.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent arg0) {
                table.clearSelection();
            }

        });
    }

}
