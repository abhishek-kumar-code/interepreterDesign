import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Interpreter {

    static String lex;
    static int col =0;
    static InputStreamReader reader;
    static Stack<String> items = new Stack<String>();
    static String var_a = "";

    public Interpreter() {
    }

    public static void main(String[] args) throws IOException{
        try{
            reader = new FileReader("./src/input_file.txt");
            lex = getTokens();
            System.out.println("CS 5361 Theory of Programming Languages");
            System.out.println("Abhishek Kumar, 2018 Copyright");
            System.out.println("******************************");
            if (P())
            {
                System.out.println("Stack on termination of the program: " + items);
                if(items.pop() == "T")
                    System.out.println("Valid Boolean Expression");
                else
                    System.out.println("Invalid Boolean Expression");

            }
            else{
                System.out.println("Stack on termination of the program: " + items);
                if(items.pop() == "T")
                    System.out.println("Valid Boolean Expression");
                else
                    System.out.println("Invalid Boolean Expression");
            }
            System.out.println("******************************");

        }
        catch (FileNotFoundException ex){
            System.out.println(ex);
        }
    }

    private static boolean P()
    {
        if (D())
        {   //System.out.println("kumar");
            if (B())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    private static boolean D()
    {
        if (lex.equals("#"))
        {
            lex = getTokens();
            if (lex.equals("a"))
            {
                //var_a = "T";
                lex = getTokens();
                if (lex.equals(":"))
                {
                    lex = getTokens();
                    if (lex.equals("="))
                    {
                        lex = getTokens();
                        if(V())
                        {
                            lex = getTokens();
                            //return true;
                            if(lex.equals(";"))
                            {
                                lex = getTokens();
                                if(D())
                                {
                                    return true;
                                }
                                else
                                {
                                    return false;
                                }
                            }
                            else
                            {
                                return false;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (lex.equals("~") || lex.equals("T") || lex.equals("F") || lex.equals("a") || lex.equals("("))
        {
            return true;
        }
        else
        {
            //System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean V()
    {

        if (lex.equals("T"))
        {
            //items.push("T");
            //lex = getTokens();
            var_a = "T";
            return true;
        }
        else if (lex.equals("F"))
        {
            //items.push("F");
            //lex = getTokens();
            var_a = "F";
            return true;
        }

        else
        {
            //System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean B()
    {
        if (IT())
        {
            if (lex.equals("."))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean IT()
    {
        //System.out.println("comp sc");
        if (OT())
        {
            if (IT_Tail())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean IT_Tail()
    {

        String t1, t2;
        if (lex.equals("->"))
        {
            lex = getTokens();
            if (OT())
            {
                t2 = items.pop();
                t1 = items.pop();
                // Get the top 2 tokens from stack and perform AND op
                if (t1.equals("T") && t2.equals("F"))
                    items.push("F");
                else
                    items.push("T");

                if (IT_Tail())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (lex.equals(".") || lex.equals(")"))
        {
            return true;
        }
        else
        {
            //System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean OT()
    {

        if (AT())
        {
            if (OT_Tail())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean OT_Tail()
    {

        String t1, t2;
        if (lex.equals("v"))
        {
            lex = getTokens();
            if (AT())
            {
                t2 = items.pop();
                t1 = items.pop();

                // Get the top 2 tokens from stack and perform AND op
                if (t1.equals("F") && t2.equals("F"))
                    items.push("F");
                else
                    items.push("T");

                if (OT_Tail())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (lex.equals("->") || lex.equals(".") || lex.equals(")"))
        {
            return true;
        }
        else
        {
            //System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean AT()
    {

        if (L())
        {
            if (AT_Tail())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean AT_Tail()
    {

        String t1, t2;
        if (lex.equals("^"))
        {
            lex = getTokens();
            if (L())
            {
                t2 = items.pop();
                t1 = items.pop();

                // Get the top 2 tokens from stack and perform AND op
                if (t1.equals("T") && t2.equals("T"))
                    items.push("T");
                else
                    items.push("F");
                if (AT_Tail())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else if (lex.equals("v") || lex.equals("->") || lex.equals(".") || lex.equals(")"))
        {
            return true;
        }
        else
        {
            //System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static boolean L()
    {

        if (lex.equals("~"))
        {
            lex = getTokens();
            if (L())
            {
                String token = items.pop();

                if (token.equals("T"))
                    items.push("F");
                else
                    items.push
                            ("T");
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (A())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static boolean A()
    {

        if (lex.equals("T"))
        {
            items.push("T");
            lex = getTokens();
            return true;
        }
        else if (lex.equals("F"))
        {
            items.push("F");
            lex = getTokens();
            return true;
        }
        // Push the string (T or F) depending on the value of 'a' in the text file
        else if (lex.equals("a"))
        {
            items.push("T");
            lex = getTokens();
            return true;
        }

        else if (lex.equals("("))
        {
            lex = getTokens();
            if (IT())
            {
                if (lex.equals(")"))
                {
                    lex = getTokens();
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            //System.out.println("Error in the format of the expression");
            System.out.println("=======================================");
            return false;
        }
    }

    private static String getTokens()
    {

        int i = 0;
        String token = "";
        char readChar;

        try
        {

            while ((i = reader.read()) != -1)
            {
                readChar = (char)i;
                ++col;
                if (readChar == '-')
                {
                    token = "-";
                    i = reader.read();
                    ++col;
                    readChar = (char)i;
                    token = token + readChar;
                    break;
                }
                else if (readChar == ' ')
                {
                    continue;
                }
                else
                {
                    token = "" + readChar;
                    break;

                }

            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }


        return token;
    }


}


