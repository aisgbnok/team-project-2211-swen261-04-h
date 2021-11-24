---
geometry: margin=1in
---

# WebCheckers Design Documentation

## Team Information

_Semester 2211, SWEN-261, Section 04, Team H_

* Anthony Swierkosz, ajs2576
* Jake Downie, jwd2488
* Ian Chasse, idc7947

## Executive Summary

WebCheckers provides players with a way to play a game of checkers against another. After signing in
with a unique name, players can spectate a game or play against each other!

### Purpose

The goal of WebCheckers is to allow multiple players to play a game of American checkers against
each other.

### Glossary and Acronyms

| Term | Definition |
|:------|:------------|
| A.I. | Artificial Intelligence |
| MVP | Minimum Viable Product |
| OO | Object Oriented |
| OS | Operating System|
| UI | User Interface |
| VO | Value Object |

## Requirements

This section describes the features of the application.

### Definition of MVP

WebCheckers MVP is capable of handling players and gameplay. Players can sign in, sign out, start a
game with another player, and resign. WebCheckers enforces the American Rules of Checkers, and moves
are validated accordingly.

### MVP Features

#### Players

- Players can sign in using a unique username
- Players can play a game of checkers against another player
- Players can sign out
- Players can resign from a game

#### Gameplay

- Allow two players to play a game of checkers against another.
- Enforce American Rules of Checkers.
- Red Player Moves First
- Alternate Players
- Validate Jump Moves

### Roadmap of Enhancements

1. Spectator Mode
2. A.I. Player
3. Enhanced UI
4. Multiple Games
5. Asynchronous Play
6. Save Game

## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](Team-H_Domain-Model.png)

After a player signs in they are added to the Lobby and then able to play in games. Each game has
two players which alternatively perform moves on the board. The board validates that moves adhere to
the American Rules of Checkers. Each game has a single board containing 64 spaces which may contain
a piece.

## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](Team-H_Architecture-Tiers-And-Layers.png)

As a web application, the user interacts with the system using a browser. The client-side of the UI
is composed of HTML pages with some minimal CSS for styling the page. There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views. Controllers
are built using the Spark framework and View are built using the FreeMarker framework. The
Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.

### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts with the
WebCheckers application.

![The WebCheckers Web Interface Statechart](Team-H_State-Chart.png)

The user interface is streamlined and consists of only three pages: a home, sign-in, and game page.
However, a there are a lot of states and routes that handles these three pages.

The player starts off signed out with only the ability to sign in. During sign in the user's name is
validated to ensure it is alphanumeric, once that is complete the player is now signed in.

Once player is signed in, the home page lists all other players and provides a method to start a
game with another player. The start game validates that starting a game is possible, this state is
not visible to the user.

Once a game is started the user is then taken to the game page where they are able to play a game of
checkers. While they always see the game page, various states like check turn, validate move, submit
turn, and backup handle various game functions.

### UI Tier

Each state is called a route which is handled using a GET and/or POST class. The WebServer class
handles assigning routes. For example, Sign In, has a GetSignInRoute class that handles rendering
the Sign-In page, and a PostSignInRoute class that handles writing to the model and application
tier. These routes and their respective http requests (GET/POST) are mapped in WebServer. WebServer
acts as the user entrypoint and route arbitrator dictating which UI class is used for each http
request. GET is used for rendering pages, and POST is used for handling user input.

> _At appropriate places as part of this narrative provide one or more static models (UML class structure or object diagrams) with some details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and sequence diagrams, as is relevant to a particular aspect of the design that you are describing. For example, in WebCheckers you might create a sequence diagram of the POST /validateMove HTTP request processing or you might show a statechart diagram if the Game component uses a state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is not mostly in this tier and cuts across multiple tiers, you can consider placing the narrative description of that feature in a separate section for describing significant features. Place this after you describe the design of the three tiers._

### Application Tier

Our application tier is composed of a PlayerLobby and a GameCenter for aiding the UI and Model
tiers. PlayerLobby handles registering and holding players along with tracking global player
statistics. Similarly, GameCenter handles registering and holding games. As well, GameCenter helps
relay information between the UI and Model tier. For example, when a player resigns the UI tier
signals to the GameCenter who resigned, GameCenter then correctly updates information stored in the
Model Tier as well as global game stats.

> _Provide a summary of the Application tier of your architecture. This section will follow the same instructions that are given for the UI Tier above._

### Model Tier

Our model tier contains components that represent different game objects like the board, player,
pieces, spaces, and moves. This is important as it represents the fundamental aspects of the game
that make up our design.

> _Provide a summary of the Model tier of your architecture. This section will follow the same instructions that are given for the UI Tier above._

### Design Improvements

As of right now the code base needs optimization and cleanup. Many of the newly developed features
like Board creation and move validation are largely fine. However, much of the original code that we
developed during the first sprint is in need of a refactor/rewrite.

#### Examples

> Our entire Application Tier which consists of PlayerLobby and GameCenter are entirely static classes. This does not adhere to OO principles and poses security risks. Instead, these classes should be instantiated in WebServer and passed to each the routes that require them. [#36](https://github.com/RIT-SWEN-261-04/team-project-2211-swen261-04-h/issues/36), [#37](https://github.com/RIT-SWEN-261-04/team-project-2211-swen261-04-h/issues/37).

> I'm sure there are many other code base design improvements that can be made, and this section will be updated to reflect them once identified.

> _After completion of the Code metrics exercise, you will also discuss the resulting metric measurements. Indicate the hot spots the metrics identified in your code base, and your suggested design improvements to address those hot spots._

## Testing

As of now (11/24/2021) we have exclusively used manual testing (i.e. running WebCheckers as a player
would and manually testing functionality).

> Unit Tests are currently in development. Really late, but in development.

### Acceptance Testing

> I don't think we have truly completed any Acceptance Testing, yet.

> _Report on the number of user stories that have passed all their acceptance criteria tests, the number that have some acceptance criteria tests failing, and the number of user stories that have not had any testing yet. Highlight the issues found during acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage

> This is yet to be completed. In development.

> _Discuss your unit testing strategy. Report on the code coverage achieved from unit testing of the code base. Discuss the team's coverage targets, why you selected those values, and how well your code coverage met your targets. If there are any anomalies, discuss those._