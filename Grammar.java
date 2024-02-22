import java.io.*;
import java.util.*;

public class Grammar {

    List<NonTerminal> nonTerminals;
    NonTerminal nonTerminal;
    List<String> prod;
    BufferedWriter writer;
    
    public Grammar(String fileName, BufferedWriter writer){
        this.writer = writer;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            nonTerminals = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null) {
                nonTerminal = new NonTerminal();
                String[] splittedLine = line.split("->|→");
                nonTerminal.nonTerminal = splittedLine[0].trim().toCharArray()[0];
                String[] prodString = splittedLine[1].split("\\||/");
                prod = new ArrayList<>();

                for (int i = 0; i < prodString.length; i++){
                    String trimmmed = prodString[i].trim();
                    if (trimmmed.equals("")) continue;
                    nonTerminal.productions.add(trimmmed);
                }
                nonTerminals.add(nonTerminal);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error processing the file...");
        }
    }


    public void printGrammer() throws IOException{
        for(NonTerminal nonTerminal : nonTerminals){
            nonTerminal.print(writer);
        }
    }

    public static void clearOutputFile() throws IOException{
        BufferedWriter clear = new BufferedWriter(new FileWriter("output.txt"));
        clear.write("");
        clear.close();
    }

    public static void main(String[] args) throws IOException{
        clearOutputFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));

        Grammar grammer = new Grammar("input.txt", writer);
        grammer.printGrammer();
        FirstAndFollow firstAndFollow = new FirstAndFollow(grammer, writer);
        firstAndFollow.printFirst();
        firstAndFollow.printFollow();
        writer.close();
    }
}
