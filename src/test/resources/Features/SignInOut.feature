Feature: User Sign-in and Sign-out

  @Smoke
  Scenario Outline: Successful sign-in with valid credentials
    Given I am on the sign-in page
    When I enter "<email>" and "<password>" for sign-in
    And I click the sign-in button
    Then I should be signed in successfully

    Examples:
      | email     | password     |
      | UserEmail | UserPassword |

  @Regression
  Scenario Outline: Sign-in with invalid username
    Given I am on the sign-in page
    When I enter "<email>" and "<password>" for sign-in
    And I click the sign-in button
    Then I should see an error message about invalid username

    Examples:
      | email            | password     |
      | InvalidUserEmail | UserPassword |

  @Regression
  Scenario Outline: Sign-in with invalid password
    Given I am on the sign-in page
    When I enter "<email>" and "<password>" for sign-in
    And I click the sign-in button
    Then I should see an error message about invalid password

    Examples:
      | email     | password            |
      | UserEmail | InvalidUserPassword |

  @Smoke
  Scenario Outline: Successful sign-out
    Given I am on the sign-in page
    Given I am on the sign-in page
    When I enter "<email>" and "<password>" for sign-in
    And I click the sign-in button
    When I click the sign-out button
    Then I should be signed out successfully

    Examples:
      | email     | password     |
      | UserEmail | UserPassword |