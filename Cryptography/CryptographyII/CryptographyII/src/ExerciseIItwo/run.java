package ExerciseIItwo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class run {
	
	static String s = File.separator;
	static String fName = "transactions".concat(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss").format(LocalDateTime.now()).toString())
								 .concat(".txt");
	static String fName1 = "attack".concat(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss").format(LocalDateTime.now()).toString())
			.concat(".txt");

	static String filePath = Paths.get("").toAbsolutePath().toString().concat(s).concat("src").concat(s).concat("ExerciseIItwo")
					  .concat(File.separator).concat(fName);

	static String filePath1 = Paths.get("").toAbsolutePath().toString().concat(s).concat("src").concat(s).concat("ExerciseIItwo")
			.concat(File.separator).concat(fName1);
	
	static void write(String content) throws IOException
	{
		Files.write(Paths.get(filePath), (content +System.lineSeparator()).getBytes(Charset.forName("UTF-8"))
					,StandardOpenOption.CREATE,StandardOpenOption.APPEND);
	}

	static void attackWrite(String content) throws IOException
	{
		Files.write(Paths.get(filePath1), (content +System.lineSeparator()).getBytes(Charset.forName("UTF-8"))
				,StandardOpenOption.CREATE,StandardOpenOption.APPEND);
	}

	public static void keyExchange(Side one, Side two, DHutils dh_utils_exchange) throws IOException
	{

		//Do computations in a
		one.generator = dh_utils_exchange.getG();
		one.modulus = dh_utils_exchange.getP();
		one.publicA = dh_utils_exchange.genPubKey(one.generator, one.getSecret(), dh_utils_exchange.getP());
		one.msg1.setGenerator(one.generator).setModulus(one.modulus).setPubKeyA(one.publicA);

		//Send msg1 from Alice\Eve to Bob
		two.msg1 = one.msg1;
		//System.out.println(two.msg1.toString());

		//Export msg1 values
		two.modulus = two.msg1.getModulus();
		two.generator = two.msg1.getGenerator();
		two.publicB = dh_utils_exchange.genPubKey(dh_utils_exchange.getG(), two.getSecret(), dh_utils_exchange.getP());
		two.msg2.setPubKeyB(two.publicB);

		//send msg2 from B to A\eve
		one.msg2 = two.msg2;

		//compute shared key for A\eve
		one.sharedKeyA = dh_utils_exchange.genSharedKey(one.msg2.getPubKeyB(), one.getSecret(), one.modulus);
		two.sharedKeyB = dh_utils_exchange.genSharedKey(two.msg1.getPubKeyA(), two.getSecret(), two.modulus);

		if (!(one.generator.equals(two.generator) && one.modulus.equals(two.modulus)))
		{
			write("Missmatch between modulus or generator");
			throw new SecurityException("Your code is terribly broken");
		}



	}

	public static void keyExchangeAttack(Side a, Side b, Side eve, DHutils dh) throws IOException
	{
		keyExchange(a,eve,dh);
		attackWrite("Communication between A and Eve");
		attackWrite("SecretA=" + a.getSecret().toString());
		attackWrite("SecretEve=" + eve.getSecret().toString());
		attackWrite("msg1.modulus=" + a.msg1.getModulus());
		attackWrite("msg1.base=" + a.msg1.getGenerator());
		attackWrite("msg1.valueA=" + a.msg1.getPubKeyA());
		attackWrite("KeyA: " + a.sharedKeyA);
		attackWrite("KeyB:" + eve.sharedKeyB);
		if (a.sharedKeyA.compareTo(eve.sharedKeyB) == 0)
			attackWrite("Shared Keys for A and Eve are matching");
		else
			attackWrite("A and Eve generated different shared keys");
		attackWrite("End of communication between A and Eve");
		attackWrite("    ");
		attackWrite("    ");

		keyExchange(eve,b,dh);
		attackWrite("Communication between B and Eve");
		attackWrite("SecretB=" + b.getSecret().toString());
		attackWrite("SecretEve=" + eve.getSecret().toString());
		attackWrite("msg1.modulus=" + eve.msg1.getModulus());
		attackWrite("msg1.base=" + eve.msg1.getGenerator());
		attackWrite("msg1.valueEve=" + eve.msg1.getPubKeyA());
		attackWrite("KeyB: " + b.sharedKeyB);
		attackWrite("KeyEve:" + eve.sharedKeyA);
		if (b.sharedKeyB.compareTo(eve.sharedKeyA) == 0)
			attackWrite("Shared Keys for B and Eve are matching");
		else
			attackWrite("B and Eve generated different shared keys");
		attackWrite("End of communication between B and Eve");


	}
	
	public static void main(String[] args) throws IOException
	
	{
		Files.createFile(Paths.get(filePath));
		Side sideA = new Side();
		Side sideB = new Side();
		Side sideEve = new Side();
		DHutils dh = new DHutils();
		//Generate secrets and write them to a file
		sideA.setSecret(dh.generateSecret());
		sideB.setSecret(dh.generateSecret());
		sideEve.setSecret(dh.generateSecret());
		keyExchange(sideA, sideB, dh);
		write("SecretA=" + sideA.getSecret().toString());
		write("SecretB=" + sideB.getSecret().toString());
		write("msg1.modulus=" + sideA.msg1.getModulus());
		write("msg1.base=" + sideA.msg1.getGenerator());
		write("msg1.valueA" + sideA.msg1.getPubKeyA());
		write("KeyA: " + sideA.sharedKeyA);
		write("KeyB:" + sideB.sharedKeyB);
		if (sideA.sharedKeyA.compareTo(sideB.sharedKeyB) == 0)
			write("Shared Keys for A and B are matching");
		else
			write("A and B generated different shared keys");
		Files.createFile(Paths.get(filePath1));
		Side attackSideA = new Side();
		Side attackSideB = new Side();
		Side attackSideEve = new Side();
		DHutils attackDh = new DHutils();
		// Generate secrets
        attackSideA.setSecret(dh.generateSecret());
        attackSideB.setSecret(dh.generateSecret());
        attackSideEve.setSecret(dh.generateSecret());
		keyExchangeAttack(attackSideA, attackSideB, attackSideEve, attackDh);

	}

}
