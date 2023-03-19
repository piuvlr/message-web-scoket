package br.com.message.handler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	
	//Método chamado toda vez que é criado uma conexão webSocket
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished - Session ID: " + session.getId());
		
		/*
		 * Apenas para gins de teste
		 * Função de timer para cliet receber mensagens
		 *  */
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					session.sendMessage(new TextMessage("Olá " + UUID.randomUUID()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 2000, 200);
	}
	
	//Método chamado toda vez que a gente recebe uma mensagem
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage - message: " + message.getPayload());
	}
	
	//Método chamado toda vez que a conexão encerrada
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed - STATUS: " + status.getCode() + " SESSION: " + session.getId());
	}
}
