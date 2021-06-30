import java.util.ArrayList;
import com.google.gson.GsonBuilder;
import java.util.Base64;
import java.security.*;


public class Blockchain {
    
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static Wallet walletA;
	public static Wallet walletB;

    public static void main(String[] args) {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		//Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringUtils.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtils.getStringFromKey(walletA.publicKey));
		//Create a test transaction from WalletA to walletB 
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifySignature());	
    }

    public static Boolean isChainValid() {
        Block currBlock;
        Block prevBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            currBlock = blockchain.get(i);
            prevBlock = blockchain.get(i-1);

            if (!currBlock.hash.equals(currBlock.calculateHash())) {
                System.out.println("Current block invalid.");
                return false;
            }
            if (!prevBlock.hash.equals(prevBlock.calculateHash())) {
                System.out.println("Current block invalid.");
                return false;
            }
            if(!currBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined.");
				return false;
			}
        }
        return true; 
    }

}
