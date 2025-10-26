# 🧪 Testni scenariji – To-Do Application

Vsi testni scenariji za aplikacijo **To-Do Application** so dostopni v tem repozitoriju.  
Vsak scenarij opisuje korake ročnega testiranja za preverjanje pravilnega delovanja posameznih funkcionalnosti aplikacije.

---

## 📦 Osnovne informacije

**Projekt:** To-Do Application  
**Avtor:** Anastasija Nechoska  
**Datum izdaje:** 26. 10. 2025  
**Verzija:** 1.0  
**Testirano okolje:**
- Backend: Spring Boot (port 8080)
- Frontend: React (port 5173)
- Baza: MySQL (`todo_db`)

---

## 🔍 Pregled scenarijev

| Št. | Naziv scenarija                  | Povezava                                                          |
|-----|----------------------------------|-------------------------------------------------------------------|
| 001 | Prijava uporabnika na dogodek    | [ToDoApp-TEST-TestScenarij-001](ToDoApp-TEST-TestScenarij-001.md) |
| 002 | Pridobivanje dogodkov uporabnika | [ToDoApp-TEST-TestScenarij-002](ToDoApp-TEST-TestScenarij-002.md) |
| 003 | Ustvarjanje dogodka (Admin)      | [ToDoApp-TEST-TestScenarij-003](ToDoApp-TEST-TestScenarij-003.md) |

---

## 🧭 Opis funkcionalnosti

Aplikacija **To-Do Application** omogoča upravljanje dogodkov in nalog, s poudarkom na vlogah uporabnikov (USER in ADMIN).  
Testni scenariji pokrivajo naslednje glavne funkcionalnosti:

1. ✅ Prijava uporabnika na dogodek (`applyToEvent`)
2. ✅ Prikaz dogodkov, na katere je uporabnik prijavljen (`getUserAppliedEvents`)
3. ✅ Ustvarjanje dogodka s strani administratorja (`createEvent`)

Vsak scenarij vključuje:
- **Predpogoje in vhodne podatke**,
- **Korake testiranja**,
- **Pričakovane rezultate**,
- in **pogoje uspešnega zaključka**.

---

## 🧩 Povzetek testiranja

| Scenarij | Status testa | Opis rezultata                           |
|----------|--------------|------------------------------------------|
| TD-001   | ✅ Uspešno    | Uporabnik se lahko prijavi na dogodek.   |
| TD-002   | ✅ Uspešno    | Sistem pravilno vrne dogodke uporabnika. |
| TD-003   | ✅ Uspešno    | Administrator lahko ustvari nov dogodek. |

---

📅 **Datum testiranja:** 26. 10. 2025  
👤 **Tester:** Anastasija Nechoska  
🧠 **Rezultat:** Vsi funkcionalni testi so bili uspešno izvedeni.  
Sistem deluje stabilno, brez napak ali izjem.

---

> 💡 *Opomba:* Testni scenariji so pripravljeni za ročno testiranje in so skladni z implementacijo razredov  
> `EventController`, `EventService`, `UserRepository` in `EvenRepository` v aplikaciji **To-Do Application**.
