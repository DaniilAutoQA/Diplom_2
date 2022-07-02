package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {

    User user = new User("Daniil", "dannil166@yandex.ru", "123");

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Check user creation")
    public void createUser(){
        UserActions.createUser(user)
                .then().assertThat().statusCode(200)
                .body("success", equalTo(true));
        UserActions.deleteUser(UserActions.getAccessTokenUser(user));
    }

    @Test
    @DisplayName("Create two equally User")
    public void createSameUser(){
        UserActions.createUser(user);
        UserActions.createUser(user)
                .then().assertThat().statusCode(403)
                .body("message", equalTo("User already exists"));
        UserActions.deleteUser(UserActions.getAccessTokenUser(user));
    }

    @Test
    @DisplayName("Check user creation without name")
    public void createUserWithOutName(){
        UserActions.createUser(new User(null, "dannil162@yandex.ru", "123"))
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Check user creation with empty name")
    public void createUserWithEmptyName(){
        UserActions.createUser(new User("", "dannil162@yandex.ru", "123"))
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Check user creation without email")
    public void createUserWithOutEmail(){
        UserActions.createUser(new User("dan", null, "123"))
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Check user creation with empty email")
    public void createUserWithEmptyEmail(){
        UserActions.createUser(new User("", "", "123"))
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Check user creation without password")
    public void createUserWithOutPassword(){
        UserActions.createUser(new User("dan", "dannil162@yandex.ru", null))
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Check user creation with empty password")
    public void createUserWithEmptyPassword(){
        UserActions.createUser(new User("", "dannil162@yandex.ru", ""))
                .then().assertThat().statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
