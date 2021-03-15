package com.smartchoice.common.model;

import com.smartchoice.common.model.rabbitmq.ExchangeName;
import com.smartchoice.common.model.rabbitmq.QueueName;

public enum Supplier {
    TIKI(true, QueueName.TIKI_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN,
            ExchangeName.TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_MAIN,
            QueueName.TIKI_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN,
            ExchangeName.TIKI_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_MAIN),
    SENDO(true, QueueName.SENDO_RABBITMQ_QUEUE_NAME_CATEGORY_REQUEST_MAIN,
            ExchangeName.SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_CATEGORY_REQUEST_MAIN,
            QueueName.SENDO_RABBITMQ_QUEUE_NAME_PRODUCT_REQUEST_MAIN,
            ExchangeName.SENDO_RABBITMQ_DIRECT_EXCHANGE_NAME_PRODUCT_REQUEST_MAIN),
    SELF(false);

    private boolean isExternal;

    private String categoryRequestQueue;
    private String categoryRequestExchange;

    private String productRequestQueue;
    private String productRequestExchange;

    Supplier(boolean isExternal) {
        this.isExternal = isExternal;
    }

    Supplier(boolean isExternal, String categoryRequestQueue, String categoryRequestExchange, String productRequestQueue, String productRequestExchange) {
        this.isExternal = isExternal;
        this.categoryRequestQueue = categoryRequestQueue;
        this.categoryRequestExchange = categoryRequestExchange;
        this.productRequestQueue = productRequestQueue;
        this.productRequestExchange = productRequestExchange;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public String getCategoryRequestQueue() {
        return categoryRequestQueue;
    }

    public String getCategoryRequestExchange() {
        return categoryRequestExchange;
    }

    public String getProductRequestQueue() {
        return productRequestQueue;
    }

    public String getProductRequestExchange() {
        return productRequestExchange;
    }
}
