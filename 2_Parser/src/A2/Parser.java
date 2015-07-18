package A2;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;


public class Parser {

	private static DefaultMutableTreeNode root;
	private static Vector<Token> tokens;
	private static int currentToken;
	private static Gui gui;
	
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
		tokens = t;
		currentToken = 0;						
		
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
		
		
		
		root = new DefaultMutableTreeNode("PROGRAM");
		gui = gui_passedObj;
		rule_PROGRAM(root);

		if(currentToken<tokens.size())
		{
			error(10,false);
		}
		return root;
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
		boolean newLine=false;
		node = new DefaultMutableTreeNode("identifier" + "("
				+ tokens.get(currentToken).getWord() + ")");
		parent.add(node);
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
		}
		flag=false;
		return newLine;
	}

	private static void rule_WHILE(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node;
        boolean newLine=false;
		node = new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
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
		if(!(newLine) && !flag)
        {
			flag=false;
		node = new DefaultMutableTreeNode("EXPRESSION");
		parent.add(node);
		rule_EXPRESSION(node);
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

	}

	private static void rule_IF(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node;
        boolean newLine=false;
        
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

		
			if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("else")) {
				node = new DefaultMutableTreeNode("else");
				parent.add(node);
				currentToken++;
				node = new DefaultMutableTreeNode("PROGRAM");
				parent.add(node);
				rule_PROGRAM(node);
			 
			
		}

	}
	
	
	
	private static void rule_RETURN(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("return");
		parent.add(node);
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
			node = new DefaultMutableTreeNode("identifier" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
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

		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		node = new DefaultMutableTreeNode("X");
		parent.add(node);
		newLine=rule_X(node);

		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("|") && (!newLine)) {
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
		}
		return newLine;

	}

	private static boolean rule_X(DefaultMutableTreeNode parent) {

		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		node = new DefaultMutableTreeNode("Y");
		parent.add(node);
		newLine=rule_Y(node);

		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("&") && (!newLine)) {
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
		}
		return newLine;

	}

	private static boolean rule_Y(DefaultMutableTreeNode parent) {

		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("!") && (!newLine)) {
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

		return newLine;
	}

	private static boolean rule_R(DefaultMutableTreeNode parent) {

		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		node = new DefaultMutableTreeNode("E");
		parent.add(node);
		newLine=rule_E(node);
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size() && (tokens.get(currentToken).getWord().equals("<")|| tokens.get(currentToken).getWord().equals(">") || tokens.get(currentToken).getWord().equals("==")|| tokens.get(currentToken).getWord().equals("!=")) && (!newLine)) 
		{
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
		}
		return newLine;
	}

	private static boolean rule_E(DefaultMutableTreeNode parent) {

		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		
		node = new DefaultMutableTreeNode("A");
		parent.add(node);
		newLine=rule_A(node);
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size()
				&& (tokens.get(currentToken).getWord().equals("+")
				|| tokens.get(currentToken).getWord().equals("-")) && (!newLine)) {
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
		}
return newLine;
	}

	private static boolean rule_A(DefaultMutableTreeNode parent) {

		DefaultMutableTreeNode node = new DefaultMutableTreeNode("B");
		boolean newLine=false;
		
		parent.add(node);
		newLine=rule_B(node);
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		while (currentToken < tokens.size()
				&& (tokens.get(currentToken).getWord().equals("*")				
				|| tokens.get(currentToken).getWord().equals("/") )&& (!newLine)) {
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
		}
return newLine;
	}

	private static boolean rule_B(DefaultMutableTreeNode parent) {

		DefaultMutableTreeNode node;
		boolean newLine=false;
		
		if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
		{
		newLine=true;	
		}
		
		if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("-") && (!newLine)) {
			node = new DefaultMutableTreeNode("-");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}
		
		node = new DefaultMutableTreeNode("C");
		parent.add(node);
		newLine=rule_C(node);

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
			node = new DefaultMutableTreeNode("integer" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}
		 else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getToken().equals("OCTAL")&&  (!newLine)) {
				node = new DefaultMutableTreeNode("identifier" + "("
						+ tokens.get(currentToken).getWord() + ")");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			} 
		
else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getToken().equals("IDENTIFIER") && (!newLine)) {
			node = new DefaultMutableTreeNode("identifier" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} 
		 else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getToken().equals("FLOAT") && (!newLine)) {
				node = new DefaultMutableTreeNode("float" + "("
						+ tokens.get(currentToken).getWord() + ")");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			} 
		 else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getToken().equals("HEXADECIMAL") && (!newLine)) {
				node = new DefaultMutableTreeNode("hexadecimal" + "("
						+ tokens.get(currentToken).getWord() + ")");
				parent.add(node);
				currentToken++;
				if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
				{
				newLine=true;	
				}
			} 
		 else if (currentToken < tokens.size()
					&& tokens.get(currentToken).getToken().equals("CHARACTER") && (!newLine)) {
				node = new DefaultMutableTreeNode("char" + "("
						+ tokens.get(currentToken).getWord() + ")");
				parent.add(node);
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
			node = new DefaultMutableTreeNode("binary" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		} 
		else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("true") && (!newLine)) {
			node = new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getWord().equals("false") && (!newLine)) {
			node = new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
			parent.add(node);
			currentToken++;
			if(currentToken < tokens.size() && tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine())
			{
			newLine=true;	
			}
		}  
		else if (currentToken < tokens.size()
				&& tokens.get(currentToken).getToken().equals("STRING") && (!newLine)) {
			node = new DefaultMutableTreeNode("string" + "("
					+ tokens.get(currentToken).getWord() + ")");
			parent.add(node);
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
			break;
		case 3:
			if(isInNewLine)
			gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected ;");
			else
				gui.writeConsole("Line" + lineNum + ": expected ;");
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
			gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected identifier or keyword");
			else
				gui.writeConsole("Line" + (lineNum) + ": expected identifier or keyword");
			
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
			gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected =");
			else
				gui.writeConsole("Line" + (lineNum) + ":expected =");
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
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": (");
			else
			gui.writeConsole("Line" + lineNum + ": expected (");
			
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
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected )");
			else
			gui.writeConsole("Line" + lineNum + ": expected )");
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
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected identifier");
			else
			gui.writeConsole("Line" + lineNum + ": expected identifier ");
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
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected value, identifier, (");
			else
			gui.writeConsole("Line" + lineNum + ": expected value, identifier, (");
			
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
			break;
		case 11:
			if(isInNewLine)
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected )");
			else
			gui.writeConsole("Line" + lineNum +": expected )");								
			
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
				gui.writeConsole("Line" + tokens.get(currentToken-1).getLine() + ": expected )");
			else
			gui.writeConsole("Line" + lineNum +": expected )");									
			
			isNewLine=isInNewLine;
			
			while(currentToken<tokens.size() && !(tokens.get(currentToken).getWord().equals(";")) && (!isInNewLine)) 
			{
				currentToken++;
				if(currentToken < tokens.size() && (tokens.get(currentToken).getLine()!=tokens.get(currentToken-1).getLine()))
				{
				isNewLine=true;	
				}
			}
		}
	}

}
