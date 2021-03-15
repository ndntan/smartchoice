package com.smartchoice.productprocessor.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.smartchoice.common.model.rabbitmq.QueueName;
import com.smartchoice.common.util.RabbitMQMessageConverter;
import com.smartchoice.productprocessor.services.category.CategoryResponseConsumer;
import com.smartchoice.productprocessor.services.product.ProductResponseConsumer;

@Component
public class RabbitQueueConfiguration {

	private static Logger log = LogManager.getLogger(RabbitQueueConfiguration.class);

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new RabbitMQMessageConverter();
	}

	@Bean
	MessageListenerAdapter categoryResponseListenerAdapter(CategoryResponseConsumer categoryResponseConsumer) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(categoryResponseConsumer, "consume");
		adapter.setMessageConverter(jsonMessageConverter());
		return adapter;
	}

	@Bean
	SimpleMessageListenerContainer categoryResponseContainer(ConnectionFactory connectionFactory,
			MessageListenerAdapter categoryResponseListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QueueName.SC_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_MAIN);
		container.setMessageListener(categoryResponseListenerAdapter);
		log.info("Listening on {}", QueueName.SC_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_MAIN);
		return container;
	}

	@Bean
	MessageListenerAdapter productResponseListenerAdapter(ProductResponseConsumer productResponseConsumer) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(productResponseConsumer, "consume");
		adapter.setMessageConverter(jsonMessageConverter());
		return adapter;
	}

	@Bean
	SimpleMessageListenerContainer productResponseContainer(ConnectionFactory connectionFactory,
															 MessageListenerAdapter productResponseListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QueueName.SC_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_MAIN);
		container.setMessageListener(productResponseListenerAdapter);
		log.info("Listening on {}", QueueName.SC_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_MAIN);
		return container;
	}
}
