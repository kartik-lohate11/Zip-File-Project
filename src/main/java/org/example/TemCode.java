package org.example;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TemCode {

    public TemCode(){
        System.out.println("TemCode invoked");
        Frame frame = new Frame("First DeskTop App");

        // create lable (Text)
        Label label = new Label("Enter Your name");
        label.setBounds(50,50,200,30);
        label.setBackground(Color.red);
        frame.add(label);

        //create button
        Button button = new Button("Click Here..");
        button.setBounds(80,80,100,50);
        frame.add(button);


        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);

        // back from the app by X button
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

    }
}
