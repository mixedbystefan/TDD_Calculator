# Miniräknare TDD 

Syftet med labb 2 i testdriven utveckling är att med hjälp av JUnit skriva tester som ska stå som grund till implementerad funktionaltet - att expandera appen utifrån dessa. 

### Verktyg

* Eclipse
* JUnit4 

## Funktionalitet

Miniräknaren klarar av de fyra vanligaste räknesätten samt exponent, modulus, roten ur och logaritm (bas10). Den klarar också av parenteser (men inte parenteser inom parenteser).

## Om koden

Appen är konsollbaserad, tar in en sträng från användaren som splittas vid varje tecken och läggs i en lista. 

```
String regex = "(?<=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])";

String temp[] = userInput.split(regex);
```

Programmet bygger på några for-loopar ordnade efter operandernas prioritet.

Psuedokod-exempel .

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

Multiplikation och division har samma prio och kan därför räknas ut tillsammans oberoende av intern ordning, om användarinput innehåller "*" kommer loopen att snurra tills varje operand av samma prioriten är ersatt med en summa.

```
if (temp[i].equals(("*"))) 

		{
			result = multiply(d1, d2); 	
	
			temp[i-1]="";
			temp[i]="";
			temp[i+1]=Double.toString(result);
		 }
```

Ett enkelt exempel 

### 1+3*2 


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

JAg har också försökt att inte göra massa test som inte testar funtionalitet som redan är testad, mot slutet så kunde jag inte motså att göra ett längre tal bara för att det ser komplicerat ut. Det fick grönt vilket borde betyda att jag varit ganska noga med testerna fram till detta.

```
@Test
	public void allOperatorsAdvanced_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "-1+2/(√16-2)*√4--2+10^2-8%3"; 
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("101.0", actual);	
	}
```
Sista gjordes tester kring parenteser och här krävdes en helt ny huvudmetod, eftersom denna kunde skrivas helt separat utan att påverka det som redan fanns där rörde det inte till koden så mycket.

I praktiken så undersöker metoden om det finns parenteser, om så är fallet så används substring-metoden för att separera dessa från resten av strängen. Sedan kan parentesen ses som eget tal (som beräknas precis som en uträkning utan parenteser) men vars resultat ”klistras” in istället för parentesen i den ursprungliga strängen. Denna passerar sedan än en gång genom metoden som gör alla beräkningar. Här så måste en parentes anslutas med * om tecknet innan eller efter är en siffra vilket görs genom en if-sats.

## Tester Undantag

Det enda testet här som jag hade (och har) problem med är testet och undantaget som ska kastas om en double översiger värdet av double_MAX_VALUE. Om detta testet körs just innan resutatet skrivs ut så innebär det att appen redan hade krashat i metoden som gjorde uträkningen innan detta. Om jag inte tänker helt fel här borde det innebära att enda sättet att kunna kontrollera vilket undntag/meddelande som kastas är att göra en kontroll i alla metoder som gör uträkningar. I nuläget känner jag inte att detta behövs utan jag har valt att illustrera det i metoden add().

Metoden som inte kastar ett undantag utan bara kontrollerar om doublevärdet överstiger double.MAX_VALUE och returnerar en boolean.

```
private boolean isNumberWithinRange(Double result) 
	{
		
		try 
		{
			if (result< Double.MAX_VALUE)
	        {
				return true;
			
	        }
			
			else return false;
	    } 
		
		catch (Exception Ignored) {
	        return false;
	    }	
	}
	
```
I metoderna kan man sedan i ifsatsen säga att om värdet är inom ramen för vad en double klarar av så görs ekvationen, annars kastas ett undantag.
	
```
//add
	public double add(double d1, double d2) 
	{
		if (isNumberWithinRange(d1+d2)) {return d1 + d2;}
		else throw new InputMismatchException("För stort double-värde"); 
	}

```
## Avrundning

Jag har haft två andra miniräknare som förebilder, macOS egna och en avancerad räknare på nätet.  Ett resultat med många decimaler visades på samma sätt men däremot gillade jag inte att 10 + 10 blev 20.0. Detta avrundades vid utskriften i mainmetoden efter alla tester. Det kanske borde testas separat bara för att det kan ge fel precis som allt annat men jag har dubbelkollat detta manuellt och nöjer mig med den lösningen här.ä

## Versionshanteringg

Git har använts lokalt sedan start, vid ett tillfälle hade jag en crash när jag gick över till Maven, då gjorde jag ett nytt projekt för att det var så mycket problem. Av den anledningen så har går inte historiken så långt tillbaka och denna version är den jag publicerat på GitHub. 

Jag tycker också egentligen att dokumentationer både ser bättre ut och går snabbare att skriva i word(sparad som PDF) men nu gör jag den som en readme på gitHUb bara för att jag vill testa hur det fungerar.

## Slutligen

För mig har denna uppgift varit väldigt givande, dels för att jag inte trodde jag skulle klara att göra miniräknaren men också för att testerna visade sig väldigt användbara. 

Jag inser förstås att mitt sätt att skriva koden säkert är väldgt DIY och förmodligen inte den mest kompakta/smartaste. 

Samtidigt är det just detta som gör det extra givande - att det faktiskt går att tänkta ut något som för mig är ganska avancerat - att "fiffla" sig till en fungerande app med hjälp av TDD.

