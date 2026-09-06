package hospital;

import util.Language;
import util.Validation;

// Chỉ điều hướng: in menu, đọc lựa chọn, gọi Manager / Language / test.
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
                case "1":
                    departmentSubmenu();
                    break;
                case "2":
                    doctorSubmenu();
                    break;
                case "3":
                    testValidation();
                    break;
                case "4":
                    Language.choose();
                    break;
                case "0":
                    System.out.println(Language.get(Language.GOODBYE));
                    return;
                default:
                    System.out.println(Language.get(Language.INVALID_CHOICE));
            }
        }
    }

    // In các mục menu chính theo ngôn ngữ đang chọn
    private void showMainMenu() {
        System.out.println("\n" + Language.get(Language.MENU_TITLE));
        System.out.println(Language.get(Language.MENU_DEPT));
        System.out.println(Language.get(Language.MENU_DOCTOR));
        System.out.println(Language.get(Language.MENU_TEST));
        System.out.println(Language.get(Language.MENU_LANGUAGE));
        System.out.println(Language.get(Language.MENU_EXIT));
    }

    // Submenu khoa: thêm khoa hoặc quay lại menu chính
    private void departmentSubmenu() {
        while (true) {
            System.out.println("\n" + Language.get(Language.SUBMENU_DEPT_TITLE));
            System.out.println(Language.get(Language.SUBMENU_DEPT_ADD));
            System.out.println(Language.get(Language.SUBMENU_BACK));
            String choice = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_CHOICE),
                    Language.EMPTY_CHOICE);
            switch (choice) {
                case "1":
                    deptManager.addFromInput();
                    break;
                case "0":
                    return;
                default:
                    System.out.println(Language.get(Language.INVALID_CHOICE));
            }
        }
    }

    // Submenu bác sĩ: thêm bác sĩ hoặc quay lại menu chính
    private void doctorSubmenu() {
        while (true) {
            System.out.println("\n" + Language.get(Language.SUBMENU_DOC_TITLE));
            System.out.println(Language.get(Language.SUBMENU_DOC_ADD));
            System.out.println(Language.get(Language.SUBMENU_BACK));
            String choice = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_CHOICE),
                    Language.EMPTY_CHOICE);
            switch (choice) {
                case "1":
                    docManager.addFromInput();
                    break;
                case "0":
                    return;
                default:
                    System.out.println(Language.get(Language.INVALID_CHOICE));
            }
        }
    }

    // Test các hàm Validation: chuỗi không rỗng, giới tính, Y/N, format ngày
    private void testValidation() {
        System.out.println(Language.get(Language.TEST_TITLE));

        String name = Validation.readNonEmptyString(
                Language.get(Language.PROMPT_TEST_NAME),
                Language.EMPTY_NAME);
        System.out.println(Language.get(Language.LABEL_ENTERED_NAME) + name);

        String gender = Validation.readGender(
                Language.get(Language.PROMPT_TEST_GENDER),
                Language.EMPTY_GENDER);
        System.out.println(Language.get(Language.LABEL_ENTERED_GENDER) + gender);

        boolean confirm = Validation.readConfirm(
                Language.get(Language.PROMPT_TEST_CONFIRM),
                Language.EMPTY_CONFIRM);
        System.out.println(Language.get(Language.LABEL_CONFIRMATION)
                + (confirm
                        ? Language.get(Language.LABEL_ACCEPTED)
                        : Language.get(Language.LABEL_DECLINED)));

        System.out.println(Language.get(Language.LABEL_DATE_FORMAT) + Validation.formatDate(new java.util.Date()));
    }
}
