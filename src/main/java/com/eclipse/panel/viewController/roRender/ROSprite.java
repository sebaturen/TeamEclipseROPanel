package com.eclipse.panel.viewController.roRender;

import com.eclipse.panel.viewController.roRender.roAct.ROAct;
import com.eclipse.panel.viewController.roRender.roAct.ROFrame;
import com.eclipse.panel.viewController.roRender.roAct.ROSubFrame;
import com.eclipse.panel.viewController.roRender.roSpr.ROImg;
import com.eclipse.panel.viewController.roRender.roSpr.ROSpr;

import javax.validation.constraints.NotNull;
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

    // Attribute
    private ROSpr roSpr;
    private ROAct roAct;

    // Last elements
    private ROFrame lastFrame;
    private ROSubFrame lastSubFrame;
    private ROImg lastImg;

    public ROSprite(ROSpr roSpr, ROAct roAct) {
        this.roSpr = roSpr;
        this.roAct = roAct;
    }

    public BufferedImage getPng(int animationId, int frameId) {

        lastFrame = roAct.getRoAnimation(animationId).getRoFrame(frameId);
        for(ROSubFrame roSubFrame : lastFrame.getSubFrames()) {
            int sprite = roSubFrame.getImage();
            if (sprite >= 0) {
                lastSubFrame = roSubFrame;
                lastImg = roSpr.getROImg(sprite);
                return roSpr.getPng(sprite);
            }
        }

        return null;
    }

    public void setAnimationFrame(int animationId, int frameId) {

        lastFrame = roAct.getRoAnimation(animationId).getRoFrame(frameId);
        for(ROSubFrame roSubFrame : lastFrame.getSubFrames()) {
            int sprite = roSubFrame.getImage();
            if (sprite >= 0) {
                lastSubFrame = roSubFrame;
                lastImg = roSpr.getROImg(sprite);
                break;
            }
        }
    }

    public ROImg getLastImg() {
        return lastImg;
    }

    public ROFrame getLastFrame() {
        return lastFrame;
    }

    public ROSubFrame getLastSubFrame() {
        return lastSubFrame;
    }

}
