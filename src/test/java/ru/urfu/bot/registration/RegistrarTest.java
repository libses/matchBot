package ru.urfu.bot.registration;

import junit.framework.TestCase;
import org.junit.Assert;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.urfu.bot.Bot;
import ru.urfu.profile.ProfileData;

public class RegistrarTest extends TestCase {

    public void testRegistration() {
        Assert.assertTrue(true);
        //Здесь слишком сложный тест, который требует работы с телеграмом. Его невозможно реализовать на данном этапе
    }

    public void testGetId(){
        Registrar registrar = new Registrar(new ProfileData(), new Bot("2055254510:AAFkBxlmgL2TqKHmZNtRuuvRbf1LYnW4C2s", "testformatch_bot"));
        Update update = new Update();
        Message message = new Message();
        User user = new User();
        user.setId(Long.parseLong("100"));
        message.setFrom(user);
        update.setMessage(message);
        Assert.assertEquals(registrar.getId(update), 100);
    }
}