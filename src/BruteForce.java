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
    private final String[] characters = new String[95];

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
        for (int i = 32; i <= 126; i++){
            characters[i - 32] = Character.toString(i);
        }
    }

    public void run(){
        if (!isDictionary){
            switch (password.length()) {
                case 2 -> combinationCharacterAttack2();
                case 3 -> combinationCharacterAttack3();
                case 4 -> combinationCharacterAttack4();
                case 5 -> combinationCharacterAttack5();
                case 6 -> combinationCharacterAttack6();
                case 7 -> combinationCharacterAttack7();
                case 8 -> combinationCharacterAttack8();
                case 9 -> combinationCharacterAttack9();
                case 10 -> combinationCharacterAttack10();
                default -> combinationCharacterAttack1();
            }
        } else {
            switch (numOfWords) {
                case 2 -> combinationDictionaryAttack2();
                case 3 -> combinationDictionaryAttack3();
                case 4 -> combinationDictionaryAttack4();
                case 69 -> crackTwoWordsPlus2Numbers();
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
     * @deprecated This method is just not as good as the systematic dictionary attack on average and also doesn't leverage multithreading very well as all threads are just fumbling in the dark rather than working together
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

    /**
     * Sets thread up to logically sift through its portion of the dictionary checking all possible combinations of 2 words in parallel
     */
    private void combinationDictionaryAttack2(){
        String test;
        long start_time = System.nanoTime();
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

    /**
     * Sets thread up to logically sift through its portion of the dictionary checking all possible combinations of 3 words in parallel
     */
    private void combinationDictionaryAttack3(){
        String test;
        long start_time = System.nanoTime();
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

    private void combinationDictionaryAttack4(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < dictionary.length; i++){
            for (int j = 0; j < dictionary.length; j++){
                for (int k = 0; k < dictionary.length; k++){
                    for (int l = 0; l < dictionary.length; l++){
                        test = dictionary[i];
                        test += dictionary[j];
                        test += dictionary[k];
                        test += dictionary[l];
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

    private void combinationCharacterAttack1(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            test = characters[i];
            if (test.equals(password)){
                long end_time = System.nanoTime();
                double difference = (end_time - start_time) / 1e6;
                System.out.println("Found it in " + difference + "ms");
                System.exit(0);
            }
        }
    }

    private void combinationCharacterAttack2(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                test = characters[i];
                test += characters[j];
                if (test.equals(password)){
                    long end_time = System.nanoTime();
                    double difference = (end_time - start_time) / 1e6;
                    System.out.println("Found it in " + difference + "ms");
                    System.exit(0);
                }
            }
        }
    }

    private void combinationCharacterAttack3(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                for (int k = 0; k < characters.length; k++){
                    test = characters[i];
                    test += characters[j];
                    test += characters[k];
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

    private void combinationCharacterAttack4(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                for (int k = 0; k < characters.length; k++){
                    for (int l = 0; l < characters.length; l++){
                        test = characters[i];
                        test += characters[j];
                        test += characters[k];
                        test += characters[l];
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

    private void combinationCharacterAttack5(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                for (int k = 0; k < characters.length; k++){
                    for (int l = 0; l < characters.length; l++){
                        for (int m = 0; m < characters.length; m++){
                            test = characters[i];
                            test += characters[j];
                            test += characters[k];
                            test += characters[l];
                            test += characters[m];
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

    private void combinationCharacterAttack6(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                for (int k = 0; k < characters.length; k++){
                    for (int l = 0; l < characters.length; l++){
                        for (int m = 0; m < characters.length; m++){
                            for (int n = 0; n < characters.length; n++){
                                test = characters[i];
                                test += characters[j];
                                test += characters[k];
                                test += characters[l];
                                test += characters[m];
                                test += characters[n];
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
    }

    private void combinationCharacterAttack7(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                for (int k = 0; k < characters.length; k++){
                    for (int l = 0; l < characters.length; l++){
                        for (int m = 0; m < characters.length; m++){
                            for (int n = 0; n < characters.length; n++){
                                for (int o = 0; o < characters.length; o++){
                                    test = characters[i];
                                    test += characters[j];
                                    test += characters[k];
                                    test += characters[l];
                                    test += characters[m];
                                    test += characters[n];
                                    test += characters[o];
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
        }
    }

    private void combinationCharacterAttack8(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                for (int k = 0; k < characters.length; k++){
                    for (int l = 0; l < characters.length; l++){
                        for (int m = 0; m < characters.length; m++){
                            for (int n = 0; n < characters.length; n++){
                                for (int o = 0; o < characters.length; o++){
                                    for (int p = 0; p < characters.length; p++){
                                        test = characters[i];
                                        test += characters[j];
                                        test += characters[k];
                                        test += characters[l];
                                        test += characters[m];
                                        test += characters[n];
                                        test += characters[o];
                                        test += characters[p];
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
            }
        }
    }

    private void combinationCharacterAttack9(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                for (int k = 0; k < characters.length; k++){
                    for (int l = 0; l < characters.length; l++){
                        for (int m = 0; m < characters.length; m++){
                            for (int n = 0; n < characters.length; n++){
                                for (int o = 0; o < characters.length; o++){
                                    for (int p = 0; p < characters.length; p++){
                                        for (int q = 0; q < characters.length; q++){
                                            test = characters[i];
                                            test += characters[j];
                                            test += characters[k];
                                            test += characters[l];
                                            test += characters[m];
                                            test += characters[n];
                                            test += characters[o];
                                            test += characters[p];
                                            test += characters[q];
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
                }
            }
        }
    }

    private void combinationCharacterAttack10(){
        String test;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                for (int k = 0; k < characters.length; k++){
                    for (int l = 0; l < characters.length; l++){
                        for (int m = 0; m < characters.length; m++){
                            for (int n = 0; n < characters.length; n++){
                                for (int o = 0; o < characters.length; o++){
                                    for (int p = 0; p < characters.length; p++){
                                        for (int q = 0; q < characters.length; q++){
                                            for (int r = 0; r < characters.length; r++){
                                                test = characters[i];
                                                test += characters[j];
                                                test += characters[k];
                                                test += characters[l];
                                                test += characters[m];
                                                test += characters[n];
                                                test += characters[o];
                                                test += characters[p];
                                                test += characters[q];
                                                test += characters[r];
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
                    }
                }
            }
        }
    }

    /**
     * Attempts to crack common pattern of two words + 2 numbers for passwords systematically
     */
    private void crackTwoWordsPlus2Numbers(){
        String test;
        String partOne;
        long start_time = System.nanoTime();
        for (int i = startPoint; i < characters.length; i++){
            for (int j = 0; j < characters.length; j++){
                test = characters[i];
                test += characters[j];
                partOne = test;
                for (int k = startPoint; k < characters.length; k++){
                    for (int l = 0; l < characters.length; l++){
                        test = partOne;
                        test += characters[k];
                        test += characters[l];
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
