package com.example.budgettc;

public class BudgetTreeNodeInfo {
    public String bookName;
    public SubBudgetCategory subBudgetCategoryClass;

    public BudgetTreeNodeInfo(String book) {
        bookName = book;

    }
    public BudgetTreeNodeInfo(String book, SubBudgetCategory subBudgetCategoryClassInstance) {
        bookName = book;
        subBudgetCategoryClass = subBudgetCategoryClassInstance;


    }

    public SubBudgetCategory getSubBudgetCategoryClass() {
        return subBudgetCategoryClass;
    }

    public String toString() {
        return bookName;
    }

    public String test() {
        return "this works!!!";
    }
}