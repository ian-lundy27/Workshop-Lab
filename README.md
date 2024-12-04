"# Workshop-Lab" 

# **Authors**
Michael Considine, Ian Lundy, Michael Connors at Fairfield University

# **Articles used for testing:**

Undergraduate Rankings.txt: https://www.fairfield.edu/news/archive/2024/september/enrollment-success-with-strong-us-news-world-report-rankings.html

Alumni Beer.txt: https://www.fairfield.edu/news/archive/2024/october/elicit-brewing-company-partnership.html

AI-Education Problem.txt: https://thinkspace.fairfield.edu/post/722199482407731200/the-ai-education-problem-the-solutions-right-in

Energy Solution.txt: https://e360.yale.edu/features/why-nuclear-power-must-be-part-of-the-energy-solution-environmentalists-climate

Not the Answer.txt: https://www.oneearth.org/the-7-reasons-why-nuclear-energy-is-not-the-answer-to-solve-climate-change/

Advantages and Challenges.txt: https://www.energy.gov/ne/articles/advantages-and-challenges-nuclear-energy

Baseball Gambling History.txt: https://www.cbssports.com/mlb/news/baseball-gambling-scandals-padres-tucupita-marcano-joins-shohei-ohtanis-interpreter-pete-rose-black-sox/

Gambling Bans.txt: https://www.mlb.com/news/mlb-announces-sports-betting-violation-suspensions

Ohtani Gambling.txt: https://www.rollingstone.com/culture/culture-features/shohei-ohtani-pete-rose-baseball-gambling-history-1234997068/

# **Sentiment analysis**

The words used and the values assigned to them used to evaluate the positivity/negativity of articles are stored in lexicon_scores.txt.

The CSV of modifying words used is stored under modifiers.csv. The list was sourced from files from https://mpqa.cs.pitt.edu/lexicons/effect_lexicon/ and reduced to a single file.

This project parses the article sentence by sentence to analyze the attitude of the article. It attempts to keep track of whether the sentence has a positive or negative attitude towards a given word: for instance, if the sentence is negative towards a negative word, the word is treated as positive. The sum of these weighted scores is divided by the number of words of the article, giving the final averaged score.

Sentence attitude towards any given word is tracked with a boolean that is flipped upon encountering any modifying word that would reverse the meaning of the sentence.

# **Overview**

This project is designed to read the text of articles, and to gather multiple pieces of data, such as the word count, appearances of each word, list of all words, and list of all words without stop words (and, or, etc). Three articles are attached under library -> nuclear-power. There are also two other topics under the library folder. There are five total classes, Topic, Article, SentimentAnalysis, ReadFiler, and Main.

![alt_text](UML%20Diagram.png)

### Topic

The topic class takes a file directory and extracts all the files inside into a linked list. From there it makes an Article object with each of the files in that list. It also has methods to analyze the Articles:

*addFileToDir* Adds a new text file to a chosen directory

*newTopic* Creates a new topic folder in the library

### Article

The Article class makes the article into a string file, where other methods parse.

*parseContent*: Skims through the article and removes all characters except for letters and numbers, this makes a list of all words in the file without anything else. Removal exceptions for words like x-ray and U.S.A, so that they keep their hyphens and periods respectively.

*buildWordList*: Breaks the parsed string apart into an array consisting of every word.

*removeStopWords*: Makes an array list of all words, excluding the stop words listed in the "stopwords.txt" file.

### SentimentAnalysis (Class)
The SentimentAnalysis class is designed to take in a given Article, and from there analyze the sentiment of the text in the Article to determine if it has a positive or negative tone.

### ReadFiler
This class is designed to take in a file, and uses a buffered reader to convert all the text in the file into a String. *filepathToString* converts the file path into a File variable type, and from there *fileToString* reads the File to retrieve a String of all the text.

### Main
Makes instances of the topic class for each of topics in the /library directory, and creates a basic user interface to interact and fetch information from the articles. Also provides an interface to add new topics, as well as new articles. Rediscovers the contents of /library after adding a new topic/article to keep the runtime of the application up to date. 

### Future Refactoring
