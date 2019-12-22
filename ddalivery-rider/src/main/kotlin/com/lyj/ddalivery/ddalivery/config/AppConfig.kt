package com.lyj.ddalivery.ddalivery.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.format.support.FormattingConversionService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.time.format.DateTimeFormatter

@Configuration
@EnableJpaAuditing
class AppConfig {

    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun conversionService(): FormattingConversionService {
        val conversionService = DefaultFormattingConversionService(false)
        val registrar = DateTimeFormatterRegistrar()
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"))
        registrar.registerFormatters(conversionService)
        // other desired formatters
        return conversionService
    }

    @Bean
    fun webClient(): WebClient {
        val timeoutClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000) //Connect Timeout
                .option(ChannelOption.SO_TIMEOUT, 1000) // Socket Timeout
                .doOnConnected { c: Connection ->
                    c.addHandlerLast(ReadTimeoutHandler(3))
                            .addHandlerLast(WriteTimeoutHandler(3))
                }
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .clientConnector(ReactorClientHttpConnector(HttpClient.from(timeoutClient)))
                .build()
    }

//    @Bean
//    fun jwtUtils(jwtSettings: JwtSettings?): JwtUtils {
//        return JwtUtils(jwtSettings)
//    }
}