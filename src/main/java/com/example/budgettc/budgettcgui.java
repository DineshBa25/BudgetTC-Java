package com.example.budgettc;

//import java tools
import static java.lang.System.out;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import javax.print.attribute.Attribute;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import javax.swing.text.html.StyleSheet;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

//Import Flatlaf look and feel
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
//import org.jfree.chart.renderer.xy.XYAreaRenderer2;
import org.jfree.chart.renderer.xy.XYAreaRenderer2;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.data.general.DefaultPieDataset;
//mport org.jfree.data.xy.DefaultTableXYDataset;
//import org.jfree.data.xy.XYSeries;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

import net.miginfocom.swing.MigLayout;


public class budgettcgui extends JFrame implements ActionListener {

    //Number -> String Formatters
    public static NumberFormat formatter = NumberFormat.getInstance();
    public static DecimalFormat decimalFormatter = new DecimalFormat("#.##");

    //Data Structures
    public static List<BudgetCategory> storageMatrix; //A universal list of all the Budget Category's
    public static List<IncomeCategory> incomeList; //A universal list of all the Income Category's
    public static List<ExpenseCategory> expenseList = new ArrayList<ExpenseCategory>(); //A Universal list of all the Expense Category's

    //UI elements
    public static JTabbedPane tabbedPane; //tabbedPane Holds all the Graphical Charts (Ex: Pie Chart, Donut Chart)
    public static JPanel handler; //Panel that holds the handler for each budget category

    public static JFrame frame; //Overall frame that houes all UI Elements
    public static JPanel y; //Houses both the center Customizable Pane and the East Customizable Pane
    public static JComponent centerCustimizablePane = new JPanel(); //Houses center section pane that is easily customizable
    public static JComponent eastCustimizablePane = new JPanel(); //Houses east side pane that is easily customizable
    public static JTabbedPane subHandlerTabbedPane;
    public static JPanel center;  //Houses the center section of UI
    public static JPanel eastSide; //Houses the east side of UI


    public static JPanel calcPanel; //Houses the Calculator Panel
    public static JPanel cards; //Holds the cards for the Calculator Panel

    //Unassigned
    public static boolean DEBUG = false;
    public static CreateExpenseLogTable newExpenseTable;
     public static SubBudgetExpenseLogTable subBudgetExpenseTable;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        budgettcgui programm = new budgettcgui();

        if(DEBUG)
            System.out.println("Initializing look and feel");
        //File file = new File(budgettcgui.class.getResource("Contrast.theme.json").getFile());
       IntelliJTheme.setup( budgettcgui.class.getResourceAsStream("/Contrast.theme.json"));
        //System.out.print(budgettcgui.class.getResourceAsStream("/Contrast.theme.json"));
        subHandlerTabbedPane =  new JTabbedPane(JTabbedPane.TOP);

        subBudgetExpenseTable = new SubBudgetExpenseLogTable();

        subHandlerTabbedPane.addTab("Allocated Expenses" , new JScrollPane(subBudgetExpenseTable.returnPane()));
        subHandlerTabbedPane.addTab("Edit Description", new JTextPane());
        subHandlerTabbedPane.addTab("Category Properties", new JTextPane());
       programm.start();

    }

    public void start() throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {

        //Read from storage File
        storageReaderNG();

        InputStream file1 =  budgettcgui.class.getResourceAsStream("/Checking1.csv");
        importExpenseFromFile(file1);

        //Init the Frame
        initializeFrame(1);

    }

    //todo move create chart into its own class
   private static DefaultPieDataset createDataset() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int x = 0; x < storageMatrix.size(); x++) {
            if (storageMatrix.get(x).getCategoryName() != null && storageMatrix.get(x).getAmountAllocated() != null) {
                //System.out.println(x[0].toString()+"         --          "+  Double.valueOf(x[1].toString()));
                dataset.setValue(storageMatrix.get(x).getCategoryName(), storageMatrix.get(x).getAmountAllocated());
                //out.println("Adding category: " + x[0].toString() +" to chart with value of: "+x[1].toString());
            }
        }
        return dataset;
    }

    static JPanel createChart(int what) {
        if (what == 1) {
            JFreeChart chart = ChartFactory.createPieChart3D(
                    "",  // chart title
                    createDataset(),         // data
                    true,            // include legend
                    true,
                    false);

            final PiePlot3D plot = (PiePlot3D) chart.getPlot();
            Random rand = new Random();
            int upperbound = 200;
            for (Object x : createDataset().getKeys()) {
                plot.setSectionPaint((Comparable) x, new Color(upperbound, upperbound, 255));
                if (upperbound > 60)
                    upperbound -= 50;
                else
                    upperbound = 200;
            }

            plot.setSectionOutlinesVisible(true);
            plot.setDefaultSectionOutlinePaint(new Color(0, 0, 0));
            plot.setStartAngle(270);
            plot.setForegroundAlpha(1.00f);
            plot.setInteriorGap(0.02);
            plot.setCircular(true);
            plot.setOutlineVisible(false);
            plot.setBackgroundAlpha(0);
            plot.getChart().setBackgroundPaint(new java.awt.Color(0, 0, 0, 0));
            plot.setLabelBackgroundPaint(Color.DARK_GRAY);
            plot.setLabelPaint(Color.WHITE);
            plot.getChart().getTitle().setPaint(java.awt.Color.WHITE);
            plot.getChart().getTitle().setFont(new java.awt.Font("SansSerif", Font.PLAIN, 30));
            JFreeChart l = plot.getChart();
            ChartPanel u = new ChartPanel(l);
            u.setMouseWheelEnabled(true);
            u.getChart().removeLegend();
            return u;
        }
        if (what == 2) {
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
            return u;

        }
    return null;
    }

    public void actionPerformed(ActionEvent e) {
        if ("end".equals(e.getActionCommand())) {
            try {
                out.println("USER ACTION: Program end requested");
                storageWriterNG();
                out.println("ACTION RESULT: Program ended succesfully!");
            }
            finally{}
            //todo replace with catch instead of finally once StorageWriterNG() in finished
                //catch (java.io.IOException x) {
               // out.println("ACTION RESULT: Program ended UNsuccesfully!\ncreateDirectory failed:" + x);
            //}
            System.exit(0);
        } else if ("Switch-Panel".equals(e.getActionCommand())) {
            if (centerCustimizablePane != handler) {
                centerCustimizablePane = handler;
                eastCustimizablePane = tabbedPane;

            } else {
                centerCustimizablePane = tabbedPane;
                eastCustimizablePane = handler;
            }
            changePanels(false);
            frame.revalidate();
            frame.repaint();


        } else if ("Add Income Category".equals(e.getActionCommand())) {
            try {
                out.println("USER ACTION: Button pressed");
                budgettcgui.initializeHandler(IncomeCategory.addNewIncome("",0.0, null));

            } finally {
            }
        }
        else if ("Add Budget Category".equals(e.getActionCommand())) {
            try {
                out.println("USER ACTION: Button pressed");
                budgettcgui.initializeHandler(BudgetCategory.addNewCategory());

            } finally {
            }
        }
    }


    //---------------------------INITIALIZE HELPER METHODS HERE-------------------------------------
    //sets the initial look and feel of the application
    public static void initializeLookAndFeel(LookAndFeel newLookAndFeel) {
        if(DEBUG)
            out.println("USER ACTION: Look and feel change requested");
        try {
            FlatLaf.registerCustomDefaultsSource("com.myapp.themes");
            FlatDarkLaf.setup();
            if(DEBUG)
                out.println("USER RESULT: Look and feel changing from " + (UIManager.getLookAndFeel()) + " to " + newLookAndFeel);
            UIManager.setLookAndFeel(newLookAndFeel);
        } catch (Exception ex) {
            System.err.println("ERROR: Failed to initialize look and feel");
        }

        UIManager.put("Component.focusWidth", 0);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public static CreateIncomeTable initializeIncomeTable() {
        String[] columnNames = {"Income Name", "Type", "Amount"};
        Object[][] temp = new Object[incomeList.size()][3];
        for (int x = 0; x < incomeList.size(); x++) {
            temp[x][0] = incomeList.get(x).getIncomeName();
            temp[x][1] = incomeList.get(x).getIncomeAmount();
            temp[x][2] = incomeList.get(x).getIncomeType();
        }

        CreateIncomeTable incomeTable = new CreateIncomeTable(temp, columnNames);
        incomeTable.setOpaque(true);
        return incomeTable;
    }

    public static CreateTable initializeBudgetTable() {
        String[] columnNames = {"Budget Category", "Amount Allocated", "HI"};
        Object[][] temp = new Object[storageMatrix.size()][2];
        for (int x = 0; x < storageMatrix.size(); x++) {
            temp[x][0] = storageMatrix.get(x).getCategoryName();
            temp[x][1] = storageMatrix.get(x).getAmountAllocated();
        }
        CreateTable newContentPane = new CreateTable(temp, columnNames);
        newContentPane.setOpaque(true); // content panes must be opaque
        return newContentPane;
    }


    public static CreateExpenseLogTable initializeExpenseTable() {

        Object[][] temp = new Object[expenseList.size()][3];
        for (int x = 0; x < expenseList.size(); x++) {
            temp[x][0] = expenseList.get(x).getExpenseName();
            temp[x][1] = expenseList.get(x).getExpenseAmount();
            temp[x][2] = expenseList.get(x);
        }
        newExpenseTable = new CreateExpenseLogTable(temp);
        newExpenseTable.setOpaque(true);
        return newExpenseTable;
    }

    //todo rework so that all the panels dont have to be reloaded, only the ones that really need to be.
    public static void changePanels(boolean alsoWestSide) {
        Component[] components = y.getComponents();
        if(DEBUG)
            out.println(components.length);

        if(alsoWestSide){
        y.remove(0);
        y.remove(0);
        y.remove(0);}
        else{
            y.remove(1);
            y.remove(1);

        }
        //y.remove(2);

        GridBagConstraints d = new GridBagConstraints();
        if(alsoWestSide) {
            d.fill = GridBagConstraints.BOTH;
            d.weightx = .5;
            d.weighty = 1;
            d.gridwidth = 1;
            d.gridx = 0;
            d.gridy = 1;
            y.add(initializeWestSide());
        }


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

    public void initializeFrame(int what) {

        frame = new JFrame("Budget TC - Create a Budget, Calculate your Savings, and Track your Expenses");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo3.png")).getImage());


        GridBagConstraints d = new GridBagConstraints();

        d.insets = new Insets(0, 0, 0, 0);
        d.fill = GridBagConstraints.HORIZONTAL;
        d.weightx = 0;
        d.weighty = 0;
        d.gridwidth = 0;
        d.gridx = 0;
        d.gridy = 0;
        frame.add(initializeMenuBar(), d);

        y = new JPanel(new GridLayout(0, 3));
        // 4. Size the frame.
        d.fill = GridBagConstraints.BOTH;
        d.weightx = .5;
        d.weighty = 1;
        d.gridwidth = 1;
        d.gridx = 0;
        d.gridy = 1;
        initializeExpenseTable();
        y.add(initializeWestSide());

        createChart(1);
        createChart(2);
        initializeHandler(null);
        createTabbedPane();

        centerCustimizablePane = handler;
        eastCustimizablePane = tabbedPane;



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


        frame.add(y, d);
        frame.pack();

        // 5. Show it.
        frame.setVisible(true);

        if(DEBUG)
            System.out.println("Current Look and Feel: " + UIManager.getLookAndFeel());
    }

    public static JPanel initializeInfoDiagram() {
        JPanel info = new JPanel(new GridLayout(1,3));

        info.add(new IncomeInfoRingChart(2000.0,IncomeCategory.getTotalIncome()));
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        /*textPane.setText(" This is where the summarized calculations will go!: \n" +
                "\nTotal Amount Budgeted: " + BudgetCategory.getTotalBudgetAmount() +
                "\nIncome Amount: $ " + IncomeCategory.getTotalIncome());
        textPane.setEditable(false);*/
        //info.add(textPane);


        StyledDocument document = textPane.getStyledDocument ();

        Style defaultStyle = StyleContext.getDefaultStyleContext ().getStyle (StyleContext.DEFAULT_STYLE);
        Style regular = document.addStyle ("regular", defaultStyle);
        StyleConstants.setFontFamily (regular, "Corbert");
        StyleConstants.setFontSize (regular, 14);

        Style redBold = document.addStyle ("redBold", defaultStyle);
        StyleConstants.setFontFamily (redBold, "Corbert");
        StyleConstants.setFontSize (redBold, 20);
        StyleConstants.setForeground(redBold,Color.red);
        StyleConstants.setBold(redBold,true);
        StyleConstants.setAlignment(redBold,StyleConstants.ALIGN_CENTER);

        Style blueBold = document.addStyle ("blueBold", defaultStyle);
        StyleConstants.setFontFamily (blueBold, "Corbert");
        StyleConstants.setFontSize (blueBold, 20);
        StyleConstants.setForeground(blueBold, new Color(1, 73, 124));
        StyleConstants.setBold(blueBold,true);
        StyleConstants.setAlignment(blueBold,StyleConstants.ALIGN_CENTER);

        Style greenBold = document.addStyle ("greenBold", defaultStyle);
        StyleConstants.setFontFamily (greenBold, "Corbert");
        StyleConstants.setFontSize (greenBold, 20);
        StyleConstants.setForeground(greenBold,  new Color(40, 115, 0));
        StyleConstants.setBold(greenBold,true);
        StyleConstants.setAlignment(greenBold,StyleConstants.ALIGN_CENTER);
        try {
            document.insertString(document.getLength(), "Total Amount Budgeted:\n", regular);
            document.insertString(document.getLength(),"$"+formatter.format(Double.parseDouble(budgettcgui.decimalFormatter.format(BudgetCategory.getTotalBudgetAmount())))+"\n",blueBold);
            document.insertString(document.getLength(), "Total Income Budgeted:\n", regular);
            document.insertString(document.getLength(),"$"+formatter.format(Double.parseDouble(budgettcgui.decimalFormatter.format(IncomeCategory.getTotalIncome())))+"\n",greenBold);
            document.insertString(document.getLength(), "Total Expenses Allocated:\n", regular);
            document.insertString(document.getLength(),"$"+formatter.format(Double.parseDouble(budgettcgui.decimalFormatter.format(-ExpenseCategory.totalAllocatedExpenseAmount)))+"\n",redBold);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        info.add(textPane);

        info.add(new BudgetInfoRingChart(8700.0,BudgetCategory.getTotalBudgetAmount()));
        return info;
    }

    public static void initializeHandler(JPanel s) {
        if (handler == null) {
            JPanel temp = new JPanel(new GridLayout(1, 1));
            temp.add(new JButton("Welcome to BudgetTC"));
            handler = temp;
        }
        else {


           // System.out.println(centerCustimizablePane.+"-----------------------------0-----------------------");
            if(centerCustimizablePane == handler) {
                handler = s;
                centerCustimizablePane = handler;

            }else{
                handler = s;
                 eastCustimizablePane = handler;}

            changePanels(false);
            frame.revalidate();
            frame.repaint();
        }
        //out.println("hi---------------------------------------------------------------------------");


        handler.repaint();
        handler.setFocusable(false);
    }

    public static JMenuBar initializeMenuBar() {

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
        //lookAndFeelHashMap.put("Dark", new FlatDarkLaf());
        lookAndFeelHashMap.put("Light", new FlatMaterialDarkerContrastIJTheme());
        lookAndFeelHashMap.put("Blue/Orange", new FlatMoonlightContrastIJTheme());
        lookAndFeelHashMap.put("Dark/Orange", new FlatArcDarkOrangeIJTheme());
        lookAndFeelHashMap.put("Atom Dark", new FlatAtomOneDarkContrastIJTheme());
        lookAndFeelHashMap.put("Space Gray", new FlatDarkFlatIJTheme());

        // themeChooser.addActionListener(e -> initializeLookAndFeel((lookAndFeelHashMap.get((String) themeChooser.getSelectedItem()))));


        //out.println(UIManager.getLookAndFeel().getDefaults().getFont());

        menu = new JMenu("Preferences");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "Choose your app preferences");

        menu.add(tbutton);
        menuBar.add(menu);


        JMenu importMenu = new JMenu("Import");
        importMenu.add(new JMenuItem("Import Expenses from .CSV"));
        importMenu.add(new JMenuItem("Import Expenses from .OFX"));
        importMenu.add(new JMenuItem("Setup Bank Information"));
        menuBar.add(importMenu);
        menuBar.add(new JMenu("Export"));
        menuBar.add(new JMenu("Help"));

        JPanel panel1 = new JPanel(new FlowLayout());
        JLabel label1 = new JLabel("Switch Theme:   ");
        panel1.add(label1);
        panel1.add(themeChooser);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(Box.createHorizontalGlue());
        //menuBar.add(new JMenuItem("Application Log"));
        menuBar.add(Box.createHorizontalGlue());
        themeChooser.setBackground(new Color(255, 255, 255, 11));
        themeChooser.setBorder(BorderFactory.createEmptyBorder());
        //menuBar.add(label1);
       // menuBar.add(themeChooser);
        menuBar.add(new ClockPane());

        return menuBar;
    }

    public static JSplitPane initializeWestSide() {
        JPanel westSide = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        //Will hold the Income table and the budget table.
        JSplitPane topSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        //Will hold the topSplitPane and the expense table to that all the splitpanes combined will allows for 3 sections for each table.
        JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


        //income table weight = .30%
        topSplitPane.setTopComponent(initializeIncomeTable());

        //budget table weight = 40%
        topSplitPane.setBottomComponent(initializeBudgetTable());

        //expense table weight = 30%

        bottomSplitPane.setTopComponent(topSplitPane);
        bottomSplitPane.setBottomComponent(newExpenseTable);
        topSplitPane.setResizeWeight(0.40);
        bottomSplitPane.setResizeWeight(0.65);

        return bottomSplitPane;
    }

    public static JPanel initializeHeader(){
        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.add(initializeInfoDiagram(), BorderLayout.CENTER);

        JButton switchPanel = new JButton("<|>");
        switchPanel.setActionCommand("Switch-Panel");
        switchPanel.addActionListener(new budgettcgui());

        headerPanel.add(switchPanel, BorderLayout.SOUTH);
        return headerPanel;
    }

    public static JPanel initializeCenter() {

       // center = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        center = new JPanel(new MigLayout());
        //center.setTopComponent(initializeHeader());
        center.add(initializeHeader()," h 25%!, wrap");
        //center.setBottomComponent(centerCustimizablePane);
        //center.setResizeWeight(0.25f);
        //center.setDividerLocation(0.25f);
        center.add(centerCustimizablePane,"h 47%!, w 98.5%!, wrap");

        center.add(subHandlerTabbedPane,"h 33%!, w 98.5%!");
        //center.set


        return center;
    }

    public static JPanel initializeEastSide() {


        if (eastSide == null) {
            eastSide = new JPanel(new GridLayout(2, 1));
            eastCustimizablePane.setFocusable(false);
            eastSide.add(eastCustimizablePane);
            calcPanel = initializeCalculatorPanel();
            eastSide.add(calcPanel);
        } else if (eastSide != null) {
            eastSide = new JPanel(new GridLayout(2, 1));
            eastCustimizablePane.setFocusable(false);
            eastSide.add(eastCustimizablePane);
            eastSide.add(calcPanel);
        }
        eastSide.setFocusable(false);
        return eastSide;
    }
    public static JTabbedPane createTabbedPane() {
        tabbedPane = new JTabbedPane();
        ImageIcon icon = new ImageIcon("logo2.png");
        BudgetPieChart panel1 = new BudgetPieChart();
        tabbedPane.addTab("Budget Chart", icon, panel1.getBudgetChartPanel(),
                "Shows a graphical display of this months budget");

        createChart(2);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        //JComponent panel2 = createChart(2);
        IncomePieChart panel2 = new IncomePieChart();
        tabbedPane.addTab("Income Chart", icon, panel2.getIncomeChartPanel(),
                "Shows a graphical display of this months income");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        //Add the tabbed pane to this panel.
        //tabbedPane.add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        return tabbedPane;
    }

    public static void storageReaderNG() throws IOException, UnsupportedLookAndFeelException {

        //try {
            //File file = new File("/storage3.txt");
            InputStream x = budgettcgui.class.getResourceAsStream("/storage3.txt");
            System.out.print(x);
            Scanner sc = new Scanner(x);
            storageMatrix = new ArrayList<BudgetCategory>();
            incomeList = new ArrayList<IncomeCategory>();
            String line;
            String Name = "";
            Double amtAllocatedForCategory = 0.00;
            Map<String, Double> tempMap = null;

            boolean temp = false;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                //out.println(line);
                if (line.contains("~") == false && !temp)
                    incomeList.add(new IncomeCategory(line, sc.nextLine(), Double.valueOf(sc.nextLine())));
                if (line.contains("~") == true) {
                    temp = true;
                    line = sc.nextLine();
                }
                if (line.contains("*") == true && temp) {
                    //out.println("hiii");
                    if (tempMap != null)
                        storageMatrix.add(new BudgetCategory(Name, amtAllocatedForCategory, tempMap));
                    tempMap = new TreeMap<String, Double>();
                    Name = line.replace("*", "");
                    amtAllocatedForCategory = Double.parseDouble(sc.nextLine());

                }
                if (line.contains("*") == false && temp) {
                    String yint = line;
                    Double xint = Double.parseDouble(sc.nextLine());
                    tempMap.put(yint, xint);
                }
            }
            sc.close();
       // } catch (Exception exception) {
        //}

    }

    //writes all of the data into a txt file so the user can access it next time.
    //todo ensure organized creation or use of storage file
    public static void storageWriterNG(){
        /*/*public static void storageWriter() throws IOException {
        //System.out.println(storage.length);
        FileWriter fWriter = new FileWriter(new File("storage.txt"), false);
        BufferedWriter writer = new BufferedWriter(fWriter);
        //Set<String> set1 = storage.keySet();
        writer.write(String.valueOf(storage.length));
        writer.newLine();
        writer.write(String.valueOf(storage[0].length));
        writer.newLine();
        writer.newLine();
        for (int x = 0; x < storage.length - 1; x++) {
            writer.write(String.valueOf(storage[x][0]));
            writer.newLine();
            writer.write(String.valueOf(storage[x][1]));
            writer.newLine();
            writer.write(String.valueOf(storage[x][2]));
            writer.newLine();
        }
        writer.close();
    }*/
    }


    public static JPanel initializeCalculatorPanel() {
        JPanel calcPane = new JPanel();
        calcPane.setLayout(new BorderLayout());


        String r401kCalculator = "Roth 401K Retirement Calculator";
        String loanPaymentCalc = "Loan Payment Calculator";
        String comboBoxItems[] = {r401kCalculator, loanPaymentCalc};
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setFocusable(false);
        cb.setEditable(false);
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, (String) e.getItem());
            }
        });
        JMenuBar menu = new JMenuBar();
        menu.add(cb);

        JPanel card1 = new JPanel();

        JPanel card2 = new JPanel();


        card1.setLayout(new GridBagLayout());


        JTextField currentAgeField = new JTextField(10);
        JTextField finalAgeField = new JTextField(10);
        JTextField amtSavedField = new JTextField(10);
        JTextField returnPercent = new JTextField(10);
        JTextField inflation = new JTextField(10);
        JSlider contributionSlider = new JSlider();
        contributionSlider.setMinimum(0);
        contributionSlider.setMaximum(1600);
        contributionSlider.setPaintTicks(true);
        contributionSlider.setMajorTickSpacing(800);
        contributionSlider.setMinorTickSpacing(100);
        contributionSlider.setPaintLabels(true);

        JPanel TEMP = new JPanel();
        TEMP.setBackground(new Color(0x4F4F4F));

        JButton okButton = new JButton("Enter");
        ActionListener okActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("OK Button Pressed, generating retirement Area chart");
                if (card1.getComponents().length > 1)
                    card1.remove(1);
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.BOTH;
                c.weightx = 1;
                c.weighty = 6;
                c.gridwidth = 1;
                c.gridx = 0;
                c.gridy = 1;
                boolean test = true;
                if(!test){
                card1.add(retirementCalc(Integer.parseInt(currentAgeField.getText())
                        , Integer.parseInt(finalAgeField.getText())
                        , Double.parseDouble(amtSavedField.getText())
                        , Double.parseDouble(returnPercent.getText())
                        , contributionSlider.getValue()
                        , Double.parseDouble(inflation.getText())),c);}
                else{
                card1.add(retirementCalc(22
                        , 60
                        , 1000.00
                        , 0.09
                        , 700.00
                        , .025), c);}
                card1.revalidate();
                card1.repaint();
                frame.repaint();
            }
        };
        okButton.addActionListener(okActionListener);

        JComponent[] components = {
                currentAgeField,
                finalAgeField,
                amtSavedField,
                contributionSlider,
                returnPercent,
                inflation,
                okButton

        };

        JLabel[] labels = {
                new JLabel("Your current age: "),
                new JLabel("The age you plan to retire: "),
                new JLabel("The Amount you have saved so far: "),
                new JLabel("The amount you plan to contribute:"),
                new JLabel("Expected rate of return (%): "),
                new JLabel("Expected Average inflation rate: "),
                new JLabel(""),
        };

        JPanel columnLayout = new JPanel();
        columnLayout.add(getTwoColumnLayout(labels, components), BorderLayout.CENTER);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = .7;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        card1.add(columnLayout, c);


        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 12;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        card1.add(TEMP, c);

        cards = new JPanel(new CardLayout());
        cards.add(card1, r401kCalculator);
        cards.add(card2, loanPaymentCalc);

        calcPane.add(menu, BorderLayout.PAGE_START);
        calcPane.add(cards, BorderLayout.CENTER);
        calcPane.setFocusable(false);

        return calcPane;
    }

    public void itemStateChanged(ItemEvent evt) {

    }


    public static JPanel retirementCalc(int currentAge, int retirementAge, double currentSav, double returnOnInvestment, double monthlyContribution, double inflation) {
        double total = currentSav;
        double itotal = currentSav;

        ArrayList<Double[]> uninflatedDataList = new ArrayList<Double[]>();
        ArrayList<Double[]> inflatedDataList = new ArrayList<Double[]>();

        JPanel dataPanel = new JPanel();
        int cnt = currentAge;
        for (int x = 0; x < (retirementAge - currentAge); x++) {

            total = (total * (1 + returnOnInvestment)) + ((monthlyContribution) * 12);
            Double[] temp = {Double.valueOf(cnt), total};
            uninflatedDataList.add(temp);
            cnt++;
        }
        cnt = currentAge;
        for (int x = 0; x < (retirementAge - currentAge); x++) {

            itotal = ((itotal * (1 + returnOnInvestment)) + ((monthlyContribution) * 12)) * (1 - inflation);
            Double[] temp = {Double.valueOf(cnt), itotal};
            inflatedDataList.add(temp);
            cnt++;
        }

        /*for (int x = 0; x < inflatedDataList.size(); x++) {
            out.println(inflatedDataList.get(x)[0] + ", " + inflatedDataList.get(x)[1]);
        }*/

        JPanel chartPanel = createAreaChart(createXYTableDataset(uninflatedDataList, inflatedDataList));
        dataPanel.add(chartPanel);
        return chartPanel;
    }


    private static DefaultTableXYDataset createXYTableDataset(List<Double[]> data, List<Double[]> idata) {

        DefaultTableXYDataset dataset = new DefaultTableXYDataset();

        XYSeries s1 = new XYSeries("Series 1", true, false);
        for (int x = 0; x < data.size(); x++)
            //dataset.addValue(data.get(x)[1].intValue(),"Without Inflation",(data.get(x)[0]).toString());
            s1.add(data.get(x)[0], data.get(x)[1]);
        XYSeries s2 = new XYSeries("Series 2", true, false);
        for (int x = 0; x < idata.size(); x++)
            //dataset.addValue(idata.get(x)[1].intValue(),"With Inflation",(idata.get(x)[0]).toString());
            s2.add(idata.get(x)[0], idata.get(x)[1]);
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        return dataset;

    }

    private static ChartPanel createAreaChart(DefaultTableXYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYAreaChart(
                "Area Chart Demo 1",  // chart title
                "Years",                       // domain axis label
                "Dollars",                       // range axis label
                dataset,                         // data
                PlotOrientation.VERTICAL,        // the plot orientation
                false,                            // legend
                true,                            // tooltips
                false                            // urls
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        //AreaPolot
        XYAreaRenderer2 renderer = new XYAreaRenderer2();
        renderer.setSeriesPaint(0, new Color(255, 0, 0, 160));
        renderer.setSeriesPaint(1, new Color(44, 243, 0, 160));
        renderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());

        plot.setRenderer(0, renderer);
        plot.setRenderer(1, renderer);

        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.getChart().setTitle("");
        plot.getChart().setBorderVisible(false);
        plot.getChart().setBackgroundPaint(new java.awt.Color(0, 0, 0, 0));
        plot.getDomainAxis().setLabelPaint(new java.awt.Color(255, 255, 255));
        plot.getRangeAxis().setLabelPaint(new java.awt.Color(255, 255, 255));
        plot.getDomainAxis().setAxisLinePaint(new java.awt.Color(255, 255, 255));
        plot.getRangeAxis().setAxisLinePaint(new java.awt.Color(255, 255, 255));
        plot.getDomainAxis().setTickLabelPaint(new java.awt.Color(255, 255, 255));
        plot.getDomainAxis().setLabelPaint(new java.awt.Color(255, 255, 255));
        plot.getRangeAxis().setLabelPaint(new java.awt.Color(255, 255, 255));
        plot.getRangeAxis().setTickLabelPaint(new java.awt.Color(255, 255, 255));
        plot.setDomainCrosshairPaint(new java.awt.Color(255, 255, 255));
        plot.getDomainAxis().setLowerMargin(0);
        plot.getDomainAxis().setUpperMargin(0);
        plot.getChart().removeLegend();

        plot.getChart().getPlot().setBackgroundPaint(new java.awt.Color(98, 98, 98));

        JFreeChart l = plot.getChart();
        ChartPanel u = new ChartPanel(l);
        return u;

    }


    public static JComponent getTwoColumnLayout(
            JLabel[] labels,
            JComponent[] fields) {
        return getTwoColumnLayout(labels, fields, true);
    }

    public static JComponent getTwoColumnLayout(
            JLabel[] labels,
            JComponent[] fields,
            boolean addMnemonics) {
        if (labels.length != fields.length) {
            String s = labels.length + " labels supplied for "
                    + fields.length + " fields!";
            throw new IllegalArgumentException(s);
        }
        JComponent panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);
        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        GroupLayout.Group yLabelGroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        hGroup.addGroup(yLabelGroup);
        GroupLayout.Group yFieldGroup = layout.createParallelGroup();
        hGroup.addGroup(yFieldGroup);
        layout.setHorizontalGroup(hGroup);
        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        layout.setVerticalGroup(vGroup);

        int p = GroupLayout.PREFERRED_SIZE;
        // add the components to the groups
        for (JLabel label : labels) {
            yLabelGroup.addComponent(label);
        }
        for (Component field : fields) {
            yFieldGroup.addComponent(field, p, p, p);
        }
        for (int ii = 0; ii < labels.length; ii++) {
            vGroup.addGroup(layout.createParallelGroup().
                    addComponent(labels[ii]).
                    addComponent(fields[ii], p, p, p));
        }

        return panel;
    }

    public static void importExpenseFromFile(InputStream file) throws IOException, UnsupportedLookAndFeelException {

        Scanner sc = new Scanner(file);
        boolean DEBUG = false;
        if(DEBUG)
            out.println("Printing File Now:");
        String line, id = "null---";
        String[] splitLine;

        while (sc.hasNextLine())
        {
            line = sc.nextLine();
            line = line.replace("\"","");
            splitLine = line.split(",");
            //out.println(Arrays.toString(splitLine));
            /*
            out.println(splitLine[0]);
            //splitLine[0].replace("")
            out.println(splitLine[0].substring(6));
            out.println(splitLine[0].substring(0,2));
            out.println(splitLine[0].substring(3,5));
            */
            String[] t = splitLine[4].split(" ");
            //out.print(Arrays.toString(t));
            //id = splitLine[4].substring(splitLine[4].length()-26);
            expenseList.add(new ExpenseCategory(
                    new Date(Integer.parseInt(splitLine[0].substring(7)),
                            Integer.parseInt(splitLine[0].substring(0,2)),
                            Integer.parseInt(splitLine[0].substring(3,5))) ,
                    splitLine[4],Double.parseDouble(splitLine[1]) ));
            if(DEBUG)
                out.println(" --> " + expenseList.get(expenseList.size() - 1).toString());


        }
    }

    public static LookAndFeel getLookAndFeel(){
        return UIManager.getLookAndFeel();
    }
}