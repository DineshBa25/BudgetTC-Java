module com.example.budgettc {


    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires java.desktop;
    requires com.formdev.flatlaf;
    requires com.formdev.flatlaf.intellijthemes;
    requires javafx.swing;
    requires org.jfree.jfreechart;
    requires com.formdev.flatlaf.extras;
    //requires miglayout.swing;
    //requires javafx.swing;

    opens com.example.budgettc to javafx.fxml;
    exports com.example.budgettc;
}