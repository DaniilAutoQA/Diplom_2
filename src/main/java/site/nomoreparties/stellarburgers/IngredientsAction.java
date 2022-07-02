package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class IngredientsAction {

    @Step("Get ingredients list")
    public static List<IngredientsJson> getIngredientList() {
        GetIngredients getIngredients =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .when()
                        .get("/api/ingredients").body().as(GetIngredients.class);
        return new ArrayList<>(getIngredients.getData());
    }

    @Step("Order creation")
    public static Response createOrder(String accessToken, Ingredient ingredient) {
        return given().auth().oauth2(accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(ingredient)
                .when()
                .post("/api/orders");

    }
}
