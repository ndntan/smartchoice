package com.smartchoice.common.model.rabbitmq;

public interface ExchangeName {
    String TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_MAIN = "tiki.category.exchange.direct.request.main";
    String TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_RETRY = "tiki.category.exchange.direct.request.retry";
    String TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_PARKING = "tiki.category.exchange.direct.request.parking";

    String TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_MAIN = "tiki.category.exchange.direct.request.main";
    String TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_RETRY = "tiki.category.exchange.direct.request.retry";
    String TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_PARKING = "tiki.category.exchange.direct.request.parking";


    String SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_MAIN = "sendo.category.exchange.direct.request.main";
    String SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_RETRY = "sendo.category.exchange.direct.request.retry";
    String SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_PARKING = "sendo.category.exchange.direct.request.parking";

    String SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_MAIN = "sendo.category.exchange.direct.request.main";
    String SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_RETRY = "sendo.category.exchange.direct.request.retry";
    String SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_PARKING = "sendo.category.exchange.direct.request.parking";

    String SC_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_RESPONSE_MAIN = "sc.category.exchange.direct.response.main";
    String SC_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_RESPONSE_RETRY = "sc.category.exchange.direct.response.retry";
    String SC_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_RESPONSE_PARKING = "sc.category.exchange.direct.response.parking";

    String SC_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_RESPONSE_MAIN = "sc.category.exchange.direct.response.main";
    String SC_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_RESPONSE_RETRY = "sc.category.exchange.direct.response.retry";
    String SC_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_RESPONSE_PARKING = "sc.category.exchange.direct.response.parking";

}
