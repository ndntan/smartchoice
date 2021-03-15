package com.smartchoice.common.model.gson;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonLocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

	private DateTimeFormatter formatter;
	private boolean ldtToUTC;

	public GsonLocalDateTimeSerializer(DateTimeFormatter formatter, boolean ldtToUTC) {
		this.formatter = formatter;
		this.ldtToUTC = ldtToUTC;
	}

	public GsonLocalDateTimeSerializer(DateTimeFormatter formatter) {
		this(formatter, true);
	}

	@Override
	public JsonElement serialize(LocalDateTime date, Type type, JsonSerializationContext jsonSerializationContext) {
		if (ldtToUTC) {
			return new JsonPrimitive(formatter.format(date.atZone(ZoneOffset.UTC)));
		} else {
			return new JsonPrimitive(formatter.format(date));
		}
	}
}
