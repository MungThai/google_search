@BDDSTORY-RCSR-11
Feature: Google Search
  @BDDTEST-RCSR-12
  Scenario: Google Search for SeleniumHQ
    Given user launches Google webapp
    When user search for a "SeleniumHQ"
    And click on search button
    Then I expect "SeleniumHQ Browser Automation" on top of the list

  @BDDTEST-RCSR-13
  Scenario: Google Search for SeleniumHQ expect false
    Given user launches Google webapp
    When user search for a "SeleniumHQ"
    And click on search button
    Then I expect "SeleniumHQ Browser Automation does not exist" on top of the list
