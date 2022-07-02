package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.nomoreparties.stellarburgers.IngredientsAction.createOrder;
import static site.nomoreparties.stellarburgers.IngredientsAction.getIngredientList;

public class GetOrderTest {

    private User user = new User("Daniil", "dannilq333@yandex.ru", "123123");
    private String accessToken;
    private Ingredient ingredientList;
    private Ingredient emptyIngredientList = new Ingredient();
    private Ingredient wrongIngredientList = new Ingredient(List.of("234123412341234", "23423424"));

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        UserActions.createUser(user);
        accessToken = UserActions.getAccessTokenUser(user);
        ingredientList = new Ingredient(List.of(getIngredientList().get(2).get_id(), getIngredientList().get(4).get_id(), getIngredientList().get(6).get_id()));
    }

    @Step("Get user orders")
    public Response getUserOrders(String accessToken) {
        return given().auth().oauth2(accessToken)
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/orders");
    }

    @Test
    @DisplayName("Check getting order for authorized user")
    public void getOrderWitAuthorizedUser() {
        createOrder(accessToken, ingredientList);
        getUserOrders(accessToken)
                .then().statusCode(200)
                .assertThat()
                .body("success", equalTo(true))
                .body("orders.ingredients[0].size()", equalTo(ingredientList.getIngredients().size()));

    }

    @Test
    @DisplayName("Check getting order for not authorized user")
    public void getOrderWitOutAuthorizedUser() {
        getUserOrders("")
                .then().statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserActions.deleteUser(accessToken);
        }
    }
}
