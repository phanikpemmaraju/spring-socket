package br.com.boavista.config;

import br.com.boavista.CustomSerializerDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

@Slf4j
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class SocketConfig {

//    @MessagingGateway(defaultRequestChannel="toTcp")
//    public interface Gateway {
//        String viaTcp(String in);
//    }

    @Bean
    @ServiceActivator(inputChannel="toTcp")
    public TcpOutboundGateway tcpOutGate(AbstractClientConnectionFactory connectionFactory) {
        TcpOutboundGateway gate = new TcpOutboundGateway();
        gate.setConnectionFactory(connectionFactory);
        gate.setOutputChannelName("toTcp");
        return gate;
    }

    @Bean
    public TcpInboundGateway tcpInGate(AbstractServerConnectionFactory connectionFactory)  {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(connectionFactory);
        inGate.setRequestChannel(fromTcp());
        return inGate;
    }

    @Bean
    public MessageChannel fromTcp() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel toTcp() {
        return new DirectChannel();
    }

    // Outgoing requests
    @Bean
    public AbstractClientConnectionFactory clientCF() {
        log.info("Client Connection Factory");
        TcpNetClientConnectionFactory tcpNetClientConnectionFactory = new TcpNetClientConnectionFactory("localhost", serverCF().getPort());
        tcpNetClientConnectionFactory.setSingleUse(true);
        return tcpNetClientConnectionFactory;
    }

    // Incoming requests
    @Bean
    public AbstractServerConnectionFactory serverCF() {
        log.info("Server Connection Factory");
        TcpNetServerConnectionFactory tcpNetServerConnectionFactory = new TcpNetServerConnectionFactory(8000);
        tcpNetServerConnectionFactory.setSerializer(new CustomSerializerDeserializer());
        tcpNetServerConnectionFactory.setDeserializer(new CustomSerializerDeserializer());
        return tcpNetServerConnectionFactory;
    }

}
