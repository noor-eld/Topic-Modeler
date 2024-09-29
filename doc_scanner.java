import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class doc_scanner {
	
	private File file;
	private File Stopwords;
	private List<String> words;
	private List<String> stopwords;

	public doc_scanner(String filename) {
		this.file = new File(filename);
		this.Stopwords = new File("StopWords.txt");
		this.words = new ArrayList<>();
		this.stopwords = new ArrayList<>();
		try {
			Scanner scan = new Scanner(file);
			Scanner scanStopwords = new Scanner(Stopwords);
			while (scanStopwords.hasNextLine()) {
				String stopword = scanStopwords.nextLine().trim().toLowerCase();
				stopwords.add(stopword);
			}
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] lineToWords = line.split("\\s+");
				for(String word : lineToWords) {
					word = word.toLowerCase();
					word = word.replaceAll("[^a-zA-Z0-9]+", "");
					boolean isStopword = false;
					for(String stopword : stopwords) {
						if(word.equals(stopword)) {
							isStopword = true;
							break;
						}
					}
					if(!isStopword) {
						words.add(word);				
					}
				}
			}
			scan.close();
			scanStopwords.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<String> getWords(){
		return words;
	}
	public File getStopwords() {
		return Stopwords;
	}
	
}
