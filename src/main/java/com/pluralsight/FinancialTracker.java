package com.pluralsight;

import java.io.*;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final File FILE = new File(FILE_NAME);
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to TransactionApp");
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            System.out.print("Enter: ");
            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        scanner.close();
    }

    // This method should load transactions from a file with the given file name.
    // If the file does not exist, it should be created.
    // The transactions should be stored in the `transactions` ArrayList.
    // Each line of the file represents a single transaction in the following format:
    // <date>|<time>|<description>|<vendor>|<amount>
    // For example: 2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
    // After reading all the transactions, the file should be closed.
    // If any errors occur, an appropriate error message should be displayed.
    public static void loadTransactions(String fileName) {
        if (FILE.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.isEmpty())
                    {
                        String[] str = line.split("\\|");
                        transactions.add( new Transaction(
                                // Day, Month, Year
                                LocalDate.parse(str[0], DATE_FORMATTER), // Date
                                LocalTime.parse(str[1], TIME_FORMATTER), // Time
                                str[2], // Description
                                str[3], // Vendor
                                new BigDecimal(str[4]) // Payment / Deposit
                        ));
                    }
                }
                bufferedReader.close();

                // Sort by Date
                transactions.sort(Comparator.comparing(Transaction::getDate).reversed());
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        } else {
            System.out.println("\nError: " + fileName + " does not exist; creating a new file now.\n");
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE));
                bufferedWriter.write("");
                System.out.println("File created successfully");
                bufferedWriter.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }

    // This method should prompt the user to enter the date, time, description, vendor, and amount of a deposit.
    // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
    // The amount should be a positive number.
    // After validating the input, a new `Transaction` object should be created with the entered values.
    // The new deposit should be added to the `transactions` ArrayList.
    private static void addDeposit(Scanner scanner) {
        promptTransaction(scanner, false);
    }

    // This method should prompt the user to enter the date, time, description, vendor, and amount of a payment.
    // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
    // The amount received should be a positive number then transformed to a negative number.
    // After validating the input, a new `Transaction` object should be created with the entered values.
    // The new payment should be added to the `transactions` ArrayList.
    private static void addPayment(Scanner scanner) {
        promptTransaction(scanner, true);
    }

    // Custom Method
    private static void promptTransaction(Scanner scanner, boolean isPayment) {
        String s = isPayment ? "payment" : "deposit";
        try {
            System.out.print("Enter the date in the format \"yyyy-MM-dd\": ");
            LocalDate date = LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);

            System.out.print("Enter the time in the format \"HH:mm:ss\": ");
            LocalTime time = LocalTime.parse(scanner.nextLine().trim(), TIME_FORMATTER);

            System.out.print("Enter the payment descriptor: ");
            String description = scanner.nextLine().trim();

            System.out.print("Enter the vendor: ");
            String vendor = scanner.nextLine().trim();

            System.out.print("Enter the amount of the " + s + " : ");
            BigDecimal payment = scanner.nextBigDecimal();

            if (isPayment) { // If this is a payment, make sure the value remains negative.
                if (payment.signum() > 0)
                {
                    payment = payment.negate();
                }
            } else { // If this is a deposit, make sure the value remains positive.
                if (payment.signum() < 0) {
                    payment = payment.abs();
                }
            }

            scanner.nextLine();

            Transaction transaction = new Transaction(date, time, description, vendor, payment);
            writeTransaction(transaction);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    // Custom Method
    private static void writeTransaction(Transaction transaction) {
        // Adding to list of transactions
        transactions.add(transaction);
        transactions.sort(Comparator.comparing(Transaction::getDate).reversed());

        // Writing to csv file
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE, true));
            bufferedWriter.newLine();
            bufferedWriter.write(transaction.toString());
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nLedger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            System.out.print("Enter: ");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                    System.out.println("Returning Home");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    // This method should display a table of all transactions in the `transactions` ArrayList.
    // The table should have columns for date, time, description, vendor, and amount.
    private static void displayLedger() {
        System.out.println();

        printTableHead("Transaction Ledger");

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (printTransactionsTable(i, transaction)) break;
        }
    }

    // This method should display a table of all deposits in the `transactions` ArrayList.
    // The table should have columns for date, time, description, vendor, and amount.
    private static void displayDeposits() {
        printTableHead("Transaction Deposits");

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (!transaction.isPayment) {
                if (printTransactionsTable(i, transaction)) break;
            }
        }
    }

    // This method should display a table of all payments in the `transactions` ArrayList.
    // The table should have columns for date, time, description, vendor, and amount.
    private static void displayPayments() {
        printTableHead("Transaction Payments");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.isPayment) {
                if (printTransactionsTable(i, transaction)) break;
            }
        }
    }

    // Reports
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nReports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            System.out.print("Enter: ");

            String input = scanner.nextLine().trim();

            switch (input) {

                // Generate a report for all transactions within the current month,
                // including the date, time, description, vendor, and amount for each transaction.
                case "1":
                    System.out.println();
                    filterTransactionsByDate(YearMonth.from(LocalDate.now()).atDay(1),
                            LocalDate.now());
                    break;

                // Generate a report for all transactions within the previous month,
                // including the date, time, description, vendor, and amount for each transaction.
                case "2":
                    System.out.println();
                    filterTransactionsByDate(YearMonth.from(LocalDate.now().minusMonths(1)).atDay(1),
                                             YearMonth.from(LocalDate.now().minusMonths(1)).atEndOfMonth());
                    break;

                // Generate a report for all transactions within the current year,
                // including the date, time, description, vendor, and amount for each transaction.
                case "3":
                    Year thisYear = Year.from(LocalDate.now());

                    System.out.println();
                    MonthDay januaryFirst = MonthDay.of(1, 1);
                    filterTransactionsByDate(Year.now().atMonthDay(MonthDay.of(1, 1)), LocalDate.now());
                    break;

                case "4":
                    Year lastYear = Year.from(LocalDate.now().minusYears(1));

                    System.out.println();
                    filterTransactionsByDate(lastYear.atMonthDay(MonthDay.of(1, 1)),
                            lastYear.atMonth(Month.DECEMBER).atEndOfMonth());
                    break;

                // Prompt the user to enter a vendor name, then generate a report for all transactions
                // with that vendor, including the date, time, description, vendor, and amount for each transaction.
                case "5":
                    System.out.println();
                    System.out.print("Enter a vendor: ");
                    filterTransactionsByVendor(scanner.nextLine().trim());
                    break;

                case "6":

                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    // This method filters the transactions by date and prints a report to the console.
    // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
    // The method loops through the transactions list and checks each transaction's date against the date range.
    // Transactions that fall within the date range are printed to the console.
    // If no transactions fall within the date range, the method prints a message indicating that there are no results.
    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        boolean isEmpty = true; // Checks if there are no transactions in the loop.
        for (Transaction transaction : transactions) {
            if (transaction.getDate().minusDays(1).isAfter(startDate) &&
                    transaction.getDate().plusDays(1).isBefore(endDate)) {
                System.out.println(transaction);
                isEmpty = false;
            }
        }

        if (isEmpty) {
            System.out.println("There are no results.");
        }
    }

    // This method filters the transactions by vendor and prints a report to the console.
    // It takes one parameter: vendor, which represents the name of the vendor to filter by.
    // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
    // Transactions with a matching vendor name are printed to the console.
    // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    private static void filterTransactionsByVendor(String vendor) {
        printTableHead("Transactions Organized by Vendor");

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (vendor.equalsIgnoreCase(transaction.getVendor())) {
                if (printTransactionsTable(i, transaction)) break;
            }
        }
    }

    // Table Construction Methods
    private static void printTableLine(int isTop) {
        switch (isTop) {
            case 0 -> { // Top
                System.out.print("┌");
                for (int i = 0; i < 204; i++) {
                    System.out.print("─");
                }
                System.out.println("┐");
            }
            case 1 -> { // Middle-Top
                System.out.print("├");
                for (int i = 0; i < 204; i++) {
                    if ((i+1) % 41 == 0) {
                        System.out.print("┬");
                    }
                    else {
                        System.out.print("─");
                    }
                }
                System.out.println("┤");
            }
            case 2 -> { // Middle Surrounded
                System.out.print("├");
                for (int i = 0; i < 204; i++) {
                    if ((i+1) % 41 == 0) {
                        System.out.print("┼");
                    }
                    else {
                        System.out.print("─");
                    }
                }
                System.out.println("┤");
            }
            case 3 -> { // Bottom
                System.out.print("└");
                for (int i = 0; i < 204; i++) {
                    if ((i+1) % 41 == 0) {
                        System.out.print("┴");
                    }
                    else {
                        System.out.print("─");
                    }
                }
                System.out.println("┘");
            }
        }
    }

    private static String centerText(String text, int width) {
        String out = String.format("%"+width+"s%s%"+width+"s", "",text,"");
        float mid = (out.length()/2);
        float start = mid - (width/2);
        float end = start + width;
        return out.substring((int)start, (int)end);
    }

    private static void printTableHead(String title) {
        printTableLine(0);
        System.out.println("│"+ centerText(title, 204) + "│");
        printTableLine(1);
        System.out.println("│"+ centerText("DATE", 40) + "│" + centerText("TIME", 40) + "│"
                + centerText("DESCRIPTION", 40) + "│" + centerText("VENDOR", 40)
                + "│" + centerText("AMOUNT", 40) + "│");
        printTableLine(2);
    }

    private static boolean printTransactionsTable(int i, Transaction transaction) {
        System.out.print("│");
        System.out.print(centerText(transaction.getDate().toString(), 40) + "│");
        System.out.print(centerText(transaction.getTime().toString(), 40) + "│");
        System.out.print(centerText(transaction.getDescription(), 40) + "│");
        System.out.print(centerText(transaction.getVendor(), 40) + "│");
        System.out.println(centerText(transaction.getAmount().toString(), 40) + "│");
        if (i == transactions.size()-1) {
            printTableLine(3);
            return true;
        }
        printTableLine(2);
        return false;
    }
}