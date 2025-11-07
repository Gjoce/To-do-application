# Poročilo o obremenitvenem testiranju (Load Testing)

## 1. Namen testa

Cilj obremenitvenega testiranja je bil preveriti, kako se sistem **To-do Application (Events API)** obnaša pri **normalni do zmerni obremenitvi**, ko več uporabnikov hkrati pošilja zahteve za pridobivanje dogodkov preko REST API-ja.

Test je bil izveden s pomočjo orodja **Apache JMeter**, ki je simuliralo več hkratnih uporabnikov, ki izvajajo zaporedne **GET** zahteve na endpoint `/events`.

---

## 2. Testni scenarij

| Parameter                             | Vrednost                           |
|---------------------------------------|------------------------------------|
| Število niti (virtualnih uporabnikov) | 20                                 |
| Ramp-Up čas                           | 10 sekund                          |
| Število ponovitev (Loop Count)        | 5                                  |
| Skupno število zahtev                 | 400                                |
| Endpoint                              | `GET http://localhost:8080/events` |
| Orodje                                | Apache JMeter (v5.6.3)             |
| Vrsta testa                           | Load test (normalna obremenitev)   |

Vsaka nit predstavlja enega uporabnika, ki večkrat zaporedoma pošlje zahtevo **GET /events**, s čimer se preverja stabilnost, hitrost odziva in zanesljivost API-ja ob več hkratnih zahtevkih.

---

## 3. Cilj testa

Preveriti, ali API:

- pravilno vrača odgovore tudi ob več hkratnih zahtevkih,
- ohranja stabilnost in nizko zakasnitev,
- ne vrača napak tipa **4xx** ali **5xx**,
- ostaja odziven v času povečane obremenitve.

**Pričakovani rezultati:**
- Strežnik naj ne vrača napak (idealno **0 % error rate**)
- Povprečni odzivni čas naj ostane pod **2 sekundama**
- Strežnik naj ostane stabilen in odziven brez timeoutov ali padcev

---

## 4. Rezultati testiranja

| Kazalnik                                 | Rezultat         |
|------------------------------------------|------------------|
| Skupno število zahtev                    | 400              |
| Povprečni odzivni čas (Average)          | **9 ms**         |
| Najdaljši odzivni čas (Max)              | **333 ms**       |
| Najkrajši odzivni čas (Min)              | **4 ms**         |
| Standardni odklon (Std. Dev.)            | **16.35 ms**     |
| Stopnja napak (Error %)                  | **0.00 % **      |
| Prepustnost (Throughput)                 | **1.4 zahtev/s** |
| Prejeto (Received KB/sec)                | **1.43 KB/s**    |
| Poslano (Sent KB/sec)                    | **0.17 KB/s**    |
| Povprečna velikost odgovora (Avg. Bytes) | **1,051 B**      |

> *Podatki izhajajo iz Apache JMeter “Summary Report” in “View Results Tree”.*

---

## 5. Analiza rezultatov

- **Vse zahteve (400)** so bile uspešno izvedene brez napak ali izjem.
- Povprečni odzivni čas **9 ms** je izjemno nizek in potrjuje visoko učinkovitost API-ja.
- Najdaljši odzivni čas **333 ms** kaže, da so občasne zakasnitve minimalne in sprejemljive.
- Stopnja napak **0 %** potrjuje stabilnost sistema tudi pri 20 hkratnih uporabnikih.
- Prepustnost **1.4 zahtev/s** je skladna z nastavitvami ramp-up časa in lokalnim testnim okoljem.
- Odgovori API-ja so vsebovali pravilno JSON strukturo z dogodki (potrjeno v **View Results Tree**).
- Strežnik ni kazal znakov preobremenitve, baza podatkov in odzivi so ostali stabilni.

---

## 6. Zaključek

Rezultati obremenitvenega testiranja kažejo, da:

- Sistem stabilno deluje pri **20 hkratnih uporabnikih**.
- Povprečni odzivni čas **9 ms** potrjuje visoko učinkovitost in hitro odzivnost API-ja.
- Ni bilo zaznanih napak (**0 % error rate**) — sistem je popolnoma stabilen.
- Strežnik se hitro odziva in ne kaže znakov preobremenitve.

Aplikacija je **primerna za produkcijsko uporabo** pod normalno obremenitvijo.  
Za nadaljnje testiranje se priporoča preverjanje delovanja pod **večjo obremenitvijo (50–100 uporabnikov)** za oceno skalabilnosti sistema.

---

## 7. Povzetek ključnih kazalnikov

| Kazalnik       | Rezultat                  |
|----------------|---------------------------|
| Funkcionalnost | Deluje pravilno           |
| Stabilnost     | Zelo stabilna (0 % napak) |
| Odzivnost      | Povprečno 9 ms            |
| Prepustnost    | 1.4 zahtev/s              |
| Napake         | 0 %                       |

---

📁 **Datoteka testa:** `testEventSummaryReport.jmx`  
🖥️ **Ciljni strežnik:** `http://localhost:8080/events`  
⚙️ **Orodje:** Apache JMeter v5.6.3  
📅 **Datum izvedbe testa:** 2025-11-08

