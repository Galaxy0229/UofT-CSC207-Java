
import java.util.List;
import java.util.Stack;

    public class Test {
        public static void main(String[] args) {
            List<String> ex = new Stack();
            ex.add("Hello");
            System.out.println(((Stack<String>) ex).pop());
        }
    }

