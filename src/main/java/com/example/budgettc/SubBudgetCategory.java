package com.example.budgettc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;

//This class create a JPanel for each budget category and all customizations to each budget category will go here

/*
# Per Main Category #
Name of Category
Amount Allocated
Color
Sub Budget
JPanel


# Per Sub-Budget
Name
Amount Allocated


Each Category should be defined in a matrix withing the main class. This class contains everything that descends from that main category.
*/
public class SubBudgetCategory extends JPanel  {

    public String name;
    public Double amountAllocated;
    public ArrayList<ExpenseCategory> allocatedExpenseList;
    public JScrollPane handler;



    public SubBudgetCategory(String categoryName, Double amountAllocated1, ArrayList<ExpenseCategory> allocatedExpenseList1) throws  UnsupportedLookAndFeelException  {
        name = categoryName;
        amountAllocated = amountAllocated1;
        allocatedExpenseList = allocatedExpenseList1;
        initializeSubBudgetCategoryHandler();

    }

    /**
     * Returns a String of the name of the Sub-Budget Category
     * @return String value containing the name of the income.
     */
    public String getCategoryName(){ return name;  }

    /**
     * Sets the name of the Sub-Budget Category
     * @param newName New name for the Sub-Budget Category
     * @return String value containing the new name of the Sub-Budget Category
     */
    public String setCategoryName(String newName){  return name = newName;  }

    /**
     * Returns a Double of the amount allocated for the Sub-Budget Category
     * @return Double value containing the amount allocated for the Sub-Budget Category.
     */
    public Double getAmountAllocated(){ return amountAllocated; }

    /**
     * Sets the amount allocated for the Sub-Budget Category
     * @return Double value containing the new amount allocated for the Sub-Budget Category
     */
    public double setAmountAllocated(Double newAmt){  return amountAllocated = newAmt;  }

    /**
     * Returns a list containing all the allocated expenses for the Sub-Budget Category
     * @return ArrayList<ExpenseCategory> containing all the allocated expenses for the Sub-Budget Category.
     */
    public ArrayList<ExpenseCategory> getAllocatedExpenseList(){
        return allocatedExpenseList;
    }

    /**
     * Generates a JScrollPane handler for the Sub Budget Category. This handler leads the user to attaching
     * new expenses to the category and shows more overall information about the category
     */
    public void initializeSubBudgetCategoryHandler() throws  UnsupportedLookAndFeelException {  }

    /**
     * Gets the JScrollPane that contains the handler for the Sub-Budget Category
     * @return JScrollPane containing the Sub-Budget Handler.
     */
    public JScrollPane getSubBudgetCategoryHandler() { return handler; }

    /**
     * Returns a String containing the Name, Amount alllocated and the Expense list.
     * @return String value containing the name of the income.
     */
    public String toString() { return "Info: " + name + " " + amountAllocated + "\n" +
                                "Expense List: " + allocatedExpenseList;    }
}