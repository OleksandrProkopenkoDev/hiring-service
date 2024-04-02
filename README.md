# Hiring service

## Project description

An application that provides recruitment services to users by publishing vacancies with stages of
assessing the applicant's knowledge.

## Table of content

* [Hiring service](#hiring-service)
    * [Project description](#project-description)
    * [Initial requirements](#initial-requirements)
    * [Set up access to gitlab registry](#set-up-access-to-gitlab-registry)
    * [Build, deploy and run](#build-deploy-and-run)
    * [Development recommendations and requirements](#development-recommendations-and-requirements)
        * [Best Practice Before Pushing & Commit:](#best-practice-before-pushing--commit)
    * [Deployment environment](#deployment-environment)
        * [Local environment](#local-environment)
        * [Staging environment](#staging-environment)
        * [Production environment](#production-environment)

## Initial requirements

To build and run the application, you need to be installed and configured:

1. Java 17 (Amazon)
2. Maven
3. Git
4. Docker
5. Lombok plugin for your IDE
6. Enabled annotation processing in your IDE settings
   (for IntelliJ IDEA Build, Execution, Deployment -> Compiler -> Annotation processors)
7. Set environment variables in any way you like.
   The Default solution is set variables as system
   variable:
    - `TRAINING_SERVICE_DB_USER_PASSWORD` - minimum 6 chars (letters, nums)

## Set up access to gitlab registry

Login to gitlab container registry.

```shell
docker login registry.gitlab.com
```

## Build, deploy and run

1. Run dependent containers.

   ```shell
   cd {project_directory}/monitaet/
   ```

   ```shell
   docker compose up -d quiz-postgres quiz-mongodb quiz-keycloak
   ```
2. You can build the application and run tests with maven command:

   `mvn clean package`

3. You can run the application with Maven command:

   `mvn spring-boot:start`
   or using Run configuration of your favorite IDE

4. Use next endpoints to ensure that application hac started successfully:

    - `http://host:port/swagger-ui/index.html`

5. Run tests:
    - unit tests `mvn test`
    - integration tests `mvn failsafe:integration-test`
    - all tests `mvn verify` or `mvn package`

## Development recommendations and requirements

1. Please check out the coding standards.
   **[Coding Standards](../../../docs/development/coding/java_code_standards.md)**
2. Set up formatting to your IDE.
   **[Formatting](../../../docs/development/coding/intellij_idea_code_formatting.md)**
3. Set up inspections to your IDE.
   **[Inspections](../../../docs/development/coding/intellij_idea_code_inspection.md)**
4. There are coding recommendations:
    1. the application implements tree layer topology with Interface Controller Layer, Service
       layer and Data access layer (DAO/repository).
       There are transport objects for each layer: request/response objects for controllers, DTOs
       for Services, and Entities for DAO.
    2. you should use the Lombok  `@Data` and other annotations in requests/responses, entities and
       DTOs to avoid writing boilerplate code.
       such as getters, setters, overridden `eqauls` and `hashCode` methods
    3. it is recommended to use `MapStruct` lib to perform conversion of the transport objects
       (including collections).
       Please refer [mapstruct docs](https://mapstruct.org/) for more details.
5. Check out how to use
   **[PMD code analyzer](../../../docs/development/coding/pmd_code_inspection.md)**

### Best Practice Before Pushing & Commit:

Run `mvn clean package` before pushing or commit to run autoformatting, all
tests and pmd validation.

## Deployment environment

### Local environment

| Variable name          | Value example    | Initial value         | Description                |
|------------------------|------------------|-----------------------|----------------------------|
| database.postgres.port | 5432             | 5433                  | Port to Postgres Database. |
| database.mongo.port    | 27777            | 27777                 | Port to Mongo database.    |
| keycloak.url           | http://host:port | http://localhost:9080 | Keycloak server url.       |

### Staging environment

TBD

### Production environment

TBD

