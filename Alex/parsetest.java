
import java.util.Collection;
import java.util.List;
import java.io.StringReader;

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
	    // You could also create a tokenizer here (as below) and pass it
	    // to DocumentPreprocessor
	    for (List<HasWord> sentence : new DocumentPreprocessor(filename)) 
	    {
			Tree parse = lp.apply(sentence);
			Tree flattened = parse.flatten();

			System.out.println(flattened.toString());
			System.out.println();
			flattened.pennPrint();
			System.out.println();
			
			System.out.println(parse.toString());
			System.out.println();	
			parse.pennPrint();
			System.out.println();

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