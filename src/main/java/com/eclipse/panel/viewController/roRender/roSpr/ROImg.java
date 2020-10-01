package com.eclipse.panel.viewController.roRender.roSpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ROImg {

    public enum ROImageVersion {
        VERSION_1,  // Compress
        VERSION_2   // RGB-A hex code
    }

    private byte[] frame;
    private short sizeX;
    private short sizeY;
    private int version;
    private boolean isCompress = false;

    public ROImg() {

    }

    private void uncompress() {
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

        frame = listToPrimitive(data);
    }

    private byte[] listToPrimitive(List<Byte> buffer) {
        byte[] bMapName = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            bMapName[i] = buffer.get(i);
        }
        return bMapName;
    }

    public byte[] getFrame() {
        if (isCompress) {
            uncompress();
            isCompress = false;
        }
        return frame;
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

    public void setCompress(boolean compress) {
        isCompress = compress;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"ROImg\", " +
                "\"frame\":" + Arrays.toString(frame) + ", " +
                "\"sizeX\":\"" + sizeX + "\"" + ", " +
                "\"sizeY\":\"" + sizeY + "\"" + ", " +
                "\"isCompress\":\"" + isCompress + "\"" +
                "}";
    }
}
