package com.sai.model

import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.scheduling.annotation.Async

abstract class AbstractProcessor<T> : ApplicationEventListener<T> where T : BaseModel {
    abstract fun process(event: T)

    @Async
    override fun onApplicationEvent(event: T) {
        process(event)
    }
}