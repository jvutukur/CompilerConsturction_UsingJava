

import java.util.Vector;

public class Lexer {

	private String text;
	private int count = 0;
	private Vector<Token> tokens;
	private static final String[] KEYWORD = { "if", "else", "while", "switch",
			"case", "return", "int", "float", "void", "char", "string",
			"boolean", "true", "false", "print" };
	// Constants; YOU WILL NEED TO DEFINE MORE CONSTANTS
	private static final int zero = 0;
	private static final int b_Or_B = 1;
	private static final int one = 2;
	private static final int twoToSeven = 3;
	private static final int eightToNine = 4;
	private static final int acd_Or_ACD = 5;
	private static final int x_Or_X = 6;
	private static final int dot = 7;
	private static final int e_Or_E = 8;
	private static final int f_Or_F = 9;
	private static final int plus_Or_Minus = 10;
	private static final int $_Or_ = 11;
	private static final int gTow_Or_GToW = 12;
	private static final int yToz_Or_YToZ = 13;
	private static final int singleQuote = 14;
	private static final int doubleQuote = 15;
	private static final int escapeCharacter = 16;
	private static final int delimiter = 17;
	private static final int operatorExceptPlusAndMinus = 18;
	private static final int whiteSpace = 19;
	private static final int allOtherCharecters = 20;

	private static final int error = 25;// error is state number 25

	// 22,23,24 are stop states

	// states table; THIS IS THE TABLE FOR BINARY NUMBERS; YOU SHOLD COMPLETE IT
	private static final int[][] stateTable = {
			{ 1, 14, 7, 7, 7, 14, 14, 13, 14, 14, 24, 14, 14, 14, 15, 19,
					error, 22, 24, 23, error },
			{ 4, 2, 4, 4, error, error, 5, 8, error, error, 24, error, error,
					error, 15, 19, error, 22, 24, 23, error },
			{ 3, error, 3, error, error, error, error, error, error, error, 24,
					error, error, error, 15, 19, error, 22, 24, 23, error },
			{ 3, error, 3, error, error, error, error, error, error, error, 24,
					error, error, error, 15, 19, error, 22, 24, 23, error },
			{ 4, error, 4, 4, error, error, error, error, error, error, 24,
					error, error, error, 15, 19, error, 22, 24, 23, error },
			{ 6, error, 6, 6, 6, 6, error, error, 6, 6, 24, error, error,
					error, 15, 19, error, 22, 24, 23, error },
			{ 6, 6, 6, 6, 6, 6, error, error, 6, 6, 24, error, error, error,
					15, 19, error, 22, 24, 23, error },
			{ 7, error, 7, 7, 7, error, error, 8, 10, error, 24, error, error,
					error, 15, 19, error, 22, 24, 23, error },
			{ 9, error, 9, 9, 9, error, error, error, 10, error, 24, error,
					error, error, 15, 19, error, 22, 24, 23, error },
			{ 9, error, 9, 9, 9, error, error, error, 10, error, 24, error,
					error, error, 15, 19, error, 22, 24, 23, error },
			{ 11, error, 11, 11, 11, error, error, error, error, error, 12,
					error, error, error, 15, 19, error, 22, 24, 23, error },
			{ 11, error, 11, 11, 11, error, error, error, error, error, 24,
					error, error, error, 15, 19, error, 22, 24, 23, error },
			{ 11, error, 11, 11, 11, error, error, error, error, error, 24,
					error, error, error, 15, 19, error, 22, 24, 23, error },
			{ 9, error, 9, 9, 9, error, error, error, error, error, 24, error,
					error, error, 15, 19, error, 22, 24, 23, error },
			{ 14, 14, 14, 14, 14, 14, 14, error, 14, 14, 24, 14, 14, 14, 15,
					19, error, 22, 24, 23, error },
			{ 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 27, 16,
					18, 16, 16, 16, 16 },
			{ 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 17, 26,
					28, 26, 26, 27, 26 },
			{ 1, 14, 7, 7, 7, 14, 14, 13, 14, 14, 24, 14, 14, 14, 15, 19,
					error, 22, 24, 23, error },
			{ 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
					16, 16, 16, 16, 16 },
			{ 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 21,
					20, 19, 19, 19, 19 },
			{ 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19,
					19, 19, 19, 19, 19 },
			{ 1, 14, 7, 7, 7, 14, 14, 13, 14, 14, 24, 14, 14, 14, 15, 19,
					error, 22, 24, 23, error },
			{ 1, 14, 7, 7, 7, 14, 14, 13, 14, 14, 24, 14, 14, 14, 15, 19,
					error, 22, 24, 23, error },
			{ 1, 14, 7, 7, 7, 14, 14, 13, 14, 14, 24, 14, 14, 14, 15, 19,
					error, 22, 24, 23, error },
			{ 1, 14, 7, 7, 7, 14, 14, 13, 14, 14, 24, 14, 14, 14, 15, 19,
					error, 22, 24, 23, error },
			{ error, error, error, error, error, error, error, error, error,
					error, 24, error, error, error, 15, 19, error, 22, 24, 23,
					error },
			{ 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 27, 26,
					28, 28, 26, 26, 26 },
			{ 1, 14, 7, 7, 7, 14, 14, 13, 14, 14, 24, 14, 14, 14, 15, 19,
					error, 22, 24, 23, error },
			{ 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26,
					26, 26, 26, 26, 26 }

	};

	// constructor
	public Lexer(String text) {
		this.text = text;
	}

	// run
	public void run() {

		tokens = new Vector<Token>();
		String line;
		int counterOfLines = 1;
		// split lines
		do {
			// String lineSeparator = System.lineSeparator();

			// int eolAt = text.indexOf(System.lineSeparator());
			int eolAt = text.indexOf("\n");

			if (eolAt >= 0) {
				line = text.substring(0, eolAt);
				if (text.length() > 0)
					text = text.substring(eolAt + 1);
			} else {
				line = text;
				text = "";
			}
			splitLine(counterOfLines, line);
			counterOfLines++;
		} while (!text.equals(""));
	}

	// slit line
	private void splitLine(int row, String line) {
		int state = 0;
		int index = 0;
		char currentChar;
		String string = "";
		if (line.equals(""))
			return;
		// DFA working
		int go;
		do {
			currentChar = line.charAt(index);
			go = calculateNextState(state, currentChar);
			if (go == 25)
				count = 0;
			if (go != 22 && go != 23 && go != 24) {
				if (go != 15 && go != 19) // condition if single quote or double
											// quote... using quotes as
											// delimiter and splitting words
				{
					string = string + currentChar;
					state = go;
				}
				if (go == 15 || go == 19) // starting of quote .. if added to
											// word count will be 1
					if (string.length() == 0 || count != 0) {
						string = string + currentChar;
						state = go;
						count++;
					}
			}

			if ((go != 15 && go != 19)
					|| ((go == 15 && count != 0) || (go == 19 && count != 0)))
				index++;

			if (go == 15 || go == 19)
				if ((go == 15 && state != 15) || (go == 19 && state != 19))
					break;
		} while (index < line.length() && go != 22 && go != 23 && go != 24
				&& go != 17 && go != 21 && go != 27); // These states are same
														// as state 0
		// review final state
		count = 0;
		if (state == 1) {
			tokens.add(new Token(string, "INTEGER", row));
		} else if (state == 3) {
			tokens.add(new Token(string, "BINARY", row));
		} else if (state == 4) {
			tokens.add(new Token(string, "OCTAL", row));
		} else if (state == 6) {
			tokens.addElement(new Token(string, "HEXADECIMAL", row));
		} else if (state == 7) {
			tokens.addElement(new Token(string, "INTEGER", row));
		} else if (state == 8) {
			tokens.addElement(new Token(string, "FLOAT", row));
		} else if (state == 9) {
			tokens.addElement(new Token(string, "FLOAT", row));
		} else if (state == 11) {
			tokens.addElement(new Token(string, "FLOAT", row));
		} else if (state == 14) {
			if (isStringKeyword(string))
				tokens.addElement(new Token(string, "KEYWORD", row));
			else
				tokens.addElement(new Token(string, "IDENTIFIER", row));
		} else if (state == 17) {
			tokens.addElement(new Token(string, "CHARACTER", row));
		} else if (state == 21) {
			tokens.addElement(new Token(string, "STRING", row));
		} else if (state == 27) {
			tokens.addElement(new Token(string, "ERROR", row));
		} else { // sate==25 or state is not final state at last character of
					// line
			if (string != "" && !string.isEmpty() && !string.equals("\uFEFF"))
				tokens.add(new Token(string, "ERROR", row));
		}

		// current char
		if (go == 22)
			tokens.add(new Token(currentChar + "", "DELIMITER", row));

		else if (go == 23) {
		}// No token needed for space

		else if (go == 24)
			tokens.add(new Token(currentChar + "", "OPERATOR", row));

		// loop
		if (index < line.length())
			splitLine(row, line.substring(index));
	}

	// calculate state
	private int calculateNextState(int state, char currentChar) {

		if (currentChar == '0')
			return stateTable[state][zero];

		else if (currentChar == 'b' | currentChar == 'B')
			return stateTable[state][b_Or_B];

		else if (currentChar == '1')
			return stateTable[state][one];

		else if (currentChar == '2' | currentChar == '3' | currentChar == '4'
				| currentChar == '5' | currentChar == '6' | currentChar == '7')
			return stateTable[state][twoToSeven];

		else if (currentChar == '8' | currentChar == '9')
			return stateTable[state][eightToNine];

		else if (is_acd(currentChar))
			return stateTable[state][acd_Or_ACD];

		else if (currentChar == 'x' | currentChar == 'X')
			return stateTable[state][x_Or_X];

		else if (currentChar == '.')
			return stateTable[state][dot];

		else if (currentChar == 'e' | currentChar == 'E')
			return stateTable[state][e_Or_E];

		else if (currentChar == 'f' | currentChar == 'F')
			return stateTable[state][f_Or_F];

		else if (currentChar == '+' | currentChar == '-')
			return stateTable[state][plus_Or_Minus];

		else if (currentChar == '$' | currentChar == '_')
			return stateTable[state][$_Or_];

		else if (is_gTow(currentChar))
			return stateTable[state][gTow_Or_GToW];

		else if (is_yToz(currentChar))
			return stateTable[state][yToz_Or_YToZ];

		else if (currentChar == '\'')
			return stateTable[state][singleQuote];

		else if (currentChar == '\"')
			return stateTable[state][doubleQuote];

		else if (currentChar == '\\')
			return stateTable[state][escapeCharacter];

		else if (isDelimiter(currentChar))
			return stateTable[state][delimiter];

		else if (isOperatorOtherThanPlusAndMinus(currentChar))
			return stateTable[state][operatorExceptPlusAndMinus];

		else if (isSpace(currentChar))
			return stateTable[state][whiteSpace];

		else
			return stateTable[state][allOtherCharecters];

	}

	// isDelimiter
	private boolean isDelimiter(char c) {
		char[] delimiters = { ':', ';', '}', '{', '[', ']', '(', ')', ',' };
		for (int x = 0; x < delimiters.length; x++) {
			if (c == delimiters[x])
				return true;
		}
		return false;
	}

	// isOperator
	private boolean isOperatorOtherThanPlusAndMinus(char o) {
		// == != do it in split line
		char[] operators = { '*', '/', '<', '>', '=', '!', '&', '|' };
		for (int x = 0; x < operators.length; x++) {
			if (o == operators[x])
				return true;
		}
		return false;
	}

	// isSpace
	private boolean isSpace(char o) {
		if (o == ' ' || o == '	'|| o=='\t')
			return true;
		else
			return false;
	}

	private boolean is_gTow(char o) {
		char gTow_And_GToW_Array[] = { 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W' };
		for (int x = 0; x < gTow_And_GToW_Array.length; x++) {
			if (o == gTow_And_GToW_Array[x])
				return true;
		}
		return false;
	}

	private boolean is_acd(char o) {
		char aTod_And_AtoD_Array[] = { 'a', 'c', 'd', 'A', 'C', 'D' };
		for (int x = 0; x < aTod_And_AtoD_Array.length; x++) {
			if (o == aTod_And_AtoD_Array[x])
				return true;
		}
		return false;
	}

	private boolean is_yToz(char o) {
		char yToz_And_YtoZ_Array[] = { 'y', 'z', 'Y', 'Z' };
		for (int x = 0; x < yToz_And_YtoZ_Array.length; x++) {
			if (o == yToz_And_YtoZ_Array[x])
				return true;
		}
		return false;
	}

	private boolean isStringKeyword(String s) {
		for (int x = 0; x < KEYWORD.length; x++) {
			if (s.equals(KEYWORD[x]))
				return true;
		}
		return false;
	}

	// getTokens
	public Vector<Token> getTokens() {
		return tokens;
	}

}
