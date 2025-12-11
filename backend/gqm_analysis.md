# CILJ 1: Zmanjšati kompleksnost glavnih razredov

## Q1.1: Kakšna je trenutna kompleksnost glavnih razredov (WMC)?

**Metrike:**
- **WMC (Weighted Methods per Class)** → število metod in njihova kompleksnost
- **Priporočena vrednost:** WMC < 20

**Izmerjene vrednosti:**

| Razred                  | WMC |
|-------------------------|-----|
| Task                    | 24  |
| Event                   | 23  |
| Users                   | 15  |
| Attachment              | 13  |
| Repozitoriji/Kontrolerji| 1–6 |

**Interpretacija:**
- Task in Event presegata priporočene meje → razreda sta prekompleksna
- Users je blizu meje, še sprejemljiv
- Repozitoriji in kontrolerji so enostavni in dobro strukturirani

**Ocena:** Kompleksnost glavnih modelnih razredov je previsoka → cilj Q1.1 NI dosežen.

---

## Q1.2: Ali imajo razredi visoko RFC (Response For Class)?

**Metrike:**
- **RFC** → število metod, ki se lahko sprožijo iz razreda
- **Priporočena vrednost:** RFC < 40

**Izmerjene vrednosti:**

| Razred             | RFC |
|-------------------|-----|
| Task              | 26  |
| Event             | 27  |
| Users             | 17  |
| Repo/Controller   | 1–6 |

**Interpretacija:**
- RFC je normalen (< 40)
- Kombinacija visok RFC + visok WMC pri Task in Event kaže, da razreda izvajata preveč logike in imata preveč odgovornosti

**Ocena:** RFC sam po sebi ni problem, vendar skupaj z WMC potrjuje previsoko kompleksnost → cilj Q1.2 NI dosežen.

---

## Q1.3: Kako kohezivni so razredi? (LCOM)

**Metrike:**
- **LCOM (Lack of Cohesion of Methods)** → visoka vrednost pomeni nizko kohezivnost

**Izmerjene vrednosti:**

| Razred     | LCOM |
|------------|------|
| Task       | 208  |
| Event      | 173  |
| Users      | 63   |
| Attachment | 62   |

**Interpretacija:**
- Task in Event imata zelo nizko kohezivnost → metode niso med seboj dovolj povezane
- Users in Attachment imata zmerno nizko kohezivnost → spremljati za morebitno refaktorizacijo

**Ocena:** Cilj Q1.3 NI dosežen → razredi potrebujejo refaktorizacijo za večjo kohezivnost.

---

## Q1.4: Predlogi za izboljšave

- Razdelitev razredov **Task** in **Event** na manjše, bolj kohezivne enote
- Premik del logike v pomožne razrede ali servise
- Zmanjšanje števila metod in odgovornosti posameznega razreda
- Redefinicija metod za boljšo povezavo znotraj razreda (izboljšava LCOM)

---

## Povzetek glavnih modelnih razredov

| Razred     | WMC | RFC | LCOM | Ocena kompleksnosti |
|------------|-----|-----|------|-------------------|
| Task       | 24  | 26  | 208  | Previsoka         |
| Event      | 23  | 27  | 173  | Previsoka         |
| Users      | 15  | 17  | 63   | Sprejemljivo      |
| Attachment | 13  | 15  | 62   | Sprejemljivo      |

# CILJ 2: Zmanjšati sklopljenost razredov

## Q2.1: Kateri razredi so najbolj odvisni od drugih?

**Metrike:**
- **CBO (Coupling Between Objects)** → število razredov, od katerih je razred odvisen
- **Priporočena vrednost:** CBO < 14

**Rezultati:**

| Razred                  | CBO |
|-------------------------|-----|
| Task                    | 3   |
| Event                   | 1   |
| Users                   | 1   |
| Attachment              | 0   |


**Interpretacija:**
- Task ima največjo odvisnost
- Event, Users, Attachment so dobro modularni

**Ocena:** Cilj Q2.1 delno NI dosežen.

---

## Q2.2: Kateri razredi najbolj prispevajo k tehničnemu dolgu?

**Metrike:**
- **Ca (Afferent Couplings)** → število razredov, ki uporabljajo ta razred
- **Ce (Efferent Couplings)** → število razredov, ki jih razred uporablja

**Rezultati glavnih modelnih razredov:**

| Razred  | Ca | Ce |
|---------|----|----|
| Task    | 0  | 22 |
| Event   | 0  | 23 |
| Users   | 0  | 15 |
| Attachment | 0 | 13 |

**Interpretacija:**
- Modelni razredi imajo veliko **Ce** → odvisni od mnogih drugih razredov → večja možnost verižnih napak
- Ca = 0 → razredi niso zelo uporabljeni drugje

**Ocena:** Cilj Q2.2 NI dosežen za Task in Event.

---

## Q2.3: Kako modularen je projekt na podlagi CBO in Ce?

- **CBO > 2** ali **Ce visoko** → nizka modularnost

**Rezultati:**

| Razred      | CBO | Ce |
|------------|-----|----|
| Task       | 3   | 22 |
| Event      | 1   | 23 |
| Users      | 1   | 15 |


**Interpretacija:**
- Task in Event zmanjšujeta modularnost zaradi visoke Ce
- Večina drugih razredov ima nizko CBO in Ce → dobro modularni

**Ocena:** Cilj Q2.3 delno NI dosežen.

## CILJ 3: Izboljšati uporabniško izkušnjo in preglednost UI

### Q3.1: Enostavnost glavnih funkcij

**Metrike:**
- Število korakov za dodajanje nove naloge.
- Število korakov za označitev naloge kot dokončane.

**Analiza:**
- Dodajanje in dokončanje nalog je jasno in enostavno vodeno.
- UI omogoča hitro interakcijo brez nepotrebnih korakov.

**Zaključek:** Cilj Q3.1 je **dosežen**.

---

### Q3.2: Vidnost in dostopnost ključnih elementov

**Metrike:**
- Število glavnih kontrolnikov na zaslonu (All, Completed, Pending, In progress, Favourites).
- Število klikov do želene funkcije (dodajanje naloge = 1 klik).
- Jasnost vizualne hierarhije.

**Analiza:**
- UI je pregledna, intuitivna in dobro organizirana.
- Ključne funkcije so takoj dostopne in jasno označene.

**Zaključek:** Cilj Q3.2 je **dosežen**.

---

### Q3.3: Odzivnost UI

**Metrike (iz Developer Tools):**
- Čas dodajanja naloge ≈ 40–70 ms (cilj < 500 ms).
- Čas osvežitve UI < 90 ms (cilj < 200 ms).

**Analiza:**
- UI se posodobi takoj po odgovoru API-ja.
- Vsi klici so uspešni, brez zakasnitev.

**Zaključek:** Cilj Q3.3 je **dosežen**.

---

### Q3.4: Hitrost odziva po uporabnikovih dejanjih

**Metrike:**
- Povprečen odziv API: ~80–120 ms
- Najdaljši odziv: 215 ms
- Delež uspešnih zahtevkov: 100 %
- Vidna posodobitev UI po odgovoru

**Analiza:**
- UI se odziva zelo hitro, brez napak.
- Spremembe (dodajanje nalog, prikaz kartice) so takoj vidne.

**Zaključek:** Cilj Q3.4 je **dosežen**.

---

### Sklep

- **CILJ 1**: Kompleksnost glavnih razredov je prevelika (Task in Event).
- **CILJ 2**: Sklopljenost je zmerna, a Task zmanjšuje modularnost.
- **CILJ 3**: Uporabniška izkušnja je dobra, UI je pregledna in odzivna.  