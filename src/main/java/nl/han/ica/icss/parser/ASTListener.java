package nl.han.ica.icss.parser;

import java.util.Stack;

import nl.han.ica.icss.ast.*;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;
	private Stack<ASTNode> currentContainer; //Yes, this is a hint... OK

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
		currentContainer.push(new ColorLiteral(ctx.getText()));
	}
	@Override
	public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		currentContainer.push(new PixelLiteral(ctx.getText()));
	}
	@Override
	public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		currentContainer.push(new PercentageLiteral(ctx.getText()));
	}

	@Override
	public void enterBool(ICSSParser.BoolContext ctx) {
		currentContainer.push(new BoolLiteral(ctx.getText()));
	}

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		if (ctx.parent.getChildCount() != 1){ //maakt deel uit van de tree
			((Declaration) currentContainer.peek()).property = ctx.getText();
		}
		else {
			currentContainer.push(new VariableReference(ctx.getText()));
		}
	}

	@Override
	public void enterOperator(ICSSParser.OperatorContext ctx) {
		currentContainer.add(new Operation(Operation.Operator.fromString(ctx.getText())));
	}

	@Override
	public void enterLogicalOperator(ICSSParser.LogicalOperatorContext ctx) {
		currentContainer.add(new Operation(Operation.Operator.fromString(ctx.getText())));
	}

	@Override
	public void enterComparator(ICSSParser.ComparatorContext ctx) {
		currentContainer.add(new Operation(Operation.Operator.fromString(ctx.getText())));
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
	@Override
	public void exitExpression(ICSSParser.ExpressionContext ctx) {
		// kijk naar het aantal children
		// als een voeg direct to
		// als meer dan 1 check voor operators op even getalen;
		// pop die shit
		if (ctx.getChildCount() > 1){

			for (int i = 2; i < ctx.getChildCount(); i+=2) {
				ASTNode right = this.currentContainer.pop();
				ASTNode operator = this.currentContainer.pop();
				ASTNode left = this.currentContainer.pop();

				((Operation)operator).rhs = (Expression) right;
				((Operation)operator).lhs = (Expression) left;

				if (i + 2 < ctx.getChildCount()){
					this.currentContainer.push(operator);
				}else {
					this.currentContainer.peek().addChild(operator);
				}
			}
		}
		else{
			ASTNode node = this.currentContainer.pop();
			this.currentContainer.peek().addChild(node);
		}
	}

	@Override
	public void exitBooleanExpression(ICSSParser.BooleanExpressionContext ctx) {
		if (ctx.getChildCount() > 1) {
			ASTNode right = this.currentContainer.pop();
			ASTNode operator = this.currentContainer.pop();
			ASTNode left = this.currentContainer.pop();

			((Operation)operator).rhs = (Expression) right;
			((Operation)operator).lhs = (Expression) left;

			this.currentContainer.push(operator);
		}
	}

	@Override
	public void exitCondition(ICSSParser.ConditionContext ctx) {
		ASTNode node = this.currentContainer.pop();
		this.currentContainer.peek().addChild(node);
	}
}
