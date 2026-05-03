import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.EnvConfig;
import praktikum.user.UserCreate;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import static java.sql.Array.*;
import static user.UserSteps.*;
import static user.UserAuthSteps.*;
import static user.orderSteps.*;

public class UserLoginTest {
    private UserCreate user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = EnvConfig.BASE_URL;
        user = UserCreate.random();
        createUser(user);
        accessToken = userLoginAndGetToken(user);
    }

    @AfterEach
    void cleanUp() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }

    @DisplayName("Успешная авторизация")
    @Test
    void testUserLogin() {
        userLogin(user);
    }

    @DisplayName("Авторизация с неверной почтой и паролем")
    @Test
    void userLoginWrongEmail() {
        userLoginWithWrongEmailAndPassword();
    }

    @DisplayName("Изменение почты у авторизованного пользователя")
    @Test
    void userChangeEmail() {
        String newEmail = "new_" + user.getEmail();
        changeEmail(accessToken, newEmail);
    }

    @DisplayName("Изменение имени у авторизованного пользователя")
    @Test
    void userChangeName() {
        String newName = "new_" + user.getName();
        changeName(accessToken, newName);
    }

    @DisplayName("Изменение данных у неавторизованного пользователя")
    @Test
    void userChangeEmailWithoutLogin() {
        String newEmail = "withoutauth_" + user.getEmail();
        changeEmailWithoutAuth(newEmail);
    }
}
