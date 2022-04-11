package com.hello;

import java.util.HashMap;
import java.util.Map;

public class ClassContainer {
    Map<String, Class> groups = new HashMap<String, Class>();

    public Class getClass(String key) {
        return groups.get(key);
    }

    public void addClass(String name, int ammount) {

        if(groups.containsKey(name)) {
            System.out.println("Klasa o takiej nazwie już instnieje");
        } else {
            Class c = new Class(name, ammount);
            groups.put(name, c);
            System.out.println("Dodano nowa klase do mapy grup: " + name);
        }

    }

    public void removeClass(String name) {
        groups.remove(name);
        System.out.println("Usunieto klase z mapy grup: " + name);
    }

    public void findEmpty() {
        System.out.println("Puste grupy:");
        for(Map.Entry<String, Class> entry: groups.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    public void summary() {
        System.out.println("Podsumowanie grup:");
        for(Map.Entry<String, Class> entry: groups.entrySet()) {

            entry.getValue().procentowe_zapelnienie = (float)entry.getValue().students.size()/(float)entry.getValue().maksymalna_ilosc_studentow *100f;

            System.out.println(entry.getKey() + ", zapelnienie: " + entry.getValue().procentowe_zapelnienie + "%");


        }
    }


}