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


## Porocilo o testiranju:
### Test 1: Edit_task
**Pozitiven scenarij:**  Preverjanje, ali je naloga uspešno urejena, ko sta podana veljavna ID naloge in opis.
**Specifični testi:**
Testiranje z veljavnim uporabniškim ID-jem (npr. uporabniški ID 101) in opisom naloge "Dopolni projekt".
Preverjanje, ali se naloga uspešno posodobi v bazi.
Testni scenarij: Kličemo metodo update_task(id, description) in preverimo, če se podatki posodobijo.

**Negativni scenariji:**
Test za neuspešno urejanje naloge zaradi neveljavnega ID-ja naloge (prazen ID).
Testni scenarij: Kličemo metodo update_task("", description), pričakujemo napako (npr. IllegalArgumentException).
Test za neuspešno urejanje naloge zaradi null opisa.
Testni scenarij: Kličemo metodo update_task(id, null), pričakujemo napako (npr. NullPointerException).
Testna orodja: JUnit 5 za preverjanje napak in verifikacijo rezultatov.
Testna tovarna: Uporabljamo JUnit parametrizirane teste za dinamično generiranje več primerov:
Testi z različnimi ID-ji uporabnikov (veljavni in neveljavni).
Testi z različnimi opisi nalog (veljavne in neveljavne vrednosti).
Orodje: JUnit 5 s pomočjo parametra @Timeout.
Preverjanje, da se metoda za posodabljanje nalog zaključi v manj kot 1 sekundi.

### Test 2: Login
**Test za uspešno prijavo uporabnika (testLoginUser_Success):**
Ta test preizkuša scenarij, kjer uporabnik z veljavnim e-poštnim naslovom in pravilnim geslom uspešno opravi prijavo. Preverja, ali metoda loginUser pravilno najde uporabnika in preveri ujemanje gesla.
**Pomen:** Ta test je ključnega pomena za zagotavljanje, da uporabniki lahko dostopajo do sistema, če vpišejo pravilne podatke.
**Test za neuspešno prijavo uporabnika (testLoginUser_Failure):**
Ta test preizkuša scenarij, kjer uporabnik z neveljavnim e-poštnim naslovom (ki ne obstaja v bazi podatkov) poskusi prijavo. Preverja, ali metoda loginUser pravilno vrne prazno vrednost (ne najde uporabnika).
**Pomen:** Ta test zagotavlja, da sistem zaščiti podatke in ne omogoča dostopa uporabnikom, ki niso registrirani v bazi.

***Kratka analiza uspešnosti testov:***
Vsi testi so bili uspešno opravljeni, kar pomeni, da so tako pozitivni kot negativni scenariji pravilno obdelani v okviru sistema za prijavo.
Test za uspešno prijavo je preveril, da je uporabnik pravilno prepoznan, ko so vneseni pravi podatki.
Test za neuspešno prijavo je preveril, da sistem zavrne neobstoječe uporabnike, kar pomeni, da ni mogoče vstopiti v sistem z napačnimi podatki.
Odpravljene napake: Med izvajanjem testov niso bile odkrite nobene napake, saj so bili vsi rezultati skladni z pričakovanji. Testi so jasno preverili različne pogoje (pravilne in napačne podatke), zato ni bilo potrebe po odpravi napak.

### Test 3: Registration
**Pozitiven scenarij:** Testiranje registracije z veljavnimi podatki.
**Specifični testi:**
Testiranje registracije z uporabniškim imenom "newuser", e-poštnim naslovom "newuser@primer.com" in geslom "SecurePassword123".
Preverjanje, da se geslo pravilno kodira z BCryptPasswordEncoder.
Preverjanje, da so vsi podatki pravilno shranjeni v bazi podatkov.
**Negativni scenarij:** Testiranje napačnih vhodnih podatkov, kot so prazno uporabniško ime ali napačen e-poštni naslov.
**Specifični testi:**
Preverjanje napak pri registraciji z neveljavnim uporabniškim imenom (prazno uporabniško ime).
Preverjanje napak pri napačnem e-poštnem naslovu (npr. "invalidemail.com" brez znaka "@").
Preverjanje, da geslo ni pravilno kodirano, če ni ustrezno zakodirano.
Orodja: JUnit 5, Mockito za preverjanje napak pri interakciji z bazo.
**Analiza uspešnosti:**
Testi so uspešno preverili vse validacije in zagotovili, da se registracija izvaja samo, če so podatki veljavni.


### Test 4: Update_task
**Pozitiven scenarij:** Preverjanje, ali je naloga uspešno posodobljena, ko je uporabnik najden in so podatki naloge ustrezni.
**Specifični testi:**
Posodobitev naloge uporabnika z ID-jem 101 in opisom "Dokončaj nalogo".
Preverjanje, da je naloga uspešno posodobljena v bazi.
Preverjanje, da se spremembe odražajo v uporabniški nalogi.
**Negativni scenariji:**
Posodobitev naloge, ki ne obstaja v bazi.
Specifični testi: Preverjanje, da posodabljanje naloge z ID-jem, ki ne obstaja, povzroči napako.
Posodobitev naloge z neveljavnim ID uporabnika.
Specifični testi: Preverjanje napak pri poskusu posodabljanja naloge z neveljavnim uporabniškim ID-jem (npr. prazen ID).
Uporabnik nima dovolj pravic za posodobitev naloge.
Specifični testi: Preverjanje, da uporabnik z ID-jem 101 ne more posodobiti naloge, ki ni njegova.
**Kratka analiza uspešnosti:**
Testi so pravilno prepoznali vse napake in zagotovili, da samo uporabnik, ki je lastnik naloge, lahko posodobi nalogo.
Vsi negativni scenariji so pravilno obravnavani s sprožitvijo napak (neobstoječa naloga, napačen ID uporabnika, pomanjkanje pravic).

------------------------
**Pomembnost testov:**
Testiranje posodabljanja nalog, prijave in registracije je ključnega pomena za zagotavljanje, da sistem omogoča samo veljavne operacije, kot so posodobitev nalog lastnikov, uspešna prijava in varna registracija uporabnikov. Testiranje varnosti pri posodobitvah nalog je še posebej pomembno za preprečevanje nepooblaščenih sprememb podatkov.


# **_Test 1: Apply to Event / Prijava na dogodek_**

  Pozitiven scenarij / Positive Scenario:
  Testiranje prijave uporabnika na dogodek z veljavnimi podatki.

  Specifični testi / Specific Tests:

    Preverjanje, da uporabnik uspešno doda dogodek v seznam udeležencev.
    Preverjanje, da se seznam udeležencev ustrezno posodobi v bazi podatkov.
    Preverjanje, da prijava ne preseže števila dovoljenih udeležencev.

  Negativen scenarij / Negative Scenario:
  Testiranje napačnih vhodnih podatkov ali pogojev za prijavo.
  Specifični testi / Specific Tests:

    Preverjanje napake, če je dogodek že popolnoma zaseden.
    Preverjanje, da uporabnik ni prijavljen na dogodek, če ta ne obstaja v bazi.

  Orodja / Tools: JUnit 5, Mockito za simulacijo obnašanja baze podatkov.

  Analiza uspešnosti / Performance Analysis:
  Testi so preverili uspešnost prijave in zagotovili, da sistem pravilno obravnava tako uspešne kot neuspešne primere prijave.

#   **Test 2: View Events (User) / Ogled dogodkov (uporabnik)**

  Pozitiven scenarij / Positive Scenario:

  Testiranje uspešnega pridobivanja seznama dogodkov.
  Specifični testi / Specific Tests:

    Preverjanje, da se seznam dogodkov pravilno vrne iz baze podatkov.
    Preverjanje, da je vsak dogodek prikazan z ustreznimi podrobnostmi (ime, lokacija, datum).

  Negativen scenarij / Negative Scenario:
  Testiranje, ko podatkov o dogodkih ni mogoče pridobiti.
  Specifični testi / Specific Tests:

    Simulacija napake baze podatkov pri pridobivanju dogodkov.
    Preverjanje, da se v primeru napake vrne prazen seznam ali ustrezno sporočilo o napaki.

  Orodja / Tools: JUnit 5, Mockito za simulacijo baze podatkov.

  Analiza uspešnosti / Performance Analysis:
  Testi so preverili tako uspešne kot neuspešne scenarije pridobivanja dogodkov in zagotovili ustrezno delovanje sistema.

#   **Test 3: View Events (Admin) / Ogled dogodkov (administrator)**

  Pozitiven scenarij / Positive Scenario:
  Testiranje uspešnega prikaza vseh dogodkov za administratorja.
  Specifični testi / Specific Tests:

    Preverjanje, da administrator vidi vse dogodke brez omejitev.
    Preverjanje, da so podrobnosti o dogodkih pravilno prikazane.

  Negativen scenarij / Negative Scenario:
  Testiranje, ko administrator poskuša pridobiti dogodke, a pride do napake.
  Specifični testi / Specific Tests:

    Simulacija napake baze podatkov pri pridobivanju dogodkov.
    Preverjanje, da se v primeru napake vrne ustrezno sporočilo o napaki.

  Orodja / Tools: JUnit 5, Mockito za simulacijo baze podatkov.

  Analiza uspešnosti / Performance Analysis:
  Testi so zagotovili, da administrator vidi vse dogodke in da sistem ustrezno obravnava napake.

#   **Test 4: Update Event / Posodobi dogodek**

  Pozitiven scenarij / Positive Scenario:
  Testiranje uspešne posodobitve dogodka z veljavnimi podatki.
  Specifični testi / Specific Tests:

    Preverjanje, da se podrobnosti dogodka pravilno posodobijo v bazi podatkov.
    Preverjanje, da posodobitev izvede samo administrator.

  Negativen scenarij / Negative Scenario:
  Testiranje napak pri posodobitvi dogodka.
  Specifični testi / Specific Tests:

    Preverjanje napake, ko uporabnik, ki ni administrator, poskuša posodobiti dogodek.
    Preverjanje napake, če dogodek za posodobitev ne obstaja.

  Orodja / Tools: JUnit 5, Mockito za simulacijo baze podatkov.

  Analiza uspešnosti / Performance Analysis:
  Testi so zagotovili, da se dogodki pravilno posodobijo in da sistem ustrezno obravnava nepooblaščene poskuse posodobitve.