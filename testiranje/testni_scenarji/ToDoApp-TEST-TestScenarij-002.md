# ✅ ToDoApp-TEST-TestScenarij-002
### Funkcionalnost: Pridobivanje dogodkov uporabnika

| Aplikacija         | To-Do Application   |
|--------------------|---------------------|
| Datum scenarija    | 26. 10. 2025        |
| Verzija aplikacije | 1.0                 |
| Avtor              | Anastasija Nechoska |

---

### Kratek opis
Preveri, ali sistem pravilno vrne seznam dogodkov, na katere je uporabnik prijavljen.

**Referenca:** TD-002  
**Zahteve v SZPO:** /  
**Namen:** Validacija metode `getUserAppliedEvents(userId)` v `EventService`.

---

### Predpogoji in vhodni podatki

| Predpogoji                              | Vhodni podatki                      |
|-----------------------------------------|-------------------------------------|
| Backend teče na `http://localhost:8080` | ID uporabnika = 1 (Ana)             |
| Frontend aktiviran                      | Dogodki: “Workshop 1”, “Workshop 2” |
| Uporabnik prijavljen na oba dogodka     |                                     |

---

### Scenariji testiranja

| Korak | Akcija                                  | Pričakovani rezultat                                 |
|-------|-----------------------------------------|------------------------------------------------------|
| 1     | Uporabnik odpre zavihek *Moji dogodki*. | API pošlje `GET /events/users/1/events`.             |
| 2     | Backend poišče vse dogodke.             | API vrne seznam 2 dogodkov.                          |
| 3     | UI prikaže rezultat.                    | Na strani se prikažeta “Workshop 1” in “Workshop 2”. |

---

### Pogoji uspešnega zaključka

| Korak | Pričakovani rezultat                |
|-------|-------------------------------------|
| 1     | API vrne status 200.                |
| 2     | Pravilni dogodki so prikazani v UI. |
| 3     | Ni napak v logih.                   |

---

### Opombe
Test uspešno opravljen – podatki iz baze `todo_db` pravilno prikazani v uporabniškem vmesniku.
