package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends TestBase {

    User user = new User("Daniil", "dannil165@yandex.ru", "123");
    String accessToken;

    @Before
    public void setUp() {
        UserActions.createUser(user);
        accessToken = UserActions.getAccessTokenUser(user);
    }

    @Test
    @DisplayName("Check status 200 and getting accessToken when user login")
    public void checkStatusWhenUserAuthorize() {
        UserActions.loginUser(user)
                .then().assertThat().statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("User authorize with wrong password, check status 401 and body")
    public void checkStatusAndBodyWhenUserAuthorizeWithWrongPassword() {
        UserActions.loginUser(new User(null, user.getEmail(), "222"))
                .then().assertThat().statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("User authorize with wrong email, check status 401 and body")
    public void checkStatusAndBodyWhenUserAuthorizeWithWrongEmail() {
        UserActions.loginUser(new User(null, "user@ya.ru", user.getPassword()))
                .then().assertThat().statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Для теста удалить")
    public void deleteUser() {
        UserActions.deleteUser(UserActions.getAccessTokenUser(user))
                .then().body("success", equalTo(true));
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserActions.deleteUser(accessToken);
        }
    }
}
