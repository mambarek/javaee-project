package com.it2go.masterdata;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Proxy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Country implements Comparable<Country> {
    private final String code;
    private final String name;

    @Override
    public int compareTo(Country o) {
        return Collator.getInstance(Locale.GERMANY).compare(name, o.name);
//        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "[" + code + "] " + name;
    }

    public static List<Country> getAllCountries(Locale locale) {
        return Arrays.stream(Continent.values()).flatMap(cont -> cont.getCountries(locale).stream()).collect(Collectors.toList());
    }

    static List<Integer> get() {
        final List<Integer> realList = new ArrayList<>();
        return (List<Integer>) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{List.class}, (proxy, method, args) -> {
            System.out.printf("Calling %s(%s)%n", method.getName(), args == null ? "" : Arrays.toString(args));
            return method.invoke(realList, args);
        });
    }

    public static void main(String[] args) {
        List<Integer> l = get();
        System.out.println("l instanceof List = " + (l instanceof List));
        System.out.println("l.getClass() = " + l.getClass());
        System.out.println("l.getClass().getSuperclass() = " + l.getClass().getSuperclass());
        System.out.println("l.getClass().getInterfaces() = " + Arrays.toString(l.getClass().getInterfaces()));
        l.add(55);
        System.out.println(l.get(0));
        System.out.println(l.toString());
    }
}
