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
public class BudgetCategory extends JPanel  {
    public String name = "";
    public Double amt = 0.0;
    public Map subBudget = null;
    public JPanel handler = null;
    public static JScrollPane l = new JScrollPane();

    public static BudgetTree allBudgetJTree = new BudgetTree();



    public BudgetCategory(String categoryName,Double amountAllocated,Map subBudgetMap) throws  UnsupportedLookAndFeelException  {
        name = categoryName;
        amt = amountAllocated;
        subBudget = subBudgetMap;
        initializeSubBudgetHandler();

        //System.out.println("---------------------------------------------------------------------hi");
        allBudgetJTree.addToTree(categoryName, subBudget);


    }

public static JTree initTree(){
    DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("The expenses");
    JTree temp = new JTree(top);
    temp.addTreeSelectionListener(new TreeSelectionListener() {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            System.out.println("new selection");
        }
    });
    return temp;
}

    public static JTree getAllBudgetJTree() {
        return new JTree();
    }

    /**
     * Returns a JPanel handler for the budget category.
     * @return String containing the name of category.
     */
    public void initializeSubBudgetHandler() throws  UnsupportedLookAndFeelException {
        JPanel newPanel = new JPanel(new GridLayout(2, 1));
        JSplitPane newPanel1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        handler = new JPanel(new GridLayout(1,1));

        //UIManager.setLookAndFeel(new FlatMaterialDarkerContrastIJTheme());
        JButton x = new JButton("hellooooooooo");
        x.setFocusable(false);


        String[] labels = {"Name: ", "Fax: ", "Email: "};
        int numPairs = labels.length;

//Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l);
            JTextField textField = new JTextField(10);

            l.setLabelFor(textField);
            p.add(textField);
        }

//Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        p.setMinimumSize(new Dimension(0,0));



        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = .2;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        //handler.add(p,c);
        newPanel.add(initializeSubBudgetTable());
        JTextArea textArea = new JTextArea();
        //newPanel1.setTopComponent(initializeSubBudgetTable());
        textArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(0,new Color(0x949494),new Color(0x949494)), "Short Description", TitledBorder.LEFT,
                TitledBorder.TOP));;
        //textArea.setBackground(new Color(58, 58, 58));
        textArea.setText("Write about what kinds of expenses go into this category ");
        newPanel.add(textArea);
        //newPanel1.setBottomComponent(addToBudgetTree());
        JColorChooser colorChooser = new JColorChooser();
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


        JTextField currentAgeField = new JTextField(10);
        JTextField finalAgeField = new JTextField(10);
        JTextField amtSavedField = new JTextField(10);

        JComponent[] components = {
                currentAgeField,
                finalAgeField,
                chooseColor,


        };

        JLabel[] labels1 = {
                new JLabel("Category Name: "),
                new JLabel("Amount allocated for Category: "),
                new JLabel("Choose Color: "),

        };

        JPanel columnLayout = new JPanel();
        columnLayout.add(getTwoColumnLayout(labels1,components),BorderLayout.CENTER);





        //andler.add(columnLayout,c);
        handler.repaint();
        ActionListener okActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("OK Button");
                System.out.println(colorChooser.getColor());
                chooseColor.setBackground(colorChooser.getColor());
                handler.repaint();
                handler.revalidate();
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
        c.weighty = .6;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
    textArea.setPreferredSize(new Dimension((int) textArea.getPreferredSize().getWidth(),20));



        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneLeft.setTopComponent( columnLayout );
        splitPaneLeft.setBottomComponent( initializeSubBudgetTable() );
        splitPaneRight.setTopComponent( splitPaneLeft );
        System.out.println("***********************" +allBudgetJTree);
        splitPaneRight.setBottomComponent(textArea);


        handler.add(splitPaneRight);
        handler.setFocusable(false);
    }

    public CreateSubBudgetTable initializeSubBudgetTable() {
        String[] columnNames = { "Name", "Amount"};
        Object[][] subMatrix = new Object[subBudget.size()][2];
        Object[] tempset =  subBudget.keySet().toArray();
        for(int x = 0 ; x < subBudget.size(); x++) {
            subMatrix[x][0] = tempset[x];
            subMatrix[x][1] = subBudget.get(tempset[x]);
        }
        for (Object[] row : subMatrix)
            // converting each row as string and then printing in a separate line
            System.out.println(Arrays.toString(row));
        CreateSubBudgetTable newContentPane = new CreateSubBudgetTable(subMatrix, columnNames);
        newContentPane.setOpaque(true); // content panes must be opaque
        return newContentPane;
    }

    public CreateSubBudgetTable initializeSubBudgetTableNG() {
        String[] columnNames = { "Name", "Amount"};
        Object[][] subMatrix = new Object[subBudget.size()][2];
        Object[] tempset =  subBudget.keySet().toArray();
        for(int x = 0 ; x < subBudget.size(); x++) {
            subMatrix[x][0] = tempset[x];
            subMatrix[x][1] = subBudget.get(tempset[x]);
        }
        for (Object[] row : subMatrix)
            // converting each row as string and then printing in a separate line
            System.out.println(Arrays.toString(row));
        CreateSubBudgetTable newContentPane = new CreateSubBudgetTable(subMatrix, columnNames);
        newContentPane.setOpaque(true); // content panes must be opaque
        return newContentPane;
    }

    public JPanel getJPanel()
    {
        return handler;
    }

    /**
     * Returns the of budget category contained in the class.
     * @return String containing the name of category.
     */
    public String getCategoryName(){
        return name;
    }

    public Double getAmountAllocated(){
        return amt;
    }
    public Map getSubBudget(){
        return subBudget;
    }

    public String setCategoryName(String newName){
        name = newName;
        return name;
    }

    public double setAmountAllocated(Double newAmt){
        amt = newAmt;
        return amt;
    }



    private static class BookInfo {
        public String bookName;

        public BookInfo(String book) {
            bookName = book;
        }

        public String toString() {
            //System.out.println("----------------------------------------------------"+bookName);
            return bookName;
        }
    }

    private static void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new DefaultMutableTreeNode("Books for Java Programmers");
        top.add(category);

        //original Tutorial
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Tutorial: A Short Course on the Basics"));
        category.add(book);

        //Tutorial Continued
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Tutorial Continued: The Rest of the JDK"));
        category.add(book);

        //JFC Swing Tutorial
        book = new DefaultMutableTreeNode(new BookInfo
                ("The JFC Swing Tutorial: A Guide to Constructing GUIs"));
        category.add(book);

        //Bloch
        book = new DefaultMutableTreeNode(new BookInfo
                ("Effective Java Programming Language Guide"));
        category.add(book);

        //Arnold/Gosling
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Programming Language"));
        category.add(book);

        //Chan
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Developers Almanac"));
        category.add(book);

        category = new DefaultMutableTreeNode("Books for Java Implementers");
        top.add(category);

        //VM
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Virtual Machine Specification"));
        category.add(book);

        //Language Spec
        book = new DefaultMutableTreeNode(new BookInfo
                ("The Java Language Specification"));
        category.add(book);
    }

    public static JScrollPane getBudgetTree() {
        return l;
    }


    /**
     * Returns the amount allocated for budget category contained in the class.
     * @return Double Value containing amount allocated.
     */
    public double amountAllocated(){
        return 0;
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
    public String toString() {
        return name;
    }
}