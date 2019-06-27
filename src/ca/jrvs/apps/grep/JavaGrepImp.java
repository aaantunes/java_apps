package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaGrepImp implements JavaGrep {

    @Override
    public void process() throws IOException {
        //call listFiles() and store as list<File>
        //pass fileList to readLines()
        //call contains pattern for every

    }

    //use lambda/Steam API's
    @Override
    public List<File> listFiles(String rootDir) throws IOException{
//        List<File> fileList = new ArrayList<>();
            return Files.walk(Paths.get(rootDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
    }

    //use lambda/Steam API's or BufferedReader and FileReader
    @Override
    public List<String> readLines(File inputFile) {

        return null;
    }

    @Override
    public boolean containsPattern(String line) {
        boolean flag = false;

        Pattern checkRegex = Pattern.compile(getRegex());
        Matcher regexMatcher = checkRegex.matcher(line);

        // Cycles through positive matches in line and prints to screen
        // Checks string isnt empty and trims any whitespace
        while (regexMatcher.find()) {
            if (regexMatcher.group().length() != 0) {
                flag = true;
                System.out.println(regexMatcher.group().trim());
            }
        }
        return flag;
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {

    }

    @Override
    public String getRootPath() {
        return null;
    }

    @Override
    public void setRootPath(String rootPath) {

    }

    @Override
    public String getRegex() {
        return null;
    }

    @Override
    public void setRegex(String regex) {

    }

    @Override
    public String getOutFile() {
        return null;
    }

    @Override
    public void setOutFile(String outFile) {

    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: regex rootPath outFile");
        }
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
