package com.example.budgettc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.*;

class ImageLabel extends JLabel {
    Image image;
    ImageObserver imageObserver;

    ImageLabel(ImageIcon icon) {
        image = icon.getImage();
        imageObserver = icon.getImageObserver();
    }

    void set(ImageIcon icon) {
        image = icon.getImage();
        imageObserver = icon.getImageObserver();
    }
    public void paint( Graphics g ) {
        super.paint( g ) ;
        g.drawImage(image,  0 , 0 , getWidth() , getHeight() , imageObserver);
    }
}

