package com.sai.config

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("nats")
class NatsConfiguration {
    var url: String? = null
    var maxReconnects: Int? = null
    var connectionTimeoutSeconds: Long? = null
}