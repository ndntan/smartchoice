package com.smartchoice.common.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


public class TimeUtil {
	public enum CF {
		ISO_8601 {
			@Override
			public DateTimeFormatter getPrinter() {
				return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			}

			@Override
			public DateTimeFormatter getParser() {
				return new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
						.appendPattern("[XXX][X]").toFormatter();
			}
		};

		public abstract DateTimeFormatter getPrinter();

		public abstract DateTimeFormatter getParser();
	}
}
