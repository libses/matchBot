package ru.urfu.bot.registration;

import ru.urfu.profile.Profile;

import java.util.Arrays;
import java.util.List;

/**
 * Класс, который хранит прогресс регистрации
 */
public class ProfileInRegistration {
    private static final List<String> registrationSteps = Arrays.asList("Имя", "Пол", "Возраст", "Город", "Фото");

    private final Profile profile;
    private int progress;

    public Profile getProfile() {
        return profile;
    }

    public ProfileInRegistration(Profile profile) {
        this.profile = profile;
        this.progress = 0;
    }

    public String getCurrentRegistrationStep() {
        return registrationSteps.get(progress);
    }

    public void updateProgress() {
        progress++;
    }

    public boolean isRegistrationCompleted() {
        return registrationSteps.size() == progress;
    }
}
