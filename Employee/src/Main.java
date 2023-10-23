import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Enter message: ");
            String message = input.nextLine();
            System.out.println(message);
        }
    }
}