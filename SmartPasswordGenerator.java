

import java.util.*;

public class SmartPasswordGenerator {
    
    // Method to generate password from user data
    public static String generatePassword(String name, String pan, String dob) {
        String password = "";
        
        // Take first 3 letters of name
        String namePart = name.substring(0, Math.min(3, name.length())).toLowerCase();
        
        // Take last 4 characters of PAN
        String panPart = pan.substring(Math.max(0, pan.length() - 4));
        
        // Take year from DOB (format: DD/MM/YYYY)
        String[] dobParts = dob.split("/");
        String year = dobParts[2];
        
        // Generate random number between 10 and 99
        Random rand = new Random();
        int randomNum = rand.nextInt(90) + 10;
        
        // Combine all parts
        password = namePart + panPart + year + "@" + randomNum;
        
        return password;
    }
    
    // Method to check password strength
    public static String checkStrength(String password) {
        int score = 0;
        
        // Check length
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;
        
        // Check for uppercase letters
        if (password.matches(".*[A-Z].*")) score++;
        
        // Check for lowercase letters
        if (password.matches(".*[a-z].*")) score++;
        
        // Check for digits
        if (password.matches(".*\\d.*")) score++;
        
        // Check for special characters
        if (password.matches(".*[@#$%^&+=].*")) score++;
        
        // Determine strength
        if (score >= 5) {
            return "STRONG";
        } else if (score >= 3) {
            return "MEDIUM";
        } else {
            return "WEAK";
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== SMART PASSWORD GENERATOR ===\n");
        
        // Accept user details
        System.out.print("Enter your Name: ");
        String name = sc.nextLine();
        
        System.out.print("Enter your PAN Number: ");
        String pan = sc.nextLine();
        
        System.out.print("Enter your Date of Birth (DD/MM/YYYY): ");
        String dob = sc.nextLine();
        
        // Generate password
        String password = generatePassword(name, pan, dob);
        
        // Display generated password
        System.out.println("\n--- Generated Password ---");
        System.out.println("Password: " + password);
        
        // Check and display strength
        String strength = checkStrength(password);
        System.out.println("Strength: " + strength);
        
        sc.close();
    }
}