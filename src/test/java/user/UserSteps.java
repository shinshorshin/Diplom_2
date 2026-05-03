package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.user.UserCreate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserSteps {

    @Step("Создание пользователя с полными данными: почта, пароль, имя")
    public static ValidatableResponse createUser(UserCreate user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/register")
                .then()
                .statusCode(200);
    }

    @Step("Создание пользователя без почты")
    public static ValidatableResponse createUserWithoutEmail() {
        UserCreate userWithoutEmail = UserCreate.withoutEmail();
        return given().log().all()
                .header("Content-type", "application/json")
                .body(userWithoutEmail)
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Создание пользователя без пароля")
    public static ValidatableResponse createUserWithoutPassword(){
        UserCreate userWithoutPassword = UserCreate.withoutPassword();
        return given().log().all()
                .header("Content-type", "application/json")
                .body(userWithoutPassword)
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Создание пользователя без имени")
    public static ValidatableResponse createUserWithoutName() {
        UserCreate userWithoutName = UserCreate.withoutName();
        return given().log().all()
                .header("Content-type", "application/json")
                .body(userWithoutName)
                .post("/api/auth/register")
                .then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Создание пользователя с данными существующего пользователя")
    public static ValidatableResponse createUserWithExistingData(UserCreate user) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/register")
                .then()
                .log().all()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }
}
