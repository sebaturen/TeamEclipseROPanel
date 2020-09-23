package com.eclipse.panel.viewController.roRender;

import com.eclipse.panel.viewController.roRender.roAct.ROAct;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ROSprite {

    private byte[] frame;
    private short sizeX;
    private short sizeY;
    private int position;
    private int[] offSet;
    private byte[] bSendPalette;
    private ROAct roAct;

    public ROSprite(short sizeX, short sizeY, byte[] frame) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.frame = frame;
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

    public BufferedImage getPng() {
        uncompress();
        // Prepare palette
        List<byte[]> rgbPalette = new ArrayList<>();
        for(int i = 0; i < bSendPalette.length; i+=4) {
            byte[] rgbIndex = Arrays.copyOfRange(bSendPalette, i, i+4);
            rgbPalette.add(rgbIndex);
        }
        // Prepare image
        BufferedImage dest = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        int x,y = x = 0;
        for(int i = 0; i < frame.length; i++) {
            if (i > 0 && i%sizeX == 0) {
                x = 0;
                y++;
            }
            int paletteColorPos = (int) frame[i] & 0xff;
            byte[] rgb = rgbPalette.get(paletteColorPos);
            Color c = new Color((int) rgb[0] & 0xff, (int) rgb[1] & 0xff, (int) rgb[2] & 0xff, (paletteColorPos == 0)? 0:255);
            dest.setRGB(x, y, c.getRGB());
            x++;
        }

        return dest;
        
    }

    public void setPalette(byte[] bPalette) {
        bSendPalette = bPalette;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAct(URL act) throws IOException {
        if (act != null) {
            roAct = new ROAct.Builder(urlToByte(act)).build();
        }
    }

    public short getSizeX() {
        return sizeX;
    }

    public short getSizeY() {
        return sizeY;
    }

    public int[] getOffSet(int animationId, int frameId) {

        return roAct.getOffSet(animationId, frameId);

    }

    // JOB SPRITE INFO
    private static final int JOB_HEADER_START =  0;
    private static final int JOB_TOTAL_FRAME_START = 4;

    public static ROSprite processSPR(URL sprite, URL palette, int spritePosition) throws Exception {

        byte[] spriteSPR = null;
        byte[] orgPalette = null;
        byte[] altPalette = null;
        List<ROSprite> framesList = new ArrayList<>();
        if (sprite != null) {
            spriteSPR = urlToByte(sprite);
        }
        if (palette != null) {
            altPalette = urlToByte(palette);
        }

        if (spriteSPR != null) {
            byte[] bTotalFrames = reverseContent(Arrays.copyOfRange(spriteSPR, JOB_TOTAL_FRAME_START, JOB_TOTAL_FRAME_START+4));
            int totalFrames = (ByteBuffer.wrap(bTotalFrames)).getInt();
            int bytePosition = JOB_TOTAL_FRAME_START+4;
            while (totalFrames != 0) {

                byte[] bSizeX = reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+2));
                byte[] bSizeY = reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition+2, bytePosition+4));
                byte[] bByteSize = Arrays.copyOfRange(spriteSPR, bytePosition+4, bytePosition+6);

                short sizeX = (ByteBuffer.wrap(bSizeX)).getShort();
                short sizeY = (ByteBuffer.wrap(bSizeY)).getShort();
                ByteBuffer bb = ByteBuffer.allocate(4);
                bb.put((byte) 0);
                bb.put((byte) 0);
                bb.put(bByteSize[1]);
                bb.put(bByteSize[0]);
                bb.rewind();
                int byteSize = bb.getInt();
                framesList.add(new ROSprite(sizeX, sizeY, Arrays.copyOfRange(spriteSPR, bytePosition+6, bytePosition+6+byteSize)));

                bytePosition += 6 + byteSize;
                totalFrames--;
            }
            orgPalette = Arrays.copyOfRange(spriteSPR, bytePosition, spriteSPR.length);
        }

        if (sprite != null && framesList.size() >= spritePosition) {
            byte[] sendPalette = null;
            if (palette != null) {
                if (altPalette != null) {
                    sendPalette = altPalette;
                }
            } else {
                sendPalette = orgPalette;
            }
            if (sendPalette != null) {
                framesList.get(spritePosition).setPalette(sendPalette);
            }
            framesList.get(spritePosition).setPosition(spritePosition);
            return framesList.get(spritePosition);
        }

        return null;

    }

    public static byte[] reverseContent(byte[] content) {
        for(int i = 0; i < content.length / 2; i++) {
            byte t = content[i];
            content[i] = content[content.length -1 -i];
            content[content.length -1 -i] = t;
        }
        return content;
    }

    private static byte[] urlToByte(URL elem) throws IOException {
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
