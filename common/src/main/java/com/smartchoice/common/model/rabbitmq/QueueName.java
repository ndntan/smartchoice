package com.smartchoice.common.model.rabbitmq;

public interface QueueName {
    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN = "tiki.category.request.main";
    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_RETRY = "tiki.category.request.retry";
    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_PARKING = "tiki.category.request.parking";

    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_MAIN = "tiki.category.response.main";
    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_RETRY = "tiki.category.response.retry";
    String TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_PARKING = "tiki.category.response.parking";

    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN = "tiki.product.request.main";
    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_RETRY = "tiki.product.request.retry";
    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_PARKING = "tiki.product.request.parking";

    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_MAIN = "tiki.product.response.main";
    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_RETRY = "tiki.product.response.retry";
    String TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_PARKING = "tiki.product.response.parking";

    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN = "sendo.category.request.main";
    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_RETRY = "sendo.category.request.retry";
    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_PARKING = "sendo.category.request.parking";

    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_MAIN = "sendo.category.response.main";
    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_RETRY = "sendo.category.response.retry";
    String SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_RESPONSE_PARKING = "sendo.category.response.parking";

    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN = "sendo.product.request.main";
    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_RETRY = "sendo.product.request.retry";
    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_PARKING = "sendo.product.request.parking";

    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_MAIN = "sendo.product.response.main";
    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_RETRY = "sendo.product.response.retry";
    String SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_RESPONSE_PARKING = "sendo.product.response.parking";
}
