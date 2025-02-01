package Utils;

import annotation.EmailValidate;
import annotation.NotNull;
import Exception.InvalidDataFormatException;
import java.lang.reflect.Field;

public class ValidateUtail {
    public static void validate(Object obj){
            validateNullField(obj);
    }
    private static void validateNullField(Object obj){
        Class<?> clazz = obj.getClass();
        String errorMessage="";
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(NotNull.class)){
                field.setAccessible(true);
                try {
                    Object value=field.get(obj);
                    if(value==null || value.toString().isEmpty()){
                        NotNull annotation = field.getAnnotation(NotNull.class);
                        errorMessage+=annotation.message()+"\n";
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            if(field.isAnnotationPresent(EmailValidate.class)){
                field.setAccessible(true);
                try {
                    Object value=field.get(obj);
                    if(!emailValidator(value.toString())){
                        EmailValidate annotation = field.getAnnotation(EmailValidate.class);
                        errorMessage+=annotation.message()+"\n";
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(!errorMessage.isEmpty()){
            throw new InvalidDataFormatException(errorMessage);
        }
    }
    private static boolean emailValidator(String email){
        return email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

}
