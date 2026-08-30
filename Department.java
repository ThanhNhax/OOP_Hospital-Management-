package oop.do_an_hospital.hospital;

// Kế thừa (extends) từ hàm cha BaseEntity
public class Department extends BaseEntity{
    // Thuộc tính của Department
    private String name;
    
    // Đảm báo đơn trách nhiệm -> ở class này chỉ quản lý các thuộc tính -> get/set thuộc tính nào.
    
    // ==== Constructor ====
    // gọi constructor của lớp cha bằng super(tham_số)
    public Department(String dID, String ten, String ngay_tao, String ngayUpdate){
        super(dID, ngay_tao, ngayUpdate); // Dùng departmentID (dID) vào id ở baseentity đối với department
        this.name = ten;
    }

    // ==== Getter/Setter ====
    // 1. Get: lấy thông tin trong private ra dùng
    public String getName(){
        return name;
    }
    // Thêm hàm lấy departmentID -> giúp lấy ID đúng nhanh
    public String getDepartmentID(){return getID();}

    // 2. Set: Đưa thông tin mới vào private (sửa thông tin)
    public void setName(String ten){
        this.name = ten;
    }


    // ==== Hàm in một obj ====
    // Dùng toString() có sẵn của Java, nếu không @Override sẽ ra chuỗi ký tự như Person... và ô địa chỉ -> Override để viết lại hàm toString, khi gọi Department d = new Department() và println(d) -> in ra đúng form đã định dạng.
    // Định nghĩa cách gán chuỗi ngay ngắn bằng toString()
    @Override public String toString(){
        // Dùng formart để vẽ khung tương tự printf("%-15s | %-20s", deparmentID, name) của C++
        return String.format("| %-15s | %-30s | %-12s | %-12s |", getDepartmentID(), name, getCreateDate(), (getLastUpdateDate() == null ? "" : getLastUpdateDate())); // Ngày update mới nếu không có thì bỏ trống.
    }
    // Thực thi viện phải định nghĩa in ra quy định ở hàm cha
    @Override public void showInfo(){
        System.out.println(this.toString()); // In ra theo kiểu toString() được định nghĩa trong class này
    }
    // Như vậy, giả sử đối tượng Department de = new Department() -> de.showInfo() sẽ in ra theo kiểu của class này
    // Và Doctor do = new Doctor() -> do.showInfo() sẽ in ra theo kiểu bảng của class Doctor.
}