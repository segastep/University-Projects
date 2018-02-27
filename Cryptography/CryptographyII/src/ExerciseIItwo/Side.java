package ExerciseIItwo;

import sun.plugin2.message.Message;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Side {
	/**
	 * @author b4036790 Georgi Nikolov
	 * Container class, representing a side in DH key exchange 
	 */
	
	private BigInteger secret;
	public BigInteger generator, modulus, publicA, publicB, sharedKeyA, sharedKeyB;
	public Map<String,BigInteger> publicItems = new HashMap();
	public void setSecret(BigInteger secret){this.secret = secret;}
	public BigInteger getSecret(){return secret;}
	public Msg msg1 = new Msg();
	public Msg msg2 = new Msg();

}
