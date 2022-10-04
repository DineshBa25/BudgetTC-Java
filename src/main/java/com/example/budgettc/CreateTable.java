package com.example.budgettc;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
//import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Random;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static com.example.budgettc.budgettcgui.*;
import static java.lang.System.out;

public class CreateTable extends JPanel {
    public CreateTable(Object[][] storage, String[] columnNames) {
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
            public boolean isCellEditable(int row, int column) {
                //all cells false
                if(column == 1)
                    return true;
                return false;
            }
        };
        table.setModel(new DefaultTableModel(storage, columnNames));

        // table.setLocation(1000, 500);
        TableColumn testColumn = table.getColumnModel().getColumn(2);
        out.println(Arrays.deepToString(storage));
        testColumn.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Random rand = new Random(); //instance of random class
                int upperbound = 99;
                int int_random = rand.nextInt(upperbound);
                JProgressBar x = new JProgressBar(0,100);
                Color amtLeftcolor = new Color(27, 107, 0);
                x.setBackground(amtLeftcolor);
                Color amtSpentColor = new Color(119, 0, 0);
                UIManager.put("ProgressBar.foreground", amtSpentColor);
                out.println("*******************************"+row
                        //storageMatrix.get(row).getTotalExpenseAllocated()
                );
                x.setValue((int)(storageMatrix.get(row).getTotalExpenseAllocated()));
                int borderSize = 2;
                x.setBorder(BorderFactory.createMatteBorder(borderSize,borderSize,borderSize,borderSize,new Color(0x00000FF, true)));
                return x;
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                String columnName = model.getColumnName(column);
                Object data = model.getValueAt(row, column);
                out.println("In Budget Table, row " + row + " column " + column + " was changed to " + data);
                if(column == 0)
                    budgettcgui.storageMatrix.get(row).setCategoryName(data.toString());
                if(column == 1)
                    budgettcgui.storageMatrix.get(row).setAmountAllocated(Double.parseDouble(data.toString()));

                out.println("**"+budgettcgui.storageMatrix.get(row));
                budgettcgui.createChart(1);
                int currIndex = budgettcgui.tabbedPane.getSelectedIndex();
                budgettcgui.createTabbedPane();
                JPanel jp1 = new JPanel(new GridLayout());
                budgettcgui.tabbedPane.setSelectedIndex(currIndex);
                jp1.add(budgettcgui.tabbedPane);

                if(budgettcgui.eastCustimizablePane==budgettcgui.handler) {
                    out.println("hi");
                    budgettcgui.centerCustimizablePane= budgettcgui.createTabbedPane() ;
                    budgettcgui.centerCustimizablePane.repaint();
                }
                else {
                    budgettcgui.eastCustimizablePane= budgettcgui.createTabbedPane() ;
                    budgettcgui.eastCustimizablePane.repaint();
                }
                changePanels(false);
                budgettcgui.frame.revalidate();
                budgettcgui.frame.repaint();




            }
        });
                table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (table.getSelectedRow() >= 0) {
                            int viewRow = table.getSelectedRow();
                            //System.out.println("A new row was selected!");
                            out.println(table.getClass());

                            budgettcgui.initializeHandler(budgettcgui.storageMatrix.get(table.getSelectedRow()).getJPanel());
                            budgettcgui.frame.revalidate();
                            budgettcgui.frame.repaint();


                        }
                    }

                });

        table.setShowVerticalLines(true);

        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);

        JButton addIncomeButton =  new JButton("Add a new Budget Category");
        addIncomeButton.setActionCommand("Add Budget Category");
        addIncomeButton.addActionListener(new budgettcgui());

        add(addIncomeButton, BorderLayout.SOUTH);


        table.getTableHeader().setBackground(new Color(0, 88, 143));
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        table.setDefaultRenderer(table.getColumnClass(0),dtcr);
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        table.getTableHeader().setFont(new Font("Corbert", Font.BOLD, 15));
        table.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                table.clearSelection();
        }});
    }
}
