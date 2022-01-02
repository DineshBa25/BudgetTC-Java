package com.example.budgettc;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Random;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
        };
        table.setModel(new DefaultTableModel(storage, columnNames));
        // table.setLocation(1000, 500);
        TableColumn testColumn = table.getColumnModel().getColumn(2);
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Tax");
        comboBox.addItem("Utilites");
        comboBox.addItem("Transportation");
        comboBox.addItem("Housing");
        comboBox.addItem("Health Care");
        comboBox.addItem("Entertainment");
        comboBox.addItem("Debt");
        comboBox.addItem("Retirement Savings");
        comboBox.addItem("\"Other Savings\"");


        testColumn.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Random rand = new Random(); //instance of random class
                int upperbound = 99;
                //generate random values from 0-24
                int int_random = rand.nextInt(upperbound);
                JProgressBar x = new JProgressBar(0,100);
                Color amtLeftcolor = new Color(27, 107, 0);
                x.setBackground(amtLeftcolor);
                Color amtSpentColor = new Color(119, 0, 0);
                UIManager.put("ProgressBar.foreground", amtSpentColor);
                x.setValue(int_random);
                int borderSize = 2;
                x.setBorder(BorderFactory.createMatteBorder(borderSize,borderSize,borderSize,borderSize,new Color(0x00000FF, true)));
                return x;
            }
        });

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
        //scrollPane.setBounds(0, 350, 1000, 100);
        //table.setPreferredSize(new Dimension(1000, 1000));
       // scrollPane.setSize(1000,1000);
        //scrollPane.constraints.fill = GridBagConstraints.VERTICAL;
        table.getModel().addTableModelListener(e -> System.out.println("The budget table was changed"));
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(table.getSelectedRow() >= 0) {
                    int viewRow = table.getSelectedRow();
                    //System.out.println("A new row was selected!");
                    System.out.println(table.getClass());

                    budgettcgui.initializeHandler(budgettcgui.storageMatrix.get(table.getSelectedRow()).getJPanel());
                }
            }

        });
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(0,new Color(0x949494),new Color(0x949494)), "Budget Table", TitledBorder.CENTER,
                TitledBorder.TOP));

        // Add the scroll pane to this panel.
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
        table.setShowGrid(true);
        //table.setBackground(new Color(49, 49, 49));
        //Font ly = new Font("Corbert", Font.BOLD, 12);
        //table.setFont(ly);
        table.getTableHeader().setBackground(new Color(0, 88, 143));
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        table.setDefaultRenderer(table.getColumnClass(0),dtcr);
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        table.getTableHeader().setFont(new Font("Corbert", Font.BOLD, 15));
        table.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                table.clearSelection();
            }

        });
    }

}
