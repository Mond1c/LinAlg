import expression.parser.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            final Scanner scanner = new Scanner(System.in);
            System.out.println(new Parser().parse(scanner.nextLine()).evaluate());

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}