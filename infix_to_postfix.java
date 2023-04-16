import java.util.HashMap;
import java.util.Stack;

public class infix_to_postfix {
    public static void main(String[] args) {
        //Since I assume numbers to be ONE LETTER Characters(even the summation), and division is integer division...
        //because of these it cannot show correct results when numbers are too big.
        String infix = "1+2*1/((2-1))+((3-1)/1-(2+(2*1)))";
        String postfix = "";

        Stack<Character> ops = new Stack<>();
        HashMap<Character, Integer> pri = new HashMap<>();
        pri.put('^', 3);
        pri.put('/', 2);
        pri.put('*', 2);
        pri.put('-', 1);
        pri.put('+', 1);
        pri.put('(', 0);//Need to research how did this solved the bug of nested paranthesis
        
        for (int i = 0; i < infix.length(); ++i) {
            Character cur = infix.charAt(i);
            if (infix_to_postfix.isOp(cur)) {
                if(ops.empty()) {
                    ops.add(cur);
                } else if (cur == '(') {
                    ops.add(cur);
                } else if (cur == ')') {
                    while (ops.peek() != '(') {
                        postfix += ops.pop();
                    }
                    ops.pop();
                } else if (ops.peek() == '(' || pri.get(cur) > pri.get(ops.peek())) {
                    ops.add(cur);
                } else if (pri.get(cur) <= pri.get(ops.peek())) {
                    while (!ops.isEmpty() && pri.get(cur) <= pri.get(ops.peek())) {
                        postfix += ops.pop();
                    }
                    ops.add(cur);
                }
            } else {
                postfix += cur;
                continue;
            }
            System.out.println(ops);
        }
        while (!ops.isEmpty()) postfix += ops.pop();
        //Result
        System.out.println("Postfix: "+ postfix);


        //CALCULATER THAT USES POSTFIX
        Stack<Character> process = new Stack<>();
        for (int i = postfix.length()-1; i >= 0; --i) process.add(postfix.charAt(i));
        while (process.size() > 1) {
            Stack<Character> poll = new Stack<>();
            while (!isOp(process.peek())) poll.add(process.pop());
            poll.add(process.pop());

            Character op = poll.pop();
            if (op == '*') {
                poll.add((char)(((poll.pop()-'0')*(poll.pop()-'0'))+'0'));
            }
            if (op == '+') {
                poll.add((char)(((poll.pop()-'0')+(poll.pop()-'0'))+'0'));
            }
            if (op == '-') {
                char c1 = poll.pop();
                char c2 = poll.pop();
                poll.add((char)(c2-c1+'0'));
            }
            if (op == '/') {
                char c1 = poll.pop();
                char c2 = poll.pop();
                poll.add((char)(((c2-'0')/(c1-'0'))+'0'));
            }
            if (op == '^') {
                char c1 = poll.pop();
                char c2 = poll.pop();
                poll.add((char)((int)Math.pow((c2-'0'),(c1-'0'))+'0'));
            }
            while (!poll.isEmpty()) process.add(poll.pop());
        }
        System.out.println("Answer: " + process.pop());
    }
    public static boolean isOp(Character c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '(' || c == ')';
    }
}
