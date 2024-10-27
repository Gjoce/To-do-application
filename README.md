# Aplikacija To-Do

## Pregled
Ta projekt je **Aplikacija To-Do**, ki sestoji iz **React frontenda** (uporaba Vite) in **Spring Boot backend-a**, povezanega z **MySQL bazo podatkov**. Frontend je razvit v **Visual Studio Code**, medtem ko je backend v **IntelliJ IDEA**. Ta postavitev nudi celostno rešitev z modernim uporabniškim vmesnikom in skalabilnim backend-om.

## Kazalo vsebine
- [Tehnologije](#tehnologije)
- [Pregled komponent](#pregled-komponent)
- [Začnite](#začnite)
  - [Predpogoji](#predpogoji)
  - [Namestitev](#namestitev)
    - [Namestitev MySQL](#namestitev-mysql) 
    - [Namestitev Frontenda](#namestitev-frontenda)
    - [Namestitev Backenda](#namestitev-backenda)
- [Struktura projekta](#struktura-projekta)
- [Standardi kodiranja](#standardi-kodiranja)
- [REST API koncne tocke](#rest-api-koncne-tocke)
- [Skripte](#skripte)
- [Uporaba](#uporaba)
- [Prispevanje](#prispevanje)
- [Dodatne informacije](#dodatne-informacije)
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


## Pregled komponent 

**Aplikacija je sestavljena iz naslednjih komponent:**

  - ***Frontend:*** Razvit v Reactu z uporabo Vite za hitro razvijanje. Nudi uporabniški vmesnik za dodajanje, urejanje, filtriranje in brisanje nalog.

  - ***Backend:*** Razvit v Spring Boot, nudi REST API za komunikacijo s frontend-om in upravlja podatke v MySQL bazi podatkov.

  - ***Baza podatkov:*** MySQL, shranjuje podatke nalog, informacije o uporabnikih in druge potrebne podrobnosti.


## Začnite

### Predpogoji
- **Node.js** (zadnja LTS različica)
- **Java SDK 21**
- **MySQL** (preverite, ali deluje strežnik MySQL)
- **IntelliJ IDEA(2024.2.0.2)** (za razvoj backend-a)
- **Visual Studio Code** (za razvoj frontend-a)

### Namestitev

1. **Klonirajte repozitorij:**
   ```bash
   git clone https://github.com/Gjoce/To-do-application.git

#### Namestitev MySQL

1. **Prenesite in namestite MySQL**: Uporabite uradno [MySQL spletno stran](https://dev.mysql.com/downloads/) za prenos in namestitev.
2. Ce zelite manualno zagnati MySQL serverja lahko to naredite na naslednji nacin. Preden izvedite naslednji ukaz pojdite v MySQL root direktorij:
     ```bash
     .\bin\mysqld.exe --defaults-file="ini.ini" --console
4. **Ustvarite bazo podatkov**: Po namestitvi se prijavite v MySQL in ustvarite novo bazo podatkov:
   ```sql
   CREATE DATABASE todo_db;

#### Namestitev Frontenda

1. **Pojdite v frontend:**
   ```bash
   cd frontend

2. **Nalozite ustrezne konfiguracije:**
   ```bash
   npm install

3. **Zaženite razvojni strežnik:**
   ```bash
   npm run dev

#### Namestitev Backenda

1. **Nastavite .env datoteko:**
     V korenu backend direktorija ustvarite .env datoteko.
     Dodajte konfiguracijo za MySQL:
   ```bash
         SPRING_DATASOURCE_URL={VAS URL V OBLIKI} jdbc:mysql://localhost:[PORT]/[DATABAZA]?useSSL=false&serverTimezone=UTC
         SPRING_DATASOURCE_USERNAME={VAS USERNAME}
         SPRING_DATASOURCE_PASSWORD={VASE GESLO}
  Poskrbite, da imate v IntelliJ ***nameščen .env plugin***, da lahko Spring Boot prepozna .env datoteko.

2. **Konfigurirajte IntelliJ za uporabo .env:**

    Odprite Run/Debug Configurations v IntelliJ.
    Pod ***Environment Variables*** dodajte .env datoteko.
  
3. **Zaženite backend:**

    V IntelliJ odprite meni ***Run*** in zaženite backend strežnik.
    Vaša Spring Boot aplikacija se bo povezala na MySQL podatkovno bazo, kot je določeno v konfiguraciji, in se zagnala na http://localhost:8080        ali na portu, določenem v application.properties.


## Struktura projekta

Struktura projekta je organizirana na naslednji način:

```plaintext
project-root/
│
├── frontend/                                  # React frontend (Vite setup)
│   ├── public/                                
│   ├── src/                                   # Source datoteke
│   │   ├── assets/                            # Staticne datoteke kot img
│   │   ├── components/                        # Komponente
│   │   │   ├── AddTask.tsx                    # Dodaj nalogo
│   │   │   ├── Footer.tsx                     # Footer komponent
│   │   │   ├── Navbar.tsx                     # Navbar komponent
│   │   │   ├── Task.tsx                       # Task komponent
│   │   │   ├── TaskList.tsx                   # Prikaz
│   │   │   └── UpdateTaskPopup.tsx            # Posodabljanje nalogo
│   │   ├── App.tsx                            # Main app komponento
│   │   ├── main.tsx                           # Vhodna mesto za aplikacijo
│   │   └── Footer.css                         # CSS 
|   |   └── Navbar.css
|   |   └── TaskCard.css                 
│   └── package.json                           # KOnfiguracijska dattoteka za npm
│
├── backend/                                   # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── si/um/si/                  # Java struktura
│   │   │   │       ├── controller/            # REST kontrolerji
│   │   │   │       │   └── TaskController.java# KOntroler za API
│   │   │   │       ├── model/                 # Data modeli (entiteti)
│   │   │   │       │   ├── Task.java          # Task enititeta
│   │   │   │       │   └── enums/             # Enums 
│   │   │   │       │       ├── TaskPriority.java
│   │   │   │       │       └── TaskStatus.java
│   │   │   │       ├── repository/            # Repozitorij za data access
│   │   │   │       │   └── TaskRepository.java
│   │   │   │       └── service/               # Business logika
│   │   │   │           └── TaskService.java
│   │   │   └── resources/
│   │   │       ├── application.properties     # Konfiguracijski podatki
│   │   │       └── static/                    # Staticne datoteke
│   │   │           └── assets/
│   │   │                                      # Static HTML file
│   │   └── test/                              # Test files za backend
│   ├── pom.xml                                # Maven konfiguracija
|    └── .env                                  #.env za environment variables                            
│
└── README.md                                  # Dokumentacija
└── .gitignore                                 # Datoteke v gitignore
                                      


```

## Standardi Kodiranja

### Imenovanje
- **Spremenljivke**: Imenovanje spremenljivk je v camelCase formatu. 
- **Funkcije in metode**: Ime funkcij in metod so prav tako v camelCase formatu, je pa opisno in se začne z glagolom (npr. `getTaskById`).

### Struktura datotek
- **Frontend**:
  - Komponente so v isti mapi (npr. `Navbar.tsx` in `TaskList.tsx`).
  - Glavne datoteke in konfiguracije, kot je `App.tsx`, so v `src/`.
- **Backend**:
  - Sledi čisti strukturi po odgovornostih (npr. `controller`, `model`, `service`).

### Stilski standardi
- **Indentacija**: Uporabi 2 ali 4 presledke za indentacijo (sledi standardom ekipe).
- **Komentiranje**: Uporabi komentarje za pojasnitev kompleksne logike, vendar se izogibaj prekomernemu komentiranju očitnega.
- **Dolžina vrstice**: Priporočljiva dolžina vrstice je 80-100 znakov.


## REST API koncne tocke

Backend nudi naslednje REST API končne točke za upravljanje nalog:

| **Metoda** | **Končna točka**       | **Opis**                              |
|------------|------------------------|---------------------------------------|
| GET        | /api/tasks            | Pridobi seznam vseh nalog            |
| GET        | /api/task/{id}        | Pridobi nalogo po njenem ID-ju       |
| POST       | /api/task             | Ustvari novo nalogo                   |
| PUT        | /api/tasks/{id}       | Posodobi obstoječo nalogo             |
| DELETE     | /api/tasks/{id}       | Izbriše nalogo po njenem ID-ju       |



## Skripte

Tu so skripte, ki jih uporabljate v namestitvi za zagon projekta:

- **Namestitev za frontend:**
  ```bash
  npm install
  npm run dev
- **Namestitev za MySQL:**
  ```bash
  .\bin\mysqld.exe --defaults-file="ini.ini" --console
 
- **Namestitev za backend(.env datoteka):**
   ```bash
   SPRING_DATASOURCE_URL={VAS URL V OBLIKI} jdbc:mysql://localhost:[PORT]/[DATABAZA]?useSSL=false&serverTimezone=UTC
   SPRING_DATASOURCE_USERNAME={VAS USERNAME}
   SPRING_DATASOURCE_PASSWORD={VASE GESLO}
  

### Uporaba

Po nastavitvi aplikacije in zagonu tako frontenda kot backenda s konfiguracijami baze podatkov lahko:

- Dostopate do aplikacije z obiskom `http://localhost:5173` v vašem spletnem brskalniku.
- Uporabite vmesnik za dodajanje, urejanje, označevanje kot zaključeno ali brisanje nalog.
- Spremembe bodo vidne v MySQL bazi podatkov preko Spring Boot API-ja.


### Prispevanje

Prispevki so vedno dobrodošli! Sledite tem korakom:

1. **Forkajte repozitorij**: [https://github.com/Gjoce/To-do-application.git](https://github.com/Gjoce/To-do-application.git)
2. **Ustvarite novo vejo**: 
   ```bash
   git checkout -b "ime-veje"
3. **Naredite spremembe in committajte:**
   ```bash
   git commit -m "dodane nove spremembe"
4. **Potisnite spremembe na vejo:**
   ```bash
   git push origin "ime-veje"
5. **Odprite pull request s podrobnim opisom svojih sprememb**


### Dodatne informacije

- Prepričajte se, da sta tako MySQL strežnik kot backend strežnik zagnana, preden zaženete frontend.
- Frontend bo privzeto komuniciral z backendom na naslovu `http://localhost:8080`.
- `.env` datoteke dodajte v `.gitignore`, da preprečite nalaganje občutljivih informacij v repozitorij.

### Licenca

Ta projekt trenutno ni licenciran. Za prihodnje spremembe licenciranja spremljajte ta repozitorij.

