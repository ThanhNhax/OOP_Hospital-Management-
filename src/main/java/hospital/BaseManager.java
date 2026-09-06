package hospital;
import java.io.*; // Dùng nhiều thư viện trong io nên dùng * để lấy tất cả - không làm nặng hơn vì chỉ lấy đúng những class được sử dụng
import java.util.ArrayList; // Để dùng mảng list. Trong pj lớn có thể import thêm List sẵn để mở rộng khi cần.
import java.util.List;
import util.Validator;

// BaseManager tạo T kế thừa từ BaseEntity và thực thi IManager<T>
public abstract class BaseManager<T extends BaseEntity> implements IManager<T>{
    // khởi tạo List với T để có thể truyền dept hoặc doc
    protected  List<T> list = new ArrayList<>();

    // Getter cho danh sách nếu cần lấy dùng ở ngoài
    public List<T> getlist(){return list;}

    // Kiểm tra trùng ID: nếu tìm thấy ID -> true (dup), else false
    // 1. Tìm ID: vì hàm findByID gọi từ IManager -> cần Override
    @Override public T findByID(String id){
        // Nếu trong vòng lặp từ đầu đến cưới List tìm thấy thì trả về item (Khi gọi sẽ truyền Dept hoặc Doc vào T)
        for (T item : list) if (item.getID().equalsIgnoreCase(id)) return item; // Trong Java dùng equals để so sánh chuỗi và IgnoreCase để so ánh hoa-thường. VD DEPT01 hay dept01 là == nhau.

        // Nếu hết vòng vẫn không tìm thấy -> trả về null
        return null;
    }

    // 2. boolean nếu tìm thấy kết quả dept -> true, null -> false. Vì hàm isDuplicateID() không ở IManager nên không cần Override. isDup cũng chỉ dùng để kiểm tra cho chính class này trong trường hợp thêm và update, không đa năng, nên không cần đưa vào IManager để đa hình.
    public boolean isDuplicateID(String id){
        return findByID(id) != null; // Nếu khác null + tìm thấy ID -> true.
    }

    // Các hàm cơ bản: kiểm tra thêm, xóa thành công hay không:
    // 1. Thêm: Kiểm tra nếu không trùng id và item != null -> thêm vào -> true; else false
    @Override public boolean add(T item){
        return (item != null && !isDuplicateID(item.getID())) ? list.add(item) : false; // Dùng hàm add để thêm vào list
    }

    // 2. Xóa theo ID: kiểm tra ID có tồn tại -> xóa thành công, không thì false.
    @Override public boolean delete(String id){
        // Đặt biến đối tượng dept là biến có id cần xóa
        T item = findByID(id);
        // Nếu tìm thấy thì dept != null, không tìm thấy id -> dept == null
        return (item != null) ? list.remove(item) : false; // Dùng hàm remove để xóa dept trong list
    }

    // Đọc/Ghi vào file
    // 1. Ghi vào file
    @Override public void saveToFile(String path){
        // Dùng try-catch để bắt trường hợp lỗi an toàn
        // Dùng ObjectOutputStream để tạo biến ghi ra file
        // Tạo ô nhớ lưu dữ liệu trong file được tạo nằm ở path với FileOutputStream(path)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))){
            // gọi hàm writeObject(departmentList) để ghi danh sách departmentList ra file
            oos.writeObject(list); // Lưu dữ liệu vào file làm ẩn -> không cần thông báo đã lưu thành công
        }catch (IOException e) {Validator.Notice("Loi: " + e); // nếu thất bại thì thông báo để biết đã lỗi, có thể dùng hàm message() để đọc được lỗi đang bị. Nhưng có thể bị null, nên dùng e đọc lỗi trực tiếp.
        }
    }

    // 2. Đọc từ file vào RAM
    /*Khi dùng readObject() ép kiểu với list = (List<T>) ois.readObject(); Java sẽ cảnh báo đang ép kiểu từ Object sang List mà không thể kiểm tra nội dung trong file có đúng là list hay không.
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
            list = (List<T>) ois.readObject(); // ép kiểu Object thành List<Dept>
        } catch (Exception e) {
            Validator.Notice("Loi: " + e.getMessage());
        }
    }
}
