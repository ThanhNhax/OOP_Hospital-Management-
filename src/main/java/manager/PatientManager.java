package manager;
import model.*;
import view.OutputViewer;

// PatientManager kế thừa từ BaseManager, truyền kiểu <Patient> vì BaseManager đã dùng Predicate
public class PatientManager extends BaseManager<Patient>{
    // Ở BaseMan đã tạo List<T> nên khi gọi BaseMan<Pat> sẽ tự hiểu list ở đây là patList.

    // Update: Không được sửa field patID (thêm điều kiện không sửa patID trong controller)
    @Override public boolean update(Patient newPat){
        // Kiểm tra patID cần sửa có tồn tại
        Patient updPatID = findByID(newPat.getPatientID()); // Nếu không tìm thấy -> null -> không có để update
        if (updPatID != null){
            // Dùng cách tìm ra updPatID ở index số mấy trong patList, sau đó dùng phương thức set để upd nhanh trên list, thay vì phải upd từng dòng như Doc (đều được, nhưng dùng cách 2 nhanh gọn hơn)
            int patInd = list.indexOf(updPatID); // Dùng indexOf để lấy index
            list.set(patInd, newPat); // tại vị trí patInd thay bằng newPat
            return true; // Trả về thành công
        }
        // Nếu không thì false
        return false;
    }

    // === Hàm in
    // 1. header cho Patient
    public void printHeader(){
        System.out.printf("| %-15s | %-30s | %-7s | %-50s | %-12s | %-10s | %-20s | %-15s | %-15s | %-12s | %-12s |\n", 
                "Patient ID", "Full Name", "Sex", "Address", "DOB", "Phone", "Diagnosis", "Doctor ID", "Status", "Create Date", "Update Date");
    }

    // 2. In ra toàn bộ list
    public void showAll(){
        // Kiểm tra list không có thì dừng luôn
        if (isEmptyList("Patient")) return;
        // Không thì in tiêu đề
        printHeader();
        // In từng dòng infor
        for (Patient pat : list) pat.showInfo();
    }

    // 3. Tìm 1 pat theo patID
    public void searchPatientByID(String patID){
        // list trống -> chắc chắn không có để search
        if (isEmptyList("Patient")) return;
        // Không thì tìm patID -> null là không có, ngược lại thì in ra
        Patient p = findByID(patID);
        if (p == null) OutputViewer.unFind("Patient", patID);
        else {
            // In tiêu đề
            printHeader();
            // In dòng thông tin
            p.showInfo();
        }
    }

    // 4. Tìm kiếm nâng cao bằng keyword theo patID, name, diagnosis, docID, AdmissionStatus: Dùng theo kiểu Ctrl + F của Google Sheet -> nhập keyword sẽ quét qua các trường trong tiêu chí và lấy ra các dòng gần giống thay vì chọn case.
    public void searchAdvance(String keyword){
        // List trống -> bỏ qua
        if (isEmptyList("Patient")) return;
        // Không thì search keyword gần giống bằng contains
        // Đặt một boolean found để dánh dấu lần tìm ra đầu tiên -> chỉ in printHeader một lần
        boolean found = false;
        // Chuyển keyword thành lower và bỏ space dư luôn để dễ gọi lại ở mỗi tiêu chí
        String keyLower = keyword.toLowerCase().trim();
        for (Patient p : list){
            // Nếu keyLower có trong bất kỳ tiêu chí nào thì xử lý
            // Đặt các tiêu chí vào biến boolean isMatch để dễ dùng lại nếu cần
            boolean isMatch = p.getPatientID().toLowerCase().contains(keyLower) || p.getPatientName().toLowerCase().contains(keyLower) || p.getPatDiagnosis().toLowerCase().contains(keyLower) || p.getDocID().toLowerCase().contains(keyLower) || p.getPatAdmissionStatus().toLowerCase().contains(keyLower);
            // Nếu isMatch true -> xử lý in tất cả gần giống
            if (isMatch){
                // Nếu chưa từng tìm thấy -> in tiêu đề 1 lần duy nhất -> found thành true
                if (!found){
                    printHeader();
                    found = true;
                }
                // In ra từng dòng infor
                p.showInfo();
            }
        }
        // Nếu chạy hết mà found vẫn là false -> không tìm ra kết quả nào
        if (!found) OutputViewer.unFind("Patient", keyword);
    }
}
