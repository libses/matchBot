package ru.urfu.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class PingPongBot extends ListenerAdapter
{
    private static UpdateHandler updateHandler;

    public static void main(String[] args) throws LoginException
    {
        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        JDABuilder.createLight("OTE1MzE1NDU2Nzc1MTc2Mjcy.YaZz8g.4WZqOqLkkEp2dbxH8E-dAgYpZ7I", GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new PingPongBot())
                .setActivity(Activity.playing("Type !ping"))
                .build();
        updateHandler = new UpdateHandler();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()){
            return;
        }
        DiscordMessageSender.api = event.getJDA();

        event.getChannel();
        Message msg = event.getMessage();
        var innerUpdate = DiscordToInnerConverter.Convert(event);

        if (innerUpdate.getMessage().hasText()) {
            try {
                updateHandler.handleText(innerUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (innerUpdate.getMessage().hasPhoto()) {
            try {
                updateHandler.handlePhoto(innerUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
