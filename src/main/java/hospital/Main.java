package hospital;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST VALIDATION ===");
        
        // Test 1: Non-empty string
        String name = util.Validation.readNonEmptyString("Enter name (cannot be empty): ");
        System.out.println("Entered name: " + name);
        
        // Test 2: Gender
        String gender = util.Validation.readGender("Enter gender (Male/Female): ");
        System.out.println("Entered gender: " + gender);
        
        // Test 3: Confirm Y/N
        boolean confirm = util.Validation.readConfirm("Do you want to continue? (Y/N): ");
        System.out.println("Confirmation: " + (confirm ? "Accepted" : "Declined"));
        
        // Test 4: Format Date
        System.out.println("Current date format: " + util.Validation.formatDate(new java.util.Date()));
    }
}
//