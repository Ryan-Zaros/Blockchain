import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Blockchain {
    
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;

    public static void main(String[] args) {

        blockchain.add(new Block("first block", "0"));
        System.out.println("Attempting to mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("second block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Attempting to mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("third block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Attempting to mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nIs blockchain valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nBlockchain:");
        System.out.println(blockchainJson);		
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
