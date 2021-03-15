package com.smartchoice.common.model.db;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class ListOfStringsUserType implements UserType, Serializable {

	private static final long serialVersionUID = -593309601184501659L;

	public static final String TYPE = "com.smartchoice.common.model.db.ListOfStringsUserType";

	private static final String DELIMITER = ",";

	private static final int[] SQL_TYPES = { Types.VARCHAR };

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return List.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y)
			return true;
		if (null == x || null == y)
			return false;
		return x.equals(y);
	}

	@Override
	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor session,
			Object owner) throws HibernateException, SQLException {
		String objectTypeStr = resultSet.getString(names[0]);
		if (objectTypeStr != null) {
			return convertToObject(objectTypeStr);
		} else {
			return new ArrayList<String>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index,
			SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			preparedStatement.setNull(index, Types.VARCHAR);
		} else {
			if (value instanceof String) {
				String strValue = (String) value;
				preparedStatement.setString(index, convertToString(Arrays.asList(strValue)));
			} else {
				List<String> strings = (List<String>) value;
				preparedStatement.setString(index, convertToString(strings));
			}
		}
	}

	private String convertToString(List<String> values) throws HibernateException {
		if (values == null || values.isEmpty()) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			for (String value : values) {
				sb.append(DELIMITER).append(value);
			}
			String str = sb.toString();
			if (str.startsWith(DELIMITER)) {
				str = str.substring(1);
			}
			return str;
		}
	}

	private List<String> convertToObject(String objectTypeStr) throws HibernateException {
		List<String> result = new ArrayList<String>();
		if (objectTypeStr != null) {
			String[] pieces = objectTypeStr.split(DELIMITER);
			result.addAll(Arrays.asList(pieces));
		}
		return result;
	}

	@Override
	public Object deepCopy(Object object) throws HibernateException {
		if (object == null) {
			return null;
		}
		return new ArrayList<>((List<String>) object);
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object object) throws HibernateException {
		return (Serializable) object;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}
