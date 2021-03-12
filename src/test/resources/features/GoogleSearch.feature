@Sanity
Feature: Google Search for SeleniumHQ

  Scenario: Verification Google Search for SeleniumHQ
    Given user launches Google webapp
    When user search for a "SeleniumHQ"
    And click on search button
    Then I expect "SeleniumHQ Browser Automation" on top of the list

  Scenario: Verification Google Search for SeleniumHQ expected false
    Given user launches Google webapp
    When user search for a "SeleniumHQ"
    And click on search button
    Then I expect "SeleniumHQ Browser Automation does not exist" on top of the list