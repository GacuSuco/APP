package nl.han.ica.icss.parser;

import java.util.Stack;

import nl.han.ica.icss.ast.*;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;
	private Stack<ASTNode> currentContainer; //Yes, this is a hint...

	public ASTListener() {
		ast = new AST();
		currentContainer = new Stack<>();
	}

    public AST getAST() {
        return ast;
    }

    @Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx){
		currentContainer.push(new Stylesheet());
	}
	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		this.ast.setRoot((Stylesheet) currentContainer.pop());
	}

	@Override
	public void enterStyleRuleSet(ICSSParser.StyleRuleSetContext ctx) {
		currentContainer.push(new Stylerule());
	}
	@Override
	public void exitStyleRuleSet(ICSSParser.StyleRuleSetContext ctx) {
		Stylerule stylerule = ((Stylerule) currentContainer.pop());
		currentContainer.peek().addChild(stylerule);
	}

	@Override
	public void enterSelectorTag(ICSSParser.SelectorTagContext ctx) {
		((Stylerule) currentContainer.peek()).selector = new TagSelector(ctx.getText());
	}

	@Override
	public void enterCssClass(ICSSParser.CssClassContext ctx) {
		((Stylerule) currentContainer.peek()).selector = new ClassSelector(ctx.getText());
	}

	@Override
	public void enterCssID(ICSSParser.CssIDContext ctx) {
		((Stylerule) currentContainer.peek()).selector = new IdSelector(ctx.getText());
	}

	@Override
	public void enterStyleDeclaration(ICSSParser.StyleDeclarationContext ctx) {
		currentContainer.push(new Declaration());
	}

	@Override
	public void exitStyleDeclaration(ICSSParser.StyleDeclarationContext ctx) {
		Declaration declaration = ((Declaration) currentContainer.pop());
		currentContainer.peek().addChild(declaration);
	}

	@Override
	public void enterAttribute(ICSSParser.AttributeContext ctx) {
		((Declaration) currentContainer.peek()).property = ctx.getText();
	}

	@Override
	public void enterColor(ICSSParser.ColorContext ctx) {
		currentContainer.peek().addChild(new ColorLiteral(ctx.getText()));
	}
	@Override
	public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		currentContainer.peek().addChild(new PixelLiteral(ctx.getText()));
	}
	@Override
	public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		currentContainer.peek().addChild(new PercentageLiteral(ctx.getText()));
	}

	@Override
	public void enterVariableDeclaration(ICSSParser.VariableDeclarationContext ctx) {
		currentContainer.push(new Declaration());
	}

	@Override
	public void exitVariableDeclaration(ICSSParser.VariableDeclarationContext ctx) {
		Declaration declaration = ((Declaration) currentContainer.pop());
		currentContainer.peek().addChild(declaration);
	}

	// Shit omgooien !!! idee nu is dat variable, color, pixal, en perc eerst worden toegevoegt. daarnaa wordt er gekeken of operation nodig is.

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		if (((Declaration) currentContainer.peek()).property == null){
			((Declaration) currentContainer.peek()).property = ctx.getText();
		}
		else {
			currentContainer.peek().addChild(new VariableReference(ctx.getText()));
		}
	}

	@Override
	public void enterOperator(ICSSParser.OperatorContext ctx) {
		currentContainer.add(new Operation(Operation.Operator.fromString(ctx.getText())));
	}


	@Override
	public void enterExpression(ICSSParser.ExpressionContext ctx) {
		System.out.println(ctx.getChildCount());
		if (ctx.getChildCount() > 1) { // wanneer count > is dan 1 heeft een operator
			for (int i = 0; i < ctx.getChildCount(); i++) {
				if (i% 2 != 0){
					System.out.println("\ni'm an operator");
					System.out.println(ctx.getChild(i).getText());
				}else {
					System.out.println("\ni'm an value\n" + ctx.getChild(i).getText());
				}
			}
		}


		super.enterExpression(ctx);
	}


}
