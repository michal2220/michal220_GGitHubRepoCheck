# GitHub Repository Checker

This application was created as an exercise for recruitment purposes.
This is a Spring Boot application which allows for checking user's non-forked repositories on GitHub.

## Features:

Response is in JSON format and contains:
- Repository name
- Owner's Login
- For each branch, it's name and commit sha

This application filters out all repositries which are forks.

## Usage

To access this application, it needs to be cloned and running on local device.
This application is used by accesing the path: 
`http://localhost:8080/app/repos/{username}`
Where `{username}` needs to be replaced with username of the GitHub user.
  
## API Endpoints

- `GET /app/repos/{username}`: Get a list of user's repositories. Accepts `Accept` header with `application/json`.


## Error Handling

- `404 Not Found`: User not found.
- `406 Not Acceptable`: Wrong header type used

## Technologies Used

- Spring Boot
- RestTemplate
- Gradle
