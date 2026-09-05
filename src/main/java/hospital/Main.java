package hospital;

import java.util.Date;
import util.Language;
import util.Validation;

public class Main {
    // Quản lý danh sách khoa trong RAM suốt vòng đời chương trình
    private static final DepartmentManager deptManager = new DepartmentManager();

    // Điểm vào chương trình: UTF-8 console -> chọn ngôn ngữ -> vòng lặp menu
    public static void main(String[] args) {
        utils.setupUtf8Console(); // Gọi trước khi in menu để tiếng Việt hiện đúng dấu
        Language.choose(); // Chọn Tiếng Việt hoặc English trước khi vào menu
        while (true) {
            showMenu();
            String choice = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_CHOICE),
                    Language.EMPTY_CHOICE);
            switch (choice) {
                case "1":
                    addDepartment(); // Thêm khoa: kiểm tra trùng ID, gán createDate
                    break;
                case "2":
                    testValidation(); // Giữ menu test nhập liệu cũ
                    break;
                case "3":
                    Language.choose(); // Đổi ngôn ngữ bất kỳ lúc nào
                    break;
                case "0":
                    System.out.println(Language.get(Language.GOODBYE));
                    return;
                default:
                    System.out.println(Language.get(Language.INVALID_CHOICE));
            }
        }
    }

    // In menu chính theo file ngôn ngữ đang chọn
    private static void showMenu() {
        System.out.println("\n" + Language.get(Language.MENU_TITLE));
        System.out.println(Language.get(Language.MENU_ADD_DEPT));
        System.out.println(Language.get(Language.MENU_TEST));
        System.out.println(Language.get(Language.MENU_LANGUAGE));
        System.out.println(Language.get(Language.MENU_EXIT));
    }

    // Thêm khoa mới: mã không trùng, tự gán ngày tạo
    private static void addDepartment() {
        System.out.println(Language.get(Language.ADD_DEPT_TITLE));

        // 1. Nhập mã khoa; trùng ID (không phân biệt hoa thường) thì bắt nhập lại
        String id;
        while (true) {
            id = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_DEPT_ID),
                    Language.EMPTY_DEPT_ID);
            if (deptManager.isDuplicateID(id)) {
                utils.Notice(Language.get(Language.DUPLICATE_DEPT_ID));
            } else {
                break;
            }
        }

        // 2. Nhập tên khoa (không được rỗng)
        String name = Validation.readNonEmptyString(
                Language.get(Language.PROMPT_DEPT_NAME),
                Language.EMPTY_DEPT_NAME);

        // 3. Gán createDate = thời điểm hiện tại; lastUpdateDate để null vì chưa sửa
        Date createDate = new Date();
        Department dept = new Department(id, name, createDate, null);

        // 4. add() còn kiểm tra trùng ID một lần nữa; thành công thì in bảng danh sách
        if (deptManager.add(dept)) {
            utils.Notice(Language.get(Language.ADD_DEPT_SUCCESS) + Validation.formatDate(createDate));
            deptManager.showAll();
        } else {
            utils.Notice(Language.get(Language.ADD_DEPT_FAIL));
        }
    }

    // Menu test các hàm Validation: chuỗi không rỗng, giới tính, Y/N, format ngày
    private static void testValidation() {
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

        System.out.println(Language.get(Language.LABEL_DATE_FORMAT) + Validation.formatDate(new Date()));
    }
}
