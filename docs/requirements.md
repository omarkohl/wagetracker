# Requirements

Informal collection of requirements and user stories.

* There are two types of users, HR (human resources) employees that need to
  know how much employees are working so they can be paid their wages and
  regular employees themselves who have the ability to track their own working
  hours.
* In the user stories below HR employees will have first names starting with H
  (e.g. Holly) and regular employees will have first names starting with E
  (e.g. Enzo).
* Implement a REST API with Spring Boot 3.

* As Holly, I want to be able to see the monthly working hours for each
  employee for payroll processing.
* As Holly, I want to set the total monthly working hours for an employee in
  case they don't use the time tracking feature.
* As Holly, I want to be able to overwrite the total monthly working hours for
  an employee in case they made a mistake.
* As Holly, I want to have a rough overview of the number of hours each
  employees has already worked in the current month.
* As Holly, I want to be able to see whether the number of working hours per
  employee and month has already been validated by an HR employee.
* As Holly, I want to be sure that once working hours have been validated they
  are not overwritten except by another HR employee.

* As Enzo, I want to have a user-friendly REST interface where I can easily
  input my start and end times for each workday and any breaks taken.
* As Enzo, I want to receive confirmation messages after successfully recording
  my daily working hours to ensure that my entries have been saved.
* As Enzo, I want to be able to view a summary of my logged working hours for
  each day, week and month to keep track of my time and ensure accuracy.
* As Enzo, I want to be able to see the start and end times I logged for a
  period of time.
* As Enzo, I want to be able to edit or delete erroneous time sheet entries.


## Someday

Out of scope for now.

* As Holly, I want to be able to log in to the system securely so that I can
  access the employee time tracking and payroll features.
* As Holly, I want to be able to view and edit the monthly working hours logged
  by employees to ensure accuracy and compliance with company policies.
* As Holly, I want to receive notifications or alerts for any discrepancies or
  inconsistencies in the logged working hours to investigate and resolve them
  promptly.
* As Holly, I want to have the ability to ensure the number of hours each
  employee has tracked are upt-to-date before I start payroll processing.

* As Enzo, I want to be able to log in to the system securely to record my daily
  working hours.
* As Enzo, I want to receive reminders or notifications if I forget to record
  my working hours for a day or if I exceed the allowed working hours to avoid
  any payroll issues.
