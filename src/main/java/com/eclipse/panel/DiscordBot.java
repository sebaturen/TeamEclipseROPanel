package com.eclipse.panel;

import com.eclipse.panel.gameObject.Monster;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordBot {

    private static final String DISCORD_CLIENT_SECRET = "NzM5NTIyNDQ2NDExMTY5OTcy.Xybr8g.prqK1kZRfXtt603Je_7PxCyk9kc";
    private static final String SERVER_ID = "494163318932504576";
    public static final String MAP_PATH = ViewController.FILEPATH +"assets/img/ro/maps/";
    public static final String MAP_POINTER_PATH = ViewController.FILEPATH +"assets/img/icons/";

    private Map<Integer, Monster> monstersReport = new HashMap<>();
    private Guild server;
    public static final DiscordBot shared = new DiscordBot().build();
    private JDA jda;

    public DiscordBot build() {
        try {
            jda = JDABuilder.createDefault(DISCORD_CLIENT_SECRET)
                    .build();
            imInStatus();
            //server = jda.getGuildById(SERVER_ID);
        } catch (LoginException e) {
            e.printStackTrace();
        }

        return this;
    }

    private DiscordBot() {

    }

    private void imInStatus() {

        try {
            MessageChannel channel = jda.getTextChannelsByName("lupita-channel", false).get(0);
            channel.sendMessage("Volvi!").queue();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No hay canal :eyes");
        }

    }

    public void reportMonsterLocation(Monster monster) {
        if(monstersReport.containsKey(monster.getId())) {
            Monster oldReport = monstersReport.get(monster.getId());
            long diff = oldReport.getTimeStamp().getTime() - monster.getTimeStamp().getTime();
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

        }
        if (!monstersReport.containsKey(monster.getId())) {

            MessageChannel channel = jda.getTextChannelsByName("lupita-channel", false).get(0);

            // Prepare message:
            String msg = monster.getMonster_name() +" ```["+ monster.getId() +"] "+ monster.getMap_name() +" ("+ monster.getX() +","+ monster.getY() +")```";

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
                double ratio = 512 / (double) 400;

                double nX = (monster.getX() * ratio) - (nW /(double) 2);
                double nY = 512 - (monster.getY() * ratio) - (nH /(double) 2);

                g.drawImage(marker, (int) nX, (int) nY, null);
                g.dispose();

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(mapLoc, "png", os);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(os.toByteArray());
                // send message
                channel.sendMessage(msg).addFile(inputStream, "map_"+ monster.getId() +".png").queue();
                // add queue monster
                monstersReport.put(monster.getId(), monster);
            } catch (IOException e) {
                channel.sendMessage(msg).queue();
            }

        }
    }
}
