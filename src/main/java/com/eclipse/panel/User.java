package com.eclipse.panel;

import com.eclipse.panel.dbConnect.DBLoadObject;

public class User {

    // User DB
    public static final String TABLE_NAME = "users";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private String nick;
    private String email;
    private String hash_password;
    private boolean is_guild_member;
    private String discord_id;
    private String discord_nick;
    private long last_login;

    // Update control
    private long create_timestamp;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int id) {
            super(TABLE_NAME, User.class);
            this.id = id;
        }

        public User build() {
            User u = (User) load(TABLE_KEY, id);
            // Load accounts (?)
            return u;
        }

    }

    public User() {

    }

    // GET SET
    public boolean isIs_guild_member() {
        return is_guild_member;
    }

    public boolean isLogin() {
        return (id > 0);
    }

    @Override
    public String toString() {
        return "{\"_class\":\"User\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"nick\":" + (nick == null ? "null" : "\"" + nick + "\"") + ", " +
                "\"email\":" + (email == null ? "null" : "\"" + email + "\"") + ", " +
                "\"hash_password\":" + (hash_password == null ? "null" : "\"" + hash_password + "\"") + ", " +
                "\"discord_id\":" + (discord_id == null ? "null" : "\"" + discord_id + "\"") + ", " +
                "\"discord_nick\":" + (discord_nick == null ? "null" : "\"" + discord_nick + "\"") + ", " +
                "\"last_login\":\"" + last_login + "\"" + ", " +
                "\"create_timestamp\":\"" + create_timestamp + "\"" +
                "}";
    }
}
