import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class TopicModelerGUI extends JFrame {
	
	private File Stopwords;
	private List<String> words;
	private List<String> stopwords;
	int score = 0;
	
    private JPanel homeScreen, mainScreen, settingsScreen;
    private JButton beginButton, fileButton1, fileButton2, compareButton, resetButton, homeButton, settingsButton, backButton;
    private JLabel descriptionLabel, label1, label2, settingsLabel;
    private File selectedFile1, selectedFile2;
    private JSlider slider;

    public TopicModelerGUI() {
        setTitle("Topic Modeler");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

      //Create home screen 
        homeScreen = new JPanel();
        homeScreen.setLayout(new BorderLayout());
        descriptionLabel = new JLabel("<html><div style='text-align: center; font-size: 30pt; display: table-cell; vertical-align: middle;'>Welcome to Topic Modeler!</div><br>"
                + "<div style='text-align: center; font-size: 15pt;'>This program compares the similarity between two text files,<br>Simply choose your files then press the compare button to see the result.</div><br><br>"
                + "<div style='text-align: center; font-size: 15pt;'>Authors: Cillian, Mo, Noor, Walid.</div></html>");
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        beginButton = new JButton("Begin");
        beginButton.addActionListener(new BeginButtonListener());
        beginButton.setBackground(Color.GREEN);
        beginButton.setFocusable(false);
        homeScreen.add(descriptionLabel, BorderLayout.CENTER);
        homeScreen.add(beginButton, BorderLayout.SOUTH);

        //Create main screen
        mainScreen = new JPanel();
        mainScreen.setLayout(new BorderLayout());
   
        // Initialize GUI components for main screen
        fileButton1 = new JButton("📄1");
        //change font size
        Font buttonFont5 = fileButton1.getFont();
        fileButton1.setFont(buttonFont5.deriveFont(buttonFont5.getSize() + 50f));
        //colour
        fileButton1.setBackground(Color.WHITE);
        fileButton1.addActionListener(new FileButtonListener1());
        fileButton1.setFocusable(false);
        fileButton2 = new JButton("📄2");
        Font buttonFont6 = fileButton2.getFont();
        fileButton2.setFont(buttonFont6.deriveFont(buttonFont6.getSize() + 50f));
        fileButton2.setBackground(Color.WHITE);
        fileButton2.addActionListener(new FileButtonListener2());
        fileButton2.setFocusable(false);
        
        compareButton = new JButton("🔍");
        Font buttonFont1 = compareButton.getFont();
        compareButton.setFont(buttonFont1.deriveFont(buttonFont1.getSize() + 50f));
        compareButton.setBackground(Color.WHITE);
        compareButton.addActionListener(new CompareButtonListener());
        compareButton.setFocusable(false);
        
        resetButton = new JButton("🗑️");
        Font buttonFont2 = resetButton.getFont();
        resetButton.setFont(buttonFont2.deriveFont(buttonFont2.getSize() + 50f));
        resetButton.setBackground(Color.WHITE);
        resetButton.addActionListener(new ResetButtonListener());
        resetButton.setFocusable(false);
        
        homeButton = new JButton("🏠");
        Font buttonFont3 =homeButton.getFont();
        homeButton.setFont(buttonFont3.deriveFont(buttonFont3.getSize() + 50f));
        homeButton.setBackground(Color.WHITE);
        homeButton.addActionListener(new HomeButtonListener());
        homeButton.setFocusable(false);
        
        // settings
        settingsButton = new JButton("   ⚙️");
        Font buttonFont4 = settingsButton.getFont();
        settingsButton.setFont(buttonFont4.deriveFont(buttonFont4.getSize() + 50f));
        
        settingsButton.setBackground(Color.WHITE);
        settingsButton.addActionListener(new SettingsButtonListener());
        settingsButton.setFocusable(false);
        
        label1 = new JLabel("File 1: ");
        label2 = new JLabel("File 2: ");
        //Edit text size
        Font currentFont = label1.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() + 4f);
        label1.setFont(newFont);
        label2.setFont(newFont);

        //Create settings screen 
        settingsScreen = new JPanel();
        settingsScreen.setLayout(new BorderLayout());
        settingsLabel = new JLabel("<html><br><div style='text-align: center; font-size: 24pt; display: table-cell; vertical-align: middle;'>Welcome to Settings</div><br>"
                + "<div style='text-align: center;'>Select the number of words you wish to search for</div><br>");
        settingsLabel.setHorizontalAlignment(JLabel.CENTER);
        //Create slider for settings
        slider = new JSlider(0,25,10);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(1);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> {
		slider.getValue();});
        backButton = new JButton("Back");
        backButton.setBackground(Color.RED);
        backButton.addActionListener(new BackButtonListener());
        backButton.setFocusable(false);
        settingsScreen.add(settingsLabel, BorderLayout.NORTH);
        settingsScreen.add(slider, BorderLayout.CENTER);
        settingsScreen.add(backButton, BorderLayout.SOUTH);

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new GridLayout(4, 2));
        filePanel.add(label1);
        filePanel.add(fileButton1);
        filePanel.add(label2);
        filePanel.add(fileButton2);
        filePanel.add(compareButton);
        filePanel.add(resetButton);
        filePanel.add(homeButton); 
        filePanel.add(settingsButton);

        mainScreen.add(filePanel, BorderLayout.CENTER);

        // Initially display home screen
        getContentPane().add(homeScreen, BorderLayout.CENTER);
    }

    // Action listener for "Begin" button to switch to main panel
    private class BeginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll(); // Remove current content
            getContentPane().add(mainScreen, BorderLayout.CENTER); // Add main panel
            revalidate(); // Refresh frame
            repaint();
        }
    }
    
    // Action listener for "Choose File 1" button
    private class FileButtonListener1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile1 = fileChooser.getSelectedFile();
                label1.setText("File 1: " + selectedFile1.getName());
                
            }
        }
    }

    // Action listener for "Choose File 2" button
    private class FileButtonListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile2 = fileChooser.getSelectedFile();
                label2.setText("File 2: " + selectedFile2.getName());
            }
        }
    }

    // Action listener for "Compare" button
    private class CompareButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (selectedFile1 == null || selectedFile2 == null) {
                JOptionPane.showMessageDialog(null, "Please select both files.");
                return;
            }

            // Call algorithm to calculate the score
            int score = scoreAlgorithm(selectedFile1.getAbsolutePath(), selectedFile2.getAbsolutePath());
            System.out.println("Score: " + score);
            
            JOptionPane.showMessageDialog(null, "Similarity Score: " + score + "/" + slider.getValue());
        }
    } 

    // Action listener for "Reset" button
    private class ResetButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selectedFile1 = null;
            selectedFile2 = null;
            label1.setText("File 1: ");
            label2.setText("File 2: ");
        }
    }

    // Action listener for "Home" button
    private class HomeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll(); // Remove current content
            getContentPane().add(homeScreen, BorderLayout.CENTER); // Add home panel
            revalidate(); // Refresh frame
            repaint();
        }
    }
    
    // Action listener for "Settings" button
    private class SettingsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll(); // Remove current content
            getContentPane().add(settingsScreen, BorderLayout.CENTER); // Add settings screen
            revalidate(); // Refresh frame
            repaint();
        }
    }
    
     // Action listener for "Back" button
    private class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll(); // Remove current content
            getContentPane().add(mainScreen, BorderLayout.CENTER); // Add main panel
            revalidate(); // Refresh frame
            repaint();
        }
    }
    
    public int scoreAlgorithm(String file1, String file2) {
        List<String> processedFile1 = file_processor(file1);
        List<String> processedFile2 = file_processor(file2);
        score = 0; //resets score

        // Find the top most common words in each file
        Map<String, Integer> topWordsFile1 = findTopWords(processedFile1);
        Map<String, Integer> topWordsFile2 = findTopWords(processedFile2);
        
        writeTopWordsToFile("topWordsFile1.txt", topWordsFile1);
        writeTopWordsToFile("topWordsFile2.txt", topWordsFile2);
        
        // Compare the sets of top most common words and calculate score.
        int score = compareTopWords(topWordsFile1, topWordsFile2);
        
        return score;
    }
    
    // https://stackoverflow.com/questions/65633941/how-to-list-the-top-n-frequent-words-in-a-string-array-in-java
    private Map<String, Integer> findTopWords(List<String> words) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        
        // Count the frequency of each word
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        
        // Sort the map by values (word frequencies) in descending order
        Map<String, Integer> sortedWordFrequency = wordFrequency.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        // Extract the top most common words
        Map<String, Integer> topWords = new LinkedHashMap<>();
        int count = 0;
        int topCommonWords = slider.getValue();
        for (Map.Entry<String, Integer> entry : sortedWordFrequency.entrySet()) {
            if (count >= topCommonWords) {
                break;
            }
            topWords.put(entry.getKey(), entry.getValue());
            count++;
        }
        
        return topWords;
    }

    private int compareTopWords(Map<String, Integer> topWordsFile1, Map<String, Integer> topWordsFile2) {
        int score = 0;
        
        // Compare the sets of top 10 words and calculate score
        for (Map.Entry<String, Integer> entry : topWordsFile1.entrySet()) {
            if (topWordsFile2.containsKey(entry.getKey())) {
                score++; // Increment score if the word is present in both sets
            }
        }
        
        return score;
    }
    
    public List<String> file_processor(String filename) {
		 File file = new File(filename);
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
		return words;
	} 
    
    // https://stackoverflow.com/questions/37527177/writing-hashmap-contents-to-the-file
    private void writeTopWordsToFile(String filename, Map<String, Integer> topWords) {
    	
        try {
            List<String> lines = topWords.entrySet().stream().map(entry -> entry.getKey() + " : " + entry.getValue()).collect(Collectors.toList());
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
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