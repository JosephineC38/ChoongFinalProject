import java.util.Scanner;
public class ATM {
    Scanner scan = new Scanner(System.in);
    Customer customer = new Customer();
    Account checking = new Account("checking");
    Account savings = new Account("savings");

    private String decision;

    //The id used for the receipt. Used only for successful actions in withdraw, deposit, or transfer.
    private int id;
    private double moneyChange;
    private Account currentAccount;

    //Checks if an action (ex: withdraw) was successful or not. Based on the boolean, will print different text for the receipt.
    private boolean successfulAction;

    //Used for printReceipt(). Based on the int, prints different text for the receipt.
    private int receipt;

    public ATM () {
        id = 10000;
        receipt = 0;
        successfulAction = true;
    }

    /**
     * The only public method and it's used to run the program.
     * Welcomes the user to the bank. Afterwards, asks for their name and to create a PIN.
     */
    public void start() {
        //Asks for customer's name.
        System.out.println("Welcome to the New York Bank!");
        System.out.print("What is your name: ");
        String name = scan.nextLine();
        customer.setName(name);
        System.out.println("Hello, " + name + ". I hope you enjoy your visit!");

        //Asks for customer's PIN. It has to be exactly 4 numbers.
        System.out.println("Your PIN has to be 4 numbers.");
        System.out.print("What wil be your PIN: ");
        int pin = scan.nextInt();
        String strPin = Integer.toString(pin);
        while (strPin.length() != 4) {
            System.out.println("Your PIN isn't 4 numbers. Please try again.");
            System.out.print("What is your PIN: ");
            pin = scan.nextInt();
            strPin = Integer.toString(pin);
        }
        customer.setPin(pin);
        System.out.println("Thank you for creating an account.");
        menu();
    }

    /**
     * A helper method used in start().
     * Requires the correct PIN before allowing the user to select their choices.
     */
    private void menu() {
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
                    withdrawMoney();
                    quit = true;
                    break;
                case "2":
                    depositMoney();
                    quit = true;
                    break;
                case "3":
                    transferMoney();
                    quit = true;
                    break;
                case "4":
                    getAccountBalance();
                    quit = true;
                    break;
                case "5":
                    changePin();
                    quit = true;
                    break;
                case "6":
                    System.out.println("Thank you for being a customer at New York Bank " + customer.getName() + ". Come again!");
                    quit = true;
                    break;
                default:
                    if(decision.equals("")) {
                        decision = scan.nextLine();
                    } else {
                        System.out.println("That is an invalid choice. Please try again.");
                        System.out.print("Pick an option: ");
                        decision = scan.nextLine();
                    }
            }
        } while (!quit);
    }

    /**
     * A helper method used in menu() for Choice 1 "Withdraw money".
     * Used to withdraw the inputted amount from either account.
     * If the amount is invalid, immediately prints the receipt without changing the account's balance.
     */
private void withdrawMoney() {
    receipt = 1;
    System.out.println("(1) Savings Account \n(2) Checking Account");
    System.out.println("Choose an account to withdraw from.");
    System.out.print("WITHDRAW: ");
    chooseAccount();

    //Asking for the amount to withdraw. Will print the receipt if the withdrawal is over the account's balance.
    System.out.println("You can only withdraw $5 and $20 bills");
    System.out.print("How much would you like to withdraw: ");
    double withdraw = scan.nextDouble();
    moneyChange = withdraw;
    while(withdraw % 5 != 0 || currentAccount.getBalance() - withdraw < 0) {
        if(withdraw % 5 != 0) {
            System.out.println("Invalid amount. Please only withdraw a multiple of 5.");
            System.out.print("How much would you like to withdraw: ");
            withdraw = scan.nextDouble();
        } else {
            successfulAction = false;
            printReceipt();
        }
    }
    moneyChange = withdraw;

    //Asking for the amount of $5 bills to withdraw.
    System.out.print("Amount of $5 bills to withdraw: ");
    int fiveBill = scan.nextInt();
    while(withdraw - (fiveBill * 5) < 0) {
        System.out.println("That goes over your withdrawal amount. Please pick a lower amount of $5 bills.");
        System.out.print("Amount of $5 bills to withdraw: ");
        fiveBill = scan.nextInt();
    }

    //Asking for the amount of $20 bills to withdraw.
    System.out.print("Amount of $20 bills to withdraw: ");
    int twentyBill = scan.nextInt();
    while(withdraw - (fiveBill * 5)  - (twentyBill * 20) < 0) {
        System.out.println("That goes over your withdrawal amount. Please pick a lower amount of $20 bills.");
        System.out.print("Amount of $20 bills to withdraw: ");
        twentyBill = scan.nextInt();
    }

    currentAccount.subtractBalance(withdraw);
    id++;
    printReceipt();

}
   /**
     * A helper method used menu() for Choice 2 "Deposit money".
     * Used to deposit an inputted amount from either account and makes an ID.
     */
   private void depositMoney() {
     System.out.println("\nDEPOSIT MONEY\n----------");
     receipt = 2;
     System.out.print("(1) Checking \n(2) Savings \nWhat account would you like to deposit into: ");
     chooseAccount();
     System.out.print("What amount would you like to deposit: ");
     double choice = scan.nextDouble();
     moneyChange = choice;
     currentAccount.addBalance(moneyChange);
     System.out.println("----------");
     id++;
     printReceipt();
   }

    /**
     * A helper method used in menu() for Choice 3 "Transfer money between accounts".
     * The user will choose an account to transfer the money from. After inputting an amount, will change both accounts balances and makes an ID.
     */
   private void transferMoney() {
     System.out.println("(1) Savings Account \n(2) Checking Account");
     receipt = 3;
     System.out.print("FROM: ");
     chooseAccount();
     System.out.print("Enter the amount of money you would like transfer: ");
     double transfer = scan.nextDouble();
     if (currentAccount.getBalance() - transfer < 0) {
         successfulAction = false;
     } else {
         if(currentAccount == savings) {
             savings.subtractBalance(transfer);
             checking.addBalance(transfer);
         } else if (currentAccount == checking) {
             checking.subtractBalance(transfer);
             savings.addBalance(transfer);
         }
         id++;
     }
     moneyChange = transfer;
     printReceipt();


   }

    /**
     * A helper method used in menu() for Choice 4 "Get account balances".
     * Will print the savings and checking account balances.
     */
    private void getAccountBalance() {
        receipt = 4;
        System.out.println("\nACCOUNT BALANCE\n----------\nSavings Account: " + savings.getBalance() +
                "\nChecking Account: " + checking.getBalance() + "\n----------");
        printReceipt();
    }

    /**
     * A helper method used in menu() for Choice 5 "Change PIN".
     * The PIN's requirements are that it has to be 4 numbers and can't be the same as the previous PIN.
     */
    private void changePin() {
        receipt = 5;
        System.out.println("\n----------");
        System.out.println("Your current PIN is " + customer.getPin() + ".");
        System.out.println("Your PIN has to be 4 numbers and can't be the previous PIN.");
        System.out.print("Enter your new PIN: ");
        int pin = scan.nextInt();
        String strPin = Integer.toString(pin);
        while (strPin.length() != 4 || pin == customer.getPin()) {
            if(strPin.length() != 4 ) {
                System.out.println("Your PIN isn't 4 numbers. Please try again.");
            } else {
                System.out.println("Your PIN is the same as your previous PIN. Please try again.");
            }
            System.out.print("What is your PIN: ");
            pin = scan.nextInt();
            strPin = Integer.toString(pin);

        }
        System.out.println("----------");
        customer.setPin(pin);
        printReceipt();
    }

    /**
     * A helper method that prints the receipt.
     * Will print a different ID and receipt based on the previous method.
     */
    private void printReceipt() {
        System.out.println("\n---");
        System.out.println("Receipt");
        if (receipt == 1) {
            if(successfulAction) {
                System.out.println("Account Transaction: " + id);
            }
            if(currentAccount.getName().equals(savings)) {
                System.out.println("Savings Account: " + savings.getBalance());
            } else {
                System.out.println("Checking Account: " + checking.getBalance());
            }

            if(successfulAction) {
                System.out.println("  - Withdrawed $" + moneyChange + " from " + currentAccount.getName());
                System.out.println("  - Action was successful");
            } else {
                System.out.println("  - You do not have enough money in your account to withdraw $" + moneyChange);
            }

        } else if (receipt ==  2) {
            System.out.println("Account Transaction: " + id);
            System.out.println("\n-");
            if(currentAccount.getName().equals(savings)) {
                System.out.println("Savings Account: " + savings.getBalance());
            } else {
                System.out.println("Checking Account: " + checking.getBalance());
            }
            System.out.println("  - Deposited $" + moneyChange + " into " + currentAccount.getName());
            System.out.println("  - Action was successful");

        //Used for transferMoney(), Choice 3 "Transfer money between accounts". Checks if the action was successful or not.
        } else if (receipt == 3) {
            System.out.println("Account Transaction: " + id);
            if (successfulAction) {
                System.out.println("Savings Account: " + savings.getBalance() +
                        "\nChecking Account: " + checking.getBalance());
                System.out.println("  - Transferred $" + moneyChange + " into " + currentAccount.getName());
                System.out.println("  - Action was successful");
            } else {
                System.out.println("Savings Account: " + savings.getBalance() +
                        "\nChecking Account: " + checking.getBalance());
                System.out.println("  - You do not have enough money in your account to transfer $" + moneyChange);
            }

        //Used for getAccountBalance(), Choice 4 "Get account balances ".
        } else if (receipt == 4) {
            System.out.println("  - Checked savings and checking accounts balances.");
            System.out.println("  - Action was successful.");

        } else if (receipt == 5) {
            System.out.println("  - Changed PIN. The new PIN is " + customer.getPin() + ".");
            System.out.println("  - Action was successful.");
        }


        System.out.println("---");
        successfulAction = true;
        returnMenu();

    }

    /**
     * A helper method that gives the user the choice to go back to the main menu.
     */
    private void returnMenu() {
        System.out.print("Would you like to do anything else: ");
        String choice = scan.nextLine();
        choice = choice.toLowerCase();
        while (!choice.equals("no") && !choice.equals("yes")) {
            if(choice.equals("")) {
                choice = scan.nextLine();
                choice = choice.toLowerCase();
            } else {
                System.out.println("That is an invalid option. Please try again.");
                System.out.print("Would you like to do anything else: ");
                choice = scan.nextLine();
                choice = choice.toLowerCase();
            }

        }
        if (choice.equals("yes")) {
            System.out.println("Returning to menu");
            menu();
        } else if (choice.equals("no")) {
            System.out.println("Thank you for being a customer at New York Bank" + customer.getName() + ". Come again!");
        }
    }

    /**
     * A helper method that allows the user pick an account.
     */
    private void chooseAccount() {
        int choice = scan.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.print("Invalid choice. Please either pick from: \n(1) Savings Account \n(2) Checking Account \nFROM: ");
            choice = scan.nextInt();
        }
        if (choice == 1) {
            currentAccount = savings;
            System.out.println("You chose the Savings Account.");
        } else {
            currentAccount = checking;
            System.out.println("You chose the Checking Account.");
        }
    }

}


