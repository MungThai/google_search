@BDDSTORY-RCSR-3
Feature: Automate Google search for SelenuimHQ
  As a google user I want to search for "SeleninuHQ"I expect to see "SeleniumHQ Browser Automation"

  @BDDTEST-RCSR-4
  Scenario: Google Search for SeleniumHQ
    Given user launches Google webapp
    When user search for a "SeleniumHQ"
    And click on search button
    Then I expect "SeleniumHQ Browser Automation" on top of the list

  @BDDTEST-RCSR-5
  Scenario: Google Search for SeleniumHQ expect false
    Given user launches Google webapp
    When user search for a "SeleniumHQ"
    And click on search button
    Then I expect "SeleniumHQ Browser Automation does not exist" on top of the list
