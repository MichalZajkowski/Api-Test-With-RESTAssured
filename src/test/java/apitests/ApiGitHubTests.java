package apitests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiGitHubTests {

    private String baseGithubApi = "https://api.github.com";
    private String users = "/users";
    private String userName = "/Mike4Z";
    private String repositories = "/repositories";
    private String idOfExampleRepo = "/210621694";
    private String BASEREQBINAPI = "https://reqbin.com";
    private String ECHO = "/echo";
    private String POST = "/post";
    private String PUT = "/put";
    private String JSON = "/json";
    private String SAMPLE = "/sample";

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
                .body("owner.login", equalTo("Mike4Z"))
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
