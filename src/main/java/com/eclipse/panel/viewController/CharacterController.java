package com.eclipse.panel.viewController;

import com.eclipse.panel.Logs;
import com.eclipse.panel.gameObject.character.Character;
import com.eclipse.panel.viewController.roRender.ROSprite;

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

    public static void renderCharacter(Character character) {
        renderCharacter(
                character.getJob_id(),
                character.getCharacter_view().get("hair_style_id").getAsInt(),
                character.getSexId(),
                character.getCharacter_view().get("clothes_color_id").getAsInt(),
                character.getCharacter_view().get("hair_color_id").getAsInt(),
                character.getHead_view().get("top_head_view_id").getAsInt(),
                character.getHead_view().get("mid_head_view_id").getAsInt(),
                character.getHead_view().get("low_head_view_id").getAsInt(),
                0
        );
    }

    public static void renderCharacter(
            int job, int head, int sex, int bodyPalette, int headPalette,
            int accTop, int accMid, int accLow,
            int frame
    ) {

        File bodyPng = new File(CHARACTER_PATH+"char_"+ job +"_"+ head +"_"+ sex +"_"+ bodyPalette +"_"+ headPalette +"_"+ frame +".png");
        File accPng = new File(CHARACTER_PATH+"acc_"+ job +"_"+ sex +"_"+ accTop +"_"+ accMid +"_"+ accLow +"_"+ frame +".png");

        // General Render data
        if (!bodyPng.exists() || !accPng.exists()) {
            int floorX = 75;
            int floorY = 125;
            int xPos;
            int yPos;

            try {
                // Body render
                if (!bodyPng.exists()) {

                    ROSprite bodyFrame = getBodySprite(job+"", sex+"", bodyPalette, frame);
                    if (bodyFrame == null) {
                        return;
                    }
                    BufferedImage bodyImg = bodyFrame.getPng();

                    ROSprite headFrame = getHeadSprite(head+"", sex+"", headPalette, frame);
                    if (headFrame == null) {
                        return;
                    }
                    BufferedImage headImg = headFrame.getPng();

                    // Mix elements
                    BufferedImage bodyMapLoc = new BufferedImage(150, 170, BufferedImage.TYPE_INT_ARGB);
                    Graphics gBody = bodyMapLoc.getGraphics();

                    // Draw body
                    xPos = floorX - (bodyFrame.getSizeX()/2) - bodyFrame.getOffSet(0, 0)[0];
                    yPos = floorY - (bodyFrame.getSizeY()/2) + bodyFrame.getOffSet(0, 0)[1];
                    gBody.drawImage(bodyImg, xPos, yPos, null);

                    // Draw head
                    xPos = floorX - (headFrame.getSizeX()/2) - headFrame.getOffSet(0, 0)[0];
                    yPos = floorY - (headFrame.getSizeY()/2) + headFrame.getOffSet(0, 0)[1];
                    gBody.drawImage(headImg, xPos, yPos, null);

                    // reference position
                    gBody.drawLine(0, floorY, 200, floorY);
                    gBody.drawLine(floorX, 0, floorX, 200);
                    gBody.fillOval(floorX-5, floorY-5, 10,10);
                    
                    ImageIO.write(bodyMapLoc, "png", bodyPng);
                }

                // Acc render
                if (!accPng.exists()) {

                    ROSprite accTopFrame = null;
                    BufferedImage accTopImg = null;
                    if (accTop > 0) {
                        accTopFrame = getAccSprite(accTop+"", sex+"", frame);
                        if (accTopFrame != null) {
                            accTopImg = accTopFrame.getPng();
                        }
                    }

                    ROSprite accMidFrame = null;
                    BufferedImage accMidFrameImg = null;
                    if (accMid > 0) {
                        accMidFrame = getAccSprite(accMid+"", sex+"", frame);
                        if (accMidFrame != null) {
                            accMidFrameImg = accMidFrame.getPng();
                        }
                    }

                    ROSprite accLowFrame = null;
                    BufferedImage accLowFrameImg = null;
                    if (accLow > 0) {
                        accLowFrame = getAccSprite(accLow+"", sex+"", frame);
                        if (accLowFrame != null) {
                            accLowFrameImg = accLowFrame.getPng();
                        }
                    }

                    // Mix elements
                    BufferedImage accMapLoc = new BufferedImage(150, 170, BufferedImage.TYPE_INT_ARGB);
                    Graphics gAcc = accMapLoc.getGraphics();

                    // Draw accessory MID
                    if (accMidFrame != null) {
                        xPos = floorX - (accMidFrame.getSizeX()/2) - accMidFrame.getOffSet(0, 0)[0] + 1;
                        yPos = floorY - (accMidFrame.getSizeY()/2) + accMidFrame.getOffSet(0, 0)[1];
                        gAcc.drawImage(accMidFrameImg, xPos, yPos, null);
                    }

                    // Draw accessory TOP
                    if (accTopFrame != null) {
                        xPos = floorX - (accTopFrame.getSizeX()/2) - accTopFrame.getOffSet(0, 0)[0] + 1;
                        yPos = floorY - (accTopFrame.getSizeY()/2) + accTopFrame.getOffSet(0, 0)[1];
                        gAcc.drawImage(accTopImg, xPos, yPos, null);
                    }

                    // Draw accessory LOW
                    if (accLowFrame != null) {
                        xPos = floorX - (accLowFrame.getSizeX()/2) - accLowFrame.getOffSet(0, 0)[0] + 1;
                        yPos = floorY - (accLowFrame.getSizeY()/2) + accLowFrame.getOffSet(0, 0)[1];
                        gAcc.drawImage(accLowFrameImg, xPos, yPos, null);
                    }

                    ImageIO.write(accMapLoc, "png", accPng);
                }
            } catch (Exception e) {
                Logs.errorLog(CharacterController.class, "Failed to render character\n" +
                        "-> "+ "char_"+ job +"_"+ head +"_"+ sex +"_"+ bodyPalette +"_"+ headPalette +"_"+ frame +".png\n" +
                        "-> "+ "acc_"+ job +"_"+ sex +"_"+ accTop +"_"+ accMid +"_"+ accLow +"_"+ frame +".png\n" +
                        "e-> "+ e);
            }
        }
    }

    private static ROSprite getBodySprite(String jobId, String sexId, int bodyPalette, int postActor) throws Exception {

        String jobSpriteAct = RO_SPRITES_LOC + JOB_LOC + jobsNameProp.getProperty(jobId) +"_"+ sexProp.getProperty(sexId);
        String palettePal = RO_SPRITES_LOC + JOB_PALETTE + jobsNameProp.getProperty(jobId) +"_"+ sexProp.getProperty(sexId) +"_"+ bodyPalette;

        URL jobSprite = classLoader.getResource(jobSpriteAct+".spr");
        URL actSprite = classLoader.getResource(jobSpriteAct+".act");
        URL palette = null;
        if (bodyPalette != 0) {
            palette = classLoader.getResource(palettePal+".pal");
        }
        ROSprite roSprite = ROSprite.processSPR(jobSprite, palette, postActor);
        if (roSprite != null) {
            roSprite.setAct(actSprite);
        }
        return roSprite;
    }

    private static ROSprite getHeadSprite(String headId, String sexId, int headPalette, int postHead) throws Exception {

        String headSpriteAct = RO_SPRITES_LOC + HEADS_LOC + headsProp.getProperty(headId) +"_"+ sexProp.getProperty(sexId);
        String palettePal = RO_SPRITES_LOC + HEADS_PALETTE_LOC +"머리"+ headsProp.getProperty(headId) +"_"+ sexProp.getProperty(sexId) +"_"+ headPalette;

        URL headSprite = classLoader.getResource(headSpriteAct+".spr");
        URL actSprite = classLoader.getResource(headSpriteAct+".act");
        URL palette = null;
        if (headPalette != 0) {
            palette = classLoader.getResource(palettePal+".pal");
        }
        ROSprite roSprite = ROSprite.processSPR(headSprite, palette, postHead);
        if (roSprite != null) {
            roSprite.setAct(actSprite);
        }
        return roSprite;
    }

    private static ROSprite getAccSprite(String accTopId, String sexId, int postHead) throws Exception {

        String accSpriteAct = RO_SPRITES_LOC + ACCESSORY_ID_NAME_LOC + sexProp.getProperty(sexId) + accessoryIdName.getProperty(accTopId);

        URL accSprite = classLoader.getResource(accSpriteAct+".spr");
        URL actSprite = classLoader.getResource(accSpriteAct+".act");
        ROSprite roSprite = ROSprite.processSPR(accSprite, null, postHead);
        if (roSprite != null) {
            roSprite.setAct(actSprite);
        }
        return roSprite;
    }

}