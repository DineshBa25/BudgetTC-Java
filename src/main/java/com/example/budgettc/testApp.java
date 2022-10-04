package com.example.budgettc;

import javax.swing.*;
import javax.swing.ImageIcon;
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
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
//import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightContrastIJTheme;
import com.formdev.flatlaf.FlatLightLaf;
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
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
//import javafx.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class testApp extends JFrame implements ActionListener {

    public static NumberFormat formatter = NumberFormat.getInstance();
    public static DecimalFormat decimalFormatter = new DecimalFormat("#.##");
    public static double netMonthlyIncome;
    public static double grossMonthlyIncome;
    private static double totalTaxes = 0;
    private static double fedTax = 0.0;
    private static double stateTax = 0.0;
    private static double cityTax = 0.0;
    private static double ficaTax = 0.0;
    private static double totalSpent = 0.0;
    public static Object[][] storage;
    private static String[] columnNames = { "Category", "Amount Allocated", "HI" };
    private static JPanel panel;
    public static JFXPanel chartpanel;
    private static JFrame frame;
    private static JTable table;
    public static JPanel center;


    public static void main(String args[]) throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {

        System.out.println("Initializing look and feel");
        initializeLookAndFeel(new FlatLightLaf());

        storageReader();

        String[] columnNames = { "Category", "Amount Allocated", "HI" };

        // UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        System.out.println("Current Look and Feel: " + UIManager.getLookAndFeel());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame("Budget TC - Create a Budget, Calculate your Savings, and Track your Expenses");
        // frame.setDefaultlook
        frame.setPreferredSize(screenSize);
        // GridBagLayout experimentLayout = new GridBagLayout();
        // 2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setLayout(experimentLayout);
        // 3. Create components and put them in the frame.

        // ...create emptyLabel...
        //JLabel one = new JLabel("Hi");
        //frame.getContentPane().add(one, BorderLayout.CENTER);
        panel = new JPanel();
        // print2D(storage);

        frame.getContentPane().setLayout(new BorderLayout(20,20));

        //frame.getContentPane().add(panel, BorderLayout.CENTER);
        // panel.setLocation(500, 500);
        panel.setBounds(0, 350, 500, 1000);
        // System.out.println("hiiiiiiiiiiiiiiiiiii");
        JPanel westSide = new JPanel(new GridLayout(2,1));
        center = new JPanel(new GridBagLayout());
        JPanel eastSide = new JPanel(new GridLayout(2,1));

        GridBagConstraints c = new GridBagConstraints();
        JButton button = new JButton("Long-Named Button 4");
        c.fill = GridBagConstraints.BOTH;
        //c.ipady = 40;      //make this component tall
        c.weightx = 1;
        c.weighty = .3;
        c.gridwidth = 0  ;
        c.gridx = 0;
        c.gridy = 0;
        center.add(button, c);



        JButton b1 = new JButton("END");
        //b1.setBounds(screenSize.width / 2, screenSize.height / 3, 70, 30);
        b1.setActionCommand("end");
        b1.setPreferredSize(new Dimension(500,500));
        b1.addActionListener(new budgettcgui());

        eastSide.add(b1);

        JButton b2 = new JButton("Switch Theme");
        b2.setBounds(screenSize.width / 2, screenSize.height / 5, 150, 30);
        b2.setActionCommand("SwitchMode");
        b2.addActionListener(new budgettcgui());

        CreateTable newContentPane = new CreateTable(storage, columnNames);
        newContentPane.setOpaque(true); // content panes must be opaque


        // frame.setContentPane(newContentPane);
        westSide.add(newContentPane);

        frame.add(b2,BorderLayout.NORTH);


        // .add(new JScrollPane(table));
        JButton endTemp = new JButton(
                "This is where the calculators will go");
        eastSide.add(endTemp);

        ImageIcon icon = new ImageIcon("Pictures/darkModeLogo.png","logo");
        JLabel label1 = new JLabel("Image and Text", icon, JLabel.CENTER);
        //center.add(label1);

        chartpanel = new JFXPanel();GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.BOTH;
        d.weightx = 1;
        d.weighty = .7;
        d.gridwidth = 1;
        d.gridx = 0;
        d.gridy = 1;
        d.insets = new Insets(60,0,0,0);  //top padding


        center.add(chartpanel,d);
        out.println(center.getPreferredSize());

        createChart(chartpanel);
        //chartpanel.setBounds(100, 300, 1000, 1000);
        center.add(new JButton("I dont know what goes here"),c);


        //chartpanel.setPreferredSize(new Dimension(1000,1000));




        // 4. Size the frame.
        frame.add(westSide,BorderLayout.WEST);
        frame.add(center,BorderLayout.CENTER);
        frame.add(eastSide, BorderLayout.EAST);

        frame.pack();
        panel.updateUI();
        // 5. Show it.
        frame.setVisible(true);

    }


    public void actionPerformed(ActionEvent e) {
        if ("end".equals(e.getActionCommand())) {
            try{
                out.println("USER ACTION: Program end requested");
                storageWriter();
                out.println("ACTION RESULT: Program ended succesfully!");
            } catch(java.io.IOException x){
                out.println("ACTION RESULT: Program ended UNsuccesfully!\ncreateDirectory failed:" + x);
            }
            System.exit(0);
        }
        else if ("SwitchMode".equals(e.getActionCommand())) {
            out.println("USER ACTION: Switch Theme requested");
            System.out.print(String.valueOf(new FlatDarkLaf()));
            if (String.valueOf(UIManager.getLookAndFeel()).equals(String.valueOf(new FlatDarkLaf())))
                try {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                } catch (Exception ex) {
                    System.err.println("Failed to initialize LaF");
                }
            else if (String.valueOf(UIManager.getLookAndFeel()).equals(String.valueOf(new FlatIntelliJLaf())))
                try {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                } catch (Exception ex) {
                    System.err.println("Failed to initialize LaF");
                }
            panel.updateUI();
            SwingUtilities.updateComponentTreeUI(frame);
        }

    }


    //---------------------------INITIALIZE HELPER METHODS HERE-------------------------------------
    //sets the initial look and feel of the application
    public static void initializeLookAndFeel(LookAndFeel newloLookAndFeel)
    {
        try {
            UIManager.setLookAndFeel(
                    //new FlatIntelliJLaf();
                    //new FlatArcDarkOrangeIJTheme()
                    new FlatMoonlightContrastIJTheme()
            );
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    // reads data from the file and adds it to the map which hold the data. Returns false if there is no data.
    public static boolean storageReader() throws IOException {
        FileReader fReader = new FileReader("/home/dineshb25/Desktop/budgettc/src/main/resources/storage.txt");
        BufferedReader reader = new BufferedReader(fReader);
        int colsize = Integer.parseInt(reader.readLine());
        int rowsize = Integer.parseInt(reader.readLine());
        storage = new Object[colsize][rowsize];
        // System.out.print("Coulumns: "+reader.readLine() + "\nRows:
        // "+reader.readLine());
        String temp1, temp2, temp3;

        netMonthlyIncome = Double.parseDouble(reader.readLine());
        /*
         * if ((temp1 = reader.readLine()) != null && (temp2 = reader.readLine()) !=
         * null) { netMonthlyIncome = Double.parseDouble(temp1); grossMonthlyIncome =
         * Double.parseDouble(temp2); //totalSpent = Double.parseDouble(temp3); }
         */
        String keyval = "";
        String val = "";
        boolean x = false;
        int y = 0;
        while (((storage[y][0] = reader.readLine()) != null)
                && ((storage[y][1] = reader.readLine()) != null)
                && ((storage[y][2] = reader.readLine()) != null)) {
            x = true;
            y++;
        }
        reader.close();
        // print2D(storage);
        return x;
    }

    // prints out a matrix into the console into a readable string, Used for testing
    // purposes and does not exist in the build
    public static void print2D(Object mat[][]) {
        // Loop through all rows
        for (Object[] row : mat)

            // converting each row as string and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }

    //------------------------METHODS and FEATURES TO IMPLEMENT OR ADD------------------------------------
    //Methods may not be fit or applicable for the program.

    //writes all of the data into a txt file so the user can access it next time.
    public static void storageWriter() throws IOException
    {
        System.out.println(storage.length);
        FileWriter fWriter = new FileWriter("storage.txt", false);
        BufferedWriter writer = new BufferedWriter(fWriter);
        //Set<String> set1 = storage.keySet();
        writer.write(String.valueOf(storage.length));
        writer.newLine();
        writer.write(String.valueOf(storage[0].length));
        writer.newLine();
        writer.write(String.valueOf(netMonthlyIncome));
        writer.newLine();
        for(int x = 0; x < storage.length-1; x++)
        {
            writer.write(String.valueOf(storage[x][0]));
            writer.newLine();
            writer.write(String.valueOf(storage[x][1]));
            writer.newLine();
            writer.write(String.valueOf(storage[x][2]));
            writer.newLine();
        }
        writer.close();
    }

    public static void createChart(JFXPanel dataPanel){
        PieChart pieChart = new PieChart();

        ObservableList<PieChart.Data> data= FXCollections.observableArrayList();
        for(Object[] x: storage)
        {
            if(x[1] != null)
            {
                data.add(new PieChart.Data(x[0].toString(),  Double.valueOf(x[1].toString()) ));
                out.println("Adding category: " + x[0].toString() +" to chart with value of: "+x[1].toString());
            }
        }
        pieChart.setData(data);
        pieChart.setTitle("Budget Summary");
        pieChart.setLabelsVisible(true);

        pieChart.setMaxSize(2000,2000);
        Group root = new Group();
        out.println(dataPanel.getPreferredSize());

        StackPane pane = new StackPane();
        pane.getChildren().add(pieChart);
        //pane.setVisible(true);

        //pieChart.prefHeightProperty().bind(pane.heightProperty());
        //pieChart.prefWidthProperty().bind(pane.widthProperty());
        //pieChart.setPrefSize(center.getMaximumSize().getHeight(), center.getMaximumSize().getWidth());
        //pieChart.setMinSize(500,500);
        out.println(pieChart.getPrefHeight());
        out.println(pieChart.getMaxHeight());
        root.getChildren().add(pieChart);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("color.css");

        //dataPanel.setPreferredSize(new Dimension(1000,1000));
        dataPanel.setScene(scene);
        //dataPanel.setPreferredSize(new Dimension(1000,1000));

    }

    public static void initializeTaxCalcPanel()
    {

    }

    public static void initializePieChart()
    {

    }

    public static void initializeExpenseLog()
    {

    }

    public static void initializeHeader()
    {

    }
/*

   //deletes all of the data in the budget
   public static void deleteData() throws IOException
   {
      FileWriter fWriter = new FileWriter("StorageVault.txt", false);
      BufferedWriter writer = new BufferedWriter(fWriter);
      writer.write("");
      writer.close();
   }



   public static void retirementCalc()
   {
      System.out.print(
             "To find out how much money you will have at retirment based on your current monthly savings allocation\n"
                     + "we will need to ask you a few questions:\n\nFirstly, what is your current age? ");
      double currentAge = input.nextDouble();
      System.out.print("\nAt what age do you plan to retire? ");
      double retirementAge = input.nextDouble();
      System.out.print("\nHow much money have you saved up till now in a Tax-Deffered savings account? ");
      double currentSav = input.nextDouble();
      double total = currentSav;
      System.out.print("\nWhat ROI would you like us to use(enter in decimal form)?"
             + "\n(enter \"0\" if you would like use the standard market return(9%)) ==> ");
      double returnOnInvestment = input.nextDouble();
      if (returnOnInvestment == 0.0)
         returnOnInvestment = 0.09;
      double monthlyContribution = storage.get("(I) Savings");
      for (int x = 0; x < (retirementAge - currentAge); x++) {
         total = (total * (1 + returnOnInvestment)) + ((monthlyContribution) * 12);
      }

      double inflationAdjustedTotal = (Math.pow(.975, (retirementAge - currentAge)) * total);

      System.out.printf("\nAfter " + (retirementAge - currentAge) + " years of investing $%.2f a month at "
             + (100 * returnOnInvestment) + "%% with a current savings of $" + formatter.format(currentSav)
             + " in a tax-deferred savings account, you will have approximatley $"
             + formatter.format(Double.parseDouble(decimalFormatter.format(total))) + " dollars.\n" + "Which in "
             + (retirementAge - currentAge) + " years will be worth $"
             + formatter.format(Double.parseDouble(decimalFormatter.format(inflationAdjustedTotal)))
             + " at an estimated 2.5%% annual inflation.\n", monthlyContribution);

      // return total;
   }





    public static void budgetSummary()
   {
      System.out.print("ID    Category             Amount Allocated     Proportion of Total"
                   +"\n--------------------------------------------------------------------");
      Set<String> set1 = storage.keySet();
      String id, cat;
      double prop;
      double total = grossMonthlyIncome;
      for (String key : set1)
      {
         id = (key.substring(key.indexOf('(') + 1, key.indexOf(')')));
         System.out.print("\n" + id);
         for (int x = id.length(); x < 5; x++)
         {
            System.out.print(" ");
         }
         cat = key.substring(key.indexOf(" "));
         System.out.print(cat);
         for (int x = cat.length(); x < 27; x++)
         {
            System.out.print(" ");
         }
         System.out.print("$" + formatter.format(Double.parseDouble(decimalFormatter.format(storage.get(key)))));
         total -= storage.get(key);
         for (int x = (formatter.format(Double.parseDouble(decimalFormatter.format(storage.get(key))))).length(); x < 16; x++)
         {
            System.out.print(" ");
         }
         System.out.print(formatter.format(
                Double.parseDouble(decimalFormatter.format((storage.get(key) / netMonthlyIncome) * 100))) + "% ");
         for (int x = (formatter.format(Double.parseDouble(decimalFormatter.format((storage.get(key) / netMonthlyIncome) * 100))))
                        .length(); x < 6; x++)
                        {

            System.out.print(" ");
         }
         for (double x = 0; x < (storage.get(key) / netMonthlyIncome) * 100; x++)
         {
            System.out.print("|");
         }
      }
      totalSpent = netMonthlyIncome - total;
      System.out.println("\n\nAmount Unallocated/Over allocated: $"
             + formatter.format(Double.parseDouble(decimalFormatter.format((total)))) + "\n");
   }



   public static void addCategory(boolean whatToDo, String category, Double value, Map<String, Double> reAdd)
   {
      int budgetSize = storage.size();
      if (whatToDo)
      {
         String x = "_(" + (budgetSize + 1) + ") " + category;
         budgetSize++;
         storage.put(x, value);
      }
      else
      {
         Set<String> set1 = reAdd.keySet();
         budgetSize++;
         for (String key : set1)
         {
            String x = "_(" + (budgetSize++) + ") " + key.substring(key.indexOf(' ')+1);
            storage.put(x, reAdd.get(key));
         }
      }
   }




   public static double ficaCalculator(double grossIncome)
   {
      double taxes = 0;
      if (grossIncome * .062 > 8853)
         taxes = 8853;
      else
         taxes = grossIncome * .062;
      return taxes;
   }


   --------------------------------------------------excess code---------------------------------------------------------
    /*
    static class MyTableModelListener implements TableModelListener {
        JTable table;

        MyTableModelListener(JTable table) {
            this.table = table;
        }

        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            TableModel model = (TableModel) e.getSource();
            String columnName = model.getColumnName(column);
            Object data = model.getValueAt(row, column);
            out.println("data in row " + row + " column " + column + " changed to " + data);
            storage[row][column] = data;
        }
    }
/*
    public static class CreateTable extends JPanel {
        // public static JTable table;
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
 /*           JTable table = new JTable(storage, columnNames);
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

            testColumn.setCellEditor(new DefaultCellEditor(comboBox));

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
            scrollPane.setBounds(0, 350, 1000, 100);
            table.getModel().addTableModelListener(new MyTableModelListener(table));
            // Add the scroll pane to this panel.
            add(scrollPane);
        }

    }

    // writes all of the data into a txt file so the user can access it next time.
    /*
     * public static void storageWriter() throws IOException { FileWriter fWriter =
     * new FileWriter("StorageVault.txt", false); BufferedWriter writer = new
     * BufferedWriter(fWriter); Set<String> set1 = storage.keySet();
     * writer.write(String.valueOf(netMonthlyIncome)); writer.newLine();
     * writer.write(String.valueOf(grossMonthlyIncome)); writer.newLine();
     * //writer.write(String.valueOf(totalSpent)); //writer.newLine(); for (String
     * key : set1) { writer.write(key); writer.newLine();
     * writer.write(String.valueOf(storage.get(key))); writer.newLine(); }
     * writer.close(); }
     */



}