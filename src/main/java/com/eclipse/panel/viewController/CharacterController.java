package com.eclipse.panel.viewController;

import com.eclipse.panel.viewController.roRender.ROFrame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CharacterController {

    private static final String RO_SPRITES_LOC = "ro_sprites/";
    private static final String HEADS_PROPERTIES = "head_id.properties";
    private static final String HEADS_LOC = "head/";
    private static final String HEADS_PALETTE = "palette/head/";
    private static final String JOB_NAME_PROPERTIES = "job_names.properties";
    private static final String JOB_LOC = "body/";
    private static final String JOB_PALETTE = "palette/body/";
    private static final String SEX_PROPERTIES = "sex_id.properties";
    private static final Properties headsProp = new Properties();
    private static final Properties jobsNameProp = new Properties();
    private static final Properties sexProp = new Properties();
    private static ClassLoader classLoader;

    static {
        try {
            classLoader = CharacterController.class.getClassLoader();
            FileInputStream fsJobs = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+JOB_NAME_PROPERTIES)).getFile());
            FileInputStream fsSex = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+SEX_PROPERTIES)).getFile());
            FileInputStream fsHeads = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+HEADS_PROPERTIES)).getFile());
            jobsNameProp.load(new InputStreamReader(fsJobs, StandardCharsets.UTF_8));
            sexProp.load(new InputStreamReader(fsSex, StandardCharsets.UTF_8));
            headsProp.load(fsHeads);
        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void renderCharacter(int charId) throws IOException {
        //Character charPlayer = new Character.Builder(charId).build();

        int job = 0;
        int head = 4;
        int sex = 0;
        int bodyPalette = 2;
        int headPalette = 6;
        int frame = 0;

        ROFrame bodyFrame = getBodySprite(
                job+"",
                sex+"",
                bodyPalette,
                frame
        );
        System.out.println(Arrays.toString(bodyFrame.getOffSet()));
        BufferedImage bodyImg = bodyFrame.getPng();

        ROFrame headFrame = getHeadSprite(
                head+"",
                sex+"",
                headPalette,
                frame
        );
        System.out.println(Arrays.toString(headFrame.getOffSet()));
        BufferedImage headImg = headFrame.getPng();

    }

    private static ROFrame getBodySprite(String jobId, String sexId, int bodyPalette, int postActor) throws IOException {

        String jobSpriteAct = RO_SPRITES_LOC + JOB_LOC + jobsNameProp.getProperty(jobId) +"_"+ sexProp.getProperty(sexId);
        String palettePal = RO_SPRITES_LOC + JOB_PALETTE + jobsNameProp.getProperty(jobId) +"_"+ sexProp.getProperty(sexId) +"_"+ bodyPalette;

        URL jobSprite = classLoader.getResource(jobSpriteAct+".spr");
        URL actSprite = classLoader.getResource(jobSpriteAct+".act");
        URL palette = null;
        if (bodyPalette != 0) {
            palette = classLoader.getResource(palettePal+".pal");
        }
        ROFrame roFrame = ROFrame.processSPR(jobSprite, palette, postActor);
        if (roFrame != null) {
            roFrame.setAct(actSprite);
        }
        return roFrame;
    }

    private static ROFrame getHeadSprite(String headId, String sexId, int headPalette, int postHead) throws IOException {

        String headSpriteAct = RO_SPRITES_LOC + HEADS_LOC + headsProp.getProperty(headId) +"_"+ sexProp.getProperty(sexId);
        String palettePal = RO_SPRITES_LOC + HEADS_PALETTE +"머리"+ headsProp.getProperty(headId) +"_"+ sexProp.getProperty(sexId) +"_"+ headPalette;

        URL headSprite = classLoader.getResource(headSpriteAct+".spr");
        URL actSprite = classLoader.getResource(headSpriteAct+".act");
        URL palette = null;
        if (headPalette != 0) {
            palette = classLoader.getResource(palettePal+".pal");
        }
        ROFrame roFrame = ROFrame.processSPR(headSprite, palette, postHead);
        if (roFrame != null) {
            roFrame.setAct(actSprite);
        }
        return roFrame;
    }

    public static void main(String... args) throws IOException {
        renderCharacter(1);
    }

}