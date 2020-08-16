package com.eclipse.panel;

import com.eclipse.panel.gameObject.Monster;
import com.eclipse.panel.gameObject.ROMap;
import com.eclipse.panel.viewController.ViewController;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.AttachmentOption;
import okhttp3.EventListener;

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

    private Map<Integer, Monster> monstersReport = new HashMap<>();
    private Date lastSpawnReport;
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


    public void reportMonsterLocation(Monster monster) {
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
                    (oldReport.getMap_name() == null || oldReport.getMap_name().isEmpty())
                    && (monster.getMap_name() != null && monster.getMap_name().length() > 0)
            ){
                monstersReport.remove(monster.getId());
            }

        }
        if (!monstersReport.containsKey(monster.getId())) {

            prepareServer();
            // Prepare message:
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String msg = monster.getMonster_name() +" ```["+ monster.getId() +"] "+ monster.getMap_name() +" ("+ monster.getX() +","+ monster.getY() +") --> "+ df.format(monster.getTimestamp()) +"```";

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
                if ((new Date()).after(c.getTime())) {
                    return;
                }
            }
            StringBuilder msg = new StringBuilder();
            msg.append("Oe! <@&"+ TAG_PROFILE_ID +"> atentos con los siguientes respawn!");
            msg.append("\n```nim");
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

            prepareServer();
            messageChannel.sendMessage(msg).queue();
            lastSpawnReport = new Date();
        }

    }

    public Monster getMonsterReport(int mapMonsterId) {
        if (monstersReport.containsKey(mapMonsterId)) {
            return monstersReport.get(mapMonsterId);
        }
        return null;
    }
}
