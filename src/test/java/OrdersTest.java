import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.EnvConfig;
import praktikum.user.UserCreate;

import java.util.Arrays;
import java.util.List;

import static user.UserAuthSteps.deleteUser;
import static user.UserAuthSteps.userLoginAndGetToken;
import static user.UserSteps.createUser;
import static user.OrderSteps.*;

public class OrdersTest {
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

    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Test
    void createOrderWithWrongIdTest() {
        createOrderWithWrongId();
    }

    @DisplayName("Создание заказа с авторизацией")
    @Test
    void createOrderWithLoginTest() {
        List<String> ingredientsIds = Arrays.asList(
                "691577430cc94f001a65b859",
                "691577430cc94f001a65b862"
        );

        createOrderWithLogin(accessToken, ingredientsIds);
    }

    @DisplayName("Создание заказа без авторизации")
    @Test
    void createOrderWithoutLoginTest() {
        List<String> ingredientsIds = Arrays.asList(
                "691577430cc94f001a65b859",
                "691577430cc94f001a65b862"
        );

        createOrderWithoutAuth(ingredientsIds);
    }

    @DisplayName("Создание заказа без ингредиентов")
    @Test
    void createOrdersWithoutIngredientsTest() {
        createOrderWithoutIngredients();
    }

    @DisplayName("Создание заказа с ингредиентом")
    @Test
    void createOrderWithIngredientTest() {
        createOrderWithIngredient();
    }

    @DisplayName("Получение заказов без передачи токена пользователя")
    @Test
    void getOrderWithoutLoginTest() {
        getOrdersWithoutLogin();
    }

    @DisplayName("Получение заказов авторизованного пользователя")
    @Test
    void getOrdersUserTest() {
        List<String> ingredientsIds = Arrays.asList(
                "691577430cc94f001a65b859",
                "691577430cc94f001a65b862"
        );

        createOrderWithLogin(accessToken, ingredientsIds);
        getOrdersUser(accessToken);
    }
}
