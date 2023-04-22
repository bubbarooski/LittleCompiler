// Generated from Little.g4 by ANTLR 4.12.0

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides an empty implementation of {@link LittleListener},
 * which can be extended to create a listener which only needs to handle a subset
 * of the available methods.
 */
@SuppressWarnings("CheckReturnValue")
public class SymbolTableBuilder implements LittleListener {

	private ArrayList<SymbolTable> symbolTables = new ArrayList<>();
	private Stack<SymbolTable> symbolTableStack = new Stack<>();
	private String currentVarType, currentID;
	private boolean startRecording = false;
	private int blockCount = 1;
	private String testOutFileName = "test.out";
	private File file = new File(testOutFileName);

	private boolean parenEncountered = false;

	private IRCGenerator ircGenerator = new IRCGenerator();

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterProgram(LittleParser.ProgramContext ctx) {
		// push global symbol table to the stack
		addNewSymbolTable("GLOBAL");
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitProgram(LittleParser.ProgramContext ctx) {
		// should pop global symbol table
		symbolTableStack.pop();
		try {
			//printSymbolTables();
		} catch (Exception e){

		}
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterId(LittleParser.IdContext ctx) {
		currentID = ctx.IDENTIFIER().getText();
		// temporary fix for non string int or float ids
		if(currentVarType == null) return;
		if(startRecording && (currentVarType.equals(LittleObject.TYPE_INT) || currentVarType.equals(LittleObject.TYPE_FLOAT))){
			symbolTableStack.peek().addEntry(currentID, new LittleObject(currentVarType, null));
		}
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitId(LittleParser.IdContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterPgm_body(LittleParser.Pgm_bodyContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitPgm_body(LittleParser.Pgm_bodyContext ctx) {
		ircGenerator.generateRET();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterDecl(LittleParser.DeclContext ctx) {

	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitDecl(LittleParser.DeclContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterString_decl(LittleParser.String_declContext ctx) {
		currentVarType = LittleObject.TYPE_STRING;
		// lhs
		ircGenerator.pushToken(new LittleObject(ctx.id().getText(), currentVarType, null));
		// :=
		ircGenerator.pushToken(new LittleObject(":=", null, null));
		// "STRING VALUE"
		ircGenerator.pushToken(new LittleObject(ctx.str().getText(), LittleObject.TYPE_STRING, null));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitString_decl(LittleParser.String_declContext ctx) {
		// ;
		ircGenerator.pushToken(new LittleObject(";", null, null));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterStr(LittleParser.StrContext ctx) {
		symbolTableStack.peek().addEntry(currentID, new LittleObject(currentVarType, ctx.STRINGLITERAL().getText()));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitStr(LittleParser.StrContext ctx) {

	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterVar_decl(LittleParser.Var_declContext ctx) {
		currentVarType = ctx.var_type().getText();
		startRecording = true;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitVar_decl(LittleParser.Var_declContext ctx) {
		startRecording = false;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterVar_type(LittleParser.Var_typeContext ctx) {

	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitVar_type(LittleParser.Var_typeContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterAny_type(LittleParser.Any_typeContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitAny_type(LittleParser.Any_typeContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterId_list(LittleParser.Id_listContext ctx) {
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitId_list(LittleParser.Id_listContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterId_tail(LittleParser.Id_tailContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitId_tail(LittleParser.Id_tailContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterParam_decl_list(LittleParser.Param_decl_listContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitParam_decl_list(LittleParser.Param_decl_listContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterParam_decl(LittleParser.Param_declContext ctx) {
		currentVarType = ctx.var_type().getText();
		startRecording = true;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitParam_decl(LittleParser.Param_declContext ctx) {
		startRecording = false;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterParam_decl_tail(LittleParser.Param_decl_tailContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitParam_decl_tail(LittleParser.Param_decl_tailContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterFunc_declarations(LittleParser.Func_declarationsContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitFunc_declarations(LittleParser.Func_declarationsContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterFunc_decl(LittleParser.Func_declContext ctx) {
		addNewSymbolTable(ctx.id().getText());
		ircGenerator.generateLABEL(ctx.id().getText());
		ircGenerator.generateLINK();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitFunc_decl(LittleParser.Func_declContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterFunc_body(LittleParser.Func_bodyContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitFunc_body(LittleParser.Func_bodyContext ctx) {
		symbolTableStack.pop();
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterStmt_list(LittleParser.Stmt_listContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitStmt_list(LittleParser.Stmt_listContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterStmt(LittleParser.StmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitStmt(LittleParser.StmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterBase_stmt(LittleParser.Base_stmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitBase_stmt(LittleParser.Base_stmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterAssign_stmt(LittleParser.Assign_stmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitAssign_stmt(LittleParser.Assign_stmtContext ctx) {
		ircGenerator.pushToken(new LittleObject(";", null, null));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterAssign_expr(LittleParser.Assign_exprContext ctx) {
		// lhs
		ircGenerator.pushToken(new LittleObject(ctx.id().IDENTIFIER().getText(), null, null));
		// :=
		ircGenerator.pushToken(new LittleObject(":=", null, null));
		// rhs in other methods such as primary, mulop, etc
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitAssign_expr(LittleParser.Assign_exprContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterRead_stmt(LittleParser.Read_stmtContext ctx) {
		String id;
		LittleParser.Id_listContext parent = ctx.id_list();
		if (!parent.id().isEmpty()) {
			id = parent.id().IDENTIFIER().toString();
			ircGenerator.determineRead(id, symbolTableStack.peek());
		} else return;

		LittleParser.Id_tailContext tailParent = parent.id_tail();

		while(true){
			if (!tailParent.id().isEmpty()) {
				id = tailParent.id().get(0).getText();
				ircGenerator.determineRead(id, symbolTableStack.peek());
				tailParent = tailParent.id_tail(0);
			} else break;
		}
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitRead_stmt(LittleParser.Read_stmtContext ctx) {

	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterWrite_stmt(LittleParser.Write_stmtContext ctx) {
		String id;
		LittleParser.Id_listContext parent = ctx.id_list();
		if (!parent.id().isEmpty()) {
			id = parent.id().IDENTIFIER().toString();
			ircGenerator.determineWrite(id, symbolTableStack.peek());
		} else return;

		LittleParser.Id_tailContext tailParent = parent.id_tail();

		while(true){
			if (!tailParent.id().isEmpty()) {
				id = tailParent.id().get(0).getText();
				ircGenerator.determineWrite(id, symbolTableStack.peek());
				tailParent = tailParent.id_tail(0);
			} else break;
		}
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitWrite_stmt(LittleParser.Write_stmtContext ctx) {
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterReturn_stmt(LittleParser.Return_stmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitReturn_stmt(LittleParser.Return_stmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExpr(LittleParser.ExprContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr(LittleParser.ExprContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExpr_prefix(LittleParser.Expr_prefixContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr_prefix(LittleParser.Expr_prefixContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterFactor(LittleParser.FactorContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitFactor(LittleParser.FactorContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterFactor_prefix(LittleParser.Factor_prefixContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitFactor_prefix(LittleParser.Factor_prefixContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterPostfix_expr(LittleParser.Postfix_exprContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitPostfix_expr(LittleParser.Postfix_exprContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterCall_expr(LittleParser.Call_exprContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitCall_expr(LittleParser.Call_exprContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExpr_list(LittleParser.Expr_listContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr_list(LittleParser.Expr_listContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterExpr_list_tail(LittleParser.Expr_list_tailContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitExpr_list_tail(LittleParser.Expr_list_tailContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterPrimary(LittleParser.PrimaryContext ctx) {
		String primary = ctx.getText();
		String type = symbolTableStack.peek().lookupType(primary);
		if (type == null) {
			if (primary.contains(".")) {
				type = LittleObject.TYPE_FLOAT;
			} else {
				type = LittleObject.TYPE_INT;
			}
		}

		if(primary.contains("(")) {
			ircGenerator.pushToken(new LittleObject("(", null, null));
			parenEncountered = true;
		}
		else ircGenerator.pushToken(new LittleObject(primary, type, null));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitPrimary(LittleParser.PrimaryContext ctx) {
		if(parenEncountered){
			ircGenerator.pushToken(new LittleObject(")", null, null));
			parenEncountered = false;
		}
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterAddop(LittleParser.AddopContext ctx) {
		ircGenerator.pushToken(new LittleObject(ctx.getText(), null, null));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitAddop(LittleParser.AddopContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterMulop(LittleParser.MulopContext ctx) {
		ircGenerator.pushToken(new LittleObject(ctx.getText(), null, null));
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitMulop(LittleParser.MulopContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterIf_stmt(LittleParser.If_stmtContext ctx) {
		addNewSymbolTable("BLOCK " + blockCount);
		blockCount++;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitIf_stmt(LittleParser.If_stmtContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterElse_part(LittleParser.Else_partContext ctx) {
		if (ctx.decl().size() != 0 && ctx.stmt_list().size() != 0) {
			addNewSymbolTable("BLOCK " + blockCount);
			blockCount++;
		}
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitElse_part(LittleParser.Else_partContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterCond(LittleParser.CondContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitCond(LittleParser.CondContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterCompop(LittleParser.CompopContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitCompop(LittleParser.CompopContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterWhile_stmt(LittleParser.While_stmtContext ctx) {
		addNewSymbolTable("BLOCK " + blockCount);
		blockCount++;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitWhile_stmt(LittleParser.While_stmtContext ctx) { }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void enterEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void exitEveryRule(ParserRuleContext ctx) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitTerminal(TerminalNode node) { }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 */
	@Override public void visitErrorNode(ErrorNode node) { }

	public void printIRC(){
		for(IRCode declareIRCode: ircGenerator.getDeclareIRC()){
			System.out.println(";" + declareIRCode);
		}
		for(IRCode irCode : ircGenerator.getIRC()){
			System.out.println(";" + irCode);
		}
	}

	public void printSymbolTables() throws Exception {
		int symbolTableCounter = 1;
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
		for (SymbolTable symbolTable : symbolTables) {
			try{
				if(symbolTableCounter != symbolTables.size())
					symbolTable.printTable(outputStreamWriter, true);
				else
					symbolTable.printTable(outputStreamWriter, false);
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
			symbolTableCounter++;
		}
		outputStreamWriter.close();
	}

	private void addNewSymbolTable(String tableName){
		SymbolTable symbolTable = new SymbolTable(tableName);
		if(!symbolTableStack.empty()) {
			symbolTable.setParentSymbolTable(symbolTableStack.peek()); // add top symbol table as parent of new symbol table
			symbolTableStack.peek().addEntry(symbolTable); // add new symbol table as a child of symbol table on top of stack
		}
		symbolTableStack.push(symbolTable);
		symbolTables.add(symbolTable);
	}

	public IRCode[] getIRCode(){
		IRCode[] irCodeArray = new IRCode[ircGenerator.getDeclareIRC().size()+ircGenerator.getIRC().size()];
		int i = 0;
		for(IRCode declareIRCode: ircGenerator.getDeclareIRC()){
			irCodeArray[i] = declareIRCode;
			i++;
		}
		for(IRCode irCode : ircGenerator.getIRC()){
			irCodeArray[i] = irCode;
			i++;
		}
		return irCodeArray;
	}
}