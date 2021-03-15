package com.smartchoice.common.model.rabbitmq;

public interface QueueName {
    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN = "tiki.category.request.main";
    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_RETRY = "tiki.category.request.retry";
    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_PARKING = "tiki.category.request.parking";

    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN = "tiki.product.request.main";
    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_RETRY = "tiki.product.request.retry";
    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_PARKING = "tiki.product.request.parking";

    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN = "sendo.category.request.main";
    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_RETRY = "sendo.category.request.retry";
    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_PARKING = "sendo.category.request.parking";

    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN = "sendo.product.request.main";
    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_RETRY = "sendo.product.request.retry";
    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_PARKING = "sendo.product.request.parking";

    String SC_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_MAIN = "sc.category.response.main";
    String SC_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_RETRY = "sc.category.response.retry";
    String SC_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_PARKING = "sc.category.response.parking";

    String SC_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_MAIN = "sc.product.response.main";
    String SC_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_RETRY = "sc.product.response.retry";
    String SC_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_PARKING = "sc.product.response.parking";
}
