# JIRA Story: SCRUM-100
Feature: Verify the system's response time when displaying 'Lost' status in purchase order details

  As a user  
  I want to verify the system's response time  
  So that I can ensure the application performs efficiently  

  Background:
    Given I am logged into the system

@smoke @Purchase_Order @Non_Functional
  Scenario: Verify system response time for 'Lost' status in purchase order details
    When I navigate to the Purchase Order module
    And I select a purchase order with 'Lost' status
    Then the system should display the 'Lost' status in the purchase order details within acceptable response time.