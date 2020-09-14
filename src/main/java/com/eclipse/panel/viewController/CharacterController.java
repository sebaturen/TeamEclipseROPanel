package com.eclipse.panel.viewController;

import com.eclipse.panel.gameObject.character.Character;
import com.eclipse.panel.viewController.rest.Guilds;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class CharacterController {

    private static final String RO_SPRITES_LOC = "ro_sprites/";
    private static final String HEADS_PROPERTIES = "head_id.properties";
    private static final String JOB_NAME_PROPERTIES = "job_names.properties";
    private static final String SEX_PROPERTIES = "sex_id.properties";
    private static final Properties heads = new Properties();
    private static final Properties jobsName = new Properties();
    private static final Properties sex = new Properties();
    private static ClassLoader classLoader;

    static {
        try {
            classLoader = CharacterController.class.getClassLoader();
            heads.load(new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+HEADS_PROPERTIES)).getFile()));
            jobsName.load(new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+JOB_NAME_PROPERTIES)).getFile()));
            sex.load(new FileInputStream(Objects.requireNonNull(classLoader.getResource(RO_SPRITES_LOC+SEX_PROPERTIES)).getFile()));
        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void renderCharacter(int charId) throws FileNotFoundException {
        //Character charPlayer = new Character.Builder(charId).build();

        int job = 0;
        int head = 0;
        int sex = 0;

        System.out.println("초보자_남");
        System.out.println(jobsName);
        System.out.println(jobsName.getProperty(job+""));
        FileInputStream bodySpr = new FileInputStream(Objects.requireNonNull(classLoader.getResource("ro_sprites/body/초보자_남.act")).getFile());
        System.out.println(bodySpr);

    }

    public static void main(String... args) throws FileNotFoundException {
        renderCharacter(1);
    }

}