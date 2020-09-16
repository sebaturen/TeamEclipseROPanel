package com.eclipse.panel.viewController;

import com.eclipse.panel.viewController.roRender.ROFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CharacterController {

    private static final String RO_SPRITES_LOC = "ro_sprites/";
    private static final String HEADS_PROPERTIES = "head_id.properties";
    private static final String HEADS_JOB_POST_PROPERTIES = "head_job_position.properties";
    private static final String HEADS_LOC = "head/";
    private static final String HEADS_PALETTE = "palette/head/";
    private static final String JOB_NAME_PROPERTIES = "job_names.properties";
    private static final String JOB_LOC = "body/";
    private static final String JOB_PALETTE = "palette/body/";
    private static final String SEX_PROPERTIES = "sex_id.properties";
    private static final String ACCESSORY_ID_NAME_PROPERTIES = "accessory_id_name.properties";
    private static final String ACCESSORY_ID_NAME = "accessory/";
    private static final Properties headsProp = new Properties();
    private static final Properties headsJobProp = new Properties();
    private static final Properties jobsNameProp = new Properties();
    private static final Properties sexProp = new Properties();
    private static final Properties accessoryIdName = new Properties();
    private static ClassLoader classLoader;

    static {
        try {
            classLoader = CharacterController.class.getClassLoader();
            FileInputStream fsJobs = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+JOB_NAME_PROPERTIES)).getFile());
            FileInputStream fsSex = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+SEX_PROPERTIES)).getFile());
            FileInputStream fsHeads = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+HEADS_PROPERTIES)).getFile());
            FileInputStream fsHeadsJob = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+HEADS_JOB_POST_PROPERTIES)).getFile());
            FileInputStream fsAccIdName = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+ACCESSORY_ID_NAME_PROPERTIES)).getFile());
            jobsNameProp.load(new InputStreamReader(fsJobs, StandardCharsets.UTF_8));
            sexProp.load(new InputStreamReader(fsSex, StandardCharsets.UTF_8));
            accessoryIdName.load(new InputStreamReader(fsAccIdName, StandardCharsets.UTF_8));
            headsProp.load(fsHeads);
            headsJobProp.load(fsHeadsJob);
        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void renderCharacter(int job) throws IOException {
        //Character charPlayer = new Character.Builder(charId).build();

        //int job = 13;
        /*
        // Martin
        job = 15;
        int head = 23;
        int sex = 0;
        int bodyPalette = 0;
        int headPalette = 6;
        int frame = 0;
        int accTop = 224;
        int accMid = 0;
        int accLow = 194;
        // Galamir
        job = 14;
        int head = 4;
        int sex = 0;
        int bodyPalette = 2;
        int headPalette = 6;
        int frame = 0;
        int accTop = 224;
        int accMid = 100;
        int accLow = 829;
        // Clein
        job = 8;
        int head = 7;
        int sex = 0;
        int bodyPalette = 2;
        int headPalette = 0;
        int frame = 0;
        int accTop = 1675;
        int accMid = 12;
        int accLow = 1691;
        // Sawako
        job = 16;
        int head = 21;
        int sex = 1;
        int bodyPalette = 0;
        int headPalette = 6;
        int frame = 0;
        int accTop = 283;
        int accMid = 1582;
        int accLow = 1579;
         */
        job = 3;
        int head = 9;
        int sex = 0;
        int bodyPalette = 0;
        int headPalette = 8;
        int frame = 0;
        int accTop = 0;
        int accMid = 0;
        int accLow = 0;

        ROFrame bodyFrame = getBodySprite(job+"", sex+"", bodyPalette, frame);
        BufferedImage bodyImg = bodyFrame.getPng();

        ROFrame headFrame = getHeadSprite(head+"", sex+"", headPalette, frame);
        BufferedImage headImg = headFrame.getPng();

        ROFrame accTopFrame = null;
        BufferedImage accTopImg = null;
        if (accTop > 0) {
            accTopFrame = getAccSprite(accTop+"", sex+"", frame);
            accTopImg = accTopFrame.getPng();
        }

        ROFrame accMidFrame = null;
        BufferedImage accMidFrameImg = null;
        if (accMid > 0) {
            accMidFrame = getAccSprite(accMid+"", sex+"", frame);
            accMidFrameImg = accMidFrame.getPng();
        }

        ROFrame accLowFrame = null;
        BufferedImage accLowFrameImg = null;
        if (accLow > 0) {
            accLowFrame = getAccSprite(accLow+"", sex+"", frame);
            accLowFrameImg = accLowFrame.getPng();
        }

        // Mix elements
        BufferedImage bodyMapLoc = new BufferedImage(150, 170, BufferedImage.TYPE_INT_ARGB);
        BufferedImage accMapLoc = new BufferedImage(150, 170, BufferedImage.TYPE_INT_ARGB);
        Graphics gBody = bodyMapLoc.getGraphics();
        Graphics gAcc = accMapLoc.getGraphics();
        int floorX = 75;
        int floorY = 125;

        // Draw body
        int xPos = floorX - (bodyFrame.getSizeX()/2) - bodyFrame.getOffSet()[0];
        int yPos = floorY - (bodyFrame.getSizeY()/2) + bodyFrame.getOffSet()[1];
        gBody.drawImage(bodyImg, xPos, yPos, null);

        // Draw head
        String fixHeadID = job+"_"+sex+"_";
        int fixPostY = bodyFrame.getSizeY() - 73;
        if (headsJobProp.get(fixHeadID+"y") != null) {
            fixPostY += Integer.parseInt(headsJobProp.get(fixHeadID+"y").toString());
        }
        int fixPostX = bodyFrame.getOffSet()[0];
        if (headsJobProp.get(fixHeadID+"x") != null) {
            fixPostX += Integer.parseInt(headsJobProp.get(fixHeadID+"x").toString());
        }
        xPos = floorX - (headFrame.getSizeX()/2) - headFrame.getOffSet()[0] - fixPostX;
        yPos = floorY - (headFrame.getSizeY()/2) + headFrame.getOffSet()[1] - fixPostY;
        gBody.drawImage(headImg, xPos, yPos, null);

        // Draw accessory MID
        if (accMidFrame != null) {
            xPos = floorX - (accMidFrame.getSizeX()/2) - accMidFrame.getOffSet()[0] - fixPostX + 2;
            yPos = floorY - (accMidFrame.getSizeY()/2) + accMidFrame.getOffSet()[1] - fixPostY;
            gAcc.drawImage(accMidFrameImg, xPos, yPos, null);
        }

        // Draw accessory LOW
        if (accLowFrame != null) {
            xPos = floorX - (accLowFrame.getSizeX()/2) - accLowFrame.getOffSet()[0] - fixPostX + 2;
            yPos = floorY - (accLowFrame.getSizeY()/2) + accLowFrame.getOffSet()[1] - fixPostY;
            gAcc.drawImage(accLowFrameImg, xPos, yPos, null);
        }

        // Draw accessory TOP
        if (accTopFrame != null) {
            xPos = floorX - (accTopFrame.getSizeX()/2) - accTopFrame.getOffSet()[0] - fixPostX + 2;
            yPos = floorY - (accTopFrame.getSizeY()/2) + accTopFrame.getOffSet()[1] - fixPostY;
            gAcc.drawImage(accTopImg, xPos, yPos, null);
        }

        // reference position
        //g.drawLine(0, floorY, 200, floorY);
        //g.drawLine(floorX, 0, floorX, 200);
        //g.fillOval(floorX-5, floorY-5, 10,10);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bodyMapLoc, "png", new File("body_meg_"+ job +"_"+ sex +".png"));
        ImageIO.write(accMapLoc, "png", new File("acc_meg_"+ job +"_"+ sex +".png"));

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

    private static ROFrame getAccSprite(String accTopId, String sexId, int postHead) throws IOException {

        String accSpriteAct = RO_SPRITES_LOC + ACCESSORY_ID_NAME + sexProp.getProperty(sexId) + accessoryIdName.getProperty(accTopId);

        URL accSprite = classLoader.getResource(accSpriteAct+".spr");
        URL actSprite = classLoader.getResource(accSpriteAct+".act");
        ROFrame roFrame = ROFrame.processSPR(accSprite, null, postHead);
        if (roFrame != null) {
            roFrame.setAct(actSprite);
        }
        return roFrame;
    }

    public static void main(String... args) {
        int i = 0;
        while (i != 25) {
            try {
                renderCharacter(i);
                break;
            } catch (IOException e) {
                System.out.println("error "+ i);
            }
            i++;
        }
    }

}