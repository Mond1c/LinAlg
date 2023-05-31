package org.linalg;


import org.linalg.expression.parser.Parser;
import org.linalg.expression.parts.Const;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            final Scanner scanner = new Scanner(System.in);
            System.out.println(new Parser().parse(scanner.nextLine()).evaluate().toLatexString());

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}