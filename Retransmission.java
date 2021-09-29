
import java.util.Random;

public class Retransmission {

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
        int errors = 0;
        int ACK = 0;
        int errors_found = 0;
        int transmitted[] = new int[3500];
        for (i = 0; i < 3500; i = i + 7) {//για όλη την πληροφορία που θέλω να μεταδόσω
            ACK = 0;
            while (ACK == 0) {
                double d = Math.random();
                for (int j = 0; j < 6; j++) {
                    if (d <= 0.5) {// στο 10% των περιπτώσεων
                        transmitted[j] = coded[j] ^ 1; // το bit αλλοιώνεται
                        errors++;
                    }
                }
                int point_of_error = (int) (transmitted[i] * Math.pow(2, 0) + transmitted[i + 1] * Math.pow(2, 1) + transmitted[i + 3] * Math.pow(2, 2));//syndrome
                if (point_of_error != 0) {
                    errors_found += 1;
                } else {
                    ACK = 1;
                }
            }
        }

     

        System.out.println("Εγιναν " + errors + " σφάλματα κατά τη μετάδοση.");
        System.out.println("Διορθώθηκαν " + errors_found + " σφάλματα με αίτηση αναμετάδοσης.");
    }
}


