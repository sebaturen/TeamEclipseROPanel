package com.eclipse.panel.viewController.roRender;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ROFrame {

    private byte[] frame;
    private short sizeX;
    private short sizeY;

    public ROFrame(short sizeX, short sizeY, byte[] frame) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.frame = frame;
    }

    private void uncompress() {
        List<Byte> buff = new ArrayList<>();
        Byte prev = null;
        for(byte b : frame) {
            if (prev != null && prev == (byte) 0) {
                if (b == 0) {
                    buff.add(b);
                } else {
                    byte compressByte = b;
                    while (compressByte-- > 1) {
                        buff.add((byte) 0);
                    }
                }
            } else {
                buff.add(b);
            }
            prev = b;
        }

        frame = listToPrimitive(buff);
    }

    private byte[] listToPrimitive(List<Byte> buffer) {
        byte[] bMapName = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            bMapName[i] = buffer.get(i);
        }
        return bMapName;
    }

    public void getPng(byte[] palette) {
        uncompress();
        // apply palette
        
    }

}
