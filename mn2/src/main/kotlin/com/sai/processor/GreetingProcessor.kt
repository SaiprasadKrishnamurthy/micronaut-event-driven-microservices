package com.sai.processor

import com.sai.model.AbstractProcessor
import com.sai.model.Greeting
import javax.inject.Singleton

@Singleton
open class GreetingProcessor : AbstractProcessor<Greeting>() {
    override fun process(event: Greeting) {
        println("${Thread.currentThread().name} ****>>>> $event")
        Thread.sleep(5000)
    }
}