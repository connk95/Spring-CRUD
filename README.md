# Employee Management Project

This is a simple Employee Management database created at Tokyo IT School. This program allows users to view employee information, add employees and departments, change employee and department details, and delete employees and departments.

## Table of Contents

- [Features](https://github.com/connk95/Spring-CRUD/blob/main/README.md#features)
- [Installation](https://github.com/connk95/Spring-CRUD/blob/main/README.md#installation)
- [Code Description](https://github.com/connk95/Spring-CRUD/blob/main/README.md#code-description)
- [Usage](https://github.com/connk95/Spring-CRUD/blob/main/README.md#usage)
- [Acknowledgements](https://github.com/connk95/Spring-CRUD/blob/main/README.md#acknowledgements)

## Features

- Create, view, update, and delete employee and department details.

## Installation

1. Clone this repository using the following command:
   ```
   git clone https://github.com/connk95/Spring-CRUD.git
   ```

2. Navigate to the project directory:
   ```
   cd Spring-CRUD
   ```

3. Build the project using Maven:
   ```
   mvn clean install
   ```

4. Run the application (You must have a relevant Oracle SQL database!):
   ```
   mvn spring-boot:run
   ```

## Code Description

[Controllers](https://github.com/connk95/Spring-CRUD/tree/main/src/main/java/jp/co/sss/crud/controller) - Contains controllers for the index, list, register, update, and delete pages.

[Entities](https://github.com/connk95/Spring-CRUD/tree/main/src/main/java/jp/co/sss/crud/entity) - Contains the entities and data structure for Employees and Departments.

[Filters](https://github.com/connk95/Spring-CRUD/tree/main/src/main/java/jp/co/sss/crud/filter) - Contains filters to prevent unregistered users, or those without permission, to access certain pages.

[Templates](https://github.com/connk95/Spring-CRUD/tree/main/src/main/resources/templates) - Contains Thymeleaf html files for the home page, list page (showing all employees and departments), register, update, and delete pages.

## Usage

1. Input a user ID and password in the home screen (user must exist in a relevant Oracle SQL database).
2. View a list of employees and departments.
3. Search by employee name or department from the sidebar.
4. Edit or delete employee or department details using the buttons in the main list.
5. Switch between employee and department view, and create a new employee or department using the buttons above the list.


## Acknowledgements

- This project was partially created by Connor Ketcheson, using a project outline while at 東京ITスクール.

---
