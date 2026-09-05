package hospital;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class utils {
    // Java 17 trên Windows mặc định in Windows-1252 -> chữ Việt bị thành ?
    // Ép System.out/err sang UTF-8; terminal Cursor nên dùng code page 65001
    public static void setupUtf8Console() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err), true, StandardCharsets.UTF_8));
    }

    // Dùng static để có thể gọi nhanh ở mọi nơi
    public static void Notice(String text) {
        System.out.println(text);
    }
}
