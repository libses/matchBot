package ru.urfu;

import ru.urfu.profile.Profile;
import ru.urfu.profile.ProfileCreator;
import ru.urfu.profile.ProfileData;

import java.util.Scanner;

public class InnerBot {
    ProfileCreator creator;
    ProfileData profileStorage;
    Scanner scanner = new Scanner(System.in);

    public void start(Profile profile) {
        while (true){
            var command = scanner.nextLine();
            if ("break".equals(command)) {
                break;
            }
            if ("seek".equals(command)) {
                profile.getMatcher().dislikeRandom();
                profile.getMatcher().getCurrent().printProfile();
            }
            if ("like".equals(command)){
                profile.getMatcher().likeRandom();
                profile.getMatcher().getCurrent().printProfile();
            }
            if ("dislike".equals(command)){
                profile.getMatcher().dislikeRandom();
                profile.getMatcher().getCurrent().printProfile();
            }
            if ("help".equals(command)) {
                System.out.println("break, seek, like, dislike, seeMe are commands");
            }
            if ("seeMe".equals(command)) {
                profile.printProfile();
            }
        }
    }


    public InnerBot(ProfileData storage) {
        this.profileStorage = storage;
        this.creator = new ProfileCreator(this.profileStorage);
    }
}
