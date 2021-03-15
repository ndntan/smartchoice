package com.smartchoice.common.model.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import com.smartchoice.common.annotation.Suppress;

/**
 * Skip fields with {@link Suppress}
 * com.smartchoice.common.annotation when Gson is used.
 */
public class IncludeWithoutSuppressStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes field) {
		Suppress annotation = field.getAnnotation(Suppress.class);
		return annotation != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
