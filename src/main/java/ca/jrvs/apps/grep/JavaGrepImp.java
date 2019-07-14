package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepImp implements JavaGrep {
    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        for (File file : listFiles(this.getRootPath())) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) throws IOException {
        return Files.walk(Paths.get(rootDir))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());


    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        return br.lines().collect(Collectors.toList());
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(this.getRegex());
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        Files.write(Paths.get(this.getOutFile()), lines);
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
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
