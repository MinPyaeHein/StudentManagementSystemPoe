package Utils;



import annotation.Column;
import annotation.Id;
import annotation.ManyToOne;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DaoUtail {

    public static List<String> getFieldNameFromObj(Object obj,Boolean need,String... fields){
        List<String> fieldsName = new ArrayList<String>();
        for (Field field: obj.getClass().getDeclaredFields()){
            List<String> listFields= List.of(fields);

            Id idAnnotation = field.getAnnotation(Id.class);
            Column columnAnnotation = field.getAnnotation(Column.class);
            ManyToOne manyToOneAnnotation = field.getAnnotation(ManyToOne.class);


            String fieldName = null;
            if (idAnnotation != null) {
                fieldName = idAnnotation.name();
            } else if (columnAnnotation != null) {
                fieldName = columnAnnotation.name();
            } else if(manyToOneAnnotation!=null){
                fieldName = manyToOneAnnotation.name();
            }

            if(need){
                if(listFields.contains(fieldName)){
                    fieldsName.add(fieldName);
                }
            }else{
                if(!listFields.contains(fieldName)){
                    fieldsName.add(fieldName);
                }
            }
        }
        return fieldsName;
    }
    public static List<Object> getFieldValueFromObj(Object obj,Boolean need,String... fields) throws IllegalAccessException {
        List<Object> values = new ArrayList<Object>();
        for (Field field: obj.getClass().getDeclaredFields()){
            List<String> listFields= List.of(fields);

            Id idAnnotation = field.getAnnotation(Id.class);
            Column columnAnnotation = field.getAnnotation(Column.class);
            ManyToOne manyToOneAnnotation = field.getAnnotation(ManyToOne.class);

            field.setAccessible(true);
            String fieldName = null;
            Object value=null;
            if (idAnnotation != null) {
                fieldName = idAnnotation.name();
                value=field.get(obj);
            } else if (columnAnnotation != null) {
                fieldName = columnAnnotation.name();
                value=field.get(obj);
            } else if(manyToOneAnnotation!=null){
                Object relatedObject = field.get(obj);
                Field idField=getIdField(relatedObject.getClass());
                idField.setAccessible(true);
                value=idField.get(relatedObject);
                fieldName = manyToOneAnnotation.name();
                System.out.println(fieldName+"="+value);
            }
            if(need){
                if(listFields.contains(fieldName)){
                    values.add(value);
                }
            }else{
                if(!listFields.contains(fieldName)){
                    values.add(value);
                }
            }
        }
        return values;
    }
    private static Field getIdField(Class<?> clazz){
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(Id.class)){
                System.out.println("Field " + field.getName() + " is annotated with @Id");
                return field;
            }
        }
        return null;
    }
}

