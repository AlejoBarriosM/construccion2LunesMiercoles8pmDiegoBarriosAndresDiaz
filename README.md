
# Project Club

## Overview
This project is a club management system designed to handle various functionalities such as user management, invoice handling, and more. It is built using Java and follows a modular architecture.

## Directory Structure
- `projectClub/`: The main directory containing all source code and configuration files.
  - `src/main/java/app/`: Contains all Java source files.
    - `controller/`: Handles the business logic and application flow.
    - `dao/`: Data access objects responsible for interacting with the database.
    - `dto/`: Data transfer objects used for transferring data between different layers.
    - `model/`: Contains the core entities of the application.
    - `service/`: Contains service interfaces and implementations.
    - `config/`: Configuration files for the application.
    - `helpers/`: Utility classes and helper methods.
  - `resources/`: Contains SQL files and other resources.
  - `.idea/`: IDE-specific configuration files.

## Getting Started
To set up the project locally, follow these steps:
1. Clone the repository: `git clone <repo-url>`
2. Navigate to the `projectClub` directory.
3. Build the project using Maven: `mvn clean install`
4. Run the project: `java -jar target/your-project.jar`
5. **Database Setup**: Execute the `club.sql` file located in the `src/main/resources/` directory to set up the database structure.

## Dependencies
The project uses the following dependencies:
- MySQL Connector for database interaction
- Maven for build automation

## License
This project is licensed under the MIT License. See the LICENSE file for details.
