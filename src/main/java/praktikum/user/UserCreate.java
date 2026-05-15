package praktikum.user;

import java.util.concurrent.ThreadLocalRandom;

public class UserCreate {

    private final String email;
    private final String password;
    private final String name;

    public UserCreate(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserCreate random() {
        var random = ThreadLocalRandom.current();
        String randomEmail = random.nextInt() + "@yandex.ru";

        return new UserCreate(randomEmail, "qwerty09876", "Sergey");
    }

    public static UserCreate withoutEmail() {
        return new UserCreate("", "qwerty09876", "Sergey");
    }
    public static UserCreate withoutPassword() {
        return new UserCreate("dfhfgdj@yandex.ru", "", "Sergey");
    }
    public static UserCreate withoutName() {
        return new UserCreate("sdgdfg@yandex.ru", "qwerty09876", "");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

}
