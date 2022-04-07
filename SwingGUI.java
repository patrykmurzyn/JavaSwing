package com.hello;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SwingGUI {

    ClassContainer groups;

    JFrame f;
    JPanel panel;
    DefaultTableModel tableModel1;
    JTable j1;
    DefaultTableModel tableModel2;
    JTable j2;

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

        cellSelectionModel1.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

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

        cellSelectionModel2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                int selectedRow = j2.getSelectedRow();

                System.out.println("Selected: " + selectedRow);
            }

        });


        JScrollPane sp2 = new JScrollPane(j2);

        panel.add(sp2, BorderLayout.CENTER);

        Panel panel2 = new Panel();
        panel2.setLayout(new BorderLayout());

        Panel panel3 = new Panel();
        panel3.setLayout(new GridLayout());

        JButton b1 = new JButton("Dodaj grupę");
        panel3.add(b1);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(j1.getSelectionModel().isSelectionEmpty()) {
                    JTextField nameField = new JTextField(15);
                    JTextField ammountField = new JTextField(5);

                    JPanel myPanel = new JPanel();
                    myPanel.add(new JLabel("Nazwa grupy:"));
                    myPanel.add(nameField);
                    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                    myPanel.add(new JLabel("Maksymalny rozmiar:"));
                    myPanel.add(ammountField);
                    j1.getSelectionModel().clearSelection();
                    tableModel2.setRowCount(0);
                    cellSelectionModel1.clearSelection();

                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Dodawanie grupy", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        groups.addClass(nameField.getText(), Integer.valueOf(ammountField.getText()));
                        RefreshGroups();
                    }
                }
            }
        });

        JButton b2 = new JButton("Edytuj grupę");
        panel3.add(b2);

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(j1.getSelectionModel().isSelectionEmpty()) {

                    JTextField nameField = new JTextField(15);
                    JTextField ammountField = new JTextField(5);

                    JPanel myPanel = new JPanel();
                    myPanel.add(new JLabel("Nazwa grupy:"));
                    myPanel.add(nameField);
                    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
                    myPanel.add(new JLabel("Maksymalny rozmiar:"));
                    myPanel.add(ammountField);
                    j1.getSelectionModel().clearSelection();
                    tableModel2.setRowCount(0);

                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Dodawanie grupy", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        groups.removeClass(nameField.getText());
                        RefreshGroups();
                    }
                }

            }
        });

        JButton b3 = new JButton("Usuń grupę");
        panel3.add(b3);
        JButton b4 = new JButton("Dodaj studenta");
        panel3.add(b4);

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                    String condition[] = {
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

                        groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0)).addStudent(s);

                        ShowStudentsForSelectedGroup(j1.getSelectedRow());


                    }
                }

            }
        });

        JButton b5 = new JButton("Edutuj studenta");
        panel3.add(b5);

        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!j2.getSelectionModel().isSelectionEmpty()) {

                    JTextField imieField = new JTextField(12);
                    JTextField nazwiskoField = new JTextField(12);
                    JTextField rokField = new JTextField(3);
                    JTextField punktyField = new JTextField(3);


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

                    String condition[] = {
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

                        Class c = groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0));

                        c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).setImie(imieField.getText());
                        c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).setNazwisko(nazwiskoField.getText());
                        c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).setRok_urodzenia(Integer.parseInt(rokField.getText()));
                        c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).setIlosc_punktow(Integer.parseInt(punktyField.getText()));
                        c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()).setStatus(sc);

                        ShowStudentsForSelectedGroup(j1.getSelectedRow());
                    }
                }
            }
        });

        JButton b6 = new JButton("Usuń studenta");
        panel3.add(b6);

        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!j2.getSelectionModel().isSelectionEmpty()) {

                    Class c = groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0));

                   c.removeStudent(c.search(tableModel2.getValueAt(j2.getSelectedRow(), 1).toString()));

                    ShowStudentsForSelectedGroup(j1.getSelectedRow());
                }
            }
        });

        Panel panel4 = new Panel();
        panel4.setLayout(new GridLayout());

        JButton b7 = new JButton("Sortuj studentów [alfabetycznie]");
        panel4.add(b7);

        b7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!j1.getSelectionModel().isSelectionEmpty()) {
                    groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0)).sortByName();

                    ShowStudentsForSelectedGroup(j1.getSelectedRow());
                }
            }
        });

        JButton b8 = new JButton("Sortuj studentów [punkty]");
        panel4.add(b8);

        b8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!j1.getSelectionModel().isSelectionEmpty()) {
                    groups.groups.get(tableModel1.getValueAt(j1.getSelectedRow(), 0)).sortByPoints();

                    ShowStudentsForSelectedGroup(j1.getSelectedRow());
                }
            }
        });

        panel2.add(panel4, BorderLayout.CENTER);

        panel2.add(panel3, BorderLayout.NORTH);

        panel.add(panel2, BorderLayout.SOUTH);

        f.add(panel);

        f.setVisible(true);
        f.setSize(920,450);
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

    private void RefreshGroups() {
        //tableModel1.setRowCount(0);

        int i = 0;
        for(Map.Entry<String, Class> entry: groups.groups.entrySet()) {

            tableModel1.insertRow(i, new Object[] {entry.getKey(), Math.round(entry.getValue().procentowe_zapelnienie * 100.0)/100.0 + "%"});
            i++;
        }

    }
}
