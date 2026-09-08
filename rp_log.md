### rp_log.md
# 30/8: bắt đầu viết code.
- Viết xong Department.java

# 31/8:
- tạo repo - push git lần 1
- tạo khung của DepartmentManagement.java (tên hàm - chưa có code)
- Sửa Department thành class con của BaseEntity, thêm BaseEntity
- sửa Departmnet: chuyển name qua Base, sửa lại bên trong Dept theo UML, đổi date dùng Date thay vì String, thêm định dạng format cho date khi in.

# 1/9:
- Tạo class Doctor kế thừa BaseEntity bổ sung sex address departmentID và override showInfo
- Sửa Department: Bổ sung constructor rỗng, sửa getName thành getDepartmentName để rõ nghĩa hơn khi lấy (name có tên khoa và tên bác sĩ khác nhau)
- Viết xong class IManager<T>

# 3/9:
- Xong DepartmentManager, chưa viết has_doctor, chờ viết DoctorManager trước.

# 6/9:
- Xong các utils Validator (bao gồm: đọc chuỗi, nhận cả rỗng cho Add/Update; xử lý rỗng; điều kiện trống để gọi lại dùng trong DeptMan/DocMan; xử lý confirm; gender; date)
- Sửa trong Dept và Doc, toString() dùng trực tiếp hàm formatDate của utils

# 7/9:
### P1:
- Thêm BaseManager<T> để làm lớp cha cho DepartmentManager và DoctorManager -> tránh dup code.
- Sửa DeptMan kế thừa BaseMan, chỉ còn hàm update và showInfo cần viết riêng.
- Thêm DocMan
- DeptMan thêm hasDoctor -> deleteDepartment -> không xóa dept còn doc.

### P2:
- Bổ sung DocMan:
    + thêm outputDoc để duyệt nếu ds trống và tạo nhanh bảng để chuẩn bị in docInfo
    + thêm searchByName dùng contains để so sánh tên trong list và nameKey.
- Bổ sung DeptMan:
    + thêm outputDept để duyệt nếu ds trống và tạo nhanh bảng để chuẩn bị in docInfo
    + thêm displayDeptByID dùng lại findByID để tìm dept có chứa ID cần tìm và in ra.
- Tách hàm in -> tạo isEmptyList trong BaseMan để kiểm tra list trống
- Đặt lại tên cho output -> printHeader() để in tiêu đề dạng bảng
- Sửa lại logic in của DeptMan và DocMan để tránh lặp code.
- Sửa DeptMan: đưa phần xóa Dept vào handle, chỉ giữ lại has_doctor để kiểm tra doctor tồn tại => sửa hàm deleteDepartment thành hasDoctor()

### P3:
- Viết lớp HosHandle:
    + Xong HandleAddNew: dept, doc
    + Xong HandleDelete: dept, doc
    + Xong handleDisplay: In toàn bộ Dept, Doc
    + Xong handleSearch: Tim DeptByID, DocByName.
    + Xong HandleUpdate: handleUpdateDept và handleUpdateDoc
    + Xong handleSaveToFile -> tính năng khi user chọn
    + Auto Save mỗi khi có thay đổi: thêm/xóa/sửa thành công.
- Viết lớp Main gọi lại các handle.

# 8/9:
- Bỏ confirm khi back main menu ở submenu