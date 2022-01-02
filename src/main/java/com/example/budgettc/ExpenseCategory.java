package com.example.budgettc;

import javax.swing.*;

import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import java.util.*;

import static java.lang.System.out;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerContrastIJTheme;

public class ExpenseCategory extends budgettcgui {
    public String expenseName;
    public Double expenseAmount;
    public Date expenseDate;
    public String expenseID;
    public JPanel expenseHandler = null;
    public static Map<String, Object[]> allExpenseMap = new TreeMap<>();

    public ExpenseCategory( Date expenseDate1, String expenseName1, Double expenseAmount1) throws UnsupportedLookAndFeelException, IOException {
        expenseName = expenseName1;
        expenseAmount = expenseAmount1;
        expenseDate = expenseDate1;
        expenseID = "expenseID1";
        Object[] temp = {expenseDate, expenseName, expenseAmount};
        allExpenseMap.put(expenseID, temp);
        initializeExpenseHandler();
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
        out.println("---------------------------------------------------------------------"+budgettcgui.getLookAndFeel());

        BudgetTree newInstance = new BudgetTree();

        expenseHandler = new JPanel(new BorderLayout());
        JButton applyButton = new JButton("Apply Selection");

        //JScrollPane pane = new JScrollPane(newInstance.getTree());

        JTree newTree = newInstance.getTree();
        newTree.expandRow(0);
        newTree.expandPath(newTree.getPathForRow(0));

        expenseHandler.add(new JLabel("Select an appropriate budget category to allocate this expense to: \n",SwingConstants.CENTER),BorderLayout.NORTH);

        expenseHandler.add(newTree);
        expenseHandler.add(applyButton, BorderLayout.SOUTH);

        out.println(newInstance.getTree());
        out.println(UIManager.getLookAndFeel());

        return expenseHandler;

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

