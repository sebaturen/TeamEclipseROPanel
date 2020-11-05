package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.Logs;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Accounts;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/system")
public class General {

    public static final String TABLE_NAME_SYSTEM_CHAT = "system_chat";
    public static final String TABLE_KEY_SYSTEM_CHAT = "id";

    @POST
    @Path("/chat")
    @Produces(MediaType.APPLICATION_JSON)
    public Response linkAccount(
            String inputData
    ) {

        // Save info
        JsonObject systemChat = JsonParser.parseString(inputData).getAsJsonObject();
        if (!systemChat.has("api_key") || systemChat.get("api_key").isJsonNull()) {
            return Response.status(403).entity("Key not match").build();
        }
        if (APIKeys.getValue(systemChat.get("api_key").getAsString()) != APIKeys.WOE_KEY_AUTH) {
            return Response.status(403).entity("Key not auth").build();
        }


        try {
            DBLoadObject.dbConnect.insert(
                    TABLE_NAME_SYSTEM_CHAT,
                    TABLE_KEY_SYSTEM_CHAT,
                    new String[]{"chat", "timestamp"},
                    new String[]{systemChat.get("chat").getAsString(), systemChat.get("timestamp").getAsLong()+""}
            );
            Logs.infoLog(this.getClass(), "["+ APIKeys.getValue(systemChat.get("api_key").getAsString()) +"] System Chat: "+ systemChat.get("chat"));
            return Response.ok().build();
        } catch (Exception e) {
            Logs.fatalLog(this.getClass(), "["+ APIKeys.getValue(systemChat.get("api_key").getAsString()) +"] FATAL Add system chat -> "+ e);
            return Response.serverError().build();
        }

    }
}
