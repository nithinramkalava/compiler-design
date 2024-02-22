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

        Grammar grammer = new Grammar("input.txt", writer);
        grammer.printGrammer();

        FirstAndFollow firstAndFollow = new FirstAndFollow(grammer, writer);
        firstAndFollow.printFirst();
        firstAndFollow.printFollow();
        
        writer.close();
    }
}
