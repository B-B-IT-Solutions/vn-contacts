package cz.prm.utils;

import lombok.Getter;

@Getter
public enum ComponentTestUser {
    USER_1("user1", "password123"), USER_2("user2", "password123"), USER_3("user3", "password123"), USER_4("user4", "password123"), USER_5("user5",
        "password123");

    private final String username;
    private final String password;

    ComponentTestUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
