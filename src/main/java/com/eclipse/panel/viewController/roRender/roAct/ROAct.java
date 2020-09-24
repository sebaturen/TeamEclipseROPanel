package com.eclipse.panel.viewController.roRender.roAct;

import com.eclipse.panel.viewController.roRender.ROActSpr;
import com.eclipse.panel.viewController.roRender.ROSprite;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ROAct {

    // Constant
    public static final int VERSION_START = 2;
    public static final int N_ANIMATIONS_START = 4;
    public static final boolean DEBUG = false;

    // Attributes
    private final List<ROAnimation> roAnimations = new ArrayList<>();

    public static class Builder {

        private URL actFile;
        public Builder(@NotNull URL actFile) {
            this.actFile = actFile;
        }

        public ROAct build() throws IOException {
            ROAct roAct = new ROAct();

            byte[] bAct = ROActSpr.urlToByte(actFile);
            short version = bAct[VERSION_START];
            byte[] bNAnimations = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, N_ANIMATIONS_START, N_ANIMATIONS_START+2));
            short nAnimations = (ByteBuffer.wrap(bNAnimations)).getShort();
            int readBytePost = N_ANIMATIONS_START+2+10;

            if (DEBUG) System.out.println("Version "+ version);
            if (DEBUG) System.out.println("Number Animations: "+ nAnimations +" "+ Arrays.toString(bNAnimations));
            if (DEBUG) System.out.println("Read Byte Position "+ readBytePost);
            while (nAnimations-- >= 1) {
                ROAnimation roAnimation = new ROAnimation();
                if (DEBUG) System.out.println("////// ("+ nAnimations +") ANIMATION /////");
                byte[] bNFrames = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                int nFrames = (ByteBuffer.wrap(bNFrames)).getInt();

                if (DEBUG) System.out.println("Number frames: "+ nFrames +" "+ Arrays.toString(bNFrames));
                if (DEBUG) System.out.println("nAnimations Read Byte Position "+ readBytePost);
                while (nFrames-- >= 1) {
                    ROFrame roFrame = new ROFrame();
                    if (DEBUG) System.out.println("======= ("+ nFrames +") FRAME =========== -> "+ readBytePost);
                    readBytePost += 32;
                    if (DEBUG) System.out.println(nFrames +". Frames Read Byte Position "+ readBytePost);

                    byte[] bNSubFrames = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+4));
                    int nSubFrames = (ByteBuffer.wrap(bNSubFrames)).getInt();
                    readBytePost += 4;

                    if (DEBUG) System.out.println("nSubFrames: "+ nSubFrames +" "+ Arrays.toString(bNSubFrames));
                    if (DEBUG) System.out.println("nFrames Read Byte Position "+ readBytePost);
                    while (nSubFrames-- >= 1) {
                        byte[] bOffsetX = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        byte[] bOffsetY = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        byte[] bImage   = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        byte[] bDirection = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        byte[] bColor = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        int offsetX = (ByteBuffer.wrap(bOffsetX)).getInt();
                        int offsetY = (ByteBuffer.wrap(bOffsetY)).getInt();
                        int image   = (ByteBuffer.wrap(bImage)).getInt();
                        int direction = (ByteBuffer.wrap(bDirection)).getInt();

                        float scaleX = 0f;
                        if (version >= 2) {
                            byte[] bScaleX = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            scaleX = (ByteBuffer.wrap(bScaleX)).getFloat();
                        }
                        float scaleY;
                        if (version >= 4) {
                            byte[] bScaleY = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            scaleY = (ByteBuffer.wrap(bScaleY)).getFloat();
                        } else {
                            scaleY = scaleX;
                        }

                        int rotation = 0;
                        int dontJump = 0;
                        if (version >= 2) {
                            byte[] bRotation = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            byte[] bDontJump = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));

                            rotation = (ByteBuffer.wrap(bRotation)).getInt();
                            dontJump = (ByteBuffer.wrap(bDontJump)).getInt();
                        }

                        if (dontJump > 0) {
                            readBytePost += 12;
                        }

                        int sizeX = 0;
                        int sizeY = 0;
                        int soundNo = 0;
                        if (version >= 5) {
                            byte[] bSizeX = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            byte[] bSizeY = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            byte[] bSoundNo = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));

                            sizeX = (ByteBuffer.wrap(bSizeX)).getInt();
                            sizeY = (ByteBuffer.wrap(bSizeY)).getInt();
                            soundNo = (ByteBuffer.wrap(bSoundNo)).getInt();
                        }

                        if (DEBUG) {
                            System.out.println("----------------- ("+ nSubFrames +") SUB FRAME ---------------------------------");
                            System.out.println("offsetX: "+ offsetX +" "+ Arrays.toString(bOffsetX));
                            System.out.println("offsetY: "+ offsetY +" "+ Arrays.toString(bOffsetY));
                            System.out.println("image: "+ image +" "+ Arrays.toString(bImage));
                            System.out.println("direction: "+ direction +" "+ Arrays.toString(bDirection));
                            System.out.println("color: "+ Arrays.toString(bColor));
                            System.out.println("scaleX: "+ scaleX);
                            System.out.println("scaleY: "+ scaleY);
                            System.out.println("rotation: "+ rotation);
                            System.out.println("dontJump: "+ dontJump);
                            System.out.println("sizeX: "+ sizeX);
                            System.out.println("sizeY: "+ sizeY);
                            System.out.println("soundNo: "+ soundNo);
                            System.out.println("nSubFrames Read Byte Position "+ readBytePost);
                            System.out.println("----------------- ("+ nSubFrames +") SUB END FRAME ---------------------------------");
                        }


                        ROSubFrame subFrame = new ROSubFrame();
                        subFrame.setOffSetX(offsetX);
                        subFrame.setOffSetY(offsetY);
                        subFrame.setImage(image);
                        subFrame.setDirection(direction);
                        subFrame.setColor(bColor);
                        subFrame.setScaleX(scaleX);
                        subFrame.setScaleY(scaleY);
                        subFrame.setRotation(rotation);
                        subFrame.setDontJump(dontJump);
                        subFrame.setSizeX(sizeX);
                        subFrame.setSizeY(sizeY);
                        subFrame.setSoundNo(soundNo);

                        roFrame.addSubFrame(subFrame);
                    }

                    if (version <= 4) {
                        readBytePost+=4;
                    }

                    int extraInfo = 0;
                    int extraX = 0;
                    int extraY = 0;
                    if (version >= 2) {
                        byte[] bExtraInfo = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        extraInfo = (ByteBuffer.wrap(bExtraInfo)).getInt();

                        if (extraInfo > 0) {
                            readBytePost+=4;
                            byte[] bExtraX = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            byte[] bExtraY = ROActSpr.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));

                            extraX = (ByteBuffer.wrap(bExtraX)).getInt();
                            extraY = (ByteBuffer.wrap(bExtraY)).getInt();

                        }
                    }

                    if (DEBUG) {
                        System.out.println("Extra Info "+ extraInfo);
                        System.out.println("ExtraX "+ extraX);
                        System.out.println("ExtraY "+ extraY);
                        System.out.println("Loc "+ readBytePost);
                        System.out.println("======= ("+ nFrames +") END FRAME ===========");
                    }
                    readBytePost+=4;

                    roFrame.setExtraInfo(extraInfo);
                    roFrame.setExtraX(extraX);
                    roFrame.setExtraY(extraY);

                    roAnimation.addFrame(roFrame);
                }
                if (DEBUG) System.out.println("////// ("+ nAnimations +") END ANIMATION /////");

                roAct.roAnimations.add(roAnimation);
            }

            return roAct;
        }
    }

    private ROAct() {

    }

    public ROAnimation getRoAnimation(int id) {
        return roAnimations.get(id);
    }
}
