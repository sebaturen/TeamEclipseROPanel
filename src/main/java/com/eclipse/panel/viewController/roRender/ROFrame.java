package com.eclipse.panel.viewController.roRender;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

    public BufferedImage getPng(byte[] palette) {
        uncompress();
        // Prepare palette
        List<byte[]> rgbPalette = new ArrayList<>();
        for(int i = 0; i < palette.length; i+=4) {
            byte[] rgbIndex = Arrays.copyOfRange(palette, i, i+4);
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

    // JOB SPRITE INFO
    private static final int JOB_HEADER_START =  0;
    private static final int JOB_TOTAL_FRAME_START = 4;

    public static BufferedImage processSPR(URL sprite, URL palette, int spritePosition) throws IOException {

        byte[] spriteSPR;
        byte[] orgPalette = null;
        byte[] altPalette = null;
        List<ROFrame> framesList = new ArrayList<>();
        spriteSPR = urlToByte(sprite);
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
                byte[] bByteSize = reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition+4, bytePosition+6));

                short sizeX = (ByteBuffer.wrap(bSizeX)).getShort();
                short sizeY = (ByteBuffer.wrap(bSizeY)).getShort();
                short byteSize = (ByteBuffer.wrap(bByteSize)).getShort();
                framesList.add(new ROFrame(sizeX, sizeY, Arrays.copyOfRange(spriteSPR, bytePosition+6, bytePosition+6+byteSize)));

                bytePosition += 6 + byteSize;
                totalFrames--;
            }
            orgPalette = Arrays.copyOfRange(spriteSPR, bytePosition, spriteSPR.length);
        }

        if (framesList.size() >= spritePosition) {
            byte[] sendPalette;
            if (palette != null) {
                sendPalette = altPalette;
            } else {
                sendPalette = orgPalette;
            }
            return framesList.get(spritePosition).getPng(sendPalette);
        }

        return null;

    }

    private static byte[] reverseContent(byte[] content) {
        for(int i = 0; i < content.length / 2; i++) {
            byte t = content[i];
            content[i] = content[content.length -1 -i];
            content[content.length -1 -i] = t;
        }
        return content;
    }

    private static byte[] urlToByte(URL elem) throws IOException {
        byte[] outByte;
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
