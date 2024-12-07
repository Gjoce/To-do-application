# Poročilo: Scrum Metodologija pri Razvoju To-Do Aplikacije

V tem terminu vaj smo sodelovali pri razvoju funkcionalnosti **dodajanja prilog k nalogam** za To-Do aplikacijo. Sledili smo Scrum metodologiji in beležili napredek na GitHubu.

---

## 1. Uporabniška zgodba

**Kot uporabnik želim možnost dodajanja prilog (npr. slike ali dokumente) k nalogam, da imam vse pomembne informacije na enem mestu.**

---

## 2. Razčlenitev uporabniške zgodbe na naloge

Uporabniško zgodbo smo razdelili na manjše naloge, ki so specifične, merljive in izvedljive v kratkem času.

| Naloga                          | Opis                                                                                                   | Priority |
| ------------------------------- | ------------------------------------------------------------------------------------------------------ | -------- |
| Ustvarjanje modela za priloge   | Dodajanje entitete `Attachment` z atributi (ID, ime datoteke, tip datoteke, povezava na nalogo).       | P02      |
| Nastavitev API-ja za nalaganje  | Implementacija endpointa za nalaganje prilog in povezovanje s specifičnimi nalogami.                   | P02      |
| Dodajanje podpore na frontend-u | Prilagoditev React aplikacije za omogočanje dodajanja prilog k nalogam (vmesnik za nalaganje datotek). | P01      |
| Shranjevanje v bazo             | Konfiguracija povezave med aplikacijo in bazo za shranjevanje podatkov o prilogah.                     | P03      |
| Testiranje funkcionalnosti      | Preverjanje pravilnosti delovanja dodajanja, nalaganja in prikaza prilog na nalogah.                   | P03      |

---

## 3. Planiranje z metodo Planning Poker

Za ocenjevanje zahtevnosti nalog smo uporabili metodo Planning Poker, pri čemer smo določili enote **Priority**.

---

## 4. Napredek na GitHub Agile tabli

Na GitHubu smo ustvarili projekt z naslednjimi stolpci:

- **To Do**: Naloge, ki jih še nismo začeli.
- **In Progress**: Naloge, ki so v razvoju.
- **Review**: Naloge, ki so pripravljene za pregled.
- **Done**: Zaključene naloge.

Vsaka naloga je bila ustvarjena kot **GitHub Issue** in premikana med stolpci glede na fazo razvoja.

---

## 5. Implementacija

V mapi `implementacija` smo začeli razvijati naslednje komponente:

- **Backend**: Endpoint za nalaganje prilog in njihovo povezovanje z nalogami (Spring Boot).
- **Frontend**: Uporabniški vmesnik za nalaganje in prikaz prilog (React).
- **Shranjevanje**: Konfiguracija baze za shranjevanje podatkov o prilogah.

---

## 6. Zaključek in naslednji koraki

- **Zaključene naloge**: Implementiran backend model in API, funkcionalnost nalaganja prilog na frontend-u.
- **Naslednji koraki**: Testiranje funkcionalnosti in optimizacija uporabniške izkušnje.

GitHub projekt smo sproti posodabljali, tako da je napredek jasno razviden na tabli.
