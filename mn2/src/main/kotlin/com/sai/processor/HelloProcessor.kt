package com.sai.processor

import com.sai.model.AbstractProcessor
import com.sai.model.Hello
import javax.inject.Singleton

@Singleton
open class HelloProcessor : AbstractProcessor<Hello>() {
    override fun process(event: Hello) {
        println("${Thread.currentThread().name} ****>>>> $event")
        Thread.sleep(5000)
    }
}