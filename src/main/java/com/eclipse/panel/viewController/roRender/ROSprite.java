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

    public ROSprite(ROSpr roSpr, ROAct roAct) {
        this.roSpr = roSpr;
        this.roAct = roAct;
    }

    public ROFrame getAnimationFrame(int animationId, int frameId) {
        return roAct.getRoAnimation(animationId).getRoFrame(frameId);
    }

    public ROSpr getRoSpr() {
        return roSpr;
    }
}
