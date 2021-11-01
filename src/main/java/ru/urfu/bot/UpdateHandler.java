package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.urfu.bot.registration.Registrar;
import ru.urfu.profile.ProfileData;
import ru.urfu.profile.ProfileSelector;

import java.util.Objects;

/**
 * Класс, который принимает и обрабатывает обновления
 */

public class UpdateHandler {
    final Registrar registrar;
    final ProfileSelector selector;

    public UpdateHandler(ProfileData data,Bot bot) {
        registrar = new Registrar(data,bot);
        selector = new ProfileSelector(data);

    }


    public void handleText(Bot bot, Update update) throws Exception {
        long id = update.getMessage().getFrom().getId();
        if(registrar.inRegistration(id) || !ProfileData.containsId(id))
            registrar.registration(update);

        if(Objects.equals(update.getMessage().getText(), "next")){
           var nextProfile = selector.getNextProfile();
           var message = String.format("%s\n%s\n%s\n%s",
                   nextProfile.getName(),
                   nextProfile.getCity(),
                   nextProfile.getAge(),
                   nextProfile.getTelegramID());

           bot.execute(SendPhoto.builder()
                   .chatId(update.getMessage().getChatId().toString())
                   .photo(new InputFile(nextProfile.getPhotoLink()))
                   .caption(message)
                   .build()
           );

        }

    }

    public void handlePhoto(Update update) throws Exception {
        registrar.registration(update);
    }


}
