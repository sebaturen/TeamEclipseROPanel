package com.eclipse.panel.gameObject.character;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Accounts;
import com.eclipse.panel.gameObject.roDB.Item;
import com.eclipse.panel.gameObject.roDB.PlayableJob;
import com.eclipse.panel.gameObject.roDB.PlayableSex;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

public class Character {

    // Character DB
    public static final String TABLE_NAME = "characters";
    public static final String TABLE_KEY = "id";
    public static final String TABLE_NAME_GUILD_HISTORY = "character_guild_history";
    public static final String TABLE_KEY_GUILD_HISTORY = "id";

    // Attributes
    private int id;
    private int account_id;
    private String name;
    private int job_id;
    private int guild_id;
    private int lvl;
    private int sex;
    private JsonObject show_equip;
    private JsonObject head_view;
    private JsonObject character_view;

    // Internal data
    private Accounts account;
    private List<Item> showEquip;
    private PlayableJob playableJob;
    private PlayableSex playableSex;

    // Update control
    private long timestamp;
    private long last_update;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int ro_id) {
            super(TABLE_NAME, Character.class);
            this.id = ro_id;
        }

        public Character build() {
            Character c = (Character) load(TABLE_KEY, id);
            // Load info (?)
            return c;
        }

    }

    private Character() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getJob_id() {
        return job_id;
    }

    public char getSex() {
        if (sex == 0) {
            return 'f';
        } else {
            return 'm';
        }
    }

    public int getSexId() {
        return sex;
    }

    public int getLvl() {
        return lvl;
    }

    public int getHairStyle() {
        return character_view.get("hair_style_id").getAsInt();
    }

    public JsonObject getShow_equip() {
        return show_equip;
    }

    public JsonObject getHead_view() {
        return head_view;
    }

    public JsonObject getCharacter_view() {
        return character_view;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"Character\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"account_id\":\"" + account_id + "\"" + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"job_id\":\"" + job_id + "\"" + ", " +
                "\"guild_id\":\"" + guild_id + "\"" + ", " +
                "\"lvl\":\"" + lvl + "\"" + ", " +
                "\"sex\":\"" + sex + "\"" + ", " +
                "\"show_equip\":" + (show_equip == null ? "null" : show_equip) + ", " +
                "\"head_view\":" + (head_view == null ? "null" : head_view) + ", " +
                "\"character_view\":" + (character_view == null ? "null" : character_view) + ", " +
                "\"account\":" + (account == null ? "null" : account) + ", " +
                "\"showEquip\":" + (showEquip == null ? "null" : Arrays.toString(showEquip.toArray())) + ", " +
                "\"playableJob\":" + (playableJob == null ? "null" : playableJob) + ", " +
                "\"playableSex\":" + (playableSex == null ? "null" : playableSex) + ", " +
                "\"timestamp\":\"" + timestamp + "\"" + ", " +
                "\"last_update\":\"" + last_update + "\"" +
                "}";
    }

}
