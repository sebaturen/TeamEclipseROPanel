package com.eclipse.panel.viewController.roRender.roAct;

import com.eclipse.panel.viewController.roRender.ROSprite;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ROAct {

    // Constant
    public static final int VERSION_START = 2;
    public static final int N_ANIMATIONS_START = 4;

    // Attributes
    private final List<ROAnimation> roAnimations = new ArrayList<>();


    public static class Builder {

        private byte[] bAct;
        public Builder(byte[] actInf) {
            this.bAct = actInf;
        }

        public ROAct build() {
            ROAct roAct = new ROAct();

            short version = bAct[VERSION_START];
            byte[] bNAnimations = ROSprite.reverseContent(Arrays.copyOfRange(bAct, N_ANIMATIONS_START, N_ANIMATIONS_START+2));
            short nAnimations = (ByteBuffer.wrap(bNAnimations)).getShort();
            int readBytePost = N_ANIMATIONS_START+2+10;

            //System.out.println("Version "+ version);
            //System.out.println("Number Animations: "+ nAnimations +" "+ Arrays.toString(bNAnimations));
            //System.out.println("Read Byte Position "+ readBytePost);
            while (nAnimations-- >= 1) {
                ROAnimation roAnimation = new ROAnimation();
                //System.out.println("////// ("+ nAnimations +") ANIMATION /////");
                byte[] bNFrames = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                int nFrames = (ByteBuffer.wrap(bNFrames)).getInt();

                //System.out.println("Number frames: "+ nFrames +" "+ Arrays.toString(bNFrames));
                //System.out.println("nAnimations Read Byte Position "+ readBytePost);
                while (nFrames-- >= 1) {
                    ROFrame roFrame = new ROFrame();
                    //System.out.println("======= ("+ nFrames +") FRAME =========== -> "+ readBytePost);
                    readBytePost += 32;
                    //System.out.println(nFrames +". Frames Read Byte Position "+ readBytePost);

                    byte[] bNSubFrames = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+4));
                    int nSubFrames = (ByteBuffer.wrap(bNSubFrames)).getInt();
                    readBytePost += 4;

                    //System.out.println("nSubFrames: "+ nSubFrames +" "+ Arrays.toString(bNSubFrames));
                    //System.out.println("nFrames Read Byte Position "+ readBytePost);
                    while (nSubFrames-- >= 1) {
                        byte[] bOffsetX = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        byte[] bOffsetY = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        byte[] bImage   = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        byte[] bDirection = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        byte[] bColor = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        int offsetX = (ByteBuffer.wrap(bOffsetX)).getInt();
                        int offsetY = (ByteBuffer.wrap(bOffsetY)).getInt();
                        int image   = (ByteBuffer.wrap(bImage)).getInt();
                        int direction = (ByteBuffer.wrap(bDirection)).getInt();

                        float scaleX = 0f;
                        if (version >= 2) {
                            byte[] bScaleX = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            scaleX = (ByteBuffer.wrap(bScaleX)).getFloat();
                        }
                        float scaleY;
                        if (version >= 4) {
                            byte[] bScaleY = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            scaleY = (ByteBuffer.wrap(bScaleY)).getFloat();
                        } else {
                            scaleY = scaleX;
                        }

                        int rotation = 0;
                        int dontJump = 0;
                        if (version >= 2) {
                            byte[] bRotation = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            byte[] bDontJump = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));

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
                            byte[] bSizeX = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            byte[] bSizeY = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            byte[] bSoundNo = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));

                            sizeX = (ByteBuffer.wrap(bSizeX)).getInt();
                            sizeY = (ByteBuffer.wrap(bSizeY)).getInt();
                            soundNo = (ByteBuffer.wrap(bSoundNo)).getInt();
                        }

                        /*System.out.println("----------------- ("+ nSubFrames +") SUB FRAME ---------------------------------");
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
                        System.out.println("----------------- ("+ nSubFrames +") SUB END FRAME ---------------------------------");*/

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

                    int extraInfo = 0;
                    int extraX = 0;
                    int extraY = 0;
                    if (version >= 2) {
                        byte[] bExtraInfo = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                        extraInfo = (ByteBuffer.wrap(bExtraInfo)).getInt();

                        if (extraInfo > 0) {
                            readBytePost+=4;
                            byte[] bExtraX = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));
                            byte[] bExtraY = ROSprite.reverseContent(Arrays.copyOfRange(bAct, readBytePost, readBytePost+=4));

                            extraX = (ByteBuffer.wrap(bExtraX)).getInt();
                            extraY = (ByteBuffer.wrap(bExtraY)).getInt();

                        }
                    }

                    if (version <= 4) {
                        readBytePost+=16;
                    }

                    /*System.out.println("Extra Info "+ extraInfo);
                    System.out.println("ExtraX "+ extraX);
                    System.out.println("ExtraY "+ extraY);
                    System.out.println("Loc "+ readBytePost);
                    System.out.println("======= ("+ nFrames +") END FRAME ===========");*/
                    readBytePost+=4;

                    roFrame.setExtraInfo(extraInfo);
                    roFrame.setExtraX(extraX);
                    roFrame.setExtraY(extraY);

                    roAnimation.addFrame(roFrame);
                }
                //System.out.println("////// ("+ nAnimations +") END ANIMATION /////");

                roAct.roAnimations.add(roAnimation);
            }

            return roAct;
        }
    }

    private ROAct() {

    }

    public int[] getOffSet(int animationId, int frameId) {

        ROSubFrame subFrame = roAnimations.get(animationId).getRoFrame(frameId).getFirstSprite();
        return new int[] { subFrame.getOffSetX(), subFrame.getOffSetY() };

    }
}
