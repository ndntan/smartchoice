package com.smartchoice.common.model.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Support serialize enum with child variables Using:
 * GsonUtils.getGsonForEnum().toJson(object)
 *
 *
 * public enum Person {
 * 
 * A(true, 24, "VN"), B(true, 25, "AU"), C(false, 23, "FR");
 * 
 * private boolean sex; private int age; private String address;
 * 
 * Person(boolean sex, int age, String address) { this.sex = sex; this.age =
 * age; this.address = address; }
 * 
 * public boolean sex() { return sex; }
 * 
 * public int age() { return age; }
 * 
 * public String address() { return address; } }
 *
 * Result:
 *
 * [ { name: "A", address: "VN", sex: "true", age: "24" }, { name: "B", address:
 * "AU", sex: "true", age: "25" }, { name: "C", address: "FR", sex: "false",
 * age: "23" } ]
 *
 */
public class GenericEnumAdapterFactory implements TypeAdapterFactory {

	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Class<? super T> rawType = type.getRawType();
		if (rawType.isEnum()) {
			return new GenericEnumAdapter<>(type);
		}
		return null;
	}
}
