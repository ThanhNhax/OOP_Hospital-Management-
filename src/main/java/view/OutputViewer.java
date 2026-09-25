package view;

public class OutputViewer {
    public static void Notice(String text){
        System.out.println(text);
    }

    public static void listEmptyErr(String objName){
        Notice("Error: " + objName + " list is empty!!");
    }

    public static void unFind(String objName, String key){
        Notice("Error: Cannot find " + objName + " matching: '" + key + "'");
    }

    public static void Successfully(String function, String objName){
        Notice("Notice: " + function + " " + objName + " successfully!");
    }

    public static void Cancelled(String function, String objName){
        Notice("Notice: " + function + " " + objName + " was cancelled!");
    }

    public static void errChoice(int ori, int dest){
        Notice("Error: Invalid choice! Please enter from " + ori + " to " + dest);
    }
    
    public static void printMainMenu() {
        Notice("\n========== HOSPITAL MANAGEMENT SYSTEM ==========");
        Notice("1. Add a new patient");
        Notice("2. Delete a patient");
        Notice("3. Edit/Update a patient");
        Notice("4. Search for patients");
        Notice("5. Display a patient");
        Notice("6. Display all patients");
        Notice("7. Save patients to file");
        Notice("8. Load patients from file");
        Notice("0. Exit");
    }
}
