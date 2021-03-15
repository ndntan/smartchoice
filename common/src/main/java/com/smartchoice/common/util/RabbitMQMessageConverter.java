package com.smartchoice.common.util;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.lang.Nullable;

import com.google.gson.Gson;

import com.smartchoice.common.model.gson.SCGson;


public class RabbitMQMessageConverter extends Jackson2JsonMessageConverter {

	private static Logger logger = LogManager.getLogger(RabbitMQMessageConverter.class);

	private Gson gson;

	public RabbitMQMessageConverter() {
		super();
		DefaultClassMapper mapper = new DefaultClassMapper();
		mapper.setTrustedPackages("*");
		setClassMapper(mapper);
		initializeGson();
	}

	/**
	 * Subclass and override to customize.
	 */
	private void initializeGson() {
		gson = SCGson.getGson();
	}

	@Override
	public Object fromMessage(Message message) {
		try {
			Object content = null;
			MessageProperties properties = message.getMessageProperties();
			if (properties != null) {
				String contentType = properties.getContentType();
				if (contentType != null && contentType.contains("json")) {
					String encoding = properties.getContentEncoding();
					if (encoding == null) {
						encoding = getDefaultCharset();
					}

					Class<?> targetClass = getClassMapper().toClass(message.getMessageProperties());
					content = convertBytesToObject(message.getBody(), encoding, targetClass);

				} else {
					logger.warn("Could not convert incoming message with content-type [{}]", contentType);
				}
			}
			if (content == null) {
				content = message.getBody();
			}
			return content;
		} catch (Exception e) {
			logger.error("Fail to convert webhook messages {}", message, e);
			throw new MessageConversionException("Failed to convert message content", e);
		}
	}

	private Object convertBytesToObject(byte[] body, String encoding, Class<?> targetClass) throws IOException {
		String contentAsString = new String(body, encoding);
		return gson.fromJson(contentAsString, targetClass);
	}

	@Override
	protected Message createMessage(Object objectToConvert, MessageProperties messageProperties,
			@Nullable Type genericType) {
		try {
			byte[] bytes;
			String jsonString = gson.toJson(objectToConvert);
			bytes = jsonString.getBytes(getDefaultCharset());
			messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
			messageProperties.setContentEncoding(getDefaultCharset());
			messageProperties.setContentLength(bytes.length);
			getClassMapper().fromClass(objectToConvert.getClass(), messageProperties);
			return new Message(bytes, messageProperties);
		} catch (Exception e) {
			logger.error("Fail to create webhook messages {}", objectToConvert, e);
			throw new MessageConversionException("Failed to convert message content", e);
		}
	}
}
