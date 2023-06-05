package org.linalg;


import org.linalg.expression.parser.Parser;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (final Scanner scanner = new Scanner(System.in)) {
            String request = scanner.nextLine();
            if (request.contains(" at ")) {
                String[] parts = request.split("at");
                if (!parts[1].contains("=")) {
                    throw new RuntimeException("You need to use =");
                }
                String[] variables = parts[1].split("=");
                if (variables.length != 2) {
                    throw new RuntimeException("You can use only one variable, example: at x = 3");
                }
                if (!variables[0].trim().equals("x")) {
                    throw new RuntimeException("You can use only x variable!");
                }
                BigDecimal x = new BigDecimal(variables[1].trim());
                System.out.println(new Parser().parse(parts[0]).evaluate(x).toLatexString());
            } else {
                System.out.println(new Parser().parse(request).evaluate().toLatexString());
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}