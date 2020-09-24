package com.eclipse.panel.viewController.roRender.roSpr;

import com.eclipse.panel.viewController.roRender.ROActSpr;
import com.eclipse.panel.viewController.roRender.ROSprite;
import com.eclipse.panel.viewController.roRender.roAct.ROAct;

import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    private final List<ROImg> frames = new ArrayList<>();
    private byte[] palette;

    public static class Builder {

        private URL sprite;

        public Builder(@NotNull URL sprite) {
            this.sprite = sprite;
        }

        public ROSpr build() throws Exception {
            ROSpr roSpr = new ROSpr();

            byte[] spriteSPR = ROActSpr.urlToByte(sprite);
            byte[] bTotalFrames = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, JOB_TOTAL_FRAME_START, JOB_TOTAL_FRAME_START+4));
            int totalFrames = (ByteBuffer.wrap(bTotalFrames)).getInt();
            int bytePosition = JOB_TOTAL_FRAME_START+4;
            while (totalFrames != 0) {

                byte[] bSizeX = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition, bytePosition+2));
                byte[] bSizeY = ROActSpr.reverseContent(Arrays.copyOfRange(spriteSPR, bytePosition+2, bytePosition+4));
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

                ROImg roImg = new ROImg();
                roImg.setSizeX(sizeX);
                roImg.setSizeY(sizeY);
                roImg.setFrame(Arrays.copyOfRange(spriteSPR, bytePosition+6, bytePosition+6+byteSize));
                roSpr.frames.add(roImg);

                bytePosition += 6 + byteSize;
                totalFrames--;
            }

            roSpr.palette = Arrays.copyOfRange(spriteSPR, bytePosition, spriteSPR.length);

            return roSpr;
        }

    }

    private ROSpr() {

    }

    public void setPalette(@NotNull URL palette) throws IOException {
        this.palette = ROActSpr.urlToByte(palette);
    }

    public BufferedImage getPng(int framePost) {

        ROImg roImg = frames.get(framePost);
        byte[] frame = roImg.getFrame();

        // Prepare palette
        List<byte[]> rgbPalette = new ArrayList<>();
        for(int i = 0; i < palette.length; i+=4) {
            byte[] rgbIndex = Arrays.copyOfRange(palette, i, i+4);
            rgbPalette.add(rgbIndex);
        }

        // Prepare image
        BufferedImage dest = new BufferedImage(roImg.getSizeX(), roImg.getSizeY(), BufferedImage.TYPE_INT_ARGB);
        int x,y = x = 0;
        for(int i = 0; i < frame.length; i++) {
            if (i > 0 && i%roImg.getSizeX() == 0) {
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

    public ROImg getROImg(int frame) {
        return frames.get(frame);
    }
}
