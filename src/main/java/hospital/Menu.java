package hospital;

import util.Language;
import util.Validation;

// Chỉ điều hướng: in menu, đọc lựa chọn, gọi Manager / Language.
public class Menu {
    private final DepartmentManager deptManager;
    private final DoctorManager docManager;

    public Menu() {
        this.deptManager = new DepartmentManager();
        this.docManager = new DoctorManager(deptManager);
    }

    // Vòng lặp menu chính cho đến khi chọn Thoát
    public void run() {
        while (true) {
            showMainMenu();
            String choice = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_CHOICE),
                    Language.EMPTY_CHOICE);
            switch (choice) {
                case "1": // Show information
                    handleSubmenu("SHOW", () -> docManager.showAll(), () -> deptManager.showAll());
                    break;
                case "2": // Add new
                    handleSubmenu("ADD", () -> docManager.addFromInput(), () -> deptManager.addFromInput());
                    break;
                case "3": // Update
                    handleSubmenu("UPDATE", () -> docManager.updateFromInput(), () -> deptManager.updateFromInput());
                    break;
                case "4": // Delete
                    handleSubmenu("DELETE", () -> docManager.deleteFromInput(), () -> deptManager.deleteFromInput(docManager));
                    break;
                case "5": // Search
                    handleSubmenu("SEARCH", () -> docManager.searchFromInput(), () -> deptManager.searchFromInput());
                    break;
                case "6": // File IO
                    while (true) {
                        System.out.println("\n--- FILE I/O ---");
                        System.out.println("1. Load from file (Đọc dữ liệu)");
                        System.out.println("2. Save to file (Lưu dữ liệu)");
                        System.out.println("0. Back (Quay lại)");
                        String fileChoice = Validation.readNonEmptyString(Language.get(Language.PROMPT_CHOICE), Language.EMPTY_CHOICE);
                        if (fileChoice.equals("1")) {
                            deptManager.loadFromFile("department.dat");
                            docManager.loadFromFile("doctor.dat");
                            System.out.println(">> Loaded from department.dat and doctor.dat");
                        } else if (fileChoice.equals("2")) {
                            deptManager.saveToFile("department.dat");
                            docManager.saveToFile("doctor.dat");
                            System.out.println(">> Saved to department.dat and doctor.dat");
                        } else if (fileChoice.equals("0")) {
                            break;
                        } else {
                            System.out.println(Language.get(Language.INVALID_CHOICE));
                        }
                    }
                    break;
                case "0": // Exit
                    System.out.println(Language.get(Language.GOODBYE));
                    return;
                default: 
                    System.out.println(Language.get(Language.INVALID_CHOICE));
                    break;
            }
        }
    }

    // In các mục menu chính
    private void showMainMenu() {
        System.out.println("\n" + Language.get(Language.MENU_TITLE));
        System.out.println(Language.get(Language.MENU_SHOW));
        System.out.println(Language.get(Language.MENU_ADD));
        System.out.println(Language.get(Language.MENU_UPDATE));
        System.out.println(Language.get(Language.MENU_DELETE));
        System.out.println(Language.get(Language.MENU_SEARCH));
        System.out.println(Language.get(Language.MENU_BUILD_DATA));
        System.out.println(Language.get(Language.MENU_EXIT));
    }

    // Generic submenu cho việc chọn Doctor / Department
    private void handleSubmenu(String actionName, Runnable docAction, Runnable deptAction) {
        while (true) {
            System.out.println("\n--- " + actionName + " ---");
            System.out.println(Language.get(Language.SUBMENU_DOC_OPT));
            System.out.println(Language.get(Language.SUBMENU_DEPT_OPT));
            System.out.println(Language.get(Language.SUBMENU_BACK));
            String choice = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_CHOICE),
                    Language.EMPTY_CHOICE);
            switch (choice) {
                case "1":
                    docAction.run();
                    break;
                case "2":
                    deptAction.run();
                    break;
                case "0":
                    return;
                default:
                    System.out.println(Language.get(Language.INVALID_CHOICE));
            }
        }
    }
}
