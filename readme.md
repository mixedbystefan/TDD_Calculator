# Miniräknare TDD 

Syftet med denna laboration är att använda att tillämpa testdriven utveckling, dvs skriva tester och bygga(expandera) applikationen utifrån dessa. 

## Verktyg

* Eclipse
* JUnit4 

## Funktionalitet

Miniräknaren klarar av de fyra räknesätten samt exponent, modulus, roten ur och logaritm (bas10). Den klarar också av parenteser (men inte parenteser inom parenteser).

## Om koden

Appen tar in en sträng från användaren som splittas vid varje tecken och läggs i en lista. 

```
String regex = "(?<=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])";

String temp[] = userInput.split(regex);
```

Programmet bygger på några for-loopar ordnade efter operandernas prioritet.

Förenklat Psuedokod-exempel kring två av dessa.

```
for (varje index i listan)
{
	OM index = * {räkna ut detta}
	OM index = / {räkna ut detta}
}

renderaNyLista()

for (varje index i listan)
{
	OM index = + {räkna ut detta}
	OM index = - {räkna ut detta}
}

renderaNyLista() och skriv ut resultat

```

Multiplikation och division har alltså samma prio och kan därför räknas ut tillsammans oberoende av intern ordning, om användarinput innehåller "*" kommer en for loopen att snurra tills varje operand av samma prioriten är ersatt med en summa.

```
if (temp[i].equals(("*"))) 

		{
			result = multiply(d1, d2); 	
	
			temp[i-1]="";
			temp[i]="";
			temp[i+1]=Double.toString(result);
		 }
```

Exempel 1+3*2 


``` 
innan talet gått in i if-satsen

[0]=”1”
[1]=”+”
[2]=”3”
[3]=”*”
[4]=”2”

efter talet gått in i if-satsen

[0]=”1”
[1]=”+”
[2]=””
[3]=””
[4]=”6”

efter talet passerat for-loopen skapas ett Stringbuffer-objekt av listan vilket raderar tomma index.


”1”  ”+”  ””  ””  ”6” 


Ny lista skapas:

[0]=”1”
[1]=”+”
[3]=”6”


```
## Svårigheter med detta

Eftersom en for-loop hanterar alla uträkningar kring operander av samma prioritet utan att brytas så krävs det att miniräknaren vet om den ska räkna ut talet som ett nytt tal eller utifrån resulatet av beräkningen innan.

Av den anledningen har jag skapat ett "minne" som kommer ihåg resulatet av beräkningen innan, minnet motsvarar resulatet av uträkningen innan MEN nollställs om uträkningen innan inluderar en operand av annan prio.



```
if (temp[i].equals(("+"))|| temp[i].equals(("-")))
{
mem=0.0;
}
			
			
if (temp[i].equals(("*"))) 
{
		if (mem==0.0) 
				{
				  result = multiply(d1, d2); mem=result;	
				}
		else 
				{
				  result = multiply(mem, d2); mem=result;		
				}
}
```



Så här i efterhand så tänker jag att det hade det varit lättare att överblicka och förstå koden om jag hade valt att rendera en ny lista efter varje uträkning, dvs inte haft flera for-loopar utan bara en och genom if-satser styrt ordningen.

Psuedokod

```
While (det finns en operand i listan)
{
     for (i=0; i<listan.length; i++)

		{
			If (i == ”*”){ multiply() break;}
			Else If (i == ”/”){ divide() break;}
			Else if (..nästa operand i prio)
			Else if..
			
		}

Update list()
}

```


    

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
