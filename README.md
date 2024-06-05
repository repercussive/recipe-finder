## Running the application locally

This application is composed of multiple Docker containers.
To run this application in your local development environment:

1. Ensure [Docker](https://www.docker.com/products/docker-desktop) is installed on your machine.
2. Clone this repository to your local machine.
3. Create a `.env` file at the root of the project.
   You can use the `.env.example` file provided in the repository as an example.
3. Navigate to the root of the project in your terminal.
4. Run the `docker-compose up` command. 

The front-end application will be available at `http://localhost:5000`.
The back-end application will be available at `http://localhost:8080`.