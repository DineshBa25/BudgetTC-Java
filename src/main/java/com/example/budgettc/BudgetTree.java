package com.example.budgettc;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.Map;

import static java.lang.System.out;

public class BudgetTree extends JTree implements TreeSelectionListener {


    public static DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("Budget Categories");
    public static JTree newtree = new JTree(treeNode);




    public BudgetTree() {
        treeNode.setAllowsChildren(true);
    }
    public void addToTree(String newNode, Map subbudget) {
        //treeNode.add(new DefaultMutableTreeNode(newNode));
        DefaultMutableTreeNode mainCategoryNode = new DefaultMutableTreeNode(new BookInfo(newNode));
        //System.out.println("---------------------------------------------------------------------hi");
        for(Object eachKey: subbudget.keySet())
        {
            mainCategoryNode.add(new DefaultMutableTreeNode(new BookInfo(eachKey.toString())));
        }

        ((DefaultTreeModel) newtree.getModel()).insertNodeInto(mainCategoryNode, treeNode, 0);
        ((DefaultTreeModel)(newtree.getModel())).reload();
        newtree.revalidate();
        newtree.repaint();
        out.println("-----------------910898765789-----------"+treeNode.getChildCount());
    }

    public JTree getTree(){
        newtree = new JTree(treeNode);
        newtree.addTreeSelectionListener(this);

        //JPanel temp = new JPanel(new GridLayout(1,1));
        //temp.add(newTreeTemp);
        out.println(treeNode.isLeaf()+" -- "+ treeNode.isRoot() + " --- " + treeNode.getChildCount());


        newtree.expandRow(0);

        out.print("**"+newtree.getPathForRow(0)+"--------------------------------------"+newtree.getRowCount());
        //newTreeTemp.setExpandedState(newTreeTemp.getPathForRow(1), true);

        //newTreeTemp.setExpandsSelectedPaths(true);
        JScrollPane temp = new JScrollPane(newtree);
        return newtree;
    }
    public BudgetTree getSelf(){
        return this;
    }


    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                newtree.getLastSelectedPathComponent();
        newtree.getRowCount();
        //if (node == null) return;

        //Object nodeInfo = node.getUserObject();

        System.out.print(e.getNewLeadSelectionPath());
        System.out.println( ((DefaultMutableTreeNode) e.getPath().getLastPathComponent()).getUserObject());
        //out.println("--- please do something!!!!!!!");
    }

    private class BookInfo {
        public String bookName;

        public BookInfo(String book) {
            bookName = book;

        }

        public String toString() {
            return bookName;
        }

        public String test() {
            return "this works!!!";
        }
    }
}
