package com.example.budgettc;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import java.util.*;

import static java.lang.System.out;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerContrastIJTheme;

public class ExpenseCategory extends budgettcgui {
    public static Map<String, Object[]> allExpenseMap = new TreeMap<>();
    public String expenseName;
    public Double expenseAmount;
    public Date expenseDate;
    public String expenseID;
    public JPanel expenseHandler = new JPanel();
    public static Double totalExpenseAmount = 0.0;
    public static Double totalAllocatedExpenseAmount = 0.0;

    public ExpenseCategory( Date expenseDate1, String expenseName1, Double expenseAmount1) throws UnsupportedLookAndFeelException, IOException {
        expenseName = expenseName1;
        expenseAmount = expenseAmount1;
        expenseDate = expenseDate1;
        expenseID = "expenseID1";
        Object[] temp = {expenseDate, expenseName, expenseAmount};
        allExpenseMap.put(expenseID, temp);
        if(expenseAmount <= 0)
        initializeExpenseHandler();
        else
            initializePushToIncomeHandler();
        if(expenseAmount<=0)
            totalExpenseAmount+=expenseAmount;
    }

    private void initializePushToIncomeHandler() {


        expenseHandler = new JPanel(new BorderLayout());




        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        textPane.setText("This seems like an income, change the income name and type below and add it as an income category: \n\n" +
                "Income Name: " + getExpenseName() +
                "\nIncome Amount: $ " + getExpenseAmount());
        textPane.setEditable(false);
        expenseHandler.add(textPane,BorderLayout.NORTH);

        expenseHandler.add(IncomeCategory.addNewIncome(expenseName, expenseAmount, this),BorderLayout.CENTER);






    }

    /**
     * Returns a String of the name of the income
     *
     * @return String value containing the name of the income.
     */
    public String getExpenseName() {
        return expenseName;
    }

    /**
     * Changes the name of the income category to the String in the parameter
     *
     * @param newName The new name to be changed to.
     * @return String containing the new name of the income.
     */
    public String setExpenseName(String newName) {
        expenseName = newName;
        return expenseName;
    }

    /**
     * Returns a Double containing the amount of income currently set to the income.
     *
     * @return Double value containing the current income from this Object.
     */
    public double getExpenseAmount() {
        return expenseAmount;
    }

    /**
     * Changes the amount of money from this income to the amount in the parameter.
     *
     * @param newAmount The Amount of income to be changed to
     * @return Double value containing the new amount that was set for the income
     */
    public double setExpenseAmount(Double newAmount) {
        return expenseAmount = newAmount;
    }

    /**
     * Returns a String of the Income type that the income is currently assigned
     *
     * @return String value containing the current incomeType that this income is assigned.
     */
    public Date getExpenseDate() {
        return expenseDate;
    }

    /**
     * Changes the currently assigned income type to a new type of income
     *
     * @return String value containing the new incomeType that this income is assigned.
     */
    public String getExpenseID() {
        return expenseID;
    }

    /**
     * Generated a JPanel handler for the income.
     * The handler contains a basic panel for the user to modify the income information, and a panel for the user to put any notes about the income
     *
     * @return JPanel containing the income handler for the income.
     */
    public JPanel initializeExpenseHandler() throws UnsupportedLookAndFeelException, IOException {
        File file = new File("./src/main/resources/Contrast.theme.json");
        IntelliJTheme.setup(file.getAbsoluteFile().toURI().toURL().openStream());
        if(super.DEBUG)
        out.println("---------------------------------------------------------------------"+budgettcgui.getLookAndFeel());

        BudgetTree newInstance = new BudgetTree();

        expenseHandler = new JPanel(new BorderLayout());
        JButton applyButton = new JButton("Apply Selection");
        applyButton.setEnabled(false);
        applyButton.setToolTipText("You must select a Sub-Budget Category");

        //JScrollPane pane = new JScrollPane(newInstance.getTree());

        JTree newTree = newInstance.getTree();
        newTree.expandRow(0);
        newTree.expandPath(newTree.getPathForRow(0));

        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        textPane.setText("Select an appropriate budget category to allocate this expense to: \n\n" +
                "Expense Name: " + getExpenseName() +
                "\nExpense Amount: $ " + getExpenseAmount());
        textPane.setEditable(false);
        expenseHandler.add(textPane,BorderLayout.NORTH);

        expenseHandler.add(new JScrollPane(newTree));

        newTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if(((DefaultMutableTreeNode)newTree.getLastSelectedPathComponent()).isLeaf()){
                    applyButton.setToolTipText("Link this expense with the selected Sub-Budget Category");

                    applyButton.setEnabled(true);

                }else{
                    applyButton.setEnabled(false);
                }
        }});
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    System.out.println(BudgetTree.selectedNode);
                    if(BudgetTree.selectedNode != null){
                        BudgetTree.selectedNode.linkExpenseCategory(getThisExpense());
                        totalAllocatedExpenseAmount += getThisExpense().expenseAmount;
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                //out.println("hi");
                                expenseHandler.remove(0);
                                expenseHandler.remove(0);
                                JTextPane textPane = new JTextPane();
                                StyledDocument doc = textPane.getStyledDocument();
                                SimpleAttributeSet center = new SimpleAttributeSet();
                                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                                textPane.setFont(new Font("Corbert", Font.BOLD, 20));
                                doc.setParagraphAttributes(0, doc.getLength(), center, false);
                                textPane.setText("Expense has been linked");
                                textPane.setBackground(Color.ORANGE);
                                textPane.setEditable(false);

                                BufferedImage myPicture = null;
                                try {
                                    myPicture = ImageIO.read(getClass().getResource("/logo5.png"));
                                   // out.println(myPicture);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                assert myPicture != null;

                                ImageLabel imglabel = new ImageLabel(new ImageIcon(myPicture));

                                //expenseHandler = new JPanel(new GridLayout(1,1));
                                JPanel newp = new JPanel(new GridLayout(1,1));
                                newp.add(imglabel);
                                expenseHandler.add(newp);
                                expenseHandler.add(imglabel);
                                expenseHandler.invalidate();
                                expenseHandler.revalidate();
                                expenseHandler.repaint();
                                frame.revalidate();
                                frame.repaint();

                                Timer timer = new Timer(1000, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        expenseHandler.removeAll();
                                        delete();
                                        frame.revalidate();
                                        frame.repaint();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();

                            }
                        });

                    }
                    else{
                        out.println("User did not select a Sub-Budget Category");
                    }

            }
        });
        expenseHandler.add(applyButton, BorderLayout.SOUTH);
        if(super.DEBUG){
        out.println(newInstance.getTree());
        out.println(UIManager.getLookAndFeel());}

        return expenseHandler;

    }

    public void delete(){

        expenseList.remove(this);
        newExpenseTable.removeSelectedRow(this);

    }

    public ExpenseCategory getThisExpense(){ return this; }

    public static Double getTotalExpenseAmount(){
        return totalExpenseAmount;
    }


    /**
     * Returns a previously generated JPanel handler for the income.
     *
     * @return String value containing the current incomeType that this income is assigned.
     */
    public JPanel getExpenseHandler() {
        return expenseHandler;
    }

    /**
     * Returns a Double containing the combined income total for all income instances.
     *
     * @return String value containing the current incomeType that this income is assigned.
     */
    public Map<String, Object[]> getExpenseMap() {
        return allExpenseMap;
    }

    /**
     * Returns a String containing basic debugging information for the income instance.
     *
     * @return String containing the Name, Amount and Income Type for the income.
     */
    public String toString() {

        /*
            *Used to get the properties of all the instances of IncomeCategory*
            for(String key: allIncomeMap.keySet()){
            System.out.println(key +" - "+ allIncomeMap.get(key)[0] + " - "+ allIncomeMap.get(key)[1]);
        }*/
        return getExpenseName() + " - " + getExpenseAmount();
    }
}

