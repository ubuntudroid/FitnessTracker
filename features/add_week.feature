Feature: Create Week feature

  Scenario: As user when I click on the + sign in the ActionBar a new empty week is created
    When I press "Add"
    Then I see "Week 1"

  @reset
  Scenario: As user when I click on the + sign in the ActionBar the second time a new empty week called "Week 2" is created
    When I press "Add"
    When I press "Add"
    Then I see "Week 2"

  @reset
  Scenario: As user when I click on a new Week 1 want to see input fields for various categories
    When I press "Add"
    When I press "Week 1"
    Then I see "Weight"
    Then I see "Fat"
    Then I see "Water"
    Then I see "Muscle"

  @reset
  Scenario: As a user when I click on save in the detail view of a week a confirmation toast should appear
    When I press "Add"
    When I press "Week 1"
    When I press "Save"
    Then I see "Data saved"

  # @active <-- This tag (or a similar) can come in pretty handy when you want to just run one scenario via --tag @active
  @reset
  Scenario: As user when I click on a new Week and input all values for Week 1 and then I click on Week 2 and input values there I want to see indicators for Week 2
    Given I have set up Week 2
    Then I see "Week 2"
    Then I see "Fat →  Muscle →  Water →"
    Then I see "stagnant" weight trend for Week 2
    Then I see "unknown" weight trend for Week 1