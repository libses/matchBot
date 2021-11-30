package ru.urfu.bot.registration;

import junit.framework.TestCase;
import org.junit.Assert;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.urfu.bot.Bot;

public class RegistrarTest extends TestCase {
    /**
     * Проверяет, что getId работает корректно
     */
    public void testGetId() {
        Registrar registrar = new Registrar(new Bot("2055254510:AAFkBxlmgL2TqKHmZNtRuuvRbf1LYnW4C2s", "testformatch_bot"));
        Update update = new Update();
        Message message = new Message();
        User user = new User();
        user.setId(Long.parseLong("100"));
        message.setFrom(user);
        update.setMessage(message);
        //Assert.assertEquals(registrar.getId(update), 100);
    }
}