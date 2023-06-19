package com.amrita.jpl21cys21053nived.endsem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedWriter;


public class AddressBookApplication extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton addButton;
    private JButton refreshButton;
    private JButton deleteButton;

    private List<Contact> contacts;

    /**
     * AddressBookApplication is used to create the window and add all the necessary buttons
     */
    public AddressBookApplication() {
        contacts = new ArrayList<>();

        setTitle("21UCYS End Semester Assignment File Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new Object[]{"Filename", "File Size", "File Type"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane,BorderLayout.CENTER);

        addButton = new JButton("Add");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);



        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showContactDialog(null);
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    contacts.remove(selectedRow);
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(AddressBookApplication.this, "Please a file to delete.");
                }
            }
        });
    }

    /**
     *
     * @param contact->object of the Contact class(contains the variables that hold the values of file name, file type and file size
     * This function also contains the actionPerformed functions that are related to the add and delete functions
     *
     */
    private void showContactDialog(Contact contact) {
        JDialog dialog = new JDialog(this, "File Details", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel filenameLabel = new JLabel("File Name:");
        JTextField filenameField = new JTextField();
        JLabel filesizeLabel = new JLabel("File Size:");
        JTextField filesizeField = new JTextField();
        JLabel filetypeLabel = new JLabel("File Type:");
        JTextField filetypeField = new JTextField();

        if (contact != null) {
            filenameField.setText(contact.getName());
            filesizeField.setText(contact.getfilesize());
            filetypeField.setText(contact.getfiletype());
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = filenameField.getText();
                String filesize = filesizeField.getText();
                String filetype = filetypeField.getText();

                if (filename.isEmpty() || filesize.isEmpty() || filetype.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Entry Fields cannot be empty.");
                } else {
                    if (contact != null) {
                        contact.setFileName(filename);
                        contact.setfilesize(filesize);
                        contact.setfiletype(filetype);
                        refreshTable();
                    } else {
                        Contact newContact = new Contact(filename, filesize, filetype);
                        contacts.add(newContact);
                        tableModel.addRow(new Object[]{filename, filesize, filetype});
                                String filePath = "C:\\JavaProjects\\finallab\\src\\com\\amrita\\jpl21cys21053nived\\endsem\\example.txt";
                                try {
                                    FileWriter fileWriter = new FileWriter(filePath,true);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                                    // Write to the file
                                    bufferedWriter.write("\nfilename: "+filename+" filesize: "+ filesize+" filetype: "+filetype);
                                    // Close the writer
                                    bufferedWriter.close();
                                } catch (IOException b) {
                                    b.printStackTrace();
                                    // Handle the exception appropriately
                                }




                    }
                    dialog.dispose();
                }
            }
        });

        dialog.add(filenameLabel);
        dialog.add(filenameField);
        dialog.add(filesizeLabel);
        dialog.add(filesizeField);
        dialog.add(filetypeLabel);
        dialog.add(filetypeField);
        dialog.add(new JLabel());
        dialog.add(saveButton);

        dialog.setVisible(true);
    }

    /**
     * adds rows to contact list
     */
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Contact contact : contacts) {
            tableModel.addRow(new Object[]{contact.getName(), contact.getfilesize(), contact.getfiletype()});
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AddressBookApplication addressBook = new AddressBookApplication();
                addressBook.setVisible(true);
            }
        });
    }
}

class Contact {
    private String filename;
    private String filesize;
    private String filetype;

    /**
     *
     * @param filename->contains file name
     * @param filesize->contains file size
     * @param filetype->contains file type
     */
    public Contact(String filename, String filesize, String filetype) {
        this.filename = filename;
        this.filesize = filesize;
        this.filetype = filetype;
    }

    /**
     *
     * @return->filename
     */
    public String getName() {
        return filename;
    }

    /**
     *
     * @param filename
     */
    public void setFileName(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return-> filesize
     */
    public String getfilesize() {
        return filesize;
    }

    /**
     *
     * @param filesize
     */
    public void setfilesize(String filesize) {
        this.filesize = filesize;
    }

    /**
     *
     * @return filetype
     */
    public String getfiletype() {
        return filetype;
    }

    /**
     *
     * @param filetype
     */
    public void setfiletype(String filetype) {
        this.filetype = filetype;
    }
}
