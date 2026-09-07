package hospital;

import java.util.Date;
import util.Language;
import util.ConsoleHelper;
import util.Validation;
import util.Validator;

public class DoctorManager extends BaseManager<Doctor> {
    private DepartmentManager deptManager;

    public DoctorManager(DepartmentManager deptManager) {
        this.deptManager = deptManager;
    }

    public boolean isValidDepartmentID(String deptID) {
        return deptManager != null && deptManager.findByID(deptID) != null;
    }

    @Override
    public boolean add(Doctor doc) {
        if (doc == null || isDuplicateID(doc.getDoctorID()) || !isValidDepartmentID(doc.getDepartmentID())) {
            return false;
        }
        return list.add(doc);
    }

    public void addFromInput() {
        System.out.println(Language.get(Language.ADD_DOC_TITLE));

        if (deptManager.getlist().isEmpty()) {
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

    public void printHeader() {
        System.out.printf("| %-15s | %-30s | %-7s | %-50s | %-15s | %-12s | %-12s |\n",
                Language.get(Language.TABLE_DOC_ID),
                Language.get(Language.TABLE_DOC_NAME),
                Language.get(Language.TABLE_SEX),
                Language.get(Language.TABLE_ADDRESS),
                Language.get(Language.TABLE_DEPT_ID),
                Language.get(Language.TABLE_CREATE_DATE),
                Language.get(Language.TABLE_UPDATE_DATE));
    }

    @Override
    public void showAll() {
        if (list.isEmpty()) {
            ConsoleHelper.printNotice(Language.get(Language.EMPTY_DOC_LIST));
            return;
        }
        printHeader();
        for (Doctor doc : list) {
            doc.showInfo();
        }
    }

    public void searchByName(String namekey) {
        boolean found = false;
        for (Doctor doc : list) {
            if (doc.getDoctorName().toLowerCase().contains(namekey.toLowerCase())) {
                if (!found) {
                    printHeader();
                    found = true;
                }
                doc.showInfo();
            }
        }
        if (!found) {
            Validator.Notice("Khong tim thay bac si nao chua tu khoa: " + namekey);
        }
    }
}
