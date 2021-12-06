package ru.urfu.bot;

/**
 * Интерфейс для клиента из стороннего приложения (тг, дискорд...)
 */

public interface IUser {

    Long getId();

    String getUserName();

    boolean isTelegramUser();
}
