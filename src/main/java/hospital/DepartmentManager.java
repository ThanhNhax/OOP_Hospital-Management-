package hospital;

import java.io.*; // Dùng nhiều thư viện trong io nên dùng * để lấy tất cả - không làm nặng hơn vì chỉ lấy đúng những class được sử dụng
import java.util.ArrayList; // Để dùng mảng list. Trong pj lớn có thể import thêm List sẵn để mở rộng khi cần.
import java.util.List;

// IManager tương tự prototype, chứa các tên hàm cần thực thi. DeptMan cần viết định nghĩa để thực thi (implements) các hàm đó. VD khi gọi:
/*IManager<Department> deptManager = new DepartmentManager();
IManager<Doctor> docManager = new DoctorManager();*/

public class DepartmentManager implements IManager<Department>{ // Nghĩa là DeptMan thực thi IMan.
    private List<Department> departmentList; // Tạo mảng chứa kiểu List tên departmentList
    // Constructor khởi tạo danh sách
    public DepartmentManager(){
        this.departmentList = new ArrayList<>(); // khởi tạo ô nhớ
    }
    // Getter cho danh sách nếu cần lấy dùng ở ngoài
    public List<Department> getDepartmentList(){return departmentList;}
    // Kiểm tra trùng ID: nếu tìm thấy ID -> true (dup), else false
    // 1. Tìm ID: vì hàm findByID gọi từ IManager -> cần Override
    @Override public Department findByID(String id){
        // Nếu trong vòng lặp từ đầu đến cưới List tìm thấy thì trả về dept
        for (Department dept : departmentList) if (dept.getDepartmentID().equalsIgnoreCase(id)) return dept; // Trong Java dùng equals để so sánh chuỗi và IgnoreCase để so ánh hoa-thường. VD DEPT01 hay dept01 là == nhau.

        // Nếu hết vòng vẫn không tìm thấy -> trả về null
        return null;
    }

    // 2. boolean nếu tìm thấy kết quả dept -> true, null -> false. Vì hàm isDuplicateID() không ở IManager nên không cần Override. isDup cũng chỉ dùng để kiểm tra cho chính class này trong trường hợp thêm và update, không đa năng, nên không cần đưa vào IManager để đa hình.
    public boolean isDuplicateID(String id){
        return (findByID(id) != null) ? true : false;
    }

    // Các hàm cơ bản: kiểm tra thêm, xóa, sửa thành công hay không:
    // 1. Thêm: Kiểm tra nếu không trùng id và dept != null -> thêm vào -> true; else false
    @Override public boolean add(Department dept){
        return (dept != null && !isDuplicateID(dept.getDepartmentID())) ? departmentList.add(dept) : false; // Dùng hàm add để thêm vào list
    }

    // 2. Xóa theo ID: kiểm tra ID có tồn tại -> xóa thành công, không thì false.
    @Override public boolean delete(String id){
        // Đặt biến đối tượng dept là biến có id cần xóa
        Department dept = findByID(id);
        // Nếu tìm thấy thì dept != null, không tìm thấy id -> dept == null
        return (dept != null) ? departmentList.remove(dept) : false; // Dùng hàm remove để xóa dept trong list
    }

    // 3. Cập nhật thông tin phòng ban: tìm theo ID, chỉ được sửa tên và ngày update cuối.
    @Override public boolean update(Department dept){
        // Đặt biến đối tượng dept là biến có id cần sửa
        Department update_dept = findByID(dept.getDepartmentID()); // Nếu null -> không tồn tại dept cần sửa.
        if (update_dept != null){
            update_dept.setName(dept.getName()); // set tên mới thành tên của dept vừa nhập
            update_dept.setLastUpdateDate(dept.getLastUpdateDate()); // set ngày update cuối thành ngày trong dept vừa nhập
            return true; // trả về update thành công
        }
        return false; // Nếu không thì return false.
    }

    // Hàm in showAll()
    @Override public void showAll(){
        // Nếu trong list rỗng -> chỉ in thông báo ds trống
        if (departmentList.isEmpty()) {
            utils.Notice("Danh sach phong ban hien tai dang trong!!");
            return; // dừng luôn.
        }

        // Nếu không thì in tiêu đề dạng bảng
        // Dùng printf để đặt format tương tự String.format
        System.out.printf("| %-15s | %-30s | %-12s | %-12s |\n", "DEPARTMENT ID", "DEPARTMENT NAME", "CREATE DATE", "UPDATE DATE");
        // Gọi showInfo trong Department để in đúng theo bảng các thông tin trong list
        for (Department dept : departmentList) dept.showInfo();
    }

    // Đọc/Ghi vào file
    // 1. Ghi vào file
    @Override public void saveToFile(String path){
        // Dùng try-catch để bắt trường hợp lỗi an toàn
        // Dùng ObjectOutputStream để tạo biến ghi ra file
        // Tạo ô nhớ lưu dữ liệu trong file được tạo nằm ở path với FileOutputStream(path)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))){
            // gọi hàm writeObject(departmentList) để ghi danh sách departmentList ra file
            oos.writeObject(departmentList); // Lưu dữ liệu vào file làm ẩn -> không cần thông báo đã lưu thành công
        }catch (IOException e) {System.out.println("Loi: " + e); // nếu thất bại thì thông báo để biết đã lỗi, có thể dùng hàm message() để đọc được lỗi đang bị. Nhưng có thể bị null, nên dùng e đọc lỗi trực tiếp.
        }
    }

    // 2. Đọc từ file vào RAM
    /*Khi dùng readObject() ép kiểu với departmentList = (List<Department>) ois.readObject(); Java sẽ cảnh báo đang ép kiểu từ Object sang List mà không thể kiểm tra nội dung trong file có đúng là list hay không.
    Việc dùng @SuppressWarnings("unchecked") sẽ giúp xác nhận đã biết nó đúng là list để ngăn Java cảnh báo*/
    @SuppressWarnings("unchecked")
    @Override public void loadFromFile(String path){
        // Dùng File với biến tên file và new File(path) để ánh xạ đến đường dẫn path xem có tồn tại file ở path chưa; nếu chưa có thì return, không load file
        File file = new File(path);
        if (!file.exists()) return; // Không cần báo lỗi, chỉ cần bỏ qua chuyện đọc file.
        // Nếu có file thì bắt đầu try-catch để load dữ liệu an toàn vào RAM
        // Dùng ObjectInputStream ánh xạ tới file ở path bằng FileInputStream để đọc data trong file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            // dùng readObject để đọc data trong file
            departmentList = (List<Department>) ois.readObject(); // ép kiểu Object thành List<Dept>
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}