package com.eclipse.panel.gameObject.character;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Accounts;

public class Character {

    // Character DB
    public static final String TABLE_NAME = "characters";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int ro_id;
    private String name;
    private int account_id;

    // Internal data
    private Accounts account;

    // Update control
    private long timestamp;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int ro_id) {
            super(TABLE_NAME, Character.class);
            this.id = ro_id;
        }

        public Character build() {
            Character c = null;
            c = (Character) load(TABLE_KEY, id);
            // Load info (?)
            return c;
        }
    }

    private Character() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"Character\", " +
                "\"ro_id\":\"" + ro_id + "\"" + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") + ", " +
                "\"account_id\":\"" + account_id + "\"" + ", " +
                "\"account\":" + (account == null ? "null" : account) + ", " +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}";
    }
}
