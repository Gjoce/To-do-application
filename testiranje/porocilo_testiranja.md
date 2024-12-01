# Poročilo o Testiranju

## 1. Opis Testov

### Test 1: Create Task - User Exists (Positive Scenario)

**Opis:**  
Preizkuša uspešno ustvarjanje naloge, ko uporabnik obstaja v bazi podatkov.

**Pomen:**  
Zagotavlja, da lahko sistem pravilno doda nalogo ob veljavnih podatkih in poveže nalogo z obstoječim uporabnikom.

---

### Test 2: Create Task - User Not Found (Negative Scenario)

**Opis:**  
Preverja obnašanje sistema, ko se poskuša ustvariti naloga za neobstoječega uporabnika.

**Pomen:**  
Zagotavlja, da sistem ustrezno obravnava napake in ne omogoča ustvarjanja nalog za neveljavne uporabnike.

---

### Test 3: Create Task - Task is Null (Negative Scenario)

**Opis:**  
Test preverja, ali metoda pravilno zavrne `null` vrednost za nalogo.

**Pomen:**  
Pomembno za zagotavljanje robustnosti sistema in preprečevanje nenapovedanih napak.

---

### Test 4: Create Task - User Data Invalid (Negative Scenario)

**Opis:**  
Test preverja obravnavo nepopolnih ali neveljavnih podatkov uporabnika.

**Pomen:**  
Zagotavlja, da sistem preverja in zavrne napačne podatke, s čimer se prepreči shranjevanje neveljavnih podatkov v bazo.

---

# Markdown Report za Testing Delete_Task

## 1. Opis Testov

### Test 1: Delete Task - Task Exists (Positive Scenario)

**Opis:**  
Preizkuša uspešno brisanje naloge, ko naloga obstaja v bazi podatkov.

**Pomen:**  
Zagotavlja, da sistem pravilno izbriše naloge, ki obstajajo, brez kakršnih koli napak.

---

### Test 2: Delete Task - Task Does Not Exist (Negative Scenario)

**Opis:**  
Preverja obnašanje sistema, ko poskuša izbrisati nalogo, ki ne obstaja.

**Pomen:**  
Zagotavlja, da sistem pravilno upravlja situacije, kjer je ID naloge neveljaven, in da ne povzroči nepričakovanih napak.

# Markdown Report za Testing Filter_Task

## 1. Opis Testov

### Test 1: Get Tasks By Status - Positive Scenario

**Opis:**  
Testira funkcionalnost pridobivanja nalog na podlagi njihovega statusa.

### Test 2: Get Tasks By Status - No Tasks Found Scenario

**Opis:**  
Preverja obnašanje sistema, ko poskuša najti nalogo, ki ne obstaja.

### Test 3: Get Tasks By Status - Negative Scenario (Exception Handling)

**Opis:**  
Preverja obnašanje sistema, ko pride do Exception.

**Pomen:**  
Filtriranje nalog omogoča uporabnikom boljši pregled nad svojimi obveznostmi. Preverjanje različnih scenarijev zagotavlja, da funkcionalnost deluje pravilno v vseh možnih pogojih.

## 1. Opis Testov

### Test za ustvarjanje dogodka (Add Event)

#### Kaj preizkuša:

Testi preizkušajo funkcionalnost ustvarjanja dogodkov v sistemu. Vključujejo različne scenarije, da se zagotovi pravilno delovanje:

- **Pozitiven scenarij (Admin uporabnik):**  
  Ustvarjanje dogodka s strani admin uporabnika.

- **Negativen scenarij (Ne-admin uporabnik):**  
  Poskus ustvarjanja dogodka s strani običajnega uporabnika, kar sproži izjemo.

- **Scenarij napake (Uporabnik ne obstaja):**  
  Poskus ustvarjanja dogodka s strani neobstoječega uporabnika, kar sproži izjemo.

#### Zakaj je pomemben:

Ustvarjanje dogodkov je ključen del sistema za upravljanje dogodkov. Zagotavljanje, da lahko dogodke ustvarjajo samo ustrezno pooblaščeni uporabniki, izboljša varnost in integriteto podatkov.

### Test za brisanje dogodka (Delete Event)

#### Cilj testa:

Testi za brisanje dogodkov preverjajo pravilno delovanje metode `deleteEvent` v storitvi `EventService`. Cilj je zagotoviti, da:

- Dogodke lahko izbrišejo samo admin uporabniki.
- Se ob nepooblaščenih ali neveljavnih operacijah sprožijo ustrezne izjeme.

## 2. Testni primeri

### a. Brisanje dogodka s strani admin uporabnika

**Opis:**  
Preverja, da lahko admin uporabnik uspešno izbriše dogodek.

**Pričakovani izid:**

- Dogodek je izbrisan iz repozitorija.
- Ni sproženih nobenih izjem.

**Dejanski izid:**  
Test je bil uspešen.

**Preverjeni pogoji:**

- Preverjena je vloga uporabnika.
- Dogodek obstaja v repozitoriju.
- Metoda za brisanje je bila klicana enkrat na repozitoriju dogodkov.

---

### b. Poskus brisanja dogodka s strani ne-admin uporabnika

**Opis:**  
Preverja, da običajen uporabnik ne more izbrisati dogodkov.

**Pričakovani izid:**  
Sprožena je `SecurityException` z sporočilom "Samo admini lahko brišejo dogodke."

**Dejanski izid:**  
Test je bil uspešen.

**Preverjeni pogoji:**

- Klicana je metoda `findById` na `UserRepository`.
- Ni interakcije z `EventRepository` za pridobivanje ali brisanje dogodka.

---

### c. Poskus brisanja neobstoječega dogodka

**Opis:**  
Preverja, da se ob poskusu brisanja neobstoječega dogodka sproži izjema.

**Pričakovani izid:**  
Sprožena je `IllegalArgumentException` z sporočilom "Dogodek ni bil najden."

**Dejanski izid:**  
Test je bil uspešen.

**Preverjeni pogoji:**

- Klicana je metoda `findById` na `UserRepository` in `EventRepository`.
- Metoda za brisanje ni bila klicana na repozitoriju dogodkov.
