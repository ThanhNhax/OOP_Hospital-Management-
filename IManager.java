package oop.do_an_hospital.hospital;

public interface IManager<T> {
    // Không cần dùng public trước các hàm vì trong interface đã mặc định mọi phương thức là public adstract
    // Thêm khoa mới (check departmentID không được trùng).
    boolean add(T item);
    // Sửa thông tin khoa theo departmentID (khi sửa xong gán lastUpdateDate là ngày hiện tại; nếu nhập khoảng trắng thì giữ nguyên thông tin cũ).
    boolean update(T item);
    // Xóa khoa (không được xóa nếu đang có Bác sĩ thuộc khoa đó)
    boolean delete(String id);
    // Tìm khoa theo departmentID
    T findByID(String id);
    // In toàn bộ danh sách khoa.
    void showAll();
    // Ghi/Đọc file department.dat
    void loadFromFile(String path);
    void saveToFile(String path);
}
