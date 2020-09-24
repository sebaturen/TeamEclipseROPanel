package com.eclipse.panel.viewController.roRender;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public interface ROActSpr {

    static byte[] reverseContent(byte[] content) {
        for(int i = 0; i < content.length / 2; i++) {
            byte t = content[i];
            content[i] = content[content.length -1 -i];
            content[content.length -1 -i] = t;
        }
        return content;
    }

    static byte[] urlToByte(URL elem) throws IOException {
        byte[] outByte;
        if (elem == null) {
            throw new IOException("url null");
        }
        try (InputStream fis = elem.openStream();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] buff = new byte[8000];
            int bytesRead = 0;

            while((bytesRead = fis.read(buff)) != -1) {
                buffer.write(buff, 0, bytesRead);
            }

            outByte = buffer.toByteArray();
        }

        return outByte;
    }

}
