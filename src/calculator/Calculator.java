package calculator;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.management.RuntimeErrorException;


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
                
                catch (Exception ignored){System.out.println("Måste finnas öppnande och stängande parentes!");}   
                
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
		
		checkInputForLetters(temp);

		if (temp.length >1) 
		{
			
			for (int i=0; i<temp.length; i++)
			{ 
			
				
				
				if (temp[i].equalsIgnoreCase(("√"))) 
				{ 
					
					int d = Integer.parseInt(temp[i+1]);
					result = root(d);
					temp[i]="";
					temp[i+1]=Double.toString(result);
					mem=0.0;
					
				
				}
				
				if (temp[i].equalsIgnoreCase(("^"))) 
				{ 
					
					double d1 = Double.parseDouble(temp[i-1]);
					double d2 = Double.parseDouble(temp[i+1]);
					result = exponent(d1, d2);
					temp[i-1]="";
					temp[i]="";
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
			
			if (temp[i].equalsIgnoreCase(("+"))|| temp[i].equalsIgnoreCase(("-")))
			{mem=0.0;}
			
			
			
			if (temp[i].equalsIgnoreCase(("*"))|| temp[i].equalsIgnoreCase(("/"))|| temp[i].equalsIgnoreCase(("%")))
			{
				
				double d1 = Double.parseDouble(temp[i-1]);
				double d2 = Double.parseDouble(temp[i+1]);
				
				if (temp[i].equalsIgnoreCase(("*"))) 
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
				if (temp[i].equalsIgnoreCase(("/"))) 
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
				
				if (temp[i].equalsIgnoreCase(("%"))) 
				{
					int res;
					if (mem==0.0) 
						{
						System.out.println("Hej");
						int _d1 = (int)d1;
						int _d2 = (int)d2;
						res = modulus(_d1, _d2);
						
						
						
						}
					else 
					{
						System.out.println("Svejs");
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
			if(temp[0].equalsIgnoreCase("+")) {temp[0]=""; continue;}
			if(temp[temp.length-1].equalsIgnoreCase("+")) {temp[temp.length-1]="";}
		
		if (temp[i].equalsIgnoreCase(("+"))|| temp[i].equalsIgnoreCase(("-")))
		{
			double d1 = 0;
			if (i==0) {continue;}
			else {d1 = Double.parseDouble(temp[i-1]);}
			double d2 = Double.parseDouble(temp[i+1]);
			
			if (temp[i].equalsIgnoreCase(("+"))) 
			{
				if (mem_2==0.0 && memoryInUse==false) {
					result = add(d1, d2); mem_2=result; memoryInUse=true;
					}
				else  
					{
					result = add(mem_2, d2); mem_2=result;
					}
			}
		
			if (temp[i].equalsIgnoreCase(("-"))) 
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
		
		
		
		String out = Double.toString(result);
		System.out.println("Detta är output från mainmetod : " + out);
		return out;
	
	}
	
	private String adjustStackedOperands(String expression) {
		String plusMinusToMinus = expression.replace("+-", "-");
		String MinusPlusToMinus = plusMinusToMinus.replace("-+", "-");
		String twoMinusEqPlus = MinusPlusToMinus.replace("--", "+");
		
		if (twoMinusEqPlus.substring(0, 1).equalsIgnoreCase("-")){
			String updatedInput= "0" + twoMinusEqPlus;
			twoMinusEqPlus = updatedInput;
		}
		return twoMinusEqPlus;
	}



	private void checkInputForLetters(String[] temp) {
		try 
		{
      	  for (String i : temp) 
      	  { 
      		isChar = i.matches("[a-öA-Ö]{1}");
      		if (isChar && !i.equals("^")) {throw new RuntimeErrorException(null, "Inga bokstäver"); }
      	  }
      	} 
		
		catch (InputMismatchException e) 
		{
      	  e.printStackTrace();
      	  System.err.println("Inga bokstäver");
      	}
		
	}



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
		if (d2==0) {throw new ArithmeticException();}
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

	
	// logarithm log
	
	public double logarithm(double d1) 
	{
		return Math.log(d1);
	}
	
	//Math.log(60984.1)=11.018368453441132
	//Math.log(-497.99)=NaN
		

	

}