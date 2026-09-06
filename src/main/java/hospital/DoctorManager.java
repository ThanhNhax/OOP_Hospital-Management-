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

    @Override public void showAll(){
        // Nếu list rỗng in thông báo
        if (list.isEmpty()){
            Validator.Notice("Danh sach bac si trong!!");
            return;
        }
        // Nếu không thì in ra bảng của doc theo format
        System.out.printf("| %-15s | %-30s | %-7s | %-50s | %-15s | %-12s | %-12s |\n", "DOCTOR ID", "DOCTOR NAME", "SEX", "ADDRESS", "DEPT ID", "CREATE DATE", "UPDATE DATE");
        for (Doctor doc : list) doc.showInfo(); // In ra từng dòng info.
    }
}
