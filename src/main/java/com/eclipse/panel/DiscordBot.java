package com.eclipse.panel;

import com.eclipse.panel.gameObject.Monster;
import com.eclipse.panel.gameObject.ROMap;
import com.eclipse.panel.gameObject.character.Character;
import com.eclipse.panel.viewController.ViewController;
import com.eclipse.panel.viewController.rest.APIKeys;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class DiscordBot {

    private static final String DISCORD_CLIENT_SECRET = "NzM5NTIyNDQ2NDExMTY5OTcy.Xybr8g.prqK1kZRfXtt603Je_7PxCyk9kc";
    private static final String SERVER_ID = "88465337883852800";
    private static final String CHANNEL_ID = "739627408486957056";
    private static final String TAG_PROFILE_ID = "744004665607454760";
    public static final String MAP_PATH = ViewController.FILEPATH +"assets/img/ro/maps/";
    public static final String MAP_POINTER_PATH = ViewController.FILEPATH +"assets/img/icons/";
    private static String lastMsg;
    private static Date lastSpawnReport;

    private Map<Integer, Monster> monstersReport = new HashMap<>();
    private Guild server;
    private MessageChannel messageChannel;
    private JDA jda;
    public static final DiscordBot shared;
    static {
        shared = DiscordBot.build();
    }

    public static DiscordBot build() {
        DiscordBot newDisc = null;
        try {
            newDisc = new DiscordBot();
            newDisc.jda = JDABuilder
                    .createDefault(DISCORD_CLIENT_SECRET)
                    .addEventListeners(new MessageListener())
                    .build();
            newDisc.jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        return newDisc;
    }

    private DiscordBot() {

    }

    private void prepareServer() {
        // Prepare server
        if (server == null || messageChannel == null) {
            try {
                jda.awaitReady();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            server = jda.getGuildById(SERVER_ID);
            assert server != null;
            messageChannel = server.getTextChannelById(CHANNEL_ID);
        }
    }

    private static class MessageListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(@NotNull MessageReceivedEvent event) {
            if (event.getChannel().getId().equals(CHANNEL_ID)) {
                Message message = event.getMessage();
                MessageChannel channel = event.getChannel();
                String msg = message.getContentDisplay();

                if ("!t".equals(msg)) {
                    MVPSheetMonitoring mvpSheetMonitoring = new MVPSheetMonitoring();
                    List<Monster> spawnList = mvpSheetMonitoring.getMonsterTimes();
                    spawnList.sort(Comparator.comparingLong(Monster::getTimestamp));

                    StringBuilder reportMsg = new StringBuilder();
                    reportMsg.append(displayMonster(spawnList));

                    channel.sendMessage(reportMsg).queue();
                }
            }
        }
    }

    public void reportMonsterLocation(JsonObject monsterData) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        APIKeys userShow = APIKeys.getValue(monsterData.get("api_key").getAsString());
        com.eclipse.panel.gameObject.Monster monster = gson.fromJson(monsterData, com.eclipse.panel.gameObject.Monster.class);

        if(monstersReport.containsKey(monster.getId())) {
            Monster oldReport = monstersReport.get(monster.getId());
            long diff = oldReport.getTimestamp() - monster.getTimestamp();
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            // Diff time
            if (diffMinutes >= 5 || diffHours >= 1 || diffDays >= 1) {
                monstersReport.remove(monster.getId());
            }

            // Diff position
            int diffX = Math.abs(oldReport.getX() - monster.getX());
            int diffY = Math.abs(oldReport.getY() - monster.getY());
            if (diffX > 30 || diffY > 30) {
                monstersReport.remove(monster.getId());
            }

            // Check if preview not have a map
            if (
                    (oldReport.getMap_name() == null || oldReport.getMap_name().isEmpty() || oldReport.getMap_name().equals("UNKNOWN!"))
                    && (monster.getMap_name() != null && monster.getMap_name().length() > 0 && !oldReport.getMap_name().equals("UNKNOWN!"))
            ){
                monstersReport.remove(monster.getId());
            }

        }
        if (!monstersReport.containsKey(monster.getId())) {

            prepareServer();
            // Prepare message:
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String charViewDetail = "";
            if (monsterData.has("character") && !monsterData.get("character").isJsonNull()) {
                JsonObject charInfo = monsterData.get("character").getAsJsonObject();
                if (charInfo != null && !charInfo.isJsonNull()) {
                    int accId = charInfo.get("account_id").getAsInt();
                    int characterId = charInfo.get("character_id").getAsInt();

                    if (characterId != 0) {
                        Character ch = new Character.Builder(characterId).build();
                        if (ch != null) {
                            charViewDetail = ch.getName();
                        } else {
                            charViewDetail = "AC: "+ accId +" | C: "+ characterId;
                        }
                    } else {
                        charViewDetail = "AC: "+ accId;
                    }
                }
            }
            String msg = monster.getMonster_name() +" ```nim\n("+ userShow +") ["+ monster.getId() +"] "+ monster.getMap_name() +" ("+ monster.getX() +","+ monster.getY() +") --> "+ df.format(monster.getTimestamp()) +" ["+ charViewDetail +"]```";

            // Prepare map view:
            try {
                BufferedImage map    = ImageIO.read(new File(MAP_PATH, monster.getMap_name()+".bmp"));
                BufferedImage marker = ImageIO.read(new File(MAP_POINTER_PATH, "mark-map.png"));

                int w = map.getWidth();
                int h = map.getHeight();
                BufferedImage mapLoc = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

                Graphics g = mapLoc.getGraphics();
                g.drawImage(map, 0, 0, null);

                int nW = 3, nH = 3;
                double ratioX = 512 / (double) ROMap.mapsSize.get(monster.getMap_name())[0];
                double ratioY = 512 / (double) ROMap.mapsSize.get(monster.getMap_name())[1];

                double nX = (monster.getX() * ratioX) - (nW /(double) 2);
                double nY = 512 - (monster.getY() * ratioY) - (nH /(double) 2);

                g.drawImage(marker, (int) nX, (int) nY, null);
                g.dispose();

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(mapLoc, "png", os);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(os.toByteArray());
                // send message
                messageChannel.sendMessage(msg).addFile(inputStream, "map_"+ monster.getId() +".png").queue();
                // add queue monster
            } catch (IOException e) {
                messageChannel.sendMessage(msg).queue();
            }
            monstersReport.put(monster.getId(), monster);

        }
    }

    public void reportDelayTime(List<Monster> spawnList) {
        spawnList.sort(Comparator.comparingLong(Monster::getTimestamp));
        if (spawnList.get(0).getTimestamp() <= 300000) {
            if (lastSpawnReport != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(lastSpawnReport);
                c.add(Calendar.MINUTE, 5);
                if ((new Date()).before(c.getTime())) {
                    return;
                }
            }
            StringBuilder msg = new StringBuilder();
            msg.append("<@&"+ TAG_PROFILE_ID +"> atentos con los siguientes respawn!");
            msg.append("\n").append(displayMonster(spawnList));

            prepareServer();
            messageChannel.sendMessage(msg).queue();
            lastSpawnReport = new Date();
        }

    }

    private static StringBuilder displayMonster(List<Monster> spawnList) {
        StringBuilder msg = new StringBuilder();
        msg.append("```nim");
        for(Monster m : spawnList) {
            long diff = m.getTimestamp();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            // Show info
            String hor = ((diffHours < 10)? "0":"")+""+ diffHours;
            String min = ((diffMinutes < 10)? "0":"")+""+ diffMinutes;
            msg
                    .append("\n - ")
                    .append("[")
                    .append(hor)
                    .append(":")
                    .append(min)
                    .append("] --> ")
                    .append(m.getMonster_name());
        }
        msg.append("\n```");

        return msg;
    }

    public void reportSystemChat(String msg) {
        if (server == null || messageChannel == null) {
            prepareServer();
        }
        if (!msg.equals(lastMsg)) {
            lastMsg = msg;
            messageChannel.sendMessage("System Chat: ```"+ msg +"```").queue();
        }
    }

    public Monster getMonsterReport(int mapMonsterId) {
        if (monstersReport.containsKey(mapMonsterId)) {
            return monstersReport.remove(mapMonsterId);
        }
        return null;
    }

}
