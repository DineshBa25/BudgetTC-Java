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

import static java.lang.System.out;

public class CreateSubBudgetTable extends JPanel {
    public Object[][] localTableDataStorage;
    public CreateSubBudgetTable(Object[][] storage, String[] columnNames) {
        localTableDataStorage = storage;
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
        table.getModel().addTableModelListener(new MyTableModelListener(table));

        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
        table.setShowVerticalLines(true);

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


        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting() && table.getSelectedRow()>=0) {
                    out.println(BudgetCategory.localSubBudgetInstanceMap);
                    out.println(table.getValueAt(table.getSelectedRow(),0) + " Selected");
                    budgettcgui.subBudgetExpenseTable.deleteAll();
                    budgettcgui.subBudgetExpenseTable.addRow(BudgetCategory.localSubBudgetInstanceMap.get(table.getValueAt(table.getSelectedRow(),0)));}
                }


    });

}
}