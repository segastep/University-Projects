package ExerciseIIone;

import java.math.BigInteger;

public class Utils {
	
	/**
	 * @author b4036790 Georgi Nikolov
	 * Utils providing necessary methods to solve linear equations
	 * Constant given from CW specification 
	 */
	
	// A value from question 2A
	public static final BigInteger A_Q2A = new BigInteger("34325464564574564564768795534569998743457687643234566579654234676796634378768" +
														  "434237897634345765879087764242354365767869780876543424");
	// B value from question 2A
	public static final BigInteger B_Q2A = new BigInteger("45292384209127917243621242398573220935835723464332452353464376432246757234546"+
														  "765745246457656354765878442547568543334677652352657235");
	// N value from question 2A
	public static final BigInteger N_Q2A = new BigInteger("64380800680355443923012985496149269915138610753401343291807343952413826484237"+
														  "06300613697153947391340909229373325903847203971333359695492563226209790366866332"
														  +"13903952966175107096769180017646161851573147596390153");
	// A value from question 2B 
	public static final BigInteger A_Q2B = new BigInteger("34325464564574564564768795534569998743457687643234566579654234676796634378768"
														  +"434237897634345765879087764242354365767869780876543424");
	
	// B value from question 2B
	public static final BigInteger B_Q2B = new BigInteger("24243252873562935279236582385723952735639239823957923562835832582635283562852"
														  +"252525256882909285959238420940257295265329820035324646");
	
	// N value from question 2B
	public static final BigInteger N_Q2B = new BigInteger("34248723532593458235023583785345602939423526832829428589598243238758257023423"+
														  "84876259232895263823795235732659632932938392985950720935732042930927056234852738"+
														  "93582930285732889238492377364284728834632342522323422");



	/**
	 * Check if a number is probable prime, used to help out with Question3
	 * @param n - number to check
	 * @return - true if relative prime, false if definitely composite number
	 */
	public boolean checkIfprobablePrime(BigInteger n)
	{
		return n.isProbablePrime(1);
	}


	// To find X we need to solve the following equation x = -b*a^-1 in Zn
	
	// Equation is solvable only if we can calculate the modular inverse, thus A and N should be co prime
	private boolean isCoprime(BigInteger a, BigInteger n)
	{
		return a.gcd(n).equals(BigInteger.ONE);
	}
	
	public BigInteger solveEquation(BigInteger a, BigInteger b, BigInteger n)
	{
		if(a.equals(BigInteger.ZERO) && b.equals(BigInteger.ZERO))
		{
			System.out.println("Every X in range N-1 is a solution");
			return null;
		}
		return isCoprime(a,n) ? b.negate().multiply(a.modInverse(n)).mod(n)  : null;
	}
	
	// Some test methods, left them here on purpose, since the code is really basic
	private BigInteger bInt(int a)
	{
		return new BigInteger(String.valueOf(a));
	}
	
	public BigInteger testShouldBeNull()
	{	
		return solveEquation(bInt(24), bInt(32), bInt(64));
	}
	
	public String testSolveEquation()
	
	{	BigInteger result = solveEquation(bInt(5), bInt(4), bInt(7));
		return result.equals(new BigInteger("2")) ? "Solve equation method returned expected value 2" : "Missmatch between actual and expected result,"
				+ "expected result was : 2 " + "but actual result is " + String.valueOf(result);
	}

	
}
