# Poročilo PMD Analize - God Class in Načrt Refaktoriranja

## Pregled
Analiza backend aplikacije To-Do razkriva več težav v zasnovi, pri čemer več razredov kaže značilnosti "God Class" (vsemogočnega razreda).

---

## 1. Kandidati za God Class

### Primarni God Class: AttachmentService.java

**Odkrite težave:**
- Več kršitev (vrstice 71, 76, 92, 95, 95)
- Lovi generične izjeme v try-catch blokih
- Težave z obravnavo izjem

**Kazalniki God Class:**
- Upravlja preveč logike (obdelava datotek, HTTP status, upravljanje izjem)
- Slaba kohezija - meša različne odgovornosti
- Visoka sklopitev - več odvisnosti

**Karakteristike:**
- Nosi večino logike sistema za delo z datotekami
- Kompleksen (več odgovornosti v enem razredu)
- Zelo sklopljen (odvisnosti od več komponent)
- Slabo vezljiv (težko ponovno uporabiti brez odvisnosti)

---

### Sekundarni God Class: EventController.java

**Odkrite težave:**
- Vrstica 57: Izogibanje lovljenju Exception v try-catch bloku
- Vrstica 102: Izogibanje lovljenju Exception v try-catch bloku

**Kazalniki God Class:**
- Krmilnik izvaja preveč poslovne logike
- Naj bi delegiral na sloj storitev

---

## 2. Težave Data Class

Več razredov modela je identificiranih kot "Data Class":

| Razred | Težave | WMC Ocena |
|--------|--------|-----------|
| LoginRequest.java | WOC=0.000%, NOAM=4, WMC=4 | Nizka kompleksnost |
| AttachmentRequest.java | WOC=0.000%, NOAM=4, WMC=4 | Nizka kompleksnost |
| Attachment.java | WOC=0.286%, NOAM=18, WMC=23 | Srednja kompleksnost |
| Event.java | WOC=1.286%, NOAM=18, WMC=23 | Srednja kompleksnost |
| Task.java | WOC=9.091%, NOAM=20, WMC=24 | Srednje-visoka kompleksnost |
| Users.java | WOC=0.000%, NOAM=13, WMC=15 | Nizka kompleksnost |

**Problem:** Ti razredi vsebujejo večinoma getterje in setterje z minimalno poslovno logiko, kar kaže na anemičen domenski model.

---

## 3. Kršitve Načrtovalskih Pravil

### Kršitve LawOfDemeter (7 primerov)
Kaže na tesno sklopitev in kršitev principa "Povej, ne vprašaj":
- Attachment.java (vrstica 11): Dostop do polja 'IDENTITY' na tuji vrednosti
- Event.java (vrstice 14, 43): Več dostopov do tujih vrednosti
- Task.java (vrstice 11, 22, 29): Neposreden dostop do tujih polj
- Users.java (vrstice 12, 14, 26, 31, 31): Več kršitev

### Kršitve ImmutableField (3 primeri)
Polja, ki bi lahko bila deklarirana kot final:
- AttachmentController.java (vrstice 98, 99, 112): message, fileName, field

---

## 4. Anti-vzorci Obravnave Izjem (5 primerov)

**AvoidCatchingGenericException:**
- EventController.java (vrstice 57, 102)
- AttachmentService.java (vrstice 95, 95)

**ExceptionAsFlowControl:**
- AttachmentService.java (vrstice 76, 92)

---

## 5. Težave Kakovosti Kode

### TooManyMethods (2 primera)
- TaskService.java (vrstica 20): "Ta razred ima preveč metod, razmislite o refaktoriranju."
- Update_task.java (vrstica 23): Podobna težava

### SimplifyBooleanReturns (1 primer)
- Razred unit test: Manjša možnost optimizacije

---

## Načrt Refaktoriranja

### Refaktoriranje 1: Izvleči Razred iz AttachmentService

**Trenutni Problem:** AttachmentService je God Class, ki upravlja več odgovornosti.

**Tip Refaktoriranja:** Extract Class (Izvleci razred)

**Katalog Preoblikovanja:** Martin Fowler - "Refactoring: Improving the Design of Existing Code"

**Rešitev:**

Pred refaktoriranjem:
```
AttachmentService
├── Logika nalaganja datotek
├── Validacija datotek
├── Operacije na podatkovni bazi
├── Obravnava izjem
└── Gradnja HTTP odgovorov
```

Po refaktoriranju:
```
AttachmentService (Orkestrator)
├── FileStorageService (Operacije z datotekami)
├── AttachmentValidator (Validacijska logika)
├── AttachmentRepository (Operacije na podatkovni bazi)
└── ExceptionMapper (Obravnava izjem)
```

**Koraki Implementacije:**
1. Ustvari nov razred FileStorageService
2. Premakni vse metode za delo z datotekami v FileStorageService
3. Ustvari AttachmentValidator za validacijsko logiko
4. Ustvari ExceptionMapper za obravnavo izjem
5. AttachmentService postane orkestrator, ki koordinira med temi razredi

**Preverjanje uspešnosti:**
- PMD WMC metrika za AttachmentService naj se zmanjša z >30 na <15
- Število odgovornosti na razred: iz 5 na 1
- Število vrstic kode na razred: iz >200 na <100

**Prednosti:**
- Princip ene odgovornosti
- Zmanjšana sklopitev
- Lažje testiranje
- Boljša vzdrževalnost

---

### Refaktoriranje 2: Premakni Poslovno Logiko iz Krmilnikov v Storitve

**Trenutni Problem:** Krmilniki (EventController, AttachmentController) vsebujejo poslovno logiko.

**Tip Refaktoriranja:** Move Method (Premakni metodo) + Extract Method (Izvleci metodo)

**Katalog Preoblikovanja:** Martin Fowler - "Move Method" in "Extract Method"

**Koraki Implementacije:**
1. Identificiraj vso poslovno logiko v krmilnikih
2. Ustvari ustrezne metode v servisni plasti
3. Premakni validacijsko logiko v storitve
4. Premakni procesiranje podatkov v storitve
5. Krmilniki naj vsebujejo samo HTTP specifično logiko

**Preverjanje uspešnosti:**
- Krmilniki naj imajo samo HTTP odgovornosti
- Število vrstic v metodah krmilnika: iz >50 na <10
- PMD kršitve "AvoidCatchingGenericException" naj se zmanjšajo

**Prednosti:**
- Krmilniki se osredotočijo le na HTTP zahteve
- Poslovna logika je testabilna brez HTTP plasti
- Pravilna hierarhija obravnave izjem

---

### Refaktoriranje 3: Vnesi Domensko Logiko v Razrede Modela

**Trenutni Problem:** Anemičen domenski model - razredi so le nosilci podatkov.

**Tip Refaktoriranja:** Move Method (Premakni metodo) iz storitev v domenske objekte

**Katalog Preoblikovanja:** Martin Fowler - "Move Method" in fowler.com/bliki/AnemicDomainModel.html

**Koraki Implementacije:**
1. Identificiraj metode v servisih, ki delujejo na enem objektu
2. Premakni te metode v ustrezne razrede modela
3. Poenoti dostop do notranjih stanj preko metod
4. Dodaj domensko pomembne metode v razrede modela

**Preverjanje uspešnosti:**
- WOC (Weight of Class) metrika naj se poveča iz <1% na >10%
- Število metod v servisnih razredih naj se zmanjša
- Inkapsulirano vedenje v domenskih razredih

**Prednosti:**
- Bogatejši domenski model
- Inkapsuliacija poslovnih pravil
- Zmanjšana kompleksnost servisne plasti

---

### Refaktoriranje 4: Odpravi Kršitve Demeterjevega Zakona

**Trenutni Problem:** Prekomerno veriženje metod in dostop do notranjih delov tujih objektov.

**Tip Refaktoriranja:** Hide Delegate (Skrij delegata) + Introduce Explaining Variable (Vnesi pojasnjevalno spremenljivko)

**Katalog Preoblikovanja:** Martin Fowler - "Hide Delegate"

**Koraki Implementacije:**
1. Poišči vse verige klicev metod (a.getB().getC().getD())
2. Ustvari delegacijske metode v začetnem razredu
3. Nadomesti vse klice verig z delegacijskimi metodami
4. PMD ponovno preveri kršitve Law of Demeter

**Preverjanje uspešnosti:**
- PMD kršitve "LawOfDemeter" naj se zmanjšajo iz 7 na 0-2
- Zmanjšana sklopitev med razredi
- Lažje spreminjanje notranje strukture

**Prednosti:**
- Zmanjšana sklopitev
- Lažje spreminjanje notranje strukture
- Bolj vzdržljiva koda

---

### Refaktoriranje 5: Implementiraj Pravilno Strategijo Obravnave Izjem

**Trenutni Problem:** Lovljenje generične Exception, uporaba izjem za nadzor poteka programa.

**Tip Refaktoriranja:** Replace Exception with Test (Nadomesti izjemo s testom) + Extract Hierarchy (Izvleci hierarhijo)

**Katalog Preoblikovanja:** Martin Fowler - "Replace Exception with Test" in "Extract Hierarchy"


**Koraki Implementacije:**
1. Ustvari hierarhijo domensko specifičnih izjem
2. Nadomesti catch(Exception e) s specifičnimi izjemami
3. Odstrani uporabo izjem za kontrolo toka (uporabi if-else)
4. Ustvari GlobalExceptionHandler za centralizirano obravnavo
5. Dodaj logging in error tracking

**Preverjanje uspešnosti:**
- PMD kršitve "AvoidCatchingGenericException" naj se zmanjšajo iz 5 na 0
- PMD kršitve "ExceptionAsFlowControl" naj se zmanjšajo iz 2 na 0
- Jasnejši stack traces za debugging

**Prednosti:**
- Jasna obravnava napak
- Ni dodatne obremenitve s performanso zaradi izjem za kontrolo toka
- Boljše razhroščevanje in beleženje

---

## Prioriteta Implementacije

1. **Visoka Prioriteta:** Refaktoriranje 1 in 2 (Ločitev sloja storitev)
2. **Srednja Prioriteta:** Refaktoriranje 5 (Obravnava izjem)
3. **Srednja Prioriteta:** Refaktoriranje 3 (Obogatitev domene)
4. **Nizka Prioriteta:** Refaktoriranje 4 (Demeterjev zakon)

---

## Strategija Preverjanja

Po vsakem refaktoriranju:

1. **Ponovno zaženi PMD analizo** za preverjanje zmanjšanja kršitev
2. **Izmeri metrike:**
    - WMC (Weighted Methods per Class) naj se zmanjša
    - Metrike sklopitve naj se izboljšajo
    - Število kršitev naj se zmanjša
3. **Zaženi unit teste** za zagotavljanje ohranjene funkcionalnosti
4. **Code review** za preverjanje izboljšane berljivosti

---

## Pričakovani Rezultati

| Metrika | Pred | Po (Pričakovano) |
|---------|------|------------------|
| God Classes | 2 | 0 |
| Kršitve DataClass | 7 | 3-4 |
| Težave z izjemami | 5 | 0 |
| Kršitve Law of Demeter | 7 | 2-3 |
| TooManyMethods | 2 | 0 |
| Povprečno WMC na razred | 18 | 10-12 |
| Skupno število razredov | 15 | 20-22 |

---

## Povzetek Tipov Refaktoriranja

| Refaktoriranje | Tip | Katalog |
|----------------|-----|---------|
| 1. AttachmentService | Extract Class | Martin Fowler |
| 2. Controller logika | Move Method, Extract Method | Martin Fowler |
| 3. Domenski model | Move Method | Martin Fowler |
| 4. Law of Demeter | Hide Delegate | Martin Fowler |
| 5. Obravnava izjem | Replace Exception with Test, Extract Hierarchy | Martin Fowler |
