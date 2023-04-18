import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        System.out.println(parser.parse(scanner.nextLine()));
    }
}