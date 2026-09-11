package model;

import utils.Utils;;

/**
 * Represent a Student entity with encapsulated fields.
 */

public class Student {
    private String id;
    private String name;
    private float score;

    // Hàm khởi tạo constructor
    public Student() {
    }

    public Student(String id, String name, float score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    // Hàm Getter/Setter
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String nam) {this.name = nam;}
    public float getScore() {return score;}
    public void setScore(float score) {this.score = score;}

    // =============
    // Các hàm xử lý một SV
    // 1. Nhập thông tin SV
    public void create(String verifiedID){
        this.id = verifiedID; // Cần lọc ID trùng ở List
        this.name = Utils.readNonEmptyString("Nhap ten: ");
        this.score = Utils.getFloat("Nhap diem: ", 0, 10);
    }

    // Cập nhật thông tin 1 SV
    public boolean updateSV(Student newStudent){
        // Nếu newStudent chưa có gì -> false
        if (newStudent == null) return false;
        // gán giá trị của newStudent vào các thuộc tính: không có điều kiện cấm sửa ID -> chỉ cần ID không trùng
        this.id = newStudent.getId(); // Lấy ID = ID mới.
        this.name = newStudent.getName(); // Lấy tên mới của newStudent gán vào Student
        this.score = newStudent.getScore();
        return true;
    }
     
    // Xóa cần xử lý trong list với list.remove(item)
    // Sắp xếp cần viết trong list -> sort list
    // Tìm kiếm cần viết trong List vì cần thao tác trên list

    // Hàm In
    @Override public String toString(){
        return String.format("| %-15s | %-30s | %-5.2f |", id, name, score);
    }
    public void showInfo(){
        System.out.println(this.toString());
    }
}
