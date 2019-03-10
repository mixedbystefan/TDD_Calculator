package tests;



import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import calculator.Calculator;

public class TestExceptions 
{
	
	//Arrange
		Calculator calc = new Calculator();
		
	// Kollar error vid delat med 0
	@Test(expected = ArithmeticException.class)
	public void dividedByZero_ExceptionThrown() 
	{
		//Arrange 
		Calculator calculator = new Calculator();
		//Act
		double actual = calculator.divide(5, 0);
		
		
	}
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void throwsNothing() {
        // no exception expected, none thrown: passes.
    }

    @Test
    public void throwsExceptionWithSpecificType() {
        thrown.expect(NullPointerException.class);
        calc.calculateExpression("(()");
    }
    
    @Test
    public void throwsExceptionWhoseMessageContainsSpecificText() {
        thrown.expectMessage("Måste finnas öppnande och stängande parentes");
        String actual = calc.calculateExpression("+5-(");
        
    }
    
    // Kollar om ett err-mess innehållande "Inga bokstäver" kastas
    
    @Test
    public void throwsExceptionForUsingLetters() {
        thrown.expectMessage("Inga bokstäver");
        String actual = calc.calculateExpression("4+A");
        
    }

}
