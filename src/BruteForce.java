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

    /**
     *
     * @param password Password to be cracked
     * @param dictionary String array of words to combine
     * @param isDictionary Boolean flag for if this thread should be performing character or dictionary attacks
     * @param startPoint Start point for this thread in the dictionary
     * @param numOfWords Number of words in the password if performing a dictionary attack
     */
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
            switch (numOfWords) {
                case 2 -> combinationDictionaryAttack2();
                case 3 -> combinationDictionaryAttack3();
                default -> combinationDictionaryAttack1();
            }
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
     * Sets thread up to logically sift through its portion of the dictionary checking all possible words in parallel
     */
    private void combinationDictionaryAttack1(){
        String test;
        long start_time = System.nanoTime();
        while(true) {
            for (int i = startPoint; i < dictionary.length; i++){
                test = dictionary[i];
                if (test.equals(password)){
                    long end_time = System.nanoTime();
                    double difference = (end_time - start_time) / 1e6;
                    System.out.println("Found it in " + difference + "ms");
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Sets thread up to logically sift through its portion of the dictionary checking all possible combinations of 2 words in parallel
     */
    private void combinationDictionaryAttack2(){
        String test;
        long start_time = System.nanoTime();
        while(true) {
            for (int i = startPoint; i < dictionary.length; i++){
                for (int j = 0; j < dictionary.length; j++){
                    test = dictionary[i];
                    test += dictionary[j];
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

    /**
     * Sets thread up to logically sift through its portion of the dictionary checking all possible combinations of 3 words in parallel
     */
    private void combinationDictionaryAttack3(){
        String test;
        long start_time = System.nanoTime();
        while(true) {
            for (int i = startPoint; i < dictionary.length; i++){
                for (int j = 0; j < dictionary.length; j++){
                    for (int k = 0; k < dictionary.length; k++){
                        test = dictionary[i];
                        test += dictionary[j];
                        test += dictionary[k];
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
}
