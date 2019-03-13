package calculator;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.annotation.processing.RoundEnvironment;
import javax.management.RuntimeErrorException;
import javax.xml.bind.ParseConversionEvent;

import org.junit.internal.builders.IgnoredBuilder;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;


public class Calculator {
	StringBuffer sBuffer;
	StringBuffer sBuf;
	Calculator check;
	boolean memoryInUse=false;
	String regex = "(?<=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*%√\\/\\^A-Za-z])";
	String updatedExpression="";
	boolean isChar;
	
	
	// main metod med konsol-gränssnitt
	public static void main(String[] args) 
	
	{	
		System.out.println("Miniräknaren");	
		String loop = "calc";
		while (loop.equalsIgnoreCase("calc")) 
		{
		
		System.out.print(">");	
		Scanner scanner =new Scanner(System.in);
		String input = scanner.nextLine();
		
		if(input.equalsIgnoreCase("q")) 
		{
			loop="quit";
			System.exit(0);
		}
		else 
		{
			Calculator calculator = new Calculator();
			String output = calculator.calculateExpression(input);
			
			// Resultatet skrivs ut och en double med .0 visas utan decimaler
			System.out.println("Resultat: " + round(output));}
		}

	}
	
	// Metod som egentligen bara sköter paranteserna men som anropar "huvudmetoden" och bygger 
	// en ny sträng när paranteserna är utträknade. Returnerar också svaret.
	
	public String calculateExpression(String s)
	
	{            
        check = new Calculator();
        
        
        checkForBracketsWithinBrackets(s);
        
        while(s.contains(Character.toString('('))||s.contains(Character.toString(')')))
        
        {
            for(int o=0; o<s.length();o++)
            
            {
                try
                {     //                                                
                    if((s.charAt(o)==')' || Character.isDigit(s.charAt(o))) // Kollar om parentesen kan räknas ut och ersättas av *
                            && s.charAt(o+1)=='(')							// Beroende på index bredvid
                    {                         
                        s=s.substring(0,o+1)+"*"+(s.substring(o+1));         
                    }                                                      
                }
                
                catch (Exception ignored){/* Används bara för att programmet inte ska crascha om ifsatsens innehåll inte funkar*/}   
                
                if(s.charAt(o)==')')
                
                {                                 
                    for(int i=o; i>=0;i--)
                    
                    {
                        if(s.charAt(i)=='(')        // Skickar in det som ligger mellan parentserna i uträkningsmetoden
                        {                          
                            String in = s.substring(i+1,o);
                            in = check.doMath(in);   
                            s=s.substring(0,i)+in+s.substring(o+1); // Gör en ny sträng utifrån inputsträngen och uträknad parentes
                            i=o=0;
                        }
                    }
                }
            }
            // Kollar så det inte finns parenteser som inte har någon motsvarande parantes
            
            if(s.contains(Character.toString('('))||s.contains(Character.toString(')'))||
                    s.contains(Character.toString('('))||s.contains(Character.toString(')')))
            
            {
            	
            	throw new RuntimeErrorException(null, "Måste finnas öppnande och stängande parentes");
                
            }
        }
        
        
        s=check.doMath(s);
       
        return s;
    }
	

	
	// Huvudmetod som anropar metoder för beräkningar
	// anropar en metod beroende på input och returnerar en summa till calculateExpression()
	// For-loopar går igenom den Lista som skapas från inputsträngen och går igenom alla räknesätt
	// I den ordning de är prioriterade. Beräkningar som är gjorda skriver över det som räknats ut med summan 
	// och mellan varje räknesätt så tas alla raderade(tomma) index bort.
	// En variabeln för minne (mem och mem_2) används för att direkt kunna plocka in resultatet av
	// tidigare uträkning under pågående for-loop. 
	

	public String doMath(String expression) {
	
		
		double result = 0.0;
		double mem = 0.0;
		double mem_2 = 0.0;
		
		
	
		// Ersätter -- med + osv
		
		expression = adjustStackedOperands(expression);

		// Delar upp input i en lista
		String temp[] = expression.split(regex);
		
		// Kollar så att input inte innehåller bokstäver mm
		
		validateInput(temp);
		
		

		if (temp.length >1) 
		{
			
			for (int i=0; i<temp.length; i++)
			{ 
			
				
				
				if (temp[i].equals(("√"))) 
				{ 
					// Kollar om strängen är double eller int
					
					if (isDouble(temp[i+1])) {result = root(Double.parseDouble(temp[i+1]));}
					else Integer.parseInt(temp[i+1]);
			
					temp[i+1]=Double.toString(result);
					mem=0.0;
					
					try {
						if (temp[i-1].equals("*")) 
						{
							temp[i-1]="";	
						}
						else temp[i]="*";
						
					} catch (Exception ignored) {
						temp[i]="";
					}
				
				}
				
				if (temp[i].equals(("^"))) 
				{ 
					
					double d1 = Double.parseDouble(temp[i-1]);
					double d2 = Double.parseDouble(temp[i+1]);
					result = exponent(d1, d2);
					temp[i-1]="";
					temp[i]="";
					temp[i+1]=Double.toString(result);
					mem=0.0;
					
				
				}
				
				if (temp[i].equals(("l"))) 
				{ 
					
					double d = Double.parseDouble(temp[i+1]);
					result = logarithm(d);
					temp[i]="*";
					temp[i+1]=Double.toString(result);
					mem=0.0;
					
				
				}
				
			
			}	
			
			// metod som tar bort tomma index
			temp = refreshList(temp);
			
		for (int i=0; i<temp.length; i++)
		{ 	
			// Tar bort + om detta inleder ekvationen
			if(temp[0].equalsIgnoreCase("+")) {temp[0]=""; continue;}
			if(temp[temp.length-1].equalsIgnoreCase("+")) {temp[temp.length-1]="";}
			
			
			// Om indexet håller + eller - så nollställs minnet
			if (temp[i].equals(("+"))|| temp[i].equals(("-")))
			{mem=0.0;}
			
			
			
			if (temp[i].equals(("*"))|| temp[i].equals(("/"))|| temp[i].equals(("%")))
			{
				
				double d1 = Double.parseDouble(temp[i-1]);
				double d2 = 0;
				
				// Om ett tal ser ut som 4*-2 så skickas talet efter "-" in i metoden som ett negativt tal
				if(isDouble(temp[i+1])) {d2 = Double.parseDouble(temp[i+1]);}
				else d2 = -(Double.parseDouble(temp[i+2]));
				
				
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
					
						temp[i-1]="";
						temp[i]="";
						temp[i+1]=Double.toString(result);
				}
				if (temp[i].equals(("/"))) 
				{
					
					if (mem==0.0) 
						{
						result = divide(d1, d2); mem=result;
						
						}
					else 
					{
						result = divide(mem, d2); mem=result;
						
					}
					
					temp[i-1]="";
					temp[i]="";
					temp[i+1]=Double.toString(result);
				}
				
				if (temp[i].equals(("%"))) 
				{
					int res;
					if (mem==0.0) 
						{
						
						int _d1 = (int)d1;
						int _d2 = (int)d2;
						res = modulus(_d1, _d2);
						
						
						
						}
					else 
					{
						
						int _d2 = (int)d2;
						int _mem= (int) mem;
						res = modulus(_mem, _d2);
						
					}
					
						temp[i]="";
						temp[i-1]="";
						temp[i+1]=Integer.toString(res);
				}
				
			
			}
			
			
		}
		
		// metod som tar bort tomma index
		temp = refreshList(temp);
		memoryInUse=false;
		
		// For loop som går igenom + och -
		
		for (int i=0; i<temp.length; i++)
		{ 
		
		if (temp[i].equals(("+"))|| temp[i].equals(("-")))
		{
			double d1 = 0;
			if (i==0) {continue;}
			else {d1 = Double.parseDouble(temp[i-1]);}
			double d2 = Double.parseDouble(temp[i+1]);
			
			if (temp[i].equals(("+"))) 
			{
				if (mem_2==0.0 && memoryInUse==false) {
					result = add(d1, d2); mem_2=result; memoryInUse=true;
					}
				else  
					{
					result = add(mem_2, d2); mem_2=result;
					}
			}
		
			if (temp[i].equals(("-"))) 
			{
				
				if (mem_2==0.0 && memoryInUse==false) 
					
					{
					result = subtract(d1, d2); mem_2=result; memoryInUse=true;
					}
				
				else 
					{
						result = subtract(mem_2, d2); mem_2=result;
					}
			}
			
		
		}
	}
		
	}
		
		// Om input från användare bara är en siffra så returneras denna som svar
		else return expression;
		
		// Resultat returneras
		
		String out = Double.toString(result);
		return out;
	
	}
	
	// Om resultatet miniräknaren ger har en decimal men .0 så tas denna bort
	private static String round(String result) 
	{
		if(isInteger(Double.parseDouble(result)))
        {
			result=Integer.toString(checkDoubleAsInt(Double.parseDouble(result)));
        }
		
		return result;
		
	}
	// Korigerar strängen vid olika fall av inputs som ska fungera men måste korrigeras för
	// att inte crascha programmet
	
	
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

	private void checkForBracketsWithinBrackets(String s) 
	{
		String temp[] = s.split(regex);
        int x =0;
		for (String i : temp) 
		{
			
			// Om två parenteser i rad är åt samma håll så kastas undantag
			if (i.equals("(")) {x++;}
			if (i.equals(")")) {x--;}
			if (x>1) {throw new RuntimeErrorException(null, "Parantes inom Parentes är inte implementerad i miniräknaren");}
			
		}
	}
	
	// Kontrollerar så inte bokstäver används samt att operander inte läggs efter varandra i otillåten ordning

	private void validateInput(String[] temp) {
		try 
		{
      	  for (String o : temp) 
      	  { 
      		isChar = o.matches("[a-öA-Ö]{1}");
      		if (isChar && !o.equals("^") && !o.equalsIgnoreCase("l")) 
      		{
      			throw new NumberFormatException("Inga bokstäver"); 
      		
      		}
      		
      		for (int i=0; i<temp.length; i++)
          	{
          		if (temp[i].equals("*") && temp[i+1].equals("*")) 
          		{
          			throw new NumberFormatException("** = Otillåten kombination av operander");
          		}
          		if (temp[i].equals("/") && temp[i+1].equals("/")) 
          		{
          			throw new NumberFormatException("// = Otillåten kombination av operander");
          		}
          		if (temp[i].equals("*") && temp[i+1].equals("/"))
          		{
          			throw new NumberFormatException("*/ = Otillåten kombination av operander");
          		}
          		if (temp[i].equals("/") && temp[i+1].equals("*")) 
          		{
          			throw new NumberFormatException("/* = Otillåten kombination av operander");
          		}
          	}
      	  }
      	  
      	
		
      	} 
		
		catch (InputMismatchException e) 
		{
      	  e.printStackTrace();
      	  System.err.println("Otillåten input");
      	}
		
	}

	// Kollar om strängen är en double
	public boolean isDouble(String value) {
	    try {
	        Double.parseDouble(value);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	private static boolean isInteger(double d){
	    double dAbs = Math.abs(d);
	    int i = (int) dAbs;
	    double result = dAbs - (double) i;
	    if(result<0.00001){
	        return true;            
	    }else{
	        return false;          
	    }
	}
	
	private static int checkDoubleAsInt(double d){
	    double dAbs = Math.abs(d);
	    int i = (int) dAbs;
	    double result = dAbs - (double) i;
	    if(result<0.0001){
	        return d<0 ? -i : i;            
	    }else{
	        return 998877665;          
	    }
	}


	// Uppdaterar listan genom att ta bort tomma index
	public String[] refreshList(String[] temp) {
		StringBuffer sBuffer = new StringBuffer();

		for (String i : temp)
		{			
			sBuffer.append(i);
		}
		updatedExpression = sBuffer.toString();
		temp = updatedExpression.split(regex);
		return temp;
	}
	
	
	
	// Alla metoder för själva beräkningarna

	//add
	public double add(double d1, double d2) 
	{
		return d1 + d2;
	}
	//subtract
	public double subtract(double d1, double d2) 
	{
		return d1 - d2;
	}
	//multiply
	public double multiply(double d1, double d2) 
	{
		return d1 * d2;
	}
	
	//divide
	public double divide(double d1, double d2) 
	
	{
		if (d2==0) {throw new ArithmeticException("Du kan inte dela med 0!"); }
		return d1 / d2;
	}	
	
	// modulus %
		
	public int modulus(int d1, int d2) 
	{
		return Math.floorMod(d1, d2);
	}
		
	// root √
		
	public double root(double d1) 
	{
		return Math.sqrt(d1);
	}
	// exponent ^
	
	public double exponent(double d1, double d2 ) 
	{
		return Math.pow(d1, d2);
	}

	
	// base 10 logarithm 10 -  skrivs som "log" eller "l"
	
	public double logarithm(double d1) 
	{
		return Math.log10(d1);
	}
	
	
	
		

	

}