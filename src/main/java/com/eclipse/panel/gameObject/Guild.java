package com.eclipse.panel.gameObject;

import com.eclipse.panel.Logs;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.character.Character;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Guild {

    // Character DB
    public static final String TABLE_NAME = "guilds";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private String name;
    private int emblem_id;
    private int recaller_id;

    // Internal data
    private List<Character> characterList;
    private Character recaller;

    // Update control
    private long timestamp;

    public static class Builder extends DBLoadObject {

        private int id;
        private boolean loadStatus = false;

        public Builder(int guild_id) {
            super(TABLE_NAME, Guild.class);
            this.id = guild_id;
        }

        public Builder fullLoad(boolean loadStatus) {
            this.loadStatus = loadStatus;
            return this;
        }

        public Guild build() {
            Guild g = (Guild) load(TABLE_KEY, id);
            if (loadStatus) {
                g.loadCharacters();
                g.loadRecaller();
            }
            return g;
        }

    }

    private Guild() {

    }

    public void loadCharacters() {

        characterList = new ArrayList<>();
        try {
            JsonArray characters_db = DBLoadObject.dbConnect.select(
                    Character.TABLE_NAME,
                    new String[]{Character.TABLE_KEY},
                    "guild_id=? ORDER BY lvl DESC, job_id DESC",
                    new String[]{id+""}
            );

            if (characters_db.size() > 0) {
                for(JsonElement character : characters_db) {
                    JsonObject characterDetail = character.getAsJsonObject();
                    characterList.add(new Character.Builder(characterDetail.get("id").getAsInt()).build());
                }
            } else {
                Logs.infoLog(this.getClass(), "Guild not have a characters ["+ id +"]");
            }
        } catch (SQLException e) {
            Logs.fatalLog(this.getClass(), "FAILED to get a guild Characters ["+ id +"] - "+ e);
        }
    }

    public void loadRecaller() {
        if (recaller_id > 0 && recaller == null) {
            recaller = new Character.Builder(recaller_id).build();
        }
    }

    public String getName() {
        return name;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public int getId() {
        return id;
    }

    public int getEmblem_id() {
        return emblem_id;
    }

    public Character getRecaller() {
        return recaller;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"Guild\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"emblem_id\":\"" + emblem_id + "\"" + ", " +
                "\"recaller_id\":\"" + recaller_id + "\"" + ", " +
                "\"characterList\":" + (characterList == null ? "null" : Arrays.toString(characterList.toArray())) + ", " +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}";
    }
}
