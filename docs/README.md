# Task Completion Notes
Documentation below is meant to serve as notes for the Heila team as well as reference for a potential user. Containerization of the client, server, and web-ui can be found in their corresponding sub-directories. More details about the automated build & publish and Docker compose tasks below.

## GitHub Actions

### docker-build-workflow
This workflow contains jobs to build the containers for the modbus-server, modbus-client, and web-ui using a simple `docker build`. It is triggered on pull requests and pushes to the main branch to ensure new code changes don't break the containers.

### publish workflows
There are separate workflows to publish each sub-application in this project using Docker's [build-push-action](https://github.com/docker/build-push-action) to publish to Docker Hub. This is triggered by pushes to the main branch as well as a daily scheduled cron.

Docker Hub repositories:
 - [web-ui](https://hub.docker.com/repository/docker/sarahmackey42/heila-devops-web-ui)
 - [modbus-server](https://hub.docker.com/repository/docker/sarahmackey42/heila-devops-modbus-server)
 - [modbus-client](https://hub.docker.com/repository/docker/sarahmackey42/heila-devops-modbus-client)

## Docker Compose
Instructions below for running the applications locally.

### Setup

Make sure you have [Docker Desktop](https://www.docker.com/products/docker-desktop/)  installed. 

### Build and run the applications
From the main project directory, start the applications by running `docker compose up`

Enter http://localhost:5000/ in a browser to see the web-ui application running.

### Stop the application

You can stop the applications by running `docker compose down` in another terminal or by hitting `CTRL + C` in your original terminal.

## Further Work

### Versioning
For the simplicity of this task, images are built and published with the `:latest` tag. Later versions of this project would add semantic versioning with version bumping automated via GitHub Actions.

### Observability
Since observability is imperative to any CI/CD system, this project would need some additions to give users and stakeholders insight on the state of the system.

This would include repo tags for version number, build status, coverage percentage, etc.

A separate dashboard to reference with this information would be helpful as well. As the project expands to include unit/functional tests, the usage of the dashboard could expand to include test status and flakiness as well as eventual deployment metrics.

One possible implementation of this could use [DataDog Monitoring](https://www.datadoghq.com/)

### Documentation
As this project sees more users, expanded documentation especially for common debugging tips would be beneficial. An example of this could include a sample tutorial video of using docker-compose so developers know what to expect from their environment.
