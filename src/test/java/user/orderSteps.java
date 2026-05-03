package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class orderSteps {

    @Step("Создание заказа с привязкой к определенному пользователю")
    public static ValidatableResponse createOrderWithLogin(String accessToken, List<String> ingredientsIds) {
        Map<String, List<String>> requestBody = new HashMap<>();
        requestBody.put("ingredients", ingredientsIds);

        return given().log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(requestBody)
                .post("/api/orders")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();
    }

    @Step("Создание заказа без привязки к определенному пользователю")
    public static ValidatableResponse createOrderWithoutAuth(List<String> ingredientsIds) {
        Map<String, List<String>> requestBody = new HashMap<>();
        requestBody.put("ingredients", ingredientsIds);

        return given().log().all()
                .header("Content-type", "application/json")
                .body(requestBody)
                .post("/api/orders")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();
    }

    @Step("Создание заказа без ингредиентов")
    public static ValidatableResponse createOrderWithoutIngredients() {
        return given().log().all()
                .header("Content-type", "application/json")
                .post("/api/orders")
                .then()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"))
                .log().all();
    }

    @Step("Создание заказа с невалидным айди ингредиента")
    public static ValidatableResponse createOrderWithWrongId() {
        String requestBody = "{\"ingredients\": [\"invalid_ingredient_id_123\"]}";
        return given().log().all()
                .header("Content-type", "application/json")
                .body(requestBody)
                .post("/api/orders")
                .then()
                .statusCode(500)
                .log().all();
    }

    @Step("Создание заказа с ингредиентами")
    public static ValidatableResponse createOrderWithIngredient() {
        String requestBody = "{\"ingredients\": [\"691577430cc94f001a65b859\"]}";
        return given().log().all()
                .header("Content-type", "application/json")
                .body(requestBody)
                .post("/api/orders")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();
    }

    @Step("Получение заказа у пользователя")
    public static ValidatableResponse getOrdersUser(String accessToken) {
        return given().log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .get("/api/orders")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .log().all();
    }

    @Step("Получение заказа без авторизации")
    public static ValidatableResponse getOrdersWithoutLogin() {
        return given().log().all()
                .header("Content-type", "application/json")
                .get("/api/orders")
                .then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"))
                .log().all();
    }
}
