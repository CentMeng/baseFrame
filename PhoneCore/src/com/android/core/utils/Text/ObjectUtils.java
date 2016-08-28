package com.android.core.utils.Text;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 字符串工具类
 * 
 * @author ld
 * 
 */
public class ObjectUtils {

	public static Object isEmpty(Object obj, Class clazz) {
		if (obj == null || clazz == null) {
            return obj;
        }
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				String fieldName = field.getName();
				if (fieldName == null || fieldName.length() == 0)
					continue;
				String fieldMethodName = fieldName.substring(0, 1)
						.toUpperCase() + fieldName.substring(1);
				String getfieldName = "get" + fieldMethodName;
				Method getMethod = clazz.getMethod(getfieldName, null);
				if (getMethod == null)
					continue;
				Object value = getMethod.invoke(obj, null);
				Class type = field.getType();
				if (type == String.class) {
					if (value == null || "null".equals(value.toString())
							|| value.toString().trim().length() == 0) {
						value = "";
						String setfieldName = "set" + fieldMethodName;
						Method setMethod = clazz.getMethod(setfieldName,
								String.class);
						if (setMethod == null)
							continue;
						setMethod.invoke(obj, value);
					}
				} else if (type == Integer.class || type == int.class
						|| type == Float.class || type == float.class
						|| type == Double.class || type == double.class) {
					if (value == null || "null".equals(value.toString())
							|| value.toString().length() == 0) {
						String setfieldName = "set" + fieldMethodName;
						Method setMethod = clazz.getMethod(setfieldName, type);
						setMethod.invoke(obj, 0);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return obj;
	}
	
	public static <T> boolean listIsNull(List<T> list){
		if(list == null || list.size() == 0){
			return true;
		}
		return false;
		
	}


}
