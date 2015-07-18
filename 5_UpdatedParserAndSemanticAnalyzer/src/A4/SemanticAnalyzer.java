package A4;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

public class SemanticAnalyzer {

	private static final Hashtable<String, Vector<SymbolTableItem>> symbolTable = new Hashtable<String, Vector<SymbolTableItem>>();
	public static Stack<String> stack = new Stack<String>();
	private static final int INTEGER = 0;
	private static final int FLOAT = 1;
	private static final int CHARACTER = 2;
	private static final int STRING = 3;
	private static final int BOOLEAN = 4;
	private static final int VOID = 5;
	private static final int error = 6;

	private static final int addition = 0;
	private static final int subtraction = 1;
	private static final int multiplication = 2;
	private static final int division = 3;
	private static final int assign = 4;
	private static final int and = 5;
	private static final int or = 6;
	private static final int not = 7;
	private static final int lessthan = 8;
	private static final int greaterthan = 9;
	private static final int equals = 10;
	private static final int notequls=11;
	private static final int unaryminus=12;
	
	private static String[][][] cube = new String[][][] {

			// addition
			{
					{ "INTEGER", "FLOAT", "error", "STRING", "error", "error" , "error"},
					{ "FLOAT", "FLOAT", "error", "STRING", "error", "error" , "error"},
					{ "error", "error", "error", "STRING", "error", "error" , "error"},
					{ "STRING", "STRING", "STRING", "STRING", "STRING","error" , "error"},
					{ "error", "error", "error", "STRING", "error", "error" , "error"},
					{ "error", "error", "error", "error", "error", "error" , "error"},
					{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//subtraction
			{
				{ "INTEGER", "FLOAT", "error", "error", "error", "error" , "error"},
				{ "FLOAT", "FLOAT", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//multiplication
			{
				{ "INTEGER", "FLOAT", "error", "error", "error", "error" , "error"},
				{ "FLOAT", "FLOAT", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//division
			{
				{ "INTEGER", "FLOAT", "error", "error", "error", "error" , "error"},
				{ "FLOAT", "FLOAT", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//assign
			{
				{ "OK", "error", "error", "error", "error", "error" , "error"},
				{ "OK", "OK", "error", "error", "error", "error" , "error"},
				{ "error", "error", "OK", "error", "error", "error" , "error"},
				{ "error", "error", "error", "OK", "error", "error" , "error"},
				{ "error", "error", "error", "error", "OK", "error" , "error"},
				{ "error", "error", "error", "error", "error", "OK" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//and
			{
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "BOOLEAN", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//or
			{
				{ "error", "error", "error", "error", "error", "error", "error" },
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "BOOLEAN", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//not
			{
				{"error","error","error","error","BOOLEAN","error","error"}
			},
			//lessthan
			{
				{ "BOOLEAN", "BOOLEAN", "error", "error", "error", "error", "error" },
				{ "BOOLEAN", "BOOLEAN", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//greaterthan
			{
				{ "BOOLEAN", "BOOLEAN", "error", "error", "error", "error", "error" },
				{ "BOOLEAN", "BOOLEAN", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//equals ==
			{
				{ "BOOLEAN", "BOOLEAN", "error", "error", "error", "error", "error" },
				{ "BOOLEAN", "BOOLEAN", "error", "error", "error", "error" , "error"},
				{ "error", "error", "BOOLEAN", "error", "error", "error" , "error"},
				{ "error", "error", "error", "BOOLEAN", "error", "error" , "error"},
				{ "error", "error", "error", "error", "BOOLEAN", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//Not Equals !=
			{
				{ "BOOLEAN", "BOOLEAN", "error", "error", "error", "error", "error" },
				{ "BOOLEAN", "BOOLEAN", "error", "error", "error", "error" , "error"},
				{ "error", "error", "BOOLEAN", "error", "error", "error" , "error"},
				{ "error", "error", "error", "BOOLEAN", "error", "error" , "error"},
				{ "error", "error", "error", "error", "BOOLEAN", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"},
				{ "error", "error", "error", "error", "error", "error" , "error"}
			},
			//unary minus
			{
				{"INTEGER","FLOAT","error","error","error","error","error"}
			}
			};

	// create here a data structure for the cube of types

	public static Hashtable<String, Vector<SymbolTableItem>> getSymbolTable() {
		return symbolTable;
	}

	public static void checkVariable(String type, String id, Gui gui,
			int lineNum) {

		// A. search the id in the symbol table
		if (symbolTable.get(id) == null) {
			// B. if !exist then insert: type, scope=global, value={0, false,
			// "", '')
			Vector<SymbolTableItem> v = new Vector<SymbolTableItem>();
			
			v.add(new SymbolTableItem(type, "global", ""));
			symbolTable.put(id, v);
		} else // C. else error: variable id is already defined
		{
			error(gui, 1, lineNum,id);
		}
	}

	public static void pushStack(String type) {

		// push type in the stack
		stack.push(type);
	}

	public static String popStack() {
		String result = "";
		// pop a value from the stack
		result=stack.pop();
		return result;
	}

	public static String calculateCube(String type, String operator) {
		String result = "";
		int operand1=6;
		// unary operator ( - and !)		
		switch(type){
		case "INTEGER":
			operand1=INTEGER;		
			break;
		case "FLOAT":
			operand1=FLOAT;
			break;
		case "HEXADECIMAL":
			operand1=INTEGER;
			break;
		case "OCTAL":
			operand1=INTEGER;
			break;
		case "CHARACTER":
			operand1=CHARACTER;
			break;
		case "STRING":
			operand1=STRING;
			break;
		case "BOOLEAN":
			operand1=BOOLEAN;
			break;
		case "void":
			operand1=VOID;
			break;
		}
		if(operator.equals("!"))
		{
			result=cube[not][0][operand1];
		}
		if(operator.equals("-"))
		{
			result=cube[unaryminus][0][operand1];
		}
		return result;
	}

	public static String calculateCube(String type1, String type2, String operator) {
		String result = "";
		int operand1=6,operand2=6;
		// binary operators
		switch(type1){
		case "INTEGER":
			operand1=INTEGER;
			break;
		case "FLOAT":
			operand1=FLOAT;
			break;
		case "HEXADECIMAL":
			operand1=INTEGER;
			break;
		case "OCTAL":
			operand1=INTEGER;
			break;
		case "CHARACTER":
			operand1=CHARACTER;
			break;
		case "STRING":
			operand1=STRING;
			break;
		case "BOOLEAN":
			operand1=BOOLEAN;
			break;
		case "void":
			operand1=VOID;
			break;
		}
		switch(type2){
		case "INTEGER":
			operand2=INTEGER;
			break;
		case "FLOAT":
			operand2=FLOAT;
			break;
		case "HEXADECIMAL":
			operand2=INTEGER;
			break;
		case "OCTAL":
			operand2=INTEGER;
			break;
		case "CHARACTER":
			operand2=CHARACTER;
			break;
		case "STRING":
			operand2=STRING;
			break;
		case "BOOLEAN":
			operand2=BOOLEAN;
			break;
		case "void":
			operand2=VOID;
			break;
		}
		if(operator.equals("+"))
		{
			result=cube[addition][operand2][operand1];
		}
		else if(operator.equals("-"))
		{
			result=cube[subtraction][operand2][operand1];
		}
		else if(operator.equals("*"))
		{
			result=cube[multiplication][operand2][operand1];
		}
		else if(operator.equals("/"))
		{
			result=cube[division][operand2][operand1];
		}
		else if(operator.equals("="))
		{
			result=cube[assign][operand2][operand1];
		}
		else if(operator.equals("&"))
		{
			result=cube[and][operand2][operand1];
		}
		else if(operator.equals("|"))
		{
			result=cube[or][operand2][operand1];
		}
		else if(operator.equals("<"))
		{
			result=cube[lessthan][operand2][operand1];
		}
		else if(operator.equals(">"))
		{
			result=cube[greaterthan][operand2][operand1];
		}
		else if(operator.equals("=="))
		{
			result=cube[equals][operand2][operand1];
		}
		else if(operator.equals("!="))
		{
			result=cube[notequls][operand2][operand1];
		}
		return result;
	}

	public static void error(Gui gui, int err, int n,String info) {
		switch (err) {
		case 1:
			gui.writeConsole("Line" + n + ": variable <"+ info +"> is already defined");
			Parser.errorcount++;
			break;
		case 2:
			gui.writeConsole("Line" + n + ": incompatible types: type mismatch");
			Parser.errorcount++;
			break;
		case 3:
			gui.writeConsole("Line" + n
					+ ": incompatible types: expected boolean");
			Parser.errorcount++;
			break;
		case 4:
			gui.writeConsole("Line" + n
					+ ": Variable <"+info+"> not found");
			Parser.errorcount++;
			break;
		}
	}

}
