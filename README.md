# readability 

<p>reads a plain text file. Gives the number of words, sentences, characters, syllables and polysyllables in the file. 
  Available estimation test - Automated readability index (ARI), Flesch–Kincaid readability test (FK), Simple Measure of Gobbledygook (SMOG),  Coleman–Liau index (CL). 
  Age calculated accoring to ARI.</P>

<span>*requires file input as <em>command line argument</em></span>

<p>command-line arguments:</p>
<pre><code>% javac Main.java </code></pre>
<pre><code>% java Main Readability.txt</code></pre>
<p>output:</p>
<pre>
The text is:
Readability is the ease with which a reader can understand a written text. In natural language, the readability of text depends on its content and its presentation. Researchers have used various factors to measure readability. Readability is more than simply legibility, which is a measure of how easily a reader can distinguish individual letters or characters from each other. Higher readability eases reading effort and speed for any reader, but it is especially important for those who do not have high reading comprehension. In readers with poor reading comprehension, raising the readability level of a text from mediocre to good can make the difference between success and failure
Words: 108
Sentences: 6
Characters: 580
Syllables: 197
Polysyllables: 21
Enter the score you want to calculate (ARI, FK, SMOG, CL, all): 
all
Automated Readability Index: 12.86 (about 18-year-olds).
Flesch–Kincaid readability tests: 12.95 (about 18-year-olds).
Simple Measure of Gobbledygook: 13.82 (about 22-year-olds).
Coleman–Liau index: 14.13 (about 20-year-olds).
This text should be understood in average by 13.44-year-olds.
</pre>
</code>
