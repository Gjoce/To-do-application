# Dokumentacija unit testov za `TaskService`

Ta dokument opisuje enotske teste (unit tests) za razred `TaskService`.  
Vsaka funkcija je testirana v pozitivnem, negativnem in robnem (edge) primeru, da se zagotovi zanesljivost in popolna pokritost kode.

---

## 1. `getTaskById(Long id)`

| **Vidik** | **Podrobnosti** |
|------------|-----------------|
| **Namen** | Preveriti, ali storitev pravilno pridobi opravilo glede na ID ali vrne prazno vrednost, če opravilo ne obstaja. |
| **Vhodni podatki** | `id: Long` – enolični identifikator opravila |
| **Izhod** | `Optional<Task>` – najdeno opravilo ali prazno |
| **Odvisnosti** | `TaskRepository.findById(Long id)` |
| **Testni primeri** | |
| **Pozitivni primer** | Repozitorij vrne veljaven objekt `Task` → pričakujemo, da je `Optional` prisoten in ima pravilen ID. |
| **Negativni primer** | Repozitorij vrne prazno vrednost, ko ID ne obstaja → pričakujemo `Optional.empty()`. |
| **Robni primer** | ID = 0 ali negativen → pričakujemo `Optional.empty()`, brez izjeme. |

---

## 2. `createTask(Task task, Long userId)`

| **Vidik** | **Podrobnosti** |
|------------|-----------------|
| **Namen** | Preveriti ustvarjanje novega opravila za obstoječega uporabnika z veljavnimi podatki. |
| **Vhodni podatki** | `task: Task`, `userId: Long` |
| **Izhod** | `Task` – novo ustvarjen in shranjen objekt |
| **Odvisnosti** | `UserRepository.findById(Long userId)`, `TaskRepository.save(Task task)` |
| **Testni primeri** | |
| **Pozitivni primer** | Veljaven `task` in obstoječ uporabnik → opravilo se shrani in vrne. |
| **Negativni primer (manjkajoč uporabnik)** | Uporabnik z danim ID-jem ne obstaja → pričakujemo `IllegalArgumentException("User with ID ... not found")`. |
| **Negativni primer (null opravilo)** | `task` je `null` → pričakujemo `IllegalArgumentException("Task cannot be null")`. |
| **Robni primer (neveljavno uporabniško ime)** | Uporabnik obstaja, vendar ima prazno uporabniško ime → pričakujemo `IllegalArgumentException("User data is incomplete or invalid")`. |

---

## 3. `updateTask(long id, Task updatedTask)`

| **Vidik** | **Podrobnosti** |
|------------|-----------------|
| **Namen** | Preveriti posodabljanje obstoječega opravila z novimi podatki. |
| **Vhodni podatki** | `id: long`, `updatedTask: Task` |
| **Izhod** | `Optional<Task>` – posodobljeno opravilo, če obstaja, sicer prazno |
| **Odvisnosti** | `TaskRepository.findById(Long id)`, `TaskRepository.save(Task task)` |
| **Testni primeri** | |
| **Pozitivni primer** | Opravilo z ID obstaja in ima nove veljavne podatke → pričakujemo posodobljeno opravilo. |
| **Negativni primer** | Opravilo z ID ne obstaja → pričakujemo `Optional.empty()`. |
| **Robni primer** | Nekatera polja (npr. opis ali naslov) so `null` → pričakujemo, da se ostala polja vseeno posodobijo. |

---

## 4. `deleteTask(Long id)`
| **Vidik** | **Podrobnosti** |
|------------|-----------------|
| **Namen** | Preveriti pravilno brisanje opravila glede na ID in obravnavo primerov, kjer opravilo ne obstaja ali je ID neveljaven. |
| **Vhodni podatki** | `id: Long` |
| **Izhod** | `void` – izbriše opravilo ali vrže izjemo |
| **Odvisnosti** | `TaskRepository.existsById(Long id)`, `TaskRepository.deleteById(Long id)` |
| **Testni primeri** | |
| **Pozitivni primer** | Opravilo obstaja → metoda `deleteById` se pokliče enkrat, brez izjeme. |
| **Negativni primer** | Opravilo ne obstaja → pričakujemo `IllegalArgumentException("Task with ID ... not found")`. |
| **Robni primeri (edge cases)** | 1. ID = 0 → pričakujemo `IllegalArgumentException`<br>2. ID < 0 → pričakujemo `IllegalArgumentException`<br>3. ID = null → pričakujemo `IllegalArgumentException` |

---

## 5. `updateFavoriteStatus(long taskId, boolean isFavorite)`

| **Vidik** | **Podrobnosti** |
|------------|-----------------|
| **Namen** | Preveriti posodabljanje statusa priljubljenosti opravila. |
| **Vhodni podatki** | `taskId: long`, `isFavorite: boolean` |
| **Izhod** | `Optional<Task>` – posodobljeno opravilo, če obstaja, sicer prazno |
| **Odvisnosti** | `TaskRepository.findById(Long id)`, `TaskRepository.save(Task task)` |
| **Testni primeri** | |
| **Pozitivni primer** | Opravilo obstaja → status `favorite` se pravilno posodobi in shrani. |
| **Negativni primer** | Opravilo ne obstaja → pričakujemo `Optional.empty()`. |
| **Robni primer** | `isFavorite = false` za opravilo, ki je že `false` → pričakujemo, da se vrne enak objekt (idempotentno vedenje). |

---

## Povzetek

| **Funkcija** | **Pozitivni primer** | **Negativni primer** | **Robni primer** |
|---------------|----------------------|-----------------------|------------------|
| `getTaskById` | Vrne pravilno opravilo | Opravilo ne obstaja | ID = 0 |
| `createTask` | Ustvari opravilo za veljavnega uporabnika | Neveljaven uporabnik ali opravilo | Prazno uporabniško ime |
| `updateTask` | Posodobi obstoječe opravilo | Opravilo ne obstaja | Delno manjkajoča polja |
| `deleteTask` | Izbriše opravilo | Opravilo ne obstaja | Neveljaven ID |
| `updateFavoriteStatus` | Posodobi status priljubljenosti | Opravilo ne obstaja | Brez spremembe, a konsistentno |

