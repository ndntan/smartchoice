package com.smartchoice.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.smartchoice.common.model.gson.IncludeWithoutSuppressStrategy;

/**
 * Mark a field as ignored during serialization/deserialization.
 * 
 * @see IncludeWithoutSuppressStrategy
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Suppress {
}
