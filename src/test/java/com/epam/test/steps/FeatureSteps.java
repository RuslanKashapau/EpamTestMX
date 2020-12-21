package com.epam.test.steps;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;

import com.epam.utils.core.ConfigReader;
import com.epam.utils.core.StringUtils;
import com.epam.utils.test.ReportUtils;
import com.epam.utils.test.Validations;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FeatureSteps {

	private static String SERVER = ConfigReader.readPropByEnvironment("server.jsonplaceholder");
	private static Response allPosts = null;
	private static Response lastPost = null;
	private static Response lastComments = null;

	@Given("^Web service is available$")
	public void web_service_is_available() throws Throwable {
		int statusCode = given().get(SERVER).then().assertThat().statusCode(200).and().extract().statusCode();
		ReportUtils.attachText("Server Response code :: " + statusCode);
	}

	@When("^Get all posts and validate response$")
	public void get_all_posts() throws Throwable {

		RequestSpecification request = RestAssured.given();
		request.baseUri(SERVER);
		request.basePath(ConfigReader.readPropByEnvironment("path.jsonplaceholder.posts"));
		allPosts = request.get().then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.body(matchesJsonSchemaInClasspath("posts_schema.json")).and().extract().response();
		ReportUtils.attachText("Response status and schema validated");
	}

	@Then("^Validate number of posts$")
	public void validate_all_posts_response() throws Throwable {
		List<String> postsList = allPosts.jsonPath().getList("$");
		Validations.validateEquals("100", "" + postsList.size(), "Total number of posts");
	}

	@When("^Get post number (\\d+) and validate id$")
	public void get_post_number_and_validate_id(int id) throws Throwable {
		RequestSpecification request = RestAssured.given();
		request.baseUri(SERVER);
		lastPost = request.get(ConfigReader.readPropByEnvironment("path.jsonplaceholder.onepost"), id).then()
				.assertThat().body("id", equalTo(id)).and().extract().response();
		ReportUtils.attachText("Post ID " + id + " validated");
	}

	@Then("^Validate post title \"([^\"]*)\"$")
	public void validate_post_title(String title) throws Throwable {
		Validations.validateContains(title, "" + lastPost.path("title"), "Title validated :: ");
	}

	@Then("^validate post body \"([^\"]*)\"$")
	public void validate_post_body(String body) throws Throwable {
		Validations.validateContains(StringUtils.fixcucumberNextLine(body), "" + lastPost.path("body"),
				"Body validated :: ");
	}

	@When("^Get comments from post (\\d+)")
	public void get_comments_from_post_and_validate_schema(int postId) throws Throwable {
		RequestSpecification request = RestAssured.given();
		request.baseUri(SERVER);
		lastComments = request.get(ConfigReader.readPropByEnvironment("path.jsonplaceholder.onepostcomments"), postId)
				.then().extract().response();
	}

	@When("^Get comments from post parameter (\\d+)$")
	public void get_comments_from_post_parameter(int postId) throws Throwable {
		RequestSpecification request = RestAssured.given().queryParam("postId", postId);
		request.baseUri(SERVER);
		lastComments = request.get(ConfigReader.readPropByEnvironment("path.jsonplaceholder.comments")).then().extract()
				.response();
	}

	@Then("^Validate comment number (\\d+) body \"([^\"]*)\"$")
	public void validate_comment_number_body(int id, String body) throws Throwable {
		List<String> jresponse = lastComments.jsonPath().getList("$");
		JSONArray jsonArray = new JSONArray(lastComments.asString());

		for (Object comment : jsonArray) {
			JSONObject jsonComment = (JSONObject) comment;

			if (id == (Integer) jsonComment.get("id")) {
				Validations.validateContains(StringUtils.fixcucumberNextLine(body), "" + jsonComment.get("body"),
						"Comment validated :: ");
			}
		}
	}

	@Then("^validate posting to web service$")
	public void validate_Posting_to_web_service() throws Throwable {
		RequestSpecification request = RestAssured.given();
		request.baseUri(SERVER);

		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", 101); // Cast
		requestParams.put("id", 101);
		requestParams.put("title", "testTitle");
		requestParams.put("body", "testBody");

		request.body(requestParams.toString());
		Response response = request.post(ConfigReader.readPropByEnvironment("path.jsonplaceholder.posts"));

		int statusCode = response.getStatusCode();
		Validations.validateEquals("201", "" + statusCode, "Post Result ");
		ReportUtils.attachText(response.asString());
	}

	@Then("^validate putting to post (\\d+)$")
	public void validate_putting_to_post(int id) throws Throwable {
		RequestSpecification request = RestAssured.given();
		request.baseUri(SERVER);

		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", 1); // Cast
		requestParams.put("id", 1);
		requestParams.put("title", "testTitle");
		requestParams.put("body", "testBody");

		request.body(requestParams.toString());
		Response response = request.put(ConfigReader.readPropByEnvironment("path.jsonplaceholder.onepost"), "" + id);

		int statusCode = response.getStatusCode();
		Validations.validateEquals("200", "" + statusCode, "Put Result ");
		ReportUtils.attachText(response.asString());
	}

	@Then("^validate delete from post (\\d+)$")
	public void validate_delete_from_post(int id) throws Throwable {
		RequestSpecification request = RestAssured.given();
		request.baseUri(SERVER);

		Response response = request.delete(ConfigReader.readPropByEnvironment("path.jsonplaceholder.onepost"), "" + id);

		int statusCode = response.getStatusCode();
		Validations.validateEquals("200", "" + statusCode, "Delete Result ");
		ReportUtils.attachText(response.asString());
	}

	@Then("^Validate post number (\\d+) doesnt exists$")
	public void validate_post_number_doents_exists(int id) throws Throwable {
		RequestSpecification request = RestAssured.given();
		request.baseUri(SERVER);
		
		request.get(ConfigReader.readPropByEnvironment("path.jsonplaceholder.onepost"), id).then().assertThat().statusCode(404);
		ReportUtils.attachText("Post ID " + id + "validated");
	}

	@Then("^Validate no comments for post (\\d+)$")
	public void validate_no_comments_for_post(int postId) throws Throwable {
		RequestSpecification request = RestAssured.given();
		request.baseUri(SERVER);
		request.get(ConfigReader.readPropByEnvironment("path.jsonplaceholder.onepostcomments"), postId).then().assertThat()
				.body("", Matchers.hasSize(0));
	}

}
