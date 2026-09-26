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

# 9/9:
- Sửa Validator: readString(), đổi về dùng Notice thay cho System.out

# 22/9:
- Chia lại folder theo MVC:
    + baseEntity + department + doctor = model. Viết thêm Patient
    + các Manager = manager + thêm PatientManager
    + Validator = inputViewer -> tách formatDate ra utils + outputViewer chứa đoạn in ra MainMenu và SubMenu
    + Handle = controller
- Fix: outputViewer: thêm các câu báo lỗi dùng chung (dept list empty, doc list empty) và sửa cách gọi trong deptman, docman.

# 23/9:
- Xong Patient.java
- Xong PatientManager.java
- Thêm regex phone và đọc dateString để nhập dob trong Input: thêm parseDate để xử lý chuyển String -> Date để kiểm tra đúng, dùng formatDate đã có chuyển Date -> String để lấy được đúng ngày cần in ra.
- thêm readUpdPhone và readUpdDate để kiểm tra đúng định dạng khi upd
- bỏ readUpdPhone và readUpdDate -> thay bằng hàm tổng quát dùng chung radValidString để xử lý mode upd/!upd và điều kiện từng thuộc tính (nếu có)
- PatientHandle.java: Xong thêm, xóa, update, in, search, save, load

# 24/9:
### P1:
- Chỉnh Menu theo đề
- Sửa search và display theo đề
- Done MainMenu

### P2:
- Sửa câu 'enter your choice' qua choice trong main
- Thêm obj 'the system' cho câu succeful khi exit
- Sửa lỗi infinite loop khi deptid/docid chưa tồn tại -> nhập doc/pat bị lỗi. Hoặc khi xóa nếu chưa tồn tại dept/doc/patid -> lặp bắt nhập lại => thêm phím 0 cho phép thoát ngang.

# 26/9:
### P1:
- Sửa lỗi truyền ngược tham số DOB và Phone của Patient (sửa trong tham số của Constructor của Patient)
- Sửa lỗi in ngày DOB, bắt buộc 2-2-4 (sửa trong utils.parseDate())
- Sửa lỗi chính tả Maie -> Male trong readGender() - lỗi không nhận 'Male'

### P2:
- Đồng bộ: câu tiếng Anh + sửa DOB trong readDate (InputValidator) thay vì utils.
- Sửa: add, upd của patient không bị ràng buộc với doctor (vì đề mới chỉ có 1 class patient)
- Sửa gender thêm chọn phím số 1, 2
- Sửa phone cho phép nhập 0 bỏ qua
- Sửa status cho phép nhập 1, 2, 3 để chọn trạng thái thay vì gõ chữ.