package com.smartchoice.tikisupplier.server.config;

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
import com.smartchoice.tikisupplier.server.service.consumers.CategoryConsumer;
import com.smartchoice.tikisupplier.server.service.consumers.ProductConsumer;

@Component
public class RabbitQueueConfiguration {

	private static Logger log = LogManager.getLogger(RabbitQueueConfiguration.class);

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new RabbitMQMessageConverter();
	}

	@Bean
	MessageListenerAdapter categoryRequestListenerAdapter(CategoryConsumer categoryConsumer) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(categoryConsumer, "consume");
		adapter.setMessageConverter(jsonMessageConverter());
		return adapter;
	}

	@Bean
	SimpleMessageListenerContainer categoryRequestContainer(ConnectionFactory connectionFactory,
			MessageListenerAdapter categoryRequestListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QueueName.TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN);
		container.setMessageListener(categoryRequestListenerAdapter);
		log.info("Listening on {}", QueueName.TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN);
		return container;
	}

	@Bean
	MessageListenerAdapter productRequestListenerAdapter(ProductConsumer productConsumer) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(productConsumer, "consume");
		adapter.setMessageConverter(jsonMessageConverter());
		return adapter;
	}

	@Bean
	SimpleMessageListenerContainer productRequestContainer(ConnectionFactory connectionFactory,
															MessageListenerAdapter productRequestListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QueueName.TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN);
		container.setMessageListener(productRequestListenerAdapter);
		log.info("Listening on {}", QueueName.TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN);
		return container;
	}
}
