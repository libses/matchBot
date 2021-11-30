package ru.urfu.bot;

public class InnerUser implements IUser{

    private Long id;

    private String userName;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public InnerUser(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
