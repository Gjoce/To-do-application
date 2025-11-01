# Poročilo o napakah 

**Projekt:** To-Do Application  
**Avtor:** Anastasija Nechoska  
**Datum:** 1. 11. 2025  
**Verzija aplikacije:** 1.0  
**Okolje:**
- Backend: Spring Boot (8080)
- Frontend: React (5173)
- Baza: MySQL (`todo_db`)

---

## 🔹 Napaka 1 – Dogodek polno zaseden brez pravilnega sporočila

| Atribut    | Vrednost                                                   |
|------------|------------------------------------------------------------|
| ID         | TD-BUG-001                                                 |
| Naziv      | Napaka pri prijavi na dogodek, ko je dogodek polno zaseden |
| Lokacija   | `EventService.java:38`                                     |
| Vrsta      | Logična napaka / neobdelana izjema                         |
| Resnost    | Major                                                      |
| Prioriteta | Visoka                                                     |
| Status     | ✅ Zaprta                                                   |

### Opis
Uporabnik se lahko še vedno prijavi na dogodek, ki je že polno zaseden, kar povzroči izjemo `IllegalStateException: Event is fully booked.`

**Pričakovano:** Sistem mora prikazati uporabniku prijazno sporočilo, brez strežniške napake.

**Popravek:** Dodan `if` pogoj v `EventService` in vrnjen `BAD_REQUEST` z uporabniškim sporočilom.

**Rezultat:** ✅ Retest uspešen – sistem pravilno obravnava polne dogodke.

---

## 🔹 Napaka 2 – Task status se ne osveži po spremembi

| Atribut    | Vrednost                                                          |
|------------|-------------------------------------------------------------------|
| ID         | TD-BUG-002                                                        |
| Naziv      | Status naloge se ne posodobi v uporabniškem vmesniku po spremembi |
| Lokacija   | `TaskController.js` (frontend)                                    |
| Vrsta      | UI napaka / manjkajoča re-render logika                           |
| Resnost    | Minor                                                             |
| Prioriteta | Srednja                                                           |
| Status     | ✅ Popravljena                                                     |

### Opis
Ko uporabnik označi nalogo kot "Done", sprememba se shrani v bazo, vendar se UI ne osveži takoj – uporabnik mora ročno osvežiti stran.

**Pričakovano:** Po kliku na “Done” naj se status v tabeli takoj posodobi.

**Vzrok:** Manjkajoča posodobitev lokalnega stanja (`setTasks()` ni bila poklicana po PATCH klicu).

**Popravek:**
```javascript 
await axios.patch(`/api/tasks/${id}`, { status: "Done" });
setTasks(prev => prev.map(t => t.id === id ? { ...t, status: "Done" } : t));

```
---
## 🔹 Napaka 3 – Datum naloge ni validiran

| Atribut    | Vrednost                                                    |
|------------|-------------------------------------------------------------|
| ID         | TD-BUG-003                                                  |
| Naziv      | Nalogo je mogoče ustvariti z datumom v preteklosti          |
| Lokacija   | `TaskService.java`                                          |
| Vrsta      | Validacijska napaka                                         |
| Resnost    | Medium                                                      |
| Prioriteta | Srednja                                                     |
| Status     | ✅ Popravljena                                               |

### Opis
Pri ustvarjanju nove naloge sistem ne preveri, ali je vneseni rok (datum) že pretekel.  
S tem uporabnik lahko ustvari nalogo z datumom v preteklosti, kar je neželeno vedenje.

**Pričakovano:** Sistem mora zavrniti ustvarjanje naloge z datumom, ki je pred trenutnim dnem.

**Vzrok:** Pomanjkanje preverjanja datuma v `createTask()` metodi.

**Popravek:**
```java
if (task.getDueDate().isBefore(LocalDate.now())) {
    throw new IllegalArgumentException("Datum naloge ne sme biti v preteklosti.");
}
```
---
## Življenjski cikel napake

**Odkrivanje (Detection)** – Napako zazna tester ali uporabnik.

**Zabeležitev (Reporting)** – Vnese se v sistem za sledenje (npr. GitHub Issues) s podatki: opis, resnost, lokacija, koraki ponovitve.

**Dodelitev (Assignment)** – Napaka se dodeli odgovornemu razvijalcu.

**Analiza in odprava (Fixing)**– Razvijalec popravi kodo in doda test.

**Retest (Verification)** – Tester preveri, ali je popravek uspešen.

**Zaprtje (Closure)** – Napaka se označi kot Zaprta; če se znova pojavi, se ponovno odpre.
