package hospital;

import util.Validator;

// IManager tương tự prototype, chứa các tên hàm cần thực thi. DeptMan cần viết định nghĩa để thực thi (implements) các hàm đó. VD khi gọi:
/*IManager<Department> deptManager = new DepartmentManager();
IManager<Doctor> docManager = new DoctorManager();*/

// Truyền Department vào BaseManager -> list ở lóp Base sẽ thành deptList, trong findByID(String id) sẽ tự tìm trên deptList.
public class DepartmentManager extends BaseManager<Department>{ // Nghĩa là DeptMan thực thi IMan.
    // Dùng list khởi tạo ở BaseManager.
    // Các hàm thêm, xóa, saveToFile(), loadFromFile() được viết ở BaseMan để dùng chung -> tránh lặp code.

    // Cập nhật thông tin phòng ban: tìm theo ID, chỉ được sửa tên và ngày update cuối.
    // Vì số lượng thông tin có thể update của Dept và Doc khác nhau -> cần viết riêng, không thể viết hàm dùng chung
    @Override public boolean update(Department dept){
        // Đặt biến đối tượng dept là biến có id cần sửa
        Department updateDept = findByID(dept.getDepartmentID()); // Nếu null -> không tồn tại dept cần sửa.
        if (updateDept != null){
            updateDept.setName(dept.getName()); // set tên mới thành tên của dept vừa nhập
            updateDept.setLastUpdateDate(dept.getLastUpdateDate()); // set ngày update cuối thành ngày trong dept vừa nhập
            return true; // trả về update thành công
        }
        return false; // Nếu không thì return false.
    }

    // === Hàm in
    // 1. printHeader -> in ra mẫu tiêu đề dạng bảng, không cần kiểm tra trống -> sẽ làm ở bước thực thi
    public void printHeader(){
        // Dùng printf để đặt format tương tự String.format
        System.out.printf("| %-15s | %-30s | %-12s | %-12s |\n", "DEPARTMENT ID", "DEPARTMENT NAME", "CREATE DATE", "UPDATE DATE");
    }
    // 2. showAll()
    @Override public void showAll(){
        // 1. Kiểm tra trống
        if (isEmptyList("Danh sach phong ban trong!!")) return; // Dừng luôn
        // Nếu không trống
        // 2. In tiêu đề
        printHeader();
        // 3. In list info.
        for (Department dept : list) dept.showInfo();
    }
    // 3. Tìm dept theo ID và in ra chi tiết
    public void displayDeparmtentByID(String id){
        // Tạo dept kiểu dept để tìm dept có ID 
        Department dept = findByID(id);
        // Nếu dept != null -> tìm thấy -> in ra => có dept <=> list không trống, không cần kiểm tra.
        if (dept != null){
            printHeader();
            dept.showInfo(); // in ra infor của dòng dept đó
        }
        // Else -> không có deptID đó -> báo lỗi
        else Validator.Notice("Khong tim thay phong ban co ID: " + id);
    }

    // =====
    // Thêm has_doctor để kiểm tra nếu có doctor thì không được xóa.
    public boolean hasDoctor(String deptID, DoctorManager docMan){
        // Duyệt trong docList, nếu vẫn có deptID trùng với deptID đang cần xóa -> có doc -> true
        for (Doctor doc : docMan.getlist()) if (doc.getDepartmentID().equalsIgnoreCase(deptID)) return true;
        // Nếu không có -> false.
        return false;
    }
}