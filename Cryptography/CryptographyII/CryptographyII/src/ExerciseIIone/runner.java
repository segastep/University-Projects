package ExerciseIIone;


public class runner {
	
	
	
	public static void main(String[] args){
		Utils util = new Utils();
		
		// Test to see if null value is handled properly
		System.out.println("Next line should print null");
		System.out.println(String.valueOf(util.testShouldBeNull()));
		
		// Test to see if equation solver works correctly
		System.out.println(util.testSolveEquation());

		//Check if N is relative prime
		// Uses Rabin-Miller algorithm to perform check so, should be trustworthy.
		System.out.println("Is N_Q2A probable prime ? " + util.checkIfprobablePrime(Utils.N_Q2A));
		System.out.println("Is N_Q2B probable prime ? " + util.checkIfprobablePrime(Utils.N_Q2B));


		// Solve first equation for question 2A
		System.out.printf("\nTrying to solve equation 2A \nInput values: \n A=%s\n B=%s\n N=%s\n X=%s\n", 
						  Utils.A_Q2A, Utils.B_Q2A, Utils.N_Q2A, util.solveEquation(Utils.A_Q2A, Utils.B_Q2A, Utils.N_Q2A));
		
		
		// Solve first equation for question 2b
				System.out.printf("\nTrying to solve equation 2B \nInput values: \n A=%s\n B=%s\n N=%s\n X=%s\n", 
								  Utils.A_Q2B, Utils.B_Q2B, Utils.N_Q2B, util.solveEquation(Utils.A_Q2B, Utils.B_Q2B, Utils.N_Q2B));
		
		
		
	}
	

}
