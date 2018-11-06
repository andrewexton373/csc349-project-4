public class Tester {

    // TESTING DENOMINATIONS
    // Set1. US denominations:100,50,25,10,5,1
    // Set2. Soviet denominations:100,50,20,15,10,5,3,2,1
    // Set3. Powers of 2:64,32,16,8,4,2,1
    // Set4. US without the nickel:100,50,25,10,1
    // Set5. Some set:66,35,27,18,10,1
    public static void main(String[] args) {
        int[] set1D = { 100, 50, 25, 10, 5, 1 };
        int[] set2D = { 100, 50, 20, 15, 10, 5, 3, 2, 1 };
        int[] set3D = { 64, 32, 16, 8, 4, 2, 1 };
        int[] set4D = { 100, 50, 25, 10, 1 };
        int[] set5D = { 66, 35, 27, 18, 10, 1 };

        test_with_denomination_set(1, set1D);
        test_with_denomination_set(2, set2D);
        test_with_denomination_set(3, set3D);
        test_with_denomination_set(4, set4D);
        test_with_denomination_set(5, set5D);
    }

    private static void test_with_denomination_set(int set_num, int[] d) {
        int matches = 0;
        for (int n=1; n<=200; n++) {
            int[] dp_results = ChangeMaker.change_DP(n, d);
            int dp_optimal_count = ChangeMaker.get_coin_count(dp_results);

            int[] greedy_results = ChangeMaker.change_greedy(n, d);
            int greedy_optimal_count = ChangeMaker.get_coin_count(greedy_results);

            if (dp_optimal_count == greedy_optimal_count) matches++;
        }
        System.out.printf("Testing set%d: x%d matches in 200 tests\n", set_num, matches);
    }

}