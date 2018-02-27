package ExerciseIItwo;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.zip.CheckedInputStream;

public class DHutils {
	/**
	 * @author b4036790 Georgi Nikolov
	 */

	private final BigInteger CONST_TWO = new BigInteger("2");
	
	private final static int M_LENGTH = 1024;

	private BigInteger p;
	private BigInteger g;


	public DHutils()
    {
        this.p = generateModulus();
        this.g = chooseGenerator();

    }
	public BigInteger getP() {return this.p;}
	public BigInteger getG(){return this.g;}
	public void setP(BigInteger p) {this.p = p;}
	
	//Generate G and P
	// A->B msg1 mod base valA
	// B->A 
	// PK for A and B

	
	/**
	 * Same method as below but to represent sharedKey generation, only for the sake of readability
	 */
	public BigInteger genSharedKey(BigInteger publicKey, BigInteger secret, BigInteger modulus)
	{
		return genPubKey(publicKey, secret, modulus);
	}
	
	/**
	 * 
	 * @param generator 
	 * @param secret
	 * @param modulus
	 * @return Public key from given inputs
	 */
	public BigInteger genPubKey(BigInteger generator, BigInteger secret, BigInteger modulus)
	{

		return generator.modPow(secret, modulus);
	}
	
	/**
	 * 
	 * @return generate a random secret < than modulus
	 */
	public BigInteger generateSecret()
	{
		while(true)
		{
			BigInteger sec = new BigInteger(M_LENGTH, new SecureRandom());
			if(sec.equals(CONST_TWO) && sec.compareTo(this.p.subtract(CONST_TWO)) <= 0) continue;
			return sec;
		}
	}
	
	public static BigInteger generateModulus()
	{
		return BigInteger.probablePrime(M_LENGTH, new SecureRandom());
	}


    private BigInteger auxGen()
    {
	    return new BigInteger(this.p.bitLength(), new SecureRandom());
    }

    /**
     *
     * @return BigInteger in range 2 p-1
     */
	private BigInteger chooseGenerator()
    {
        BigInteger x = auxGen();
        if (x.compareTo(this.p) == -1 && x.compareTo(CONST_TWO) > 0)
        {
            return x;
        }

        return chooseGenerator();
    }

}
