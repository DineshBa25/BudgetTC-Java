package com.example.budgettc;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RowSelectionListner extends budgettcgui implements ListSelectionListener {
    JTable table;

    public RowSelectionListner(JTable table) {
        this.table = table;
    }
        @Override
        public void valueChanged(ListSelectionEvent event) {

            int viewRow = table.getSelectedRow();
                System.out.println("A new row was selected!");
                System.out.println(table.getModel().getValueAt(table.getSelectedRow(),0));
                initializeHandler("The row containing " + (table.getModel().getValueAt(table.getSelectedRow(),0)).toString() + " was Selected!");
            }
        }


