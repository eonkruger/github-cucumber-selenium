#Author: Ash, Manual 2 Automation Testing
@Search 
Feature: PageTitle

  @Regression
  Scenario Outline: Validate Page Title
    Given I am on the homepage
    When I search for "<search item>"
    Then the page title should include "<search item>"

    Examples: 
      | search item |
      | iphone      |
