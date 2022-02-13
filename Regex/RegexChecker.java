import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexChecker {
 

  private static Pattern pattern;
  private static Matcher matcher;
  

  public RegexChecker(String s) {
    
  }
  
  
  public static void main(String[] args){


    //match one String against one regex
    System.out.println(Pattern.matches("([.w]){2}f", "..f"));
    System.out.println(Pattern.matches("[k*]a*b", "kaaaaabx"));

    //using a Matcher object to find matching substrings
    pattern = Pattern.compile("[a-c]\\+{5}\""); //becomes \+ repeated 5 times
    matcher = pattern.matcher("a1bb\\\\c2a4\\\\b+++++\"6c89\\\\c+++++\"");
    List<String> alphaNumSequence = new ArrayList<String>();
    while(matcher.find()) {
        System.out.println(matcher.start()); // first index
    	System.out.println(matcher.group());
    	alphaNumSequence.add(matcher.group());
    }

    //using the String method "matches" to check if a String matches a regex
    String str = new String();
    str = "Hello_World11";
    boolean isSame = str.matches("(((Hello)))\\w.*\\4(1)");
    System.out.println(isSame);
   }
}








