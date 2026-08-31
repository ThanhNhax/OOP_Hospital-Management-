package hospital;

import java.util.Date;
import java.text.SimpleDateFormat; // Ép kiểu khi in ngày về dd/mm/yyyy cho đẹp khi đưa vào bảng.

// Kế thừa (extends) từ hàm cha BaseEntity
public class Department extends BaseEntity {
    // Không có thuộc tính riêng, tất cả đều kế thừa từ BaseEntity
    // Đảm báo đơn trách nhiệm -> ở class này chỉ quản lý các thuộc tính -> get/set
    // thuộc tính nào.

    // ==== Constructor ====
    public Department(){} // Khi viết constructor có tham số luôn cần có constructor rỗng để nhập hoặc thêm .dat, vì Java đã xóa constructor mặc định sau khi tạo constructor có tham số.
    // gọi constructor của lớp cha bằng super(tham_số)
    public Department(String dID, String ten, Date ngay_tao, Date ngayUpdate) {
        super(dID, ten, ngay_tao, ngayUpdate);
    } // Dùng departmentID (dID) vào id ở baseentity đối với department

    // ==== Getter/Setter ====
    // 1. Get: lấy thông tin trong private ra dùng
    public String getDepartmentID(){return getID();} // Thêm hàm lấy departmentID -> giúp lấy ID đúng nhanh
    public String getDepartmentName(){return getName();} // Lấy đúng tên khoa

    // 2. Set: Không có set ở class này, vì tất cả được set ở Base.

    // ==== Hàm in một obj ====
    // Dùng toString() có sẵn của Java, nếu không @Override sẽ ra chuỗi ký tự như
    // Person... và ô địa chỉ -> Override để viết lại hàm toString, khi gọi
    // Department d = new Department() và println(d) -> in ra đúng form đã định
    // dạng.
    // Định nghĩa cách gán chuỗi ngay ngắn bằng toString()
    @Override
    public String toString() {
        // Đặt biến ép kiểu cách in ngày trước
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // đặt biến String để nhận kết quả ngày in trả về. "" nếu NULL, else thì trả
        // đúng ngày get được đã sdf.format
        String strCreateDate = (getCreateDate() != null) ? sdf.format(getCreateDate()) : "";
        String strUpdateDate = (getLastUpdateDate() != null) ? sdf.format(getLastUpdateDate()) : "";
        // Dùng formart để vẽ khung tương tự printf("%-15s | %-20s", deparmentID, name) của C++
        return String.format("| %-15s | %-30s | %-12s | %-12s |", getDepartmentID(), getDepartmentName(), strCreateDate, strUpdateDate);
    }

    // Thực hiện việc phải định nghĩa hàm showInfo() riêng của class được quy định ở
    // hàm cha
    @Override
    public void showInfo() {
        System.out.println(this.toString()); // In ra theo kiểu toString() được định nghĩa trong class này
    }
    // Như vậy, giả sử đối tượng Department de = new Department() -> de.showInfo()
    // sẽ in ra theo kiểu của class này
    // Và Doctor do = new Doctor() -> do.showInfo() sẽ in ra theo kiểu bảng của
    // class Doctor.
}