package com.example.budgettc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import com.formdev.flatlaf.FlatDarkLaf;
import static java.lang.System.out;


public class IncomeCategory extends JPanel {
    public String incomeName;
    public Double incomeAmount;
    public String incomeType;
    public JPanel incomeHandler = null;
    public static double incomeTotal = 0.0;
    public static Map<String, Object[]> allIncomeMap = new TreeMap<>();


    public IncomeCategory(String incomeName1,String incomeType1, Double incomeAmountAllocated) throws UnsupportedLookAndFeelException {
        incomeName = incomeName1;
        incomeType = incomeType1;
        incomeAmount= incomeAmountAllocated;
        Object[] temp = {incomeType, incomeAmount};
        allIncomeMap.put(incomeName,temp);
    }

    /**
     * Returns a String of the name of the income
     * @return String value containing the name of the income.
     */
    public String getIncomeName(){
        return incomeName;
    }

    /**
     * Changes the name of the income category to the String in the parameter
     * @return String containing the new name of the income.
     * @param newName The new name to be changed to.
     */
    public String setIncomeName(String newName){
        incomeName =  newName;
        return incomeName;
    }

    /**
     * Returns a Double containing the amount of income currently set to the income.
     * @return Double value containing the current income from this Object.
     */
    public double getIncomeAmount(){
        return incomeAmount;
    }

    /**
     * Changes the amount of money from this income to the amount in the parameter.
     * @return Double value containing the new amount that was set for the income
     * @param newAmount The Amount of income to be changed to
     */
    public double setIncomeAmount(Double newAmount){
        return incomeAmount = newAmount;
    }

    /**
     * Returns a String of the Income type that the income is currently assigned
     * @return String value containing the current incomeType that this income is assigned.
     */
    public String getIncomeType(){
        return incomeType;
    }

    /**
     * Changes the currently assigned income type to a new type of income
     * @return String value containing the new incomeType that this income is assigned.
     * @param newIncomeType The income type to be assigned.
     */
    public String setIncomeType(String newIncomeType){
        incomeType = newIncomeType;
        return incomeType;
    }
    /**
     * Generated a JPanel handler for the income.
     * The handler contains a basic panel for the user to modify the income information, and a panel for the user to put any notes about the income
     * @return JPanel containing the income handler for the income.
     */
    public JPanel initializeIncomeHandler() throws UnsupportedLookAndFeelException {
        incomeHandler = new JPanel(new GridBagLayout());
        incomeHandler.setFocusable(false);

        return incomeHandler;
    }

    /**
     * Returns a previously generated JPanel handler for the income.
     * @return String value containing the current incomeType that this income is assigned.
     */
    public JPanel getIncomeHandler(){
        return incomeHandler;
    }

    /**
     * Returns a Double containing the combined income total for all income instances.
     * @return String value containing the current incomeType that this income is assigned.
     */
    public Double getIncomeTotal(){
        return incomeTotal;
    }

    /**
     * Returns a Map containing the income total by the type of income for all income instances.
     * @return TreeMap<String,Double> containing the current incomeTotal for all types of income.
     */
    public TreeMap<String,Double> getIncomeTotalByType(){
        return new TreeMap();
    }
    /**
     * Returns a String containing basic debugging information for the income instance.
     * @return String containing the Name, Amount and Income Type for the income.
     */
    public String toString(){

        /*
            *Used to get the properties of all the instances of IncomeCategory*
            for(String key: allIncomeMap.keySet()){
            System.out.println(key +" - "+ allIncomeMap.get(key)[0] + " - "+ allIncomeMap.get(key)[1]);
        }*/
        return getIncomeName()+ " - " + getIncomeAmount();
    }

}