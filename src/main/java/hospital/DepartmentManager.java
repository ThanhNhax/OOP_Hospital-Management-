package hospital;

import java.io.*;
import java.util.Date;
import util.Language;
import util.ConsoleHelper;
import util.Validation;
import util.Validator;

public class DepartmentManager extends BaseManager<Department> {

    public void addFromInput() {
        System.out.println(Language.get(Language.ADD_DEPT_TITLE));

        String id;
        while (true) {
            id = Validation.readNonEmptyString(
                    Language.get(Language.PROMPT_DEPT_ID),
                    Language.EMPTY_DEPT_ID);
            if (isDuplicateID(id)) {
                ConsoleHelper.printNotice(Language.get(Language.DUPLICATE_DEPT_ID));
            } else {
                break;
            }
        }

        String name = Validation.readNonEmptyString(
                Language.get(Language.PROMPT_DEPT_NAME),
                Language.EMPTY_DEPT_NAME);

        Date createDate = new Date();
        Department dept = new Department(id, name, createDate, null);

        if (add(dept)) {
            ConsoleHelper.printNotice(Language.get(Language.ADD_DEPT_SUCCESS) + Validation.formatDate(createDate));
            showAll();
        } else {
            ConsoleHelper.printNotice(Language.get(Language.ADD_DEPT_FAIL));
        }
    }

    @Override
    public boolean update(Department dept) {
        Department updateDept = findByID(dept.getDepartmentID());
        if (updateDept != null) {
            updateDept.setName(dept.getName());
            updateDept.setLastUpdateDate(dept.getLastUpdateDate());
            return true;
        }
        return false;
    }

    public void printHeader() {
        System.out.printf("| %-15s | %-30s | %-12s | %-12s |\n",
                Language.get(Language.TABLE_DEPT_ID),
                Language.get(Language.TABLE_DEPT_NAME),
                Language.get(Language.TABLE_CREATE_DATE),
                Language.get(Language.TABLE_UPDATE_DATE));
    }

    @Override
    public void showAll() {
        if (list.isEmpty()) {
            ConsoleHelper.printNotice(Language.get(Language.EMPTY_DEPT_LIST));
            return;
        }
        printHeader();
        for (Department dept : list) {
            dept.showInfo();
        }
    }

    public void displayDeparmtentByID(String id) {
        Department dept = findByID(id);
        if (dept != null) {
            printHeader();
            dept.showInfo();
        } else {
            Validator.Notice("Khong tim thay phong ban co ID: " + id);
        }
    }

    public boolean hasDoctor(String deptID, DoctorManager docMan) {
        for (Doctor doc : docMan.getlist()) {
            if (doc.getDepartmentID().equalsIgnoreCase(deptID)) {
                return true;
            }
        }
        return false;
    }

    public void updateFromInput() {
        String id = Validation.readNonEmptyString(Language.get(Language.PROMPT_UPDATE_ID), Language.EMPTY_DEPT_ID);
        Department dept = findByID(id);
        if (dept == null) {
            ConsoleHelper.printNotice(Language.get(Language.NOT_FOUND));
            return;
        }
        String name = Validation.readNonEmptyString(Language.get(Language.PROMPT_DEPT_NAME), Language.EMPTY_DEPT_NAME);
        dept.setName(name);
        dept.setLastUpdateDate(new Date());
        ConsoleHelper.printNotice(Language.get(Language.UPDATE_SUCCESS));
    }

    public void deleteFromInput(DoctorManager docMan) {
        String id = Validation.readNonEmptyString(Language.get(Language.PROMPT_DELETE_ID), Language.EMPTY_DEPT_ID);
        if (hasDoctor(id, docMan)) {
            ConsoleHelper.printNotice(Language.get(Language.DELETE_FAIL));
            return;
        }
        if (delete(id)) {
            ConsoleHelper.printNotice(Language.get(Language.DELETE_SUCCESS));
        } else {
            ConsoleHelper.printNotice(Language.get(Language.DELETE_FAIL));
        }
    }

    public void searchFromInput() {
        String id = Validation.readNonEmptyString(Language.get(Language.PROMPT_SEARCH_ID), Language.EMPTY_DEPT_ID);
        Department dept = findByID(id);
        if (dept != null) {
            printHeader();
            dept.showInfo();
        } else {
            ConsoleHelper.printNotice(Language.get(Language.NOT_FOUND));
        }
    }
}