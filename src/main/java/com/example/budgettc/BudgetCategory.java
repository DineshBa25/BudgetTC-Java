package com.example.budgettc;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import static java.lang.System.out;


public class BudgetCategory extends budgettcgui  {
    //todo implement a color variable that stores the color of the budget category
    public String name = "";
    public Double amt = 0.0;
    public TreeMap<String,SubBudgetCategory> subBudgetInstanceMap = new TreeMap<String,SubBudgetCategory>();
    public static TreeMap<String,SubBudgetCategory> localSubBudgetInstanceMap= new TreeMap<String,SubBudgetCategory>();
    //public static TreeMap<String,SubBudgetCategory> subBudgetInstanceMap = new TreeMap<String,SubBudgetCategory>();
    public Map<String, SubBudgetCategory> subBudget;
    public JPanel handler2 = null;
    public static double totalBudgetAmount;
    public static JScrollPane l = new JScrollPane();
    public static BudgetTree allBudgetJTree = new BudgetTree();
    public static JTabbedPane subBudgetHandlerPane;
    public Double totalExpensesAllocated = 0.0;
    //public static final SubBudgetExpenseLogTable tempTable = new SubBudgetExpenseLogTable();
    //public static Tab




    public BudgetCategory(String categoryName,Double amountAllocated,Map subBudgetMap) throws  UnsupportedLookAndFeelException  {
        //put the data into class variables
        name = categoryName;
        amt = amountAllocated;
        subBudget = subBudgetMap;
        totalBudgetAmount += amountAllocated;

    subBudgetHandlerPane = new JTabbedPane();
        //for each Sub-Budget category in the Sub-Budget provided, this creates a SubBudgetCategory instance of the Sub-Budget
        // and puts it in a map that links Sub-Budget name to its SubBudgetCategory instance
        //localSubBudgetInstanceMap  = new TreeMap<String,SubBudgetCategory>();

        for(Object each: subBudgetMap.keySet()) {
            SubBudgetCategory temp =  new SubBudgetCategory(each.toString(), (double) subBudgetMap.get(each));
            subBudgetInstanceMap.put(each.toString(), temp);
            localSubBudgetInstanceMap.put(each.toString(), temp);
        }



        //Create the Sub-Budget handler for this Budget Category
        initializeSubBudgetHandler();
        //add the category to the budget tree, so that the user can select it when trying to allocate and expense to this category
        allBudgetJTree.addToTree(categoryName, subBudgetInstanceMap);
    }

    /**
     * Returns a JPanel handler for the budget category.
     * @return String containing the name of category.
     */
    public void initializeSubBudgetHandler() throws  UnsupportedLookAndFeelException {
        //handler is initialized with a Grid Layout because in a BorderLayout, the JSplitPane is not forced to use all the space
        handler2 = new JPanel(new GridLayout(1,1));

        //Create a text area that allows to user to store information about the budget category
        //todo Move text area to its own method so that it can be stored in .budget File
        JTextArea textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(0,new Color(0x949494),new Color(0x949494)), "Short Description", TitledBorder.CENTER,
                TitledBorder.TOP));;
        textArea.setText("Write about what kinds of expenses go into this category ");

        //Create the user Fields that will go into the userFieldsPanel
        JTextField currentAgeField = new JTextField(10);
        JTextField finalAgeField = new JTextField(10);
        JTextField amtSavedField = new JTextField(10);


        //currentAgeField.setFocusable(false);
        //finalAgeField.setFocusable(false);
        //List of all the generated components that will go into the userFieldsPanel
        JComponent[] components = {
                currentAgeField,
                finalAgeField,
                generateColorChooserButton(),
        };

        //List of labels that will be assigned to each component above in the userFieldsPanel
        JLabel[] labels1 = {
                new JLabel("Category Name: "),
                new JLabel("Amount allocated for Category: "),
                new JLabel("Choose Color: "),

        };

        //Panel that holds all the user entry fields for customizing the budget categories
        JPanel userFieldsPanel = new JPanel();
        userFieldsPanel.add(getTwoColumnLayout(labels1,components), BorderLayout.CENTER);
/*
        JPanel newp = new JPanel(new GridLayout(1,1));
        //out.println("***********************************************"+subBudgetExpenseTable.initScrollPane().getComponentCount());
        //newp.add(new SubBudgetExpenseLogTable());

        //tempTable = subBudgetExpenseTable;

        ///ystem.out.println(tempTable.returnPane());
        System.out.println("hiiiiiiiiiiiiiiii" + tempTable.returnPane().getPreferredSize());
        subBudgetHandlerPane.addTab("Allocated Expenses",tempTable.returnPane());
        subBudgetHandlerPane.addTab("Short Description", textArea);
*/
        // Initialize two vertical JSplitPanes
        // two JSplitPanes are need so that there can be a 3 layered JSplitPane
        JSplitPane splitPaneTop= new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane splitPaneBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        //add all the components to the JSplitPanes
        splitPaneTop.setTopComponent(userFieldsPanel);
        splitPaneTop.setBottomComponent(initializeSubBudgetTable());
        splitPaneBottom.setTopComponent(splitPaneTop);
        splitPaneBottom.setBottomComponent(subHandlerTabbedPane);

        boolean firstResize = true;
        splitPaneBottom.addComponentListener(new ComponentAdapter(){
            boolean firstResize = true;
            @Override
            public void componentResized(ComponentEvent e) {
                if(firstResize){
                    splitPaneBottom.setDividerLocation(0.55);
                    firstResize = false;
                }
            }
        });
        //add the JSplitPanes to the handler
        handler2.add(splitPaneBottom);
        handler2.setFocusable(false); //because only tables should be focusable
    }

    /**
     * Generates a JButton that displays a color chooser for the category when pressed.
     * @return JButton containing a color chooser dialog.
     */
    public JButton generateColorChooserButton() {
        //create the JColor chooser and remove all the panels of the color chooser that are not needed
        JColorChooser colorChooser = new JColorChooser();
        colorChooser.removeChooserPanel(colorChooser.getChooserPanels()[1]);
        colorChooser.removeChooserPanel(colorChooser.getChooserPanels()[1]);
        colorChooser.removeChooserPanel(colorChooser.getChooserPanels()[1]);
        colorChooser.removeChooserPanel(colorChooser.getChooserPanels()[1]);
        colorChooser.setPreviewPanel(new JPanel());

        //todo make the chooseColor button background the color of the budget categories color upon start.
        //create the JButton that allows the user to access the color chooser
        JButton chooseColor = new JButton("Choose a Color");

        //Action Listener that responds when the user presses "OK" on the color chooser
        ActionListener okActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                out.println("OK Button");
                out.println(colorChooser.getColor());
                chooseColor.setBackground(colorChooser.getColor());
                handler2.repaint();
                handler2.revalidate();
            }
        };

        //Action Listener that responds when the user presses "Cancel" on the color chooser
        ActionListener cancelActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {out.println("Cancel Button");
            }
        };

        //When the user pressed the color chooser button, generated a dialog that displays the color chooser
        chooseColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {JColorChooser.createDialog(null,
                        "Pick a color", true,
                        colorChooser,okActionListener, cancelActionListener).setVisible(true);
            }
        });

        return chooseColor; //return the color choose button
    }

    /**
     * Generates the Sub-Budget Table by feeding it data about the Budget Category.
     * @return returns a CreateSubBudgetTable type which is essentially a table.
     */
    public CreateSubBudgetTable initializeSubBudgetTable() {
        String[] columnNames = { "Name", "Amount"};
        Object[][] subMatrix = new Object[subBudgetInstanceMap.size()][3];
        Object[] subBudgetKeySet = subBudgetInstanceMap.keySet().toArray();

        //Create a matrix for the Sub Budget Table using the subBudget Map.
        for(int i = 0; i < subBudgetKeySet.length; i++) {
            subMatrix[i][0] = subBudgetInstanceMap.get(subBudgetKeySet[i]).getCategoryName();
            subMatrix[i][1] = subBudgetInstanceMap.get(subBudgetKeySet[i]).getAmountAllocated();
            subMatrix[i][2] = subBudgetInstanceMap.get(subBudgetKeySet[i]);

        }

        //System.out.println("Sub Matrix: ---->\n" + Arrays.deepToString(subMatrix));
        CreateSubBudgetTable newSubBudgetTable = new CreateSubBudgetTable(subMatrix, columnNames);
        newSubBudgetTable.setOpaque(true); // content panes must be opaque
        return newSubBudgetTable;
    }

    public static void setSubBudgetHandler(SubBudgetCategory subBudgetCategory){
        out.println("*Setting pane ========= " + subBudgetCategory + " \n" + subBudgetHandlerPane.getTabCount());

        //subBudgetHandlerPane.addTab("Allocated Expenses",subBudgetCategory.getSubBudgetCategoryHandler());
        //subBudgetHandlerPane.remove(0);
        subBudgetHandlerPane.revalidate();
        subBudgetHandlerPane.repaint();
        changePanels(false);
        handler.revalidate();
        handler.repaint();
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Returns the handler for the Budget Category
     * @return JPanel containing handler for the Budget Category.
     */
    public JPanel getJPanel()
    {
        return handler2;
    }

    /**
     * Sets the name of budget category contained in the class.
     * @return String containing the new name of category.
     * @param newName New name to be changed to.
     */
    public String setCategoryName(String newName){
        return name = newName;
    }

    /**
     * Returns the name of budget category contained in the class.
     * @return String containing the name of category.
     */
    public String getCategoryName(){
        return name;
    }

    /**
     * Sets the amount allocated for the budget category contained in the class.
     * @return Double value containing the new amount allocated for the budget category contained in the class.
     * @param newAmt New Amount to be allocated to the Budget Category
     */
    public double setAmountAllocated(Double newAmt){
        totalBudgetAmount-= amt;
        totalBudgetAmount+= newAmt;
        amt = newAmt;
        return amt;
    }

    /**
     * Returns the amount allocated for the budget category contained in the class.
     * @return Double value containing amount allocated for the budget category contained in the class.
     */
    public Double getAmountAllocated(){
        return amt;
    }

    /**
     * Returns the total amount allocated for the entire budget.
     * @return Double value containing total amount allocated for the entire budget.
     */
    public static Double getTotalBudgetAmount(){
        return totalBudgetAmount;
    }

    /**
     * Returns the total dollar amount of expenses allocated for the budget category contained in the class.
     * @return  Map containing subBudget.
     */
    public double getTotalExpenseAllocated(){
        int total = 0;
        //out.println("_"+subBudget.keySet());
        //out.println(subBudgetInstanceMap.keySet());

        for(String key: subBudgetInstanceMap.keySet()){
            total += -1*subBudgetInstanceMap.get(key.toString()).totalSubBudgetExpensesAllocated;
        //    out.println("**+"+subBudgetInstanceMap.get(String.valueOf(key)).totalSubBudgetExpensesAllocated);
        }
        out.println("____"+total/amt*100);
        return total/amt*100;
    }

    /**
     * Returns the subBudget map for the budget category contained in the class.
     * @return  Map containing subBudget.
     */
    /*public Component getProgressBarTableCellComponent(){
        JProgressBar x = new JProgressBar(0,100);
        Color amtLeftcolor = new Color(27, 107, 0);
        x.setBackground(amtLeftcolor);
        Color amtSpentColor = new Color(119, 0, 0);
        UIManager.put("ProgressBar.foreground", amtSpentColor);
        out.println("*******************************"+storageMatrix.get(row).getTotalExpenseAllocated());
        x.setValue((int)( -1.0 *storageMatrix.get(row).getTotalExpenseAllocated()));
        int borderSize = 2;
        x.setBorder(BorderFactory.createMatteBorder(borderSize,borderSize,borderSize,borderSize,new Color(0x00000FF, true)));
        return x;
    }*/

    /**
     * Returns the subBudget map for the budget category contained in the class.
     * @return  Map containing subBudget.
     */
    public Map getSubBudget(){
        return subBudget;
    }



    /**
     * Generates a JPanel that allows the user to add a new category to the budget
     * @return JPanel containing user feilds to add a new category to the budget
     */
    public static JPanel addNewCategory(){

        //Create the user Fields/Components that will go into the userFieldsPanel
        JTextField budgetCategoryNameField = new JTextField();
        JTextField amountAllocatedField = new JTextField();

        JButton addBudgetButton = new JButton("Add to Budget");
        //Action listener for the user presses th addBudgetButton
        addBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Something Happened");
                try {
                    budgettcgui.storageMatrix.add(new BudgetCategory(budgetCategoryNameField.getText(), Double.parseDouble(amountAllocatedField.getText()), new TreeMap()));
                } catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                //reloads the panels
                budgettcgui.changePanels(true);
            }
        });

        //List of all the generated components that will go into the userFieldsPanel
        JComponent[] components = {
                budgetCategoryNameField,
                amountAllocatedField,
                addBudgetButton
        };

        //List of labels that will be assigned to each component above in the userFieldsPanel
        JLabel[] labels1 = {
                new JLabel("Budget Category Name: "),
                new JLabel("Amount Allocated: "),
                new JLabel("")
        };

        //Panel that holds all the user entry fields for adding a new Budget Category
        JPanel userFieldsPanel = new JPanel();
        userFieldsPanel.add(budgettcgui.getTwoColumnLayout(labels1,components),BorderLayout.CENTER);

        return userFieldsPanel;
    }


    /**
     * @return JScrollPane containing the overallbudget tree.
     */
    public static JScrollPane getBudgetTree() {
        return l;
    }


    public String toString() {
        return name +" - "+ amt;
    }
}