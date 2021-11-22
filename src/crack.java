import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class crack {
    public static void main(String[] args) throws IOException{
        BruteForce[] threads = new BruteForce[Runtime.getRuntime().availableProcessors()]; //instantiate array of threads equal to the users number of threads

        //reading dictionary into array
        BufferedReader in = new BufferedReader(new FileReader("./src/bigdict.txt"));
        String str;
        List<String> list = new ArrayList<>();
        while((str = in.readLine()) != null){
            list.add(str);
        }
        String[] stringArr = list.toArray(new String[0]);
        in.close();

        //getting user input
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter password");
        String password = scan.nextLine();
        String charThreadNum;
        System.out.println("You have " + threads.length + " threads available");
        while (true) {
            System.out.println("Enter number of character threads (rest will be dictionary threads)");
            charThreadNum = scan.nextLine();
            if (Integer.parseInt(charThreadNum) <= threads.length){
                break;
            } else {
                System.out.println("You don't have that many threads!");
            }
        }
        System.out.println("How many words? (if performing dictionary attack)");
        String numOfWords = scan.nextLine();

        //creating and starting our threads
        for (int i = 0; i < threads.length; i++){
            if (i < Integer.parseInt(charThreadNum)){
                threads[i] = new BruteForce(password, stringArr, false, 0, Integer.parseInt(numOfWords));
            } else {
                threads[i] = new BruteForce(password, stringArr, true, i * (stringArr.length / threads.length), Integer.parseInt(numOfWords));
            }
            threads[i].start();
        }
        System.out.println("Running " + threads.length + " threads");
    }
}
