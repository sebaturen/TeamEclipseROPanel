package com.eclipse.panel.viewController.roRender;

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

public class ROFrame {

    private byte[] frame;
    private short sizeX;
    private short sizeY;
    private int position;
    private byte[] bSendPalette;
    private byte[] bAct;

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
            bAct = urlToByte(act);
        }
    }

    public static final int VERSION_START = 2;
    public static final int N_ANIMATIONS_START = 4;
    public int[] getOffSet() {

        short version = bAct[VERSION_START];
        byte[] bNAnimations = reverseContent(Arrays.copyOfRange(bAct, N_ANIMATIONS_START, N_ANIMATIONS_START+2));
        short nAnimations = (ByteBuffer.wrap(bNAnimations)).getShort();
        int readBytePost = N_ANIMATIONS_START+2+10;
        while (nAnimations > 1) {
            byte[] bNFrames = reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+4));
            int nFrames = (ByteBuffer.wrap(bNFrames)).getInt();
            readBytePost += 4;
            while (nFrames > 1) {
                readBytePost += 32;

                byte[] bNSubFrames = reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+4));
                int nSubFrames = (ByteBuffer.wrap(bNSubFrames)).getInt();
                readBytePost += 4;

                while (nSubFrames >= 1) {
                    byte[] bOffsetX = reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+4));
                    byte[] bOffsetY = reverseContent(Arrays.copyOfRange(bAct, readBytePost+4, readBytePost+8));
                    int offsetX = (ByteBuffer.wrap(bOffsetX)).getInt();
                    int offsetY = (ByteBuffer.wrap(bOffsetY)).getInt();

                    return new int[] {offsetX, offsetY};
                    /** TO DO = http://mist.in/gratia/ro/spr/ActFileFormatFix.html
                     * WE NO COMPLETE BECOUSE ONLY USE THE FRONT AND STAND POSITION IN FIRST MVP
                     *                     readBytePost += 4 + 4 + 4 + 4 + 4; // offset x, offset y, image, direction, color
                     *                     if (version >= 2) readBytePost += 4 + 4 + 4; // scale x, rotation, dontjumps
                     *                     if (version >= 4) readBytePost += 4;
                     *
                     *                     System.out.println("offset"+convertBytesToHex(Arrays.copyOfRange(bAct, readBytePost, readBytePost+8)));
                     *
                     *                     System.out.println(Arrays.toString(bOffsetX));
                     *                     System.out.println(Arrays.toString(bOffsetY));
                     *
                     *                     System.out.println(offsetX +","+ offsetY);
                     *                     System.exit(-1);
                     *                     readBytePost += 4 + 4;
                     *
                     *                     nSubFrames--;
                     */
                }
                nFrames--;
            }
            nAnimations--;
        }
        return null;
    }

    // JOB SPRITE INFO
    private static final int JOB_HEADER_START =  0;
    private static final int JOB_TOTAL_FRAME_START = 4;

    public static ROFrame processSPR(URL sprite, URL palette, int spritePosition) throws IOException {

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
            framesList.get(spritePosition).setPalette(sendPalette);
            framesList.get(spritePosition).setPosition(spritePosition);
            return framesList.get(spritePosition);
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
