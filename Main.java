package com.hello;

    public class Main {

    public static void main(String[] args) {

        ClassContainer groups = new ClassContainer();
        groups.addClass("1B", 12);
        groups.addClass("3A", 28);

        Student s1 = new Student("Adam",      "Nowak",       StudentCondition.chory,       2003);
        Student s2 = new Student("Jakub",     "Kowalski",    StudentCondition.odrabiajacy, 2004);
        Student s3 = new Student("Adrian",    "Wisniewski",  StudentCondition.obecny,      2003);
        Student s4 = new Student("Paulina",   "Lewandowska", StudentCondition.obecny,      2003);
        Student s5 = new Student("Kinga",     "Majewska",    StudentCondition.obecny,      2003);
        Student s6 = new Student("Artur",     "Kowalski",    StudentCondition.odrabiajacy, 2004);
        Student s7 = new Student("Mateusz",   "Zielinski",   StudentCondition.obecny,      2003);
        Student s8 = new Student("Karol",     "Szymanski",   StudentCondition.chory,       2003);
        Student s9 = new Student("Dawid",     "Kaminski",    StudentCondition.obecny,      2003);
        Student s10 = new Student("Marcin",   "Wozniak",     StudentCondition.odrabiajacy, 2005);
        Student s11 = new Student("Dominika", "Wilk",        StudentCondition.obecny,      2003);
        Student s12 = new Student("Paulina",   "Lewandowska", StudentCondition.obecny,      2003);
        Student s13 = new Student("Kinga",     "Majewska",    StudentCondition.obecny,      2003);
        Student s14 = new Student("Artur",     "Kowalski",    StudentCondition.odrabiajacy, 2004);
        Student s15 = new Student("Mateusz",   "Zielinski",   StudentCondition.obecny,      2003);
        Student s16 = new Student("Karol",     "Szymanski",   StudentCondition.chory,       2003);

        groups.getClass("1B").addStudent(s1);
        groups.getClass("1B").addStudent(s2);
        groups.getClass("1B").addStudent(s3);
        groups.getClass("1B").addStudent(s4);
        groups.getClass("1B").addStudent(s5);
        groups.getClass("1B").addStudent(s6);
        groups.getClass("1B").addStudent(s7);
        groups.getClass("1B").addStudent(s8);
        groups.getClass("1B").addStudent(s9);
        groups.getClass("1B").addStudent(s10);
        groups.getClass("3A").addStudent(s11);
        groups.getClass("3A").addStudent(s12);
        groups.getClass("3A").addStudent(s13);
        groups.getClass("3A").addStudent(s14);
        groups.getClass("3A").addStudent(s15);
        groups.getClass("3A").addStudent(s16);

        groups.getClass("1B").addPoints(s1, 8);
        groups.getClass("1B").addPoints(s2, 4);
        groups.getClass("1B").addPoints(s3, 12);
        groups.getClass("1B").addPoints(s4, 20);
        groups.getClass("1B").addPoints(s5, 1);
        groups.getClass("1B").addPoints(s6, 9);
        groups.getClass("1B").addPoints(s7, 0);
        groups.getClass("1B").addPoints(s8, 33);
        groups.getClass("1B").addPoints(s9, 5);
        groups.getClass("1B").addPoints(s10, 17);
        groups.getClass("3A").addPoints(s11, 19);
        groups.getClass("3A").addPoints(s12, 1);
        groups.getClass("3A").addPoints(s13, 6);
        groups.getClass("3A").addPoints(s14, 13);
        groups.getClass("3A").addPoints(s15, 5);
        groups.getClass("3A").addPoints(s16, 26);


        groups.summary();

        //c1.searchPatrial("a");
        //c1.countByCondition(StudentCondition.obecny);

        //groups.getClass("1B").sortByPoints();


        //c1.max();

        new SwingGUI(groups);
    }
}
