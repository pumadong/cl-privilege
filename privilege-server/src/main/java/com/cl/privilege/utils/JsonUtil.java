package com.cl.privilege.utils;

import java.lang.reflect.Field;
import java.util.Collection;


public class JsonUtil {

	public static StringBuffer convertObj2json(Collection<?> cs) {

		StringBuffer sbf = new StringBuffer();

		if (null == cs || 0 == cs.toArray().length){
			
			return sbf.append("[").append("]");
		}

		Object[] ls = cs.toArray();
		int size = ls.length;

		sbf.append("[");
		if (0 == size)
			return new StringBuffer("[]");
		for (int k = 0; k < size; k++) {
			Object o = ls[k];

			sbf.append(convertObj2json(o));

			if (k < size - 1)
				sbf.append(", ");
		}

		return sbf.append("]");

	}

	public static StringBuffer convertObj2json(Object o) {

		StringBuffer sbf = new StringBuffer();
		if (null == o)
			return sbf;

		sbf.append("{");
		Class<?> classType = o.getClass();
		Field[] fields = classType.getDeclaredFields();

		int length = fields.length;

		for (int i = 0; i < length; i++) {

			String fieldName = fields[i].getName();
			Class<?> clazzType = fields[i].getType();
			Package package1 = clazzType.getPackage();
			Object fo = ReflectionUtil.getFieldValue(o, fieldName);

			if (!(fo instanceof Collection)
					&& (clazzType.isPrimitive() || null == package1
							|| package1.getName().equals("java.lang") || package1
							.getName().equals("java.util"))) {
				sbf.append("\"").append(fieldName).append("\":\"").append(fo)
						.append("\"");
			} else if (!(fo instanceof Collection)) {
				sbf.append("\"").append(fieldName).append("\":").append(
						convertObj2json(fo));
			}

			if (fo instanceof Collection) {

				sbf.append("\"").append(fieldName).append("\":").append(
						convertObj2json((Collection<?>) fo));

			}

			if (i < length - 1)
				sbf.append(", ");

		}

		return sbf.append("}");
	}

}