package ExpenseTracker;

import java.io.*;
import java.util.*;

class Expense implements Serializable {
    double amount;
    String category;
    String date;
    
    Expense(double amount, String category, String date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    
    public String toString() {
        return date + " | " + category + " | ₹" + amount;
    }
}

public class ExpenseTracker {
    
    static ArrayList<Expense> expenses = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    
    // Add expense
    public static void addExpense() {
        System.out.print("Enter amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();
        
        System.out.print("Enter category (Food/Travel/Shopping/Bills/Other): ");
        String category = sc.nextLine();
        
        System.out.print("Enter date (DD/MM/YYYY): ");
        String date = sc.nextLine();
        
        expenses.add(new Expense(amount, category, date));
        System.out.println("Expense added successfully!\n");
    }
    
    // Display all expenses
    public static void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.\n");
            return;
        }
        
        System.out.println("\n--- All Expenses ---");
        for (Expense e : expenses) {
            System.out.println(e);
        }
        System.out.println();
    }
    
    // Monthly expense report
    public static void monthlyReport() {
        System.out.print("Enter month (1-12): ");
        int month = sc.nextInt();
        System.out.print("Enter year (YYYY): ");
        int year = sc.nextInt();
        
        double total = 0;
        System.out.println("\n--- Report for " + month + "/" + year + " ---");
        
        for (Expense e : expenses) {
            String[] dateParts = e.date.split("/");
            int expMonth = Integer.parseInt(dateParts[1]);
            int expYear = Integer.parseInt(dateParts[2]);
            
            if (expMonth == month && expYear == year) {
                System.out.println(e);
                total += e.amount;
            }
        }
        
        System.out.println("Total Expenses: ₹" + total + "\n");
    }
    
    // Highest expense category
    public static void highestCategory() {
        HashMap<String, Double> categoryTotal = new HashMap<>();
        
        for (Expense e : expenses) {
            categoryTotal.put(e.category, 
                categoryTotal.getOrDefault(e.category, 0.0) + e.amount);
        }
        
        if (categoryTotal.isEmpty()) {
            System.out.println("No expenses to analyze.\n");
            return;
        }
        
        String highestCat = "";
        double highestAmt = 0;
        
        for (Map.Entry<String, Double> entry : categoryTotal.entrySet()) {
            if (entry.getValue() > highestAmt) {
                highestAmt = entry.getValue();
                highestCat = entry.getKey();
            }
        }
        
        System.out.println("Highest Expense Category: " + highestCat);
        System.out.println("Total Spent: ₹" + highestAmt + "\n");
    }
    
    // Save data to file
    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("expenses.dat"))) {
            oos.writeObject(expenses);
            System.out.println("Data saved successfully!\n");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    // Load data from file
    public static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("expenses.dat"))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                ArrayList<?> rawList = (ArrayList<?>) obj;
                ArrayList<Expense> loaded = new ArrayList<>();
                for (Object item : rawList) {
                    if (item instanceof Expense) {
                        loaded.add((Expense) item);
                    }
                }
                expenses = loaded;
                System.out.println("Data loaded successfully!\n");
            } else {
                System.out.println("Saved data has unexpected format. Starting fresh.\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.\n");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        loadData();
        
        while (true) {
            System.out.println("=== PERSONAL EXPENSE TRACKER ===");
            System.out.println("1. Add Expense");
            System.out.println("2. Display All Expenses");
            System.out.println("3. Monthly Expense Report");
            System.out.println("4. Highest Expense Category");
            System.out.println("5. Save & Exit");
            System.out.print("Choose option: ");
            
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1: addExpense(); break;
                case 2: displayExpenses(); break;
                case 3: monthlyReport(); break;
                case 4: highestCategory(); break;
                case 5: 
                    saveData();
                    System.out.println("Thank you for using Expense Tracker!");
                    return;
                default: System.out.println("Invalid option!\n");
            }
        }
    }
}
