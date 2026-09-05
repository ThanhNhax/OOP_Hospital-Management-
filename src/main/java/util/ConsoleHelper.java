package util;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

// Hỗ trợ console: UTF-8 trên Windows và in thông báo cho người dùng
public class ConsoleHelper {
    // Java 17 trên Windows mặc định in Windows-1252 -> chữ Việt bị thành ?
    // Gắn lại System.out/err bằng UTF-8; terminal Cursor nên dùng code page 65001
    public static void enableUtf8ConsoleOutput() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err), true, StandardCharsets.UTF_8));
    }

    // In một dòng thông báo ra console (thành công, lỗi, danh sách trống...)
    public static void printNotice(String message) {
        System.out.println(message);
    }
}
