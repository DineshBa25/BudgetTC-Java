package com.example.budgettc;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.awt.*;
import static java.lang.System.out;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.TreeMap;
//import java.util.Set;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
//import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import com.formdev.flatlaf.FlatLightLaf;
import javafx.scene.Scene;
//import com.formdev.flatlaf.FlatPropertiesLaf;
//import com.formdev.flatlaf.FlatSystemProperties;
//import com.formdev.flatlaf.IntelliJTheme;
//import com.formdev.flatlaf.demo.LookAndFeelsComboBox;
//import com.formdev.flatlaf.demo.DemoPrefs;
//import com.formdev.flatlaf.demo.intellijthemes.*;
//import com.formdev.flatlaf.extras.*;
//import com.formdev.flatlaf.extras.components.FlatTriStateCheckBox;
//import com.formdev.flatlaf.extras.components.FlatTriStateCheckBox.State;
//import com.formdev.flatlaf.ui.FlatUIUtils;
//import com.formdev.flatlaf.util.SystemInfo;
//import com.formdev.flatlaf.util.UIScale;
import javax.swing.table.TableModel;
import javax.swing.event.*;
import java.io.IOException;

public class MyTableModelListener extends budgettcgui implements TableModelListener {
        JTable table;

        public MyTableModelListener(JTable table) {
            this.table = table;
        }

        public void tableChanged(TableModelEvent e){
            int row = e.getFirstRow();
            int column = e.getColumn();
            TableModel model = (TableModel) e.getSource();
            String columnName = model.getColumnName(column);
            Object data = model.getValueAt(row, column);
            out.println("data in row " + row + " column " + column + " changed to " + data);
            if(column == 0)
            storageMatrix.get(row).setCategoryName(data.toString());
            if(column == 1)
            storageMatrix.get(row).setAmountAllocated(Double.parseDouble(data.toString()));
            /*try{
                storageWriter();
            } catch(java.io.IOException x){
                    out.println("createDirectory failed:" + x);
            }*/
            createChart(1);
            int currIndex = tabbedPane.getSelectedIndex();
            createTabbedPane();
            JPanel jp1 = new JPanel(new GridLayout());
            tabbedPane.setSelectedIndex(currIndex);
            jp1.add(tabbedPane);

            if(eastCustimizablePane==handler) {

                centerCustimizablePane= jp1;
                centerCustimizablePane.repaint();
            }
            else
            {
                eastCustimizablePane= jp1;
                eastCustimizablePane.repaint();
            }

            changePanels(1);



        }


}

