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

CSV of words and their polarity used is stored under word-sentiments.csv. The list was sourced from https://mpqa.cs.pitt.edu/lexicons/subj_lexicon/ and reduced to a csv of only word and polarity.

# **Overview**

This project is designed to read the text of articles, and to gather multiple pieces of data, such as the word count, appearances of each word, list of all words, and list of all words without stop words (and, or, etc). Three articles are attached under library -> nuclear-power. There are also two other topics under the library folder. There are three total classes, Topic, Article, and Main.

![alt text][UML]

[UML]: https://github.com/ian-lundy27/Workshop-Lab/blob/main/UML%20Diagram.png

### Topic

The topic class takes a file directory and extracts all of the files inside into a linked list. From there it makes an Article object with each of the files in that list.

### Article

The Article class makes the article into a string file, where other methods parse and analyze the content, such as retrieving the number of words, statements, individual words, and removing stop words.

*parseContent*: Skims through the article and removes all characters except for letters and numbers, this makes a list of all words in the file without anything else. Removal exceptions for words like x-ray and U.S.A, so that they keep their hyphens and periods respectively.

*buildWordList*: Breaks the parsed string apart into a array consisting of every word.

*findWordFrequency*: Builds a hash map to keep count of how many times each word in the article appears.

*sortFrequencyList*: Builds a linked list with all of the words sorted by number of appearances. Words that appear the same amount of times are sorted alphabetically.

*getNumStatements*: Returns the number of punctuation marks in the article using the parsed content string.

*getArticleLength*: Returns the total number of words in the article.

*removeStopWords*: Makes an array list of all words, excluding the stop words listed in the "stopwords.txt" file.

*statementCount*: Count periods, question marks, and exclamation marks to return the number of sentences.

### Main

Makes instances of the topic class for each of the three topics, and creates a basic user interface to interact and fetch information from the articles.