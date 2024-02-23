import java.io.*;

public class Main {

    public static void clearOutputFile() throws IOException{
        BufferedWriter clear = new BufferedWriter(new FileWriter("output.txt"));
        clear.write("");
        clear.close();
    }

    public static void main(String[] args) throws IOException{
        clearOutputFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));

        Grammar grammar = new Grammar("input.txt", writer);
        grammar.printGrammar();

        FirstAndFollow firstAndFollow = new FirstAndFollow(grammar, writer);
        firstAndFollow.printFirst();
        firstAndFollow.printFollow();

        // CLR clr = new CLR(grammar);
        // // clr.calculateStates();
        // clr.printStates();
        
        writer.close();
    }
}
