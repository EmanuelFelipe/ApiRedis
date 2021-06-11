package com.iesbpi.redis.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.web.servlet.function.RequestPredicates.GET
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions
import org.springframework.web.servlet.function.ServerResponse


@Configuration
class RedisConfig: CachingConfigurerSupport() {

    val mapper = ObjectMapper()

    companion object{
        private val log = LoggerFactory.getLogger(javaClass)
    }

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration("redis-10955.c15.us-east-1-2.ec2.cloud.redislabs.com", 10955)
        redisStandaloneConfiguration.password = RedisPassword.of("A0VSKH81kLXcnwKXHezwN3fVRFA4d3Dz")
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {

        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(redisConnectionFactory())
            keySerializer = RedisSerializer.string()
        }
    }




    @Bean
    fun route(api: ApiHandle): RouterFunction<ServerResponse> {
        return RouterFunctions.route(GET("/ping"), api::ping)
    }
}