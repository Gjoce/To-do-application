# Testno poročilo – Sistem za upravljanje nalog (TaskController)

## Izvleček

Ta dokument predstavlja rezultate ročnega funkcionalnega testiranja, izvedenega na osnovnih operacijah sistema za upravljanje nalog. Vse testirane funkcionalnosti so izpolnile pričakovane zahteve in delovale brez napak.

**Datum testiranja:** 6. november 2025  
**Metoda testiranja:** Ročno testiranje prek spletnega uporabniškega vmesnika  
**Tester:** Georgi Dimov

---

## Testno okolje

- **Metoda testiranja:** Ročno testiranje preko UI
- **Brskalnik:** Google Chrome;v=141
- **Uporabniška vloga:** Registriran uporabnik s standardnimi pravicami
- **Obseg testiranja:** CRUD operacije TaskController-ja

---

## Testni primeri

### Testni primer 1: Ustvarjanje naloge

**Cilj:** Preveriti, ali lahko uporabnik uspešno ustvari novo nalogo, povezano z njegovim računom.

**Koraki izvedbe:**

1. Prijavil sem se kot registriran uporabnik
3. Kliknil sem gumb "Add task"
4. Odprl se je "modal"
4. Vnesel sem naslednje podatke naloge:
    - **Task title:** Priprava dokumentacije
    - **Description:** Napiši poročilo za testiranje sistema
    - **Status:** PENDING
    - **Priority:** LOW
    - **Due date:** 28. november 2025
5. Kliknil sem gumb "Add task"

**Pričakovani rezultat:** Nova naloga se prikaže na seznamu nalog z vsemi pravilno prikazanimi vnesenimi podatki.

**Dejanski rezultat:** Naloga je bila uspešno ustvarjena in se je pojavila na seznamu nalog z vsemi pravilno izpolnjenimi podatkovnimi polji.

**Status:** **USPEŠEN**

---

### Testni primer 2: Posodabljanje naloge

**Cilj:** Preveriti, ali lahko uporabnik uspešno spremeni podatke obstoječe naloge.

**Koraki izvedbe:**

1. Na seznamu nalog sem poiskal prej ustvarjeno nalogo "Priprava dokumentacije"
2. Kliknil sem na nalogo
3. Spremenil sem naslednje polje:
    - **Status:** Spremenil iz PENDING v FINISHED
4. Kliknil sem "Update task"

**Pričakovani rezultat:** Posodobljeni podatki se takoj prikažejo v tabeli/seznamu nalog brez napak ali zamikov.

**Dejanski rezultat:** Status in datum zaključka sta bila uspešno posodobljena in pravilno prikazana brez kakršnihkoli napak ali zakasnitev.

**Status:** **USPEŠEN**

---

### Testni primer 3: Brisanje naloge

**Cilj:** Preveriti, ali lahko uporabnik uspešno izbriše obstoječo nalogo iz sistema.

**Koraki izvedbe:**

1. Na seznamu nalog sem poiskal nalogo "Priprava dokumentacije"
2. Kliknil sem gumb "Delete" ob nalogi

**Pričakovani rezultat:** Naloga je odstranjena s seznama nalog brez napak.

**Dejanski rezultat:** Naloga je bila uspešno izbrisana in se več ne pojavlja na seznamu nalog. Med postopkom brisanja ni prišlo do nobenih napak.

**Status:** **USPEŠEN**

---

## Povzetek testiranja

| Testni primer | Funkcionalnost | Status | Opombe |
|---------------|----------------|--------|--------|
| TP-01 | Ustvarjanje naloge | USPEŠEN | Vsa polja pravilno shranjena |
| TP-02 | Posodabljanje naloge | USPEŠEN | Spremembe takoj vidne |
| TP-03 | Brisanje naloge | USPEŠEN | Naloga uspešno odstranjena |

---

## Zaključki

Vse tri osnovne funkcionalnosti upravljanja nalog (ustvarjanje, posodabljanje, brisanje) so bile ročno testirane prek uporabniškega vmesnika in delujejo v skladu s specifikacijami. Sistem pravilno obdeluje uporabniške vnose in spremembe brez napak ali težav z zmogljivostjo.

**Splošna ocena:** Modul TaskController izpolnjuje vse funkcionalne zahteve za testirane operacije.

---

**Poročilo pripravil:** Georgi Dimov 
**Datum:** 6. november 2025
