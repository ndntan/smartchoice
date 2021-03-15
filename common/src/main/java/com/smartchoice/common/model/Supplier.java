package com.smartchoice.common.model;

import com.smartchoice.common.model.rabbitmq.ExchangeName;
import com.smartchoice.common.model.rabbitmq.QueueName;

public enum Supplier {
    TIKI(true, QueueName.TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN,
            ExchangeName.TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_MAIN,
            QueueName.TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN,
            ExchangeName.TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_MAIN,
            QueueName.TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_RETRY,
            ExchangeName.TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_RETRY,
            QueueName.TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_RETRY,
            ExchangeName.TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_RETRY),
    SENDO(true, QueueName.SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN,
            ExchangeName.SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_MAIN,
            QueueName.SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN,
            ExchangeName.SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_MAIN,
            QueueName.SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_RETRY,
            ExchangeName.SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_RETRY,
            QueueName.SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_RETRY,
            ExchangeName.SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_RETRY),
    SELF(false);

    private boolean isExternal;

    private String categoryRequestMainQueue;
    private String categoryRequestMainExchange;

    private String productRequestMainQueue;
    private String productRequestMainExchange;

    private String categoryRequestRetryQueue;
    private String categoryRequestRetryExchange;

    private String productRequestRetryQueue;
    private String productRequestRetryExchange;

    Supplier(boolean isExternal) {
        this.isExternal = isExternal;
    }

    Supplier(boolean isExternal, String categoryRequestMainQueue, String categoryRequestMainExchange,
             String productRequestMainQueue, String productRequestMainExchange,
             String categoryRequestRetryQueue, String categoryRequestRetryExchange, String productRequestRetryQueue,
             String productRequestRetryExchange) {
        this.isExternal = isExternal;
        this.categoryRequestMainQueue = categoryRequestMainQueue;
        this.categoryRequestMainExchange = categoryRequestMainExchange;
        this.productRequestMainQueue = productRequestMainQueue;
        this.productRequestMainExchange = productRequestMainExchange;
        this.categoryRequestRetryQueue = categoryRequestRetryQueue;
        this.categoryRequestRetryExchange = categoryRequestRetryExchange;
        this.productRequestRetryQueue = productRequestRetryQueue;
        this.productRequestRetryExchange = productRequestRetryExchange;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public String getCategoryRequestMainQueue() {
        return categoryRequestMainQueue;
    }

    public String getCategoryRequestMainExchange() {
        return categoryRequestMainExchange;
    }

    public String getProductRequestMainQueue() {
        return productRequestMainQueue;
    }

    public String getProductRequestMainExchange() {
        return productRequestMainExchange;
    }

    public String getCategoryRequestRetryQueue() {
        return categoryRequestRetryQueue;
    }

    public String getCategoryRequestRetryExchange() {
        return categoryRequestRetryExchange;
    }

    public String getProductRequestRetryQueue() {
        return productRequestRetryQueue;
    }

    public String getProductRequestRetryExchange() {
        return productRequestRetryExchange;
    }
}
