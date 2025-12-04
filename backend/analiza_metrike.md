# Analiza metrik aplikacije

## 1. Struktura kode

Analiza aplikacije kaže, da je večina razredov majhna in preprosta, kar pomeni dobro kohezivnost in enostavno vzdrževanje. Nekateri model razredi, kot so `Event` in `Task`, imajo visoko kompleksnost (WMC) in nizko kohezivnost (LCOM), kar pomeni, da so kompleksni in bi jih bilo smiselno refaktorizirati. Repozitoriji in kontrolerji so lahki in dobro strukturirani.

---

## 2. Objektno usmerjene (OOP) metrike (CKJM)

| Kratica | Pomen |
|---------|-------|
| **WMC** | Weighted Methods per Class – vsota kompleksnosti vseh metod v razredu. Višji WMC pomeni kompleksnejši razred. |
| **DIT** | Depth of Inheritance Tree – globina dedovanja razreda. Višja vrednost pomeni globlje dedovanje. |
| **NOC** | Number of Children – število neposrednih podrazredov. |
| **CBO** | Coupling Between Object classes – število razredov, s katerimi je razred povezan. Višja vrednost pomeni večjo medsebojno odvisnost. |
| **RFC** | Response For a Class – število metod, ki jih razred lahko pokliče ali ki jih lahko pokličejo drugi razredi. |
| **LCOM** | Lack of Cohesion in Methods – merilo kohezivnosti razreda. Višje število pomeni, da metode delujejo bolj neodvisno. |
| **Ca** | Afferent Couplings – število razredov, ki se sklicujejo na ta razred. |
| **Ce** | Efferent Couplings – število razredov, na katere se ta razred sklicuje. |

### Rezultati OOP metrik

| Razred | WMC | DIT | NOC | CBO | RFC | LCOM | Ca | Ce |
|--------|-----|-----|-----|-----|-----|------|----|----|
| si.um.si.ToDoAppApplication | 2 | 1 | 0 | 2 | 4 | 1 | 0 | 2 |
| si.um.si.WebConfig | 3 | 1 | 0 | 5 | 10 | 3 | 0 | 3 |
| si.um.si.config.SecurityConfig | 2 | 1 | 0 | 2 | 4 | 1 | 0 | 2 |
| si.um.si.controller.ErrorResponse | 2 | 1 | 0 | 0 | 3 | 0 | 0 | 2 |
| si.um.si.controller.SuccessResponse | 3 | 1 | 0 | 0 | 4 | 0 | 0 | 3 |
| si.um.si.Login.LoginRequest | 5 | 1 | 0 | 0 | 6 | 6 | 0 | 5 |
| si.um.si.model.Attachment | 13 | 1 | 0 | 0 | 15 | 62 | 0 | 13 |
| si.um.si.model.Event | 23 | 1 | 0 | 1 | 27 | 173 | 0 | 23 |
| si.um.si.model.Task | 24 | 1 | 0 | 3 | 26 | 208 | 0 | 22 |
| si.um.si.model.Users | 15 | 1 | 0 | 1 | 17 | 63 | 0 | 15 |
| si.um.si.repository.AttachmentRepository | 1 | 1 | 0 | 1 | 1 | 0 | 0 | 1 |
| si.um.si.repository.EvenRepository | 6 | 1 | 0 | 2 | 6 | 15 | 0 | 6 |
| si.um.si.repository.TaskRepository | 3 | 1 | 0 | 2 | 3 | 3 | 0 | 3 |
| si.um.si.repository.UserRepository | 4 | 1 | 0 | 1 | 4 | 6 | 0 | 4 |
| si.um.si.rest.InfoController | 2 | 1 | 0 | 0 | 3 | 1 | 0 | 2 |

**Opazke:**
- Večina razredov ima DIT = 1, kar pomeni plitvo dedovanje.
- Razredi `Event` in `Task` imajo visoko WMC in LCOM, kar kaže na kompleksne in manj kohezivne razrede.
- Repozitoriji in kontrolerji so enostavni in dobro strukturirani.
- Skupna vezanost (CBO, Ce, Ca) je nizka, kar pomeni majhno medsebojno odvisnost razredov.

---

## 3. Preštevanje linij kode (cloc)

Ukaz:
```bash
npx cloc . --exclude-dir=node_modules,dist,build,.next,.git

```

## Rezultati

| Jeziki       | Datoteke | Prazne vrstice | Komentarji | Koda |
| ------------ | -------- | -------------- | ---------- | ---- |
| JSON         | 5        | 2              | 0          | 3298 |
| Java         | 39       | 678            | 193        | 2174 |
| TypeScript   | 23       | 161            | 22         | 1765 |
| Markdown     | 8        | 381            | 0          | 1032 |
| CSS          | 8        | 108            | 15         | 644  |
| XML          | 9        | 0              | 0          | 306  |
| Bourne Shell | 1        | 26             | 48         | 185  |
| DOS Batch    | 1        | 15             | 0          | 134  |
| Maven        | 1        | 12             | 2          | 118  |
| YAML         | 1        | 21             | 0          | 110  |
| JavaScript   | 1        | 1              | 0          | 27   |
| HTML         | 1        | 0              | 0          | 19   |
| Properties   | 2        | 9              | 17         | 16   |
| Text         | 1        | 2              | 0          | 7    |
| SVG          | 1        | 0              | 0          | 1    |
| **Skupaj**   | 102      | 1416           | 297        | 9836 |

Opazke:

- Projekt vsebuje 102 datoteke, s skupno 9836 vrsticami.

- Java je glavni programski jezik, s 2174 vrsticami kode.

- Projekt je dobro komentiran in strukturiran, kar pripomore k berljivosti.

## 4. Povzetek metrik

| Metrična skupina                           | Povprečje / vrednost           |
| ------------------------------------------ | ------------------------------ |
| Število Java datotek                       | 39                             |
| Skupno LOC (Java)                          | 2174                           |
| Komentar LOC (Java)                        | 193                            |
| Prazne LOC (Java)                          | 678                            |
| Povprečni WMC                              | ~8.3                           |
| Največji WMC                               | 24 (`Task`)                    |
| Povprečni LCOM                             | ~35                            |
| Največji LCOM                              | 208 (`Task`)                   |
| Povprečni DIT                              | 1                              |
| Povprečni CBO                              | ~1.5                           |
| Razredi z visoko kompleksnostjo (WMC > 15) | Event, Task, Users, Attachment |

## Sklep:

Večina razredov je majhna in kohezivna, kar je dobro za vzdrževanje.

- Nekateri model razredi so kompleksni in zahtevajo refaktorizacijo.

- Repozitoriji in kontrolerji so preprosti in dobro strukturirani.

- Projekt je srednje velik, z dobro dokumentirano kodo.
