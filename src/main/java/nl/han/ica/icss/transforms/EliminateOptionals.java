package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;

public class EliminateOptionals implements Transform {
    @Override
    public void apply(AST ast) {
        // lijst met alle conditional stylerule die condiie false hebben
        java.util.Stack<ASTNode> garbage = new java.util.Stack<>();

        // zoek naar false conditional stylerules
        for (ASTNode n: ast.root.getChildren()) {
            if(n.getClass() == Stylerule.class){
                if(((Stylerule) n).condition != null){
                    if(!((BoolLiteral) ((Stylerule) n).condition).value){
                        garbage.push(n);
                    }
                }
            }
        }

        // delete alle childs uit root die in gabarge zitten.
        garbage.forEach((node) ->  ast.root.removeChild(node));
    }

}
