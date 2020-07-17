package com.eclipse.panel.gameObject;

import com.eclipse.panel.User;
import com.eclipse.panel.dbConnect.DBLoadObject;

public class Accounts {

    // Accounts DB
    public static final String TABLE_NAME = "accounts";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int ro_id;
    private int user_id;

    // Internal data
    private User user;

    // Update control
    private long timestamp;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int ro_id) {
            super(TABLE_NAME, Accounts.class);
            this.id = ro_id;
        }

        public Accounts build() {
            Accounts a = null;
            if (id > 0) {
                a = (Accounts) load(TABLE_KEY, id);
                // Load characters (?)
            }
            return a;
        }
    }

    private Accounts() {

    }

    @Override
    public String toString() {
        return "{\"_class\":\"Accounts\", " +
                "\"ro_id\":\"" + ro_id + "\"" + ", " +
                "\"user_id\":\"" + user_id + "\"" + ", " +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}";
    }
}
