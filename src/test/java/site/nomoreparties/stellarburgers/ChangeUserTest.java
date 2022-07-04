package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class ChangeUserTest extends TestBase {

    User user = new User("Daniil", "dannil333@yandex.ru", "123123");
    String accessToken;

    @Before
    public void setUp() {
        UserActions.createUser(user);
        accessToken = UserActions.getAccessTokenUser(user);
    }

    @Test
    @DisplayName("Check changing Email for authorized user")
    public void changeEmailForAuthorizedUser() {
        String newEmail = "dannil55@yandex.ru";
        UserJson userJson = UserActions.updateInformationUser(new User(null, newEmail, null), accessToken).as(UserJson.class);
        MatcherAssert.assertThat(userJson.getUser(), notNullValue());
        Assert.assertEquals(newEmail, userJson.getUser().getEmail());
    }

    @Test
    @DisplayName("Check changing Email for authorized user on the existing Email")
    public void changeEmailForAuthorizedUserOnExistingEmail() {
        User user2 = new User("Daniil", "dannil777@yandex.ru", "123123");
        UserActions.createUser(user2);
        UserJson userJson = UserActions.updateInformationUser(new User(null, user2.getEmail(), null), accessToken).as(UserJson.class);
        Assert.assertEquals("User with such email already exists", userJson.getMessage());
        UserActions.deleteUser(UserActions.getAccessTokenUser(user2));
    }

    @Test
    @DisplayName("Check changing Name for authorized user")
    public void changeNameForAuthorizedUser() {
        String newName = "dan";
        UserJson userJson = UserActions.updateInformationUser(new User(newName, user.getEmail(), null), accessToken).as(UserJson.class);
        MatcherAssert.assertThat(userJson.getUser(), notNullValue());
        Assert.assertEquals(newName, userJson.getUser().getName());
    }

    @Test
    @DisplayName("Check changing Password for authorized user")
    @Description("Checking authorize after changing Password")
    public void changePasswordForAuthorizedUser() {
        UserActions.updateInformationUser(new User(user.getName(), user.getEmail(), "123123"), accessToken).then().assertThat().statusCode(200);
        UserActions.loginUser(new User(user.getName(), user.getEmail(), "123123")).then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check changing Email for Not authorized user")
    public void changeEmailForNotAuthorizedUser() {
        String newEmail = "dannil16661@yandex.ru";
        UserActions.updateInformationUser(new User(null, newEmail, null), "")
                .then().assertThat().statusCode(401)
                .body("message", CoreMatchers.equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Check changing Name for Not authorized user")
    public void changeNameForNotAuthorizedUser() {
        String newName = "dan";
        UserActions.updateInformationUser(new User(newName, null, null), "")
                .then().assertThat().statusCode(401)
                .body("message", CoreMatchers.equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Check changing Password for Not authorized user")
    public void changePasswordForNotAuthorizedUser() {
        UserActions.updateInformationUser(new User(null, null, "33"), "")
                .then().assertThat().statusCode(401)
                .body("message", CoreMatchers.equalTo("You should be authorised"));
    }

   @After
    public void tearDown() {
        if (accessToken != null) {
            UserActions.deleteUser(accessToken);
        }
    }
}
