package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.ProfileData;

public class UpdateHandler {
    final ProfileData data;
    final Registrar registrar;

    public UpdateHandler(ProfileData data,Bot bot) {
        this.data = data;
        registrar = new Registrar(data,bot);

    }


    public void textHandler(Bot bot, Update update) throws Exception {
        registrar.registration(update);
    }

    public void photoHandler(Bot bot, Update update) {


    }


}
