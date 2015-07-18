package A4;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import com.sun.org.apache.bcel.internal.classfile.Code;

import A4.Lexer;

public class Parser {

	public static int errorcount=0;
	private static DefaultMutableTreeNode root;
	private static Vector<Token> tokens;
	private static int currentToken;
	private static Gui gui;
	private static int rule_a_count=0;
//	private static String flagForOperator_rulea="";
	
	private static int rule_e_count=0;
//	private static String flagForOperator_rulee="";
	
	private static int rule_r_count=0;
	//private static String flagForOperator_ruler="";
	
	private static int rule_y_count=0;
//	private static String flagForOperator_ruley="";
	
	private static int rule_x_count=0;
	//private static String flagForOperator_rulex="";
	
	private static int rule_exp_count=0;
//	private static String flagForOperator_ruleexp="";
	
	static boolean flag=false;
	static Set<String> program_FirstSet=new HashSet<String>();	    
	static Set<String> program_FollowSet=new HashSet<String>();
	
	static Set<String> body_FirstSet=new HashSet<String>();	    
	static Set<String> body_FollowSet=new HashSet<String>();
	
	static Set<String> print_FirstSet=new HashSet<String>();	    
	static Set<String> print_FollowSet=new HashSet<String>();
	
	static Set<String> assignment_FirstSet=new HashSet<String>();	    
	static Set<String> assignment_FollowSet=new HashSet<String>();
	
	static Set<String> variable_FirstSet=new HashSet<String>();	    
	static Set<String>	variable_FollowSet=new HashSet<String>();
	
	static Set<String> while_FirstSet=new HashSet<String>();	    
	static Set<String> while_FollowSet=new HashSet<String>();
	
	static Set<String> if_FirstSet=new HashSet<String>();	    
	static Set<String> if_FollowSet=new HashSet<String>();
	
	static Set<String> return_FirstSet=new HashSet<String>();	    
	static Set<String> return_FollowSet=new HashSet<String>();
	
	static Set<String> expression_FirstSet=new HashSet<String>();	    
	static Set<String> expression_FollowSet=new HashSet<String>();
	
	static Set<String> x_FirstSet=new HashSet<String>();	    
	static Set<String> x_FollowSet=new HashSet<String>();
	
	static Set<String> y_FirstSet=new HashSet<String>();	    
	static Set<String> y_FollowSet=new HashSet<String>();
	
	static Set<String> r_FirstSet=new HashSet<String>();	    
	static Set<String> r_FollowSet=new HashSet<String>();
	
	static Set<String> e_FirstSet=new HashSet<String>();	    
	static Set<String> e_FollowSet=new HashSet<String>();
	
	static Set<String> a_FirstSet=new HashSet<String>();	    
	static Set<String> a_FollowSet=new HashSet<String>();
	
	static Set<String> b_FirstSet=new HashSet<String>();	    
	static Set<String> b_FollowSet=new HashSet<String>();
	
	static Set<String> c_FirstSet=new HashSet<String>();	    
	static Set<String> c_FollowSet=new HashSet<String>();
	
	
	public static DefaultMutableTreeNode run(Vector<Token> t, Gui gui_passedObj) {
		
		{
		c_FirstSet.add("INTEGER");
		c_FirstSet.add("OCTAL");
		c_FirstSet.add("HEXADECIMAL");
		c_FirstSet.add("BINARY");
		c_FirstSet.add("STRING");
		c_FirstSet.add("CHAR");
		c_FirstSet.add("FLOAT");
		c_FirstSet.add("IDENTIFIER");
		//handling true and false separately

		b_FirstSet.add("-");
		b_FirstSet.addAll(c_FirstSet);
		
		a_FirstSet.addAll(b_FirstSet);
		
		e_FirstSet.addAll(a_FirstSet);
		
		r_FirstSet.addAll(e_FirstSet);
		
		y_FirstSet.add("!");
		y_FirstSet.addAll(r_FirstSet);
		
		x_FirstSet.addAll(y_FirstSet);
		
		expression_FirstSet.addAll(x_FirstSet);
		
		print_FirstSet.add("print");
		
		return_FirstSet.add("");
		
		if_FirstSet.add("if");
		
		while_FirstSet.add("while");
		
		variable_FirstSet.add("int");
		variable_FirstSet.add("float");
		variable_FirstSet.add("boolean");
		variable_FirstSet.add("void");
		variable_FirstSet.add("char");
		variable_FirstSet.add("string");
		
		assignment_FirstSet.add("IDENTIFIER");				
		
		body_FirstSet.addAll(print_FirstSet);
		body_FirstSet.addAll(assignment_FirstSet);
		body_FirstSet.addAll(variable_FirstSet);
		body_FirstSet.addAll(while_FirstSet);
		body_FirstSet.addAll(if_FirstSet);
		body_FirstSet.addAll(return_FirstSet);
		
		program_FirstSet.add("{");
		
		}
		
		{
			program_FollowSet.add("$");
			
			body_FollowSet.add("}");
			
			variable_FollowSet.add(";");
			
			assignment_FollowSet.add(";");
			
			while_FollowSet.addAll(body_FirstSet);
			while_FollowSet.add("}");
			
			if_FollowSet.addAll(body_FirstSet);
			if_FollowSet.add("}");
			
			return_FollowSet.add("");
			
			print_FollowSet.add(";");
			 
			expression_FollowSet.add(";");
			expression_FollowSet.add(")");
			
			x_FollowSet.add("|");
			x_FollowSet.addAll(expression_FollowSet);
			
			y_FollowSet.add("&");
			y_FollowSet.addAll(x_FollowSet);
			
			r_FollowSet.addAll(y_FollowSet);
			
			e_FollowSet.addAll(r_FollowSet);
			e_FollowSet.add("!=");
			e_FollowSet.add("==");
			e_FollowSet.add(">");
			e_FirstSet.add("<");
			
			a_FollowSet.add("+");
			a_FollowSet.add("-");
			a_FollowSet.addAll(e_FollowSet);
			
			b_FollowSet.add("*");
			b_FollowSet.add("/");
			b_FollowSet.addAll(a_FollowSet);
						
			c_FollowSet.addAll(b_FirstSet);
			
			
		}
		
		tokens = t;
		currentToken = 0;			
		errorcount=0;
		SemanticAnalyzer.getSymbolTable().clear();	
		while(!(SemanticAnalyzer.stack.empty()))
		{
			SemanticAnalyzer.stack.pop();
		}
		
		CodeGenerator.clear(gui);
		
		
		root = new DefaultMutableTreeNode("PROGRAM");
		gui = gui_passedObj;
		rule_BodyPROGRAM(root);
		gui.writeSymbolTable(SemanticAnalyzer.getSymbolTable());

		
		if(currentToken<tokens.size())
		{
			error(10,false);
		}
		
		if(errorcount==0)
		{
			CodeGenerator.writeCode(gui);
		}
		else
		{
			CodeGenerator.clear(gui);
			CodeGenerator.addInstruction("Not generating Instructions because Input has errors", "", "");
			CodeGenerator.writeCode(gui);
		}
		return root;
	}

	private static void rule_BodyPROGRAM(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node;
			
			if(currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("{"))
			{
			node = new DefaultMutableTreeNode("{");
			parent.add(node);
			currentToken++;
			}
			else
			error(1,false);

		node = new DefaultMutableTreeNode("BODY");
		parent.add(node);
		rule_BODY(node);
		
		

		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("}")) {
			node = new DefaultMutableTreeNode("}");
			parent.add(node);
			CodeGenerator.addInstruction("OPR", "0","0");
			currentToken++;

		} else
			error(2,false);		
	}

	private static void rule_PROGRAM(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node;
			
			if(currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("{"))
			{
			node = new DefaultMutableTreeNode("{");
			parent.add(node);
			currentToken++;
			}
			else
			error(1,false);

		node = new DefaultMutableTreeNode("BODY");
		parent.add(node);
		rule_BODY(node);
		
		

		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("}")) {
			node = new DefaultMutableTreeNode("}");
			parent.add(node);
			currentToken++;

		} else
			error(2,false);		
	}

	private static void rule_BODY(DefaultMutableTreeNode parent) {
		
		while (currentToken < tokens.size() && (!tokens.get(currentToken).getWord().equals("}")) ) {
			DefaultMutableTreeNode node;
			boolean newLine=false;
			String word = tokens.get(currentToken).getWord();
			String token = tokens.get(currentToken).getToken();
		while(!(SemanticAnalyzer.stack.empty()))
				{
			SemanticAnalyzer.stack.pop();
				}
			
			if (word.equals("print")) {
				node = new DefaultMutableTreeNode("PRINT");
				parent.add(node);
				newLine=rule_PRINT(node);
				
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				newLine=true;	
				}
				
				if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(";") && (!newLine) ) {
					node = new DefaultMutableTreeNode(";");
					parent.add(node);
					currentToken++;
					if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
					{
					newLine=true;	
					}
				} else
					error(3,newLine);
			} 
			else if (token.equals("IDENTIFIER")) {
				node = new DefaultMutableTreeNode("ASSIGNMENT");
				parent.add(node);
				newLine=rule_ASSIGNMENT(node);

				
				
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			
				if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(";") && (!newLine)) {
					node = new DefaultMutableTreeNode(";");
					parent.add(node);
					currentToken++;
					if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
					{
					newLine=true;	
					}
				} else
				{
					error(3,newLine);
					
					}
				

			} else if (word.equals("while")) {
				node = new DefaultMutableTreeNode("WHILE");
				parent.add(node);
				rule_WHILE(node);
			} else if (word.equals("if")) {
				node = new DefaultMutableTreeNode("IF");
				parent.add(node);
				rule_IF(node);

			} else if (word.equals("return")) {
				node = new DefaultMutableTreeNode("RETURN");
				parent.add(node);
				rule_RETURN(node);
				
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			
				
				if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(";") && (!newLine)) {
					node = new DefaultMutableTreeNode(";");
					parent.add(node);
					currentToken++;
					if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
					{
					newLine=true;	
					}
				} else
					error(3,newLine);
			}
			//
			else if (word.equals("int") | word.equals("float")
					| word.equals("boolean") | word.equals("char")
					| word.equals("string") | word.equals("void")) {
				node = new DefaultMutableTreeNode("VARIABLE");
				parent.add(node);
				newLine=rule_VARIABLE(node);

				if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(";") && (!newLine)) {
					node = new DefaultMutableTreeNode(";");
					parent.add(node);
					currentToken++;
					if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
					{
					newLine=true;	
					}
				} else
					error(3,newLine);

			}
			else if(word.equals("switch"))
			{
				node = new DefaultMutableTreeNode("SWITCH");
				parent.add(node);
				rule_SWITCH(node);
			}
			else if(word.equals("\t\t") || word.equals("\t") || word.equals("\t\t\t"))
			{
				currentToken++;
			}
			else
				error(4,newLine);

		}
	}

	private static boolean rule_PRINT(DefaultMutableTreeNode parent) {
		boolean newLine=false;
		
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("print");
		parent.add(node);
		currentToken++;
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{					
		 newLine=true;
		}
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(") && (!newLine)) {
			node = new DefaultMutableTreeNode("(");
			parent.add(node);			
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} else
			error(6,newLine);				
		
		if(newLine)
		{
			error(9,true);
		}
				
		if(!newLine && !flag)
		{
			flag=false;
		node = new DefaultMutableTreeNode("EXPRESSION");
		parent.add(node);
		newLine=rule_EXPRESSION(node);
		}
		flag=false;
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;
		}
		
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(")") && (!newLine)) {
			node = new DefaultMutableTreeNode(")");
			parent.add(node);
			CodeGenerator.addInstruction("OPR", "21", "0");
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} else
			error(12,newLine);
		
		return newLine;
	}


	private static boolean rule_ASSIGNMENT(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node;
		String nameOfVariable="";
		boolean newLine=false;
		node = new DefaultMutableTreeNode("identifier" + "("
				+ tokens.get(currentToken).getWord() + ")");
		nameOfVariable=tokens.get(currentToken).getWord();
		parent.add(node);
		//------------------------
		String type="";
		try
		{					
		type=SemanticAnalyzer.getSymbolTable().get(tokens.get(currentToken).getWord()).get(0).getType();
		}
		catch(Exception e)
		{
			type="error";
			SemanticAnalyzer.error(gui,4,tokens.get(currentToken).getLine(),tokens.get(currentToken).getWord());
		}
		switch(type)
		{
		case "int":
			type="INTEGER";
			break;
		case "float":
			type="FLOAT";
			break;
		case "boolean":
			type="BOOLEAN";
			break;
		case "char":
			type="CHARACTER";
			break;
		case "string":
			type="STRING";
			break;
		case "void":
			type="void";
			break;		
		default:
			type="error";
			break;
		}				
		SemanticAnalyzer.pushStack(type);		
		//------------------------
		currentToken++;
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("=") && (!newLine)) {
			node = new DefaultMutableTreeNode("=");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} else
			error(5,newLine);

		//if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine() && !flag)
		{
		newLine=true;	
		error(9,true);
		}
		//if(!newLine){		
		if(!newLine && !flag){
			flag=false;
		node = new DefaultMutableTreeNode("EXPRESSION");
		parent.add(node);
		newLine=rule_EXPRESSION(node);
		//-----------------
		String result="";
		try{
			String x=SemanticAnalyzer.popStack();
			String y=SemanticAnalyzer.popStack();
			result=SemanticAnalyzer.calculateCube(x, y, "=");	
		}
		catch(Exception e)
		{
			
		}
				
		if(!result.equals("OK"))
		{
			SemanticAnalyzer.error(gui,2, tokens.get(currentToken).getLine(),tokens.get(currentToken).getWord());
		}
		CodeGenerator.addInstruction("STO",nameOfVariable, "0");
		//----------
		}
		flag=false;
		return newLine;
	}

	private static void rule_WHILE(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node;
        boolean newLine=false;
        CodeGenerator.labelsCount++;
        String label1Name="e"+CodeGenerator.labelsCount;
        int label1Value;
        
        CodeGenerator.labelsCount++;
        String label2Name="e"+CodeGenerator.labelsCount;
        int label2Value;
        
		node = new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
		parent.add(node);
		currentToken++;		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		label2Value=CodeGenerator.instructions.size()+1;
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(") && (!newLine)) {
			node = new DefaultMutableTreeNode("(");
			parent.add(node);
			currentToken++;						
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} else
			error(6,newLine);
        
		if(newLine)
		{
			error(9,true);
		}
		if(!(newLine) && !flag)
        {
			flag=false;
		node = new DefaultMutableTreeNode("EXPRESSION");
		parent.add(node);
		rule_EXPRESSION(node);
		CodeGenerator.addInstruction("JMC", "#"+label1Name,"false");
		String x="";
		try
		{
			x=SemanticAnalyzer.popStack();
		}
		catch(Exception e)
		{
			
		}
		if(!x.equals("BOOLEAN"))
		{
			SemanticAnalyzer.error(gui,3,tokens.get(currentToken).getLine(),"");
		}
        }
		flag=false;
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(")") && (!newLine)) {
			node = new DefaultMutableTreeNode(")");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} else
			error(7,newLine);

		node = new DefaultMutableTreeNode("PROGRAM");
		parent.add(node);
		rule_PROGRAM(node);
		CodeGenerator.addInstruction("JMP", "#"+label2Name, "0");
		label1Value=CodeGenerator.instructions.size()+1;
		
		CodeGenerator.addLabel(label1Name, label1Value);
		CodeGenerator.addLabel(label2Name, label2Value);

	}

	private static void rule_IF(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node;
        boolean newLine=false;
        
        CodeGenerator.labelsCount++;
        String label1Name="e"+CodeGenerator.labelsCount;
        int label1Value;
        
        CodeGenerator.labelsCount++;
        String label2Name="e"+CodeGenerator.labelsCount;
        int label2Value;
        
        
        
		node = new DefaultMutableTreeNode("if");
		parent.add(node);
		currentToken++;
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(") && (!newLine)) {
			node = new DefaultMutableTreeNode("(");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} else
			error(6,newLine);

		if(newLine)
		{
			error(9,true);
		}
		
		if(!newLine && !flag){
		flag=false;
			node = new DefaultMutableTreeNode("EXPRESSION");
		parent.add(node);
		rule_EXPRESSION(node);
		CodeGenerator.addInstruction("JMC", "#"+label1Name,"false");
		String x="error";
		try{
		x=SemanticAnalyzer.popStack();
		}
		catch(Exception e)
		{
			
		}
		if(!x.equals("BOOLEAN"))
		{
			SemanticAnalyzer.error(gui,3,tokens.get(currentToken).getLine(),"");
		}
		}
		flag=false;
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(")") && (!newLine)) {
			node = new DefaultMutableTreeNode(")");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} else
			error(7,newLine);

		node = new DefaultMutableTreeNode("PROGRAM");
		parent.add(node);
		rule_PROGRAM(node);

		CodeGenerator.addInstruction("JMP","#"+label2Name ,"0" );
		label1Value=CodeGenerator.instructions.size()+1;
		
			if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("else")) {
				node = new DefaultMutableTreeNode("else");
				parent.add(node);
				currentToken++;
				node = new DefaultMutableTreeNode("PROGRAM");
				parent.add(node);
				rule_PROGRAM(node);
			 
			
		}
			label2Value=CodeGenerator.instructions.size()+1;
			CodeGenerator.addLabel(label1Name, label1Value);
			CodeGenerator.addLabel(label2Name, label2Value);
	}
	
	private static void rule_SWITCH(DefaultMutableTreeNode parent){
		DefaultMutableTreeNode node;        
		CodeGenerator.labelsCount++;
        String endofSwitchLabel="endOfSwitchLabel"+CodeGenerator.labelsCount;
		int endOfSwitchLabelValue;
		
        node = new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
		parent.add(node);
		currentToken++;				
		
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(")) {
			node = new DefaultMutableTreeNode("(");
			parent.add(node);
			currentToken++;						
			
		} else
			error(13,false);
		
		
		
		String switchCondtionid="";
		if(currentToken < tokens.size()&& tokens.get(currentToken).getToken().equals("IDENTIFIER"))
        {			
			node = new DefaultMutableTreeNode("identifier" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			switchCondtionid=tokens.get(currentToken).getWord();
			
			currentToken++;
        }
		else
			error(14,false);
		
				if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(")")) {
			node = new DefaultMutableTreeNode(")");
			parent.add(node);
			currentToken++;
			
		} else
			error(15,false);
		
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("{"))
		{
		node = new DefaultMutableTreeNode("{");
		parent.add(node);
		currentToken++;
		}
		
		else
		error(16,false);
		
		node = new DefaultMutableTreeNode("CASES");
		parent.add(node);
		rule_CASES(node,switchCondtionid,endofSwitchLabel);

		while(currentToken<tokens.size() && tokens.get(currentToken).getWord().equals("case"))
		{
			rule_CASES(node,switchCondtionid,endofSwitchLabel);
		}
		
		if(currentToken<tokens.size() && tokens.get(currentToken).getWord().equals("default"))
		{
		node = new DefaultMutableTreeNode("DEFAULT");
		parent.add(node);
		rule_DEFAULT(node);
		}
		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("}")) {
			node = new DefaultMutableTreeNode("}");
			parent.add(node);
			currentToken++;

		} else
			error(17,false);	
		
		endOfSwitchLabelValue=CodeGenerator.instructions.size()+1;
		CodeGenerator.addLabel(endofSwitchLabel,endOfSwitchLabelValue);
	}
	
	private static void rule_CASES(DefaultMutableTreeNode parent,String switchCondtionid,String endofSwitchLabel)
	{
		DefaultMutableTreeNode node;
		CodeGenerator.labelsCount++;
		String label1="e"+CodeGenerator.labelsCount;
		int label1Value;
		if(currentToken<tokens.size() && tokens.get(currentToken).getWord().equals("case"))
		{
		node=new DefaultMutableTreeNode("CASE");
		parent.add(node);
		currentToken++;				
		}
		else
		{
			error(18,false);
		}
		
		if(currentToken<tokens.size()  && (tokens.get(currentToken).getToken().equals("INTEGER") ||tokens.get(currentToken).getToken().equals("BINARY")|| tokens.get(currentToken).getToken().equals("OCTAL")|| tokens.get(currentToken).getToken().equals("HEXADECIMAL")))
				{
			node=new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
			parent.add(node);
			CodeGenerator.addInstruction("LOD", switchCondtionid, "0");
			CodeGenerator.addInstruction("LIT", tokens.get(currentToken).getWord(), "0");
			CodeGenerator.addInstruction("OPR", "15", "0");
			CodeGenerator.addInstruction("JMC","#"+label1 , "false");
			currentToken++;
			
				}
		else
		{
			error(17,false);
		}
		
		if(currentToken<tokens.size()  && (tokens.get(currentToken).getWord().equals(":")))
		{
			node=new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
			parent.add(node);
			currentToken++;
			
		}
		else
		{
			error(18,false);
		}
		
		node = new DefaultMutableTreeNode("PROGRAM");
		parent.add(node);
		rule_PROGRAM(node);
		CodeGenerator.addInstruction("JMP", "#"+endofSwitchLabel, "0");
		
		label1Value=CodeGenerator.instructions.size()+1;
		CodeGenerator.addLabel(label1, label1Value);
	}
	private static void rule_DEFAULT(DefaultMutableTreeNode parent)
	{
		
		DefaultMutableTreeNode node;
		
		
		if(currentToken<tokens.size() && tokens.get(currentToken).getWord().equals("default"))
		{
			node=new DefaultMutableTreeNode("DEFAULT");
			parent.add(node);
			currentToken++;
		}
		else
		{
			error(19,false);
		}
		
		if(currentToken<tokens.size() && tokens.get(currentToken).getWord().equals(":"))
		{
			node=new DefaultMutableTreeNode(":");
			parent.add(node);
			currentToken++;
		}
		else
		{
			error(18,false);
		}
		
		node=new DefaultMutableTreeNode("PROGRAM");
		parent.add(node);
		rule_PROGRAM(node);
		
	}
	private static void rule_RETURN(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("return");
		parent.add(node);
		CodeGenerator.addInstruction("OPR", "1", "0");
		currentToken++;

	}

	
	private static boolean rule_VARIABLE(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node;
        boolean newLine=false;
        
		node = new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
		parent.add(node);
		currentToken++;
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("IDENTIFIER") && (!newLine)) {
			SemanticAnalyzer.checkVariable(tokens.get(currentToken-1).getWord(),tokens.get(currentToken).getWord(),gui,tokens.get(currentToken).getLine());			
			//------------------------------
			node = new DefaultMutableTreeNode("identifier" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			CodeGenerator.addVariable(tokens.get(currentToken-1).getWord(),tokens.get(currentToken).getWord());
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}
		else
			error(8,newLine);
		
		
		
		return newLine;
	}


	private static boolean rule_EXPRESSION(DefaultMutableTreeNode parent) {
		String flagForOperator_ruleexp="";
		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		node = new DefaultMutableTreeNode("X");
		parent.add(node);
		newLine=rule_X(node);
		
		rule_exp_count++;
		
		


		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("|") && (!newLine)) {
			flagForOperator_ruleexp=tokens.get(currentToken).getWord();
			node = new DefaultMutableTreeNode("|");
			
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
			node = new DefaultMutableTreeNode("X");
			parent.add(node);
			newLine=rule_EXPRESSION(node);

			if(!flagForOperator_ruleexp.equals(""))
			{
				String result="error";
				try
				{
				String x=SemanticAnalyzer.popStack();
				String y=SemanticAnalyzer.popStack();
				result=SemanticAnalyzer.calculateCube(x, y, flagForOperator_ruleexp);
				}
				catch(Exception e)
				{
					
				}
				flagForOperator_ruleexp="";
				SemanticAnalyzer.pushStack(result);
				rule_exp_count=0;
				CodeGenerator.addInstruction("OPR", "8", "0");
			}
		}
		return newLine;

	}

	private static boolean rule_X(DefaultMutableTreeNode parent) {
		String flagForOperator_rulex="";
		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		node = new DefaultMutableTreeNode("Y");
		parent.add(node);
		newLine=rule_Y(node);
		
		rule_x_count++;
		
		

		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("&") && (!newLine)) {
			flagForOperator_rulex=tokens.get(currentToken).getWord();
			node = new DefaultMutableTreeNode("&");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
			node = new DefaultMutableTreeNode("Y");
			parent.add(node);
			newLine=rule_X(node);
		

			if(!flagForOperator_rulex.equals(""))
			{
				String result="error";
				try{
					
				
				String x=SemanticAnalyzer.popStack();
				String y=SemanticAnalyzer.popStack();
				result=SemanticAnalyzer.calculateCube(x, y, flagForOperator_rulex);
				}
				catch(Exception e)
				{
					
				}
				flagForOperator_rulex="";
				SemanticAnalyzer.pushStack(result);
				rule_x_count=0;
				CodeGenerator.addInstruction("OPR", "9", "0");
			}
		}
		return newLine;

	}

	private static boolean rule_Y(DefaultMutableTreeNode parent) {
		String flagForOperator_ruley="";
		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("!") && (!newLine)) {
			flagForOperator_ruley="!";
			node = new DefaultMutableTreeNode("!");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}
		
		node = new DefaultMutableTreeNode("R");
		parent.add(node);
		newLine=rule_R(node);
		
		if(!flagForOperator_ruley.equals(""))
		{
		String x="error";
		try{
			x=SemanticAnalyzer.popStack();
		}
		catch(Exception e)
		{
			
		}
		String result=SemanticAnalyzer.calculateCube(x, "!");
		SemanticAnalyzer.pushStack(result);
		flagForOperator_ruley="";
		CodeGenerator.addInstruction("OPR", "10","0");
		}

		return newLine;
	}

	private static boolean rule_R(DefaultMutableTreeNode parent) {
		String flagForOperator_ruler="";
		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		node = new DefaultMutableTreeNode("E");
		parent.add(node);
		newLine=rule_E(node);
		
		rule_r_count++;
		
		
		
		
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size() && (tokens.get(currentToken).getWord().equals("<")|| tokens.get(currentToken).getWord().equals(">") || tokens.get(currentToken).getWord().equals("==")|| tokens.get(currentToken).getWord().equals("!=")) && (!newLine)) 
		{
			flagForOperator_ruler=tokens.get(currentToken).getWord();
			if (currentToken < tokens.size()
					&& tokens.get(currentToken).getWord().equals("<")) {
				node = new DefaultMutableTreeNode("<");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
				node = new DefaultMutableTreeNode("E");
				parent.add(node);
				newLine=rule_E(node);
			} else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getWord().equals(">")) {
				node = new DefaultMutableTreeNode(">");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
				node = new DefaultMutableTreeNode("E");
				parent.add(node);
				newLine=rule_E(node);
			}
			 else if (currentToken < tokens.size()
						&& tokens.get(currentToken).getWord().equals("==")) {
					node = new DefaultMutableTreeNode("==");
					parent.add(node);
					currentToken++;
					if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
					{
					newLine=true;	
					}
					node = new DefaultMutableTreeNode("E");
					parent.add(node);
					newLine=rule_E(node);
				}
			 else if (currentToken < tokens.size()
						&& tokens.get(currentToken).getWord().equals("!=")) {
					node = new DefaultMutableTreeNode("!=");
					parent.add(node);
					currentToken++;
					if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
					{
					newLine=true;	
					}
					node = new DefaultMutableTreeNode("E");
					parent.add(node);
					newLine=rule_E(node);
				}
		
		
			if(!flagForOperator_ruler.equals(""))
			{
				
				String operatorNum="";
				switch(flagForOperator_ruler)
				{
					case "<":
						operatorNum="12";
						break;
					case ">":
						operatorNum="11";
						break;
					case "==":
						operatorNum="15";
						break;
					case "!=":
						operatorNum="16";
						break;
						
				}
				CodeGenerator.addInstruction("OPR", operatorNum, "0");
				
				String result="error";
				try
				{
				String x=SemanticAnalyzer.popStack();
				String y=SemanticAnalyzer.popStack();
				result=SemanticAnalyzer.calculateCube(x, y, flagForOperator_ruler);
				}
				catch(Exception e)
				{
					
				}
				flagForOperator_ruler="";
				SemanticAnalyzer.pushStack(result);
				rule_r_count=0;
				
			}
		
		}
		return newLine;
	}

	private static boolean rule_E(DefaultMutableTreeNode parent) {
		String flagForOperator_rulee="";
		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		
		node = new DefaultMutableTreeNode("A");
		parent.add(node);
		newLine=rule_A(node);
		rule_e_count++;
		
		
		
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size()
				&& (tokens.get(currentToken).getWord().equals("+")
				|| tokens.get(currentToken).getWord().equals("-")) && (!newLine)) {
		
			flagForOperator_rulee=(tokens.get(currentToken).getWord());
			
			if (currentToken < tokens.size()
					&& tokens.get(currentToken).getWord().equals("+")) {
				node = new DefaultMutableTreeNode("+");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
				node = new DefaultMutableTreeNode("A");
				parent.add(node);
				newLine=rule_A(node);
			} else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getWord().equals("-")) {
				node = new DefaultMutableTreeNode("-");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
				node = new DefaultMutableTreeNode("A");
				parent.add(node);
				newLine=rule_A(node);
			}
			
			if(!flagForOperator_rulee.equals(""))
			{
				
				String operatorNum="";
				switch(flagForOperator_rulee)
				{
					case "+":
						operatorNum="2";
						break;
					case "-":
						operatorNum="3";
						break;					
						
				}
				CodeGenerator.addInstruction("OPR", operatorNum, "0");
				
				
				
				String result="error";
				try{
				String x=SemanticAnalyzer.popStack();
				String y=SemanticAnalyzer.popStack();
				result=SemanticAnalyzer.calculateCube(x, y, flagForOperator_rulee);
				flagForOperator_rulee="";
				}
				catch(Exception e)
				{
					
				}
				SemanticAnalyzer.pushStack(result);
				rule_e_count=0;
				
			}
		}
return newLine;
	}

	private static boolean rule_A(DefaultMutableTreeNode parent) {

		String flagForOperator_rulea="";		
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("B");
		boolean newLine=false;
		
		parent.add(node);
		newLine=rule_B(node);
		rule_a_count++;
		
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size()
				&& (tokens.get(currentToken).getWord().equals("*")				
				|| tokens.get(currentToken).getWord().equals("/") )&& (!newLine)) {
			flagForOperator_rulea=tokens.get(currentToken).getWord();
			if (currentToken < tokens.size()
					&& tokens.get(currentToken).getWord().equals("*")) {
				node = new DefaultMutableTreeNode("*");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
				node = new DefaultMutableTreeNode("B");
				parent.add(node);

				newLine=rule_B(node);

			} else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getWord().equals("/")) {
				node = new DefaultMutableTreeNode("/");
				parent.add(node);
				node = new DefaultMutableTreeNode("B");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
				newLine=rule_B(node);
			}
		
			if(!flagForOperator_rulea.equals(""))
			{
				
				String operatorNum="";
				switch(flagForOperator_rulea)
				{
					case "*":
						operatorNum="4";
						break;
					case "/":
						operatorNum="5";
						break;					
						
				}
				CodeGenerator.addInstruction("OPR", operatorNum, "0");
				
				
				String result="error";
				try{
				String x=SemanticAnalyzer.popStack();
				String y=SemanticAnalyzer.popStack();
				result=SemanticAnalyzer.calculateCube(x, y, flagForOperator_rulea);
				flagForOperator_rulea="";
				}
				catch(Exception e)
				{
					
				}
				
				SemanticAnalyzer.pushStack(result);
				rule_a_count=0;
			}
			
		}
return newLine;
	}

	private static boolean rule_B(DefaultMutableTreeNode parent) {

		String flagForOperator="";
		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("-") && (!newLine)) {
			flagForOperator="-";
			node = new DefaultMutableTreeNode("-");
			parent.add(node);
		    CodeGenerator.addInstruction("LIT", "0", "0");
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}
		
		node = new DefaultMutableTreeNode("C");
		parent.add(node);
		newLine=rule_C(node);
//----------------------
		if(flagForOperator.equals("-"))
		{
			String x="error";
		try{
			x=SemanticAnalyzer.popStack();
		}
		catch(Exception e){
			
		}
		String result=SemanticAnalyzer.calculateCube(x, "-");
		SemanticAnalyzer.pushStack(result);
		flagForOperator="";
		
		CodeGenerator.addInstruction("OPR", "3", "0");
		}
		return newLine;
	}

	private static boolean rule_C(DefaultMutableTreeNode parent) {

		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		if (currentToken < tokens.size()
				&& tokens.get(currentToken).getToken().equals("INTEGER") && (!newLine)) {
			SemanticAnalyzer.pushStack(tokens.get(currentToken).getToken());
			//-----------------------------------------------------
			node = new DefaultMutableTreeNode("integer" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}		
		 else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getToken().equals("OCTAL")&&  (!newLine)) {
			 //SemanticAnalyzer.pushStack(tokens.get(currentToken).getToken());
			 SemanticAnalyzer.pushStack("INTEGER");
				//-----------------------------------------------------
				node = new DefaultMutableTreeNode("identifier" + "("
						+ tokens.get(currentToken).getWord() + ")");
				parent.add(node);
				CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			} 
		
else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getToken().equals("IDENTIFIER") && (!newLine)) {
	
	String type="";
	try
	{					
	type=SemanticAnalyzer.getSymbolTable().get(tokens.get(currentToken).getWord()).get(0).getType();
	}
	catch(Exception e)
	{
		type="error";
		SemanticAnalyzer.error(gui,4,tokens.get(currentToken).getLine(),tokens.get(currentToken).getWord());
	}
	switch(type)
	{
	case "int":
		type="INTEGER";
		break;
	case "float":
		type="FLOAT";
		break;
	case "boolean":
		type="BOOLEAN";
		break;
	case "char":
		type="CHARACTER";
		break;
	case "string":
		type="STRING";
		break;
	case "void":
		type="void";
		break;
	default:
		type="error";
		break;
	
	}
	
	SemanticAnalyzer.pushStack(type);
	
	//-----------------------------------------------------
			node = new DefaultMutableTreeNode("identifier" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			CodeGenerator.addInstruction("LOD",tokens.get(currentToken).getWord() , "0");
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} 
		 else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getToken().equals("FLOAT") && (!newLine)) {
			 SemanticAnalyzer.pushStack(tokens.get(currentToken).getToken());
				//-----------------------------------------------------	
			 node = new DefaultMutableTreeNode("float" + "("
						+ tokens.get(currentToken).getWord() + ")");
				parent.add(node);
				CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			} 
		 else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getToken().equals("HEXADECIMAL") && (!newLine)) {
			 //SemanticAnalyzer.pushStack(tokens.get(currentToken).getToken());
			 SemanticAnalyzer.pushStack("INTEGER");
				//-----------------------------------------------------	
			 node = new DefaultMutableTreeNode("hexadecimal" + "("
						+ tokens.get(currentToken).getWord() + ")");
				parent.add(node);
				CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			} 
		 else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getToken().equals("CHARACTER") && (!newLine)) {			
			 SemanticAnalyzer.pushStack(tokens.get(currentToken).getToken());
				//-----------------------------------------------------
			 node = new DefaultMutableTreeNode("char" + "("
						+ tokens.get(currentToken).getWord() + ")");
				parent.add(node);
				CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			} 
		else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("(") && (!newLine)) {
		
			node = new DefaultMutableTreeNode("(");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
			node = new DefaultMutableTreeNode("EXPRESSION");
			parent.add(node);
			newLine=rule_EXPRESSION(node);
			
			if(currentToken < tokens.size() &&tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
			
			if(currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(")") && (!newLine))
					{
			node = new DefaultMutableTreeNode(")");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
					}
			else
				error(11,newLine);
		} 
		else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getToken().equals("BINARY") && (!newLine)) {
			//SemanticAnalyzer.pushStack(tokens.get(currentToken).getToken());
			SemanticAnalyzer.pushStack("INTEGER");
			//-----------------------------------------------------
			node = new DefaultMutableTreeNode("binary" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} 
		else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("true") && (!newLine)) {
			SemanticAnalyzer.pushStack("BOOLEAN");
			//SemanticAnalyzer.pushStack(tokens.get(currentToken).getToken());
			//-----------------------------------------------------
			node = new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
			parent.add(node);
			CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("false") && (!newLine)) {
			SemanticAnalyzer.pushStack("BOOLEAN");
			//-----------------------------------------------------
			node = new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
			parent.add(node);
			CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}  
		else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getToken().equals("STRING") && (!newLine)) {
			SemanticAnalyzer.pushStack(tokens.get(currentToken).getToken());
			//-----------------------------------------------------
			node = new DefaultMutableTreeNode("string" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			CodeGenerator.addInstruction("LIT",tokens.get(currentToken).getWord() , "0");
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}
		else
			error(9,newLine);
		return newLine;
	}

	public static void error(int err,boolean isInNewLine) {
		int lineNum;
		if(currentToken<tokens.size())
		lineNum = tokens.get(currentToken).getLine();
		else
			lineNum=tokens.get(currentToken-1).getLine();
		
		boolean isNewLine=isInNewLine;
		switch (err) {
		case 1:
			gui.writeConsole("Line" + lineNum + ": expected {");
			errorcount++;
			while(currentToken < tokens.size() && !body_FollowSet.contains(tokens.get(currentToken).getWord()))
			{
				String currentWord=tokens.get(currentToken).getWord();
				String currentTokenValue=tokens.get(currentToken).getToken();
				
			if(body_FirstSet.contains(currentWord) || body_FirstSet.contains(currentTokenValue))
			{
			break;	
			}
			else
			{
				currentToken++;
			}
			}
			break;
		case 2:
			gui.writeConsole("Line" + lineNum + ": expected }");
			errorcount++;
			break;
		case 3:
			if(isInNewLine){
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected ;");
				errorcount++;
			}			
			else
			{
				gui.writeConsole("Line" + lineNum + ": expected ;");
				errorcount++;
			}
				
			isNewLine=isInNewLine;
			 
			/*
			while(currentToken < tokens.size() && (!isNewLine) && !body_FollowSet.contains(tokens.get(currentToken).getWord()))			
			{
				if(body_FirstSet.contains(tokens.get(currentToken).getWord())|| body_FirstSet.contains(tokens.get(currentToken).getToken()))
						{
					break;
						}
				else
				{
					currentToken++;
					if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
					{
					isNewLine=true;	
					}
				
				}
			} */
			break;
		case 4:
			if(isInNewLine)
			{
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected identifier or keyword");
				errorcount++;
			}
			else
			{
				gui.writeConsole("Line" + (lineNum) + ": expected identifier or keyword");
				errorcount++;
			}
				
			
			isNewLine=false;			
			while(currentToken < tokens.size() && (!body_FollowSet.contains(tokens.get(currentToken).getWord())) && (!isNewLine))
			{
				if(body_FirstSet.contains(tokens.get(currentToken).getWord()))
						{
					break;
						}
				else
				{
					currentToken++;
				}
				
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				isNewLine=true;	
				}
								
			}
			break;
		case 5:
			if(isInNewLine)
			{
			gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected =");
			errorcount++;
			}
			else
			{
				gui.writeConsole("Line" + (lineNum) + ":expected =");
				errorcount++;
			}
			isNewLine=isInNewLine;
			//while((!expression_FollowSet.contains(tokens.get(currentToken).getWord())) && (!isNewLine))					
			while(currentToken < tokens.size() && !(tokens.get(currentToken).getWord().contains(";") ||tokens.get(currentToken).getWord().contains(")")) && (!isNewLine))
			{
				if(expression_FirstSet.contains(tokens.get(currentToken).getToken()) || tokens.get(currentToken).getWord().equals("true") ||tokens.get(currentToken).getWord().equals("false"))
						{
					break;
						}
				else
				{
					currentToken++;
				}
				
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				isNewLine=true;	
				}
				
			}
			if(currentToken < tokens.size() && tokens.get(currentToken).getWord().contains(";") && (!isNewLine))
				flag=true;
			break;
		case 6:
			if(isInNewLine)
			{
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": (");
				errorcount++;
			}
			else
			{
			gui.writeConsole("Line" + lineNum + ": expected (");
			errorcount++;
			}
			isNewLine=isInNewLine;
			while(currentToken<tokens.size() && (! tokens.get(currentToken).getWord().contains(")")) && (!isNewLine))
					{
				if(expression_FirstSet.contains(tokens.get(currentToken).getToken()) || tokens.get(currentToken).getWord().equals("true") ||tokens.get(currentToken).getWord().equals("false") && (!isNewLine))
					break;
				else
					currentToken++;
			if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
			{
			isNewLine=true;	
			}
					}
			
			if(currentToken < tokens.size() && tokens.get(currentToken).getWord().contains(")"))
				flag=true;
			break;
		case 7:
			isNewLine=isInNewLine;
			if(isInNewLine)
				{
				
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected )");
				errorcount++;
				}
			else
			{
				gui.writeConsole("Line" + lineNum + ": expected )");
				errorcount++;
			}
			isInNewLine=isNewLine;
			while(currentToken<tokens.size() && (!program_FollowSet.contains(tokens.get(currentToken).getWord())) && (!isNewLine) )
			{
				if(program_FirstSet.contains(tokens.get(currentToken).getWord()))
					break;
				else
					currentToken++;
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				isNewLine=true;	
				}
			}
			break;
		case 8:
			if(isInNewLine)
				{
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected identifier");
				errorcount++;
				}
			else
			{
				gui.writeConsole("Line" + lineNum + ": expected identifier ");
				errorcount++;
			}
			isNewLine=isInNewLine;
			while(currentToken<tokens.size() && !(tokens.get(currentToken).getWord().equals(";")) && (!isInNewLine)) 
			{
				currentToken++;
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				isNewLine=true;	
				}
			}
			break;
			
		case 9:
			if(isInNewLine)
				{
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected value, identifier, (");
				errorcount++;
				}
			else
			{
				gui.writeConsole("Line" + lineNum + ": expected value, identifier, (");
				errorcount++;
			}
			
			isNewLine=isInNewLine;
			while(currentToken<tokens.size() && !expression_FollowSet.contains(tokens.get(currentToken).getWord()) && (!isNewLine))
					{
				currentToken++;
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				isNewLine=true;	
				}
					}
			
			break;
		case 10:
			gui.writeConsole("Line" + lineNum +": Extra Charecters after closing of program");
			errorcount++;
			break;
		case 11:
			if(isInNewLine)
				{
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected )");
				errorcount++;
				}
			else
			{
				gui.writeConsole("Line" + lineNum +": expected )");
				errorcount++;
			}
			
			isNewLine=isInNewLine;
			while(currentToken<tokens.size() && !(expression_FollowSet.contains(tokens.get(currentToken).getWord())) && (!isInNewLine))
			{
				currentToken++;
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				isNewLine=true;	
				}
			}
			
			break;
			
			
		case 12:
			if(isInNewLine)
				{
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected )");
				errorcount++;
				}
			else
			{
				gui.writeConsole("Line" + lineNum +": expected )");
				errorcount++;
			}
			
			isNewLine=isInNewLine;
			
			while(currentToken<tokens.size() && !(tokens.get(currentToken).getWord().equals(";")) && (!isInNewLine)) 
			{
				currentToken++;
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				isNewLine=true;	
				}
			}
			break;
		
		case 13:
			//missing identifier (switch
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
			break;
		case 18:
			break;
		case 19:
			break;
		}
	}

}
