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

    // Hàm in showAll()
    @Override public void showAll(){
        // Nếu trong list rỗng -> chỉ in thông báo ds trống
        if (list.isEmpty()) { // list nằm ở BaseMan
            Validator.Notice("Danh sach phong ban trong!!");
            return; // dừng luôn.
        }

        // Nếu không thì in tiêu đề dạng bảng
        // Dùng printf để đặt format tương tự String.format
        System.out.printf("| %-15s | %-30s | %-12s | %-12s |\n", "DEPARTMENT ID", "DEPARTMENT NAME", "CREATE DATE", "UPDATE DATE");
        // Gọi showInfo trong Department để in đúng theo bảng các thông tin trong list
        for (Department dept : list) dept.showInfo();
    }

    // Thêm has_doctor kiểm tra nếu có doctor thì không được xóa.
    public boolean deleteDepartment(String deptID, DoctorManager docMan){
        // Duyệt trong docList, nếu vẫn có deptID trùng với deptID đang cần xóa -> có doc -> không xóa -> false
        for (Doctor doc : docMan.getlist()){
            if (doc.getDepartmentID().equalsIgnoreCase(deptID)){
                Validator.Notice("Loi: Van con bac si trong phong ban nay, khong the xoa!!");
                return false;
            }
        }
        // Nếu không có -> xóa
        return super.delete(deptID); // Dùng delete ở BaseMan để xóa dept khỏi list.
    }
}