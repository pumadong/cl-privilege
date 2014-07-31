package com.cl.privilege.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

@SuppressWarnings("rawtypes")
public class ReflectionUtil
{
  private static Logger logger = Logger.getLogger(ReflectionUtil.class);

  public static Object invokeGetterMethod(Object target, String propertyName)
  {
    String getterMethodName = "get" + StringUtils.capitalize(propertyName);
    return invokeMethod(target, getterMethodName, new Class[0], new Object[0]);
  }

  public static void invokeSetterMethod(Object target, String propertyName, Object value)
  {
    invokeSetterMethod(target, propertyName, value, null);
  }

  public static void invokeSetterMethod(Object target, String propertyName, Object value, Class<?> propertyType)
  {
    
	Class type = (propertyType != null) ? propertyType : value.getClass();
    String setterMethodName = "set" + StringUtils.capitalize(propertyName);
    invokeMethod(target, setterMethodName, new Class[] { type }, new Object[] { value });
  }

  public static Object getFieldValue(Object object, String fieldName)
  {
    Field field = getDeclaredField(object, fieldName);

    if (field == null) {
      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
    }

    makeAccessible(field);

    Object result = null;
    try {
      result = field.get(object);
    } catch (IllegalAccessException e) {
      logger.error("不可能抛出的异常{}", e);
    }
    return result;
  }

  public static void setFieldValue(Object object, String fieldName, Object value)
  {
    Field field = getDeclaredField(object, fieldName);

    if (field == null) {
      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
    }

    makeAccessible(field);
    try
    {
      field.set(object, value);
    } catch (IllegalAccessException e) {
      logger.error("不可能抛出的异常:{}");
    }
  }

  public static void setFieldValueByFieldType(Object object, String fieldName, Object value)
  {
    Field field = getDeclaredField(object, fieldName);

    if (field == null) {
      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
    }
    setFieldValue(object, fieldName, convertStringToObject((String)value, field.getType()));
  }

  public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters)
  {
    Method method = getDeclaredMethod(object, methodName, parameterTypes);
    if (method == null) {
      throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
    }

    method.setAccessible(true);
    try
    {
      return method.invoke(object, parameters);
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }
  }

  public static Field getDeclaredField(Object object, String fieldName)
  {
    Assert.notNull(object, "object不能为空");
    Assert.hasText(fieldName, "fieldName");
    for (Class superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
      try
      {
        return superClass.getDeclaredField(fieldName);
      }
      catch (NoSuchFieldException e)
      {
      }
    return null;
  }

  protected static void makeAccessible(Field field)
  {
    if ((!(Modifier.isPublic(field.getModifiers()))) || (!(Modifier.isPublic(field.getDeclaringClass().getModifiers()))))
      field.setAccessible(true);
  }

  @SuppressWarnings("unchecked")
  protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes)
  {
    Assert.notNull(object, "object不能为空");

    for (Class superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
      try
      {
        return superClass.getDeclaredMethod(methodName, parameterTypes);
      }
      catch (NoSuchMethodException e)
      {
      }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static <T> Class<T> getSuperClassGenricType(Class clazz)
  {
    return getSuperClassGenricType(clazz, 0);
  }

  public static Class getSuperClassGenricType(Class clazz, int index)
  {
    Type genType = clazz.getGenericSuperclass();

    if (!(genType instanceof ParameterizedType)) {
      logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
      return Object.class;
    }

    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();

    if ((index >= params.length) || (index < 0)) {
      logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);

      return Object.class;
    }
    if (!(params[index] instanceof Class)) {
      logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
      return Object.class;
    }

    return ((Class)params[index]);
  }

  public static List convertElementPropertyToList(Collection collection, String propertyName)
  {
    List<Object> list = new ArrayList<Object>();
    Iterator i$;
    try {
      for (i$ = collection.iterator(); i$.hasNext(); ) { Object obj = i$.next();
        list.add(PropertyUtils.getProperty(obj, propertyName));
      }
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }

    return list;
  }

  public static String convertElementPropertyToString(Collection collection, String propertyName, String separator)
  {
    List list = convertElementPropertyToList(collection, propertyName);
    return StringUtils.join(list, separator);
  }

  public static Object convertStringToObject(String value, Class<?> toType)
  {
    try
    {
      DateConverter dc = new DateConverter();
      dc.setUseLocaleFormat(true);
      dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
      ConvertUtils.register(dc, Date.class);
      return ConvertUtils.convert(value, toType);
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }
  }

  public static RuntimeException convertReflectionExceptionToUnchecked(Exception e)
  {
    if ((e instanceof IllegalAccessException) || (e instanceof IllegalArgumentException) || (e instanceof NoSuchMethodException))
    {
      return new IllegalArgumentException("Reflection Exception.", e); }
    if (e instanceof InvocationTargetException)
      return new RuntimeException("Reflection Exception.", ((InvocationTargetException)e).getTargetException());
    if (e instanceof RuntimeException) {
      return ((RuntimeException)e);
    }
    return new RuntimeException("Unexpected Checked Exception.", e);
  }

  static
  {
    DateConverter dc = new DateConverter();
    dc.setUseLocaleFormat(true);
    dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
    ConvertUtils.register(dc, Date.class);
  }
}