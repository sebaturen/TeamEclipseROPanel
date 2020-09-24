package com.eclipse.panel.viewController.roRender.roSpr;

import java.util.ArrayList;
import java.util.List;

public class ROImg {

    private byte[] frame;
    private short sizeX;
    private short sizeY;

    public ROImg() {

    }

    private byte[] uncompress() {
        List<Byte> data = new ArrayList<>();

        for (int i = 0; i < frame.length; i++) {
            if (frame[i] == 0) {
                int  cantBlanc = frame[i+1] & 0xFF;
                do {
                    data.add((byte) 0);
                } while (cantBlanc-- > 1);
                i += 1;
            } else {
                data.add(frame[i]);
            }
        }

        return listToPrimitive(data);
    }

    private byte[] listToPrimitive(List<Byte> buffer) {
        byte[] bMapName = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            bMapName[i] = buffer.get(i);
        }
        return bMapName;
    }

    public byte[] getFrame() {
        return uncompress();
    }

    public void setFrame(byte[] frame) {
        this.frame = frame;
    }

    public short getSizeX() {
        return sizeX;
    }

    public void setSizeX(short sizeX) {
        this.sizeX = sizeX;
    }

    public short getSizeY() {
        return sizeY;
    }

    public void setSizeY(short sizeY) {
        this.sizeY = sizeY;
    }

    public int getCenterX() {
        return sizeX/2;
    }

    public int getCenterY() {
        return sizeY/2;
    }

}
