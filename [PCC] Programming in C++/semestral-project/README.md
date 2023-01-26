# Výpočet determinantu čtvercové matice

## Zadání

Naimplementovat program pro výpočet determinantu čtvercové matice pomocí Gaussovy eliminační metody. Očekává se, že
tento program bude schopen pracovat s maticemi 1000x1000 v rozumném čase. Aplikace na vstupu dostane čtvercovou 
matici, na výstupu vypíše hodnotu jejího determinantu.

## Popis

Kalkulačka pro výpočet determinantu čtvercové matice může přijmout na vstup matici z .txt souboru, který bude umístěn ve složce /matrices, nebo
vygenerovat matici s náhodnými hodnotami o zadaném rozměru. Vygenerovanou matici pak uloží do složky /matrices s
názvem "generated".
Složka /matrices již obsahuje několik matic pro testování.

## Implementace

Nejprve tento program převede matici na horní trojúhelníkovou matici pomocí Gaussovy eliminační metody, a poté vypočítá
determinant vynásobením hlavní diagonály. Složitost algoritmu je O(n^2). Vícevláknová implementace: program rozdělí řádky
matice mezi vlákny (množství závisí na hardwaru). Potom tato vlákna upravují původní matici na horní trojúhelníkovou matici.
A spočítá se determinant. Používá se Work crew model.

## Spuštění a přepínače

Program se spouští z příkazové řádky.
Parametry nastavení programu:

- -s - jednovláknová varianta;
- -t - vícevláknová varianta;
- -f [name] (-s | -t) - dostane matici z .txt souboru s názvem [name];
- -r [dim] (-s | -t) - vygeneruje matici o rozměru [dim], a poté uloží matici do souboru .txt s názvem "generated";
- pokud do příkazové řádky zadat jen ./Determinant program vypíše seznam parametrů, které podporuje.

Příklady spuštění:

```bash
./Determinant
```

```bash
./Determinant -f matrix50-1 -s
```

```bash
./Determinant -r 20 -t
```

## Měření

Měření proběhlo vůči kódu v commitu 8519d578, na 8 jádrovém i5-9300H CPU taktovaném na 2.4 GHz. Vyšlo mi, že jednovláknová
varianta potřebuje pro matici 1000x1000 **debug:** ~5200ms, vícevláknová ~5200ms; **release:** ~1200ms, vícevláknová ~1200ms. 
**Jelikož jsem nevěděl jak paralelizovat Gaussovu eliminační metodu, nedalo se mi naimplementovat vícevláknovou variantu, 
která by byla rychlejší než jednovláknová.**

