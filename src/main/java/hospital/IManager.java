package hospital;



public interface IManager<T> {
    boolean add(T item);
    boolean update(T item);
    boolean delete(String id);
    T findById(String id);
    void showAll();
    void loadFromFile(String path) throws Exception;
    void saveToFile(String path) throws Exception;
}
