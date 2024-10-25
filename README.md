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


