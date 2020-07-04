package com.eclipse.panel.gameObject.roDB;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.google.gson.JsonElement;

public class Skill {

    // Skill DB
    public static final String TABLE_NAME = "skills_db";
    public static final String TABLE_KEY = "ro_id";

    // Attributes
    private int ro_id;
    private int class_id;
    private String name;
    private String description;
    private JsonElement dependence;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int ro_id) {
            super(TABLE_NAME, Skill.class);
            this.id = ro_id;
        }

        public Skill build() {
            return (Skill) load(TABLE_KEY, id);
        }

    }

    private Skill() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"Skill\", " +
                "\"ro_id\":\"" + ro_id + "\"" + ", " +
                "\"class_id\":\"" + class_id + "\"" + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"description\":" + (description == null ? "null" : "\"" + description + "\"") + ", " +
                "\"dependence\":" + (dependence == null ? "null" : dependence) +
                "}";
    }
}
