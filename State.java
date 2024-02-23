import java.util.*;

public class State {
    int stateNum;
    List<NonTerminal> nonTerminals;
    Grammar grammar;

    public State(Grammar grammar) {
        this.nonTerminals = new ArrayList<>();
        this.grammar = grammar;
    }

    public void calculateRestOfTheState() {
        NonTerminal firstNonTerminal = nonTerminals.get(0);
        for(String production: firstNonTerminal.productions){
            NonTerminal newNonTerminal = new NonTerminal();
            int dotPos = production.indexOf(".");
            if (dotPos == production.length() - 1) continue;
            char nextChar = production.charAt(dotPos + 1);
            if ((int) nextChar < 65 || (int) nextChar > 96){
                if (nextChar == 'i'){
                    try{
                        char secondChar = production.charAt(dotPos + 2);
                        if (secondChar == 'd') 
                            firstNonTerminal.firstCLR = firstNonTerminal.firstCLR + " " + nextChar + secondChar;
                        else throw new Exception("blah");
                    } catch (Exception exception) {
                        firstNonTerminal.firstCLR = firstNonTerminal.firstCLR + " " + nextChar;
                    }
                }
                else 
                    firstNonTerminal.firstCLR = firstNonTerminal.firstCLR + " " + nextChar;
            } else {
                for(NonTerminal otherNonTerminal: grammar.nonTerminals){
                    if (otherNonTerminal.nonTerminal.equals(nextChar)){
                        Set<String> otherNonTerminalFirst;
                        if (otherNonTerminal.first.size() > 0){
                            otherNonTerminalFirst = otherNonTerminal.first;
                        } else {
                            otherNonTerminalFirst = grammar.first(otherNonTerminal);
                        }
                        for(String first: otherNonTerminalFirst){
                            if (!first.equals("ε"))
                                firstNonTerminal.firstCLR = firstNonTerminal.firstCLR + " " + first;
                        }
                    }
                }
            }
        }
        
    }

    public void printState() {
        System.out.println("State " + stateNum + ":");
        for(NonTerminal nonTerminal: nonTerminals){
            System.out.println(nonTerminal.nonTerminal + ":" + nonTerminal.productions.toString()+", "+nonTerminal.firstCLR);
        }
    }
}
