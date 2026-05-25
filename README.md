# Gamestudio – Reversi

This project is a reupload and modified version of a school project called **Gamestudio**.

The application contains a web implementation of the game **Reversi**, together with basic services such as score tracking, comments, ratings, and simple user login.

## Project Description

Gamestudio is a simple Spring Boot web application.

The main part of the project is the Reversi game, which can be played directly in the browser.

Users can:

- play Reversi,
- start a new game,
- see the current game state,
- see the score of both players,
- save the final score,
- add comments,
- rate the game,
- view the overall game rating,
- log in using a simple username,
- log out from the application.

The main focus of this reupload was improving and modernizing the web UI.

## Technologies Used

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- Jakarta EE imports
- HTML
- CSS
- Maven

## Project Structure

Basic project structure:
```text
gamestudio
├── src
│   ├── main
│   │   ├── java
│   │   │   └── sk.tuke.gamestudio
│   │   │       ├── entity
│   │   │       ├── game.reversi
│   │   │       ├── server
│   │   │       └── service
│   │   └── resources
│   │       ├── static
│   │       │   └── css
│   │       ├── templates
│   │       │   ├── homepage.html
│   │       │   ├── reversi.html
│   │       │   └── comment.html
│   │       └── application.properties
│   └── test
├── gamestudio.http
├── pom.xml
└── README.md
```

## Main Pages

| URL | Description |
| --- | --- |
| `/` | Homepage |
| `/homepage` | Homepage |
| `/reversi` | Reversi game |
| `/reversi/new` | Start a new Reversi game |
| `/comment` | Scores, comments, and ratings |
| `/user/login?login=username` | Log in user |
| `/user/logout` | Log out user |

## User Interface
The UI was redesigned for the main parts of the application:
- homepage,
- Reversi game page,
- comments and ratings page.


## How to Run the Project
This is a Maven project.

### 1. Clone the repository
```bash
git clone <https://github.com/hud-pa/gamestudio.git>
cd gamestudio
```

### 2. Run the application
You can run the application from an IDE or by using Maven.
Using Maven: mvn spring-boot:run
After the application starts, open: http://localhost:8080/

## Database Configuration
The project uses JPA services for scores, comments, and ratings.
Database configuration is located in: `src/main/resources/application.properties`

Before running the project, make sure the database configuration matches your local environment.

## Reversi Game
The Reversi game is available at:`/reversi`
The user can:
- play the game in the browser,
- start a new game,
- see the current player,
- see the current game state,
- see the score of the black and white player,
- save the final result after the game ends.

## Comments and Ratings
The comments and ratings page is available at: `/comment`

The user can:
- view the best scores,
- add a comment,
- rate the game using stars,
- see their own rating,
- see the overall game rating,
- view comments from other users.

## Notes

This project is a school project created for learning and demonstration purposes.

This reupload includes mainly:

- improved web UI
- new homepage
- updated Reversi page
- updated comments and ratings page
- simple user login display
- logout option
- game rating overview
- cleanup of old UI files