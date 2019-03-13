package calculator;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

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
		while (1==1) {
		
		System.out.print(">");	
		Scanner scanner =new Scanner(System.in);
		String input = scanner.nextLine();
		Calculator calculator = new Calculator();
		String output = calculator.calculateExpression(input);
		System.out.println("Resultat: " + output);
		}

	}
	
	
	
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
                    if((s.charAt(o)==')' || Character.isDigit(s.charAt(o))) // om o är ) eller siffra SAMT parantesen efter är (
                            && s.charAt(o+1)=='(')
                    {                         
                        s=s.substring(0,o+1)+"*"+(s.substring(o+1));        
                    }                                                      
                }
                
                catch (Exception ignored){/* Används bara för att programmet inte ska crascha om ifsatsens innehåll inte funkar*/}   
                
                if(s.charAt(o)==')')
                
                {                                 
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
                }
            }
            // 
            if(s.contains(Character.toString('('))||s.contains(Character.toString(')'))||
                    s.contains(Character.toString('('))||s.contains(Character.toString(')')))
            
            {
            	
            	throw new RuntimeErrorException(null, "Måste finnas öppnande och stängande parentes");
                
            }
        }
        
        
        s=check.doMath(s);
        return s;
    }
	

	
	// Huvudmetod so tar in det som matas in i miniräknaren som en sträng
	// anropar en metod beroende på input och returnerar en summa
	
	
	



	public String doMath(String expression) {
	
		
		double result = 0.0;
		double mem = 0.0;
		double mem_2 = 0.0;
		
		
	
		// Ersätter -- med + osv
		
		expression = adjustStackedOperands(expression);

		// Delar upp input i en lista
		String temp[] = expression.split(regex);
		
		// Kollar så att input inte innehåller bokstäver
		
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
			
			temp = refreshList(temp);
			
		for (int i=0; i<temp.length; i++)
		{ 	
			// Tar bort + om detta inleder ekvationen
			if(temp[0].equalsIgnoreCase("+")) {temp[0]=""; continue;}
			if(temp[temp.length-1].equalsIgnoreCase("+")) {temp[temp.length-1]="";}
			
			
			
			if (temp[i].equals(("+"))|| temp[i].equals(("-")))
			{mem=0.0;}
			
			
			
			if (temp[i].equals(("*"))|| temp[i].equals(("/"))|| temp[i].equals(("%")))
			{
				
				double d1 = Double.parseDouble(temp[i-1]);
				
				double d2 = 0;;
				try 
				{
					d2 = Double.parseDouble(temp[i+1]);
					
					
				} 
				
				// Om ett tal ser ut som 4*-2 så skickas talet efter "-" in i metoden som ett negativt tal
				catch (Exception Ignored){d2 = -(Double.parseDouble(temp[i+2]));}
					 
				
				
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
		
		temp = refreshList(temp);
		memoryInUse=false;
		
		
		for (int i=0; i<temp.length; i++)
		{ 
			if(temp[0].equals("+")) {temp[0]=""; continue;}
			if(temp[temp.length-1].equals("+")) {temp[temp.length-1]="";}
		
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
		
		// Om det bara är en siffra retureneras den
		else return expression;
		// Resultat returneras
		String out = Double.toString(result);
		return out;
	
	}
	
	// Korigerar strängen vid olika fall av inputs med + och - som skulle crascha programmet annars
	
	private String adjustStackedOperands(String expression) {
		String plusMinusToMinus = expression.replace("+-", "-");
		String ZeroTimesMinus = plusMinusToMinus.replace("0*-", "0*");
		String MinusPlusToMinus = ZeroTimesMinus.replace("-+", "-");
		String logTol = MinusPlusToMinus.replace("log", "l");
		String multiplyBeforeRoot = logTol.replace("*√", "√");
		String twoMinusEqPlus =multiplyBeforeRoot.replace("--", "+");
		
		
		
		if (twoMinusEqPlus.substring(0, 1).equalsIgnoreCase("-")){
			String updatedInput= "0" + twoMinusEqPlus;
			twoMinusEqPlus = updatedInput;
		}
		
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
			
			
			if (i.equals("(")) {x++;}
			if (i.equals(")")) {x--;}
			if (x>1) {throw new RuntimeErrorException(null, "Parantes inom Parentes är inte implementerad i miniräknaren");}
			
		}
	}

	private void validateInput(String[] temp) {
		try 
		{
      	  for (String o : temp) 
      	  { 
      		isChar = o.matches("[a-öA-Ö]{1}");
      		if (isChar && !o.equals("^") && !o.equalsIgnoreCase("l")) 
      		{
      			throw new RuntimeErrorException(null, "Inga bokstäver"); 
      		
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

	
	// base 10 logarithm 10 -  log
	
	public double logarithm(double d1) 
	{
		return Math.log10(d1);
	}
	
	
	
		

	

}