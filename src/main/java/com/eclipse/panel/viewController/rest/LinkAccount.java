package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.Logs;
import com.eclipse.panel.User;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.Accounts;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/link")
public class LinkAccount {

    @PUT
    @Path("/{account_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response linkAccount(
            @PathParam("account_id") int accId,
            String inputData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("account_id", accId);

        // Save info
        JsonObject acData = JsonParser.parseString(inputData).getAsJsonObject();
        if (!acData.has("api_key") || APIKeys.getValue(acData.get("api_key").getAsString()) == APIKeys.UNKNOWN) {
            return Response.status(403).entity("Key not match").build();
        }

        Logs.warningLog(this.getClass(), "["+ APIKeys.getValue(acData.get("api_key").getAsString()) +"] CHECK "+ acData.toString());
        Accounts ac = new Accounts.Builder(accId).build();
        if (ac != null && ac.getUser_id() == 0 && acData.has("chat_name")) {
            User u = new User.Builder(acData.get("chat_name").getAsString(), true).build();
            if (u != null) {
                Map<Object, Object> inf = new HashMap<>();
                inf.put("user_id", u.getId());
                try {
                    DBLoadObject.dbConnect.update(
                            Accounts.TABLE_NAME,
                            inf,
                            Accounts.TABLE_KEY +" = ?",
                            new String[] { accId +"" }
                    );
                    return Response.ok().entity(okInfo.toString()).build();
                } catch (Exception e) {
                    Logs.fatalLog(this.getClass(), "["+ APIKeys.getValue(acData.get("api_key").getAsString()) +"] FATAL [Exception] [linkAccount] -> "+ e);
                }
            }
            return Response.status(Response.Status.NOT_FOUND).entity(okInfo.toString()).build();
        } else {
            return Response.notModified().entity(okInfo.toString()).build();
        }

    }
}
