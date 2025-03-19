# Contract-first API Testing with Quarkus

This repository contains the slides and demo project for my talk "Contract-first API Testing with Quarkus".

## License

- The **slides** (`slides/` directory) are licensed under **[CC BY 4.0](LICENSE.md)**.
- The **demo project** (`demo-project/` directory) is licensed under the **[MIT License](demo-project/LICENSE.md)**.
- **Exceptions**:
    - All images inside `slides/assets/restricted/` **are NOT covered** by the CC license.
    - These images **cannot be reused, modified, or distributed** without explicit permission.

## Slides

he slides are built with [Reveal.js](https://revealjs.com/) and can be found in the [slides/](slides) directory.

## Demo project

This project (partially) represents the demo use case of an online boardgame website.
It covers 3 components of this hypothetical website:

* Game Service. The service that hosts the games played.
* Player Profile Service. A small dedicated microservice that provides and maintains player profiles.
* The Player Profile API. The main API exposed by the Player Profile Service.

The repository has one root POM for ease of use in this demo setup.
The top level modules within the root POM represent projects that in a real situation would be separate repositories.

### Player Profile API

This module represents an API Contract repository as described in the talk.
It is a multi-module project with one module (`player-profile-api-contract`) that holds the OpenAPI contract and one
module that generates interface code for Quarkus.
For services that use other technologies, other modules for generated code can be added.

### Player Profile Service

This module is a partial implementation of a Quarkus microservice that hosts player profiles.
Only the API Controllers are fully implemented.

### Game Service

This module is a partial implementation of a Quarkus microservice that is the game engine.
Only the Rest Client for the Player Profile API is fully implemented.

### OpenAPI Test Support

This module holds a small library with some custom code that can be used to verify if JSON objects are valid request or
response bodies for an API.

## Talk abstract

When you first dive into contract testing you’ll soon hit upon Pact. But Pact is consumer-first.
What if you want to work contract-first? There aren’t many tools supporting that approach.

This is exactly the challenge we had in our project.
So we went looking for a simple pragmatic solution.
With just Quarkus, OpenAPI and Git we created a simple setup that works wonders.

In this session I will take you with me on our journey on how we created a simple but effective contract-first setup.
I’ll explain why we made the choices we made and of course I’ll demonstrate the full setup from start to end.

After this session you will know how you can create a similar setup for your own project.
Even if you have a different technical stack.

## Conferences

This talk was / will be given at the following conferences:

| Conference | Date       | Location                      | Link                                                                          | Note              | 
|------------|------------|-------------------------------|-------------------------------------------------------------------------------|-------------------|
| mini.CONF  | 20-03-2025 | 's Hertogenbosch, Netherlands | [miniconf.io](https://miniconf.io/episodes/episode-x/)                        | 15 minute version |
| JavaLand   | 02-04-2025 | Nürnburgring, Germany         | [javaland.eu](https://my.doag.org/events/javaland/2025/agenda/#agendaId.5289) |                   |