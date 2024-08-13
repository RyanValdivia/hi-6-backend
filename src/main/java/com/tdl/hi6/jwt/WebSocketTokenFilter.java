package com.tdl.hi6.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketTokenFilter implements ChannelInterceptor {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    public Message<?> preSend (Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                String username = jwtService.getUsernameFromToken(token);
                if (username != null && jwtService.isTokenValid
                        (token, userDetailsService.loadUserByUsername(username))) {
                    return message;
                }
            }
        }
        throw new RuntimeException("Token not valid");
    }
}
