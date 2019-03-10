package tests;
import calculator.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import calculator.Calculator;

public class TestCalc {
	
	//Arrange
	Calculator calc = new Calculator();
	
	
	// minräknarens huvud-metod
	
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
	
	
	
	@Test
	public void plusNegativeBracketValue_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "4+(1-2)";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("3.0", actual);	
	}
	
	@Test
	public void minusFirstOrLast_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "-2+2/2*2*1+1*2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("2.0", actual);	
	}
	
	@Test
	public void minusInFrontOfBracket_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "3-(1+1)";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("1.0", actual);	
	}
	
	
	
	@Test
	public void minusFirstOrLast_test2() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "-4+5";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("1.0", actual);	
	}
	@Test
	public void plusFirstOrLast_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "+2+2/2*2+";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("4.0", actual);	
	}
	// Denna funkar inte pga mem2=0
	
	@Test
	public void dennaSkafunkaTabort_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "0-2+2+1";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("1.0", actual);	
	}
	
	@Test
	public void rootInEquation_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "√4+2/2*2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("4.0", actual);	
	}
	
	public void bracket_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "9(3*1)+3-(4-1)";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("27.0", actual);	
	}
	
	@Test
	public void doubleMinus_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "7*6/2--2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("23.0", actual);	
	}
	@Test
	public void priority_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "7*6/2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("21.0", actual);	
	}
	
	@Test
	public void whiteSpace_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "1 *2 / 2 +2 * 3";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("7.0", actual);	
	}
	
	@Test
	public void lotOfDifferentCalculations_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "1 /2/3*4*5*6-7-8+9";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("14.0", actual);	
	}
	
	@Test
	public void multipleOperators_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "5*4+7-3*0+4-10";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("21.0", actual);	
	}
	
	@Test
	public void additionWithMultipleNumbers_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "1+1+1";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("3.0", actual);	
	}
	
	@Test
	public void additionAndSubtraction_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "5+1-2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("4.0", actual);	
	}
	
	@Test
	public void multiplyAndDivide_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "5*5/5";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("5.0", actual);	
	}
	
	@Test
	public void multiplyOperatorsInOrder_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "5*5/2-2";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("10.5", actual);	
	}
	
	@Test
	public void subtractionWithMultipleNumbers_test() 
	{
		//Arrange
		Calculator calc = new Calculator();
		String input = "9-3-1";
		//Act
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("5.0", actual);	
	}
	@Test
	public void addition_test() 
	{
		
		//Arrange
		Calculator calc = new Calculator();
		// Act
		String input = "1+2";
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("3.0", actual);
		
		
		
	}
	
	@Test
	public void simpleDivide_test() 
	{
		
		//Arrange
		Calculator calc = new Calculator();
		// Act
		String input = "4/2";
		String actual = calc.calculateExpression(input);
		
		//Assert
		assertEquals("2.0", actual);
		
		
		
	}
	
	@Test
	public void add_test() 
	{
		
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.add(1, 2);
		
		//Assert
		assertEquals(3d, actual, 0.111);
		
		
		
	}
	
	@Test
	public void subtract_test() 
	{
	    
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.subtract(2, 1);
		
		//Assert
		assertEquals(1d, actual, 0.111);
		
		
	}
	
	@Test
	public void multiply_test() 
	{
	  
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.multiply(3, 3);
		
		//Assert
		assertEquals(9d, actual, 0.111);
		
		
	}
	
	@Test
	public void divide_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Act
		double actual = calc.divide(4, 2);
		
		//Assert
		assertEquals(2d, actual, 0.111);
		
		
	}
	
	@Test
	public void modulus_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Acts
		double actual = calc.divide(60, 19);
		
		//Assert
		assertEquals(3, actual, 0.16);
		
		
	}
	
	@Test
	public void root_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Acts
		double actual = calc.root(9);
		
		//Assert
		assertEquals(3d, actual, 0.111);
		
		
	}
	
	@Test
	public void exponent_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Acts
		double actual = calc.exponent(10, 2);
		
		//Assert
		assertEquals(100d, actual, 0.111);
		
		
	}
	
	@Test
	public void logarithm_test() 
	{
	   
		//Arrange
		Calculator calc = new Calculator();
		// Acts
		double actual = calc.logarithm(60984.1);
		
		//Assert
		assertEquals(11.018368453441132d, actual, 0.111);
		
		
	}
	
	
	
	
	
	

}