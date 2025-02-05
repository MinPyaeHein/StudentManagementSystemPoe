package Dao;

import java.sql.SQLException;
import java.util.List;

public interface GeneralDao<T>{
    public void insert(T obj) throws SQLException;
    public void update(T obj,String... conductions) throws SQLException;
    public void delete(T obj) throws SQLException;
    public T selectById(T obj);
    public List<T> selectAll();


}
