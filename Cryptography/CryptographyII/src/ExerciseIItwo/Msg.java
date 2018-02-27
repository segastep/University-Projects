package ExerciseIItwo;

import java.lang.reflect.Field;
import java.math.BigInteger;

public class Msg {

    public BigInteger getGenerator() {
        return generator;
    }

    public Msg setGenerator(BigInteger generator) {
        this.generator = generator;
        return this;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public Msg setModulus(BigInteger modulus) {
        this.modulus = modulus;
        return this;
    }

    public BigInteger getPubKeyA() {
        return pubKeyA;
    }

    public Msg setPubKeyA(BigInteger pubKeyA) {
        this.pubKeyA = pubKeyA;
        return this;
    }

    public BigInteger getPubKeyB() {
        return pubKeyB;
    }

    public Msg setPubKeyB(BigInteger pubKeyB) {
        this.pubKeyB = pubKeyB;
        return this;
    }

    public BigInteger getPublicKeyEve() {
        return publicKeyEve;
    }

    public Msg setPublicKeyEve(BigInteger publicKeyEve) {
        this.publicKeyEve = publicKeyEve;
        return this;
    }

    private BigInteger generator, modulus, pubKeyA,pubKeyB, publicKeyEve;



    public String toString()
    {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }


}
