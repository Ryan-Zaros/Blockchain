import java.security.*;
import java.util.ArrayList;

public class Transaction {
    public PublicKey sender;
    public PublicKey receiver;
    public String TransactionId;
    public float value;
    public byte[] signature;
    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int numTransactions = 0;

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.receiver = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash() {
        numTransactions++;
        return StringUtils.applySha256(StringUtils.getStringFromKey(sender) 
                                        + StringUtils.getStringFromKey(receiver)
                                        + numTransactions
                                        + Float.toString(value));
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(receiver) + Float.toString(value)	;
        signature = StringUtils.applyECDSASig(privateKey, data);		
    }
    //Verifies the data we signed hasnt been tampered with
    public boolean verifySignature() {
        String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(receiver) + Float.toString(value)	;
        return StringUtils.verifyECDSASig(sender, data, signature);
    }
}
