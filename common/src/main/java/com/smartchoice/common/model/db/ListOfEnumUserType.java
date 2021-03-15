package com.smartchoice.common.model.db;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.TypeResolver;
import org.hibernate.type.spi.TypeConfiguration;
import org.hibernate.usertype.ParameterizedType;

public class ListOfEnumUserType extends MutableUserType implements ParameterizedType {

	public static final String TYPE = "com.smartchoice.common.model.db.ListOfEnumUserType";

	public static final String DELIMITER = ",";

	private Class<? extends Enum> enumClass;

	private Class<?> identifierType;

	private Method identifierMethod;

	private Method valueOfMethod;

	private static final String defaultIdentifierMethodName = "name";

	private static final String defaultValueOfMethodName = "valueOf";

	private AbstractSingleColumnStandardBasicType type;

	private int[] sqlTypes;

	private boolean useEmptyList;

	@Override
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClass");
		try {
			enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
		} catch (ClassNotFoundException exception) {
			throw new HibernateException("Enum class not found", exception);
		}

		String identifierMethodName = parameters.getProperty("identifierMethod", defaultIdentifierMethodName);

		try {
			identifierMethod = enumClass.getMethod(identifierMethodName, new Class[0]);
			identifierType = identifierMethod.getReturnType();
		} catch (Exception exception) {
			throw new HibernateException("Failed to optain identifier method", exception);
		}

		TypeResolver tr = new TypeConfiguration().getTypeResolver();
		type = (AbstractSingleColumnStandardBasicType) tr.basic(identifierType.getName());
		if (type == null) {
			throw new HibernateException("Unsupported identifier type " + identifierType.getName());
		}
		sqlTypes = new int[] { type.sqlType() };

		String valueOfMethodName = parameters.getProperty("valueOfMethod", defaultValueOfMethodName);
		try {
			valueOfMethod = enumClass.getMethod(valueOfMethodName, new Class[] { identifierType });
		} catch (Exception exception) {
			throw new HibernateException("Failed to optain valueOf method", exception);
		}

		try {
			useEmptyList = Boolean.parseBoolean(parameters.getProperty("useEmptyList", "true"));
		} catch (Exception e) {
			throw new HibernateException("Failed to optain useEmptyList", e);
		}
	}

	@Override
	public Class returnedClass() {
		return enumClass;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		Object identifier = type.get(rs, names[0], session);
		if (identifier == null) {
			return useEmptyList ? new ArrayList<>() : null;
		}
		try {
			List<Object> lst = new ArrayList<>();
			String[] pieces = identifier.toString().split(DELIMITER);
			for (String piece : pieces) {
				if (!StringUtils.isEmpty(piece)) {
					lst.add(valueOfMethod.invoke(enumClass, new Object[] { piece }));
				}
			}

			return lst;
		} catch (Exception exception) {
			throw new HibernateException("Exception while invoking valueOfMethod of enumeration class: ", exception);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {

		if (value == null) {
			if (useEmptyList) {
				st.setObject(index, "");
			} else {
				st.setNull(index, type.sqlType());
			}
			return;
		}

		try {
			List<? extends Enum> lst = (List<? extends Enum>) value;

			StringBuilder sb = new StringBuilder();
			for (Enum enum1 : lst) {
				sb.append(DELIMITER).append(identifierMethod.invoke(enum1, new Object[0]));
			}
			String str = sb.toString();
			if (str.startsWith(DELIMITER)) {
				str = str.substring(1);
			}
			st.setObject(index, str);
		} catch (Exception exception) {
			throw new HibernateException("Exception while invoking identifierMethod of enumeration class: ", exception);
		}
	}

	@Override
	public int[] sqlTypes() {
		return sqlTypes;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		return new ArrayList<>((List) value);
	}

}
