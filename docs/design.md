# Design

## API

The API should contain a version number (/v0/) to make future changes easier.
During initial development this number should be 0 to communicate that it's not
yet stable.

Work periods are located underneath each employee instead of on the top level
because needing to list work periods independent of the employee they belong to
seems to be unlikely. E123 is an employee ID.

/v0/time-tracking/E123/work-periods/

In the payroll case it seems likely that in the majority of the cases the users
want to filter by the month first and by the employee second. If listing
multiple months for a single employee is an important use case a second endpoint
can be added later to reduce the amount of HTTP requests.

/v0/payroll/2024-01/E123

time-tracking and payroll are already separate on the top level because they are
disjoint. They could even be separated into different application
(microservices) but that would be overkill to start with. The code should be
modularized properly to make this transition possible in the future.


## Architecture

Use a hexagonal architecture to ensure future extensibility even though for
such a simple application right now it's overkill.
