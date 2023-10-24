import java.util.ArrayDeque;

public class Calculate {
    public ArrayDeque<Object> list;

    public Calculate(ArrayDeque<Object> list) {
        this.list = list;
    }

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
    
    // Howe: Implement function that says if it's an operator
    public boolean parenOrOp(Object selectedToken) {
        
        if (selectedToken instanceof Character && (selectedToken.equals('(') || selectedToken.equals(')'))) {
            return true;
        }
        else {
            return false;
        }
    }
  

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

                System.out.println("Currently on stack: " + stack.toString());
                System.out.println("Currently on queue: " + outputQueue.toString());
            }

            // If the token is an operator (the "queue operator") then:
            else if (parenOrOp(selectedToken) == false) {
                // while there is an operator token at the top of the stack (peek) (the "stack operator"), and the stack operator has greater precedence than the queue operator,
                if (stack.isEmpty() == false && stack.peek() instanceof Character) {
                    // pop the stack operator off the stack and add it to the output queue;
                    int firstPrecedent = getPrec(stack.peek());
                    int objPrecendence = getPrec(selectedToken);
                    if (firstPrecedent >= objPrecendence) {
                        outputQueue.addLast(stack.pop());
                    }
                    // when no more high-precedence stack operators remain, finally push the queue operator onto the stack.
                    stack.push(selectedToken);
                    //outputQueue.addLast(stack.pop());
                }
                else {
                    stack.push(selectedToken);
                }
                System.out.println("Currently on stack: " + stack.toString());
                System.out.println("Currently on queue: " + outputQueue.toString());
            }

            else if (parenOrOp(selectedToken) == true) {
            
                // If the token is a left parenthesis, then push it onto the stack.
                if (selectedToken.equals('(')) {
                    stack.push('(');
                    System.out.println("Currently on stack: " + stack.toString());
                    System.out.println("Currently on queue: " + outputQueue.toString());
                }

                // If the token is a right parenthesis:
                else if (selectedToken.equals(')')) {
                    
                    //Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue.
                    while (!stack.peek().equals('(')) {
                        if (stack.isEmpty() == true) {
                            
                            //no left parenthesis exists then...the person inputting the expression messed up and forgot a matching paranthesis
                            throw new RuntimeException("Missing matching parenthesis.");
                        }
                        outputQueue.addLast(stack.pop());

                        System.out.println("Currently on stack: " + stack.toString());
                        System.out.println("Currently on queue: " + outputQueue.toString());
                        
                        // Pop the left parenthesis from the stack, but not onto the output queue.
                        
                        // If the stack runs out without finding a left parenthesis, then there are mismatched parentheses.
                    }

                    if (stack.peek().equals('(')) { 
                        stack.pop();
                        //why is this here? we all don't really know
                        //technically pops off the rest of the functio
                        //technically it may not be a String might be a Character
                        // if (stack.isEmpty() == false && stack.peek() instanceof String) {
                        //     outputQueue.addLast(stack.pop());
                        // }
                        
                        System.out.println("Currently on stack: " + stack.toString());
                        System.out.println("Currently on queue: " + outputQueue.toString());
                    }
                    
                    // When there are no more tokens to read:
                   
                    //Pop the left parenthesis from the stack, but not onto the output queue.
                    //If the stack runs out without finding a left parenthesis, then there are mismatched parentheses.
                }
            }

            
        }

        while (!stack.isEmpty()) {
            outputQueue.addLast(stack.pop());
        }
        System.out.println(outputQueue.toString());
            // Remaining portions of instructions:

            // While there are still tokens in the stack:
            // If the token on the top of the stack is a parenthesis, then there are mismatched parentheses.
            // If it is an operator, pop it onto the output queue.
            // Exit.

        Postfix postfixOperation = new Postfix(outputQueue);
        return postfixOperation.postFixArithmetic();
    }
}
