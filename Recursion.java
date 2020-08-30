/**
 * This is a recursion program which computes all the possible factors of any 
 * number provided. The user inputs a number and the program prints the 
 * factors of the given integer on each individual line.
 * @author Ruchik Chaudhari
 * 05/29/2020
 */
public class Recursion {

    /**
     * The main calls the function factorOf with a parameter of an integer
     * @param args
     */
    public static void main(String[] args) {
        // Call factorsOf function and test 2,3,8,9,20,35
    	int [] testCase = {2,3,8,9,20,35};
    	for (int testNum : testCase) {
    		factorsOf(testNum);
    	}
    }
    
    /**
     * The function calls its helper function and prints out all the possible 
     * factors on an individual line. For instance if the user passed in number
     * eight then 2 2 2 / 2 4 / 4 2 ("/" represents a new line) is printed. 
     * @param n - number to be factored greater than 1
     */
    public static void factorsOf(int n) {
       
    	System.out.println(String.format("factorsOf(%d)", n));
    	
        // call helper function with result, product, n
    	factorsOf("",1,n);
    	System.out.println();
    	System.out.println();
    	
    }
    
    /**
     * The helper function for factorsOf(int n) function with two extra 
     * parameters. One describes the path we took to get to the factors of any 
     * given integer and the other one is an integer which keeps the program on 
     * track and provokes recursion. For instance if the user passed in number
     * eight then 2 2 2 / 2 4 / 4 2 ("/" represents a new line) is printed. 
     * 
     * @param result - the string of factors so far (e.g. "2 2 ")
     * @param product - the value equivalent of the string factors (e.g. 4)
     * @param n - the number to be factored (e.g 8)
     */
    public static void factorsOf(String result, int product, int n) {
        // print the (<result>) with String.format
    	System.out.printf("(%s)", result);
        
        // failure / backtrack base case 
        // if product exceeds n, dead end, return without doing anything
    	if(product > n) {
    		return;
    	}
    	
        // success base case 
        // if product equals n, print the factorization (2 2 2) on its own line
    	else if (product == n) {
    		System.out.println();
    		System.out.printf("Factorization => %s", result);
    		System.out.println();
    	}
    	
        // Recursive Case
    	// If the product is less than the integer then keep recursing.
    	else {
    		for (int factor = 2; factor < n; factor++) {
    			int num = factor * product; 
    			factorsOf(result+factor+" ", num, n);
    		}
    	}
    }
}
