import java.util.Scanner;
public class ATM {
    Scanner scan = new Scanner(System.in);
    Customer customer = new Customer();

    private String decision;
    private int id;

    private int receipt;
    public ATM () {
        id = 10000;
        receipt = 0;
    }

    public void start() {
        //Asks for customer's name
        System.out.println("Welcome to the New York Bank!");
        System.out.print("What is your name: ");
        String name = scan.nextLine();
        customer.setName(name);
        System.out.println("Hello, " + name + ". I hope you enjoy your visit!");

        //Asks for customer's PIN
        System.out.println("Your PIN has to be 4 numbers");
        System.out.print("What is your PIN: ");
        int pin = scan.nextInt();
        String strPin = Integer.toString(pin);
        while (strPin.length() != 4) {
            System.out.println("Your PIN isn't 4 numbers. Please try again.");
            System.out.print("What is your PIN: ");
            pin = scan.nextInt();
            strPin = Integer.toString(pin);
        }
        customer.setPin(pin);
        System.out.println("Thank you for creating an account");
        menu();
    }

    public void menu() {
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
                    changePin();
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
            }
        } while (!quit);
    }

    private void changePin() {
        System.out.println("Your current PIN is " + customer.getPin() + ".");
        System.out.print("Enter your new PIN: ");
        int newPin = scan.nextInt();
        customer.setPin(newPin);
        receipt = 5;
        printReceipt();
    }

    private void printReceipt() {
        id++;
        System.out.println("----------");
        System.out.println("Receipt");
        System.out.println("----");
        System.out.println("Account Transaction: " + id);
        if(receipt == 5) {
            System.out.println("  - Changed PIN. The new PIN is " + customer.getPin());
        }
        System.out.println("----------\n");

        System.out.print("Would you like to do anything else: ");
        String choice = scan.nextLine();
        choice = choice.toLowerCase();
        while (!choice.equals("no") && !choice.equals("yes")) {
                System.out.println("That isn't an option. Please try again.");
                System.out.print("Would you like to do anything else: ");
                choice = scan.nextLine();
                choice = choice.toLowerCase();

        }
        if (choice.equals("yes")) {
            System.out.println("Returning to menu");
            menu();
        } else if (choice.equals("no")) {
            System.out.println("Thank you for being a customer at New York Bank. Come again!");
        }



    }


}

