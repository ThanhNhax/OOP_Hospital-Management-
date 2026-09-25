package view;

import java.util.Scanner;
import java.util.function.Predicate;
import util.Utils;

public class InputValidator {
    private static final Scanner scn = new Scanner(System.in);

    public static String readString(String msg){
        OutputViewer.Notice(msg);
        return scn.nextLine().trim();
    }

    public static String readNonEmptyString(String msg){
        while (true){
            String input = readString(msg);
            if (!input.isEmpty()) return input;
            OutputViewer.Notice("Error: Input cannot be empty! Please try again.");
        }
    }

    public static String readUpdateString(String field, String oldValue){
        String input = readString("Enter new " + field + " (Enter to keep " + oldValue + "): ");
        return (input.isEmpty() ? oldValue : input);
    }

    public static String readValidString(String field, String oldValue, String mode, Predicate<String> condition, String errMsg){
        while (true){
            String prompt = mode.equalsIgnoreCase("update") ? field + " (Enter to keep " + oldValue + "): " : field + ": ";
            String input = readString(prompt);
            
            // Allow canceling only if it's explicitly stated in the prompt by the caller (like for ID)
            if (input.equals("0") && field.contains("0 to exit")) return null;
            if (input.equals("0") && field.contains("0 to cancel")) return null;

            if (mode.equalsIgnoreCase("update") && input.isEmpty()) return oldValue;
            if (condition.test(input)) return input;
            OutputViewer.Notice(errMsg);
        }
    }

    public static boolean readConfirm(String function){
        while (true){
            String input = readNonEmptyString("Do you want to " + function + "? (Y/N): ");
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("Yes")) return true;
            if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("No")) return false;
            OutputViewer.Notice("Error: Must enter Y/N or Yes/No!");
        }
    }

    public static String readGender(String field, String oldSex, String mode){
        String input = readValidString(field, oldSex, mode, g -> g.matches("(?i)^(Nam|Nu|M|F|Male|Female|1|2)$"), "Error: Must enter M/F, Male/Female or 1: Male, 2: Female");
        if (input.equalsIgnoreCase("Nam") || input.equalsIgnoreCase("M") || input.equalsIgnoreCase("Male") || input.equals("1")) return "Male";
        if (input.equalsIgnoreCase("Nu") || input.equalsIgnoreCase("F") || input.equalsIgnoreCase("Female") || input.equals("2")) return "Female";
        return input;
    }

    public static String readPhone(String msg, String oldPhone, String mode){
        while (true) {
            String prompt = mode.equalsIgnoreCase("update") ? msg + " (Enter to keep " + oldPhone + ", 0 to skip): " : msg + " (Enter 0 to skip): ";
            String input = readString(prompt);
            
            // Người dùng muốn giữ phím 0 để bỏ qua nhập số điện thoại
            if (input.equals("0")) return "None"; 
            
            if (mode.equalsIgnoreCase("update") && input.isEmpty()) return oldPhone;
            if (input.matches("^0\\d{9}$")) return input;
            
            OutputViewer.Notice("Error: Invalid phone number! Must be 10 digits starting with 0.");
        }
    }

    public static String readDate(String field, String oldDate, String mode){
        return readValidString(field, oldDate, mode, 
            dateStr -> dateStr.matches("^\\d{2}/\\d{2}/\\d{4}$") && Utils.parseDate(dateStr) != null, 
            "Error: Invalid date! Please enter valid date with full 4-digit year in dd/MM/yyyy format (e.g. 28/02/2026).");
    }

    public static String readAdmissionStatus(String field, String oldStatus, String mode){
        while (true){
            String prompt = mode.equalsIgnoreCase("update") 
                    ? field + " (1: Admitted, 2: Discharged, 3: In Treatment, Enter to keep " + oldStatus + "): " 
                    : field + " (1: Admitted, 2: Discharged, 3: In Treatment): ";
            String input = readString(prompt);
            
            if (mode.equalsIgnoreCase("update") && input.isEmpty()) return oldStatus;
            
            if (input.equals("1") || input.equalsIgnoreCase("Admitted")) return "Admitted";
            if (input.equals("2") || input.equalsIgnoreCase("Discharged")) return "Discharged";
            if (input.equals("3") || input.equalsIgnoreCase("In Treatment")) return "In Treatment";
            
            OutputViewer.Notice("Error: Must choose 1, 2, or 3!!");
        }
    }
}
