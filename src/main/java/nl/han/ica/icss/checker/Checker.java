package nl.han.ica.icss.checker;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.HashSet;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.parser.ICSSParser;

import static nl.han.ica.icss.ast.Operation.Operator.*;

public class Checker {

    public enum ExpressionType { //???
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
        for (ASTNode n: stylerule.getChildren()) {
            if (n.getClass() == Declaration.class){
                checkDeclaration((Declaration) n);
            }
        }
    }

    private void checkDeclaration(Declaration declaration) {
	    Class<?> ref = declaration.expression.getClass();
	    if(declaration.expression.getClass() == VariableReference.class){
            ref = getVariableReferenceType(declaration.expression.getNodeLabel());
        }
        if (declaration.expression.getClass() == Operation.class) {
            ref = getOperationReferenceType((Operation) declaration.expression);
        }
        if(ref == null) {
            declaration.expression.setError("Null reference!");
        }
        else {
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
        if(symboltable.containsKey(variableReference)){
            if (symboltable.get(variableReference).expression.getClass() == Operation.class){
                return getOperationReferenceType((Operation) symboltable.get(variableReference).expression);
            }
            return symboltable.get(variableReference).expression.getClass();
        } else{
            return null;
        }
    }

    private Class<?> getOperationReferenceType(Operation operation) {
        if (operation.operator == PLUS ||
                operation.operator == MIN){
            Class<?> refLeft = operation.lhs.getClass();
            Class<?> refRight = operation.rhs.getClass();

            if (refLeft == VariableReference.class){
                refLeft = getVariableReferenceType(operation.lhs.getNodeLabel());
            }
            if (refLeft == Operation.class){
                refLeft = getOperationReferenceType((Operation) operation.lhs);
            }
            if (refRight == VariableReference.class){
                refRight = getVariableReferenceType(operation.rhs.getNodeLabel());
            }
            if (refRight == Operation.class){
                refRight = getOperationReferenceType((Operation) operation.rhs);
            }

            if(refLeft == refRight){
                return operation.lhs.getClass();
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
        return null;
    }

    private void addAssignment(Assignment assignment ){
        symboltable.put(assignment.name.getNodeLabel(), assignment);
    }

}
