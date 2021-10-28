---
geometry: margin=.75in
---

# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation. As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information

* Team name: TEAMNAME
* Team members
    * Ian Chasse (idc7947)
    * Jake Downie (jwd2488)
    * Anthony Swierkosz (ajs2576)

## Executive Summary

Using Agile methodology, our team iteratively developed WebCheckers by utilizing the Java Spark
micro-framework coupled with the FreeMarker template engine. The application architecture was
designed to utilize Object-Oriented design concepts from SOLID and GRASP. Therefore, the application
is split into three main tiers: UI, Model, and Application.

### Purpose

WebCheckers allows multiple users to play a game of American checkers against another user.

### Glossary and Acronyms

| Term | Definition |
|:------|:------------|
| VO | Value Object |
| MVP | Minimum Viable Product |
| UI | User Interface |
| A.I. | Artificial Intelligence|

## Requirements

This section describes the features of the application.

### Definition of MVP

The WebCheckers MVP should be capable of handling users and gameplay. Players must be able to sign
in, start a game with another player, and resign/sign out if they wish. They must be able to play a
game which enforces the American Rules of Checkers. Throughout the product flow the system must
complete validation like gameboard move validation, and username validation to ensure legal
gameplay.

### MVP Features

#### User Handling

- Allow multiple users to sign in and be connected to a single server.
- Ensure no username is used twice.
- Allow players to resign and/or sign out.

#### Gameplay

- Allow two players to play a game of checkers against another.
- Enforce American Rules of Checkers.
- Red Player Moves First
- Alternate Players
- Validate Jump Moves

### Roadmap of Enhancements

1. Save Game
2. Multiple Games
3. Asynchronous Play
4. A.I. Player

## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](domain-model.png)

- Players view a homepage and then begin the sign-in process.
- Once the player is signed in to the application then they can start a game of checkers.
- A game of checkers consists of a board, a playerLobby, and other attributes like a gameID.
- The board contains rows which contains spaces, and each valid space might have a piece.
- A piece can be red or black and normal or a king.

## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

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

![The WebCheckers Web Interface Statechart](web-interface.png)

The application provies teh player with a view of a traditional chess board with access to submit moves, resing a game, among other things. 

### UI Tier

> _Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities. This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing. For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._

### Application Tier

> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._

### Model Tier

> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._

### Design Improvements

> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements. After completion of the Code metrics exercise, you
> will also discuss the resutling metric measurements. Indicate the
> hot spots the metrics identified in your code base, and your
> suggested design improvements to address those hot spots._

## Testing

> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing

> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage

Unit tests are written before development starts in most cases unless an urgent issue needs to be resolved.
Eventually, all unit tests are to be completed and validated. User testing is done by one of the developers. 