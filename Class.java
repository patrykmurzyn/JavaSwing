package com.hello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class Class {

    String nazwa_grupy;
    ArrayList<Student> students = new ArrayList<Student>();
    int maksymalna_ilosc_studentow;
    float procentowe_zapelnienie;

    public Class(String nazwa_grupy, int maksymalna_ilosc_studentow) {
        this.nazwa_grupy = nazwa_grupy;
        this.maksymalna_ilosc_studentow = maksymalna_ilosc_studentow;
    }

    public void addStudent(Student s) {
        if(students.size() + 1 > maksymalna_ilosc_studentow) {
            System.out.println("Przekroczono limit studentow");
        } else if(students.contains(s)) {
            System.out.println("Student " + s.imie + " " + s.nazwisko + " juz istnieje w grupie " + this.nazwa_grupy);
        }
        else students.add(s);
    }



    public void removeStudent(Student s) {
        students.remove(s);
    }

    public void addPoints(Student s, double ocena) {
        if(!this.students.contains(s)){
            System.out.println("Student nie znajduje sie w tej grupie!");
        } else {
            s.ilosc_punktow += ocena;
            System.out.println("Dodano " + ocena + " do punktow " + s.imie + " " + s.nazwisko + ", aktualnie: " + s.ilosc_punktow);
        }
    }

    public void removePoints(Student s, double ocena) {
        if(!this.students.contains(s)){
            System.out.println("Student nie znajduje sie w tej grupie!");
        } else {
            s.ilosc_punktow += ocena;
            System.out.println("Odjeto " + ocena + " od punktow " + s.imie + " " + s.nazwisko + ", aktualnie: " + s.ilosc_punktow);
        }
    }

    public void getStudent(Student s) {
        if(s.ilosc_punktow == 0) {
            students.remove(s);
            System.out.println("Liczba punktow studenta rowna 0 - usuniety");
        } else if(!this.students.contains(s)){
            System.out.println("Student nie znajduje sie w tej grupie!");
        } else {
            s.print();
        }
    }



    public void changeCondition(Student s, StudentCondition d) {
        if(!this.students.contains(s)){
            System.out.println("Student nie znajduje sie w tej grupie!");
        } else {
            s.status = d;
            System.out.println("Zmieniono status " + s.imie + " " + s.nazwisko + " na " + s.status);
        }
    }

    public Student search(String naz) {
        boolean isFound = false;
        for(Student s : students) {
            if(s.nazwisko == naz) {
                System.out.println("Znaleziono studenta: " + s.nazwisko + s.imie);
                s.print();
                isFound = true;
                return s;
            }
        }
        if(!isFound) System.out.println("Brak studenta");
        return new Student();
    }

    public void searchPatrial(String value) {
        boolean isFound = false;
        for(Student s : students) {
            if(s.nazwisko.toLowerCase().contains(value.toLowerCase()) || s.imie.toLowerCase().contains(value.toLowerCase())) {
                System.out.println(s.imie + " " + s.nazwisko);
                isFound = true;
            }
        }
        if(!isFound) System.out.println("Nie znaleziono studenta!");
    }

    public void countByCondition(StudentCondition condition) {
        int counter = 0;
        for(Student s : students) {
            if(s.status == condition) counter++;
        }
        System.out.println("Liczba studentow o stanie: " + condition + ": " + counter);
    }

    public void summary() {
        System.out.println("Informacje o wszystkich studentach: ");
        int i = 1;
        for(Student s : students) {
            System.out.println(i + ") " + s.imie + " " + s.nazwisko + ", rok urodzenia: " + s.rok_urodzenia + ", status: " + s.status + ", liczba punktow: " + s.ilosc_punktow);
            i++;
        }
    }

    public void sortByName() {
        students.sort(Student::compareByName);
        System.out.println("Lista studentow zostala posortowane alfabetycznie po nazwiskach: ");
        this.summary();
    }

    public void sortByPoints() {
        students.sort(Student::compareByPoints);
        System.out.println("Lista studentow zostala posortowane rosnaco po liczbie punktow: ");
        this.summary();
    }

    public void max() {

        System.out.println("Najwiecej uzyskanych punktow: " + Collections.max(students, Student::compareByPoints));
    }

}