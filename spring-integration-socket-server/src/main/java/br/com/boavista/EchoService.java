package br.com.boavista;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@MessageEndpoint
public class EchoService {

//    @Transformer(inputChannel="fromTcp", outputChannel="toEcho")
//    public String convert(String string) {
//        log.info("Echo convert {} ", string);
//        return string;
//    }
//
//    @ServiceActivator(inputChannel="toEcho")
//    public String upCase(String in) {
//        log.info("Echo upCase {} ", in);
//        return in.toUpperCase();
//    }

    // Calls this method first
    @Transformer(inputChannel="fromTcp", outputChannel= "toEcho")
    public SocketData convert(SocketData object) throws InterruptedException {
        log.info("Echo convert: {} ", object);
        return object;
    }

//    @ServiceActivator(inputChannel= "toEcho")
//    public String upCase(byte[] bytes) {
//        log.info("Echo upCase {} ", new String(bytes));
//        byte[] responseBytes = ("Response sent to server " + (byte) 0x03).getBytes();
//        log.info("Echo upCase {} ", new String(responseBytes));
//        return new String(responseBytes);
//    }

    @ServiceActivator(inputChannel= "toEcho")
    public SocketData upCase(SocketData object) throws IOException {
        log.info("Echo upCase before: {} ", object);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(object.getData().getBytes());
        output.write((byte) 0x03);
        byte[] out = output.toByteArray();
        log.info("Echo upCase after: {} ", new String(out));
        return object;
    }
}
