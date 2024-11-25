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
- **Spring Boot 3.3.4** (Maven)
- **MySQL Connector (8.0.33)**
- **Java Dotenv (5.2.2)**
- **Java SDK 21**
- **MySQL** (za bazo podatkov)


## Pregled komponent 

**Aplikacija je sestavljena iz naslednjih komponent:**

  - ***Frontend:*** Razvit v Reactu z uporabo Vite za hitro razvijanje. Nudi uporabniški vmesnik za dodajanje, urejanje, filtriranje in brisanje nalog.

  - ***Backend:*** Razvit v Spring Boot, nudi REST API za komunikacijo s frontend-om in upravlja podatke v MySQL bazi podatkov.

  - ***Baza podatkov:*** MySQL, shranjuje podatke nalog, informacije o uporabnikih in druge potrebne podrobnosti.
### 1. UserController
**Opis:**
`UserController` je odgovoren za upravljanje uporabnikov, vključno z registracijo, posodabljanjem profilov in pridobivanjem podatkov, povezanih z uporabniki, kot so naloge ali dogodki. Deluje kot vstopna točka za vse funkcionalnosti, povezane z uporabniki.  

**Ključna metoda:**
```bash
registerUser(@RequestBody user: User): User
```
**Namen:**
Omogoča registracijo uporabnika z vnosom uporabniških podatkov in shranjevanjem uporabnika v bazo podatkov.  
**Primer uporabe:**
Nov uporabnik se prijavi v sistem z vnosom svojega imena, e-pošte in gesla.  

### 2. TaskController 
**Opis:**
`TaskController` omogoča upravljanje nalog z nudenjem endpointov za ustvarjanje, pridobivanje, posodabljanje in brisanje nalog. Prav tako omogoča filtriranje nalog glede na njihov status (npr. `COMPLETED`, `PENDING`).   

**Ključna metoda:**
```bash
`getAllTasks(@RequestParam(required = false) status: String): List<Task>`  
```
**Namen:**
Pridobi vse naloge v sistemu. Če je parameter `status` podan, naloge filtrira glede na ta status.  
**Primer uporabe:**
Uporabnik pregleda vse svoje naloge ali jih filtrira, da vidi samo naloge, ki so v čakanju (`PENDING`).    

### 3. EventController 
**Opis:**
`EventController` upravlja operacije, povezane z dogodki, vključno z ustvarjanjem, posodabljanjem in pridobivanjem dogodkov. Omogoča tudi upravljanje udeležencev dogodkov in filtriranje dogodkov glede na tip.    

**Ključna metoda:**
```bash
`getAllEvents(@RequestParam(required = false) type: String): List<Event>`  
```
**Namen:**
 Pridobi vse dogodke. Če je parameter `type` podan, dogodke filtrira glede na določeni tip.    
**Primer uporabe:**
Uporabnik pregleda vse razpoložljive dogodke ali jih filtrira, da vidi samo delavnice (`workshops`).  

### 3. Task Model
**Opis:**
•	Task je entiteta, ki predstavlja naloge v sistemu. Vsaka naloga vsebuje naslov, opis, status, prioriteto, rok za dokončanje ter časovne oznake za ustvarjanje in posodabljanje naloge. Naloge imajo različne statuse (npr. PENDING, COMPLETED) in prioritete (npr. LOW, HIGH), ki jih je mogoče filtrirati za boljše upravljanje nalog.
•	Status in prioriteta naloge sta shranjena kot Enum tipa, kar omogoča boljšo obvladovanje teh vrednosti v aplikaciji.
•	Časovne oznake createdAt in updatedAt se samodejno posodabljajo ob ustvarjanju in spreminjanju naloge.

**Ključne lastnosti:**
•	Id: Unikatni identifikator naloge.
•	Title: Naslov naloge, ki je obvezen.
•	Description: Opis naloge, ki je neobvezen in lahko vsebuje do 1000 znakov.
•	Status: Status naloge (npr. PENDING, COMPLETED), ki je obvezen.
•	Priority: Prioriteta naloge (npr. LOW, HIGH), ki je obvezen.
•	DueDate: Rok za dokončanje naloge, ki je neobvezen.
•	CreatedAt: Datum in čas, ko je bila naloga ustvarjena (samodejno nastavljeno).
•	UpdatedAt: Datum in čas, ko je bila naloga nazadnje posodobljena (samodejno nastavljeno).

   

**Ključna metoda:**
```bash
@PrePersist
protected void onCreate() {
    this.createdAt = LocalDateTime.now();
}

@PreUpdate
protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
}
 
```
**Namen:**
 Avtomatsko posodabljanje 
**Taskpriority Enum(prioriteto naloge)**
	LOW
	MEDIUM	
	HIGH
 **Taskstatus Enum(status naloge)**
	PENDING
	RUNNING	
	COMPLETED

**Primer uporabe:**
•	Ko uporabnik ustvari novo nalogo, vključi naslov, opis, status, prioriteto, ter datum, do katerega mora biti naloga zaključena. Ob tem se naloga shrani v bazo podatkov, kjer se beležijo podatki o času ustvarjanja in zadnjem posodabljanju.

### 4. Event Model
**Opis:**
•	Event je entiteta, ki predstavlja dogodke v sistemu. Vsak dogodek vsebuje naslov, opis, tip dogodka, status, prioriteto, datum začetka, datum zaključka ter časovne oznake za ustvarjanje in posodabljanje dogodka. Dogodki imajo različne statuse (npr. PENDING, COMPLETED) in prioritete (npr. LOW, HIGH), ki jih je mogoče filtrirati za boljše upravljanje dogodkov.
•	Tip dogodka in status sta shranjena kot Enum tipa, kar omogoča boljše obvladovanje teh vrednosti v aplikaciji.
•	Časovne oznake createdAt in updatedAt se samodejno posodabljajo ob ustvarjanju in spreminjanju dogodka.


**Ključne lastnosti:**
•	Id: Unikatni identifikator dogodka.
•	Title: Naslov dogodka, ki je obvezen.
•	Description: Opis dogodka, ki je neobvezen in lahko vsebuje do 1000 znakov.
•	DueDate: Datum in cas, kdaj je dogodek.
•	CreatedAt: Datum in čas, ko je bil dogodek ustvarjen (samodejno nastavljeno).
•	UpdatedAt: Datum in čas, ko je bil dogodek nazadnje posodobljen (samodejno nastavljeno).

**Ključna metoda:**
```bash
@PrePersist
protected void onCreate() {
    this.createdAt = LocalDateTime.now();
}

@PreUpdate
protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
}

 
```
**Namen:**
 Avtomatsko posodabljanje 

**Primer uporabe:**
•	Ko admin ustvari nov dogodek, vključi naslov, opis, ter datum začetka in zaključka dogodka. Ob tem se dogodek shrani v bazo podatkov, kjer se beležijo podatki o času ustvarjanja in zadnjem posodabljanju.

### 5. User Model
**Opis:**
•	User je entiteta, ki predstavlja uporabnike v sistemu. Vsak uporabnik vsebuje ime, e-poštni naslov, geslo ter vlogo (npr. ADMIN, USER), ki določa, kakšne privilegije ima uporabnik v aplikaciji. Vloga uporabnika je shranjena kot Enum tipa, kar omogoča enostavno dodeljevanje različnih pravic glede na uporabniško vlogo.
•	Geslo uporabnika je shranjeno v šifrirani obliki, kar zagotavlja varnost uporabniških podatkov.


**Ključne lastnosti:**
•	Id: Unikatni identifikator uporabnika.
•	username: Ime uporabnika, ki je obvezen.
•	Email: E-poštni naslov uporabnika, ki je obvezen in mora biti edinstven.
•	Password: Geslo uporabnika, ki je obvezen in shranjeno v šifrirani obliki.
•	Role: Vloga uporabnika (npr. ADMIN, USER), ki je obvezen in določa pravice uporabnika v aplikaciji.

**Role Enum:**
•	ADMIN: Uporabnik z vsemi privilegiji za upravljanje sistema.
•	USER: Običajen uporabnik, ki ima omejen dostop do funkcionalnosti aplikacije.


**Primer uporabe:**
•	Ko uporabnik ustvari nov račun, vnese svoje ime, e-poštni naslov in geslo. Sistem dodeli vlogo uporabniku, bodisi kot USER bodisi kot ADMIN, odvisno od pravic, ki so potrebne. Ob tem se uporabnik shrani v bazo podatkov, kjer se beležijo podatki o času ustvarjanja in zadnjem posodabljanju.

### 6. TaskService Razred
**Opis:**
Logika za upravljanje nalog. Komunicira s TaskRepository za pridobivanje in obdelavo podatkov.

**Ključna metoda:**
```bash
•	getTasksByUserId(userId: Long): List<Task> – Pridobi naloge za določenega uporabnika.
•	getTasksByStatus(status: String): List<Task> – Pridobi naloge glede na status.

 
```
**Namen:**
 Obdeluje zahteve za naloge ter omogoča ustvarjanje, posodabljanje, pridobivanje in brisanje.

**Primer uporabe:**
• Pridobi naloge za določenega uporabnika.
•	Uporabnik pridobi naloge glede na status.

### 7. UserService Razred
**Opis:**
Upravljanje poslovne logike za uporabnike, povezano s UserRepository.

**Ključna metoda:**
```bash
•	getUserById(userId: Long): User – Pridobi uporabnika z določenim ID-jem.
•	createUser(user: User): void – Ustvari novega uporabnika.
```
**Namen:**
Izvaja iskanje, ustvarjanje, posodabljanje in brisanje uporabnikov.

**Primer uporabe:**
• Admin pridobi uporabnika z določenim ID-jem.
•	Ustvari novega uporabnika.

### 8. EventRepository Vmesnik
**Opis:**
Vmesnik za upravljanje podatkov o dogodkih.

**Ključna metoda:**
```bash
•	findEventById(eventId: Long): Event – Pridobi dogodek z določenim ID-jem.
```
**Namen:**
Določa operacije za shranjevanje, pridobivanje, posodabljanje in brisanje dogodkov.

**Primer uporabe:**
•Admin pridobi dogodek z določenim ID-jem.

### 9. TaskRepository Vmesnik
**Opis:**
Dostop do podatkov o nalogah in iskanje nalog glede na status ali uporabnika.

**Ključna metoda:**
```bash
•	findByUserId(userId: Long): List<Task> – Pridobi naloge za določenega uporabnika.
•	findByStatus(status: String): List<Task> – Pridobi naloge glede na status.

```
**Namen:**
Izvaja poizvedbe in trajno shranjevanje nalog.

**Primer uporabe:**
• Uporabniki pogledajo svoje naloge
• Uporabniki pogledajo status svoje naloge

### 10. EventService Razred
**Opis:**
Obdeluje poslovno logiko za dogodke in komunicira z EventRepository.

**Ključna metoda:**
```bash
•	getEventById(eventId: Long): Event – Pridobi dogodek z določenim ID-jem.
•	getEventsByUserId(userId: Long): List<Event> – Pridobi dogodke za uporabnika.
•	addEvent(event: Event): void – Ustvari nov dogodek.
•	updateEvent(event: Event): void – Posodobi dogodek.
•	deleteEvent(eventId: Long): void – Izbriše dogodek.


```
**Namen:**
Ustvarjanje, posodabljanje, pridobivanje in brisanje dogodkov.

**Primer uporabe:**
• Uporabnik pridobi dogodek z določenim ID-jem.
• Uporabnik pridobi vse svoje dogodke.
• Uporabnik ustvari nov dogodek.
• Uporabnik posodobi dogodek.
• Uporabnik izbrise dogodek.

### 11. UserRepository Vmesnik
**Opis:**
Upravljanje podatkov o uporabnikih.

**Ključna metoda:**
```bash
•	findByUserId(userId: Long): User – Pridobi uporabnika z določenim ID-jem.
```
**Namen:**
Izvaja operacije za shranjevanje, iskanje in brisanje podatkov o uporabnikih.

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

2. **Namestitev odvisnosti in gradnja projekta:**
    Zaženite naslednji ukaz Maven, da namestite odvisnosti in zgradite projekt
   ```bash
   mvn clean install

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

    V IntelliJ odprite meni ***Run*** in zaženite backend strežnik ali pa to naredite z naslednjim ukazom:
     ```bash
     mvn spring-boot:start
     ```
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
   mvn clean install
   ```
   ```bash
   SPRING_DATASOURCE_URL={VAS URL V OBLIKI} jdbc:mysql://localhost:[PORT]/[DATABAZA]?useSSL=false&serverTimezone=UTC
   SPRING_DATASOURCE_USERNAME={VAS USERNAME}
   SPRING_DATASOURCE_PASSWORD={VASE GESLO}
   ```
    ```bash
     mvn spring-boot:start
  

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


### Implementacija funkcionalnosti za Repository and Service

Dodali smo funkcionalnosti za upravljanje opravil (Tasks) v aplikaciji, vključno z ustvarjanjem, posodabljanjem, brisanjem in iskanjem opravil glede na njihov status. 

## Delovanje 
Ustvarjanje opravil: Uporabnik lahko ustvari novo opravilo, ki je povezano z določenim uporabnikom. To vključuje določitev naslova, opisa, statusa, prioritete in datuma zaključka.
Posodabljanje opravil: Uporabnik lahko posodobi podatke obstoječega opravila, vključno z naslovom, opisom, statusom, prioriteto in datumom zaključka.
Brisanje opravil: Opravila se lahko izbrišejo s podajanjem ID-ja.
Filtriranje opravil po statusu: Uporabnik lahko pridobi seznam opravil, ki ustrezajo določenemu statusu (npr. PENDING, COMPLETED).

### Dostop

Ustvarjanje opravil:
Pošljite POST zahtevo na /tasks z ustreznimi podatki (npr. naslov, opis, status).
Posodabljanje opravil:
Pošljite PUT zahtevo na /tasks/{id}, kjer {id} predstavlja ID obstoječega opravila.
Brisanje opravil:
Pošljite DELETE zahtevo na /tasks/{id}, kjer {id} predstavlja ID opravila, ki ga želite izbrisati.
Filtriranje po statusu:
Pošljite GET zahtevo na /tasks/status/{status}, kjer {status} predstavlja željeni status opravil.

### Implementirane funkcionalnosti za Controllers:
Upravljanje dogodkov (EventController):

-Pridobivanje vseh dogodkov: Funkcionalnost za pridobivanje vseh dogodkov, ki so shranjeni v sistemu.
-Pridobivanje dogodka po ID-ju: Omogoča iskanje specifičnega dogodka z uporabo njegovega ID-ja.
-Kreiranje novega dogodka (samo za administratorje): Omogoča administratorjem, da ustvarijo nove dogodke v sistemu. Uporabniki z drugimi vlogami nimajo dostopa do te funkcionalnosti.
-Posodabljanje obstoječega dogodka: Omogoča posodabljanje podatkov obstoječih dogodkov, kot so ime, datum in opis.
-Brisanje dogodka (samo za administratorje): Samo uporabniki z administratorskimi pravicami imajo možnost brisanja dogodkov iz sistema.
-Prikaz dogodkov, ki se bodo zgodili po določenem času: Omogoča filtriranje dogodkov, ki so načrtovani za prihodnost, na podlagi določenega datuma.
-Prikaz dogodkov, ki jih je ustvaril določen uporabnik: Omogoča iskanje dogodkov, ki so jih ustvarili določeni uporabniki.
-Prikaz dogodkov, na katerih uporabnik sodeluje: Omogoča iskanje dogodkov, na katerih je določen uporabnik prijavljen kot udeleženec.

Upravljanje nalog (TaskController):

-Pridobivanje vseh nalog (z možnostjo filtriranja po statusu).
-Pridobivanje specifične naloge po ID-ju.
-Kreiranje nove naloge, povezane z uporabnikom.
-Posodabljanje obstoječe naloge.
-Brisanje naloge.

Upravljanje uporabnikov (UserController):
-Registracija novega uporabnika.
-Prijava obstoječega uporabnika.
-Prikaz nalog, ki so dodeljene določenemu uporabniku.
-Prikaz dogodkov, na katere se je uporabnik prijavil.
-Prijava uporabnika na dogodek.

### Delovanje
Upravljanje dogodkov:
-Dogodke lahko ustvarijo, posodobijo ali izbrišejo samo administratorji. Uporabniki lahko dogodke pregledujejo in se prijavljajo nanje.
-Dogodki so dostopni prek API-ja /events, kjer so na voljo različne poti za prikaz dogodkov, filtriranje po času, in pridobivanje dogodkov določenega uporabnika.
Upravljanje nalog:
-Naloge se lahko filtrirajo po statusu (npr. "PENDING", "COMPLETED") ali pa pridobite vse naloge.
-Posamezne naloge lahko dodajate, posodabljate ali brišete.
Upravljanje uporabnikov:
-Uporabniki se lahko registrirajo prek API-ja /api/users/register in prijavijo prek /api/users/login.
-Registrirani uporabniki lahko vidijo svoje naloge in dogodke, na katere so se prijavili.

### Dostop
Zahteve po API-ju:
-Uporabite orodje, kot je Postman, ali brskalniške konzole za pošiljanje HTTP zahtev na ustrezne končne točke.
Testiranje uporabnikov:
-Registrirajte novega uporabnika prek /api/users/register z metodo POST.
-Prijavite se kot registriran uporabnik prek /api/users/login z metodo POST in pridobite dostop do specifičnih funkcionalnosti.
Testiranje dogodkov:
-Pridobite seznam vseh dogodkov prek /events z metodo GET.
-Ustvarite dogodek z metodo POST in ustreznim uporabniškim ID-jem.
-Testiranje nalog:
-Pridobite vse naloge prek /api/tasks z metodo GET.
-Filtrirajte naloge z dodatkom parametra ?status=<STATUS>.
-Dodajte novo nalogo z metodo POST in določite, kateremu uporabniku pripada.
