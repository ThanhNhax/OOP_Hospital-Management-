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