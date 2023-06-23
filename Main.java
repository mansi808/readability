import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader(args[0]);
        Scanner scanner = new Scanner(System.in);

        //printing file content
        System.out.println("The text is:");
        System.out.println(fileReader.fileAsString);

        System.out.println("Words: "+fileReader.getWords());
        System.out.println("Sentences: "+fileReader.getSentences());
        System.out.println("Characters: "+fileReader.getCharacters());
        System.out.println("Syllables: "+fileReader.getSyllables());
        System.out.println("Polysyllables: "+fileReader.getPolysyllables());
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String grading_system = scanner.next();

        if (grading_system.equals("ARI") || grading_system.equals("all")) System.out.println("Automated Readability Index: "+fileReader.getARIScore() + " (about "+fileReader.age(fileReader.getARIScore())+"-year-olds).");;
        if (grading_system.equals("FK") || grading_system.equals("all"))  System.out.println("Flesch–Kincaid readability tests: "+ fileReader.getFKScore() + " (about "+fileReader.age(fileReader.getFKScore())+"-year-olds).");
        if (grading_system.equals("SMOG")|| grading_system.equals("all")) System.out.println("Simple Measure of Gobbledygook: "+fileReader.getSMOGScore() + " (about "+fileReader.age(fileReader.getSMOGScore())+"-year-olds).");
        if (grading_system.equals("CL")|| grading_system.equals("all")) System.out.println("Coleman–Liau index: "+ fileReader.getCLScore() + " (about "+fileReader.age(fileReader.getCLScore())+"-year-olds).");
        if (grading_system.equals("all")) fileReader.avg_age();
    }
}

class FileReader {
    String filename;
    String fileAsString;
    private final int words;
    private final int sentences;
    private final int characters;
    private final int syllables;
    private final int polysyllables; //words with more than 2 syllables
    private final double ARIscore;   //Automated readability index
    private final double FKscore;    //Flesch–Kincaid readability test
    private final double SMOGscore;  //Simple Measure of Gobbledygook
    private final double CLscore;    //Coleman–Liau index

    FileReader(String filename) throws IOException {
        this.filename = filename;
        this.fileAsString = readFileAsString();
        this.characters = getCharacters();
        this.words = getWords();
        this.sentences = getSentences();
        this.syllables = getSyllables();
        this.polysyllables = getPolysyllables();
        this.ARIscore = getARIScore();
        this.FKscore = getFKScore();
        this.SMOGscore = getSMOGScore();
        this.CLscore = getCLScore();
    }

    //returns file(plain text --> .txt extension) as a string
    public String readFileAsString() throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

    public int getCharacters() {
        //file string without spaces, newline characters and tabs
        String file_str = fileAsString.replaceAll("[\\s\\t\\n]","");
        char[] CharArr = file_str.toCharArray();
        return CharArr.length;
    }

    public int getWords() {
        String[] WordArr = fileAsString.split("[\\s]+");
        return WordArr.length;
    }

    public int getSentences() {
        String[] SentenceArr = fileAsString.split("[\\.!?]");
        return SentenceArr.length;
    }

    public int getSyllables() {
        int count =0;
        String[] wordArr = fileAsString.split("[\\s]+");
        for (String x: wordArr) {
            x = x.trim()
                    .replaceAll("[Ee]$","") //remove e at end
                    .replaceAll("[AEIOUYaeiouy]{2,}","a") // replace double vowels with single vowels
                    .replaceAll("[^AEIOUYaeiouy]",""); //remve all non vowels
            count += x.length() >0 ? x.length(): 1;
        }
        return count;
    }

    public int getPolysyllables() {
        int count =0;
        String[] wordArr = fileAsString.split("[\\s]+");
        for (String x: wordArr) {
            x = x.trim()
                    .replaceAll("[e]$","") //remove e at end
                    .replaceAll("[AEIOUYaeiouy]{2,}","a")
                    .replaceAll("[^AEIOUYaeiouy]","");
            count += x.length() > 2 ? 1: 0;
        }
        return count;
    }

    //program outputs the difficulty level in age in accordance to Automated readability index
    public int age(double score) {
        int scoring = (int) Math.ceil(score);
        return (scoring+4)!=18 ? scoring+5: scoring+8;
    }

    public double getARIScore() {
        return Double.parseDouble(String.format("%.2f",(4.71 * characters/words) +(0.5 * words/sentences) -21.43));
    }

    public double getFKScore() {
        return Double.parseDouble(String.format("%.2f",0.39 * words / sentences + 11.8 * syllables /words - 15.59));
    }

    public double getSMOGScore() {
        return Double.parseDouble(String.format("%.2f",(1.043 * Math.sqrt((double)polysyllables* 30/sentences) +3.1291)));
    }

    public double getCLScore() {
        double s = ((double)sentences / words) * 100;
        double l = ((double)characters / words) * 100;
        return Double.parseDouble(String.format("%.2f", 0.0588 * l - 0.296 * s - 15.8));
    }

    public void avg_age() {
        double avg_age=  (ARIscore+getFKScore()+getSMOGScore()+CLscore)/4;
        System.out.println(String.format("This text should be understood in average by %.2f-year-olds.",avg_age));
    }

} //class ends