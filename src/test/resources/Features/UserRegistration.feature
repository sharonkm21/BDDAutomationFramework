Feature: User Registration

  @Smoke
  Scenario Outline: Successful registration with valid details
    Given I am on the account creation page
    When I enter "<FirstName>" and "<LastName>" for Personal Information
    When I enter "<email>" and "<password>" for Sign in Information
    And I submit the registration form
    Then I should be registered successfully

    Examples:
      | FirstName     | LastName     |  | email     | password     |
      | UserFirstName | UserLastName |  | UserEmail | UserPassword |

  @Regression
  Scenario Outline: Registration with an already used email
    Given I am on the account creation page
    When I enter "<FirstName>" and "<LastName>" for Personal Information
    When I enter "<email>" and "<password>" for Sign in Information
    And I submit the registration form
    Then I should see an error message about email already in use

    Examples:
      | FirstName     | LastName     |  | email     | password     |
      | UserFirstName | UserLastName |  | UserEmail | UserPassword |

  @Regression
  Scenario Outline: Registration with invalid email format
    Given I am on the account creation page
    When I enter "<FirstName>" and "<LastName>" for Personal Information
    When I enter "<email>" and "<password>" for Sign in Information
    And I submit the registration form
    Then I should see an error message about invalid email format

    Examples:
      | FirstName     | LastName     |  | email            | password     |
      | UserFirstName | UserLastName |  | InvalidUserEmail | UserPassword |