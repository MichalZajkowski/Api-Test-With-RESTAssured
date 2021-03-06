package apitests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiGitHubTest {

    private final String baseGithubApi = "https://api.github.com";
    private final String users = "/users";
    private final String userName = "/MichalZajkowski";
    private final String repositories = "/repositories";
    private final String idOfExampleRepo = "/210621694";
    private final String BASEREQBINAPI = "https://reqbin.com";
    private final String ECHO = "/echo";
    private final String POST = "/post";
    private final String PUT = "/put";
    private final String JSON = "/json";
    private final String SAMPLE = "/sample";

    @Test
    public void testGitHubIsWorkingAndStatusCodeIs200() {
        given().when()
                .get(baseGithubApi + users + userName)
                .then()
                .statusCode(200);
    }

    @Test
    public void testResponseBodyAndStatusCodeIs200() {
        given().when()
                .get(baseGithubApi + repositories + idOfExampleRepo)
                .then()
                .body("id", Matchers.is(Integer.TYPE))
                .body("owner.login", equalTo("MichalZajkowski"))
                .body("owner.type", equalTo("User"))
                .body("forks", Matchers.is(Integer.TYPE))
                .body("fork", Matchers.is(Boolean.TYPE))
                .statusCode(200);
    }

    @Test
    public void testResponseHeaderDataShouldBeCorrect() {
        given().when().
                get(baseGithubApi + repositories + idOfExampleRepo).
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                header("Server", equalTo("GitHub.com"));
    }

    @Test
    public void testPOSTMethodAndStatusCodeIs200() {
        RestAssured.baseURI = BASEREQBINAPI + ECHO + POST;
        given().urlEncodingEnabled(true)
                .param("login", "login")
                .param("password", "password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .post(JSON)
                .then().statusCode(200);
    }

    @Test
    public void test() {
        given()
                .when()
                .authentication().oauth2("mt0dgHmLJMVQhvjpNXDyAff83vA_PxH23Y")
                .put(BASEREQBINAPI + SAMPLE + PUT + JSON)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("success", equalTo("true"));
    }
}
