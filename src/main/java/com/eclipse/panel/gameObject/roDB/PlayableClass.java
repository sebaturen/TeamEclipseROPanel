package com.eclipse.panel.gameObject.roDB;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class PlayableClass {

    // Playable Class DB
    public static final String TABLE_NAME = "playable_class";
    public static final String TABLE_KEY = "ro_id";

    // Attributes
    private int ro_id;
    private String name;
    private int father;
    private JsonArray skills;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int ro_id) {
            super(TABLE_NAME, PlayableClass.class);
            this.id = ro_id;
        }

        public PlayableClass build() {
            return (PlayableClass) load(TABLE_KEY, id);
        }

    }

    private PlayableClass() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"PlayableClass\", " +
                "\"ro_id\":\"" + ro_id + "\"" + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"pather\":\"" + father + "\"" + ", " +
                "\"skills\":" + (skills == null ? "null" : skills) +
                "}";
    }
}
