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

public class CreateIncomeTable extends JPanel {
    public CreateIncomeTable(Object[][] storage, String[] columnNames) {

        JTable table = new JTable(storage, columnNames){
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                //Always toggle on single selection
                super.changeSelection(rowIndex, columnIndex, false, false);
            }

        };
        table.setModel(new DefaultTableModel(storage, columnNames));


        JScrollPane scrollPane = new JScrollPane(table);
        table.getModel().addTableModelListener(new MyTableModelListener(table));
        //Add the scroll pane to this panel.

        table.getSelectionModel().addListSelectionListener(new RowSelectionListner(table));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(0,new Color(0x949494),new Color(0x949494)), "Income Table", TitledBorder.CENTER,
                TitledBorder.TOP));
        table.setShowGrid(true);
        //table.setBackground(new Color(49, 49, 49));
        //Font ly = new Font("Corbert", Font.BOLD, 12);
        //table.setFont(ly);
        table.getTableHeader().setBackground(new Color(14, 96, 2));
        table.getTableHeader().setFont(new Font("Corbert", Font.BOLD, 15));

        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
    }

}
