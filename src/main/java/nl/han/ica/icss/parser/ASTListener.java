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
	public void enterDeclarations(ICSSParser.DeclarationsContext ctx) {
		currentContainer.push(new Declaration());
	}

	@Override
	public void exitDeclarations(ICSSParser.DeclarationsContext ctx) {
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
}
