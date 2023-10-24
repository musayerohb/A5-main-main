import java.util.Scanner;
import java.io.StringReader;
import java.util.ArrayDeque;

/** 
 *  Shows use of StreamTokenizer.
 *  @author Nick Howe
 *  @version 26 September 2013
 */
public class Tokenizer {
  /** Pattern that matches on words */
  public static final String WORD = "[a-zA-Z]*\\b";

  /** Pattern that matches on arithmetic symbols */
  public static final String SYMBOL = "[^\\w]";

  /** 
   *  Converts the input string to a queue of tokens 
   *  @param input  the string to convert
   *  @return  the queue of tokens
   */
  public static ArrayDeque<Object> readTokens(String input) {
    Scanner scanner = new Scanner(new StringReader(input));
    ArrayDeque<Object> list = new ArrayDeque<>();
    // Below is a complicated regular expression that will split the
    // input string at various boundaries.
    scanner.useDelimiter
      ("(\\s+"                            // whitespace
      +"|(?<=[a-zA-Z])(?=[^a-zA-Z])"      // word->non-word
      +"|(?<=[^a-zA-Z])(?=[a-zA-Z])"      // non-word->word
      +"|(?<=[^0-9\\056])(?=[0-9\\056])"  // non-number->number
      +"|(?<=[0-9\\056])(?=[^0-9\\056])"  // number->non-number
      +"|(?<=[^\\w])(?=[^\\w]))");        // symbol->symbol
    
    while (scanner.hasNext()) {
      if (scanner.hasNextDouble()) {
        list.add(scanner.nextDouble());
      } else if (scanner.hasNext(WORD)) {
        list.add(scanner.next(WORD));
      } else if (scanner.hasNext(SYMBOL)) {
        list.add(scanner.next(SYMBOL).charAt(0));
      } else {
        list.add(scanner.next());
      }
    }
    scanner.close();
    return list;

  }

  /** Run short test */
  public static void main(String[] args) {
    if (args.length==0) {
      System.err.println("Usage:  java Tokenizer <expr>");
    } else {
      System.out.println(readTokens(args[0]));
    }
  }
}
