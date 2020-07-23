package com.eclipse.panel.gameObject.character;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.roDB.Skill;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

public class CharacterInfo {

    // Character DB
    public static final String TABLE_NAME = "character_info";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private int character_id;
    private int job_lvl;
    private int stat_str;
    private int stat_agi;
    private int stat_vit;
    private int stat_int;
    private int stat_dex;
    private int stat_luk;
    private JsonObject character_skills;

    // Update control
    private long last_update;

    // Internal data
    private List<Skill> skills;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int id) {
            super(TABLE_NAME, CharacterInfo.class);
            this.id = id;
        }

        public CharacterInfo build() {
            CharacterInfo ci = (CharacterInfo) load(TABLE_KEY, id);
            // Load playable class
            // Load sex
            // Load Skills
            return ci;
        }
    }

    private CharacterInfo() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"CharacterInfo\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"character_id\":\"" + character_id + "\"" + ", " +
                "\"job_lvl\":\"" + job_lvl + "\"" + ", " +
                "\"stat_str\":\"" + stat_str + "\"" + ", " +
                "\"stat_agi\":\"" + stat_agi + "\"" + ", " +
                "\"stat_vit\":\"" + stat_vit + "\"" + ", " +
                "\"stat_int\":\"" + stat_int + "\"" + ", " +
                "\"stat_dex\":\"" + stat_dex + "\"" + ", " +
                "\"stat_luk\":\"" + stat_luk + "\"" + ", " +
                "\"character_skills\":" + (character_skills == null ? "null" : character_skills) + ", " +
                "\"last_update\":\"" + last_update + "\"" + ", " +
                "\"skills\":" + (skills == null ? "null" : Arrays.toString(skills.toArray())) +
                "}";
    }
}
