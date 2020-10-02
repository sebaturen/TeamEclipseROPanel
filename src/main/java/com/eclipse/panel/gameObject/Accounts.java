package com.eclipse.panel.gameObject;

import com.eclipse.panel.User;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.character.Character;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Accounts {

    // Accounts DB
    public static final String TABLE_NAME = "accounts";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private int user_id;

    // Internal data
    private User user;
    private List<Character> charactersList = new ArrayList<>();

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

    public List<Character> getCharacters() {
        charactersList = new ArrayList<>();
        try {
            JsonArray characters = DBLoadObject.dbConnect.select(
                    Character.TABLE_NAME,
                    new String[]{Character.TABLE_KEY},
                    "account_id = ?",
                    new String[]{id+""}
            );

            for(JsonElement chars : characters) {
                JsonObject roChar = chars.getAsJsonObject();
                System.out.println(roChar);
                charactersList.add(new Character.Builder(roChar.get(Character.TABLE_KEY).getAsInt()).build());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return charactersList;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"Accounts\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"user_id\":\"" + user_id + "\"" + ", " +
                "\"timestamp\":\"" + timestamp + "\"" +
                "}";
    }
}
