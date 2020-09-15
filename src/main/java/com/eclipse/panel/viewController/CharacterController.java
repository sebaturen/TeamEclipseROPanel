package com.eclipse.panel.viewController;

import com.eclipse.panel.gameObject.character.Character;
import com.eclipse.panel.viewController.rest.Guilds;
import com.eclipse.panel.viewController.roRender.ROFrame;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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

    // JOB SPRITE INFO
    private static final int JOB_HEADER_START =  0;
    private static final int JOB_TOTAL_FRAME_START = 4;

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
        int head = 0;
        int sex = 0;
        int palette = 0;
        String jobSpriteAct = RO_SPRITES_LOC + JOB_LOC + jobsNameProp.getProperty(job+"") +"_"+ sexProp.getProperty(sex+"");

        URL jobSprite = classLoader.getResource(jobSpriteAct+".spr");
        if (jobSprite != null) {

            byte[] jobSpriteSPR;
            try (InputStream fis = jobSprite.openStream();
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

                byte[] buff = new byte[8000];
                int bytesRead = 0;

                while((bytesRead = fis.read(buff)) != -1) {
                    buffer.write(buff, 0, bytesRead);
                }

                jobSpriteSPR = buffer.toByteArray();
            }

            if (jobSpriteSPR != null) {
                byte[] bTotalFrames = reverseContent(Arrays.copyOfRange(jobSpriteSPR, JOB_TOTAL_FRAME_START, JOB_TOTAL_FRAME_START+4));
                int totalFrames = (ByteBuffer.wrap(bTotalFrames)).getInt();
                int bytePosition = JOB_TOTAL_FRAME_START+4;
                List<ROFrame> framesList = new ArrayList<>();
                while (totalFrames != 0) {
                    byte[] bSizeX = reverseContent(Arrays.copyOfRange(jobSpriteSPR, bytePosition, bytePosition+2));
                    byte[] bSizeY = reverseContent(Arrays.copyOfRange(jobSpriteSPR, bytePosition+2, bytePosition+4));
                    byte[] bByteSize = reverseContent(Arrays.copyOfRange(jobSpriteSPR, bytePosition+4, bytePosition+6));

                    short sizeX = (ByteBuffer.wrap(bSizeX)).getShort();
                    short sizeY = (ByteBuffer.wrap(bSizeY)).getShort();
                    short byteSize = (ByteBuffer.wrap(bByteSize)).getShort();
                    byte[] bSprite = Arrays.copyOfRange(jobSpriteSPR, bytePosition+6, bytePosition+6+byteSize);
                    framesList.add(new ROFrame(sizeX, sizeY, bSprite));

                    bytePosition += 6 + byteSize;
                    totalFrames--;
                }
                byte[] bPalette = Arrays.copyOfRange(jobSpriteSPR, bytePosition, jobSpriteSPR.length);
                framesList.get(0).getPng(bPalette);
                //System.out.println(convertBytesToHex(jobSpriteSPR));
            }
        }

        //FileInputStream bodySpr = new FileInputStream(Objects.requireNonNull(classLoader.getResource(jobSprite)).getFile());
        //System.out.println(bodySpr);

    }

    public static void main(String... args) throws IOException {
        renderCharacter(1);
    }

    /**
     * Convert byte[] -> String [AA BB CC ...]
     * @param bytes
     * @return
     */
    public static String convertBytesToHex(byte[] bytes) {

        StringBuilder result = new StringBuilder();

        for (byte temp : bytes) {
            int decimal = (int) temp & 0xff;  // bytes widen to int, need mask, prevent sign extension
            String hex = Integer.toHexString(decimal).toUpperCase();
            if (hex.length() == 1) {
                hex = "0"+ hex;
            }
            result.append(hex);
        }
        return result.toString();

    }

    public static byte[] reverseContent(byte[] content) {
        for(int i = 0; i < content.length / 2; i++) {
            byte t = content[i];
            content[i] = content[content.length -1 -i];
            content[content.length -1 -i] = t;
        }
        return content;
    }
}