package Dao.impl;
import Dao.GeneralDao;
import Utils.AlertUtil;
import Utils.DaoUtail;
import annotation.Column;
import annotation.Id;
import annotation.Table;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public abstract class GeneralDaoImpl<T> implements GeneralDao<T> {
    public ConnectionDaoImpl connectionDao;
    private Class<T> classType;
    private String tableName;

    public GeneralDaoImpl(Class<T> classType) {
        this.classType = classType;
        connectionDao = new ConnectionDaoImpl();
        this.tableName=classType.getAnnotation(Table.class).name();
    }
    public abstract T  convertToObject(ResultSet rs) throws SQLException;
    //insert
    @Override
    public void insert(T obj) {
        String query=generateInsertQuery(obj);
        System.out.println(query);
        String idColumn=getColumnName(obj,"Id");
        executeUpdate( "insert",obj, query,idColumn);
    }

    @Override
    public void update(T obj,String... conductions) {
        String query=generateUpdateQuery(obj,conductions);
        executeUpdate("update",obj,query,conductions);

    }
    @Override
    //Delete Row
    public void delete(T obj) {
        String idColumn=getColumnName(obj,"Id");
        String sql = "DELETE FROM " + this.tableName +" WHERE "+idColumn+" = ?";
        executeUpdate("delete",obj,sql,"id");

    }
    @Override
    //select by id
    public T selectById(T obj) {
        List<T> list= null;
        String query=null;
        try {
            String idColumn=getColumnName(obj,"Id");
            Object object= DaoUtail.getFieldValueFromObj(obj,true,idColumn).get(0);
            query  = "SELECT  * From " + this.tableName + " where "+idColumn+" = ?";
            System.out.println(query);
            list = executeQuerry(query, object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch(NullPointerException e) {
            System.out.println("error is here null pointer exception "+this.tableName);
            System.out.println("querry"+query);
        }
        if(list!=null){
            return list.get(0);
        }
        return null;
    }



    @Override
    //select all
    public List<T> selectAll(){
        String sql = "SELECT * FROM " + this.tableName ;
        return executeQuerry(sql);
    }

    public List<T> executeQuerry(String query,Object... values){
        List<T> list= new ArrayList<>();
        Connection connection = null;
        try {
            connection = connectionDao.connectionWithSqlDb();
            System.out.println("connection"+connection);
            PreparedStatement preparedStatement =  connection.prepareStatement(query);
            int count = 1;
            for (Object obj : values) {
                preparedStatement.setObject(count, obj);
                count++;
            }
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                list.add(convertToObject(rs));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    //insert,update,delete
    private void executeUpdate(String type,Object obj,String query,String... conductions){
        try {
            Connection connection = connectionDao.connectionWithSqlDb();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int count = 1;
            if(!type.equals("delete")){
                List<Object> values= DaoUtail.getFieldValueFromObj(obj,false ,conductions);
                for (Object value : values) {
                   System.out.println("value to insert==="+value.toString());
                    preparedStatement.setObject(count, value);
                    count++;
                }
            }
            if(type.equals("update")||type.equals("delete")){
                List<Object> values= DaoUtail.getFieldValueFromObj(obj,true,conductions);
                for(Object value:values){
                    preparedStatement.setObject(count, value);
                    count++;
                }
            }
            int rowAffect = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (RuntimeException | SQLException | IllegalAccessException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
        } catch (IOException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //insert,update,delete for customized
    public void executeUpdate(String query,Object... obj){
        try {
            Connection connection = connectionDao.connectionWithSqlDb();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int count = 1;
            for(Object o : obj){
                if (o instanceof java.util.Date) {
                    preparedStatement.setObject(count, o, java.sql.Types.TIMESTAMP); // Specify TIMESTAMP for Date objects
                } else {
                    preparedStatement.setObject(count, o); // Use default handling for other objects
                }
                count++;
            }
            int rowAffect = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateInsertQuery(Object obj){

        String query = "INSERT INTO " + this.tableName + " (";
        String idColumn=getColumnName(obj,"Id");
        List<String> fieldsName = DaoUtail.getFieldNameFromObj(obj,false ,idColumn);
        for (String fieldName: fieldsName) {
            query += fieldName + ", ";
        }
        query = query.substring(0, query.length() - 2);
        query += ") VALUES (";
        for (String fieldName :fieldsName) {
            query += "?, ";
        }
        query = query.substring(0, query.length() - 2);
        query += ")";
        return query;
    }
    private String generateUpdateQuery(Object obj,String... conductions){

        String sql = "UPDATE " + this.tableName + " SET " ;
        List<String> updateFields = DaoUtail.getFieldNameFromObj(obj,false,conductions);
        for(String fieldName: updateFields) {
            sql += fieldName + " = ?, ";
        }
        sql = sql.substring(0,sql.length() - 2);
        sql += " WHERE ";
        for (String conduction: conductions ){
            sql +=conduction+" = ? and ";
        }
        sql = sql.substring(0,sql.length() - 4);
        return sql;
    }

    private String getColumnName(Object obj,String annoType) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (annoType.equals("Id")) {
                if (field.isAnnotationPresent(Id.class)) {
                    Id id = field.getAnnotation(Id.class);
                    return id.name();
                }
            } else if (annoType.equals("Column")) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    return column.name();
                }
            }

        }
        return null;
    }
}
