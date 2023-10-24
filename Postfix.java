import java.util.ArrayDeque;

import javax.management.RuntimeErrorException;

public class Postfix {
    
    //eatin a burger with no honey mustard
    //All static methods
    
// addFirst(x): adds element at the beginning
// addLast(x): adds element at the end
// removeFirst(): removes element from the beginning
// removeLast(): removes element from the end
// Use as a queue: addLast() and removeFirst()
// Use as a stack: addFirst and removeFirst()

//Implementation of push(x) :
// Create new node with x as data, pointing to head
// Make head point at the newly created node

// Implementation of pop():
// Make note of head.data for return value
// Make head point at head.next
    public ArrayDeque<Object> list; 

    public Postfix(ArrayDeque<Object> list) {
        this.list = list;
        
    }

    public double postFixArithmetic() {
       Object[] Arraylist = list.toArray();
       ArrayDeque<Object> stack = new ArrayDeque<>();
    
    //    if (stack.size() == 0) {
    //         throw new RuntimeErrorException(null, "No expression inputted.");     
    //    }

       for (int item = 0; item < Arraylist.length; item++) {
            if (Arraylist[item] instanceof Double) {
                double d = (Double)Arraylist[item];
                stack.push(d);
                System.out.println("Currently on the stack: " + stack.toString());
            }
            if(stack.size() == 0) {
                throw new RuntimeErrorException(null, "No expression inputted.");     
            }
            else if (Arraylist[item] instanceof Character) {
                Character operator = (Character)Arraylist[item];

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

                System.out.println("Currently on the stack: " + stack.toString());
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
    

}
