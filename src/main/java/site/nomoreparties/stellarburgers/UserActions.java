package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserActions {

    @Step("User creation")
    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step("User login")
    public static Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/login");
    }

    @Step("Getting accessToken user")
    public static String getAccessTokenUser(User user) {
        return loginUser(user)
                .then().extract().body().path("accessToken").toString().substring(7);
    }

    @Step("Getting user information")
    public static Response getInformationUser(String token) {
        return given()
                .auth().oauth2(token)
                .get("/api/auth/user");
    }

    @Step("Update user information")
    public static Response updateInformationUser(User user, String token) {
        return given()
                .auth().oauth2(token)
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .patch("/api/auth/user");
    }

    @Step("User deletion")
    public static Response deleteUser(String token) {
        return given()
                .auth().oauth2(token)
                .delete("/api/auth/user");
    }
}
