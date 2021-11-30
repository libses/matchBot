package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public class InnerUpdate implements IUpdate {

    IMessage message;

    public InnerUpdate(IMessage message) {
        this.message = message;
    }

    @Override
    public IMessage getMessage() {
        return message;
    }
}
