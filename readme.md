# Miniräknare TDD 

Syftet med labb 2 i testdriven utveckling är att med hjälp av JUnit skriva tester som ska stå som grund för implementerad funktionalitet - att expandera appen utifrån dessa.

## Verktyg

* Eclipse
* JUnit4 

## Funktionalitet

Miniräknaren klarar av de fyra vanligaste räknesätten samt exponent, modulus, roten ur och logaritm (bas10). Den klarar också av parenteser
(men inte parenteser inom parenteser).

Appen har ett konsolbaserat gränssnitt

## Om koden

Först tas användarinput(sträng)från användaren som splittas vid varje tecken och läggs i en lista. 

```
String regex = "(?<=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])";

String temp[] = userInput.split(regex);
```

Programmet bygger på några for-loopar ordnade efter operandernas prioritet.


### Psuedokod-exempel 

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

Multiplikation och division har samma prio och kan därför räknas ut tillsammans oberoende av intern ordning, om användarinput innehåller * kommer loopen att snurra tills varje operand av denna prioritet är ersatt med ett resultat från uträkningen.

```
if (temp[i].equals(("*"))) 

		{
			result = multiply(d1, d2); 	
	
			temp[i-1]="";
			temp[i]="";
			temp[i+1]=Double.toString(result);
		 }
```


### EX  1+3*2 


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

Eftersom varje for-loop hanterar flera operander utan att brytas mellan uträkningarna så används en minnesvariabel.

Minnet motsvarar resultatet av uträkningen innan och nollställs om uträkningen innan inkluderar en operand av annan prio.


### Om index pekar på  + eller -

```
if (temp[i].equals(("+"))|| temp[i].equals(("-")))
{
mem=0.0;
}
```
### Minnet avgör vad som går in i metoden för multiplikationen
```			
			
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

    

## Parenteser

När allt annat fungerade så skrevs en metod för att hantera parenteser, eftersom de har högst prio och måste räknas ut först fick denna metod bli huvudmetod och vara den som körs från mainmetoden.

I praktiken så undersöker metoden om det finns parenteser, om så är fallet så används substring-metoden för att separera dessa från resten av input-strängen. Sedan kan parentesen ses som eget tal (som beräknas precis som en uträkning utan parenteser).

Resultatet "klistras" sedan in istället för parentesen i den ursprungliga strängen. 


Substring() används för att "montera" en ny sträng (nedan)

```

for(int i=o; i>=0;i--)
                    
             {
                 if(s.charAt(i)=='(')        
                        {                          
                            String in = s.substring(i+1,o);
                            in = check.doMath(in);   
                            s=s.substring(0,i)+in+s.substring(o+1); 
                            i=o=0;
                        }
                    }

```
Denna uppdaterade sträng passerar sedan än en gång genom metoden som gör alla beräkningar. 


Om tecknet innan eller efter parentesen är en siffra ersätts denna med "*" för att parentsens innehåll ska multipliceras efter att innehållet monterats i ursprungliga inpusträngen.


## Felhantering

Mycket av felhanteringen gjordes direkt på användarens input. 

Här hade jag macOS miniräknare som förebild, exempelvis vill jag att appen ska returnera svaret 10 om användarens input är 10. 

Jag vill också att det inte ska spela någon roll om ekvationen inleds med +. 

Ett inledande minustecken kraschade koden för att det inte gick att hämta en siffra innan minustecknet, detta löstes med att helt enkelt lägga till en nolla. 

-5+2 är samma sak som 0-5+2.

Så här ser felhanteringen ut som görs på strängen med metoden replace()

```
private String adjustStackedOperands(String expression) 
	{
		String plusMinusToMinus = expression.replace("+-", "-");
		String ZeroTimesMinus = plusMinusToMinus.replace("0*-", "0*");
		String MinusPlusToMinus = ZeroTimesMinus.replace("-+", "-");
		String logTol = MinusPlusToMinus.replace("log", "l");
		String logTol_2 = logTol.replace("Log", "l");
		String logTol_3 = logTol_2.replace("LOG", "l");
		String multiplyBeforeRoot = logTol_3.replace("*√", "√");
		String twoMinusEqPlus =multiplyBeforeRoot.replace("--", "+");
		
		
		// Om första tecknet är - så läggs en nolla till innan
		if (twoMinusEqPlus.substring(0, 1).equalsIgnoreCase("-")){
			String updatedInput= "0" + twoMinusEqPlus;
			twoMinusEqPlus = updatedInput;
		}
		// Om första tecknet är * så skickas felmeddelande
		if (expression.substring(0, 1).equalsIgnoreCase("*")|| expression.substring(0, 1).equalsIgnoreCase("/"))
		{throw new ArrayIndexOutOfBoundsException("Första tecknet får inte vara * eller /!");}
		
		
		
		return twoMinusEqPlus;
	}

```
## Tankar kring testerna

Jag är inte så avancerad i matematik och till en början så såg jag inte riktigt fördelen med TDD, jag ville snabbt skulle komma igång med miniräknaren som kändes svår att koda, testerna såg jag som något som mest skulle vara i vägen.

Snabbt blev det tydligt att det var otroligt användbart att ha en rad tester som kan köras. Inte före bara att bygga koden utan även för att ständigt kontrollera at befintlig kod fungerar. Det övergick ganska snabbt i att testerna var helt nödvändiga, väldigt intressant process för mig.

Enda gången jag egentligen frångick att utgå från testerna var mot slutet då jag försökte provocera fram fel, då gick det snabbare att köra programmet och skriva "dumma" tal. Sedan kunde tester på de som kraschade göras och fixas.

Jag antar att det är ungefär så det fungerar i verkligheten, att man inte låser sig till att utgå från testerna men att det helt enkelt är väldigt användbart så att man automatiskt förlitar sig på dom.

Det som egentligen tog mest tid vara att testa kombinationer som kanske inte är helt vanliga och som inte förhåller sig till siffra-operand-siffra.

Exempelvis 2*(-√4) har en rad operander mellan siffrorna och därför måste varje fall lösas med en if-sats. 



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

Jag har också försökt att inte göra massa test som inte testar funktionalitet som redan är testad, mot slutet så kunde jag inte motså att göra ett längre tal bara för att det ser komplicerat ut. 

Det fick grönt vilket borde betyda att testerna har gjort ett bra jobb fram till detta.

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
Sist gjordes tester kring parenteser och här krävdes en helt ny huvudmetod, eftersom denna kunde skrivas helt separat utan att påverka det som redan fanns där rörde det inte till koden så mycket.

I praktiken så undersöker metoden om det finns parenteser, om så är fallet så används substring() för att separera dessa från resten av strängen. Sedan kan parentesen ses som eget tal (som beräknas precis som en uträkning utan parenteser) men vars resultat ”klistras” in istället för parentesen i den ursprungliga strängen. Denna passerar sedan än en gång genom metoden som gör alla beräkningar. Här så måste en parentes ersättas med * om tecknet innan eller efter är en siffra vilket görs genom en if-sats.

Parenteser inom parenteser blev för svårt för mig att tänka kring så detta implementerades inte, undantagshantering på detta gjordes däremot.

## Tester Undantag

Det enda testet här som jag inte känner mig helt övertygad om är testet och undantaget som ska kastas om en double överstiger värdet av double_MAX_VALUE. 

Om detta testet körs just innan "slutresutatet" skrivs ut så innebär det att appen redan hade krashat i metoden som gjorde uträkningen för att få detta resultat om värdet var för stort.

Det innebär också att programmet kan krascha av en overload vid alla tillfällen en input, konvertering eller utskrift görs. 

Om jag inte tänker helt fel här borde det innebära att enda sättet att kunna kontrollera detta är att göra det vid användarinput, innan alla uträkningarna osv. I nuläget väljer jag därför att endast illustrera en implementation i metoden add().

Metoden kontrollerar om doublevärdet överstiger double.MAX_VALUE.

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
I metoden add() körs additionen OM ovanstående metod säger att resultatet är inom ramen för en double.
	
```
//add
	public double add(double d1, double d2) 
	{
		if (isNumberWithinRange(d1+d2)) {return d1 + d2;}
		else throw new InputMismatchException("För stort double-värde"); 
	}
	

```


## Avrundning

Jag har haft två andra miniräknare som förebilder, macOS egna och en avancerad räknare på nätet. Ett resultat med många decimaler visades på samma sätt men däremot gillade jag inte att 10 + 10 blev 20.0. Detta avrundades vid utskriften i main-metoden efter alla tester. Det kanske borde testas separat bara för att det kan ge fel precis som allt annat men jag har dubbelkollat detta manuellt och nöjer mig med den lösningen här.


## Versionshantering

Git har använts lokalt sedan start, vid ett tillfälle hade jag en krasch när jag gick över till Maven, då gjorde jag ett nytt projekt för att det var så mycket problem. Av den anledningen så har går inte historiken så långt tillbaka och denna version är den jag publicerat på GitHub.

Jag tycker också egentligen att dokumentationer både ser bättre ut och går snabbare att skriva i word(sparad som PDF) men nu gör jag den som en readme på GitHUb bara för att jag vill testa hur det fungerar

Hittade en smidig lösning (för sent) - användes enbart för att kontrollera stavfel.

* [Word-doc to readme.md](https://gist.github.com/jesperronn/ff5764274b3642bc7f2f) 

## Slutligen

För mig har denna uppgift varit väldigt givande, dels för att jag inte trodde jag skulle klara att göra miniräknaren men också för att testerna visade sig väldigt användbara.

Jag inser förstås att mitt sätt att skriva koden säkert är väldigt DIY och förmodligen inte den mest kompakta/smartaste.

Samtidigt är det just detta som gör det extra givande - att det faktiskt går att tänkta ut något som för mig är ganska avancerat - att "fiffla" sig till en fungerande app med hjälp av TDD.

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

