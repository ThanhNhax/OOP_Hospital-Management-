package controller;
// Dùng list động thay cho mảng tĩnh
import java.util.ArrayList; 
import java.util.List;

// Gọi các lớp tầng Manager và utils
import model.Student;
import utils.Utils;

// import thư viện hỗ trợ sort
import java.util.Collections;
import java.util.Comparator;

public class StudentList {
    // Tạo mảng list động
    private List<Student> list;

    public StudentList() {
        this.list = new ArrayList<>();
    }

    // Getter lấy List
    public List<Student> getList() {
        return list;
    }
    // Setter đưa newList vào List
    public void setList(List<Student> list) {
        this.list = list;
    }

    // === Hàm phụ - Manager:
    // Kiểm tra dup
    // 1. kiểm tra trùng ID: nếu có trùng thì trả về item trùng, không thì trả về null
    public Student findByID(String id){
        for (Student item : list) if (item.getId().equalsIgnoreCase(id)) return item;
        return null;
    }
    // 2. boolean nếu tìm thấy kết quả std
    public boolean isDuplicate(String ID){
        return findByID(ID) != null;
    }

    // Sắp xếp
    // 1. SortByName()
    public void sortByName(){
        Collections.sort(list, new Comparator<Student>(){
            @Override public int compare(Student s1, Student s2){ // khi dùng hàm sort này bắt buộc dùng kiểu int, compare(obj1, obj2)
                // Tên sắp xếp tăng dần
                return s1.getName().compareToIgnoreCase(s2.getName()); // dùng compareToIgnoreCase để so sánh s1 và s2 không phân biệt hoa thường
            }
        });
    }
    // 2. SortByName()
    public void sortByScore(){
        Collections.sort(list, new Comparator<Student>(){
            @Override public int compare(Student s1, Student s2){
                // Điểm sắp xếp giảm dần + ép kiểu Float khi so sánh
                return Float.compare(s2.getScore(), s1.getScore()); // điểm không cần so sánh hoa-thường -> không cần dùng compareToIgnoreCase
            }
        });
    }

    // === Các hàm chính: thêm/xóa/sửa/tìm kiếm.
    // 1. Thêm SV vào list
    public boolean add(Student student) {
        // thêm sinh viên vào mảng cuối, chú ý kiểm tra ko dc trùng id
        return (student != null && !isDuplicate(student.getId())) ? list.add(student) : false;
    }
    // 2. Xoá 1 sinh viên theo ID
    public boolean delete(String id) {
        // Tìm sv theo ID
        Student std = findByID(id);
        return (std != null) ? list.remove(std) : false; // Nếu std == null -> không tìm ra id.
    }
    // 3. cập nhật thông tin của 1 sinh viên theo ID
    public boolean update(Student newStudent){
        // tìm id của newStudent trong std
        Student std = findByID(newStudent.getId());
        // Nếu std != null -> tìm ra -> update newStd vào std; else -> false
        return (std != null) ? std.updateSV(newStudent) : false;
    }

    // 4. Hàm in
    // 4.1. Kiểm tra list rỗng
    public boolean isEmptyList(String errorMsg){
        if (list.isEmpty()) {
            Utils.Notice(errorMsg);
            return true;
        }
        return false;
    }
    // 4.2. Tạo tiêu đề bảng
    public void printHeader(){
        System.out.printf("| %-15s | %-30s | %-5s |\n", "ID", "Ten Sinh Vien", "Diem");
    }
    // 4.3 In ra list SV
    public void displayList(){
        // Kiểm tra danh sách trống thì báo trống và ket thuc
        if (isEmptyList("Danh sach trong!!")) return;
        // Nếu không thì in tiêu đề
        printHeader();
        // Lặp vòng lặp in ra từng dòng info
        for (Student std : list) std.showInfo();
    }
    // 4.4 tìm danh sách sinh viên có tên chứa key name nhập vào -> in ra các dòng
    public List<Student> search(String name) {
        List<Student> result = new ArrayList<>(); // tạo list chứa list kết quả
        // Duyệt tìm trong list
        for (Student std : list){
            // dùng toLowerCase để không phân biệt hoa-thường và contains để tìm chuỗi con trong chuỗi name
            if (std.getName().toLowerCase().contains(name.toLowerCase())) result.add(std); // Mỗi lần tìm ra name gần giống thì add std vào list result
        }
        // Kết thúc vòng lặp, trả về list kết quả
        return result;
    }
}