package com.hello;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class SwingGUI {

    ClassContainer groups;

    JFrame f;
    JPanel panel;
    DefaultTableModel tableModel1;
    JTable j1;
    DefaultTableModel tableModel2;
    JTable j2;
    boolean selectStudent = true;

    SwingGUI(ClassContainer groups) {

        this.groups = groups;

        f = new JFrame("Dziennik");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //lista grup
        tableModel1 = new DefaultTableModel();
        j1 = new JTable(tableModel1);
        tableModel1.addColumn("Nazwa Grupy");
        tableModel1.addColumn("Zapełnienie");

        {
            int i = 0;
            for(Map.Entry<String, Class> entry: groups.groups.entrySet()) {

                tableModel1.insertRow(i, new Object[] {entry.getKey(), Math.round(entry.getValue().procentowe_zapelnienie * 100.0)/100.0 + "%"});
                i++;
            }
        }

        j1.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel1 = j1.getSelectionModel();
        j1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel1.addListSelectionListener(e -> {

            if(selectStudent) {
                int selectedRow = j1.getSelectedRow();

                System.out.println("Selected: " + selectedRow);

                ShowStudentsForSelectedGroup(selectedRow);
            }

        });

        JScrollPane sp1 = new JScrollPane(j1);
        panel.add(sp1, BorderLayout.WEST);

        //lista studentow

        tableModel2 = new DefaultTableModel();
        j2 = new JTable(tableModel2);
        tableModel2.addColumn("Imię");
        tableModel2.addColumn("Nazwisko");
        tableModel2.addColumn("Punkty");

        j2.setBounds(30, 40, 200, 300);

        j2.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel2 = j2.getSelectionModel();
        j2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel2.addListSelectionListener(e -> {

            int selectedRow = j2.getSelectedRow();

            System.out.println("Selected: " + selectedRow);
        });


        JScrollPane sp2 = new JScrollPane(j2);

        panel.add(sp2, BorderLayout.CENTER);

        Panel panel2 = new Panel();
        panel2.setLayout(new BorderLayout());

        Panel panel3 = new Panel();
        panel3.setLayout(new GridLayout());

        JButton b1 = new JButton("Dodaj grupę");
        panel3.add(b1);

        b1.addActionListener(e -> {

            JTextField nameField = new JTextField(15);
            JTextField ammountField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Nazwa grupy:"));
            myPanel.add(nameField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Maksymalny rozmiar:"));
            myPanel.add(ammountField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Dodawanie grupy", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                groups.addClass(nameField.getText(), Integer.parseInt(ammountField.getText()));
                tableModel1.addRow(new Object[] {nameField.getText(), "0.00%"});

            }
        });

        JButton b2 = new JButton("Edytuj grupę");
        panel3.add(b2);

        b2.addActionListener(e -> {
            if(!j1.getSelectionModel().isSelectionEmpty()) {

                JTextField nameField = new JTextField(15);
                JTextField ammountField = new JTextField(5);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Nazwa grupy:"));
                myPanel.add(nameField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Maksymalny rozmiar:"));
                myPanel.add(ammountField);

                Class c = groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0));

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Edytowanie grupy", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION && Integer.parseInt(ammountField.getText()) >= c.students.size()) {

                    groups.groups.remove(c.nazwa_grupy);

                    c.nazwa_grupy = nameField.getText();
                    c.maksymalna_ilosc_studentow = Integer.parseInt(ammountField.getText());

                    c.procentowe_zapelnienie = (float)c.students.size()/(float)c.maksymalna_ilosc_studentow *100f;

                    tableModel1.setValueAt(c.nazwa_grupy, j1.getSelectedRow(), 0);
                    tableModel1.setValueAt(Math.round(c.procentowe_zapelnienie * 100.0)/100.0 + "%", j1.getSelectedRow(), 1);

                    groups.groups.put(c.nazwa_grupy, c);
                }
            }

        });

        JButton b3 = new JButton("Usuń grupę");
        panel3.add(b3);

        b3.addActionListener(e -> {
            if(!j1.getSelectionModel().isSelectionEmpty()) {


                groups.removeClass(String.valueOf(tableModel1.getValueAt(j1.getSelectedRow(), 0)));
                selectStudent = false;
                tableModel1.removeRow(j1.getSelectedRow());
                tableModel2.setRowCount(0);

            }
            selectStudent = true;

        });

        JButton b4 = new JButton("Dodaj studenta");
        panel3.add(b4);

        b4.addActionListener(e -> {
            if(!j1.getSelectionModel().isSelectionEmpty()) {

                JTextField imieField = new JTextField(12);
                JTextField nazwiskoField = new JTextField(12);
                JTextField rokField = new JTextField(3);

                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Imię:"));
                myPanel.add(imieField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Nazwisko:"));
                myPanel.add(nazwiskoField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Rok urodzenia:"));
                myPanel.add(rokField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer

                String[] condition = {
                        "obecny", "odrabiający", "chory", "nieobecny"
                };

                JList l = new JList(condition);

                myPanel.add(new JLabel("Status:"));
                myPanel.add(l);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Dodawanie studenta", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                    StudentCondition sc = StudentCondition.brak;
                    if(l.getSelectedIndex() == 0) sc = StudentCondition.obecny;
                    else if(l.getSelectedIndex() == 1) sc = StudentCondition.odrabiajacy;
                    else if(l.getSelectedIndex() == 2) sc = StudentCondition.chory;
                    else if(l.getSelectedIndex() == 3) sc = StudentCondition.nieobecny;

                    Student s = new Student(imieField.getText(), nazwiskoField.getText(), sc, Integer.parseInt(rokField.getText()));

                    Class c = groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0));

                    c.addStudent(s);

                    ShowStudentsForSelectedGroup(j1.getSelectedRow());

                    c.procentowe_zapelnienie = (float)c.students.size()/(float)c.maksymalna_ilosc_studentow *100f;

                    tableModel1.setValueAt(Math.round(c.procentowe_zapelnienie * 100.0)/100.0 + "%", j1.getSelectedRow(), 1);

                }
            }

        });

        JButton b5 = new JButton("Edutuj studenta");
        panel3.add(b5);

        b5.addActionListener(e -> {
            if(!j2.getSelectionModel().isSelectionEmpty()) {

                Class c = groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0));

                JTextField imieField = new JTextField(c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).imie,12);
                JTextField nazwiskoField = new JTextField(c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).nazwisko,12);
                JTextField rokField = new JTextField(String.valueOf(c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).rok_urodzenia),3);
                JTextField punktyField = new JTextField(String.valueOf(c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).ilosc_punktow),3);


                JPanel myPanel = new JPanel();
                myPanel.add(new JLabel("Imię:"));
                myPanel.add(imieField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Nazwisko:"));
                myPanel.add(nazwiskoField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Rok urodzenia:"));
                myPanel.add(rokField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                myPanel.add(new JLabel("Punkty:"));
                myPanel.add(punktyField);
                myPanel.add(Box.createHorizontalStrut(15)); // a spacer

                String[] condition = {
                        "obecny", "odrabiający", "chory", "nieobecny"
                };

                JList l = new JList(condition);

                myPanel.add(new JLabel("Status:"));
                myPanel.add(l);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Edytowanie studenta", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                    StudentCondition sc = StudentCondition.brak;
                    if(l.getSelectedIndex() == 0) sc = StudentCondition.obecny;
                    else if(l.getSelectedIndex() == 1) sc = StudentCondition.odrabiajacy;
                    else if(l.getSelectedIndex() == 2) sc = StudentCondition.chory;
                    else if(l.getSelectedIndex() == 3) sc = StudentCondition.nieobecny;

                    Student stud = new Student(imieField.getText(), nazwiskoField.getText(), sc, Integer.parseInt(rokField.getText()));

                    stud.ilosc_punktow = Double.valueOf(punktyField.getText());
                    c.removeStudent(c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()));

                    c.addStudent(stud);

                    tableModel2.setValueAt(stud.imie, j2.getSelectedRow(), 0);
                    tableModel2.setValueAt(stud.nazwisko, j2.getSelectedRow(), 1);
                    tableModel2.setValueAt(stud.ilosc_punktow, j2.getSelectedRow(), 2);
                }
            }
        });

        JButton b6 = new JButton("Usuń studenta");
        panel3.add(b6);

        b6.addActionListener(e -> {
            if(!j2.getSelectionModel().isSelectionEmpty()) {

                Class c = groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0));

                c.removeStudent(c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()));

                ShowStudentsForSelectedGroup(j1.getSelectedRow());

                ShowStudentsForSelectedGroup(j1.getSelectedRow());

                c.procentowe_zapelnienie = (float)c.students.size()/(float)c.maksymalna_ilosc_studentow *100f;

                tableModel1.setValueAt(Math.round(c.procentowe_zapelnienie * 100.0)/100.0 + "%", j1.getSelectedRow(), 1);

            }
        });

        Panel panel4 = new Panel();
        panel4.setLayout(new GridLayout());

        JButton b7 = new JButton("Sortuj studentów [alfabetycznie]");
        panel4.add(b7);

        b7.addActionListener(e -> {
            if(!j1.getSelectionModel().isSelectionEmpty()) {
                groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0)).sortByName();

                ShowStudentsForSelectedGroup(j1.getSelectedRow());
            }
        });

        JButton b8 = new JButton("Sortuj studentów [punkty]");
        panel4.add(b8);

        b8.addActionListener(e -> {
            if(!j1.getSelectionModel().isSelectionEmpty()) {
                groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0)).sortByPoints();

                ShowStudentsForSelectedGroup(j1.getSelectedRow());
            }
        });

        Panel panel5 = new Panel();
        panel5.setLayout(new GridLayout());

        JTextField tf = new JTextField(15);
        panel5.add(tf);

        JButton b9 = new JButton("Szukaj");
        panel5.add(b9);

        b9.addActionListener(e -> {
            if(!j1.getSelectionModel().isSelectionEmpty()) {

                searchPatrialStudent(tf.getText());

            }
        });

        JButton b10 = new JButton("Szukaj");

        String[] condition = {
                "obecny", "odrabiający", "chory", "nieobecny"
        };

        JComboBox cb = new JComboBox(condition);

        b10.addActionListener(e -> {
            if(!j1.getSelectionModel().isSelectionEmpty()) {

                String s = String.valueOf(cb.getSelectedItem());
                StudentCondition sc = StudentCondition.brak;

                if(s == "obecny") sc = StudentCondition.obecny;
                else if(s == "odrabiający") sc = StudentCondition.odrabiajacy;
                else if(s == "chory") sc = StudentCondition.chory;
                else if(s == "nieobecny") sc = StudentCondition.nieobecny;

                searchStudentByCondition(sc);

            }
        });

        panel5.add(cb);
        panel5.add(b10);

        panel2.add(panel4, BorderLayout.CENTER);

        panel2.add(panel3, BorderLayout.NORTH);

        panel2.add(panel5, BorderLayout.SOUTH);

       // panel2.add(panel6, BorderLayout.SOUTH);

        panel.add(panel2, BorderLayout.SOUTH);

        f.add(panel);

        f.setVisible(true);
        f.setSize(905,450);
    }

    private void ShowStudentsForSelectedGroup(int index) {
        System.out.println(tableModel1.getValueAt(index, 0));

        tableModel2.setRowCount(0);

        for(int i = 0; i < groups.groups.get(tableModel1.getValueAt(index, 0)).students.size(); i++) {
            tableModel2.insertRow(i, new Object[] {groups.groups.get(tableModel1.getValueAt(index, 0)).students.get(i).imie,
                    groups.groups.get(tableModel1.getValueAt(index, 0)).students.get(i).nazwisko,
                    groups.groups.get(tableModel1.getValueAt(index, 0)).students.get(i).ilosc_punktow});
        }
    }

    private void searchPatrialStudent(String value) {
        tableModel2.setRowCount(0);
        boolean isFound = false;
        int i = 0;
        for(Student s : groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0)).students) {

            if(s.nazwisko.toLowerCase().contains(value.toLowerCase()) || s.imie.toLowerCase().contains(value.toLowerCase())) {
                tableModel2.insertRow(i, new Object[] {s.imie,
                        s.nazwisko,
                        s.ilosc_punktow});
                System.out.println(s.imie + " " + s.nazwisko);
                isFound = true;
                i++;
            }
        }
        if(!isFound) System.out.println("Nie znaleziono studenta!");
    }

    private void searchStudentByCondition(StudentCondition c) {
        tableModel2.setRowCount(0);
        boolean isFound = false;
        int i = 0;
        for(Student s : groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0)).students) {

            if(s.status == c) {
                tableModel2.insertRow(i, new Object[] {s.imie,
                        s.nazwisko,
                        s.ilosc_punktow});
                System.out.println(s.imie + " " + s.nazwisko);
                isFound = true;
                i++;
            }
        }
        if(!isFound) System.out.println("Nie znaleziono studenta!");
    }

    private void RefreshGroups() {
        tableModel1.setRowCount(0);
        int i = 0;
        for(Map.Entry<String, Class> entry: groups.groups.entrySet()) {

            tableModel1.insertRow(i, new Object[] {entry.getKey(), Math.round(entry.getValue().procentowe_zapelnienie * 100.0)/100.0 + "%"});
            i++;
        }

    }
}