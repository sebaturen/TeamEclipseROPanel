package com.eclipse.panel.viewController.roRender.roSpr;

import com.eclipse.panel.viewController.roRender.ROActSpr;
import com.eclipse.panel.viewController.roRender.ROSprite;
import com.eclipse.panel.viewController.roRender.roAct.ROAct;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ROSpr {

    // JOB SPRITE INFO
    private static final int JOB_HEADER_START =  0;
    private static final int JOB_TOTAL_FRAME_START = 4;

    // Attribute
    private final List<ROImg> framesV1 = new ArrayList<>();
    private final List<ROImg> framesV2 = new ArrayList<>();
    private int version;
    private byte[] palette;

    public static class Builder {

        private URL sprite;

        public Builder(@NotNull URL sprite) {
            this.sprite = sprite;
        }

        public ROSpr build() throws Exception {
            ROSpr roSpr = new ROSpr();

            byte[] spriteSPR = ROActSpr.urlToByte(sprite);
            byte[] bVersion = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, JOB_TOTAL_FRAME_START, JOB_TOTAL_FRAME_START+4));
            roSpr.version = (ByteBuffer.wrap(bVersion)).getInt();

            if (roSpr.version > 300) {
                unPackV3(spriteSPR, roSpr);
            } else if (roSpr.version > 0) {
                unPackV1(spriteSPR, roSpr);
            } else {
                unPackV2(spriteSPR, roSpr);
            }

            return roSpr;
        }

        /**
         * U can see the documentation in:
         *  http://mist.in/gratia/ro/spr/SprFileFormat.html
         * The info is divide in frames and that it is compress
         * @param spriteSPR
         * @param roSpr
         */
        private void unPackV1(byte[] spriteSPR, ROSpr roSpr) {
            int bytePosition = JOB_TOTAL_FRAME_START;
            byte[] bTotalFrames = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=4));
            int totalFrames = (ByteBuffer.wrap(bTotalFrames)).getInt();
            unPackV1(roSpr, totalFrames, spriteSPR, bytePosition);
        }

        private int unPackV1(ROSpr roSpr, int totalFrames, byte[] spriteSPR, int bytePosition) {
            while (totalFrames-- >= 1) {

                byte[] bSizeX = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=2));
                byte[] bSizeY = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=2));
                byte[] bByteSize = Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=2);

                short sizeX = (ByteBuffer.wrap(bSizeX)).getShort();
                short sizeY = (ByteBuffer.wrap(bSizeY)).getShort();
                ByteBuffer bb = ByteBuffer.allocate(4);
                bb.put((byte) 0);
                bb.put((byte) 0);
                bb.put(bByteSize[1]);
                bb.put(bByteSize[0]);
                bb.rewind();
                int byteSize = bb.getInt();

                ROImg roImg = new ROImg();
                roImg.setSizeX(sizeX);
                roImg.setSizeY(sizeY);
                roImg.setCompress(true);
                roImg.setVersion(1);
                roImg.setFrame(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=byteSize));
                roSpr.framesV1.add(roImg);
            }

            roSpr.palette = Arrays.copyOfRange(spriteSPR, spriteSPR.length-1024, spriteSPR.length);

            return bytePosition;
        }

        /**
         * All frame have a full info, the structure is:
         *      [total frame]:
         *          [size x] [size y] {
         *              [RGBA] x sprite
         *          }
         * @param spriteSPR
         * @param roSpr
         */
        private void unPackV2(byte[] spriteSPR, ROSpr roSpr) {
            int bytePosition = JOB_TOTAL_FRAME_START+2;
            byte[] bTotalFrames = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=2));
            short totalFrames = (ByteBuffer.wrap(bTotalFrames)).getShort();
            unPackV2(roSpr, totalFrames, spriteSPR, bytePosition);
        }

        private int unPackV2(ROSpr roSpr, int totalFrames, byte[] spriteSPR, int bytePosition) {
            while (totalFrames-- >= 1) {

                byte[] bSizeX = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=2));
                byte[] bSizeY = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=2));

                short sizeX = (ByteBuffer.wrap(bSizeX)).getShort();
                short sizeY = (ByteBuffer.wrap(bSizeY)).getShort();
                int byteSize = sizeX * sizeY * 4;

                ROImg roImg = new ROImg();
                roImg.setSizeX(sizeX);
                roImg.setSizeY(sizeY);
                roImg.setVersion(2);
                roImg.setFrame(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=byteSize));
                roSpr.framesV2.add(roImg);
            }

            return bytePosition;
        }

        /**
         * V3 have the last 2 type in one,
         *      The First is N frames in v1 format
         *      The second group is N frame in v2 format
         * @param spriteSPR
         * @param roSpr
         */
        private void unPackV3(byte[] spriteSPR, ROSpr roSpr) {
            int bytePosition = JOB_TOTAL_FRAME_START;
            byte[] bTotalFrames1 = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=2));
            byte[] bTotalFrames2 = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+=2));
            short totalFrames1 = (ByteBuffer.wrap(bTotalFrames1)).getShort();
            short totalFrames2 = (ByteBuffer.wrap(bTotalFrames2)).getShort();

            // Unpack v1 frames:
            bytePosition = unPackV1(roSpr, totalFrames1, spriteSPR, bytePosition);

            // Unpack v2 frames:
            unPackV2(roSpr, totalFrames2, spriteSPR, bytePosition);
        }


    }

    private ROSpr() {

    }

    public void setPalette(@NotNull URL palette) throws IOException {
        this.palette = ROActSpr.urlToByte(palette);
    }

    public BufferedImage getPng(ROImg.ROImageVersion version, int framePost, boolean mirror) {

        ROImg roImg;
        if (version == ROImg.ROImageVersion.VERSION_1) {
            roImg = framesV1.get(framePost);
        } else {
            roImg = framesV2.get(framePost);
        }

        return switch (roImg.getVersion()) {
            case 1 -> getPngV1(roImg, mirror);
            case 2 -> getPngV2(roImg, mirror);
            default -> null;
        };
    }

    private BufferedImage getPngV1(ROImg roImg, boolean mirror) {
        byte[] frame = roImg.getFrame();
        // Prepare palette
        List<byte[]> rgbPalette = new ArrayList<>();
        for(int i = 0; i < palette.length; i+=4) {
            byte[] rgbIndex = Arrays.copyOfRange(palette, i, i+4);
            rgbPalette.add(rgbIndex);
        }

        // Prepare image
        BufferedImage dest = new BufferedImage(roImg.getSizeX(), roImg.getSizeY(), BufferedImage.TYPE_INT_ARGB);
        int x, y = x = 0;
        if (mirror) x = roImg.getSizeX()-1;
        for(int i = 0; i < frame.length; i++) {
            if (i > 0 && i%roImg.getSizeX() == 0) {
                if (mirror) x = roImg.getSizeX()-1; else x = 0;
                y++;
            }

            int paletteColorPos = (int) frame[i] & 0xff;
            byte[] rgb = rgbPalette.get(paletteColorPos);
            Color c = new Color((int) rgb[0] & 0xff, (int) rgb[1] & 0xff, (int) rgb[2] & 0xff, (paletteColorPos == 0)? 0:255);
            dest.setRGB(x, y, c.getRGB());
            if (mirror) x--; else x++;
        }

        return dest;
    }

    private BufferedImage getPngV2(ROImg roImg, boolean mirror) {
        byte[] frame = roImg.getFrame();
        // Prepare image
        BufferedImage dest = new BufferedImage(roImg.getSizeX(), roImg.getSizeY(), BufferedImage.TYPE_INT_ARGB);
        int x,y = x = 0;
        if (mirror) x = roImg.getSizeX()-1;
        for(int i = frame.length-1; i > 0; i-=4) {
            if (x == roImg.getSizeX()) {
                if (mirror) x = roImg.getSizeX()-1; else x = 0;
                y++;
            }
            Color c = new Color((int) frame[i] & 0xff, (int) frame[i-1] & 0xff, (int) frame[i-2] & 0xff, (int) frame[i-3] & 0xff);
            dest.setRGB(x, y, c.getRGB());
            if (mirror) x--; else x++;
        }

        return dest;
    }

    public ROImg getROImg(ROImg.ROImageVersion version, int frame) {
        if (version == ROImg.ROImageVersion.VERSION_1) {
            return framesV1.get(frame);
        } else {
            return framesV2.get(frame);
        }
    }

    @Override
    public String toString() {
        return "{\"_class\":\"ROSpr\", " +
                "\"version\":\"" + version + "\"" + ", " +
                "}";
    }
}
