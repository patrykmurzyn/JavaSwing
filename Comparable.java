package com.hello;

import java.util.Comparator;

public interface Comparable<Student> {

    int compareByName(Student s);

    int compareByPoints(Student s);
}