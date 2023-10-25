import java.util.ArrayDeque;
/**
 * Contains methods that take in an expression, converts it to postfix arithmetic, and solves the expression.
 */
public class Calculate {
    // The list that will house the expression being calculated.
    public ArrayDeque<Object> list;

    /**
     * Constructor for the Calculate class. Initializes the list representing the operation to be calculated.
     * @param list (ArrayDque<Object>) The list containing the parts of the operation being solved
     */ 
    public Calculate(ArrayDeque<Object> list) {
        this.list = list;
    }
    
    /**
     * Takes in the elements of the expression directly to the left and right from when the method is called and checks if either is an exponent by calling the getPrec method. It returns true if the returned precedent matches the precedent of '^', and false if not.
     * @param objectLeft (Object) The object to the left of the current position in the expression
     * @param objectRight (Object) The object to the right of the current position in the expression
     * @return true if the element follows right association (is an exponent), false if not.
     */
    public boolean isRightAssociation(Object objectLeft, Object objectRight) {
        if (getPrec(objectLeft) == 3) {
            return true;
        }
        else {
            return false;
        } 
    }
    
    /**
     * Takes in a given operator and checks its precedence score.
     * @param operator The operator being checked
     * @return the precedent of the operator
     */
    public int getPrec(Object operator) {
        int precedent = 0;
        if (operator.equals('+') || operator.equals('-')) {
            precedent = 1;
        }
        else if (operator.equals('*') || operator.equals('/')) {
            precedent = 2;
        }
        else if (operator.equals('^')) {
            precedent = 3;
        }
        return precedent;
        
    }

    /**
     * Checks to see if the token is an operation or a parenthesis part of an operation.
     * @param selectedToken (Object) The token from the list representing the operation
     * @return true if the selected token is an operator, false if it's not
     */
    public boolean parenOrOp(Object selectedToken) {
        if (selectedToken instanceof Character && (selectedToken.equals('(') || selectedToken.equals(')'))) {
            return true;
        }
        else {
            return false;
        }
    }
  
    /**
     * Takes in an expression in infix notation and calculates the result using postfix notation.
     * @return The solved expression.
     */
    public double calculateResult() {
        ArrayDeque<Object> stack = new ArrayDeque<>();
        ArrayDeque<Object> outputQueue = new ArrayDeque<>();
        // While there are tokens to be read:
        while (list.isEmpty() == false) {
            // Read a token.
            Object selectedToken = list.removeFirst();
            
            // If the token is a number, then add it to the output queue.
            if (selectedToken instanceof Double) {
                outputQueue.addLast(selectedToken);

            }

            // If the token is an operator (the "queue operator") then:
            else if (parenOrOp(selectedToken) == false) {
                // while there is an operator token at the top of the stack (peek) (the "stack operator"), and the stack operator has greater precedence than the queue operator,
                if (stack.isEmpty() == false && stack.peek() instanceof Character) {
                    // pop the stack operator off the stack and add it to the output queue;
                    int firstPrecedent = getPrec(stack.peek());
                    int objPrecendence = getPrec(selectedToken);
                    
                    // if (selectedToken.equals('^') && firstPrecedent > objPrecendence) {
                    //     outputQueue.addLast(stack.pop());
                    // }

                    if (firstPrecedent >= objPrecendence && !isRightAssociation(stack.peek(), selectedToken)) {
                        outputQueue.addLast(stack.pop());
                    }
                    // when no more high-precedence stack operators remain, finally push the queue operator onto the stack.
                    stack.push(selectedToken);
                }
                else {
                    stack.push(selectedToken);
                }

            }

            else if (parenOrOp(selectedToken) == true) {
            
                // If the token is a left parenthesis, then push it onto the stack.
                if (selectedToken.equals('(')) {
                    stack.push('(');
                }

                // If the token is a right parenthesis:
                else if (selectedToken.equals(')')) {
                    
                    //Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue.
                    while (!stack.peek().equals('(')) {
                        if (stack.isEmpty() == true) {
                            
                            // If the stack runs out without finding a left parenthesis, then there are mismatched parentheses.
                            throw new RuntimeException("Missing matching parenthesis.");
                        }
                        outputQueue.addLast(stack.pop());
                        
                    }

                    if (stack.peek().equals('(')) { 
                        // Pop the left parenthesis from the stack, but not onto the output queue.
                        stack.pop();
                    
                    }
                }
            }
            
        }

    // When there are no more tokens to read:
        // While there are still tokens in the stack:
        while (!stack.isEmpty()) {
            // If it is an operator, pop it onto the output queue.
            outputQueue.addLast(stack.pop());
        }
        // Exit.
        
        Postfix postfixOperation = new Postfix(outputQueue);
        return postfixOperation.postFixArithmetic();
    }

    public static void main(String args[]) {
        ArrayDeque<Object> list = Tokenizer.readTokens(args[0]);
        Calculate calculate = new Calculate(list);
        System.out.println(calculate.calculateResult());
    }
}
