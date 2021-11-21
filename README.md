# WebCheckers

An online Checkers game system built in Java 8 => 17 and Spark, a web micro-framework.

## Team H

_Semester 2211, SWEN-261, Section 04, Team H_

- Anthony Swierkosz, ajs2576
- Jake Downie, jwd2488
- Ian Chasse, idc7947

## Prerequisites

- You must have Java 8 to 17
- You must have Maven 3.6.3 or later
- [It is recommended to have `JAVA_HOME` setup in your environment](https://docs.oracle.com/en/cloud/saas/enterprise-performance-management-common/diepm/epm_set_java_home_104x6dd63633_106x6dd6441c.html)

_This project has been tested up to Java SE `17.0.1` and Maven `3.8.4`, but other versions may
work._

## Running

1. Clone the repository

```
git clone https://github.com/RIT-SWEN-261-04/team-project-2211-swen261-04-h.git
```

_If you are using IntelliJ, you can use
the **[IntelliJ](https://github.com/RIT-SWEN-261-04/team-project-2211-swen261-04-h/tree/master#intellij)**
directions for steps 2-3._

### Terminal

2. Go to the root directory
3. Run the maven execution plugin

```
mvn compile exec:java
```

### IntelliJ

2. Open the root directory
3. Run the `Run` configuration, <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>F10</kbd>

### Play

4. Go to [`http://localhost:4567/`](http://localhost:4567/) in your browser
5. Follow the directions and begin playing a game

## Known Bugs & Disclaimers

> 👉 Turn submission is not yet implemented

> 👉 After submitting a turn, move validation breaks. For more details on why, see [#32](https://github.com/RIT-SWEN-261-04/team-project-2211-swen261-04-h/issues/32)

> 👉 There are instances where routing breaks. e.g., players challenge another around the same time results in error message for second player [#34](https://github.com/RIT-SWEN-261-04/team-project-2211-swen261-04-h/issues/32)

## Debugging & Testing

The Maven build script provides hooks for run unit tests and generate code coverage reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one
   of `ui`, `appl`, `model`
2. Open in your browser the file at `PROJECT_HOME/target/site/jacoco/{ui, appl, model}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Model tier tests open in your browser the file
   at `PROJECT_HOME/target/site/jacoco/model/index.html`
3. To view the Application tier tests open in your browser the file
   at `PROJECT_HOME/target/site/jacoco/appl/index.html`
4. To view the UI tier tests open in your browser the file
   at `PROJECT_HOME/target/site/jacoco/ui/index.html`

## How to generate the Design documentation PDF

1. Execute `mvn exec:exec@docs`
2. Note: this command will fail on a clean project without a `/target`
   directory. Create the directory first if running after a `clean` operation without any
   intervening commands that create the directory, such as compile.
3. The generated PDF will be in `PROJECT_HOME/target/` directory

## How to create a zipfile distribution of the source for the project

1. Execute `mvn exec:exec@zip`
2. The distribution zipfile will be in `PROJECT_HOME/target/WebCheckers.zip`

## License

WebCheckers is licensed under
the [MIT LICENSE.](https://github.com/RIT-SWEN-261-04/team-project-2211-swen261-04-h/blob/master/LICENSE)
