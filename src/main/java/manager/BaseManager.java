package manager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.*;
import view.*;

public abstract class BaseManager<T extends BaseEntity> implements IManager<T>{
    protected  List<T> list = new ArrayList<>();

    public List<T> getList(){return list;}

    @Override public T findByID(String id){
        for (T item : list) if (item.getID().equalsIgnoreCase(id)) return item;
        return null;
    }

    public boolean isDuplicateID(String id){
        return findByID(id) != null; 
    }

    @Override public boolean add(T item){
        return (item != null && !isDuplicateID(item.getID())) ? list.add(item) : false;
    }

    @Override public boolean delete(String id){
        T item = findByID(id);
        return (item != null) ? list.remove(item) : false;
    }

    @Override public void saveToFile(String path){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))){
            oos.writeObject(list);
        }catch (IOException e) {
            OutputViewer.Notice("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override public void loadFromFile(String path){
        File file = new File(path);
        if (!file.exists()) return; 
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            List<T> fileList = (List<T>) ois.readObject();
            for(T item : fileList){
                if(item != null && !isDuplicateID(item.getID())){
                    list.add(item);
                }
            }
        } catch (Exception e) {
            OutputViewer.Notice("Error loading file: " + e.getMessage());
        }
    }

    public boolean isEmptyList(String errorMsg){
        if (list.isEmpty()) { 
            OutputViewer.listEmptyErr(errorMsg);
            return true;
        }
        return false;
    }
}
