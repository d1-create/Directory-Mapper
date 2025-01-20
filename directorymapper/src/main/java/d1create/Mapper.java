package d1create;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Mapper {
    public static void main(String[] args) {
        Mapper mapper = new Mapper(args);
    }

    int depth = 1;
    boolean should_map = true;
    String menu = "Usage: Mapper [depth]";

    public Mapper(String[] args){
        //find arguements if available and check if none available then should_map should be true
        if(args.length!=0){
            for(String s: args){
                if(s.equals("-h") || s.equals("--help")){
                    System.out.println(menu);
                    should_map = false;
                }
                else if(tryParseInt(s)){
                    depth = Integer.parseInt(s);
                }
                else{
                    System.out.println("Could not recognise input. Please try again");
                    System.out.println(menu);
                    should_map = false;
                }
            }
        }

        if(should_map){
            plotMap();
        }
    }

    public void plotMap(){
        System.out.println("Plotting directory");
         // path of the specific directory 
        String directoryPath = System.getProperty("user.dir");
        System.out.println(directoryPath);
        // create a Path object for the specified directory
        Path directory = Paths.get(directoryPath);

        recursiveSearch(directory, depth);
    }

    private void recursiveSearch(Path directory, int depth) {
        if (depth < 0) {
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    System.out.println("--".repeat(this.depth - depth) + file.toAbsolutePath());
                    recursiveSearch(file, depth - 1);
                } else {
                    System.out.println("--".repeat(this.depth - depth) + file.toAbsolutePath());
                }
            }
        } catch (IOException e) {
            System.out.println("Your directory is inaccessible");
            e.printStackTrace();
        }
    }


    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}