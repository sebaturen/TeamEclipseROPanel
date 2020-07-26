package com.eclipse.panel.viewController.rest;


import com.eclipse.panel.Logs;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Accounts;
import com.eclipse.panel.gameObject.Guild;
import com.eclipse.panel.gameObject.character.Character;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.text.View;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Path("/characters")
public class Characters {

    @PUT
    @Path("/{account_id}/{character_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCharacterDetail(
            @PathParam("account_id") int accId,
            @PathParam("character_id") int charId,
            String inputData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("char_id", charId);
        //okInfo.addProperty("pj_data", pjData);

        // Save info
        JsonObject pjData = JsonParser.parseString(inputData).getAsJsonObject();

        // Check is account previously exist:
        try {
            if (pjData.get("name").toString().length() > 0) {
                createAccount(accId);
                if (pjData.has("guild_id") && pjData.get("guild_id").getAsInt() != 0) {
                    createUpdateGuild(pjData);
                }
                createUpdateCharacter(pjData);
            }
            return Response.ok().entity(okInfo.toString()).build();
        } catch (Exception e) {
            Logs.fatalLog(this.getClass(), "FATAL Add Character Detail is failed -> "+ e);
            //e.printStackTrace();
        }

        return Response.serverError().entity("Failed").build();

    }

    private void createAccount(int accId) throws Exception {

        try {

            JsonArray account_db = DBLoadObject.dbConnect.select(
                    Accounts.TABLE_NAME,
                    new String[] {"timestamp"},
                    Accounts.TABLE_KEY +" = ?",
                    new String[] {accId+""}
            );

            boolean isInDb = (account_db.size() > 0);

            if (!isInDb) {
                try {
                    DBLoadObject.dbConnect.insert(
                            Accounts.TABLE_NAME,
                            Accounts.TABLE_KEY,
                            new String[]{"id", "timestamp"},
                            new String[]{accId+"", new Date().getTime()+""}
                    );
                    Logs.infoLog(this.getClass(), "Create account success ["+ accId +"]");
                } catch (Exception e) {
                    Logs.fatalLog(this.getClass(), "FATAL Create/Update account [Exception] [createAccount] -> "+ e);
                    throw new Exception("Process not completed");
                }
            }

        } catch (SQLException e) {
            Logs.fatalLog(this.getClass(), "FATAL Create/Update create Account [SQLException] [createAccount] -> "+ e);
            throw new Exception("Process not completed");
        }

    }

    private void createUpdateCharacter(JsonObject pjData) throws Exception {

        try {

            JsonArray character_db = DBLoadObject.dbConnect.select(
                    Character.TABLE_NAME,
                    new String[] {"last_update", "guild_id"},
                    Character.TABLE_KEY +"=?",
                    new String[] { pjData.get("character_id").getAsString() }
            );

            boolean isInDb = (character_db.size() > 0);
            long lastUpdate = 0L;
            if (character_db.size() > 0) {
                lastUpdate = character_db.get(0).getAsJsonObject().get("last_update").getAsLong();
            }

            JsonObject showEquip = new JsonObject();
            showEquip.addProperty("weapon_id", pjData.get("weapon_id").getAsInt());
            showEquip.addProperty("shield_id", pjData.get("shield_id").getAsInt());

            JsonObject headView = new JsonObject();
            headView.addProperty("low_head_view_id", pjData.get("low_head_view_id").getAsInt());
            headView.addProperty("top_head_view_id", pjData.get("top_head_view_id").getAsInt());
            headView.addProperty("mid_head_view_id", pjData.get("mid_head_view_id").getAsInt());

            JsonObject characterView = new JsonObject();
            characterView.addProperty("hair_style_id", pjData.get("hair_style_id").getAsInt());
            characterView.addProperty("hair_color_id", pjData.get("hair_color_id").getAsInt());
            characterView.addProperty("clothes_color_id", pjData.get("clothes_color_id").getAsInt());

            Map<Object, Object> info = new HashMap<>();
            info.put("id", pjData.get("character_id").getAsString());
            info.put("account_id", pjData.get("account_id").getAsString());
            info.put("name", pjData.get("name").getAsString());
            info.put("job_id", pjData.get("job_id").getAsString());
            info.put("lvl", pjData.get("lvl").getAsString());
            info.put("sex", pjData.get("sex").getAsString());
            info.put("show_equip", showEquip.toString());
            info.put("head_view", headView.toString());
            info.put("character_view", characterView.toString());
            info.put("last_update", new Date().getTime()+"");

            if (pjData.has("guild_id") && pjData.get("guild_id").getAsInt() > 0) {
                info.put("guild_id", pjData.get("guild_id").getAsString());
            } else {
                info.put("guild_id", null);
            }

            if (!isInDb) {

                info.put("timestamp", new Date().getTime()+"");

                try {

                    DBLoadObject.dbConnect.insert(
                            Character.TABLE_NAME,
                            Character.TABLE_KEY,
                            info
                    );
                    Logs.infoLog(this.getClass(), "Create Character success ["+ pjData.get("character_id") +"]");

                } catch (Exception e) {
                    Logs.fatalLog(this.getClass(), "FATAL [Exception] [createUpdateCharacter] -> "+ e);
                    throw new Exception("Process not completed");
                }

            } else {

                DBLoadObject.dbConnect.update(
                        Character.TABLE_NAME,
                        info,
                        Character.TABLE_KEY +"=?",
                        new String[] { pjData.get("character_id").getAsString() }
                );
                Logs.infoLog(this.getClass(), "Update Character success ["+ pjData.get("character_id") +"]");

            }

        } catch (SQLException e) {
            Logs.fatalLog(this.getClass(), "FATAL Update/Create Character [SQLException] [createUpdateCharacter] -> "+ e);
            throw new Exception("Process not completed");
        }

    }

    private void createUpdateGuild(JsonObject pjInfo) throws Exception {

        try {
            JsonArray guild_db = DBLoadObject.dbConnect.select(
                    Guild.TABLE_NAME,
                    new String[] {Guild.TABLE_KEY},
                    Guild.TABLE_KEY +" = ?",
                    new String[] {pjInfo.get("guild_id").getAsString()}
            );

            boolean isInDb = (guild_db.size() > 0);

            if (!isInDb) { // Insert

                DBLoadObject.dbConnect.insert(
                        Guild.TABLE_NAME,
                        Guild.TABLE_KEY,
                        new String[] {
                                "id",
                                "emblem_id",
                                "timestamp"
                        },
                        new String[] {
                                pjInfo.get("guild_id").getAsString(),
                                pjInfo.get("emblem_id").getAsString(),
                                new Date().getTime()+""
                        }
                );
                Logs.infoLog(this.getClass(), "Create Guild success ["+ pjInfo.get("guild_id") +"]");

            } else {

                Map<Object, Object> inf = new HashMap<>();
                inf.put("emblem_id", pjInfo.get("emblem_id").getAsString());

                DBLoadObject.dbConnect.update(
                        Guild.TABLE_NAME,
                        inf,
                        Guild.TABLE_KEY +" = ?",
                        new String[]{pjInfo.get("guild_id").getAsString()}
                );
                Logs.infoLog(this.getClass(), "Update Guild success ["+ pjInfo.get("guild_id") +"]");

            }

        } catch (Exception e) {
            Logs.fatalLog(this.getClass(), "FATAL Create/Update Guild [SQLException] [createUpdateGuild] -> "+ e);
            throw new Exception("Process not completed");
        }

    }
}
