package com.example.budgettc;

//import java tools
import static java.lang.System.out;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


//Import Flatlaf look and feel
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightContrastIJTheme;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatNightOwlContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSpacegrayIJTheme;


import javafx.scene.control.ColorPicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


//Import JavaFX
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

//Unassigned/General TO_DO Items (For use in Intellij management)
//todo refactor organize and clean code
//todo change budget table column name



public class budgettcgui extends JFrame implements ActionListener, ItemListener{

    //Formatters
    public static NumberFormat formatter = NumberFormat.getInstance();
    public static DecimalFormat decimalFormatter = new DecimalFormat("#.##");

    //Public Variables
    public static double netMonthlyIncome;
    public static double grossMonthlyIncome;
    private static double totalTaxes = 0;
    private static double fedTax = 0.0;
    private static double stateTax = 0.0;
    private static double cityTax = 0.0;
    private static double ficaTax = 0.0;
    private static double totalSpent = 0.0;

    //Data Structures
    public static Object[][] storage;


    //UI elements
    public static JTabbedPane tabbedPane;
    public static JPanel tabPane;
    public static ChartPanel chartpanel;
    public static JPanel handler;
    public static JFrame frame;
    public static JPanel y;
    public static Scene scene;
    public static JFXPanel test;
    public static JPanel cards;
    public static JPanel centerCustimizablePane = new JPanel();
    public static JPanel eastCustimizablePane = new JPanel();
    public static JPanel center;
    public static JPanel eastSide;
    public static JPanel calcPanel;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException  {
        budgettcgui programm = new budgettcgui();
        programm.start();
    }

    public void start() throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException  {
        // can now access non-static fields
        System.out.println("Initializing look and feel");

        storageReader();

        String[] columnNames = { "Name", "Amount Allocated", "Category" };

        // UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        System.out.println("Current Look and Feel: " + UIManager.getLookAndFeel());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame("Budget TC - Create a Budget, Calculate your Savings, and Track your Expenses");

        initializeFrame(1);

    }
    private static PieDataset createDataset() {

        DefaultPieDataset dataset = new DefaultPieDataset( );
        print2D(storage);
        for(Object[] x: storage)
        {
            if(x[1] != null && x[0] != null)
            {
                System.out.println(x[0].toString()+"         --          "+  Double.valueOf(x[1].toString()));
                dataset.setValue(x[0].toString(),  Double.parseDouble(x[1].toString()));
                out.println("Adding category: " + x[0].toString() +" to chart with value of: "+x[1].toString());
            }
        }
        return dataset;
    }

    static void createChart(int what) {
        if(what==1) {
            JFreeChart chart = ChartFactory.createPieChart3D(
                    "",  // chart title
                    createDataset(),         // data
                    true,            // include legend
                    true,
                    false);

            final PiePlot3D plot = (PiePlot3D) chart.getPlot();
            Random rand = new Random();
            int upperbound = 200;
            for(Object x: createDataset().getKeys())
            {
                plot.setSectionPaint((Comparable) x,new Color(upperbound, upperbound, 255));
                if(upperbound > 60)
                    upperbound -= 50;
                else
                    upperbound = 200;
            }

            plot.setSectionOutlinesVisible(true);
            plot.setDefaultSectionOutlinePaint(new Color(0,0,0));
            plot.setStartAngle(270);
            plot.setForegroundAlpha(1.00f);
            plot.setInteriorGap(0.02);
            plot.setCircular(true);
            plot.setOutlineVisible(false);
            plot.setBackgroundAlpha(0);
            //Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
            plot.getChart().setBackgroundPaint(new java.awt.Color(0, 0, 0, 0));
            plot.setLabelBackgroundPaint(Color.DARK_GRAY);
            plot.setLabelPaint(Color.WHITE);
            plot.getChart().getTitle().setPaint(java.awt.Color.WHITE);
            plot.getChart().getTitle().setFont(new java.awt.Font("SansSerif", Font.PLAIN, 30));
            JFreeChart l = plot.getChart();
            ChartPanel u = new ChartPanel(l);
            //u.setBackground(java.awt.Color.black);
            u.setMouseWheelEnabled(true);
            u.getChart().removeLegend();
            chartpanel = u;
        }
        if(what==2) {
            JFreeChart chart = ChartFactory.createRingChart(
                    "",  // chart title
                    createDataset(),         // data
                    true,            // include legend
                    true,
                    false);

            RingPlot plot = (RingPlot) chart.getPlot();
            plot.setSectionPaint("One", new Color(160, 160, 255));
            plot.setSectionPaint("Two", new Color(128, 128, 255 - 32));
            plot.setSectionPaint("Three", new Color(96, 96, 255 - 64));
            plot.setSectionPaint("Four", new Color(64, 64, 255 - 96));
            plot.setSectionPaint("Five", new Color(32, 32, 255 - 128));
            plot.setSectionPaint("Six", new Color(0, 0, 255 - 144));
            //plot.setStartAngle(270);
            plot.setForegroundAlpha(1.00f);
            plot.setInteriorGap(0.02);
            plot.setCircular(true);
            plot.setOutlineVisible(false);
            plot.setBackgroundAlpha(0);
            //Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
            plot.getChart().setBackgroundPaint(new java.awt.Color(0, 0, 0, 0));
            plot.setLabelBackgroundPaint(Color.DARK_GRAY);
            plot.setLabelPaint(Color.WHITE);
            plot.getChart().getTitle().setPaint(java.awt.Color.WHITE);

            plot.setOuterSeparatorExtension(0);
            plot.setInnerSeparatorExtension(0);




            JFreeChart l = plot.getChart();
            ChartPanel u = new ChartPanel(l);
            //u.setBackground(java.awt.Color.black);
            u.setMouseWheelEnabled(true);
            u.getChart().removeLegend();
            chartpanel = u;
            tabbedPane = new JTabbedPane();

            JComponent panel1 = chartpanel;
            ImageIcon icon = new ImageIcon("logo2.png");
            tabbedPane.addTab("Tab 1",icon, panel1,
                    "Does nothing");

        }


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
        else if("Switch-Panel".equals(e.getActionCommand())){
            if(centerCustimizablePane != handler) {
                centerCustimizablePane = handler;
                JPanel jp1 = new JPanel(new GridLayout());
                jp1.add(createTabbedPane());
                eastCustimizablePane= jp1;
            }
            else {
                JPanel jp1 = new JPanel(new GridLayout());
                jp1.add(createTabbedPane());
                centerCustimizablePane= jp1;
                eastCustimizablePane = handler;
            }
            changePanels(0);
            frame.revalidate();
            frame.repaint();

            //out.println("hi---------------------------------------------------------------------------");


        }
    }


    //---------------------------INITIALIZE HELPER METHODS HERE-------------------------------------
    //sets the initial look and feel of the application
    public static void initializeLookAndFeel(LookAndFeel newLookAndFeel)
    {
        out.println("USER ACTION: Look and feel change requested");
        try {
            out.println("USER RESULT: Look and feel changing from " + (UIManager.getLookAndFeel()) + " to " + newLookAndFeel);
            UIManager.setLookAndFeel(newLookAndFeel);
        } catch (Exception ex) {
            System.err.println("ERROR: Failed to initialize look and feel");
        }

        UIManager.put( "Component.focusWidth", 0 );
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public static CreateIncomeTable initializeIncomeTable() {
        String[] incomeColumnNames = {"Name", "Amount", "Category"};
        CreateIncomeTable incomeTable = new CreateIncomeTable(storage,incomeColumnNames);
        incomeTable.setOpaque(true);
        return incomeTable;
    }

    public static CreateTable initializeBudgetTable() {
        String[] columnNames = { "Category", "Amount Allocated", "HI" };
        CreateTable newContentPane = new CreateTable(storage, columnNames);
        newContentPane.setOpaque(true); // content panes must be opaque
        return newContentPane;
    }

    public static CreateSubBudgetTable initializeSubBudgetTable() {
        String[] columnNames = { "Name", "Amount" , "N/a"};
        CreateSubBudgetTable newContentPane = new CreateSubBudgetTable(storage, columnNames);
        newContentPane.setOpaque(true); // content panes must be opaque
        return newContentPane;
    }
    public static CreateExpenseLogTable initializeExpenseTable() {
        String[] expenseColumnNames = { "Expense", "Date Due", "Ammount", "Link" , "Go"};
        CreateExpenseLogTable newExpenseTable = new CreateExpenseLogTable(storage, expenseColumnNames);
        newExpenseTable.setOpaque(true);
        return newExpenseTable;
    }
    public void changePanels(int what){
        Component[] components = y.getComponents();
        out.println(components.length);
        y.remove(1);
        y.remove(1);
        //y.remove(2);

        GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.BOTH;
        d.weightx = .5;
        d.weighty = 1;
        d.gridwidth = 1;
        d.gridx = 1;
        d.gridy = 1;
        y.add(initializeCenter());


        d.fill = GridBagConstraints.BOTH;
        d.weightx = 1;
        d.weighty = 1;
        d.gridwidth = 1;
        d.gridx = 0;
        d.gridy = 1;
        y.add(initializeEastSide());

        y.repaint();
        y.revalidate();
    }

    public void initializeFrame(int what){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeLookAndFeel(new FlatDarkLaf());
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo3.png")).getImage());



        GridBagConstraints d = new GridBagConstraints();

        d.insets = new Insets(0,0,0,0);
        d.fill = GridBagConstraints.HORIZONTAL;
        d.weightx = 0;
        d.weighty = 0;
        d.gridwidth = 0;
        d.gridx = 0;
        d.gridy = 0;
        frame.add(initializeMenuBar(),d);

        y = new JPanel(new GridLayout(0,3));
        // 4. Size the frame.
        d.fill = GridBagConstraints.BOTH;
        d.weightx = .5;
        d.weighty = 1;
        d.gridwidth = 1;
        d.gridx = 0;
        d.gridy = 1;
        y.add(initializeWestSide());

        createChart(1);
        initializeHandler("This is where the handler will go");


            centerCustimizablePane = handler;
            JPanel jp1 = new JPanel(new GridLayout());
            jp1.add(createTabbedPane());
            eastCustimizablePane= jp1;




        d.fill = GridBagConstraints.BOTH;
        d.weightx = .5;
        d.weighty = 1;
        d.gridwidth = 1;
        d.gridx = 1;
        d.gridy = 1;
        y.add(initializeCenter());


        d.fill = GridBagConstraints.BOTH;
        d.weightx = 1;
        d.weighty = 1;
        d.gridwidth = 1;
        d.gridx = 0;
        d.gridy = 1;
        y.add(initializeEastSide());


        frame.add(y,d);
        frame.pack();

        // 5. Show it.
        frame.setVisible(true);
    }

    public static JPanel initializeInfoDiagram() {
        JPanel info = new JPanel();
        info.add(new JButton("This is where the summarized calculations will go!"));
        return info;
    }

    public static void initializeHandler(String s) {
        handler = new JPanel(new GridLayout(4,1));
        JButton x = new JButton(s);
        x.setFocusable(false);
        handler.add(x);
        handler.add(initializeSubBudgetTable());
        JTextArea textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(0,new Color(0x949494),new Color(0x949494)), "Short Description", TitledBorder.LEFT,
                TitledBorder.TOP));;
        textArea.setBackground(new Color(58, 58, 58));
        textArea.setText("Write about what kinds of expenses go into this category ");
        //handler.add(textArea);
        JColorChooser colorChooser = new JColorChooser();
        colorChooser.getChooserPanels().toString();
        for(javax.swing.colorchooser.AbstractColorChooserPanel panel: colorChooser.getChooserPanels());
        out.println("*********************************"+colorChooser.getChooserPanels());
        colorChooser.removeChooserPanel(colorChooser.getChooserPanels()[1]);
        colorChooser.removeChooserPanel(colorChooser.getChooserPanels()[1]);
        colorChooser.removeChooserPanel(colorChooser.getChooserPanels()[1]);
        colorChooser.removeChooserPanel(colorChooser.getChooserPanels()[1]);
        colorChooser.setPreviewPanel(new JPanel());
        //JPanel test = new JPanel(new GridLayout(1,2));
        JPanel pt = new JPanel();
        pt.setLayout(new BorderLayout());
        pt.setBorder(new EmptyBorder(16, 16, 16, 16));
        JButton chooseColor = new JButton("Choose Color");
        pt.add(chooseColor);

        ActionListener okActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("OK Button");
                System.out.println(colorChooser.getColor());
            }
        };

        ActionListener cancelActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Cancel Button");
            }
        };

        chooseColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JColorChooser.createDialog(null,
                        "Pick a color", true,
                        colorChooser,okActionListener, cancelActionListener).setVisible(true);
            }
        });


        //test.add(textArea);
        //test.add(colorChooser);

        handler.add(pt);
        handler.setFocusable(false);
        }

    public static JMenuBar initializeMenuBar(){

        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        //menuBar.add(menu);

//a group of JMenuItems
        menuItem = new JMenuItem("Switch Theme",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menuItem.setActionCommand("SwitchMode");
        menuItem.addActionListener(new budgettcgui());
        //menuBar.add(menuItem);

        JMenuItem menuItem1 = new JMenuItem("End Program",
                KeyEvent.VK_T);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem1.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menuItem1.setActionCommand("end");
        menuItem1.addActionListener(new budgettcgui());
        //menuBar.add(menuItem1);
        JButton tbutton = new JButton("End Program");
        tbutton.setOpaque(false);
        tbutton.setContentAreaFilled(false);
        tbutton.setActionCommand("end");
        tbutton.setBorderPainted(true);
        tbutton.addActionListener(new budgettcgui());
        menuBar.add(tbutton);

        JComboBox themeChooser = new JComboBox<>();
        themeChooser.addItem("Dark");
        themeChooser.addItem("Light");
        themeChooser.addItem("Blue/Orange");
        themeChooser.addItem("Dark/Orange");
        themeChooser.addItem("Atom Dark");
        themeChooser.addItem("Space Gray");
        themeChooser.setPreferredSize(themeChooser.getMinimumSize());
        themeChooser.setMaximumSize(themeChooser.getMinimumSize());
        Map<String, LookAndFeel> lookAndFeelHashMap = new HashMap<String, LookAndFeel>();
        lookAndFeelHashMap.put("Dark", new FlatDarkLaf() );
        lookAndFeelHashMap.put("Light", new FlatLightLaf() );
        lookAndFeelHashMap.put("Blue/Orange", new FlatMoonlightContrastIJTheme());
        lookAndFeelHashMap.put("Dark/Orange", new FlatArcDarkOrangeIJTheme());
        lookAndFeelHashMap.put("Atom Dark", new FlatAtomOneDarkContrastIJTheme());
        lookAndFeelHashMap.put("Space Gray", new FlatArcDarkContrastIJTheme());

        themeChooser.addActionListener(e -> initializeLookAndFeel((lookAndFeelHashMap.get((String) themeChooser.getSelectedItem()))));

        //out.println(UIManager.getLookAndFeel().getDefaults().getFont());

        menu = new JMenu("Preferences");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Choose your app preferences");

        menu.add(tbutton);
        menuBar.add(menu);
        menuBar.add(new JMenu("Import"));
        menuBar.add(new JMenu("Export"));
        menuBar.add(new JMenu("Help"));

        JPanel panel1 = new JPanel(new FlowLayout());
        JLabel label1 = new JLabel("Switch Theme:   ");
        panel1.add(label1);
        panel1.add(themeChooser);
        menuBar.add(Box.createHorizontalGlue());
        themeChooser.setBackground(new Color(255, 255, 255, 11));
        themeChooser.setBorder(BorderFactory.createEmptyBorder());
        menuBar.add(label1);
        menuBar.add(themeChooser);



        return menuBar;
    }

    public static JPanel initializeWestSide() {
        JPanel westSide = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.weightx = 1;
        c.weighty = .3;
        c.gridwidth = 0  ;
        c.gridx = 0;
        c.gridy = 0;

        westSide.add(initializeIncomeTable(),c);

        c.weighty = .45;
        c.gridy = 1;
        westSide.add(initializeBudgetTable(),c);



        c.weighty = .25;
        c.gridy = 2;
        westSide.add(initializeExpenseTable(),c);
        return westSide;
    }
    public JPanel initializeCenter() {
        //if(center== null) {
            center = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1;
            c.weighty = .3;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 0;
            //out.println(c.ipady+"-------------------------");
            center.add(new JButton("This is where the summarized calculations will go!"), c);

            JButton switchPanel = new JButton("<|>");
            switchPanel.setActionCommand("Switch-Panel");
            switchPanel.addActionListener(new budgettcgui());
            c.weighty = .005;
            c.gridx = 0;
            c.gridy = 1;
            center.add(switchPanel, c);

            GridBagConstraints d = new GridBagConstraints();
            d.fill = GridBagConstraints.BOTH;
            d.weightx = 1;
            d.weighty = .6;
            d.gridwidth = 1;
            d.gridx = 0;
            d.gridy = 2;
            d.insets = new Insets(10, 0, 0, 0);

            center.add(centerCustimizablePane, d);
            center.setFocusable(false);
        //}
        /*if(center != null) {
            center = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1;
            c.weighty = .3;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 0;
            //out.println(c.ipady+"-------------------------");
            center.add(new JButton("This is where the summarized calculations will go!"), c);

            JButton switchPanel = new JButton("<|>");
            switchPanel.setActionCommand("Switch-Panel");
            switchPanel.addActionListener(new budgettcgui());
            c.weighty = .005;
            c.gridx = 0;
            c.gridy = 1;
            center.add(switchPanel, c);

            GridBagConstraints d = new GridBagConstraints();
            d.fill = GridBagConstraints.BOTH;
            d.weightx = 1;
            d.weighty = .6;
            d.gridwidth = 1;
            d.gridx = 0;
            d.gridy = 2;
            d.insets = new Insets(10, 0, 0, 0);

            center.add(centerCustimizablePane, d);
            center.setFocusable(false);
        }*/
        return center;
    }
    public JPanel initializeEastSide() {


        if(eastSide == null) {
            eastSide = new JPanel(new GridLayout(2, 1));
            eastCustimizablePane.setFocusable(false);
            eastSide.add(eastCustimizablePane);
            calcPanel = initializeCalculatorPanel();
            eastSide.add(calcPanel);
        }
        else if(eastSide!=null)
        {
            eastSide = new JPanel(new GridLayout(2, 1));
            eastCustimizablePane.setFocusable(false);
            eastSide.add(eastCustimizablePane);
            eastSide.add(calcPanel);
        }
        eastSide.setFocusable(false);
        return eastSide;
    }
    public JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = new ImageIcon("logo2.png");
        createChart(1);
        JComponent panel1 = chartpanel;
        tabbedPane.addTab("3D Chart", icon, panel1,
                "Does nothing");

        createChart(2);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = new JButton();
        tabbedPane.addTab("Flat Chart", icon, chartpanel,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = new JButton();
        tabbedPane.addTab("Tab 3", icon, panel3,
                "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JComponent panel4 =new JButton();
        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Tab 4", icon, panel4,
                "Does nothing at all");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        return tabbedPane;
    }



    // reads data from the file and adds it to the map which hold the data. Returns false if there is no data.
    //todo rework storage system to adjust for if there is no storage file present
    public static boolean storageReader() throws IOException {

        try {
            FileReader fReader = new FileReader("storage1.txt");
            BufferedReader reader = new BufferedReader(fReader);
            int colsize = Integer.parseInt(reader.readLine());
            int rowsize = Integer.parseInt(reader.readLine());
            storage = new Object[colsize][rowsize];

            netMonthlyIncome = Double.parseDouble(reader.readLine());

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
            return x;
        }
        catch(Exception exception)
        {}
        return true;
    }

    // prints out a matrix into the console into a readable string, Used for testing
    // purposes and does not exist in the build
    public static void print2D(Object mat[][]) {
        // Loop through all rows
        for (Object[] row : mat)

            // converting each row as string and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }

    //writes all of the data into a txt file so the user can access it next time.
    //todo ensure organized creation or use of storage file
    public static void storageWriter() throws IOException
    {
        System.out.println(storage.length);
        FileWriter fWriter = new FileWriter(new File("storage.txt"), false);
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
/*
    //creates pie chart based on data in the storage matrix
    public static AnchorPane createChart(){
        pieChart = new PieChart();

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


        AnchorPane pane = new AnchorPane (pieChart);
        AnchorPane.setTopAnchor (pieChart, 0.0);
        AnchorPane.setBottomAnchor (pieChart, 0.0);
        AnchorPane.setLeftAnchor (pieChart, 0.0);
        AnchorPane.setRightAnchor (pieChart, 0.0);

        scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("color.css");
        test.setScene(scene);
        return pane;


    }
*/

    public JPanel initializeCalculatorPanel()
    {
        JPanel calcPane = new JPanel();
        calcPane.setLayout(new BorderLayout());
        /*//calcPane.add(new JButton("NORTH"),BorderLayout.NORTH);
        calcPane.add(new JButton("SOUTH"),BorderLayout.SOUTH);
        calcPane.add(new JButton("EAST"),BorderLayout.EAST);
        calcPane.add(new JButton("WEST"),BorderLayout.WEST);
        calcPane.add(new JButton("CENTER"),BorderLayout.CENTER);
        JMenuBar whichCalc = new JMenuBar();
        JButton button = new JButton("Switch Calc");
        button.addActionListener(e -> out.println("hi"));
        whichCalc.add(button);
        calcPane.add(whichCalc,BorderLayout.NORTH);
        return calcPane;*/

        //JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String BUTTONPANEL = "buttons";
        String TEXTPANEL = "text";
        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setFocusable(false);
        cb.setEditable(false);
        cb.addItemListener(this);
        //comboBoxPane.add(cb);
        JMenuBar menu = new JMenuBar();
        menu.add(cb);
        //menu.add(new JButton("hello"));

        JPanel card1 = new JPanel();
        card1.add(new JButton("SOUTH"),BorderLayout.SOUTH);
        card1.add(new JButton("EAST"),BorderLayout.EAST);
        card1.add(new JButton("WEST"),BorderLayout.WEST);
        card1.add(new JButton("CENTER"),BorderLayout.CENTER);

        JPanel card2 = new JPanel();
        card2.setLayout(new BorderLayout());
        card2.add(new JButton("SOUTH"),BorderLayout.SOUTH);
        card2.add(new JButton("EAST"),BorderLayout.EAST);
        card2.add(new JButton("WEST"),BorderLayout.WEST);
        //card2.add(new JButton("CENTER"),BorderLayout.CENTER);

        String[] labels = {"City: ", "State: ", "Married or Single: ", "Income: "};
        int numPairs = labels.length;

//Create and populate the panel.
        JPanel s = new JPanel(new GridLayout(3,3));
        JPanel p = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l);
            JComboBox textField = new JComboBox();
            textField.addItem("Married");
            textField.addItem("Single");
            l.setLabelFor(textField);
            p.add(textField);
        }
        s.add(p);
        SpringUtilities.makeCompactGrid(p,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        card2.add(s,BorderLayout.CENTER);


        cards = new JPanel(new CardLayout());
        cards.add(card1, BUTTONPANEL);
        cards.add(card2, TEXTPANEL);

        calcPane.add(menu, BorderLayout.PAGE_START);
        calcPane.add(cards, BorderLayout.CENTER);
        calcPane.setFocusable(false);


//Method came from the ItemListener class implementation,
//contains functionality to process the combo box item selecting

return calcPane;
    }
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
    //------------------------METHODS and FEATURES TO IMPLEMENT OR ADD------------------------------------
    //Methods may not be fit or applicable for the program.

    //todo add calculator panel that allows for the display of multiple different calculators with duality on east side
    //todo tax calculator to tax calc panel
    public static void initializeTaxCalcPanel()
    {

    }

    //todo add application header with logo
    public static void initializeHeader()
    {

    }


   //deletes all the data in the budget
   public static void deleteData() throws IOException
   {

   }


    //todo ad requirement calculator to calculator panel
   public static void retirementCalc()
   {
      /*System.out.print(
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
      */
   }

   //todo add ability for user to generate a pdf/file with a summary of budget and transaction data
    public static void budgetSummary()
   {
       /*
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
      */
   }

}