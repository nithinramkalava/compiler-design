import java.io.*;
import java.util.*;

public class Grammar {

    List<NonTerminal> nonTerminals;
    NonTerminal nonTerminal;
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


    public void printGrammar() throws IOException{
        for(NonTerminal nonTerminal : nonTerminals){
            nonTerminal.print(writer);
        }
    }

}
