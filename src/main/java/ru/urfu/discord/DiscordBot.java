package ru.urfu.discord;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import ru.urfu.bot.IUpdate;
import ru.urfu.bot.UpdateHandler;

import javax.security.auth.login.LoginException;

/**
 * Класс дискорд бота.
 */

public class DiscordBot extends ListenerAdapter {
    private static UpdateHandler updateHandler;

    public static void main(String[] args) throws LoginException {
        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        var token = System.getenv("DISCORD_BOT_TOKEN");
        JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new DiscordBot())
                .setActivity(Activity.playing("Type !ping"))
                .build();
        updateHandler = new UpdateHandler();
    }

    /**
     * Обрабатывает входящее сообщение
     * @param event ивент от дискорда
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        DiscordMessageSender.api = event.getJDA();

        event.getChannel();
        Message msg = event.getMessage();
        var innerUpdate = DiscordToInnerConverter.Convert(event);

        updateHandler.handleUpdate(innerUpdate);
    }
}
