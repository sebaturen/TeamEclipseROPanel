package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.Logs;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Guild;
import com.eclipse.panel.gameObject.woe.CastleBreaker;
import com.eclipse.panel.viewController.GuildController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/woe")
public class WoE {

    @GET
    @Path("/breaker/{time}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response woeBreaker(
            @PathParam("time") String time
    ) {
        try {
            List<CastleBreaker> castleBreakerList = GuildController.getBreakers(time);
            JsonObject breaksCities = new JsonObject();

            for(CastleBreaker castBreak : castleBreakerList) {
                String mapName = castBreak.getCastle().getMap().getSlug();
                int castNumber = castBreak.getCastle().getCast_number();
                if (breaksCities.has(mapName)) {
                    if (breaksCities.get(mapName).getAsJsonObject().has("castle_"+ castNumber)) {

                        breaksCities
                                .get(mapName)
                                .getAsJsonObject()
                                .get("castle_"+ castNumber)
                                .getAsJsonObject()
                                .get("break_history")
                                .getAsJsonArray()
                                .add(getBreakCastleInfo(castBreak));

                    } else {

                        JsonObject castles = new JsonObject();
                        castles.addProperty("castle_map_name", castBreak.getCastle().getCastle_map_name());
                        castles.addProperty("castle_name", castBreak.getCastle().getName());

                        JsonArray breakHistory = new JsonArray();
                        breakHistory.add(getBreakCastleInfo(castBreak));
                        castles.add("break_history", breakHistory);


                        breaksCities.get(mapName).getAsJsonObject().add("castle_"+ castNumber, castles);
                    }
                } else {
                    // Map detail
                    JsonObject mapDetail = new JsonObject();
                    mapDetail.addProperty("map_name", castBreak.getCastle().getMap().getName());
                    mapDetail.addProperty("map_slug", castBreak.getCastle().getMap().getSlug());

                    JsonObject castles = new JsonObject();
                    castles.addProperty("castle_map_name", castBreak.getCastle().getCastle_map_name());
                    castles.addProperty("castle_name", castBreak.getCastle().getName());

                    JsonArray breakHistory = new JsonArray();
                    breakHistory.add(getBreakCastleInfo(castBreak));
                    castles.add("break_history", breakHistory);

                    mapDetail.add("castle_"+ castNumber, castles);

                    breaksCities.add(mapName, mapDetail);

                }
            }


            return Response.ok().entity(breaksCities.toString()).build();
        } catch (ParseException e) {
            Logs.fatalLog(this.getClass(), "FAILED to get a castle breaker in time ["+ time +"]");
        }

        return Response.serverError().build();
    }

    @POST
    @Path("/break/cast/{cast_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guildBreaker(
            @PathParam("cast_id") int castId,
            String inputData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("cast", castId+"");

        // Save info
        JsonObject breakCast = JsonParser.parseString(inputData).getAsJsonObject();
        if (!breakCast.has("api_key") || APIKeys.getValue(breakCast.get("api_key").getAsString()) != APIKeys.WOE_KEY_AUTH) {
            return Response.status(403).entity("Key not match").build();
        }

        // Get guild id
        try {

            JsonArray guild_db = DBLoadObject.dbConnect.select(
                    Guild.TABLE_NAME,
                    new String[] {"id"},
                    "name = ? ORDER BY id DESC",
                    new String[] {breakCast.get("guild_name").getAsString()}
            );

            Map<Object, Object> info = new HashMap<>();
            info.put("cast_id", castId);
            info.put("timestamp", breakCast.get("timestamp"));

            if (guild_db.size() > 0) {
                info.put("guild_id", guild_db.get(0).getAsJsonObject().get("id").getAsInt());
            } else {
                info.put("guild_temp_name", breakCast.get("guild_name").getAsString());
            }

            DBLoadObject.dbConnect.insert(
                    CastleBreaker.TABLE_NAME,
                    CastleBreaker.TABLE_KEY,
                    info
            );

            Logs.infoLog(this.getClass(), "Add CAST Breaker! ["+ castId +"]: ["+ breakCast.get("guild_name") +"]");

            return Response.ok().build();

        } catch (Exception e) {
            Logs.fatalLog(this.getClass(), "FAILED to input WoE Breaker info! [guildBreaker]: "+ e);
        }

        return Response.notModified().entity(okInfo.toString()).build();
    }

    private JsonObject getBreakCastleInfo(CastleBreaker cb) {
        JsonObject breakCastle = new JsonObject();

        if (cb.getGuild() != null) {

            breakCastle.addProperty("guild_id", cb.getGuild().getId());
            breakCastle.addProperty("guild_name", cb.getGuild().getName());
            breakCastle.addProperty("guild_emblem", cb.getGuild().getEmblem_id());
            breakCastle.addProperty("timestamp_break", cb.getTimestamp());

        } else {

            breakCastle.addProperty("guild_id", -1);
            breakCastle.addProperty("guild_name", cb.getGuild_temp_name());
            breakCastle.addProperty("guild_emblem", -1);
            breakCastle.addProperty("timestamp_break", cb.getTimestamp());

        }

        return breakCastle;

    }
}
