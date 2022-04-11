package com.hello;

public enum StudentCondition {
    odrabiajacy {
        @Override public String toString() {
            return "odrabiajacy";
        }

    },
    chory {
        @Override public String toString() {
            return "chory";
        }
    },
    nieobecny {
        @Override public String toString() {
            return "nieobecny";
        }
    },
    obecny {
        @Override public String toString() {
            return "obecny";
        }
    },
    brak {
        @Override public String toString() {
            return "brak";
        }
    };

}