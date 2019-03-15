package tests;



import static org.junit.Assert.assertEquals;

import java.util.InputMismatchException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import calculator.Calculator;

public class TestExceptions 
{
	
	//Arrange
		Calculator calc = new Calculator();
		
	// Kollar error vid division med 0
	@Test(expected = ArithmeticException.class)
	public void dividedByZero_ExceptionThrown() 
	{
		//Arrange 
		Calculator calculator = new Calculator();
		//Act
		double actual = calculator.divide(5, 0);
		
		
	}
	
	// Testar Gränsvärde 
	// Undantag ska kastas när resultatet en metod övertsiger det värde som 
	// kan lagras i en double
		
		@Test(expected = InputMismatchException.class)
		public void _doubleMax_ExceptionThrown() 
		{
			//Arrange 
			Calculator calculator = new Calculator();
			//Act
			double actual = calculator.add(Double.MAX_VALUE, 1);
			
			
		}
	
	// Undantag ska kastas om första tecken är * eller /
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void notALlowedSubstringZero_ExceptionThrown() 
	{
		//Arrange 
		Calculator calculator = new Calculator();
		//Act
		 String actual = calc.calculateExpression("*5-1");
		
		
	}
	
	// Undantag ska kastas om * eller / används flera gånger i rad
		@Test(expected = NumberFormatException.class)
		public void multipleOperandsInRow_ExceptionThrown() 
		{
			//Arrange 
			Calculator calculator = new Calculator();
			//Act
			 String actual = calc.calculateExpression("5**5");
			
			
		}
	
	
	
	
	// Undantag ska kastas om antalet paranteser är ojämt
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();

    
    @Test
    public void throwsExceptionWhenBracketIsMissing() {
        thrown.expectMessage("Måste finnas öppnande och stängande parentes");
        String actual = calc.calculateExpression("+5-(");
        
    }
    

    // Undantag ska kastas om användaren försöker annvända parenteser inom parenteser
    @Test
    public void throwsExceptionForUnimplementedBracketUse() {
        thrown.expectMessage("Parantes inom Parentes är inte implementerad i miniräknaren");
        String actual = calc.calculateExpression("(1(1*2))");
        
    }
    
    // Undantag ska kastats
    
    @Test
    public void throwsExceptionForUsingLetters() {
        thrown.expectMessage("Inga bokstäver");
        String actual = calc.calculateExpression("4+A");
        
    }

}
