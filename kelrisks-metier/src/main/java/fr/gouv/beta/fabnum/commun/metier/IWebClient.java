package fr.gouv.beta.fabnum.commun.metier;

import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

public interface IWebClient {
    
    default WebClient getWebClient() {
        
        TcpClient tcpClient = TcpClient.newConnection();
        
        return org.springframework.web.reactive.function.client.WebClient.builder()
                       .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                       .build();
    }
}
