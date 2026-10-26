package com.java.traning.strings;

public class StringDemo {

    public static void main(String[] args) {

        // ---------- 1. Ways of creating String values ----------
        // a) String literal: stored in the string pool and reused
        String literal1 = "Java";
        String literal2 = "Java";

        // b) new String(...): always creates a new object on the heap
        String fromNew = new String("Java");

        // c) From a char array
        char[] chars = {'J', 'a', 'v', 'a'};
        String fromChars = new String(chars);

        // e) Concatenation of values: a compile-time constant or a runtime result
        String concatenated = "Ja" + "va";                  // constant, pooled
        String part = "Ja";
        String runtimeConcat = part + "va";                 // created at runtime

        // f) Text block (Java 15+): multi-line string literal
        String block = """
                Line one
                Line two
                """;
        System.out.println("Literal          : " + literal1);
        System.out.println("new String       : " + fromNew);
        System.out.println("From char[]      : " + fromChars);
        System.out.println("Text block       : " + block.strip());


        // Identity: literals share one pooled object, new String does not
        System.out.println("\nliteral1 == literal2       : " + (literal1 == literal2));
        System.out.println("literal1 == fromNew        : " + (literal1 == fromNew));
        System.out.println("literal1.equals(fromNew)   : " + literal1.equals(fromNew));
        System.out.println("concatenated == \"Java\"     : " + (concatenated == "Java"));

        //JVM creates a new String object on the heap to hold the result.
        // That object has the same characters as "Java", but it’s a different object, so == (which compares references)
        System.out.println("runtimeConcat == \"Java\"    : " + (runtimeConcat == "Java"));
        System.out.println("runtimeConcat.equals(\"Java\")   :" + runtimeConcat.equals("Java"));   // true


        // ---------- 2. Important String methods ----------
        String text = "  Hello, Java World  ";

        System.out.println("\nlength()          : " + text.length());
        System.out.println("trim()            : [" + text.trim() + "]");
        System.out.println("strip()           : [" + text.strip() + "]");
        System.out.println("isBlank()         : " + "   ".isBlank());
        System.out.println("charAt(2)         : " + text.charAt(2));
        System.out.println("substring(2, 7)   : " + text.substring(2, 7));
        System.out.println("indexOf(\"Java\")   : " + text.indexOf("Java"));
        System.out.println("contains(\"World\") : " + text.contains("World"));
        System.out.println("startsWith(\"  H\") : " + text.startsWith("  H"));
        System.out.println("toUpperCase()     : " + text.toUpperCase());
        System.out.println("toLowerCase()     : " + text.toLowerCase());
        System.out.println("replace('l', 'L') : " + text.replace('l', 'L'));
        System.out.println("repeat(2)         : " + "ab".repeat(2));
        System.out.println("equalsIgnoreCase  : " + "JAVA".equalsIgnoreCase("java"));
        System.out.println("compareTo(\"B\")    : " + "A".compareTo("B"));
        System.out.println("split(\",\")        : " + String.join("|", "a,b,c".split(",")));
        System.out.println("join              : " + String.join("-", "2026", "10", "25"));
        System.out.println("toCharArray()     : " + "abc".toCharArray().length + " chars");
    }
}