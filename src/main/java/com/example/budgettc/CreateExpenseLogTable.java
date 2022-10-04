package com.example.budgettc;

import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import static java.lang.System.out;

public class CreateExpenseLogTable extends JPanel {

    public static JTable expenseLogTable = null;
    public static Object[][] localTableDataStorage;

    public CreateExpenseLogTable(Object[][] storage) {
        localTableDataStorage = storage;
            if(expenseLogTable == null){
                out.println("Generating Table");
                initTable();
                initScrollPane();
            } else {
                out.println("Table was already generated, call to CreateExpenseLogTable has not been satisfied");
            }
    }
    public void initTable() {
        String[] columnNames = {"Unallocated Expenses" + " (" + (localTableDataStorage.length) + ")", "Amount"};
        expenseLogTable = new JTable(localTableDataStorage, columnNames) {
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                super.changeSelection(rowIndex, columnIndex, false, false);

            }

            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (column == 1 && Double.valueOf(this.getValueAt(row, 1).toString()) <= 0) {

                    //comp.setForeground(Color.RED);
                    comp.setForeground(new Color(0xFF0000));
                } else if (column == 1 && Double.valueOf(this.getValueAt(row, 1).toString()) > 0) {

                    //comp.setForeground(new Color(0x009600));
                    comp.setForeground(new Color(0x00A100));
                } else if (column == 0 && Double.valueOf(this.getValueAt(row, 1).toString()) > 0) {
                    comp.setForeground(new Color(0xFFFFFF));
                    //comp.setBackground(new Color(0x212121));

                    comp.setFont(new Font("Corbert", Font.BOLD, 11));
                } else
                    comp.setForeground(Color.LIGHT_GRAY);
                //comp.setBackground(new Color(0x212121));
                return comp;

            }

        };
        expenseLogTable.setModel(new DefaultTableModel(localTableDataStorage, columnNames));
        expenseLogTable.getModel().addTableModelListener(e -> out.println("The expense table was changed"));
        expenseLogTable.getColumnModel().getColumn(1).setMaxWidth(100);
        expenseLogTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        expenseLogTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(expenseLogTable.getSelectedRow()>=0){
                out.println("Expense Selected Row: "+expenseLogTable.getSelectedRow());
                budgettcgui.initializeHandler(budgettcgui.expenseList.get(expenseLogTable.getSelectedRow()).getExpenseHandler());}
            }
        });
        for(int x =0; x < expenseLogTable.getRowCount(); x++){
            expenseLogTable.prepareRenderer(expenseLogTable.getCellRenderer(x,1),x,1);
        }

        expenseLogTable.setShowVerticalLines(true);
        expenseLogTable.getTableHeader().setBackground(new Color(124, 0, 0));
        expenseLogTable.getTableHeader().setFont(new Font("Corbert", Font.BOLD, 15));
    }
    public void initScrollPane(){
        setLayout(new BorderLayout());
        add(new JScrollPane(expenseLogTable),BorderLayout.CENTER);
    }

    public void removeSelectedRow(ExpenseCategory expenseToBeRemoved){
        int selectedrow = expenseLogTable.getSelectedRow();
        expenseLogTable.clearSelection();
        ((DefaultTableModel) expenseLogTable.getModel()).removeRow(selectedrow);
        expenseLogTable.requestFocus();
        if(selectedrow < expenseLogTable.getRowCount()) {
            expenseLogTable.changeSelection(selectedrow, 0, false, false);
            expenseLogTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Unallocated Expenses" + " (" + (expenseLogTable.getRowCount()) + ")");
        } else {
            expenseLogTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Unallocated Expenses" + " (0)");
        }
        expenseLogTable.getTableHeader().repaint();

        for(int i = 0; i < localTableDataStorage.length; i++){
            if(localTableDataStorage[i][2] == expenseToBeRemoved)
               out.println(localTableDataStorage[i]);
        }

    // ((DefaultTableModel) expenseLogTable.getModel()).fireTableRowsDeleted(selectedrow,selectedrow+1);;
    }
}