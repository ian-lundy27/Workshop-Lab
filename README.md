"# Workshop-Lab" 

This project is designed to read the text of articles, and to gather multiple pieces of data, such as the word count, appearances of each word, list of all words, and list of all words without stop words (and, or, etc). Three articles are attached under library -> nuclear-power. There are three classes, main included.

Articles used for testing:

fairfieldArticle1: https://www.fairfield.edu/news/archive/2024/september/enrollment-success-with-strong-us-news-world-report-rankings.html

fairfieldArticle2: https://www.fairfield.edu/news/archive/2024/october/elicit-brewing-company-partnership.html

fairfieldArticle3: https://thinkspace.fairfield.edu/post/722199482407731200/the-ai-education-problem-the-solutions-right-in

nuclearArticle1.txt: https://e360.yale.edu/features/why-nuclear-power-must-be-part-of-the-energy-solution-environmentalists-climate

nucleararticle2.txt: https://www.oneearth.org/the-7-reasons-why-nuclear-energy-is-not-the-answer-to-solve-climate-change/

nuclearArticle3.txt: https://www.energy.gov/ne/articles/advantages-and-challenges-nuclear-energy

baseballGamblingHistory.txt: https://www.cbssports.com/mlb/news/baseball-gambling-scandals-padres-tucupita-marcano-joins-shohei-ohtanis-interpreter-pete-rose-black-sox/

gamblingBans.txt: https://www.mlb.com/news/mlb-announces-sports-betting-violation-suspensions

ohtaniGambling.txt: https://www.rollingstone.com/culture/culture-features/shohei-ohtani-pete-rose-baseball-gambling-history-1234997068/

#Topic#

The topic class takes a file directory and extracts all of the files inside into a linked list. From there it makes an Article object with each of the files in that list.

#Article#

The Article class makes the article into a string file, where other methods parse and analyze the content.

-parseContent: Skims through the article and removes all characters except for letters and numbers, this makes a list of all words in the file without anything else. Removal exceptions for words like x-ray and U.S.A, so that they keep their hyphens and periods respectively.

-buildWordList: Breaks the parsed string apart into a array consisting of every word.

-findWordFrequency: Builds a hash map to keep count of how many times each word in the article appears.

-sortFrequencyList: Builds a linked list with all of the words sorted by number of appearances. Words that appear the same amount of times are sorted alphabetically.

-getNumStatements: Returns the number of punctuation marks in the article using the parsed content string.

-getArticleLength: Returns the total number of words in the article.

-removeStopWords: Makes an array list of all words, excluding the stop words listed in the "stopwords.txt" file.

-statementCount: Count periods, question marks, and exclamation marks to return the number of sentences.