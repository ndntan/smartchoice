package com.smartchoice.common.model.gson;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GsonZdtToLdtUTCDeserializer implements JsonDeserializer<LocalDateTime> {

	private DateTimeFormatter formatter;

	/**
	 * @param formatter Must have timezone part, otherwise this will fail.
	 */
	public GsonZdtToLdtUTCDeserializer(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	@Override
	public LocalDateTime deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext jsonDeserializationContext) {
		String dateTime = jsonElement.getAsString();
		try {
			if (dateTime.length() == 16) {
				dateTime = dateTime + ":00Z";
			}
			return ZonedDateTime.parse(dateTime, formatter).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
		} catch (DateTimeParseException e) {
			throw new JsonParseException(e);
		}
	}
}
