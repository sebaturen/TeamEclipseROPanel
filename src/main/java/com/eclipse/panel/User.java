package com.eclipse.panel;

import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Accounts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class User {

    // User DB
    public static final String TABLE_NAME = "users";
    public static final String TABLE_KEY = "id";

    // Attributes
    private int id;
    private long discord_id;
    private String nick;
    private String avatar;
    private String locale;

    // Update control
    private long last_login;
    private long create_timestamp;

    // Internal Info
    private List<Accounts> accountsList;

    public static class Builder extends DBLoadObject {

        private static final String DISCORD_API = "https://discord.com/api/v6";
        private int id;
        private String code;
        private long discordId;

        public Builder() {
            super(TABLE_NAME, User.class);
        }

        public Builder(int id) {
            super(TABLE_NAME, User.class);
            this.id = id;
        }

        public Builder(String code) {
            super(TABLE_NAME, User.class);
            this.code = code;
        }

        public Builder(long discordId) {
            super(TABLE_NAME, User.class);
            this.discordId = discordId;
        }

        public User build() {
            User newUser = null;

            if (id > 0) {
                newUser = (User) load(TABLE_KEY, id);
            }
            if (discordId > 0) {
                newUser = (User) load("discord_id", discordId);
            }
            if (code != null) {
                String clientId = GeneralConfig.getStringConfig("DISCORD_CLIENT_ID");
                String clientSecret = GeneralConfig.getStringConfig("DISCORD_CLIENT_SECRET");
                String redirectUri = GeneralConfig.getStringConfig("DISCORD_REDIRECT_URI");

                Map<String, String> body = new HashMap<>() {{
                    put("client_id", clientId);
                    put("client_secret", clientSecret);
                    put("grant_type", "authorization_code");
                    put("code", code);
                    put("redirect_uri", redirectUri);
                    put("scope", "identify");
                }};

                String form = body.keySet().stream()
                        .map(key -> key + "=" + URLEncoder.encode(body.get(key), StandardCharsets.UTF_8))
                        .collect(Collectors.joining("&"));

                try {

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(DISCORD_API +"/oauth2/token"))
                            .headers("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString(form))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    JsonObject tokenInfo = new JsonParser().parse(response.body()).getAsJsonObject();

                    if (tokenInfo.has("token_type")) {

                        request = HttpRequest.newBuilder()
                                .uri(URI.create(DISCORD_API +"/users/@me"))
                                .header("Authorization", tokenInfo.get("token_type").getAsString() +" "+ tokenInfo.get("access_token").getAsString())
                                .build();

                        response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        JsonObject userInfo = new JsonParser().parse(response.body()).getAsJsonObject();

                        JsonArray userExist = DBLoadObject.dbConnect.select(
                                User.TABLE_NAME,
                                new String[]{"id"},
                                "discord_id =?",
                                new String[]{userInfo.get("id").getAsString()}
                        );
                        if (userExist.size() > 0) {
                            newUser = new User.Builder(userInfo.get("id").getAsLong()).build();
                            newUser.nick = userInfo.get("username").getAsString();
                            newUser.avatar = userInfo.get("avatar").getAsString();
                            newUser.locale = userInfo.get("locale").getAsString();
                            newUser.updateInfo();
                        } else {
                            // Save a new user
                            newUser = new User();
                            newUser.discord_id = userInfo.get("id").getAsLong();
                            newUser.nick = userInfo.get("username").getAsString();
                            newUser.avatar = userInfo.get("avatar").getAsString();
                            newUser.locale = userInfo.get("locale").getAsString();
                            newUser.create_timestamp = new Date().getTime();
                            newUser.id = User.saveUser(newUser);

                        }

                    }
                } catch (InterruptedException | IOException | SQLException e) {
                    e.printStackTrace();
                }

            }

            return newUser;
        }
    }

    // Constructor
    public User() {

    }

    private void updateInfo() {
        Map<Object, Object> info = new HashMap<>() {{
            put("nick", nick);
            put("avatar", avatar);
            put("locale", locale);
            put("last_login", new Date().getTime());
        }};

        try {
            DBLoadObject.dbConnect.update(
                    TABLE_NAME,
                    info,
                    TABLE_KEY +"= ?",
                    new String[]{id+""}
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int saveUser(User u) {
        Map<Object, Object> info = new HashMap<>() {{
            put("nick", u.nick);
            put("discord_id", u.discord_id);
            put("avatar", u.avatar);
            put("locale", u.locale);
            put("create_timestamp", u.create_timestamp);
            put("last_login", new Date().getTime());
        }};

        int id = 0;

        try {
            id = Integer.parseInt(DBLoadObject.dbConnect.insert(
                    TABLE_NAME,
                    TABLE_KEY,
                    info
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    // GET SET
    public boolean isLogin() {
        return (id > 0);
    }

    public void copy(User u) {
        id = u.id;
        nick = u.nick;
        discord_id = u.discord_id;
        avatar = u.avatar;
        locale = u.locale;
        last_login = u.last_login;
        create_timestamp = u.create_timestamp;
    }

    public long getDiscord_id() {
        return discord_id;
    }

    public String getNick() {
        return nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLocale() {
        return locale;
    }

    public List<Accounts> getAccounts() {
        accountsList = new ArrayList<>();
        try {
            JsonArray accounts = DBLoadObject.dbConnect.select(
                    Accounts.TABLE_NAME,
                    new String[]{Accounts.TABLE_KEY},
                    "user_id = ?",
                    new String[]{id+""}
            );

            for(JsonElement acs : accounts) {
                JsonObject ac = acs.getAsJsonObject();
                accountsList.add(new Accounts.Builder(ac.get(Accounts.TABLE_KEY).getAsInt()).build());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accountsList;
    }
    @Override
    public String toString() {
        return "{\"_class\":\"User\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"discord_id\":\"" + discord_id + "\"" + ", " +
                "\"nick\":" + (nick == null ? "null" : "\"" + nick + "\"") + ", " +
                "\"avatar\":" + (avatar == null ? "null" : "\"" + avatar + "\"") + ", " +
                "\"locale\":" + (locale == null ? "null" : "\"" + locale + "\"") + ", " +
                "\"last_login\":\"" + last_login + "\"" + ", " +
                "\"create_timestamp\":\"" + create_timestamp + "\"" +
                "}";
    }
}
