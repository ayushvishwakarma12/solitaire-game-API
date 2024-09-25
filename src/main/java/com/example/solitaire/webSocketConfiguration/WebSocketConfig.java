//package com.example.solitaire.webSocketConfiguration;
//
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
//    @Override
//    public void registerStompEndPoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/game-websocket").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//}
