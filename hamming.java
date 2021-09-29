//icsd13170 
import java.math.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class hamming {

    public static void main(String args[]) {

        int undetected_error_count = 0;
        int detected_error = 0;
        int no_errors_detected=0;
        int i;
        for (i = 0; i <= 10000; i++) {
            Random rn = new Random();
            int randomInt = rn.nextInt(2);
            int[] k = new int[4];
            int[] h = new int[7];
            int[] b1 = new int[7];
            for (int n = 0; n < 4; n++) {
                k[n] = rn.nextInt(2);
            }
            b1 = generateCode(k);
            System.out.println("Generated code is:" + Arrays.toString(b1));

            //adding some noise probability
            for (int j = 0; j <= 6; j++) {

                h[j] = (rn.nextInt(100) + 1 > 10) ? b1[j] : flip_bit(b1[j]);

            }
            System.out.println("Fliped code is:    " + Arrays.toString(h));

            //checking 
            int parity[] = new int[3];
            int p4[] = {h[0], h[2], h[4], h[6]};
            int p5[] = {h[1], h[2], h[5], h[6]};
            int p6[] = {h[3], h[4], h[5], h[6]};

            parity[0] = set_parity_bit(p4);
            parity[1] = set_parity_bit(p5);
            parity[2] = set_parity_bit(p6);

            int position = (int) (parity[2] * Math.pow(2, 2) + parity[1] * Math.pow(2, 1) + parity[0] * Math.pow(2, 0));
            if (position == 0 ) {
                System.out.println("\nRECEIVER:");
                System.out.println("No error is detected");
                no_errors_detected++;

            } else {
                System.out.println("\nRECEIVER:");
                System.out.println("Error is detected at position " + position + " at the receiving end.");
                detected_error++;

                System.out.println("Correcting the error.... ");
//                if (h[position] == 1) {
//                    h[position] = 0;
//                } else {
//                    h[position] = 1;
//                }
//
//                System.out.print("The correct code is ");
//                System.out.print(Arrays.toString(h));
            }
            if (position == 0 &&  !Arrays.equals(h, b1) ){
                undetected_error_count++;
                System.out.println("But we have an undetected error comparing the 2 codewords before and after flip");

            }
            System.out.println("\n\n\n");

        }
        float prob1 = (float) (undetected_error_count / 10000.0) * 100;
        float prob2 = (float) ((float)detected_error / (detected_error+undetected_error_count) * 100);

        System.out.println("P = "+0.1 );
        System.out.println("Undetected error count: " + (float) prob1);
        System.out.println("Detected error count and Correction: " + (float) prob2);
    }

    static int[] generateCode(int a[]) {
        int b[];
        int i = 0, parity_count = 0, j = 0, k = 0;
        while (i < a.length) {
            if (Math.pow(2, parity_count) == i + parity_count + 1) {
                parity_count++;
            } else {
                i++;
            }
        }
        b = new int[a.length + parity_count];
        for (i = 1; i <= b.length; i++) {
            if (Math.pow(2, j) == i) {
                b[i - 1] = 2;
                j++;
            } else {
                b[k + j] = a[k++];
            }
        }
        for (i = 0; i < parity_count; i++) {
            b[((int) Math.pow(2, i)) - 1] = getParity(b, i);
        }
        return b;
    }

    public static int flip_bit(int x) {
        if (x == 0) {
            return 1;
        } else {
            return 0;
        }

    }

    static int getParity(int b[], int power) {
        int parity = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] != 2) {
                int k = i + 1;
                String s = Integer.toBinaryString(k);
                int x = ((Integer.parseInt(s)) / ((int) Math.pow(10, power))) % 10;
                if (x == 1) {
                    if (b[i] == 1) {
                        parity = (parity + 1) % 2;
                    }
                }
            }
        }
        return parity;
    }

    static int set_parity_bit(int a[]) {
        int count = 0; //........Initialising count to zero which will count the number of 1. 
        int l = a.length;

        for (int i = 0; i < l; ++i) {
            if (a[i] == 1) {
                ++count; //............Incrementing count if value in array "a" is 1.
            }
        }
        if ((count % 2) == 0) {
            return 0;//........Returning 0 if even number of 1  
        } else {
            return 1;//........Returning 1 if odd number of 1
        }
    }

}
