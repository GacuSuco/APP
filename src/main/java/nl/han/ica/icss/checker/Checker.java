package nl.han.ica.icss.checker;

import java.lang.ref.Reference;
import java.util.HashMap;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.parser.ICSSParser;

import static nl.han.ica.icss.ast.Operation.Operator.*;

public class Checker {

    public enum ExpressionType {
        PIXELVALUE,
        PERCENTAGE,
        COLORVALUE,
        BOOLVALUE,
        UNDEFINED
    }

    private HashMap<String,Assignment> symboltable;

	public void check(AST ast) {
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

    private void checkStyleRule(Stylerule stylerule) {
        for (ASTNode n: stylerule.getChildren()) {
            if (n.getClass() == Declaration.class){
                checkDeclaration((Declaration) n);
            }
        }
    }

    private void checkDeclaration(Declaration declaration) {
        System.out.println(declaration.expression.getClass().toString());
        if(declaration.expression.getClass() == VariableReference.class){
            if(getVariableReferenceType(declaration.expression.getNodeLabel()) == null) {
                declaration.expression.setError("Null reference!");
                System.out.println("Error: Null reference!");
            }
        }
        if (declaration.expression.getClass() == Operation.class) {
            if(getOperationReferenceType((Operation) declaration.expression) == NullPointerException.class) {
                declaration.expression.setError("Null reference!");
                System.out.println("Error: Null reference!");
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
        System.out.println("stop iets in symbol");
        symboltable.put(assignment.name.getNodeLabel(), assignment);
    }


}
