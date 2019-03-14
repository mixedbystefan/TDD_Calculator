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



## Tankar kring testerna

Jag är inte så avancerad i matematik och till en början så såg jag inte riktigt födelen med TDD, jag ville snabbt skulle komma igång med miniräknaren då det kändes som väldigt svårt, testerna kändes som något som mest skulle vara ivägen. 

Snabbt blev det tydligt att det var otroligt använbart att ha en rad tester som kan köras. Int före bara att bygga koden utan även för att ständigt kontrollera at befintlig kod fungerar. Det övergick ganska snabbt i att testerna var helt nödvändiga, väldigt intressant process för mig.

Enda gången jag egentligen frångick att utgå från testerna var mot slutet då jag försökte provocera fram fel, då gick det snabbare att köra programmet och skriva "dumma" tal. Sedan kunde tester på de som crashade göras och fixas.

Jag antar att det är ungefär så det fungerar i verkligheten, att man inte låser sig till att utgå från testerna men att det helt enkelt är väldigt användbart så att man automatiskt förlitar sig på dom.

Det som egentligen tog mest tid vara att testa kombinationer som kanske inte är helt vanliga och som inte förhåller sig till det vanliga - siffra operand siffra

Exempelvis 2*(-√4) har en rad operander mellan siffrorna och därför måste varje fall lösas. 

Ex.

```
if (temp[i].equals(("√"))) 
{ 

			result = root(Double.parseDouble(temp[i+1]));

			temp[i+1]=Double.toString(result);
			mem=0.0;
					
			try 
			{
				if (temp[i-1].equals("*")) 
				{
					temp[i-1]="";	
				}
				if (temp[i-1].equals("-")) 
				{
					temp[i]="-";
					temp[i-1]="";
				}
				
			   else   temp[i]="*";
						
			} 
					
			catch (Exception ignored)
			 
					{
						temp[i]="";
					}
				
}
```
Sista gjordes tetser kring parenteser och här krävdes en helt ny huvudmetod, eftersom denna kunde skrivas helt separat utan att påverka det som redan fanns där rörde det inte till koden så mycket.



## Versionshanteringg

Git har använts men GitHub kom in vid ett senare tillfälle på denna version av appen, jag hade en tidigare version där jag arbetade mot GitHub men hade en krasch i kombination med att jag gick över till MAven. JAg lessnade och skapade ett nytt projekt med samma kod och arbetade lokalt några dagar. Det är denna version jag nu har på GitHub.

## Slutligenn


