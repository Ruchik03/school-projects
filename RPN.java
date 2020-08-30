import java.util.*;

/**
 * The program implements RPN (Reverse Polish Notation). The user inputs a 
 * series of numbers and inputs operators after the numbers whenever required.
 * For instance user would input 5 3 1 + - => 5 - 4 => 1.0 and 1.0 would be the
 * output.
 * @author Ruchik Chaudhari
 * 06/04/2020
 *
 */
public class RPN {

	/**
	 * Calls the test function to check if the program is running as expected. 
	 * @param args
	 */
    public static void main(String[] args) {
    	//Call the test function
        testRPN();
    }
 
    /**
     * Tests the RPN evaluator - Do not modify this in any way
     */
    public static void testRPN() {
        String[] tests = {
                "2 2 +", 
                "2 3 -", 
                "4 5 *", 
                "6 5 /", 
                "1 2 3 4 5 6 7 8 9 + + + + + + + +",
        "5 2 2 * - 1 2 + /"};
        double[] results = {4.0,-1,20,1.2,45, 0.3333333333333333};

        for (int i = 0; i < tests.length; i++) {
            System.out.println(String.format("Evaluating => %s", tests[i]));
            double result = evaluateRPN(tests[i]);
            System.out.println(String.format("Result => %s", result));
            if (result != results[i]) {
                System.out.println(String.format("Error on test %s expected %s, received %s", 
                        tests[i], results[i], evaluateRPN(tests[i])));
                return;
            }
        }
        System.out.println("Congratulations - you passed the tests");

    }
    
    /**
	 * Read input into the Queue. Goes through the Queue and whenever it's a number
	 * moves it to the Stack. If an operator is spotted in the Queue then performs
	 * the operation on the top two numbers in the stack.
	 * The parameter is the input received from the user. 
	 * Input: "2 2 + 3 * 4 9 + +" ==> Queue: [2, 2, +, 3, *, 4, 9, +, +] ==> Stack[]
	 * Queue: [2, +, 3, *, 4, 9, +, +] ==> Stack: [2]
	 * Queue: [+, 3, *, 4, 9, +, +] ==> Stack: [2, 2]
	 * Queue: [3, *, 4, 9, +, +] ==> Stack: [4.0]
	 * Queue: [*, 4, 9, +, +] ==> Stack: [4.0, 3]
	 * Queue: [4, 9, +, +] ==> Stack: [12.0]
	 * Queue: [9, +, +] ==> Stack: [12.0, 4]
	 * Queue: [+, +] ==> Stack: [12.0, 4, 9]
	 * Queue: [+] ==> Stack: [12.0, 13.0]
	 * Queue: [] ==> Stack: [25.0]
	 * @param String input
	 * @return Double - the result of evaluated input
	 */
    public static double evaluateRPN(String input) {
        // Create queue, transfer input into it "2 3 +" -> [2 3 +]
    	Queue <String>inputQueue = new LinkedList<String>();
    	Scanner sc = new Scanner(input);
    	while(sc.hasNext()) {
    		inputQueue.add(sc.next());
    	}
    	
        // Print input
    	System.out.println(inputQueue);

    	// Create new empty stack
    	Stack <String> numbers = new Stack<String>();
    	// Pop off each item in the queue and evaluate it
    	while (!inputQueue.isEmpty()) {
    		String token = inputQueue.remove();
    		// if operator such as '*' - pop two operands, evaluate, push result: Queue [+] Stack [2 3] => [][5]
    		if(isOperator(token)) {
    			double op2 = Double.parseDouble(numbers.pop());
    			double op1 = Double.parseDouble(numbers.pop());
    			String result = evaluateBinaryOperator(op1, token, op2);
    			numbers.push(result);
    			System.out.print(inputQueue);
    			System.out.println(numbers);
    		}
    		// else operands such as "5" just need to be pushed [3 +][2]=>[+][2 3]
    		else if(!isOperator(token)) {
    			numbers.push(token);
    		}
    	}
    	// return last item in stack [][5]
    	return Double.parseDouble(numbers.pop());
    }

    /**
	 * Checks if the given input is an operator (+,-,*,/) or not and returns
	 * true if it is or false if it is not.
	 * @param input- element of the Queue 
	 */
    private static boolean isOperator(String input) {
    	//Create a string of all possible operators
    	String operator = "+-*/";
    	
    	//Check if the input matches with any operator
    	if (operator.indexOf(input) != -1)
    		return true;
    	
    	return false;
    }

    /**
     * Gets two numbers and an operator as a parameter. Evaluates the two numbers
     * as described by the operator and converts the result into string and  
     * returns the result. For instance: (3.0, "*", 2.0) => "6.0" 
     * @param op1 - first number 
     * @param operator - evaluator of op1 and op2
     * @param op2 - second number 
     * @return result 
     */
    private static String evaluateBinaryOperator(Double op1, String operator, Double op2) {
      
    	// Perform the evaluation as described by the operator 
    	double result = 0.0;
    	switch(operator) {
    		case "+":
    			result = op1 + op2;
    			break;
    		case "-":
    			result = op1 - op2;
    			break;
    		case "*":
    			result = op1 * op2;
    			break;
    		case "/":
    			result = op1 / op2;
    			break;
    	}
    	 // Return op1 <operator> op2
    	return String.valueOf(result);
    }
}