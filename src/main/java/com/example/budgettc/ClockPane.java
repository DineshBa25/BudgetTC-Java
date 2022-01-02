package com.example.budgettc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockPane extends JLabel {

    public SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");

    public ClockPane() {

        setHorizontalAlignment(JLabel.CENTER);
        setFont(UIManager.getFont("Label.font"));
        tickTock();


        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tickTock();
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
    }


    public void tickTock() {

        setText(sdf.format(new Date())+"  ");
    }
}