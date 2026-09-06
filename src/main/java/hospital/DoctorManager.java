package hospital;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.Language;
import util.ConsoleHelper;
import util.Validation;

// DoctorManager thực thi IManager<Doctor>. Khi thêm bác sĩ: không trùng doctorID, departmentID phải tồn tại (khóa ngoại).
public class DoctorManager implements IManager<Doctor> {
    private List<Doctor> doctorList;
    private DepartmentManager deptManager; // Dùng để kiểm tra khóa ngoại DeptID

    // Nhận DepartmentManager để add() kiểm tra khoa có tồn tại không
    public DoctorManager(DepartmentManager deptManager) {
        this.doctorList = new ArrayList<>();
        this.deptManager = deptManager;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    // Tìm bác sĩ theo doctorID (không phân biệt hoa thường)
    @Override
    public Doctor findByID(String id) {
        for (Doctor doc : doctorList) {
            if (doc.getDoctorID().equalsIgnoreCase(id)) {
                return doc;
            }
        }
        return null;
    }

    // true nếu doctorID đã có trong danh sách
    public boolean isDuplicateID(String id) {
        return findByID(id) != null;
    }

    // true nếu DeptID tồn tại trong DepartmentManager (khóa ngoại hợp lệ)
    public boolean isValidDepartmentID(String deptID) {
        return deptManager != null && deptManager.findByID(deptID) != null;
    }

    // Thêm bác sĩ: không null, không trùng ID, DeptID phải có trong danh sách khoa
    @Override
    public boolean add(Doctor doc) {
        if (doc == null || isDuplicateID(doc.getDoctorID()) || !isValidDepartmentID(doc.getDepartmentID())) {
            return false;
        }
        return doctorList.add(doc);
    }

    // Nhập từ console rồi gọi add(): kiểm tra trùng doctorID và khóa ngoại DeptID
    public void addFromInput() {
        System.out.println(Language.get(Language.ADD_DOC_TITLE));

        if (deptManager.getDepartmentList().isEmpty()) {
            ConsoleHelper.printNotice(Language.get(Language.EMPTY_DEPT_LIST));
            return;
        }

        String id;
        while (true) {
            id = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_DOC_ID),
                    Language.EMPTY_DOC_ID);
            if (isDuplicateID(id)) {
                ConsoleHelper.printNotice(Language.get(Language.DUPLICATE_DOC_ID));
            } else {
                break;
            }
        }

        String name = Validation.readNonEmptyString(
                Language.get(Language.PROMPT_DOC_NAME),
                Language.EMPTY_DOC_NAME);
        String sex = Validation.readGender(
                Language.get(Language.PROMPT_DOC_SEX),
                Language.EMPTY_GENDER);
        String address = Validation.readNonEmptyString(
                Language.get(Language.PROMPT_DOC_ADDRESS),
                Language.EMPTY_ADDRESS);

        String deptID;
        while (true) {
            deptID = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_DOC_DEPT_ID),
                    Language.EMPTY_DEPT_ID);
            if (!isValidDepartmentID(deptID)) {
                ConsoleHelper.printNotice(Language.get(Language.INVALID_DEPT_FK));
            } else {
                break;
            }
        }

        Date createDate = new Date();
        Doctor doc = new Doctor(id, name, sex, address, deptID, createDate, null);

        if (add(doc)) {
            ConsoleHelper.printNotice(Language.get(Language.ADD_DOC_SUCCESS) + Validation.formatDate(createDate));
            showAll();
        } else {
            ConsoleHelper.printNotice(Language.get(Language.ADD_DOC_FAIL));
        }
    }

    // Xóa bác sĩ theo ID
    @Override
    public boolean delete(String id) {
        Doctor doc = findByID(id);
        return (doc != null) ? doctorList.remove(doc) : false;
    }

    // Cập nhật tên, giới tính, địa chỉ, khoa và ngày sửa
    @Override
    public boolean update(Doctor doc) {
        Doctor updateDoc = findByID(doc.getDoctorID());
        if (updateDoc == null) {
            return false;
        }
        if (!isValidDepartmentID(doc.getDepartmentID())) {
            return false;
        }
        updateDoc.setName(doc.getName());
        updateDoc.setSex(doc.getSex());
        updateDoc.setAddress(doc.getAddress());
        updateDoc.setDepartmentID(doc.getDepartmentID());
        updateDoc.setLastUpdateDate(doc.getLastUpdateDate());
        return true;
    }

    // In bảng danh sách bác sĩ
    @Override
    public void showAll() {
        if (doctorList.isEmpty()) {
            ConsoleHelper.printNotice(Language.get(Language.EMPTY_DOC_LIST));
            return;
        }
        System.out.printf("| %-15s | %-30s | %-7s | %-50s | %-15s | %-12s | %-12s |\n",
                Language.get(Language.TABLE_DOC_ID),
                Language.get(Language.TABLE_DOC_NAME),
                Language.get(Language.TABLE_SEX),
                Language.get(Language.TABLE_ADDRESS),
                Language.get(Language.TABLE_DEPT_ID),
                Language.get(Language.TABLE_CREATE_DATE),
                Language.get(Language.TABLE_UPDATE_DATE));
        for (Doctor doc : doctorList) {
            doc.showInfo();
        }
    }

    @Override
    public void saveToFile(String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(doctorList);
        } catch (IOException e) {
            System.out.println(Language.get(Language.FILE_IO, e.toString()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadFromFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            doctorList = (List<Doctor>) ois.readObject();
        } catch (Exception e) {
            System.out.println(Language.get(Language.FILE_IO, e.getMessage()));
        }
    }
}
