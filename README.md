# XP Voting / [https://XTC.vote](https://XTC.vote)
[![Java CI with Maven](https://github.com/nickhumberstone/xpvoting/actions/workflows/maven.yml/badge.svg)](https://github.com/nickhumberstone/xpvoting/actions/workflows/maven.yml)

## What is XTC.vote?

XTC.vote is an open spaces topic proposal and voting app. Users can submit topics, and then vote on their favourite ones to help an open spaces meeting decide which of the user-submitted topics should be discussed.

## What is it built with?

XTC.vote is built with Java, Spring Boot, HTMX, Thymeleaf, and Tailwind. The app is hosted on Heroku on a custom domain.

## To run locally:

First clone the repo using either of these methods:

```bash
gh clone nickhumberstone/xpvoting
```

```bash
git clone git@github.com:nickhumberstone/xpvoting.git
```

Then, as a responsible programmer, you should run the test suite:

```bash
./mvnw verify
```

Run command (it will run on [localhost:8080](http://localhost:8080)):

```bash
./mvnw spring-boot:run
```
