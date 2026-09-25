# Hệ thống Quản lý Bệnh viện (Hospital Management System)

## 1. Tổng quan hệ thống (System Overview)
Hệ thống Quản lý Bệnh viện là một ứng dụng console viết bằng Java, được thiết kế để quản lý hồ sơ bệnh nhân. Ứng dụng cho phép người dùng thực hiện các thao tác CRUD (Thêm, Đọc, Sửa, Xóa) trên dữ liệu bệnh nhân, tìm kiếm bệnh nhân cụ thể, và lưu trữ dữ liệu vĩnh viễn (persist data) xuống file.

## 2. Các quyết định thiết kế chính (Major Design Decisions)
- **Áp dụng các nguyên lý OOP (Object-Oriented Programming):**
  - **Tính đóng gói (Encapsulation):** Tất cả các thuộc tính của `Patient` và `BaseEntity` đều được để ở dạng `private` và chỉ truy cập qua các hàm `getter/setter`. Dữ liệu nhập vào đều được validate chặt chẽ ở `InputValidator` trước khi gán.
  - **Tính kế thừa (Inheritance):** Lớp `Patient` kế thừa từ lớp trừu tượng `BaseEntity` để tái sử dụng các thuộc tính chung (ID, Name, Create Date, Update Date).
  - **Tính trừu tượng (Abstraction) & Đa hình (Polymorphism):** Sử dụng Interface `IManager<T>` và abstract class `BaseManager<T>` làm bộ khung chuẩn, giúp dễ dàng mở rộng nếu sau này muốn quản lý thêm Bác sĩ (Doctor) hay Khoa (Department).
- **Phân tách trách nhiệm (Separation of Concerns):** Tuân thủ mô hình tương tự MVC:
  - **Model:** Chứa cấu trúc dữ liệu (`BaseEntity`, `Patient`).
  - **Manager:** Xử lý logic thao tác dữ liệu nội bộ (list, search, validation).
  - **Controller/Handle:** Điều hướng luồng chạy và tương tác với người dùng.
  - **View:** `InputValidator` và `OutputViewer` chuyên lo việc hiển thị và nhận/kiểm tra dữ liệu chuẩn.

## 3. Trách nhiệm của các Class (Class Responsibilities)
- `model.BaseEntity`: Class trừu tượng cung cấp các thuộc tính dùng chung như ID, Tên, Ngày tạo, Ngày cập nhật.
- `model.Patient`: Đại diện cho một bệnh nhân, kế thừa `BaseEntity` và có thêm các thuộc tính: giới tính, địa chỉ, sđt, ngày sinh, chẩn đoán, bác sĩ phụ trách, và trạng thái nhập viện.
- `manager.IManager`: Interface định nghĩa các thao tác CRUD và Đọc/Ghi file cơ bản.
- `manager.BaseManager`: Class trừu tượng triển khai (implement) `IManager` bằng Generics, xử lý các logic chung như thêm, xóa, và I/O file.
- `manager.PatientManager`: Kế thừa `BaseManager` để dùng riêng cho `Patient`, triển khai logic cập nhật và tìm kiếm chuyên sâu.
- `controller.PatientHandle`: Điều khiển luồng công việc quản lý bệnh nhân, kết nối giữa người dùng và `PatientManager`.
- `controller.HospitalHandler`: Controller tổng (Facade), nhận lệnh từ Menu và phân phối cho các Handle tương ứng.
- `view.InputValidator`: Xử lý cực kỳ chặt chẽ dữ liệu đầu vào (dùng Regex, Loop) để đảm bảo tính toàn vẹn của dữ liệu (chống rỗng, chống format sai).
- `view.OutputViewer`: Nơi tập trung chứa các thông báo, menu, và câu báo lỗi.
- `main.Main`: Điểm vào (Entry point) của chương trình, hiển thị Menu và chạy vòng lặp hệ thống.

## 4. Các mối quan hệ (Relationships)
- `Patient` **kế thừa (extends)** `BaseEntity`.
- `BaseManager` **triển khai (implements)** `IManager` và quản lý một danh sách (List) các `BaseEntity`.
- `PatientManager` **kế thừa (extends)** `BaseManager<Patient>`.
- `PatientHandle` **sử dụng (uses)** `PatientManager`, `InputValidator` và `OutputViewer`.
- `HospitalHandler` **tập hợp (aggregates)** `PatientHandle`.

## 5. Định dạng File lưu trữ (File Format)
Hệ thống sử dụng **Java Object Serialization** để lưu trữ dữ liệu. Các hồ sơ bệnh nhân (Patient records) được lưu vào file `patient.dat` dưới định dạng nhị phân (binary). 
- Khi lưu, toàn bộ ArrayList chứa các đối tượng sẽ được ghi thẳng xuống file.
- Khi đọc (load) từ file, hệ thống sẽ giải mã (deserialize) danh sách này và chỉ nạp những bản ghi hợp lệ, không bị trùng ID vào danh sách hiện tại để tránh lỗi mất dữ liệu.
