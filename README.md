# Aplikacija To-Do

## Pregled
Ta projekt je **Aplikacija To-Do**, ki sestoji iz **React frontenda** (uporaba Vite) in **Spring Boot backend-a**, povezanega z **MySQL bazo podatkov**. Frontend je razvit v **Visual Studio Code**, medtem ko je backend v **IntelliJ IDEA**. Ta postavitev nudi celostno rešitev z modernim uporabniškim vmesnikom in skalabilnim backend-om.

## Kazalo vsebine
- [Tehnologije](#tehnologije)
- [Začnite](#začnite)
  - [Predpogoji](#predpogoji)
  - [Namestitev](#namestitev)
- [Struktura projekta](#struktura-projekta)
- [Spremenljivke okolja](#spremenljivke-okolja)
- [Baza podatkov](#baza-podatkov)
- [Pregled komponent](#pregled-komponent)
- [REST API končne točke](#rest-api-konce-točke)
- [Skripte](#skripte)
- [Uporaba](#uporaba)
- [Prispevanje](#prispevanje)
- [Licenca](#licenca)

## Tehnologije

### Frontend
- **React** (zadnja različica Vite)
- **TypeScript**
- **CSS/Styled Components** 

### Backend
- **Spring Boot** (Maven)
- **Java SDK 21**
- **MySQL** (za bazo podatkov)


## Začnite

### Predpogoji
- **Node.js** (zadnja LTS različica)
- **Java SDK 21**
- **MySQL** (preverite, ali deluje strežnik MySQL)
- **IntelliJ IDEA** (za razvoj backend-a)
- **Visual Studio Code** (za razvoj frontend-a)

### Namestitev

1. **Klonirajte repozitorij:**
   ```bash
   git clone https://github.com/Gjoce/To-do-application.git



## Struktura projekta

Struktura projekta je organizirana na naslednji način:

```plaintext
project-root/
│
├── frontend/                                                                      # React projekt (Vite)
│   ├── public/                
│   ├── src/                                                                       # Izvorne datoteke
│   │   ├── components/AddTask,Footer,Navbar,Task,TaskList,UpdateTaskPopup         # React komponente
│   │   ├── App.tsx                                                                # Glavna komponenta aplikacije
│   │   ├── main.tsx                                                               # Vstopna točka aplikacije
│   │   └── ...css
│   └── package.json                                                               # Konfiguracijska datoteka za npm
│
├── backend/                                                                       # Spring Boot projekt
│   ├── src/main/java                                                              # Java izvorne datoteke
│   │   ├── si/um/si/                                                              # Paket aplikacije
│   │   │   ├── controller/TaskController                                          # Kontrolerji za REST API
│   │   │   ├── model/Task                                                         # Modeli (entitete)
│   │   │   ├── repository/TaskRepository                                          # Vmesniki za dostop do podatkov
│   │   │   └── service/TaskService                                                # Logika storitev
│   │   └── ...
│   ├── src/main/resources/application/properties                                  # Viri 
│   └── pom.xml                                                                    # Konfiguracijska datoteka za Maven
│
└── README.md                                                                      # Dokumentacija projekta

```
## Baza podatkov

Ta projekt uporablja **MySQL** kot sistem za upravljanje baz podatkov. Vsebuje tabelo za shranjevanje to-do predmetov. Spodaj so informacije o nastavitvah in strukturi baze podatkov.

### Namestitev MySQL

1. **Prenesite in namestite MySQL**: Uporabite uradno [MySQL spletno stran](https://dev.mysql.com/downloads/) za prenos in namestitev.
2. **Ustvarite bazo podatkov**: Po namestitvi se prijavite v MySQL in ustvarite novo bazo podatkov:
   ```sql
   CREATE DATABASE todo_db;



**SpringBoot Setup (backend)**: 

 **Visit the Spring Initializr**: Go to [https://start.spring.io/].

 **Configure the Project**:
   - Choose *Maven* or *Gradle* as the project type.
   - Select *Java* as the language.
   - Enter metadata like *Group*, *Artifact*, and *Name*.

 **Add Dependencies**: Select the necessary dependencies (e.g., Spring Web, Spring Data JPA, MySQL Driver, Spring Boot DevTools).

 **Generate the Project**: Click *Generate* to download a ZIP file.

 **Extract and Open**: Extract the ZIP file and open the project in your IDE.

 **Make sure the database is connected and updated properly.**

 **Run the Application**: Use the command `./mvnw spring-boot:run` to start the application Or the **RUN button**.
   


 **Vite with React setup (frontend)**:
   -Navigate to the frontend directory
   -Install all necessary dependencies 
      npm create vite@latest (project name)
      navigate to project directory
      run npm install (to install necessary dependencies)
      run npm i bootstrap (**bootstrap**)
      run npm i react-dom (**react-dom**)
      run npm i react (**react**)
      npm run dev (to start application) which should run here: http://localhost:5173


### **Environmental Variables [Spremenljivke okolja] 

6. **Setup**:
   create an .env file inside the backend/.env file:  This is required to store sensitive database credentials
   SPRING_DATASOURCE_URL=jdbc:mysql://localhost:(**portnumber**)/(**database project name**)?useSSL=false&serverTimezone=UTC
   SPRING_DATASOURCE_USERNAME=(**database usernames**)
   SPRING_DATASOURCE_PASSWORD=(**password**)

**Update the database connection details**: inside application.properties (found in backend/src/main/resources/application/properties)
spring.application.name= (**DB_URL**)
server.port=8080 ()
server.servlet.context-path=
server.error.include-message=always

# Other properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect


### Component Overview [Pregled komponent]

The application is composed of the following components:

**Frontend**: Developed in React using Vite for fast development. It provides the user interface for adding, editing, and deleting tasks.
**Backend**: Developed in Spring Boot, it provides a REST API for communication with the frontend and manages data in the MySQL database.
**Database**: MySQL, stores task data, user information, and other necessary details.



### REST API Endpoints
Overview

The backend provides the following REST API endpoints for managing tasks:

**Method**	**Endpoint**	         **Description**
GET	/api/tasks	         Retrieves a list of all tasks
GET	/api/task/{id}	      Retrieves a task by its ID
POST	/api/task	         Creates a new task
PUT	/api/tasks/{id}	   Updates an existing task
DELETE	/api/tasks/{id}	Deletes a task by its ID




### Scripts [Skripte]
**Frontend Scripts**

In the frontend directory, you can use the following scripts:

    npm install: Installs all necessary dependencies.
    npm run dev: Starts the development server.
    npm run build: Builds the production version of the application.

**Backend Scripts**

In the backend directory, you can use the following scripts:

    ./mvnw clean install: Cleans and prepares the project.
    ./mvnw spring-boot:run: Starts the Spring Boot application or run application the **Run button**.




### Usage [Uporaba]

   After setting up the application and running both the frontend and backend with database configurations, you can:

    Access the application by navigating to http://localhost:5173 in your web browser.
    Use the interface to add, edit, mark as complete, or delete tasks.
    The changes will be reflected in the MySQL database via the Spring Boot API.




### Contribution [Prispevanje]

   Contibutions are always welcome.
   Just follws these steps
   **fork the repository**: https://github.com/Gjoce/To-do-application.git
   **create a new branch**: git checkout -b "your branch"
   **make changes and commit**: git commit -m "add new changes"
   **push to the branch**: git push origin "your branch",
   **open a pull request with a detailed description of your changes**





### Additional Information [Dodatne informacije]

    Ensure that both the MySQL server and the backend server are running before starting the frontend.
    The frontend will communicate with the backend at http://localhost:8080 by default.
    .env files should be added to .gitignore to prevent sensitive information from being uploaded to the repository.


    

### License [Licenca]

This project is licensed under the MIT License - see the LICENSE file for more details.
