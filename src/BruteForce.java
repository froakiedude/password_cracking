import java.util.Random;

/**
 * Provides methods to brute force password combinations on a per-thread basis
 */
public class BruteForce extends Thread{
    private final String password;
    private final String[] dictionary;
    private final boolean isDictionary;
    private final int startPoint;
    private final int numOfWords;

    public BruteForce(String password, String[] dictionary, boolean isDictionary, int startPoint, int numOfWords){
        this.password = password;
        this.dictionary = dictionary;
        this.isDictionary = isDictionary;
        this.startPoint = startPoint;
        this.numOfWords = numOfWords;
    }

    public void run(){
        if (!isDictionary){
            randomCharacterAttack();
        } else {
            combinationDictionaryAttack();
        }
    }

    /**
     * Combines random characters together until a match is found
     */
    private void randomCharacterAttack(){
        Random rand = new Random();
        String currentChar;
        StringBuilder test;
        long start_time = System.nanoTime();
        while(true) {
            test = new StringBuilder();
            for (int i = 0; i < password.length(); i++){
                currentChar = Character.toString(rand.nextInt(26) + 97);
                test.append(currentChar);
            }
            if (test.toString().equals(password)){
                long end_time = System.nanoTime();
                double difference = (end_time - start_time) / 1e6;
                System.out.println("Found it in " + difference + "ms");
                System.exit(0);
            }
        }
    }

    /**
     * Combines random words and checks them until match is found
     */
    private void randomDictionaryAttack(){
        Random rand = new Random();
        String currentWord;
        String test;
        long start_time = System.nanoTime();
        while(true) {
            test = "";
            for (int i = 0; i < 2; i++){
                currentWord = dictionary[rand.nextInt(dictionary.length)];
                test += currentWord;
            }
            if (test.equals(password)){
                long end_time = System.nanoTime();
                double difference = (end_time - start_time) / 1e6;
                System.out.println("Found it in " + difference + "ms");
                System.exit(0);
            }
        }
    }

    /**
     * Sets thread up to logically sift through its portion of the dictionary checking all possible combinations in parallel
     */
    private void combinationDictionaryAttack(){
        String test;
        long start_time = System.nanoTime();
        while(true) {
            for (int i = startPoint; i < dictionary.length; i++){
                for (int j = 0; j < dictionary.length; j++){
                    test = dictionary[i];
                    for (int k = 0; k < numOfWords - 1; k++) {
                        test += dictionary[j];
                    }
                    if (test.equals(password)){
                        long end_time = System.nanoTime();
                        double difference = (end_time - start_time) / 1e6;
                        System.out.println("Found it in " + difference + "ms");
                        System.exit(0);
                    }
                }
            }
        }
    }

}
