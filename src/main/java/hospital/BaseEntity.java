package hospital;

import java.io.Serializable;
import java.util.Date;

// Cần implements Serializable để sau này ghi/đọc đối tượng ra file .dat
public abstract class BaseEntity implements Serializable {
    private String id; // ID chính của Department là departmentID, của doctor là doctorID -> ID ở Base
                       // khi được kế thừa ở class nào sẽ được định nghĩa đúng với bản chất của class
                       // đó.
    private String name;
    private Date createDate;
    private Date lastUpdateDate;

    // === Khai báo Constructor ===
    // 1. Tạo constructor không tham số để sau này có thể tạo vỏ rỗng như BaseEntity
    // d = new BaseEntity() và dùng obj d đó, VD: d.setName()
    public BaseEntity() {
    }

    // 2. Constructor đầy đủ thông tin để nạp vào
    public BaseEntity(String ID, String ten, Date ngay_tao, Date ngayUpdate) {
        this.id = ID;
        this.name = ten;
        this.createDate = ngay_tao;
        this.lastUpdateDate = ngayUpdate;
    }

    // ==== Getter/Setter ====
    // departmentID không được đổi -> không set nó hoặc có thì là private.
    // createDate cũng không được đổi -> khóa setter bằng private hoặc không set.
    // 1. Get: lấy thông tin trong private ra dùng
    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    // 2. Set: Đưa thông tin mới vào private (sửa thông tin)
    public void setName(String ten) {
        this.name = ten;
    }

    public void setLastUpdateDate(Date ngayUpdate) {
        this.lastUpdateDate = ngayUpdate;
    }

    // === Hàm trừu tượng (abstract) showInfo() để ép các lớp con phải tự định nghĩa
    // cách in thông tin riêng
    public abstract void showInfo();
}
