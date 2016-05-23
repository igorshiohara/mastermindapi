# MastermindAPI

## Tech stack
* Java 8
* Maven 3
* Tomcat 7
* Google Guice
* Google Guava
* Jersey
* RxObservable to Asynchorous calls

## How to Run
If you use maven, just execute mvn tomcat7:run or Deploy the mastermind-api.war file on Tomcat 7

We've used in memory implementation (cache), so it is not necessary to configure nothing.

## Things that I tried, but dind't have time to complete
1. Swagger UI to documentation (almost done in a branch)
2.  Create more unit tests and integration tests with RestAssured

I sent with the project a very nice postman collection, that's help so much to test the API and understand better.

## Api documentation

### Create a new game endpoint

**Description:** This endpoint creates a new Mastermind game.

**URL Example:** https://azmastermind-api.herokuapp.com/api/new_game/{user}

**Header:** Content-Type: application/json

**HTTP Method:** POST

**Request Param:** PathParam=user

**Response example:**
```json{
  "pins": [
    {
      "color": "GREEN",
      "position": "ONE",
      "positionValue": 1
    },
    {
      "color": "RED",
      "position": "TWO",
      "positionValue": 2
    },
    {
      "color": "YELLOW",
      "position": "THREE",
      "positionValue": 3
    },
    {
      "color": "CYAN",
      "position": "FOUR",
      "positionValue": 4
    },
    {
      "color": "YELLOW",
      "position": "FIVE",
      "positionValue": 5
    },
    {
      "color": "GREEN",
      "position": "SIX",
      "positionValue": 6
    },
    {
      "color": "YELLOW",
      "position": "SEVEN",
      "positionValue": 7
    },
    {
      "color": "MAGENTA",
      "position": "EIGHT",
      "positionValue": 8
    }
  ],
  "codeLength": 8,
  "gameKey": {
    "hash": "e62f4a0e-0106-4258-833a-aa871908f7a6"
  },
  "numGuesses": 0,
  "guessResults": [
    
  ],
  "solved": false,
  "session": {
    "id": 1,
    "createdTime": {
      "nano": 932000000,
      "dayOfYear": 143,
      "dayOfWeek": "SUNDAY",
      "month": "MAY",
      "dayOfMonth": 22,
      "year": 2016,
      "monthValue": 5,
      "second": 39,
      "minute": 25,
      "hour": 19,
      "chronology": {
        "calendarType": "iso8601",
        "id": "ISO"
      }
    },
    "users": [
      "igor"
    ]
  },
  "timeGameStarted": null,
  "timeGameFinished": null
}
```

### Join in a new game endpoint

**Description**: This endpoint join some user a new Mastermind game created before.

**URL Example:** https://azmastermind-api.herokuapp.com/api/join

**Body:**
```json{
  "user": "geiser",
  "sessionId": "1",
  "gameKey": "e62f4a0e-0106-4258-833a-aa871908f7a6"
}
```

**Header:** Content-Type: application/json

**HTTP Method:** POST

**Response example:** geiser entered in the game with sessionId 1

### Make a guess endpoint

**Description:** This endpoint make a guess

**URL Example:** https://azmastermind-api.herokuapp.com/api/guess

**Body:**
```json{
  "user": "igor",
  "gameKey": "b1d30b7e-a561-43e9-a049-2ad57a1badcc",
  "pins": [
    {
      "color": "C",
      "position": 1
    },
    {
      "color": "C",
      "position": 2
    },
    {
      "color": "R",
      "position": 3
    },
    {
      "color": "P",
      "position": 4
    },
    {
      "color": "R",
      "position": 5
    },
    {
      "color": "B",
      "position": 6
    },
    {
      "color": "Y",
      "position": 7
    },
    {
      "color": "M",
      "position": 8
    }
  ]
}
``` 

**Header:** Content-Type: application/json

**HTTP Method:** POST

**Response example:**
```json{
  "exact": 1,
  "near": 0,
  "guess": {
    "user": "igor",
    "pins": [
      {
        "color": "CYAN",
        "position": "ONE",
        "positionValue": 1
      },
      {
        "color": "CYAN",
        "position": "TWO",
        "positionValue": 2
      },
      {
        "color": "RED",
        "position": "THREE",
        "positionValue": 3
      },
      {
        "color": "PURPLE",
        "position": "FOUR",
        "positionValue": 4
      },
      {
        "color": "RED",
        "position": "FIVE",
        "positionValue": 5
      },
      {
        "color": "BLUE",
        "position": "SIX",
        "positionValue": 6
      },
      {
        "color": "YELLOW",
        "position": "SEVEN",
        "positionValue": 7
      },
      {
        "color": "MAGENTA",
        "position": "EIGHT",
        "positionValue": 8
      }
    ],
    "gameKey": "cf6fa2b6-b44a-40dd-9ed8-be1dfdfbebb9"
  },
  "solved": false
}
```

### Game info endpoint

**Description:** This endpoint make a guess

**URL Example:** https://azmastermind-api.herokuapp.com/api/game/{gameKey}

**Header:** Content-Type: application/json

**HTTP Method:** GET

**Response example:**
```json
{
  "pins": [
    {
      "color": "ORANGE",
      "position": "ONE",
      "positionValue": 1
    },
    {
      "color": "GREEN",
      "position": "TWO",
      "positionValue": 2
    },
    {
      "color": "ORANGE",
      "position": "THREE",
      "positionValue": 3
    },
    {
      "color": "MAGENTA",
      "position": "FOUR",
      "positionValue": 4
    },
    {
      "color": "MAGENTA",
      "position": "FIVE",
      "positionValue": 5
    },
    {
      "color": "MAGENTA",
      "position": "SIX",
      "positionValue": 6
    },
    {
      "color": "RED",
      "position": "SEVEN",
      "positionValue": 7
    },
    {
      "color": "BLUE",
      "position": "EIGHT",
      "positionValue": 8
    }
  ],
  "codeLength": 8,
  "gameKey": {
    "hash": "c624aada-f6fa-4ac9-867e-f7e34a87f3f2"
  },
  "numGuesses": 0,
  "guessResults": [
    
  ],
  "solved": false,
  "session": {
    "id": 2,
    "createdTime": {
      "second": 21,
      "minute": 28,
      "hour": 23,
      "nano": 709000000,
      "dayOfYear": 143,
      "year": 2016,
      "month": "MAY",
      "dayOfMonth": 22,
      "dayOfWeek": "SUNDAY",
      "monthValue": 5,
      "chronology": {
        "calendarType": "iso8601",
        "id": "ISO"
      }
    },
    "users": [
      "igor"
    ]
  },
  "timeGameStarted": null,
  "timeGameFinished": null
}
```

### Get the user turn endpoint

**Description:** This endpoint get whose is the user turn

**URL Example:** https://azmastermind-api.herokuapp.com/api/status/cf6fa2b6-b44a-40dd-9ed8-be1dfdfbebb9

**Header:** Content-Type: application/json

**HTTP Method:** GET

**Response example:** It is time to igor make the move.

### Get the history of a specific game

**Description:** This endpoint get the history of the guesses for a specific game

**URL Example:** https://azmastermind-api.herokuapp.com/api/history/cf6fa2b6-b44a-40dd-9ed8-be1dfdfbebb9

**Header:** Content-Type: application/json

**HTTP Method:** GET

**Response example:**
```json
[
  {
    "exact": 2,
    "near": 0,
    "guess": {
      "user": "igor",
      "pins": [
        {
          "color": "CYAN",
          "position": "ONE",
          "positionValue": 1
        },
        {
          "color": "CYAN",
          "position": "TWO",
          "positionValue": 2
        },
        {
          "color": "RED",
          "position": "THREE",
          "positionValue": 3
        },
        {
          "color": "PURPLE",
          "position": "FOUR",
          "positionValue": 4
        },
        {
          "color": "RED",
          "position": "FIVE",
          "positionValue": 5
        },
        {
          "color": "BLUE",
          "position": "SIX",
          "positionValue": 6
        },
        {
          "color": "YELLOW",
          "position": "SEVEN",
          "positionValue": 7
        },
        {
          "color": "MAGENTA",
          "position": "EIGHT",
          "positionValue": 8
        }
      ],
      "gameKey": "7becaccc-6021-4f73-91d6-e5f8c3a78ba3"
    },
    "solved": false
  }
]
```

### Get the history of a specific game by user

**Description:** This endpoint get the history of the guesses for a specific game by user

**URL Example:** https://azmastermind-api.herokuapp.com/api/history/cf6fa2b6-b44a-40dd-9ed8-be1dfdfbebb9?byUser={user}

**Header:** Content-Type: application/json

**HTTP Method:** GET

**Response example:**
```json[
  {
    "exact": 2,
    "near": 0,
    "guess": {
      "user": "igor",
      "pins": [
        {
          "color": "CYAN",
          "position": "ONE",
          "positionValue": 1
        },
        {
          "color": "CYAN",
          "position": "TWO",
          "positionValue": 2
        },
        {
          "color": "RED",
          "position": "THREE",
          "positionValue": 3
        },
        {
          "color": "PURPLE",
          "position": "FOUR",
          "positionValue": 4
        },
        {
          "color": "RED",
          "position": "FIVE",
          "positionValue": 5
        },
        {
          "color": "BLUE",
          "position": "SIX",
          "positionValue": 6
        },
        {
          "color": "YELLOW",
          "position": "SEVEN",
          "positionValue": 7
        },
        {
          "color": "MAGENTA",
          "position": "EIGHT",
          "positionValue": 8
        }
      ],
      "gameKey": "7becaccc-6021-4f73-91d6-e5f8c3a78ba3"
    },
    "solved": false
  }
]
```

## Built With
* Java
* Maven
* Tomcat
* Jersey
* Guava
* Guice
* Reactive (RxJava)

## Try it out
 azmastermind-api.herokuapp.com
