import java.io.*;
import java.util.*;

public class NonTerminal{

    Character nonTerminal;
    List<String> productions;
    Set<String> first;
    Set<String> follow;


    public NonTerminal() {
        this.productions = new ArrayList<>();
        this.first = new HashSet<>();
        this.follow = new HashSet<>();
    }

    public void print(BufferedWriter writer) {
        try {
            writer.write(nonTerminal + ":" + productions.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to the output file...");
        }
    }
    
    
}