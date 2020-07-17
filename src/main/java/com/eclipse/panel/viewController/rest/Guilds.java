package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.Logs;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Guild;
import com.eclipse.panel.gameObject.character.Character;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Path("/guilds")
public class Guilds {

    @PUT
    @Path("/{account_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGuildDetail(
        @PathParam("account_id") int accId,
        String inputData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("account_id", accId);

        // Save info
        JsonObject guildData = JsonParser.parseString(inputData).getAsJsonObject();

        // Get guild ID buy character
        try {

            JsonArray character_db = DBLoadObject.dbConnect.select(
                    Character.TABLE_NAME,
                    new String[] {"guild_id"},
                    "account_id = ? AND name = ?",
                    new String[] { accId+"", guildData.get("character_name").getAsString() }
            );

            long guildId = 0L;
            if (character_db.size() > 0) {
                guildId = character_db.get(0).getAsJsonObject().get("guild_id").getAsLong();
            }

            if (guildId != 0) { // Have guild ID

                JsonArray guild_db = DBLoadObject.dbConnect.select(
                        Guild.TABLE_NAME,
                        new String[] {"id", "name"},
                        Guild.TABLE_KEY +" = ?",
                        new String[] {guildId+""}
                );

                boolean isInDb = (guild_db.size() > 0);

                if (!isInDb) { // Not exist guild in DB

                    DBLoadObject.dbConnect.insert(
                            Guild.TABLE_NAME,
                            Guild.TABLE_KEY,
                            new String[] {
                                    "id",
                                    "name",
                                    "timestamp"
                            },
                            new String[] {
                                    guildId+"",
                                    guildData.get("guild_name").getAsString(),
                                    new Date().getTime()+""
                            }
                    );

                    return Response.ok().entity(okInfo.toString()).build();

                } else {

                    if (!guild_db.get(0).getAsJsonObject().has("name")) {

                        Map<Object, Object> inf = new HashMap<>();
                        inf.put("name", guildData.get("guild_name").getAsString());

                        DBLoadObject.dbConnect.update(
                                Guild.TABLE_NAME,
                                inf,
                                Guild.TABLE_KEY +"= ?",
                                new String[]{guildId+""}
                        );

                        return Response.ok().entity(okInfo.toString()).build();

                    }

                }

            }

            return Response.notModified().entity(okInfo.toString()).build();

        } catch (Exception e) {
            Logs.fatalLog(this.getClass(), "FATAL [SQLException] [addGuildDetail] -> "+ e);
        }

        return Response.serverError().entity("Failed").build();
    }

}
