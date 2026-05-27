package TextSteganography;

import java.io.*;
import java.util.*;

public class TextSteganography {
    
    static Scanner sc = new Scanner(System.in);
    
    // Convert message to binary
    public static String textToBinary(String text) {1
        StringBuilder binary = new StringBuilder();
        for (char c : text.toCharArray()) {
            String binaryChar = Integer.toBinaryString(c);
            while (binaryChar.length() < 8) {
                binaryChar = "0" + binaryChar;
            }
            binary.append(binaryChar);
        }
        return binary.toString();
    }
    
    // Convert binary to text
    public static String binaryToText(String binary) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            if (i + 8 <= binary.length()) {
                String byteStr = binary.substring(i, i + 8);
                int charCode = Integer.parseInt(byteStr, 2);
                text.append((char) charCode);
            }
        }
        return text.toString();
    }
    
    // Encode message into cover text
    public static String encodeMessage(String coverText, String secretMessage) {
        String binaryMsg = textToBinary(secretMessage);
        StringBuilder encoded = new StringBuilder();
        
        int binaryIndex = 0;
        for (int i = 0; i < coverText.length() && binaryIndex < binaryMsg.length(); i++) {
            char c = coverText.charAt(i);
            encoded.append(c);
            
            if (c == ' ') {
                if (binaryMsg.charAt(binaryIndex) == '1') {
                    encoded.append(' '); // Double space for 1
                }
                binaryIndex++;
            }
        }
        
        // Append remaining cover text
        if (encoded.length() < coverText.length()) {
            encoded.append(coverText.substring(encoded.length()));
        }
        
        return encoded.toString();
    }
    
    // Decode message from encoded text
    public static String decodeMessage(String encodedText) {
        StringBuilder binaryMsg = new StringBuilder();
        
        for (int i = 0; i < encodedText.length() - 1; i++) {
            if (encodedText.charAt(i) == ' ' && encodedText.charAt(i + 1) == ' ') {
                binaryMsg.append('1');
                i++;
            } else if (encodedText.charAt(i) == ' ') {
                binaryMsg.append('0');
            }
        }
        
        return binaryToText(binaryMsg.toString());
    }
    
    // Save to file
    public static void saveToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            System.out.println("File saved: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
    
    // Load from file
    public static String loadFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return content.toString();
    }
    
    public static void main(String[] args) {
        System.out.println("=== TEXT STEGANOGRAPHY SYSTEM ===\n");
        
        while (true) {
            System.out.println("1. Encode Secret Message");
            System.out.println("2. Decode Secret Message");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            if (choice == 1) {
                // Encoding
                System.out.print("Enter cover text: ");
                String coverText = sc.nextLine();
                
                System.out.print("Enter secret message: ");
                String secretMsg = sc.nextLine();
                
                String encodedText = encodeMessage(coverText, secretMsg);
                saveToFile("encoded.txt", encodedText);
                
                System.out.println("\n--- Encoded Text (Visible content looks normal) ---");
                System.out.println(encodedText);
                System.out.println("\n Message hidden successfully!\n");
                
            } else if (choice == 2) {
                // Decoding
                String encodedText = loadFromFile("encoded.txt");
                if (encodedText.isEmpty()) {
                    System.out.println("No encoded file found. Encode a message first.\n");
                    continue;
                }
                
                String hiddenMsg = decodeMessage(encodedText);
                System.out.println("\n--- Hidden Message ---");
                System.out.println(hiddenMsg);
                System.out.println();
                
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option!\n");
            }
        }
    }
}
