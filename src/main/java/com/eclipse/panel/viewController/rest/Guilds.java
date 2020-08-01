package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.Logs;
import com.eclipse.panel.dbConnect.DBLoadObject;
import com.eclipse.panel.gameObject.woe.CastleBreaker;
import com.eclipse.panel.gameObject.Guild;
import com.eclipse.panel.gameObject.character.Character;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

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
        if (!guildData.has("api_key") || APIKeys.getValue(guildData.get("api_key").getAsString()) == APIKeys.UNKNOWN) {
            return Response.status(403).entity("Key not match").build();
        }

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

                    boolean needUpdate = false;
                    if (!guild_db.get(0).getAsJsonObject().has("name")) {
                        needUpdate = true;
                    } else {
                        String nName = guildData.get("guild_name").getAsString();
                        String name = guild_db.get(0).getAsJsonObject().get("name").getAsString();
                        if (!nName.equals(name)) {
                            needUpdate = true;
                        }
                    }

                    if (needUpdate) {

                        Map<Object, Object> inf = new HashMap<>();
                        inf.put("name", guildData.get("guild_name").getAsString());

                        DBLoadObject.dbConnect.update(
                                Guild.TABLE_NAME,
                                inf,
                                Guild.TABLE_KEY +"= ?",
                                new String[]{guildId+""}
                        );

                        Logs.infoLog(this.getClass(), "Update Guild Name [addGuildDetail]");
                        return Response.ok().entity(okInfo.toString()).build();

                    } else {
                        Logs.infoLog(this.getClass(), "Update Guild Name NOT NEED [addGuildDetail]");
                    }

                }

            }

            return Response.notModified().entity(okInfo.toString()).build();

        } catch (Exception e) {
            Logs.fatalLog(this.getClass(), "FATAL [SQLException] [addGuildDetail] -> "+ e);
        }

        return Response.serverError().entity("Failed").build();
    }

    @PUT
    @Path("/{guild_id}/emblem/{emblem_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGuildEmblem(
            @PathParam("guild_id") int guildId,
            @PathParam("emblem_id") int emblemId,
            String inputData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("guild_id", guildId);
        okInfo.addProperty("emblem_id", emblemId);

        // Save info
        JsonObject emblemData = JsonParser.parseString(inputData).getAsJsonObject();
        if (!emblemData.has("api_key") || APIKeys.getValue(emblemData.get("api_key").getAsString()) == APIKeys.UNKNOWN) {
            return Response.status(403).entity("Key not match").build();
        }

        byte[] bEmblemLogo = Base64.getDecoder().decode(emblemData.get("emblem").getAsString());
        bEmblemLogo = decompress(bEmblemLogo);

        if (bEmblemLogo.length > 0) {
            writeByte(bEmblemLogo, guildId, emblemId);
            Logs.infoLog(this.getClass(), "Guild emblem is update! ["+guildId +"/"+ emblemId +"]");
            return Response.ok().entity(okInfo.toString()).build();
        }

        return Response.notModified().entity(okInfo.toString()).build();

    }

    @PUT
    @Path("/cast/{cast_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guildBreaker(
            @PathParam("cast_id") int castId,
            String inputData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("cast", castId);

        // Save info
        JsonObject breakCast = JsonParser.parseString(inputData).getAsJsonObject();
        if (!breakCast.has("api_key") || APIKeys.getValue(breakCast.get("api_key").getAsString()) == APIKeys.UNKNOWN) {
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

        } catch (Exception e) {
            Logs.fatalLog(this.getClass(), "FAILED to input WoE Breaker info! [guildBreaker]: "+ e);
        }

        return Response.notModified().entity(okInfo.toString()).build();
    }

    public static byte[] decompress(byte[] data) {
        try {

            Inflater inflater = new Inflater();
            inflater.setInput(data);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            byte[] output = outputStream.toByteArray();

            return output;
        } catch (DataFormatException | IOException e) {
            return new byte[0];
        }
    }

    public static void writeByte(byte[] bytes, int guildId, int emblemId) {

        String FILEPATH = System.getProperty( "catalina.base" ) +"/team_eclipse/ROOT/assets/img/ro/guilds/emblems/";
        File filePng = new File(FILEPATH+"Poring_"+ guildId +"_"+ emblemId +".png");

        try {
            // BMP to PNG
            ByteArrayInputStream bInput = new ByteArrayInputStream(bytes);
            BufferedImage im;
            im = ImageIO.read(bInput);

            Image transpImg2 = TransformColorToTransparency(im, new Color(250, 0, 250), new Color(255, 10, 255));
            BufferedImage resultImage2 = ImageToBufferedImage(transpImg2, im.getWidth(), im.getHeight());

            // Initialize a pointer
            // in file using OutputStream
            // Starts writing the bytes in it
            ImageIO.write(resultImage2, "png", filePng);
        } catch (Exception e) {
            Logs.fatalLog(Guilds.class, "FAILED to save guild emblem "+ e);
        }
    }

    private static BufferedImage ImageToBufferedImage(Image image, int width, int height) {
        BufferedImage dest = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return dest;
    }

    private static Image TransformColorToTransparency(BufferedImage image, Color c1, Color c2) {
        // Primitive test, just an example
        final int r1 = c1.getRed();
        final int g1 = c1.getGreen();
        final int b1 = c1.getBlue();
        final int r2 = c2.getRed();
        final int g2 = c2.getGreen();
        final int b2 = c2.getBlue();
        ImageFilter filter = new RGBImageFilter() {
            public final int filterRGB(int x, int y, int rgb) {
                int r = (rgb & 0xFF0000) >> 16;
                int g = (rgb & 0xFF00) >> 8;
                int b = rgb & 0xFF;
                if (r >= r1 && r <= r2 &&
                        g >= g1 && g <= g2 &&
                        b >= b1 && b <= b2
                ) {
                    // Set fully transparent but keep color
                    return rgb & 0xFFFFFF;
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

}
