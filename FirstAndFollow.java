import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FirstAndFollow {

    List<NonTerminal> nonTerminals;
    BufferedWriter writer;

    public FirstAndFollow(Grammar grammar, BufferedWriter writer) {
        this.nonTerminals = grammar.nonTerminals;
        this.writer = writer;
        nonTerminals.get(0).follow.add("$");
    }

    public void printFirst() throws IOException{
        writer.newLine();
        for(NonTerminal nonTerminal: nonTerminals){
            nonTerminal.first.addAll(first(nonTerminal));
            writer.write("first("+nonTerminal.nonTerminal+"): " + nonTerminal.first.toString());
            writer.newLine();
        }
    }

    public Set<String> first(NonTerminal nonTerminal){
    
        for(String prod: nonTerminal.productions){
            int charPos = -1;
            boolean epsilon = true;
            try {
                while (epsilon) {
                    charPos ++;
                    char currChar = prod.charAt(charPos);
                    epsilon = firstSuccessor(nonTerminal, currChar, prod, charPos);
                }
            } catch (Exception exception) {
               // ignore 
            }
        }
        return nonTerminal.first;
    }

    public boolean firstSuccessor(NonTerminal nonTerminal, char firstChar, String prod, int pos){
        if ((int) firstChar < 65 || (int) firstChar > 96){
            if (firstChar == 'i'){
                try{
                    char secondChar = prod.charAt(pos + 1);
                    if (secondChar == 'd') 
                        nonTerminal.first.add(firstChar+"d");
                    else throw new Exception("blah");
                } catch (Exception exception) {
                    nonTerminal.first.add(Character.toString(firstChar));
                }
            }
            else 
                nonTerminal.first.add(Character.toString(firstChar));
        } else {
            for(NonTerminal otherNonTerminal: nonTerminals){
                if (otherNonTerminal.nonTerminal.equals(firstChar)){
                    Set<String> otherNonTerminalFirst;
                    if (otherNonTerminal.first.size() > 0){
                        otherNonTerminalFirst = otherNonTerminal.first;
                    }
                    else otherNonTerminalFirst = first(otherNonTerminal);
                    nonTerminal.first.addAll(otherNonTerminalFirst);
                    if (otherNonTerminalFirst.contains("ε")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Set<String> follow(NonTerminal nonTerminal){
        if (nonTerminal.followCalculated) return nonTerminal.follow;

        for( NonTerminal otherNonTerminal: nonTerminals) {
            for (String prod: otherNonTerminal.productions){
                if(prod.contains(nonTerminal.nonTerminal.toString())){
                    int index = prod.indexOf(nonTerminal.nonTerminal.toString());
                    if (index == prod.length() -  1) {
                        nonTerminal.follow.addAll(follow(otherNonTerminal));
                        nonTerminal.followCalculated = true;
                    } else {
                        boolean epsilon = true;
                        index ++;
                        while (epsilon && index < prod.length()) {
                            NonTerminal temp = new NonTerminal();
                            temp.productions.add(Character.toString(prod.charAt(index)));
                            Set<String> otherNonTerminalFirst = first(temp);
                            if (otherNonTerminalFirst.contains("ε")) {
                                otherNonTerminalFirst.remove("ε");
                            } else epsilon = false;
                            nonTerminal.follow.addAll(otherNonTerminalFirst);
                            index ++;
                        }
                        if (epsilon) {
                            nonTerminal.follow.addAll(follow(otherNonTerminal));
                        }
                        nonTerminal.followCalculated = true;
                    }
                }
            }
        }
        nonTerminal.followCalculated = true;
        return nonTerminal.follow;
    }

    public void printFollow() throws IOException{
        writer.newLine();
        for(NonTerminal nonTerminal: nonTerminals){
            nonTerminal.follow.addAll(follow(nonTerminal));
            writer.write("follow("+nonTerminal.nonTerminal+"): " + nonTerminal.follow.toString());
            writer.newLine();
        }
    }
}
