package com.eclipse.panel.viewController;

import com.eclipse.panel.Logs;
import com.eclipse.panel.gameObject.character.Character;
import com.eclipse.panel.viewController.roRender.ROSprite;
import com.eclipse.panel.viewController.roRender.roAct.ROAct;
import com.eclipse.panel.viewController.roRender.roAct.ROSubFrame;
import com.eclipse.panel.viewController.roRender.roSpr.ROSpr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CharacterController {

    public static String CHARACTER_PATH = ViewController.FILEPATH +"assets/img/ro/characters/";
    private static final String RO_SPRITES_LOC = "ro_sprites/";
    private static final String HEADS_LOC = "head/";
    private static final String HEADS_PALETTE_LOC = "palette/head/";
    private static final String JOB_LOC = "body/";
    private static final String JOB_PALETTE = "palette/body/";
    private static final String ACCESSORY_ID_NAME_LOC = "accessory/";

    private static final String HEADS_PROPERTIES = "head_id.properties";
    private static final String JOB_NAME_PROPERTIES = "job_names.properties";
    private static final String SEX_PROPERTIES = "sex_id.properties";
    private static final String ACCESSORY_ID_NAME_PROPERTIES = "accessory_id_name.properties";

    public static final Properties headsProp = new Properties();
    public static final Properties jobsNameProp = new Properties();
    private static final Properties sexProp = new Properties();
    public static final Properties accessoryIdName = new Properties();
    private static ClassLoader classLoader;

    static {
        try {
            classLoader = CharacterController.class.getClassLoader();
            FileInputStream fsJobs = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+JOB_NAME_PROPERTIES)).getFile());
            FileInputStream fsSex = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+SEX_PROPERTIES)).getFile());
            FileInputStream fsHeads = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+HEADS_PROPERTIES)).getFile());
            FileInputStream fsAccIdName = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+ACCESSORY_ID_NAME_PROPERTIES)).getFile());
            jobsNameProp.load(new InputStreamReader(fsJobs, StandardCharsets.UTF_8));
            sexProp.load(new InputStreamReader(fsSex, StandardCharsets.UTF_8));
            accessoryIdName.load(new InputStreamReader(fsAccIdName, StandardCharsets.UTF_8));
            headsProp.load(fsHeads);
        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public static Character getCharacter(String id) {
        return new Character.Builder(Integer.parseInt(id)).build();
    }

    public static String[] renderCharacter(Character character) {
        return renderCharacter(
                character.getJob_id(),
                character.getCharacter_view().get("hair_style_id").getAsInt(),
                character.getSexId(),
                character.getCharacter_view().get("clothes_color_id").getAsInt(),
                character.getCharacter_view().get("hair_color_id").getAsInt(),
                character.getHead_view().get("top_head_view_id").getAsInt(),
                character.getHead_view().get("mid_head_view_id").getAsInt(),
                character.getHead_view().get("low_head_view_id").getAsInt(),
                0,
                0
        );
    }

    public static String[] renderCharacter(
            int job, int head, int sex, int bodyPalette, int headPalette,
            int accTop, int accMid, int accLow,
            int actionId, int frameId
    ) {

        String bodyPngFileName = "char_"+ job +"_"+ head +"_"+ sex +"_"+ bodyPalette +"_"+ headPalette +"_"+ actionId +"_"+ frameId +".png";
        String accPngFileName = "acc_"+ job +"_"+ sex +"_"+ accTop +"_"+ accMid +"_"+ accLow +"_"+ actionId +"_"+ frameId +".png";
        File bodyPng = new File(CHARACTER_PATH + bodyPngFileName);
        File accPng = new File(CHARACTER_PATH + accPngFileName);

        // General Render data
        if (!bodyPng.exists() || !accPng.exists()) {
            int floorX = 75;
            int floorY = 125;
            int xPos;
            int yPos;
            ROSprite bodyFrame = null;
            ROSprite headFrame = null;

            try {
                // Body render
                if (!bodyPng.exists()) {

                    bodyFrame = getBodySprite(job+"", sex+"", bodyPalette);
                    BufferedImage bodyImg = bodyFrame.getPng(actionId, frameId);

                    headFrame = getHeadSprite(head+"", sex+"", headPalette);
                    BufferedImage headImg = headFrame.getPng(actionId, frameId);

                    // Mix elements
                    BufferedImage bodyMapLoc = new BufferedImage(150, 170, BufferedImage.TYPE_INT_ARGB);
                    Graphics gBody = bodyMapLoc.getGraphics();

                    // Draw body
                    xPos = floorX - (bodyFrame.getLastImg().getCenterX()) + bodyFrame.getLastSubFrame().getOffSetX();
                    yPos = floorY - (bodyFrame.getLastImg().getCenterY()) + bodyFrame.getLastSubFrame().getOffSetY();
                    gBody.drawImage(bodyImg, xPos, yPos, null);

                    // Draw head
                    int headPostX = 0;
                    int headPostY = 0;
                    /*System.out.println("Head FIX POST");
                    System.out.println("Body ExtraX "+ bodyFrame.getLastFrame().getExtraX() +" - Head Extra X "+ headFrame.getLastFrame().getExtraX() +" -- OffsetX: "+ headFrame.getLastSubFrame().getOffSetX());
                    System.out.println("Body ExtraY "+ bodyFrame.getLastFrame().getExtraY() +" - Head Extra Y "+ headFrame.getLastFrame().getExtraY() +" -- OffsetY: "+ headFrame.getLastSubFrame().getOffSetY());*/
                    if (headFrame.getLastFrame().getExtraX() != 0 || headFrame.getLastFrame().getExtraY() != 0) {
                        headPostX = bodyFrame.getLastFrame().getExtraX() - headFrame.getLastFrame().getExtraX();
                        headPostY = bodyFrame.getLastFrame().getExtraY() - headFrame.getLastFrame().getExtraY();
                    }
                    //System.out.println("HeadPostX "+ headPostX +" - HeadPostY "+ headPostY);
                    headPostX += headFrame.getLastSubFrame().getOffSetX();
                    headPostY += headFrame.getLastSubFrame().getOffSetY();
                    //System.out.println("READY HeadPostX "+ headPostX +" - HeadPostY "+ headPostY);
                    xPos = floorX - (headFrame.getLastImg().getCenterX()) + headPostX;
                    yPos = floorY - (headFrame.getLastImg().getCenterY()) + headPostY;
                    //System.out.println("xpos "+ xPos +" - ypos "+ yPos);
                    gBody.drawImage(headImg, xPos, yPos, null);


                    /*/ reference position
                    gBody.drawLine(0, floorY, 200, floorY);
                    gBody.drawLine(floorX, 0, floorX, 200);
                    gBody.fillOval(floorX-5, floorY-5, 10,10);*/
                    
                    ImageIO.write(bodyMapLoc, "png", bodyPng);
                }

                // Acc render
                if (!accPng.exists()) {

                    ROSprite accTopFrame = null;
                    BufferedImage accTopImg = null;
                    if (accTop > 0) {
                        accTopFrame = getAccSprite(accTop+"", sex+"");
                        accTopImg = accTopFrame.getPng(actionId, frameId);
                    }

                    ROSprite accMidFrame = null;
                    BufferedImage accMidFrameImg = null;
                    if (accMid > 0) {
                        accMidFrame = getAccSprite(accMid+"", sex+"");
                        accMidFrameImg = accMidFrame.getPng(actionId, frameId);
                    }

                    ROSprite accLowFrame = null;
                    BufferedImage accLowFrameImg = null;
                    if (accLow > 0) {
                        accLowFrame = getAccSprite(accLow+"", sex+"");
                        accLowFrameImg = accLowFrame.getPng(actionId, frameId);
                    }

                    // Mix elements
                    BufferedImage accMapLoc = new BufferedImage(150, 170, BufferedImage.TYPE_INT_ARGB);
                    Graphics gAcc = accMapLoc.getGraphics();

                    // Draw accessory MID
                    if (accMidFrame != null) {
                        if (bodyFrame == null) {
                            bodyFrame = getBodySprite(job+"", sex+"", bodyPalette);
                            bodyFrame.setAnimationFrame(actionId, frameId);
                        }
                        // Draw
                        int accMidFrameX = 0;
                        int accMidFrameY = 0;
                        if (accMidFrame.getLastFrame().getExtraX() != 0 || accMidFrame.getLastFrame().getExtraY() != 0) {
                            accMidFrameX = bodyFrame.getLastFrame().getExtraX() - accMidFrame.getLastFrame().getExtraX();
                            accMidFrameY = bodyFrame.getLastFrame().getExtraY() - accMidFrame.getLastFrame().getExtraY();
                        }
                        accMidFrameX += accMidFrame.getLastSubFrame().getOffSetX();
                        accMidFrameY += accMidFrame.getLastSubFrame().getOffSetY();
                        xPos = floorX - (accMidFrame.getLastImg().getCenterX()) + accMidFrameX;
                        yPos = floorY - (accMidFrame.getLastImg().getCenterY()) + accMidFrameY;
                        gAcc.drawImage(accMidFrameImg, xPos, yPos, null);
                    }

                    // Draw accessory TOP
                    if (accTopFrame != null) {
                        if (bodyFrame == null) {
                            bodyFrame = getBodySprite(job+"", sex+"", bodyPalette);
                            bodyFrame.setAnimationFrame(actionId, frameId);
                        }
                        // Draw
                        int accTopFrameX = 0;
                        int accTopFrameY = 0;
                        if (accTopFrame.getLastFrame().getExtraX() != 0 || accTopFrame.getLastFrame().getExtraY() != 0) {
                            accTopFrameX = bodyFrame.getLastFrame().getExtraX() - accTopFrame.getLastFrame().getExtraX();
                            accTopFrameY = bodyFrame.getLastFrame().getExtraY() - accTopFrame.getLastFrame().getExtraY();
                        }
                        accTopFrameX += accTopFrame.getLastSubFrame().getOffSetX();
                        accTopFrameY += accTopFrame.getLastSubFrame().getOffSetY();
                        xPos = floorX - (accTopFrame.getLastImg().getCenterX()) + accTopFrameX;
                        yPos = floorY - (accTopFrame.getLastImg().getCenterY()) + accTopFrameY;
                        gAcc.drawImage(accTopImg, xPos, yPos, null);
                    }

                    // Draw accessory LOW
                    if (accLowFrame != null) {
                        if (bodyFrame == null) {
                            bodyFrame = getBodySprite(job+"", sex+"", bodyPalette);
                            bodyFrame.setAnimationFrame(actionId, frameId);
                        }
                        // Draw
                        int accLowFrameX = 0;
                        int accLowFrameY = 0;
                        if (accLowFrame.getLastFrame().getExtraX() != 0 || accLowFrame.getLastFrame().getExtraY() != 0) {
                            accLowFrameX = bodyFrame.getLastFrame().getExtraX() - accLowFrame.getLastFrame().getExtraX();
                            accLowFrameY = bodyFrame.getLastFrame().getExtraY() - accLowFrame.getLastFrame().getExtraY();
                        }
                        accLowFrameX += accLowFrame.getLastSubFrame().getOffSetX();
                        accLowFrameY += accLowFrame.getLastSubFrame().getOffSetY();
                        xPos = floorX - (accLowFrame.getLastImg().getCenterX()) + accLowFrameX;
                        yPos = floorY - (accLowFrame.getLastImg().getCenterY()) + accLowFrameY;
                        gAcc.drawImage(accLowFrameImg, xPos, yPos, null);
                    }

                    /*/ reference position
                    gAcc.drawLine(0, floorY, 200, floorY);
                    gAcc.drawLine(floorX, 0, floorX, 200);
                    gAcc.fillOval(floorX-5, floorY-5, 10,10);*/

                    ImageIO.write(accMapLoc, "png", accPng);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logs.errorLog(CharacterController.class, "Failed to render character\n" +
                        "-> "+ "char_"+ job +"_"+ head +"_"+ sex +"_"+ bodyPalette +"_"+ headPalette +"_"+ frameId +".png\n" +
                        "-> "+ "acc_"+ job +"_"+ sex +"_"+ accTop +"_"+ accMid +"_"+ accLow +"_"+ frameId +".png\n" +
                        "e-> "+ e);
            }
        }

        return new String[] {bodyPngFileName, accPngFileName};
    }

    private static ROSprite getBodySprite(String jobId, String sexId, int bodyPalette) throws Exception {

        String jobSpriteAct = RO_SPRITES_LOC + JOB_LOC + jobsNameProp.getProperty(jobId) +"_"+ sexProp.getProperty(sexId);
        String palettePal = RO_SPRITES_LOC + JOB_PALETTE + jobsNameProp.getProperty(jobId) +"_"+ sexProp.getProperty(sexId) +"_"+ bodyPalette;

        URL jobSprite = classLoader.getResource(jobSpriteAct+".spr");
        URL actSprite = classLoader.getResource(jobSpriteAct+".act");

        ROSpr roSpr = new ROSpr.Builder(jobSprite).build();
        if (bodyPalette != 0) {
            URL palette = classLoader.getResource(palettePal+".pal");
            roSpr.setPalette(palette);
        }
        return new ROSprite(roSpr, new ROAct.Builder(actSprite).build());

    }

    private static ROSprite getHeadSprite(String headId, String sexId, int headPalette) throws Exception {

        String headSpriteAct = RO_SPRITES_LOC + HEADS_LOC + headsProp.getProperty(headId+"_"+ sexId) +"_"+ sexProp.getProperty(sexId);
        String palettePal = RO_SPRITES_LOC + HEADS_PALETTE_LOC +"머리"+ headsProp.getProperty(headId+"_"+ sexId) +"_"+ sexProp.getProperty(sexId) +"_"+ headPalette;

        URL headSprite = classLoader.getResource(headSpriteAct+".spr");
        URL actSprite = classLoader.getResource(headSpriteAct+".act");

        ROSpr roSpr = new ROSpr.Builder(headSprite).build();
        if (headPalette != 0) {
            URL palette = classLoader.getResource(palettePal+".pal");
            roSpr.setPalette(palette);
        }
        return new ROSprite(roSpr, new ROAct.Builder(actSprite).build());

    }

    private static ROSprite getAccSprite(String accTopId, String sexId) throws Exception {

        String accSpriteAct = RO_SPRITES_LOC + ACCESSORY_ID_NAME_LOC + sexProp.getProperty(sexId) + accessoryIdName.getProperty(accTopId);

        URL accSprite = classLoader.getResource(accSpriteAct+".spr");
        URL actSprite = classLoader.getResource(accSpriteAct+".act");

        return new ROSprite(new ROSpr.Builder(accSprite).build(), new ROAct.Builder(actSprite).build());

    }

}