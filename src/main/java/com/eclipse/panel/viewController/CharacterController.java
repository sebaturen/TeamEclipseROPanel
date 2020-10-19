package com.eclipse.panel.viewController;

import com.eclipse.panel.Logs;
import com.eclipse.panel.User;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.character.Character;
import com.eclipse.panel.viewController.roRender.ROSprite;
import com.eclipse.panel.viewController.roRender.roAct.ROAct;
import com.eclipse.panel.viewController.roRender.roAct.ROFrame;
import com.eclipse.panel.viewController.roRender.roAct.ROSubFrame;
import com.eclipse.panel.viewController.roRender.roSpr.ROSpr;
import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CharacterController {

    public static String CHARACTER_PATH = ViewController.FILEPATH +"assets/img/ro/characters/";
    public static String ITEMS_PATH = ViewController.FILEPATH +"assets/img/ro/items/";
    private static final String RO_SPRITES_LOC = "ro_sprites/";
    private static final String HEADS_LOC = "head/";
    private static final String HEADS_PALETTE_LOC = "palette/head/";
    private static final String JOB_LOC = "body/";
    private static final String JOB_PALETTE = "palette/body/";
    private static final String ACCESSORY_ID_NAME_LOC = "accessory/";
    private static final String ITEM_LOC = "items/";

    private static final String HEADS_PROPERTIES = "head_id.properties";
    private static final String JOB_NAME_PROPERTIES = "job_names.properties";
    private static final String SEX_PROPERTIES = "sex_id.properties";
    private static final String ACCESSORY_ID_NAME_PROPERTIES = "accessory_id_name.properties";
    private static final String ITEM_ID_NAME_PROPERTIES = "items_id_name.properties";

    public static final Properties headsProp = new Properties();
    public static final Properties jobsNameProp = new Properties();
    private static final Properties sexProp = new Properties();
    public static final Properties accessoryIdName = new Properties();
    public static final Properties itemNameProp = new Properties();
    private static ClassLoader classLoader;

    static {
        try {
            classLoader = CharacterController.class.getClassLoader();
            FileInputStream fsJobs = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+JOB_NAME_PROPERTIES)).getFile());
            FileInputStream fsSex = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+SEX_PROPERTIES)).getFile());
            FileInputStream fsHeads = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+HEADS_PROPERTIES)).getFile());
            FileInputStream fsAccIdName = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+ACCESSORY_ID_NAME_PROPERTIES)).getFile());
            FileInputStream fsItemIdName = new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+ITEM_ID_NAME_PROPERTIES)).getFile());
            jobsNameProp.load(new InputStreamReader(fsJobs, StandardCharsets.UTF_8));
            sexProp.load(new InputStreamReader(fsSex, StandardCharsets.UTF_8));
            accessoryIdName.load(new InputStreamReader(fsAccIdName, StandardCharsets.UTF_8));
            headsProp.load(fsHeads);
            itemNameProp.load(new InputStreamReader(fsItemIdName, StandardCharsets.UTF_8));
        } catch(IOException e) {
            Logs.fatalLog(CharacterController.class, e.toString());
        }
    }

    public static Character getCharacter(String id) {
        return new Character.Builder(Integer.parseInt(id)).build();
    }

    public static String[] renderCharacter(Character character) {

        int action = 0;
        int direction = 0;

        if (character.getCharacter_display() != null
                && !character.getCharacter_display().isJsonNull()
        ) {
            action = character.getCharacter_display().get("action").getAsInt();
            direction = character.getCharacter_display().get("direction").getAsInt();
        }

        return renderCharacter(
                character.getJob_id(),
                character.getCharacter_view().get("hair_style_id").getAsInt(),
                character.getSexId(),
                character.getCharacter_view().get("clothes_color_id").getAsInt(),
                character.getCharacter_view().get("hair_color_id").getAsInt(),
                character.getHead_view().get("top_head_view_id").getAsInt(),
                character.getHead_view().get("mid_head_view_id").getAsInt(),
                character.getHead_view().get("low_head_view_id").getAsInt(),
                action+direction,
                0
        );
    }

    public static String[] renderCharacter(
            int job, int head, int sex, int bodyPalette, int headPalette,
            int accTop, int accMid, int accLow,
            int actionId, int frameId
    ) {
        // Fix sex if sex is incorrect
        if (job == 19 || job == 4020 || job == 4042) sex = 1;
        if (job == 20 || job == 4021 || job == 4043) sex = 0;

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
            ROSprite bodySprite = null;
            ROFrame bodyFrame = null;
            ROSprite headSprite = null;

            try {
                // Body render
                if (!bodyPng.exists()) {

                    bodySprite = getBodySprite(job+"", sex+"", bodyPalette);
                    bodyFrame = bodySprite.getAnimationFrame(actionId, frameId);
                    headSprite = getHeadSprite(head+"", sex+"", headPalette);

                    // Mix elements
                    BufferedImage bodyMapLoc = new BufferedImage(150, 170, BufferedImage.TYPE_INT_ARGB);
                    Graphics gBody = bodyMapLoc.getGraphics();

                    // Draw body
                    drawElement(
                            gBody,
                            null,
                            bodySprite,
                            floorX,
                            floorY,
                            actionId,
                            frameId,
                            false
                    );

                    // Draw head
                    drawElement(
                            gBody,
                            bodyFrame,
                            headSprite,
                            floorX,
                            floorY,
                            actionId,
                            frameId,
                            false
                    );

                    /*/ reference position
                    gBody.drawLine(0, floorY, 200, floorY);
                    gBody.drawLine(floorX, 0, floorX, 200);
                    gBody.fillOval(floorX-5, floorY-5, 10,10);*/
                    
                    ImageIO.write(bodyMapLoc, "png", bodyPng);
                }

                // Acc render
                if (!accPng.exists()) {

                    ROSprite accTopFrame = null;
                    if (accTop > 0) {
                        accTopFrame = getAccSprite(accTop+"", sex+"");
                    }

                    ROSprite accMidFrame = null;
                    if (accMid > 0) {
                        accMidFrame = getAccSprite(accMid+"", sex+"");
                    }

                    ROSprite accLowFrame = null;
                    if (accLow > 0) {
                        accLowFrame = getAccSprite(accLow+"", sex+"");
                    }

                    if (bodySprite == null) {
                        bodySprite = getBodySprite(job+"", sex+"", bodyPalette);
                        bodyFrame = bodySprite.getAnimationFrame(actionId, frameId);
                    }

                    // Mix elements
                    BufferedImage accMapLoc = new BufferedImage(150, 170, BufferedImage.TYPE_INT_ARGB);
                    Graphics gAcc = accMapLoc.getGraphics();

                    // Draw accessory MID
                    drawElement(
                            gAcc,
                            bodyFrame,
                            accMidFrame,
                            floorX,
                            floorY,
                            actionId,
                            frameId,
                            false
                    );

                    // Draw accessory TOP
                    drawElement(
                            gAcc,
                            bodyFrame,
                            accTopFrame,
                            floorX,
                            floorY,
                            actionId,
                            frameId,
                            false
                    );

                    // Draw accessory LOW
                    drawElement(
                            gAcc,
                            bodyFrame,
                            accLowFrame,
                            floorX,
                            floorY,
                            actionId,
                            frameId,
                            false
                    );

                    /*/ reference position
                    gAcc.drawLine(0, floorY, 200, floorY);
                    gAcc.drawLine(floorX, 0, floorX, 200);
                    gAcc.fillOval(floorX-5, floorY-5, 10,10);*/

                    ImageIO.write(accMapLoc, "png", accPng);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logs.errorLog(CharacterController.class, "Failed to render character\n" +
                        "-> "+ bodyPngFileName +"\n" +
                        "-> "+ accPngFileName +"\n" +
                        "e-> "+ e);
            }
        }

        return new String[] {bodyPngFileName, accPngFileName};
    }

    public static String[] renderItems(Character character) {
        int weaponId = character.getShow_equip().get("weapon_id").getAsInt();
        int shieldId = character.getShow_equip().get("shield_id").getAsInt();

        String weaponFile = "";
        String shieldFile = "";
        if (weaponId > 0) {
            weaponFile = renderItem(weaponId);
        }
        if (shieldId > 0) {
            shieldFile = renderItem(shieldId);
        }

        return new String[] { weaponFile, shieldFile };
    }
    public static String renderItem(int itemId) {
        String itemFileName = "item_"+ itemId +".png";

        File itemPng = new File(ITEMS_PATH + itemFileName);

        if (!itemPng.exists()) {

            try {
                // Item Render
                ROSprite itemSprite = getItemSprite(itemId);
                //ROFrame itemFrame = itemSprite.getAnimationFrame(0, 0);

                // Mix elements
                BufferedImage itemLoc = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
                Graphics gBody = itemLoc.getGraphics();

                // Draw body
                drawElement(
                        gBody,
                        null,
                        itemSprite,
                        15,
                        15,
                        0,
                        0,
                        true
                );

                ImageIO.write(itemLoc, "png", itemPng);

            } catch (Exception e) {
                e.printStackTrace();
                Logs.errorLog(CharacterController.class, "Failed to render item\n" +
                        "-> "+ itemFileName +"\n" +
                        "e-> "+ e);
            }
        }
        return itemFileName;
    }

    private static void drawElement(
            Graphics graphics,
            ROFrame referenceFrame,
            ROSprite drawFrame,
            int floorX,
            int floorY,
            int actionId,
            int frameId,
            boolean ignorePosition
    ) {

        if (drawFrame != null) {
            ROFrame roFrame = drawFrame.getAnimationFrame(actionId, frameId);
            // Draw
            int drawFrameX = 0;
            int drawFrameY = 0;
            if (roFrame.getExtraX() != 0 || roFrame.getExtraY() != 0) {
                if (referenceFrame != null) {
                    drawFrameX = referenceFrame.getExtraX() - roFrame.getExtraX();
                    drawFrameY = referenceFrame.getExtraY() - roFrame.getExtraY();
                }
            }
            for(ROSubFrame roSubFrame : roFrame.getSubFrames()) {
                if (roSubFrame.getImage() >= 0) {
                    drawFrameX += roSubFrame.getOffSetX();
                    drawFrameY += roSubFrame.getOffSetY();
                    BufferedImage img = drawFrame.getRoSpr().getPng(roSubFrame.getSpriteVersion(), roSubFrame.getImage(), (roSubFrame.getDirection() == 1));
                    int xPos = floorX - (img.getWidth()/2);
                    if (!ignorePosition) xPos += drawFrameX;
                    int yPos = floorY - (img.getHeight()/2);
                    if (!ignorePosition) yPos += drawFrameY;
                    graphics.drawImage(img, xPos, yPos, null);
                }
            }
        }

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

    private static ROSprite getItemSprite(int itemId) throws Exception {

        String itemSpriteAct = RO_SPRITES_LOC + ITEM_LOC + itemNameProp.getProperty(itemId+"");

        URL itemSprite = classLoader.getResource(itemSpriteAct+".spr");
        URL actSprite = classLoader.getResource(itemSpriteAct+".act");

        ROSpr roSpr = new ROSpr.Builder(itemSprite).build();
        return new ROSprite(roSpr, new ROAct.Builder(actSprite).build());
    }

    public static void updateCharacterDisplay(Map<String, String[]> params, User u) {

        if (
            params.containsKey("char_bg")
            && params.containsKey("char_action")
            && params.containsKey("char_direction")
            && params.containsKey("char_id")
        ) {
            String charBg = params.get("char_bg")[0];
            int charAction = Integer.parseInt(params.get("char_action")[0]);
            int charDirection = Integer.parseInt(params.get("char_direction")[0]);
            int charId = Integer.parseInt(params.get("char_id")[0]);

            // Validation info
            if (
                    (
                        charBg.equals("")
                        || charBg.equals("prontera.jpg")
                        || charBg.equals("geffen.jpg")
                        || charBg.equals("morroc.jpg")
                        || charBg.equals("izlude.jpg")
                        || charBg.equals("aldebaran.jpg")
                        || charBg.equals("payon.jpg")
                        || charBg.equals("rachel.jpg")
                        || charBg.equals("yuno.jpg")
                    )
                        &&
                    (
                        charAction == 0
                        || charAction == 8
                        || charAction == 32
                        || charAction == 48
                        || charAction == 96
                    )
                        &&
                    (
                        charDirection == 0
                        || charDirection == 1
                        || charDirection == 7
                    )
            ) {

                if (u.hasCharacter(charId)) {
                    Map<Object, Object> inf = new HashMap<>();

                    JsonObject charDisplay = new JsonObject();
                    charDisplay.addProperty("background_bg", charBg);
                    charDisplay.addProperty("action", charAction);
                    charDisplay.addProperty("direction", charDirection);

                    inf.put("character_display", charDisplay);

                    try {
                        DBLoadObject.dbConnect.update(
                                Character.TABLE_NAME,
                                inf,
                                Character.TABLE_KEY +"= ?",
                                new String[]{charId +""}
                        );
                    } catch (Exception e) {
                        Logs.fatalLog(CharacterController.class, "Failed to upload character info "+ e);
                    }

                }
            }
        }
    }
}