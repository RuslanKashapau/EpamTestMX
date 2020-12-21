#Author: acortes
@Test @PARALLEL
Feature: Posts web service

  @PositiveTest
  Scenario: All posts
    Given Web service is available
    When Get all posts and validate response
    Then Validate number of posts

  @PositiveTest
  Scenario: First Post
    Given Web service is available
    When Get post number 1 and validate id
    Then Validate post title "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
    And validate post body "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit"

  @PositiveTest
  Scenario: Last Post
    Given Web service is available
    When Get post number 100 and validate id
    Then Validate post title "at nam consequatur ea labore ea harum"
    And validate post body "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut"

  @PositiveTest
  Scenario: Comments from Post
    Given Web service is available
    When Get comments from post 1
    Then Validate comment number 5 body "harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit"

  @PositiveTest
  Scenario: Comments from Post
    Given Web service is available
    When Get comments from post parameter 1
    Then Validate comment number 5 body "harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit"

  @PositiveTest
  Scenario: Post test
    Given Web service is available
    Then validate posting to web service

  @PositiveTest
  Scenario: Put test
    Given Web service is available
    Then validate putting to post 1

  @PositiveTest
  Scenario: Delete test
    Given Web service is available
    Then validate delete from post 1

  @NegativeTest
  Scenario: Non existent Post
    Given Web service is available
    Then Validate post number 101 doesnt exists

  @NegativeTest
  Scenario: Non existent Post
    Given Web service is available
    Then Validate post number 0 doesnt exists

  @NegativeTest
  Scenario: No comments for non existent Post
    Given Web service is available
    Then Validate no comments for post 101

  @NegativeTest
  Scenario: No comments for non existent Post
    Given Web service is available
    Then Validate no comments for post 0
