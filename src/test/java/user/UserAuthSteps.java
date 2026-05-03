package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.user.UserCreate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserAuthSteps {
    @Step("Успешная авторизация под созданным пользователем")
    public static ValidatableResponse userLogin(UserCreate user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/login")
                .then()
                .body("success", equalTo(true));
    }

    @Step("Логин с неверными учетными данными")
    public static ValidatableResponse userLoginWithWrongEmailAndPassword() {
        UserCreate invalidUser = new UserCreate("agdgdfgrw@yandex.ru", "d32432afg", "AnyName");

        return given().log().all()
                .header("Content-type", "application/json")
                .body(invalidUser)
                .post("/api/auth/login")
                .then()
                .log().all()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Получение токена пользователя")
    public static String userLoginAndGetToken(UserCreate user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
    }

    @Step("Удаление пользователя по токену")
    public static ValidatableResponse deleteUser(String accessToken) {
        return given().log().all()
                .header("Authorization", accessToken)
                .delete("/api/auth/user")
                .then()
                .log().all()
                .statusCode(202);
    }

    @Step("Успешное изменение почты у авторизованного пользователя")
    public static ValidatableResponse changeEmail(String accessToken, String newEmail) {
        return given().log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body("{\"email\": \"" + newEmail + "\"}")
                .patch("/api/auth/user")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Step("Успешное изменение имени у авторизованного пользователя")
    public static ValidatableResponse changeName(String accessToken, String newName) {
        return given().log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body("{\"name\": \"" + newName + "\"}")
                .patch("/api/auth/user")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Step("Изменение почты без авторизации (ожидается ошибка)")
    public static ValidatableResponse changeEmailWithoutAuth(String newEmail) {
        String requestBody = String.format("{\"email\": \"%s\"}", newEmail);

        return given().log().all()
                .header("Content-type", "application/json")
                .body(requestBody)
                .patch("/api/auth/user")
                .then()
                .log().all()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
