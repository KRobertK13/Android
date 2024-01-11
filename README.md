# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### 2023. 10. 18.
### MOBWEB - MANAGEMENT OF BUDGET WITH EXCLUSIVE BALANCE-SHEET
### Kovács Róbert Kristóf - R92D9T
### kovacsrobert20011013@gmail.com 
### Laborvezető: Hudák János

## Bemutatás

A MOBWEB a modern banki alkalmazásokhoz hasonlóan képes nyomonkövetni a kiadások és bevételek alakulását, azzal a különbséggel, hogy lehetőséget biztosít a jövő megtervezésére is.
Mindemellett szemléletes eszközöket kínál a felhasználó számára a pénzügyi helyzetének elemzéséhez.
Az alkalmazás azon egyéneknek készül, akik a pénzügyi tudatosságukat és a hatékony spórolást egy egészen új szintre szeretnék emelni.

## Főbb funkciók

Az alkalmazás kezdőlapja egy menü, ahol a maximális tervezett kiadás és megtakarítani kívánt összeg állítható be. Ezen kívül választhatunk a rendelkezésre álló másik két Activity használata közül.
A két Activity: 
- A bevétel-kiadás lista.
  Itt típusonként vehetünk fel objektumokat kiadás vagy bevétel előjellel. Felvehető típusok: Munka, Eladás, Vásárlás, Kikapcsolódás, Rezsi. Továbbá megadhatjuk a kiadás nevét, mértékét és egy rövid ismertetőt hozzá.
  A listából törölhetünk elemeket egyesével, vagy akár egyszerre az összeset.
- Az elemző nézet: Két Fragmentet tartalmaz.
  - Kiadás elemző: Diagramon ábrázolja a kiadások tipusonkénti egymáshoz viszonyított mennyiségét. Továbbá feliratokon összesíti, egyes típusú kiadásokra hány alkalommal került sor és összesen mennyi a rájuk elköltött összeg. A nézet láthatóvá teszi, hogy mennyire állunk közel vagy egyenesen mennyivel léptük át a tervezett keretet.
  - Kiadás-bevétel elemző: Összesíti a bevételeket és kiadásokat, arányukat diagrammon ábrázolja. Feltünteti, hogy mennyire vagyunk közel a megtakarítási célhoz.

Amennyiben elértük vagy átléptük a költési keretet az alkalmazás értesítéssel jelzi, ahogy azt is, ha elértük a megtakarítani kívánt összeget.
Az alkalmazás a munka folyamán történt változtatásokat természetesen elmenti.

## Választott technológiák:

- UI
- fragmentek
- RecyclerView
- Perzisztens adattárolás
- Intent
