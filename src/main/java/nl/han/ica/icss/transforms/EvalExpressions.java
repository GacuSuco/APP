package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;

import java.util.HashMap;

public class EvalExpressions implements Transform {

    private HashMap<String,Assignment> symboltable; //Another hint...



    // check bij alle ecpression de gebruikte litteral
    // als expresion van declaration gelijk is aan varref wordt expresion de declaration

    @Override
    public void apply(AST ast) {
        symboltable = ast.symboltable;

        for (ASTNode n: ast.root.getChildren()) {
            if(n.getClass() == Stylerule.class){
                this.checkStyleRule((Stylerule) n);
            }
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
        while (declaration.expression.getClass() == VariableReference.class){
            declaration.expression = symboltable.get(declaration.expression.getNodeLabel()).expression;
            System.out.println("varref in " + declaration.getNodeLabel() + " becomes " + declaration.expression.getClass().getSimpleName());

            if (declaration.expression.getClass() == Operation.class){
                System.out.println("Operation in " + declaration.expression.getClass().getSimpleName());
            }
        }
    }
}
