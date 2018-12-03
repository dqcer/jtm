package com.dqcer.jtm.core.web;

import com.dqcer.jtm.core.util.DateUtil;
import com.dqcer.jtm.core.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @Author: dongQin
 * @Date: 2018/11/13 16:47
 * @Description:mybatis拦截器
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = { Statement.class })})
@Slf4j
public class SqlStatementTimeInterceptor implements Interceptor {


    //@Value("sql.execute.maxTime")
    private long maxTime = 1L;

    @Override
    public Object intercept(Invocation invocation){
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } catch (Exception e) {
            log.error("拦截参数失败: {}", e.getMessage());
            return null;
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            if (time > maxTime){
                BoundSql boundSql = statementHandler.getBoundSql();
                String sql = boundSql.getSql();
                Object parameterObject = boundSql.getParameterObject();
                List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
                sql = formatSql(sql, parameterObject, parameterMappingList, boundSql);
                log.warn("method: {}, execute time: {} ms,  sql: {}", mappedStatement.getId(), time, sql);
            }
        }


    }

    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }
    @Override
    public void setProperties(Properties arg0) {
    }

    /**
     * 格式化SQL
     *
     * @param sql
     * @param parameterObject
     * @param parameterMappingList
     * @return
     */
    private String formatSql(String sql, Object parameterObject, List<ParameterMapping> parameterMappingList, BoundSql boundSql) {
        if (sql == null || sql.length() == 0) {
            return "";
        }
        sql = beautifySql(sql);
        if (parameterObject == null || parameterMappingList == null || parameterMappingList.size() == 0) {
            return sql;
        }
        // 异常时返回
        String sqlWithoutReplacePlaceholder = sql;
        try {
            if (parameterMappingList != null) {
                Class<?> parameterObjectClass = parameterObject.getClass();
                sql = checkType(sql, parameterMappingList, parameterObject, parameterObjectClass, boundSql);
            }
        } catch (Exception e) {
            return sqlWithoutReplacePlaceholder;
        }
        return sql;
    }

    /**
     * 美化Sql
     *
     * @param sql
     * @return
     */
    private String beautifySql(String sql)  {
        sql = sql.replaceAll("[\\s\n ]+"," ");
        return sql;
    }

    /**
     * list参数
     *
     * @param sql
     * @param col
     * @return
     */
    private String handleListParameter(String sql, Collection<?> col) {
        if (col != null && col.size() != 0) {
            for (Object obj : col) {
                String value = null;
                Class<?> objClass = obj.getClass();
                if (isPrimitiveOrPrimitiveWrapper(objClass)) {
                    value = obj.toString();
                } else if (objClass.isAssignableFrom(String.class)) {
                    value = "\"" + obj.toString() + "\"";
                }
                sql = sql.replaceFirst("\\?", value);
            }
        }
        return sql;
    }

    /**
     * map参数
     *
     * @param sql
     * @param paramMap
     * @param parameterMappingList
     * @return
     */
    private String handleMapParameter(String sql, Map<?, ?> paramMap, List<ParameterMapping> parameterMappingList) {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            Object propertyName = parameterMapping.getProperty();
            Object propertyValue = paramMap.get(propertyName);
            if (!isEmpty(propertyValue)) {
                if (propertyValue.getClass().isAssignableFrom(String.class)) {
                    propertyValue = "\"" + propertyValue + "\"";
                }
                sql = sql.replaceFirst("\\?", propertyValue.toString());
            }
        }
        return sql;
    }

    /**
     * 基本类型、对象、string、pageUtil
     *
     * @param sql
     * @param parameterMappingList
     * @param parameterObjectClass
     * @param parameterObject
     * @return
     * @throws Exception
     */
    private String handleCommonParameter(String sql, List<ParameterMapping> parameterMappingList, Class<?> parameterObjectClass,
                                         Object parameterObject, BoundSql boundSql) throws Exception {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            String propertyValue = null;
            if (isPrimitiveOrPrimitiveWrapper(parameterObjectClass)) {
                propertyValue = parameterObject.toString();
            } else if(parameterObjectClass.isAssignableFrom(String.class)){
                propertyValue = "\'" + parameterObject.toString() + "\'";
            } else if (parameterObjectClass.isAssignableFrom(PageUtil.class)) {
                final String fieldName = "conditions";
                Field field = parameterObjectClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object condition = field.get(parameterObject);
                Class<?> conditionValueClass = condition.getClass();
                return checkType(sql, parameterMappingList, condition, conditionValueClass, boundSql);

            }else if(parameterObject instanceof MapperMethod.ParamMap){
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap)parameterObject;
                String property = parameterMapping.getProperty();
                if(paramMap.containsKey(property)){
                    Object o = paramMap.get(property);
                    if (!isEmpty(o)){
                        Class<?> aClass = o.getClass();
                        if (aClass.isAssignableFrom(String.class)) {
                            propertyValue = "\'" + aClass.toString() + "\'";
                        } else {
                            propertyValue = aClass.toString();
                        }
                    }else {
                        propertyValue = "\'"+"缺失"+"\'";
                    }
                    break;
                }else {
                    propertyValue = "\'"+"缺失"+"\'";
                    break;
                }
            } else{
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)){
                    Object additionalParameter = boundSql.getAdditionalParameter(propertyName);
                    propertyValue = getParameterValue(additionalParameter);
                }
                propertyName = propertyName.indexOf(".") != -1 ? StringUtils.unqualify(propertyName) : propertyName;
                List<Field> fields = getAllFieldsList(parameterObjectClass);
                for (Field field : fields){
                    if (field.getName().equals(propertyName)){
                        field.setAccessible(true);
                        Object object = field.get(parameterObject);
                        propertyValue = getParameterValue(object);
                        break;
                    }
                }
            }
            sql = sql.replaceFirst("\\?", propertyValue);
        }
        return sql;
    }

    /**
     * 检查对象类型
     *
     * @param sql sql语句
     * @param parameterMappingList 参数list
     * @param condition 分页object
     * @param conditionValueClass 分页class
     * @param boundSql
     * @return 替换后的SQL语句
     * @throws Exception
     */
    private String checkType(String sql, List<ParameterMapping> parameterMappingList, Object condition, Class<?> conditionValueClass, BoundSql boundSql) throws Exception {
        if (isStrictMap(conditionValueClass)) {
            DefaultSqlSession.StrictMap<Collection<?>> strictMap = (DefaultSqlSession.StrictMap<Collection<?>>)condition;
            final String keyProperty = "list";
            if (isList(strictMap.get(keyProperty).getClass())) {
                sql = handleListParameter(sql, strictMap.get(keyProperty));
            }
        } else if (isMap(conditionValueClass)) {
            Map<?, ?> paramMap = (Map<?, ?>) condition;
            sql = handleMapParameter(sql, paramMap, parameterMappingList);
        } else{
            sql = handleCommonParameter(sql, parameterMappingList, conditionValueClass, condition,boundSql);
        }
        return sql;
    }

    /**
     * 反射获取所有的字段包含父类
     *
     * @param cls
     * @return Field [] 数组
     */
    public  Field[] getAllFields(final Class<?> cls) {
        final List<Field> allFieldsList = getAllFieldsList(cls);
        return allFieldsList.toArray(new Field[allFieldsList.size()]);
    }

    /**
     * 反射获取所有的字段包含父类
     *
     * @param cls
     * @return list集合
     */
    public  List<Field> getAllFieldsList(final Class<?> cls) {
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            for (final Field field : declaredFields) {
                allFields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }


    /**
     * 是否基本数据类型或者基本数据类型的包装类
     *
     * @param parameterObjectClass
     * @return
     */
    private boolean isPrimitiveOrPrimitiveWrapper(Class<?> parameterObjectClass) {
        return parameterObjectClass.isPrimitive() || (parameterObjectClass.isAssignableFrom(Byte.class)
                        || parameterObjectClass.isAssignableFrom(Short.class)
                        || parameterObjectClass.isAssignableFrom(Integer.class)
                        || parameterObjectClass.isAssignableFrom(Long.class)
                        || parameterObjectClass.isAssignableFrom(Double.class)
                        || parameterObjectClass.isAssignableFrom(Float.class)
                        || parameterObjectClass.isAssignableFrom(Character.class)
                        || parameterObjectClass.isAssignableFrom(Boolean.class));
    }

    /**
     * 是否DefaultSqlSession的内部类StrictMap
     *
     * @param parameterObjectClass
     * @return
     */
    private boolean isStrictMap(Class<?> parameterObjectClass) {
        return parameterObjectClass.isAssignableFrom(DefaultSqlSession.StrictMap.class);
    }

    /**
     * 是否List的实现类
     *
     * @param clazz
     * @return
     */
    private boolean isList(Class<?> clazz) {
        Class<?>[] interfaceClasses = clazz.getInterfaces();
        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(List.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否Map的实现类
     *
     * @param parameterObjectClass
     * @return
     */
    private boolean isMap(Class<?> parameterObjectClass) {
        Class<?>[] interfaceClasses = parameterObjectClass.getInterfaces();
        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(Map.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将Object类型toString()
     *
     * @param obj
     * @return
     */
    private  String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            String dateToString = DateUtil.dateToDateTime( (Date) obj);
            value = "to_date('"+dateToString+"','yyyy-mm-dd HH24:mi:ss')";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

}
