package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.Logs;
import com.eclipse.panel.gameObject.woe.CastleBreaker;
import com.eclipse.panel.viewController.GuildController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.List;

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
