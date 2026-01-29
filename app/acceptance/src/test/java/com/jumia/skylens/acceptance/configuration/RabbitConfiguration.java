package com.jumia.skylens.acceptance.configuration;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${RABBIT_HOST:localhost}")
    private String host;

    @Value("${RABBIT_PORT:5672}")
    private int port;

    @Value("${RABBIT_USERNAME:guest}")
    private String username;

    @Value("${RABBIT_PASSWORD:guest}")
    private String password;

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {

        final CachingConnectionFactory factory = new CachingConnectionFactory(host, port);

        factory.setUsername(username);
        factory.setPassword(password);

        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        return new RabbitTemplate(connectionFactory);
    }
}
