package hospital;

import util.Language;
import util.ConsoleHelper;

public class Main {
    // Điểm vào chương trình: UTF-8 console -> chọn ngôn ngữ -> giao cho Menu
    public static void main(String[] args) {
        ConsoleHelper.enableUtf8ConsoleOutput();
        Language.set(Language.EN); // Mặc định tiếng Anh
        new Menu().run();
    }
}
