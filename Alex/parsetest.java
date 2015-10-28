
import java.util.Collection;
import java.util.List;
import java.io.StringReader;
import java.lang.reflect.Array;


import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

class parsetest {
	public static void main(String[] args)
	{
		String filename = "single.txt";
		String parserModel = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
		LexicalizedParser lp = LexicalizedParser.loadModel(parserModel);

		TreebankLanguagePack tlp = lp.treebankLanguagePack(); // a PennTreebankLanguagePack for English
	    GrammaticalStructureFactory gsf = null;
	    if (tlp.supportsGrammaticalStructures()) 
	    {
	      gsf = tlp.grammaticalStructureFactory();
	    }

	    for (List<HasWord> sentence : new DocumentPreprocessor(filename)) 
	    {
			Tree parse = lp.apply(sentence);
			Tree flattened = parse.flatten();

			if(Array.getLength(args) > 0)
			{
				System.out.println();

				switch(args[0])
				{
					case "tree" : //print a formatted tree
						parse.pennPrint();
						break;

					case "string" : //print the tree as string
						System.out.println(parse.toString());
						break;

					case "array" : //print all elements of tree converted to array
						Object[] parsearray = parse.toArray();
						for(Object x : parsearray)
						{
							System.out.println(x.toString());
							System.out.println();
						}
						break;

					case "ftree" : //as above but with flattened tree
						flattened.pennPrint();
						break;

					case "fstring" : //as above but with flattened tree
						System.out.println(flattened.toString());
						break;

					case "farray" : //as above but with flattened tree
						Object[] flatarray = flattened.toArray();
						for(Object x : flatarray)
						{
							System.out.println(x.toString());
							System.out.println();
						}
						break;

				}
			}

/*			if (gsf != null) 
			{
				GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
				Collection tdl = gs.typedDependenciesCCprocessed();
				System.out.println(tdl);
				System.out.println();
			}*/
   		}
    
	}
}