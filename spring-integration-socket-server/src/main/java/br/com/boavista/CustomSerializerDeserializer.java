package br.com.boavista;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class CustomSerializerDeserializer implements Serializer, Deserializer {

    @Override
    public Object deserialize(InputStream inputStream) throws IOException {
        log.info("Deserialize the object");
        final int BUFFER_SIZE = 32000;
        final int ETX = 0x03;

        final StringBuffer buffer = new StringBuffer();
        int readLength;
        byte[] bytes;
        bytes = new byte[BUFFER_SIZE];
        do {
            readLength = inputStream.read(bytes);
            if(readLength > 0){
                buffer.append(new String(bytes),0,readLength);
            }
        } while (bytes[readLength-1] != ETX);
        final StringBuffer stringBuffer = buffer.deleteCharAt(buffer.length() - 1);
        String toString = stringBuffer.toString();
        final String SOCKETY = "SOCKETY             ";
        log.info("String : {} ", toString.substring(20));
        final SocketData customObject = SocketData.builder().data(toString.substring(20)).build();
        log.info("Custom object : {} ", customObject.getData());
        return customObject;
    }

    @Override
    public void serialize(Object object, OutputStream outputStream) throws IOException {
        log.info("Serialize the object");
        outputStream.write(((SocketData) object).getData().getBytes());
        outputStream.write(0x03);
        outputStream.flush();
    }
}
