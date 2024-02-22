import java.io.*;
import java.util.*;

public class Grammer {

    class NonTermainal{

        Character nonTermainal;
        List<String> productions;
        Set<String> first;
        Set<Character> follow;


        public NonTermainal() {
            this.productions = new ArrayList<>();
            this.first = new HashSet<>();
            this.follow = new HashSet<>();
        }


        public void print(BufferedWriter writer) {
            try {
                writer.write(nonTermainal + ":" + productions.toString());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error writing to the output file...");
            }
        }
        
        
    }

    List<NonTermainal> nonTermainals;
    NonTermainal nonTermainal;
    List<String> prod;
    BufferedWriter writer;
    
    public Grammer(String fileName, BufferedWriter writer){
        this.writer = writer;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            String line;
            nonTermainals = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null) {
                nonTermainal = new NonTermainal();
                String[] splittedLine = line.split("->|→");
                nonTermainal.nonTermainal = splittedLine[0].trim().toCharArray()[0];
                String[] prodString = splittedLine[1].split("\\||/");
                prod = new ArrayList<>();

                for (int i = 0; i < prodString.length; i++){
                    String trimmmed = prodString[i].trim();
                    if (trimmmed.equals("")) continue;
                    nonTermainal.productions.add(trimmmed);
                }
                nonTermainals.add(nonTermainal);
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.out.println("Error processing the file...");
        }

    }

    public void printFirst() throws IOException{
        writer.newLine();
        for(NonTermainal nonTermainal: nonTermainals){
            nonTermainal.first.addAll(first(nonTermainal));
            writer.write("first("+nonTermainal.nonTermainal+"): " + nonTermainal.first.toString());
            writer.newLine();
        }
    
    }

    public Set<String> first(NonTermainal nonTermainal){
    
        for(String prod: nonTermainal.productions){
            int charPos = -1;
            boolean epsilon = true;
            try {
                while (epsilon) {
                    charPos ++;
                    char currChar = prod.charAt(charPos);
                    epsilon = firstSuccessor(nonTermainal, currChar, prod, charPos);
                }
            } catch (Exception exception) {
                
            }
        }

        return nonTermainal.first;
    }

    public boolean firstSuccessor(NonTermainal nonTermainal, char firstChar, String prod, int pos){
        if ((int) firstChar < 65 || (int) firstChar > 96){
            if (firstChar == 'i'){
                try{
                    char secondChar = prod.charAt(pos + 1);
                    if (secondChar == 'd') 
                        nonTermainal.first.add(firstChar+"d");
                    else throw new Exception("blah");
                } catch (Exception exception) {
                    nonTermainal.first.add(firstChar+"");
                }
            }
            else 
                nonTermainal.first.add(firstChar+"");
        } else {
            for(NonTermainal otherNonTermainal: nonTermainals){
                if (otherNonTermainal.nonTermainal.equals(firstChar)){
                    Set<String> otherNonTerminalFirst;
                    if (otherNonTermainal.first.size() > 0){
                        otherNonTerminalFirst = otherNonTermainal.first;
                    }
                    else otherNonTerminalFirst = first(otherNonTermainal);
                    nonTermainal.first.addAll(otherNonTerminalFirst);
                    if (otherNonTerminalFirst.contains("ε")) {
                        return true;
                    }
                }
            }
            
        }
        return false;
    }

    public void printGrammer() throws IOException{
        for(NonTermainal nonTermainal : nonTermainals){
            nonTermainal.print(writer);
        }
    }



    public static void main(String[] args) throws IOException{
        BufferedWriter clear = new BufferedWriter(new FileWriter("output.txt"));
        clear.write("");
        clear.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
        Grammer grammer = new Grammer("input.txt", writer);
        grammer.printGrammer();
        grammer.printFirst();
        writer.close();
    }
}
