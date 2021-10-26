package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.ProfileData;

/**
 * Класс, который принимает и обрабатывает обновления
 */

public class UpdateHandler {
    final ProfileData data;
    final Registrar registrar;

    public UpdateHandler(ProfileData data,Bot bot) {
        this.data = data;
        registrar = new Registrar(data,bot);

    }


    public void handleText(Bot bot, Update update) throws Exception {
        registrar.registration(update);
    }

    public void handlePhoto(Bot bot, Update update) {


    }


}
