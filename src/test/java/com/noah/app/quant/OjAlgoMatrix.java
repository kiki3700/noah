package com.noah.app.quant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ojalgo.matrix.Primitive64Matrix;
import org.ojalgo.random.Weibull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OjAlgoMatrix {
	
	
	@Test
	public void test() {
		 Primitive64Matrix.Factory matrixFactory = Primitive64Matrix.FACTORY;
		  Primitive64Matrix matrixA = matrixFactory.makeEye(5, 5);
		  
		  System.out.println(matrixA.toString());
		  //Internally this creates an "eye-structure" - not a large array.
		  Primitive64Matrix matrixB = matrixFactory.makeFilled(5, 3,new Weibull(5.0, 2.0));
	        // When you create a matrix with random elements you can specify
	        // their distribution.
		  Primitive64Matrix matrixC = matrixA.multiply(matrixB);
	        // Multiplying two PrimitiveMatrix:s is trivial. There are no
	        // alternatives, and the returned product is a PrimitiveMatrix
	        // (same as the inputs).
		  
	        /* Inverting matrices */

	        matrixA.invert();
	        // If you want to invert a PrimitiveMatrix then just do it...
	        // The matrix doesn't even have to be square or anything.
	        // You'll get a pseudoinverse or whatever is possible.
	        
	        /* Solving equation system */

	        matrixA.solve(matrixC);
	        // PrimitiveMatrix "is" the equation system body.
	        // You just supply the RHS, and you get the solution.
	        // If necessary it will be a least squares solution, or something
	        // derived from the pseudoinverse.
	        

	}
	@Test
	public void toCovMatrix() {
	}
	
}
