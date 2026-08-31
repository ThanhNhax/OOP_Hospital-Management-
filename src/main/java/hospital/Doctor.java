package hospital;

public class Doctor extends BaseEntity {

    // Constructor không tham số
    public Doctor() {
    }

    @Override
    public void showInfo() {
        System.out.println(this.toString()); // In ra theo kiểu toString() được định nghĩa trong class này
    }
    // Như vậy, giả sử đối tượng Department de = new Department() -> de.showInfo()
    // sẽ in ra theo kiểu của class này
    // Và Doctor do = new Doctor() -> do.showInfo() sẽ in ra theo kiểu bảng của
    // class Doctor.
}
