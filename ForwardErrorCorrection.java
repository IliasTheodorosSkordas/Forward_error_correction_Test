
import java.util.Random;

public class ForwardErrorCorrection {

    public static void main(String[] args) {
        //προσομοίωση πηγής
        Random rand = new Random();
        int source[] = new int[2000];//γεμίζω ένα πίνακα με 2000 τυχαία binary bits
        for (int i = 0; i < 2000; i++) {
            source[i] = rand.nextInt(2);
        }
        //κωδικοποίηση με Hamming(7,4)
        int coded[] = new int[3500];
        int i = 0;
        while (i < 2000) {//για τις 500 τετράδες της πηγής
            for (int j = 0; j < 3500; j = j + 7) {//για τις 500 κωδικοποιημένες επτάδες 
                coded[j] = source[i] ^ source[i + 1] ^ source[i + 3]; // υπολογίζω τις τιμές τους
                coded[j + 1] = source[i] ^ source[i + 2] ^ source[i + 3];
                coded[j + 2] = source[i];// ^ bitwise XOR
                coded[j + 3] = source[i + 1] ^ source[i + 2] ^ source[i + 3];
                coded[j + 4] = source[i + 1];
                coded[j + 5] = source[i + 2];
                coded[j + 6] = source[i + 3];

            }
            i = i + 4;
        }
        //μετάδοση
        int errors=0;
        int transmitted[] = new int[3500];
        for (i = 0; i < 3500; i++) {//για όλη την πληροφορία που θέλω να μεταδόσω
            double d = Math.random();
            if (d <= 0.5) {// στο 10% των περιπτώσεων
                transmitted[i] = coded[i] ^ 1; // το bit αλλοιώνεται
                errors++;
            }

        }

        //αποκωδικοποίηση/ανίχνευση-διόρθωση
        int errors_found=0;
        int loops=1;
        for (int j = 0; j < 3500; j = j + 7) {
           int point_of_error=(int)(transmitted[j]*Math.pow(2,0)+transmitted[j+1]*Math.pow(2,1)+transmitted[j+3]*Math.pow(2,2));//syndrome
           if (point_of_error!=0){
               errors_found+=1;
               transmitted[loops*point_of_error]^=1;
           }
        }
        
        System.out.println("Εγιναν "+errors+" σφάλματα κατά τη μετάδοση.");
        System.out.println("Διορθώθηκαν "+errors_found+" σφάλματα κατά την αποκωδικοποίηση.");
    }
}

