# ✅ ToDoApp-TEST-TestScenarij-003
### Funkcionalnost: Ustvarjanje dogodka (Admin)

| Aplikacija         | To-Do Application   |
|--------------------|---------------------|
| Datum scenarija    | 26. 10. 2025        |
| Verzija aplikacije | 1.0                 |
| Avtor              | Anastasija Nechoska |

---

### Kratek opis
Preveri, ali lahko admin uporabnik uspešno ustvari nov dogodek.

**Referenca:** TD-003  
**Zahteve v SZPO:** /  
**Namen:** Preveriti metodo `createEvent(event, userId)` v `EventService`, da pravilno doda nov dogodek in preveri vlogo uporabnika.

---

### Predpogoji in vhodni podatki

| Predpogoji                       | Vhodni podatki           |
|----------------------------------|--------------------------|
| Backend teče na `localhost:8080` | Uporabnik (Admin) ID=5   |
| Frontend na `localhost:5173`     | Dogodek: “Team Workshop” |
| Vloga uporabnika = ADMIN         | Max udeležencev = 20     |

---

### Scenariji testiranja

| Korak | Akcija                                       | Pričakovani rezultat                      |
|-------|----------------------------------------------|-------------------------------------------|
| 1     | Admin se prijavi in odpre *Ustvari dogodek*. | Obrazec se prikaže.                       |
| 2     | Vnese ime, opis, datum in max udeležence.    | Podatki validni.                          |
| 3     | Pošlje obrazec.                              | API `POST /events?userId=5` – status 200. |
| 4     | Odpre seznam dogodkov.                       | Novi dogodek je viden.                    |

---

### Pogoji uspešnega zaključka

| Korak | Pričakovani rezultat                     |
|-------|------------------------------------------|
| 1     | Dogodek se shrani v bazo.                |
| 2     | Sistem preveri vlogo uporabnika (ADMIN). |
| 3     | Dogodek je prikazan v UI.                |

---

### Opombe
Preverjeno s Postmanom in MySQL Workbench.  
Uspešno ustvarjen dogodek: “Team Workshop”.
