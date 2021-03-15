package com.smartchoice.common.model.gson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class GenericEnumAdapter<T> extends TypeAdapter<T> {

	private Map<String, T> values = new HashMap<>();

	private TypeToken<T> type;

	public GenericEnumAdapter(TypeToken<T> type) {
		Class<T> rawType = (Class<T>) type.getRawType();
		for (T t : rawType.getEnumConstants()) {
			values.put(t.toString(), t);
		}
	}

	@Override
	public void write(JsonWriter out, T value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		// write begin
		out.beginObject();
		out.name("name");
		out.value(value.toString());
		Field[] fields = value.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				if (field.trySetAccessible()) {
					Object fieldValue;
					try {
						fieldValue = field.get(value);
						if (fieldValue != null) {
							out.name(field.getName());
							out.value(fieldValue.toString());
						}
					} catch (IllegalAccessException e) {
						throw new IOException(e);
					}
				}
			}
		}
		// write end
		out.endObject();
	}

	@Override
	public T read(JsonReader in) throws IOException {
		T value = null;
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
		} else if (in.peek() == JsonToken.BEGIN_OBJECT) {
			in.beginObject();
			while (in.hasNext()) {
				String field = in.nextName();
				if ("name".equals(field)) {
					value = values.get(in.nextString());
				}
			}
			in.endObject();
		}
		return value;
	}
}
