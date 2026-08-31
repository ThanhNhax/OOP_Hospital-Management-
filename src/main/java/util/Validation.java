package util;

import java.util.Scanner;

public class Validation {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readNonEmptyString(String msg) {
        String result;
        while (true) {
            System.out.print(msg);
            result = scanner.nextLine().trim();
            if (!result.isEmpty()) {
                return result;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public static String readGender(String msg) {
        while (true) {
            String result = readNonEmptyString(msg);
            if (result.equalsIgnoreCase("Nam") || result.equalsIgnoreCase("Nu") || 
                result.equalsIgnoreCase("Male") || result.equalsIgnoreCase("Female") ||
                result.equalsIgnoreCase("M") || result.equalsIgnoreCase("F")) {
                return result;
            }
            System.out.println("Invalid gender. Please enter Male/Female or Nam/Nu.");
        }
    }

    public static boolean readConfirm(String msg) {
        while (true) {
            String result = readNonEmptyString(msg);
            if (result.equalsIgnoreCase("Y") || result.equalsIgnoreCase("Yes")) {
                return true;
            }
            if (result.equalsIgnoreCase("N") || result.equalsIgnoreCase("No")) {
                return false;
            }
            System.out.println("Invalid input. Please enter Y or N.");
        }
    }

    public static String formatDate(java.util.Date date) {
        if (date == null) return "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
