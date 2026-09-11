package utils;
import java.util.Scanner;

public class Utils {
    // Khai báo scn chuẩn bị cho read
    private static final Scanner scn = new Scanner(System.in);

    // Các hàm dùng chung
    // 0. In thông báo
    public static void Notice(String text){
        System.out.println(text);
    }
    // 1. Đọc chuỗi - chấp nhận cả rỗng
    public static String readString(String msg){
        Notice(msg);
        return scn.nextLine().trim(); // dùng netLine() để dọn ký tự rác tương đương cin.ignore của C++, trim() để cắt bỏ space dư ở đầu vào cuối.
    }

    // 2. Xử lý nonEmpty
    public static String readNonEmptyString(String msg){
        while(true){
            String input = readString(msg); // Nhập vào
            // Nếu ! rỗng -> nhận input
            if (!input.isEmpty()) return input;
            // Không thì báo lỗi -> lặp
            Notice("Loi: Khong the bo trong!!!");
        }
    }

    // 3. Nhập số nguyên (dùng trong menu)
    public static int getInt(String msg, int min, int max){
        // Nếu nhập sai bắt nhập lại đến khi đúng
        while(true){
            Notice(msg);
            // dùng hasNextInt() của Java để kiểm tra dữ liệu trong buffer có phải int hay không
            if (scn.hasNextInt()){
                int val = scn.nextInt();
                scn.nextLine(); // dọn rác enter tương tự cin.ignore()
                if (val >= min && val <= max) return val; // Nhập đúng trong khoảng thì nhận về value
                // Nếu không thì thông báo lỗi
                Notice("Loi: Gia tri phai tu " + min + " den " + max + "!");
            }
            // Nếu nhập ký tự không phải int
            else{
                Notice("Loi: Phai nhap so nguyen!!");
                scn.nextLine(); // Tương đương cin.clear() + cin.ignore() trong C++
            }
        }
    }

    // 4. Nhập điểm trung bình cho SV từ 0-10
    public static float getFloat(String msg, float min, float max){
        while (true){
            Notice(msg);
            if (scn.hasNextFloat()){
                float val = scn.nextFloat();
                scn.nextLine();
                if (val >= min && val <= max) return val;
                Notice("Loi: Gia tri phai tu " + min + " and " + max + "!");
            } else {
                Notice("Loi: Phai nhap so thuc!!");
                scn.nextLine();
            }
        }
    }


}
