// Java Final
// Adan Villa
// Andrea Marquez
// Rakshya Kafle
// OCCC Spring 2026
// Advanced Java


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class AMV_PersonGUI extends JFrame {
    private ArrayList<PersonVilla> personList = new ArrayList<>();
    private DefaultListModel<PersonVilla> listModel = new DefaultListModel<>();
    private JList<PersonVilla> displayList = new JList<>(listModel);
    
    private File currentFile = null;
    private boolean isModified = false;
    private boolean isConstructing = false;
    private JMenuItem itemSave, itemSaveAs;

    public AMV_PersonGUI() {
        setTitle("AMV Person Management System");
        setSize(600, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        setupMenu();
        setupLayout();
        
        // Window listener for the "X" button save prompt
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { handleExit(); }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem itemNew = new JMenuItem("New");
        JMenuItem itemOpen = new JMenuItem("Open...");
        itemSave = new JMenuItem("Save");
        itemSaveAs = new JMenuItem("Save as...");
        JMenuItem itemExit = new JMenuItem("Exit");

        fileMenu.add(itemNew);
        fileMenu.add(itemOpen);
        fileMenu.addSeparator();
        fileMenu.add(itemSave);
        fileMenu.add(itemSaveAs);
        fileMenu.addSeparator();
        fileMenu.add(itemExit);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("Display help options"));

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Action Listeners
        itemNew.addActionListener(e -> handleNew());
        itemOpen.addActionListener(e -> handleOpen());
        itemSave.addActionListener(e -> handleSave());
        itemSaveAs.addActionListener(e -> handleSaveAs());
        itemExit.addActionListener(e -> handleExit());
    }

    private void setupLayout() {
        add(new JScrollPane(displayList), BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        JButton btnAdd = new JButton("Add Person");
        JButton btnDelete = new JButton("Delete Person");

        btnAdd.addActionListener(e -> createPersonProcess());
        btnDelete.addActionListener(e -> {
            int idx = displayList.getSelectedIndex();
            if (idx != -1) {
                personList.remove(idx);
                listModel.remove(idx);
                isModified = true;
            }
        });

        panel.add(btnAdd);
        panel.add(btnDelete);
        add(panel, BorderLayout.SOUTH);
    }

    private void createPersonProcess() {
        // Disable save options while constructing
        isConstructing = true;
        updateMenuState();
        
        try {
            String[] options = {"Person", "RegisteredPerson", "OCCCPerson"};
            int type = JOptionPane.showOptionDialog(this, "Select Person Type", "Create",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (type == -1) return;

            String first = JOptionPane.showInputDialog("First Name:");
            String last = JOptionPane.showInputDialog("Last Name:");
            String dateStr = JOptionPane.showInputDialog("Enter Date (MM DD YYYY):");
            
            if (first == null || last == null || dateStr == null) return;

            String[] dParts = dateStr.split(" ");
            OCCCDate dob = new OCCCDate(Integer.parseInt(dParts[1]), Integer.parseInt(dParts[0]), Integer.parseInt(dParts[2]));

            PersonVilla p;
            if (type == 1) {
                String govID = JOptionPane.showInputDialog("Government ID:");
                p = new RegisteredPerson(first, last, dob, govID);
            } else if (type == 2) {
                String govID = JOptionPane.showInputDialog("Government ID:");
                String studentID = JOptionPane.showInputDialog("Student ID:");
                p = new OCCCPerson(first, last, dob, govID, studentID);
            } else {
                p = new PersonVilla(first, last, dob);
            }

            personList.add(p);
            listModel.addElement(p);
            isModified = true;

        } catch (InvalidOCCCDateException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Date!"); // Required popup[cite: 1]
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error in input.");
        } finally {
            isConstructing = false;
            updateMenuState();
        }
    }

    private void updateMenuState() {
        itemSave.setEnabled(!isConstructing);
        itemSaveAs.setEnabled(!isConstructing);
    }

    private void handleSave() {
        if (currentFile == null) handleSaveAs();
        else serializeData(currentFile);
    }

    private void handleSaveAs() {
        JFileChooser jfc = new JFileChooser();
        if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = jfc.getSelectedFile();
            serializeData(currentFile);
        }
    }

    private void serializeData(File f) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(personList);
            isModified = false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Save Failed.");
        }
    }

    private void handleOpen() {
        if (isModified && !checkSaveFirst()) return;
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()))) {
                personList = (ArrayList<PersonVilla>) ois.readObject();
                listModel.clear();
                for (PersonVilla p : personList) listModel.addElement(p);
                currentFile = jfc.getSelectedFile();
                isModified = false;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Open Failed.");
            }
        }
    }

    private void handleNew() {
        if (isModified && !checkSaveFirst()) return;
        personList.clear();
        listModel.clear();
        currentFile = null;
        isModified = false;
    }

    private boolean checkSaveFirst() {
        int res = JOptionPane.showConfirmDialog(this, "Save changes before continuing?", "Unsaved Progress", JOptionPane.YES_NO_CANCEL_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            handleSave();
            return true;
        }
        return (res == JOptionPane.NO_OPTION);
    }

    private void handleExit() {
        if (isModified) {
            if (checkSaveFirst()) System.exit(0);
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new AMV_PersonGUI();
    }
}