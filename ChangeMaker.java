import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class ChangeMaker {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); // ONLY CREATE SCANNER ONCE - OR FACE PENALTY (says spec)
       
        System.out.print("Enter the number of coin-denominations and the set of coin values: ");
        Integer K = scan.nextInt(); // # OF COIN-DENOMINATIONS
        int k = K.intValue();

        ArrayList<Integer> Denominations = new ArrayList<Integer>();
        for (int i=0; i<k; i++) {
            Denominations.add(scan.nextInt());
        }
        int[] d = convertIntegers(Denominations);

        System.out.print("Enter a positive amount to be changed (enter 0 to quit): ");
        Integer N = scan.nextInt(); // AMOUNT TO CALCULATE CHANGE FOR
        int n = N.intValue();

        if (n<=0) {
            System.exit(0);
        }
        else {
            int[] change_DP_result = change_DP(n, d);
            System.out.println("DP algorithm results");
            print_change_results(n, d, change_DP_result);

            int[] change_greedy_result = change_greedy(n, d);
            System.out.println("Greedy algorithm results");
            print_change_results(n, d, change_greedy_result);
        }

    }
 
    // DYNAMIC PROGRAMING

    static int[] C;
    public static int[] change_DP(int n, int[] d) {
        C = new int[n + 1]; // stores min number of coins to make change for n
        Arrays.fill(C, Integer.MAX_VALUE);
        C[0] = 0;

        int[] A = new int[n + 1]; // saves index of denomination used when computing C[j]
        Arrays.fill(A, -1);

        int[] B = new int[d.length + 1]; // stores count of coins used for index of denomination
        Arrays.fill(B, 0);

        int j = 1;

        // more change to be given
        while (j<=n) {

            // System.out.println("Amount: " + j);
            int minimum = Integer.MAX_VALUE;
            int denominationUsed = -1;

            // for each denomination
            for (int i=0; i<d.length; i++) {

                // if the current amount is greator than current denomination
                if (j >= d[i]) {
                    int current = C[j-d[i]];
                    if (current < minimum) {
                        minimum = current;
                        denominationUsed = i;
                    }
                } else {
                    continue;
                }
            }

            // System.out.println(d[denominationUsed]);
            A[j] = denominationUsed;
            C[j] = minimum + 1;
            j++;
        }

        while (n > 0) {
            int dIndex = A[n];
            n = n - d[dIndex];
            B[dIndex]++;
        }

        return B;
    }
    
    // GREEDY

    public static int[] change_greedy(int n, int[] d) {
        int[] change = new int[d.length];
        int remaining = n, i = 0;
        while (remaining > 0) {
            while (d[i] <= remaining) {
                change[i]++;
                remaining -= d[i];
            } 
            i++;
            
        }
        return change;
    }

    // HELPERS

    // IS THIS OKAY TO BE PUBLIC ???? - FOR TESTING
    public static int get_coin_count(int[] results) {
        int total = 0;
        for (int i = 0; i < results.length; i++) {
            total += results[i];
        }
        return total;
    }

    // PRINT FORMAT
    // DP algorithm results
    // Amount:87
    // Optimal distribution:3*25 c+1*10 c+2*1c
    // Optimal coin count:6
    private static void print_change_results(int n, int[] d, int[] results) {
        System.out.println("Amount: " + n);
        System.out.print("Optimal distribution: ");
        // LOOP OVER DENOMINATIONS AND COUNTS, PRINT EACH
        for (int i = 0; i < d.length; i++) {
            if (results[i] > 0)
                System.out.printf("%d*%dc ", results[i], d[i]);
        }
        System.out.print("\n");
        System.out.println("Optimal coin count: " + get_coin_count(results));
    }

    private static int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
}