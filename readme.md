# Miniräknare TDD (Labb2)

Syftet med denna laboration är att använda att tillämpa testdriven utveckling, dvs skriva tester och bygga(expandera) applikationen utifrån dessa.

## Verktyg

Här används Eclipse med JUnit4 

### Funktioner

Miniräknaren tar in en sträng från användaren och returnerar ett svar som skrivs ut på konsollen. Input valideras och om exempelvis bokstäver skrivs kommer ett felmeddelande att tala om detta.


 

```
Klarar av:

+ addition 
- subtraktion
* multiplikation
/ division
% modulus 
√ rot
^ exponent 
l logarithm (bas10) även "log" kan skrivas
() Parenteser

Klarar inte av:

parenteser inom parenteser.

```

### Grundtanke

Programmet bygger på for-loopar som beräknar delar av det inmatade talet efter operandernas prioritet.

Tankesättet är gissningsvis resulatet av begränsade mattematikkunskaper, koden hade förmodligen gått att göra enklare men funktionaliteten är bra.

miniräknaren tar in en sträng från användaren splittas vid varje tecken och läggs i en lista. 

String regex = "(?<=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])";

String temp[] = expression.split(regex);

En inmatning av 3*2+3/2 ser ut så här:

Miniräknaren
> 3*2+3/2  

[0]=”3”
[1]=”*”
[2]=”2”
[3]=”+”
[4]=”4”
[5]=”/”
[6]=”2”

En for-loop tar hand om 3*2 och 4/2, ersätter dessa tecken med summan och ”” för att radera oanvända index.

[0]=””
[1]=””
[2]=”6” - dvs 3*2
[3]=”+”
[4]=””
[5]=””
[6]=”2” – dvs 4/2

När det inte finns några ”*” eller ”/” så bryts loopen och en ny lista sparas där tomma index raderas. Detta genom att använda ett Stringbuffer-objekt och append’a alla index i listan.

”” + ”” + ”6” + ”+” + ”” + ”” + ”2” = ”6+2”
[0]=”6”  
[1]=”+”
[2]=”2” 

Detta summeras sedan och ger ett resultat. Hade det varit fler tal att addera/subtrahera så hade resultatet av en uträkning sparats som en minnesvariabel och istället för att låta ”double d1 + double d2” gå in i metoden för addition får ”double resultat + double d2” göra det vilket i slutändan ger en double som resultat.

Detta sätt att bygga koden visade sig fungera bra, det krävdes mycket hantering av fall där flera operander i rad får index att peka på tecken som metoderna inte kan ta emot.

### Parenteser

När allt annat fungerade så skrevs en metod för att hantera parenteser, eftersom de har högst prio och måste räknas ut först fick denna metod bli huvudmetod och vara den som körs från mainmetoden. 

I praktiken så undersöker metoden om det finns parenteser, om så är fallet så används substring-metoden för att separera dessa från resten av input-strängen. Sedan kan parentesen ses som eget tal (som beräknas precis som en uträkning utan parenteser) men vars resultat ”klistras” in istället för parentesen i den ursprungliga strängen. Denna passerar sedan än en gång genom metoden som gör alla beräkningar. Här så måste en parentes anslutas med "*" om tecknet innan eller efter är en siffra. Detta görs genom en if-sats.


### Problem med detta tankesätt

Det var e

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Tester

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
