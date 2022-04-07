package com.hello;

public class Student implements Comparable<Student>{
    static int id = 0;
    String imie;
    String nazwisko;
    StudentCondition status;
    int rok_urodzenia;
    double ilosc_punktow;

    public Student(String imie, String nazwisko, StudentCondition status, int rok_urodzenia) {
        this.id = this.id;
        this.id++;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.status = status;
        this.rok_urodzenia = rok_urodzenia;
        this.ilosc_punktow = 0;
    }

    public Student() {
        this.id = this.id;
        this.id++;
        this.imie = "brak";
        this.nazwisko = "brak";
        this.status = StudentCondition.brak;
        this.rok_urodzenia = 0;
        this.ilosc_punktow = 0;
    }

    public String print() {
        return "Student{" +
                "Imie='" + imie + '\'' +
                ", Nazwisko='" + nazwisko + '\'' +
                ", rok_urodzenia=" + rok_urodzenia +
                ", ilosc_punktow=" + ilosc_punktow +
                '}';
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setStatus(StudentCondition status) {
        this.status = status;
    }

    public void setRok_urodzenia(int rok_urodzenia) {
        this.rok_urodzenia = rok_urodzenia;
    }

    public void setIlosc_punktow(double ilosc_punktow) {
        this.ilosc_punktow = ilosc_punktow;
    }

    @Override
    public int compareByName(Student s) {
        return this.nazwisko.compareTo(s.nazwisko);
    }

    @Override
    public int compareByPoints(Student s) {
        return Double.compare(this.ilosc_punktow, s.ilosc_punktow);
    }
}
