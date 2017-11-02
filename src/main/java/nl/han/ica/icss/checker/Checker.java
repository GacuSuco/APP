package nl.han.ica.icss.checker;

import java.util.HashMap;
import java.util.HashSet;

import nl.han.ica.icss.ast.*;

public class Checker {
    public enum ExpressionType { // You have no power here
        PIXELVALUE,
        PERCENTAGE,
        COLORVALUE,
        BOOLVALUE,
        UNDEFINED
    }

    private HashMap<String,Assignment> symboltable;
    private HashSet<String> pixelDeclarations;
    private HashSet<String> percentageDeclarations;
    private HashSet<String> colorDeclarations;

	public void check(AST ast) {
	    // building style type declarations
        this.initStyleDeclarationTypes();

	    //Clear symbol table
        symboltable = new HashMap<>();

        for (ASTNode n: ast.root.getChildren()) {
            if (n.getClass() == Assignment.class){
                this.addAssignment((Assignment) n);
            }

            if(n.getClass() == Stylerule.class){
                this.checkStyleRule((Stylerule) n);
            }
        }

        //Save the symbol table.
        ast.symboltable = symboltable;
        //Save the verdict
		if(ast.getErrors().isEmpty()) {
            ast.checked = true;
        }
	}

    private void initStyleDeclarationTypes() {
	    // HashSets vullen alle ondersteunde declaraties
	    this.pixelDeclarations = new HashSet<>();
	    this.percentageDeclarations = new HashSet<>();
	    this.colorDeclarations = new HashSet<>();

	    this.pixelDeclarations.add("height");
        this.pixelDeclarations.add("width");
        this.pixelDeclarations.add("padding-top");
        this.pixelDeclarations.add("padding-right");
        this.pixelDeclarations.add("padding-bottom");
        this.pixelDeclarations.add("padding-left");
        this.pixelDeclarations.add("margin-top");
        this.pixelDeclarations.add("margin-right");
        this.pixelDeclarations.add("margin-bottom");
        this.pixelDeclarations.add("margin-left");

        this.percentageDeclarations.add("height");
        this.percentageDeclarations.add("width");
        this.percentageDeclarations.add("padding-top");
        this.percentageDeclarations.add("padding-right");
        this.percentageDeclarations.add("padding-bottom");
        this.percentageDeclarations.add("padding-left");
        this.percentageDeclarations.add("margin-top");
        this.percentageDeclarations.add("margin-right");
        this.percentageDeclarations.add("margin-bottom");
        this.percentageDeclarations.add("margin-left");

        this.colorDeclarations.add("background-color");
        this.colorDeclarations.add("color");
    }

    private void checkStyleRule(Stylerule stylerule) {
	    if(stylerule.condition != null){
	        this.checkCondition(stylerule.condition);
        }
        for (ASTNode n: stylerule.getChildren()) {
            if (n.getClass() == Declaration.class){
                checkDeclaration((Declaration) n);
            }
        }
    }

    private boolean checkCondition(Expression condition) {
	    Class<?> ref = condition.getClass();
	    // check of condition een operation is. Zo ja, haal de literal op die gebruikt wordt.
        // als er een GT of LT gebruikt wordt zal het een booleanLiteral returnen als het een geldige condition is.
        if (ref == Operation.class){
            ref = getOperationReferenceType((Operation) condition);
        }
        // check of condition een VariableReference is. Zo ja, haal de literal op die toegewezen is aan de var ref.
        if(ref == VariableReference.class) {
            ref = getVariableReferenceType(condition.getNodeLabel());
        }
        if (ref == null){
            condition.setError("Null reference");
            return false;
        }
        else {
            // controleer of ref een boolliteral is, zo niet is het geen geldige condition
            if (ref != BoolLiteral.class){
                condition.setError("Invalid condition: expected a boolean ref:" + ref.getSimpleName());
                return false;
            }
            else {
                return true;
            }
        }
    }

    private void checkDeclaration(Declaration declaration) {
	    Class<?> ref = declaration.expression.getClass();
	    // als expression een variableReference is, haal de gebruikte literal op.
	    if(declaration.expression.getClass() == VariableReference.class){
            ref = getVariableReferenceType(declaration.expression.getNodeLabel());
        }
        // als expression een operation is. haal de gebruikte literal op.
        if (declaration.expression.getClass() == Operation.class) {
            ref = getOperationReferenceType((Operation) declaration.expression);
        }
        if(ref == null) {
            declaration.expression.setError("Null reference!");
        }
        else {
	        // controleer of de literal wel geldig is voor de attribute.
            if (ref == PixelLiteral.class && !pixelDeclarations.contains(declaration.property)) {
                declaration.expression.setError("Incompatible type in declaration: \"" + ref.getSimpleName() + "\" " +
                        "cannot be assigned to \"" + declaration.property + "\".");
            }
            if (ref == PercentageLiteral.class && !percentageDeclarations.contains(declaration.property)) {
                declaration.expression.setError("Incompatible type in declaration: \"" + ref.getSimpleName() + "\" " +
                        "cannot be assigned to \"" + declaration.property + "\".");
            }
            if (ref == ColorLiteral.class && !colorDeclarations.contains(declaration.property)) {
                declaration.expression.setError("Incompatible type in declaration: \"" + ref.getSimpleName() + "\" " +
                        "cannot be assigned to \"" + declaration.property + "\".");
            }
        }
    }

    private Class<?> getVariableReferenceType(String variableReference){
	    // check of varref is gedeclareert.
        if(symboltable.containsKey(variableReference)){
            // als varref een Operation is als expression. haal de gebruikte literal op.
            if (symboltable.get(variableReference).expression.getClass() == Operation.class){
                return getOperationReferenceType((Operation) symboltable.get(variableReference).expression);
            }
            return symboltable.get(variableReference).expression.getClass();
        } else{
            return null;
        }
    }

    private Class<?> getOperationReferenceType(Operation operation) {
        // als operator een + - < > = is controleer dan of literals wel overeen komen.
        if (operation.operator == Operation.Operator.PLUS || operation.operator == Operation.Operator.MIN ||
                operation.operator == Operation.Operator.LT || operation.operator == Operation.Operator.GT ||
                operation.operator == Operation.Operator.EQ){
            Class<?> refLeft = operation.lhs.getClass();
            Class<?> refRight = operation.rhs.getClass();

            // mogelijke recursieve aanroep. als lhs een varref is haal dan de literal op.
            if (refLeft == VariableReference.class){
                refLeft = getVariableReferenceType(operation.lhs.getNodeLabel());
            }
            // recursieve aanroep. als lhs een operation is, roep zichzelf aan om gebruikte literal te bemachtigen.
            if (refLeft == Operation.class){
                refLeft = getOperationReferenceType((Operation) operation.lhs);
            }
            // mogelijke recursieve aanroep. als rhs een varref is haal dan de literal op.
            if (refRight == VariableReference.class){
                refRight = getVariableReferenceType(operation.rhs.getNodeLabel());
            }
            // recursieve aanroep. als rhs een operation is, roep zichzelf aan om gebruikte literal te bemachtigen.
            if (refRight == Operation.class){
                refRight = getOperationReferenceType((Operation) operation.rhs);
            }

            // als lhs en rhs van het zelfde type zijn is het een geldige operation.
            if(refLeft == refRight){
                // als operator een lt of rt bevat, return boolliteral
                // lhs en rhs zijn van het zelfde type dus is het een geldige operation
                // anders return gebruikte literal.
                if (operation.operator == Operation.Operator.LT || operation.operator == Operation.Operator.GT
                        || operation.operator == Operation.Operator.EQ){
                    return BoolLiteral.class;
                }
                return refLeft;
            }
            else {
                if (refLeft != null && refRight != null){
                    operation.setError("Operator \"" + operation.operator.getOperator() + "\" " +
                            "could not be applied on \""+ refLeft.getSimpleName() + "\" and \""+
                            refRight.getSimpleName() + "\".");
                }
                else {
                    if (refLeft == null){
                        operation.lhs.setError("Null reference!");
                    }
                    if (refRight == null){
                        operation.rhs.setError("Null reference!");
                    }
                }
                return null;
            }
        }
        // als operation een AND of OR bevat. bevat het meerdere conditions
        if (operation.operator == Operation.Operator.AND || operation.operator == Operation.Operator.OR){
            // controler of linkerkant een geldige condition is.
            if(!checkCondition(operation.lhs)){
                operation.lhs.setError("Invalid Boolean expression!");
                return null;
            }
            // controler of rechterkant een geldige condition is.
            if(!checkCondition(operation.rhs)){
                operation.rhs.setError("Invalid Boolean expression!");
                return null;
            }
            return BoolLiteral.class;
        }
        return null;
    }

    private void addAssignment(Assignment assignment ){
        symboltable.put(assignment.name.getNodeLabel(), assignment);
    }

}
