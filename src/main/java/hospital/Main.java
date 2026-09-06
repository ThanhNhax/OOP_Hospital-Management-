package hospital;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST VALIDATION ===");
        
        // Test 1: Non-empty string
        String name = util.Validator.readNonEmptyString("Enter name (cannot be empty): ");
        System.out.println("Entered name: " + name);
        
        // Test 2: Gender
        String gender = util.Validator.readGender("Enter gender (Male/Female): ");
        System.out.println("Entered gender: " + gender);
        
        // Test 3: Confirm Y/N
        boolean confirm = util.Validator.readConfirm("Do you want to continue? (Y/N): ");
        System.out.println("Confirmation: " + (confirm ? "Accepted" : "Declined"));
        
        // Test 4: Format Date
        System.out.println("Current date format: " + util.Validator.formatDate(new java.util.Date()));
    }
}
//