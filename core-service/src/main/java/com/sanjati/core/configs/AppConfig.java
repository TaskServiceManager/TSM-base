package com.sanjati.core.configs;


import com.sanjati.core.properties.AuthServiceIntegrationProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(
        AuthServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class AppConfig {
    private final AuthServiceIntegrationProperties authServiceIntegrationProperties;

    @Bean
    @LoadBalanced
    public WebClient.Builder authWebClient() {
//        TcpClient tcpClient = TcpClient
//                .create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, authServiceIntegrationProperties.getConnectTimeout())
//                .doOnConnected(connection -> {
//                    connection.addHandlerLast(new ReadTimeoutHandler(authServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
//                    connection.addHandlerLast(new WriteTimeoutHandler(authServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
//                });

        return WebClient
                .builder()
                .baseUrl(authServiceIntegrationProperties.getUrl());
//                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))

    }
}
