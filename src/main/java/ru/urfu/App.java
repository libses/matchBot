package ru.urfu;

import ru.urfu.profile.ProfileStorage;

public class App
{
    public static void main( String[] args ) throws Exception {

        var profiles = new ProfileStorage();
        var bot = new InnerBot(profiles);
        for (int i = 0; i < 5; i++){
            profiles.addProfile(bot.creator.createFullProfile());
        }
        var profile1 = bot.creator.createFullProfile();
        bot.start(profile1);
    }
}
