package Dao;

import java.util.List;

public interface GeneralDao<T>{
    public void insert(T obj);
    public void update(T obj,String... conductions);
    public void delete(T obj);
    public T selectById(T obj);
    public List<T> selectAll();


}
