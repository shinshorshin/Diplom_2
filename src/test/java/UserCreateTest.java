import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.EnvConfig;
import praktikum.user.UserCreate;

import static user.UserAuthSteps.deleteUser;
import static user.UserAuthSteps.userLoginAndGetToken;
import static user.UserSteps.*;


public class UserCreateTest {
    private UserCreate user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = EnvConfig.BASE_URL;
        user = UserCreate.random();
    }

    @AfterEach
    void cleanUp() {
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }

    @DisplayName("Успешное создание пользователя")
    @Test
    void userCreate() {
        createUser(user);
        accessToken = userLoginAndGetToken(user);
    }

    @DisplayName("Создание пользователя без почты")
    @Test
    void userWithoutEmail() {
        createUserWithoutEmail();
    }

    @DisplayName("Создание пользователя без пароля")
    @Test
    void userWithoutPassword() {
        createUserWithoutPassword();
    }

    @DisplayName("Создание пользователя без имени")
    @Test
    void userWithoutName() {
        createUserWithoutName();
    }

    @DisplayName("Создание пользователя с данными существующего пользователя")
    @Test
    void userCreateAgain() {
        createUser(user);
        accessToken = userLoginAndGetToken(user);
        createUserWithExistingData(user);
    }

}
