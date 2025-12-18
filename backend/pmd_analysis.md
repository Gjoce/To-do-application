# Analiza razreda *TaskService* z uporabo PMD in predlogi preoblikovanj

## 1. Analiza z orodjem PMD

Za analizo kode je bilo uporabljeno orodje **PMD**, pri čemer so bila upoštevana pravila iz kategorije *Design* (npr. *TooManyMethods*). Na podlagi rezultatov analize je bil razred **TaskService** prepoznan kot najbolj kompleksen razred v sistemu.

Razred vsebuje veliko število metod in združuje več različnih odgovornosti:
- izvajanje CRUD operacij nad entiteto `Task`,
- validacijo vhodnih podatkov,
- poslovno logiko (upravljanje priljubljenih opravil, filtriranje po statusu),
- neposredno uporabo več repozitorijev (`TaskRepository`, `UserRepository`).

Takšna zasnova kaže na nizko kohezijo in visoko sklopljenost, kar je značilno za **God Class**. Čeprav PMD pravilo *GodClass* ni bilo neposredno sproženo, kombinacija več kršitev pravil iz kategorije *Design* utemeljuje obravnavo razreda `TaskService` kot God Class.

---

## 2. Cilj preoblikovanja

Cilj preoblikovanja je zmanjšati kompleksnost razreda, izboljšati kohezijo ter razdeliti odgovornosti na več manjših, bolj specializiranih razredov. Preoblikovanja so opisana konceptualno in niso dejansko implementirana v kodi.

---

## 3. Predlagana preoblikovanja (PMD → Preoblikovanje → Katalog)

### 1. Extract Class (Izločitev razreda)

Validacijska logika v metodi `createTask` (preverjanje obstoja uporabnika, preverjanje veljavnosti podatkov) bi bila izločena v nov razred **TaskValidationService**.

**Učinek:**
- večja kohezija razreda `TaskService`,
- ločitev validacijske logike od poslovne logike.

---

### 2. Move Method (Premik metod)

Metodi `getFavoriteTasksByUser` in `updateFavoriteStatus` bi bili premaknjeni v ločen razred **TaskFavoriteService**.

**Učinek:**
- zmanjšanje števila metod v razredu `TaskService`,
- jasnejša razdelitev poslovnih odgovornosti.

---

### 3. Extract Class (Izločitev razreda)

Funkcionalnost, povezana s filtriranjem opravil po statusu (`getTasksByStatus`), bi bila izločena v razred **TaskStatusService**.

**Učinek:**
- izboljšana modularnost sistema,
- boljša berljivost in vzdrževanje kode.

---

### 4. Split Class (Razdelitev razreda)

Razred `TaskService` bi bil razdeljen na:
- **TaskCommandService** (ustvarjanje, posodabljanje in brisanje opravil),
- **TaskQueryService** (pridobivanje in iskanje opravil).

**Učinek:**
- jasna ločitev med ukazi in poizvedbami,
- zmanjšana kompleksnost posameznih razredov.

---

### 5. Introduce Interface (Uvedba vmesnika)

Uvedel bi se vmesnik **ITaskService**, ki bi ga implementirala razreda `TaskCommandService` in `TaskQueryService`.

**Učinek:**
- zmanjšana sklopljenost,
- izboljšana testabilnost in razširljivost sistema.

---

## 4. Preverjanje po preoblikovanju

Po izvedbi opisanih preoblikovanj bi se analiza z orodjem PMD ponovila. Pričakovani rezultat bi bil:
- manj ali nič kršitev pravil *TooManyMethods*,
- odsotnost razredov, ki bi združevali večino logike sistema.

S tem bi bila pomanjkljivost tipa God Class uspešno odpravljena.

---

## 5. Zaključek

Na podlagi PMD analize je bil razred `TaskService` prepoznan kot kandidat za God Class zaradi visoke kompleksnosti, nizke kohezije in večih odgovornosti. Z uporabo petih preoblikovanj iz kataloga preoblikovanj bi bilo mogoče izboljšati strukturo sistema, zmanjšati sklopljenost ter povečati berljivost in vzdrževanje kode.
