package com.example.budgettc;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubBudgetExpenseTableModel  extends AbstractTableModel {

    private List<Object[]> rows;
    private String[] columnNames = new String[]{"Allocated Expenses ", "Amount", ""};

    public SubBudgetExpenseTableModel() {
        rows = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 2)
            return true;
        else
            return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object[] row = rows.get(rowIndex);
        if(columnIndex == 2)
            return new JButton("hi");
        return row[columnIndex];

    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (column == 2)
            return new JButton("hi");
        return null;
    }



    public void addRow() {
        int rowCount = getRowCount();
        String[] row = new String[getColumnCount()];
        for (int index = 0; index < getColumnCount(); index++) {
            row[index] = rowCount + "x" + index;
        }
        rows.add(row);
        fireTableRowsInserted(rowCount, rowCount);
    }

    public void addRow(SubBudgetCategory subBudgetCategory) {
        int rowCount = getRowCount();
        String[][] x = subBudgetCategory.getMatrixOfExpenses();
        Object[] y;
        for (int i = 0; i < x.length; i++) {
            System.out.println("Adding: " + Arrays.toString(x[i]));
            y = Arrays.copyOf(x[i], 3, Object[].class); //create new array from old array and allocate one more element
            y[2] = new JButton("Hi");
            rows.add(y);
        }
        //System.out.println("*()))))))))))))))))))))))))))))))))))"+rows);
        columnNames[0] = "Allocated Expenses (0)";
        fireTableRowsInserted(rowCount, rowCount);
        budgettcgui.frame.revalidate();
        budgettcgui.frame.repaint();
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public void deleteAll() {
        //rows = new ArrayList<>();
        int rowCount = getRowCount();
        rows.clear();
        //fireTableDataChanged();
        fireTableRowsDeleted(0, rowCount);
        setColumnNames(new String[]{"Allocated Expenses (0)", "Amount", "button"});
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JButton newb = new JButton();
        newb.setBackground(new Color(0x670000));
        newb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Clicked");
            }
        });
        return newb;

    }

}
