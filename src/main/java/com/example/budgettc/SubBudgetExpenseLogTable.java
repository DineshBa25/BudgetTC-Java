package com.example.budgettc;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EventObject;
import java.util.Random;

import static java.lang.System.out;

public class SubBudgetExpenseLogTable {

    public static JTable subBudgetExpenseLogTable;
    public static Object[][] localTableDataStorage;
    public JPanel panel;

    public SubBudgetExpenseLogTable() {
        localTableDataStorage = new Object[][]{{0, 0}, {0, 0}};
        //if(subBudgetExpenseLogTable == null){
        out.println("Sub-Budget expense log table was not null, generating table");
        out.println("Initializing Table");
        initTable();
        out.println("Initializing JScrollPane");
        panel = new JPanel(new GridLayout(1,1));
        initScrollPane();
        out.println(subBudgetExpenseLogTable);
        //add(new JLabel("hi"));
        // }
        //}
    }

    public static void updateData(SubBudgetCategory category){
        //localTableDataStorage = category.getMatrixOfExpenses();
        //((SubBudgetExpenseTableModel) expenseLogTable.getModel()).addRow();
    }


    public static void initTable() {
        String[] columnNames = {"Allocated Expenses" + " (" + (localTableDataStorage.length) + ")", "Amount", "button"};
        System.out.println("Actually Printing Table");
        subBudgetExpenseLogTable = new JTable(new Object[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, columnNames){

            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                super.changeSelection(rowIndex, columnIndex, false, false);

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
                return comp;

            }


        };
        TableCellRenderer tableRenderer;
        tableRenderer = subBudgetExpenseLogTable.getDefaultRenderer(JButton.class);
        subBudgetExpenseLogTable.setDefaultRenderer(JButton.class, new JTableButtonRenderer(tableRenderer));


        subBudgetExpenseLogTable.setModel(new SubBudgetExpenseTableModel());
        subBudgetExpenseLogTable.getModel().addTableModelListener(e -> out.println("The Sub-budget expense table was changed"));
        subBudgetExpenseLogTable.getColumnModel().getColumn(1).setMaxWidth(100);
        subBudgetExpenseLogTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        subBudgetExpenseLogTable.getColumnModel().getColumn(2).setMaxWidth(30);
        subBudgetExpenseLogTable.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                BufferedImage myPicture = null;
                try {
                    myPicture = ImageIO.read(getClass().getResource("/minilogo6.png"));
                    // out.println(myPicture);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                assert myPicture != null;

                ImageLabel imglabel = new ImageLabel(new ImageIcon(myPicture));
                int borderSize = 5;
                imglabel.setBorder(BorderFactory.createMatteBorder(borderSize,borderSize,borderSize,borderSize,new Color(0x00000FF, true)));


                //expenseHandler = new JPanel(new GridLayout(1,1));
                JPanel newp = new JPanel(new GridLayout(1,1));
                return imglabel;
            }
        });
        subBudgetExpenseLogTable.getColumnModel().getColumn(2).setCellEditor(new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

                Image myPicture = null;
                try {
                    myPicture = ImageIO.read(getClass().getResource("/minilogo6.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                assert myPicture != null;

                JButton button1 = new JButton();
                button1.setIcon(new ImageIcon(myPicture.getScaledInstance(30,20,Image.SCALE_FAST)));
                button1.setFocusPainted(false);
                button1.setBackground(new Color(0x0000000, true));
                button1.setBorderPainted(false);
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        out.println("Delete Button pressed: " + subBudgetExpenseLogTable.getModel().getValueAt(row,0));
                    }
                });
                return button1;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return true;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return true;
            }

            @Override
            public boolean stopCellEditing() {
                return true;
            }

            @Override
            public void cancelCellEditing() {

            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {

            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {

            }
        });

        subBudgetExpenseLogTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {}

        });

        for(int x =0; x < subBudgetExpenseLogTable.getRowCount(); x++){
            subBudgetExpenseLogTable.prepareRenderer(subBudgetExpenseLogTable.getCellRenderer(x,1),x,1);
        }


        subBudgetExpenseLogTable.setShowVerticalLines(true);
        subBudgetExpenseLogTable.getTableHeader().setBackground(new Color(124, 0, 0));
        subBudgetExpenseLogTable.getTableHeader().setFont(new Font("Corbert", Font.BOLD, 15));

        //ButtonColumn buttonColumn = new ButtonColumn(table, delete, 2);

        System.out.println("--------------------------------------Actually Printing Table");
    }
    public void initScrollPane(){
        //setLayout(new BorderLayout());
        //removeAll();
        panel.add(new JScrollPane(subBudgetExpenseLogTable));
        //add(new JButton("ji"));
        //return subBudgetExpenseLogTable;
    }

    public void updateData(Object[][] x){
        localTableDataStorage = x;
        ((DefaultTableModel) subBudgetExpenseLogTable.getModel()).fireTableDataChanged();
    }

    public void addRow(SubBudgetCategory subCat){
        out.println("hi****************************************************************((((((((((((((((((((((((((((");
        ((SubBudgetExpenseTableModel) subBudgetExpenseLogTable.getModel()).addRow(subCat);
        subBudgetExpenseLogTable.getColumnModel().getColumn(0).setHeaderValue("Allocated Expenses ("+ subCat.allocatedExpenseList.size() +")");
        subBudgetExpenseLogTable.revalidate();
        subBudgetExpenseLogTable.repaint();
    }

    public void deleteAll(){
        ((SubBudgetExpenseTableModel) subBudgetExpenseLogTable.getModel()).deleteAll();
        subBudgetExpenseLogTable.getColumnModel().getColumn(0).setHeaderValue("Allocated Expenses ("+ 0 +")");
        subBudgetExpenseLogTable.revalidate();
        subBudgetExpenseLogTable.repaint();
    }

    public JPanel returnPane(){
        return panel;
    }

}

class JTableButtonRenderer implements TableCellRenderer {
    private TableCellRenderer defaultRenderer;
    public JTableButtonRenderer(TableCellRenderer renderer) {
        defaultRenderer = renderer;
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value instanceof Component)
            return (Component)value;
        return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}