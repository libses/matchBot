package ru.urfu.bot;

/**
 * Класс пользователя как клиента какого-то стороннего приложения для внутренней логики. Здесь храним
 * то, куда отправлять ответное сообщение и другую техническую информацию.
 */
public class InnerUser implements IUser {

    private final Long id;

    private UpdateSource source;

    private final String userName;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public UpdateSource getSource() {
        return source;
    }

    public InnerUser(Long id, String userName, UpdateSource updateSource) {
        this.id = id;
        this.userName = userName;
        this.source = updateSource;
    }
}
