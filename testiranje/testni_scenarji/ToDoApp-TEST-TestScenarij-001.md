# ✅ ToDoApp-TEST-TestScenarij-001
### Funkcionalnost: Prijava uporabnika na dogodek

| Aplikacija         | To-Do Application   |
|--------------------|---------------------|
| Datum scenarija    | 26. 10. 2025        |
| Verzija aplikacije | 1.0                 |
| Avtor              | Anastasija Nechoska |

---

### Kratek opis
Preveri, ali lahko uporabnik uspešno odda prijavo na obstoječ dogodek in ali sistem pravilno zapiše podatke v bazo.

**Referenca:** TD-001  
**Zahteve v SZPO:** /  
**Namen:** Preveriti, da metoda `applyToEvent(eventId, userId)` pravilno poveže uporabnika z dogodkom in upošteva maksimalno število udeležencev.

---

### Predpogoji in vhodni podatki

| Predpogoji                                          | Vhodni podatki               |
|-----------------------------------------------------|------------------------------|
| Backend Spring Boot teče na `http://localhost:8080` | ID uporabnika = 1 (Ana)      |
| Frontend React teče na `http://localhost:5173`      | ID dogodka = 10 (Workshop 1) |
| Dogodek obstaja v bazi                              | Max udeležencev = 5          |
| Uporabnik še ni prijavljen na dogodek               |                              |

---

### Scenariji testiranja

| Korak | Akcija                                          | Pričakovani rezultat                                |
|-------|-------------------------------------------------|-----------------------------------------------------|
| 1     | Uporabnik Ana klikne *“Prijavi se na dogodek”*. | Pojavi se potrditveno okno.                         |
| 2     | Uporabnik potrdi prijavo.                       | Pošlje se zahteva `POST /events/10/apply?userId=1`. |
| 3     | Backend obdela prijavo.                         | API vrne status **200 OK** in posodobljen dogodek.  |
| 4     | Uporabnik osveži stran dogodka.                 | Dogodek kaže “Prijavljen/a”.                        |

---

### Pogoji uspešnega zaključka

| Korak | Pričakovani rezultat                            |
|-------|-------------------------------------------------|
| 1     | Uporabnik se doda v seznam udeležencev dogodka. |
| 2     | Dogodek se v bazi posodobi.                     |
| 3     | UI pravilno prikaže stanje “Prijavljen/a”.      |

---

### Opombe
Test izveden preko Postman in preverjen v bazi MySQL (tabela `event_participants`).
