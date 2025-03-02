/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package bec.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

//import simula.compiler.utilities.Global;
//import simula.compiler.utilities.Token;
//import simula.compiler.utilities.Option;
//import simula.compiler.utilities.Util;

public final class Scanner { 
    
    /**
     * The pushBack stack
     */
    private Stack<Character> puchBackStack=new Stack<Character>();

    /**
     * The current source file reader;
     */
    private Reader sourceFileReader;


    //********************************************************************************
    //*** CONSTRUCTORS: Scanner
    //********************************************************************************
	/**
	 * Constructs a new SimulaScanner that produces Items scanned from the specified
	 * source.
	 * 
	 * @param reader The character source to scan
	 */
	public Scanner(final Reader reader) {
		this.sourceFileReader = reader;
	}

    //********************************************************************************
    //*** Close
    //********************************************************************************
	/**
	 * Close the scanner.
	 */
	void close() {
		try {
			sourceFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			Util.IERR("");
		}
		sourceFileReader=null;
	}

    //********************************************************************************
    //**	                                                                 nextToken 
    //********************************************************************************
	public Object nextToken() throws IOException {
    	while(true)	{
//    		Token.lineNumberBeforeScanBasic = Global.sourceLineNumber;

    		if(Character.isLetter(getNext())) return(scanName());
    		switch(current) {
	            case '0':case '1':case '2':case '3':case '4':
	            case '5':case '6':case '7':case '8':case '9':return(scanNumber());
	            case '\n':			/* NL (LF) */
	            case ' ':
	            case '\r':			/* CR */
	            	break;
	            default:
//	            	System.out.println("Scanner: char "+(char)current);
	            	return Character.valueOf((char) current);
    		}
    	}
    }

    //********************************************************************************
    //**	                                                                scanNumber 
    //********************************************************************************
    private Integer scanNumber() throws IOException {
       	StringBuilder number=new StringBuilder();
    	
    	number.append((char)current);
    	while (Character.isDigit(getNext()))
    		number.append((char)current);
    	pushBack (current);
//    	System.out.println("Scanner: Number "+number);
    	return Integer.decode(number.toString());
    }

    //********************************************************************************
    //**					                                                  scanName
    //********************************************************************************
    /**
     * Scan identifier or reserved name.
     * <pre>
     * Reference-Syntax:
     * 
     *    identifier = letter  { letter  |  digit  |  _  }
     *    
     *    
     * End-Condition: current is last character of construct
     *                getNext will return first character after construct
     * </pre>
     * @return the resulting identifier
     * @throws IOException 
     */
    private String scanName() throws IOException {
    	StringBuilder name=new StringBuilder();
//    	Util.ASSERT(Character.isLetter((char)(current)),"Expecting a Letter");
    	name.append((char)current);
    	while ((Character.isLetter(getNext()) || Character.isDigit(current) || current == '_'))
    		name.append((char)current);
    	pushBack(current);
//    	System.out.println("Scanner: Name "+name);
    	return(name.toString());
    }
	
    
    //********************************************************************************
    //**	                                                                 UTILITIES 
    //********************************************************************************
	
	/**
	 * The current character read.
	 */
    private int current;
    
    /**
     * Returns next input character.
     * @return next input character
     * @throws IOException 
     */
    private int getNext() throws IOException {
    	if(puchBackStack.empty()) {
    		int c=sourceFileReader.read();
    		if(c=='\n') Global.sourceLineNumber++;
//    		else if(c<0) { EOF_SEEN=true; c=EOF_MARK; }
    		else if(c<0) throw new RuntimeException("EOF");
    		else if(c<32) c=' '; // Whitespace
    		current=c;
    	} else current=puchBackStack.pop();
    	return(current);
    }

    /**
     * Push a character onto the puchBackStack.
     * @param chr character to be pushed
     */
    private void pushBack(final int chr) {
	    // push given value back into the input stream
	    puchBackStack.push((char)chr);
	    current=' ';
    }
 
}
