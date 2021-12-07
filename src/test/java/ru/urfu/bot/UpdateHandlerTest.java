package ru.urfu.bot;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;

public class UpdateHandlerTest extends TestCase {

    private final InnerUser user = new InnerUser(1L, "Dima", UpdateSource.Test);

    private final InnerUser user2 = new InnerUser(2L, "Leha", UpdateSource.Test);

    private final UpdateHandler uh = new UpdateHandler();

    /**
     * Большой нефункциональный тест апдейт хендлера.
     */
    public void testHandleUpdateText() {
        uh.handleUpdate(doCycle("text"));
        Assert.assertSame("Тебя нет в нашей базе, давай зарегистрируемся\n" +
                "\n" +
                "Напиши свое имя:)", MessageSender.currentMessage);


        uh.handleUpdate(doCycle("Дима"));
        Assert.assertSame("Выбери гендер", MessageSender.currentMessage);


        uh.handleUpdate(doCycle("Мужской\uD83D\uDE4B\u200D♂️"));
        Assert.assertSame("Сколько тебе лет?", MessageSender.currentMessage);


        uh.handleUpdate(doCycle("9"));
        Assert.assertSame("Введи ещё раз. Ты вводишь странные цифры или тебе слишком мало лет.\n" +
                "Регистрация доступна с 10 лет.", MessageSender.currentMessage);


        uh.handleUpdate(doCycle("10"));
        Assert.assertSame("Напиши город", MessageSender.currentMessage);


        uh.handleUpdate(doCycle("Курган"));
        Assert.assertSame("Отправь фотку)", MessageSender.currentMessage);


        var InnerPhoto = new InnerPhoto("aboba");
        var list = new ArrayList<IPhotoSize>();
        list.add(InnerPhoto);
        var msg = new InnerMessage(user,null, 100L, list);
        var upd = new InnerUpdate(msg, UpdateSource.Test);
        uh.handleUpdate(upd);
        Assert.assertSame("Ты в базе!\n" +
                "Ты можешь отправить свою геопозицию, если в твоём клиенте есть такая функция и мы будем искать людей возле тебя!", MessageSender.currentMessage);


        uh.handleUpdate(doCycle("Дальше"));
        Assert.assertSame("К сожалению нам некого тебе показать", MessageSender.currentMessage);


        uh.handleUpdate(doCycleForSecondUser("text"));
        uh.handleUpdate(doCycleForSecondUser("Дима"));
        uh.handleUpdate(doCycleForSecondUser("Мужской\uD83D\uDE4B\u200D♂️"));
        uh.handleUpdate(doCycleForSecondUser("10"));
        uh.handleUpdate(doCycleForSecondUser("Курган"));


        var InnerPhoto2 = new InnerPhoto("aboba");
        var list2 = new ArrayList<IPhotoSize>();
        list2.add(InnerPhoto2);
        var msg2 = new InnerMessage(user2,null, 101L, list2);
        var upd2 = new InnerUpdate(msg2, UpdateSource.Test);
        uh.handleUpdate(upd2);


        uh.handleUpdate(doCycleForSecondUser("Дальше"));
        var messageSplit = MessageSender.currentMessage.split("\n");
        Assert.assertEquals("Дима", messageSplit[0]);
        Assert.assertEquals("Курган", messageSplit[1]);
        Assert.assertEquals("10", messageSplit[2]);
    }

    private InnerUpdate doCycle(String text){
        var message = new InnerMessage(user, text, 100L, new ArrayList<>());
        return new InnerUpdate(message, UpdateSource.Test);
    }

    private InnerUpdate doCycleForSecondUser(String text){
        var message = new InnerMessage(user2, text, 101L, new ArrayList<>());
        return new InnerUpdate(message, UpdateSource.Test);
    }
}