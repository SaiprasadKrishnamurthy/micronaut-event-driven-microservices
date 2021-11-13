package org.sai.tools.grouping.config

import org.sai.tools.grouping.repository.MemberRepository
import org.sai.tools.grouping.service.GroupingService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class Bootstrap {
//    @Bean
    fun start(memberRepository: MemberRepository, service: GroupingService) = CommandLineRunner {
        val inputs = listOf(
            "A,B,C,D",
            "A,B,X,D",
            "A,B,P,J",
            "A,B,P,J",
            "A,B,P,J,M,Y",
            "A,B,P,J,M,Y",
        )

//        val groups = service.groups(inputs)
//        groups.forEach { g ->
//            println(g)
//        }
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedHeaders = listOf("*")
        config.allowedMethods = listOf("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
        config.allowedOrigins = listOf("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}