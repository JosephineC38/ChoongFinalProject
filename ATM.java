import java.util.Scanner;
public class ATM {
    Scanner scan = new Scanner(System.in);
    Customer customer = new Customer();

    private String decision;
    public ATM () {}

    public void start() {
        //Asks for customer's name
        System.out.println("Welcome to the bank!");
        System.out.print("What is your name: ");
        String name = scan.nextLine();
        customer.setName(name);
        System.out.println(name + ", what a wonderful name");

        //Asks for customer's PIN
        System.out.print("What is your PIN: ");
        int pin = scan.nextInt();
        customer.setPin(pin);
        menu();
    }

    public void menu() {

        System.out.println("Welcome to the New York Bank!");
        System.out.print("Enter your PIN: ");
        int userPin = scan.nextInt();
        while (userPin != customer.getPin()) {
            System.out.println("Incorrect PIN. Please try again.");
            System.out.print("Enter your PIN: ");
            userPin = scan.nextInt();
        }
        System.out.println("\n---------\nMain menu \n1. Withdraw money \n2. Deposit money \n3. Transfer money between accounts \n4. Get account balances \n5. Change PIN \n6. Exit \n---------");
        System.out.print("Pick an option: ");
        decision = scan.nextLine();
        boolean quit = false;
        do {
            switch (decision) {
                case "1":
                    System.out.println("1");
                    quit = true;
                    break;
                case "2":
                    System.out.println("2");
                    quit = true;
                    break;
                case "3":
                    System.out.println("3");
                    quit = true;
                    break;
                case "4":
                    System.out.println("4");
                    quit = true;
                    break;
                case "5":
                    System.out.println("5");
                    quit = true;
                    break;
                case "6":
                    System.out.println("6");
                    quit = true;
                    break;
                default:
                    System.out.println("That is an invalid choice. Please try again.");
                    System.out.print("Pick an option: ");
                    decision = scan.nextLine();
                    break;
            }
        } while (!quit);
    }


}

