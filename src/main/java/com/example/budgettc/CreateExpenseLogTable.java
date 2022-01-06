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

import static java.lang.System.out;

public class CreateExpenseLogTable extends JPanel {

    public static JTable expenseLogTable;

    public CreateExpenseLogTable(Object[][] storage, String[] columnNames) {

        System.out.print("Reloading Table");
        columnNames[0] = "Unallocated Expenses" + " (" + (storage.length) + ")";
        JTable table = new JTable(storage, columnNames){
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                    super.changeSelection(rowIndex, columnIndex, false, false);

            }
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component comp=super.prepareRenderer(renderer,row, column);
                if( column == 1 &&  Double.valueOf(this.getValueAt(row,1).toString())<= 0)
                {

                    //comp.setForeground(Color.RED);
                    comp.setForeground(new Color(0xFF0000));
                }
                else if( column == 1 &&  Double.valueOf(this.getValueAt(row,1).toString())> 0)
                {

                    //comp.setForeground(new Color(0x009600));
                    comp.setForeground(new Color(0x00A100));
                }
                else if (column == 0 && Double.valueOf(this.getValueAt(row, 1).toString()) > 0)
                {
                    comp.setForeground(new Color(0xFFFFFF));
                    //comp.setBackground(new Color(0x212121));

                    comp.setFont(new Font("Corbert", Font.BOLD, 11));
                }
                else
                    comp.setForeground(Color.LIGHT_GRAY);
                    //comp.setBackground(new Color(0x212121));
                    return comp;

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
        /*scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Expense Table/Log", TitledBorder.CENTER,
                TitledBorder.TOP));*/
        table.getModel().addTableModelListener(e -> out.println("The expense table was changed"));

        table.getColumnModel().getColumn(1).setMaxWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                out.println("Expense Selected Row: "+table.getSelectedRow());
                budgettcgui.initializeHandler(budgettcgui.expenseList.get(table.getSelectedRow()).getExpenseHandler());
                //budgettcgui.center.setResizeWeight(0.25);
                //budgettcgui.center.setDividerLocation(0.25);

            }
        });
        for(int x =0; x < table.getRowCount(); x++){
            table.prepareRenderer(table.getCellRenderer(x,1),x,1);
        }


        /*scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(0,new Color(0x05D5D5D, true),new Color(0x646464)), "Unallocated Expense", TitledBorder.CENTER,
                TitledBorder.TOP));*/
        //table.setShowGrid(true);
        table.setShowVerticalLines(true);
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