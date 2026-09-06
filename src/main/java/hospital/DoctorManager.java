package hospital;

import util.Validator;

// DocMan kế thừa từ BaseMan
public class DoctorManager extends BaseManager<Doctor>{
    // list ở đây tự hiểu là docList, hàm findByID tự hiểu duyệt trên docList
    @Override public boolean update(Doctor doc){
        // Kiểm tra docID cần update có tồn tại
        Doctor updateDoc = findByID(doc.getDoctorID()); // Nếu không tìm thấy -> không có doc cần update
        if (updateDoc != null){
            updateDoc.setName(doc.getName()); // Set tên doc mới
            updateDoc.setSex(doc.getSex()); // Set giới tính mới
            updateDoc.setAddress(doc.getAddress());
            updateDoc.setDepartmentID(doc.getDepartmentID()); // Lấy deptID mới của Doc
            updateDoc.setLastUpdateDate(doc.getLastUpdateDate());
            return true;
        }
        return false;
    }

    // === Hàm in
    // 1. Tạo tiêu đề bảng của Doctor để dùng nhanh
    public void printHeader(){
        System.out.printf("| %-15s | %-30s | %-7s | %-50s | %-15s | %-12s | %-12s |\n", "DOCTOR ID", "DOCTOR NAME", "SEX", "ADDRESS", "DEPT ID", "CREATE DATE", "UPDATE DATE");
    }

    @Override public void showAll(){
        // Kiểm tra list trống thì dừng luôn
        if (isEmptyList("Danh sach bac si trong!!")) return;
        // không thì in tiêu đề
        printHeader();
        for (Doctor doc : list) doc.showInfo(); // In ra từng dòng info.
    }

    // Thêm tìm Doc bằng tên
    public void searchByName(String namekey){
        // Đặt kết quả tìm ban đầu là false
        boolean found = false;
        // Duyệt doc cần tìm trong list
        for (Doctor doc: list){
            if (doc.getDoctorName().toLowerCase().contains(namekey.toLowerCase())){ // Dùng contains để kiểm tra xem chuỗi trong getDoctorName (tên doc đang có trong list) có chứa chuỗi con namekey (từ khóa tên cần tìm) hay không.
                // Dùng !found để chỉ in dòng tiêu đề 1 lần -> chuyển sang true
                if (!found){
                    printHeader();
                    found = true;
                }
                // sau đó chỉ in nội dung thông tin bác sĩ
                doc.showInfo();
            }
        }
        // Nếu hết vòng vẫn không tìm thấy bác sĩ nào -> vẫn false -> thông báo
        if (!found){
            Validator.Notice("Khong tim thay bac si nao chua tu khoa: " + namekey);
        }
    }
}
