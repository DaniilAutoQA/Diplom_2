package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static site.nomoreparties.stellarburgers.IngredientsAction.createOrder;
import static site.nomoreparties.stellarburgers.IngredientsAction.getIngredientList;

public class CreateOrderTest extends TestBase {

    private User user = new User("Daniil", "dannilq333@yandex.ru", "123123");
    private String accessToken;
    private Ingredient ingredientList;
    private Ingredient emptyIngredientList = new Ingredient();
    private Ingredient wrongIngredientList = new Ingredient(List.of("234123412341234", "23423424"));

    @Before
    public void setUp() {
        UserActions.createUser(user);
        accessToken = UserActions.getAccessTokenUser(user);
        ingredientList = new Ingredient(List.of(getIngredientList().get(2).get_id(), getIngredientList().get(4).get_id(), getIngredientList().get(6).get_id()));
    }

    @Test
    @DisplayName("Check order creation with authorized")
    public void createOrderWithAuthorized() {
        OrdersJson order = createOrder(accessToken, ingredientList).body().as(OrdersJson.class);
        assertThat(order.getOrder().getIngredients().get(0).get_id(), equalTo(ingredientList.getIngredients().get(0)));
        assertThat(order.getOrder().getIngredients().get(1).get_id(), equalTo(ingredientList.getIngredients().get(1)));
        assertThat(order.getOrder().getIngredients().get(2).get_id(), equalTo(ingredientList.getIngredients().get(2)));
        assertThat(order.getOrder().getNumber(), notNullValue());
        assertThat(order.getOrder().getOwner().getEmail(), equalTo(user.getEmail()));
        assertThat(order.isSuccess(), equalTo(true));
        assertThat(order.getName(), equalTo("Метеоритный space spicy бургер"));

    }

    @Test
    @DisplayName("Check order creation without authorized")
    public void createOrderWithOutAuthorized() {
        OrdersJson order = createOrder("", ingredientList).body().as(OrdersJson.class);
        assertThat(order.isSuccess(), equalTo(true));
        assertThat(order.getOrder().getNumber(), notNullValue());
        assertThat(order.getName(), equalTo("Метеоритный space spicy бургер"));
        assertThat(order.getOrder().getIngredients(), nullValue());
        assertThat(order.getOrder().getOwner(), nullValue());

    }

    @Test
    @DisplayName("Check order creation without ingredients")
    public void createOrderWithOutIngredients() {
        createOrder(accessToken, emptyIngredientList)
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Check order creation with wrong ingredients")
    public void createOrderWithWrongIngredients() {
        createOrder(accessToken, wrongIngredientList)
                .then().statusCode(500);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserActions.deleteUser(accessToken);
        }
    }
}
