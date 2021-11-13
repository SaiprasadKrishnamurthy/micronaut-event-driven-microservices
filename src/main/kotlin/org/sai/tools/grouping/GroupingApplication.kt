package org.sai.tools.grouping

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class GroupingApplication

fun main(args: Array<String>) {
    runApplication<GroupingApplication>(*args)
}
