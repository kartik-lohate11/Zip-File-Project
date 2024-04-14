package Zip_File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ZipFIle extends JFrame {
    private File selectedFile;

    public ZipFIle(){
        setTitle("ZIP TEXT FILES");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 400);

        JLabel label = new JLabel("<html><u>Select Your Text File</u></html>");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout());
        JButton processButton = new JButton("Compressed File");
        processButton.setPreferredSize(new Dimension(150, 40));
        processButton.setFont(new Font("Arial", Font.PLAIN, 14));

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    String fileName = selectedFile.getName();
                    System.out.println("Selected file name: " + fileName);
                    System.out.println("Selected file path: " + filePath);

                    // Call the method to update the file
                    String updatedFile = "D:\\"+"compressed"+fileName;
                    Compressed compressed = new Compressed();
                    boolean isCompresed = compressed.CompressedData(filePath,updatedFile);
                    if (isCompresed) {
                        JOptionPane.showMessageDialog(null, "Your File is Compressed..");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error occurred while updating the file.");
                    }
                }
            }
        });

        JButton decompressButton = new JButton("Decompressed File");
        decompressButton.setPreferredSize(new Dimension(150, 40));
        decompressButton.setFont(new Font("Arial", Font.PLAIN, 14));

        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
                jFileChooser.setFileFilter(filter);
                int val = jFileChooser.showOpenDialog(null);
                if(val == jFileChooser.APPROVE_OPTION){
                    selectedFile = jFileChooser.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    String name = selectedFile.getName();
                    System.out.println("Decompressed file name = "+selectedFile+" and path = "+path);
                    String updatedFile = "D:\\"+"decompressed"+name;
                    Compressed compressed = new Compressed();
                    boolean b = compressed.deCompressed(path,updatedFile);
                    if (b) {
                        JOptionPane.showMessageDialog(null, "Your File is DeCompressed..");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error occurred while updating the file.");
                    }
                }
            }
        });

        centerPanel.add(decompressButton);
        centerPanel.add(processButton);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}



