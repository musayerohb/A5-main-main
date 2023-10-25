import java.util.ArrayDeque;

import javax.management.RuntimeErrorException;
/**
 * Contains methods that calculate the result of an expression in postfix notation.
 */
public class Postfix {
    // The list that will house the expression being calculated.
    public ArrayDeque<Object> list; 

    /**
     * Constructor for the Postfix class. Initializes the list representing the operation to be converted to postfix arithmetic before being calculated.
     * @param list (ArrayDque<Object>) The list containing the parts of the operation to be converted
     */ 
    public Postfix(ArrayDeque<Object> list) {
        this.list = list;
        
    }

    /**
     * Calculates the result of an expression in postfix notation.
     * @return The result in a stack.
     */
    public double postFixArithmetic() {
       Object[] Arraylist = list.toArray();
       ArrayDeque<Object> stack = new ArrayDeque<>();
    
       if (list.size() == 0) {
            throw new RuntimeErrorException(null, "No expression inputted.");     
       }

       for (int item = 0; item < Arraylist.length; item++) {
            if (Arraylist[item] instanceof Double) {
                double d = (Double)Arraylist[item];
                stack.push(d);
    
            }
            if(stack.size() == 0) {
                throw new RuntimeErrorException(null, "No expression inputted.");     
            }
            else if (Arraylist[item] instanceof Character) {
                // Character operator = (Character)Arraylist[item];

                Object secondNumber = stack.pop();
                Object firstNumber = null;
                try {
                    firstNumber = stack.pop();
                }
                catch (Exception e) {
                    throw new RuntimeException("Malformed expression detected, either postfix notation not done correctly or trying to do too many operations on too few numbers.");
                }            
                double secondNumberDouble = (Double)secondNumber;
                double firstNumberDouble = (Double)firstNumber;
                if (Arraylist[item].equals('+')) {
                    double result = firstNumberDouble + secondNumberDouble;
                    stack.push(result);
                } 
                else if (Arraylist[item].equals('-')) {
                    double result = firstNumberDouble - secondNumberDouble;
                    stack.push(result);
                }
                else if (Arraylist[item].equals('*')) {
                    double result = firstNumberDouble * secondNumberDouble;
                    stack.push(result);
                }
                else if (Arraylist[item].equals('/')) {
                    double result = firstNumberDouble / secondNumberDouble;
                    stack.push(result);
                }
                else if (Arraylist[item].equals('^')) {
                    double result = Math.pow(firstNumberDouble, secondNumberDouble);
                    stack.push(result);
                }

                //push last two numbers off the stack
                //use operation on them (last of the two numbers goes first) and do the math on it
            }
        }

        if (stack.size() != 1) {
            throw new RuntimeErrorException(null, "Stack operation error: too many operations for too few numbers.");
        }
        double result = (Double)stack.pop();
        return result;
        //only if the size of the stack is 1 should you push the only value on there and return it.
        
    }
    
    public static void main(String args[]) {
        ArrayDeque<Object> list = Tokenizer.readTokens(args[0]);
        Postfix postfixCalculator = new Postfix(list);
        System.out.println(postfixCalculator.postFixArithmetic());
    }

}
