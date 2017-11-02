package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.*;

import java.util.HashMap;

public class EvalExpressions implements Transform {

    private HashMap<String,Assignment> symboltable; //Another hint...

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
        if(stylerule.condition != null){
            if (stylerule.condition.getClass() == VariableReference.class) {
                stylerule.condition = checkVariableReference(stylerule.condition.getNodeLabel());
            }
            if (stylerule.condition.getClass() == Operation.class){
                stylerule.condition = checkOperation(((Operation)stylerule.condition));
            }
        }
        for (ASTNode n: stylerule.getChildren()) {
            if (n.getClass() == Declaration.class){
                checkDeclaration((Declaration) n);
            }
        }
    }
    private void checkDeclaration(Declaration declaration) {
        if (declaration.expression.getClass() == VariableReference.class) {
            declaration.expression = checkVariableReference(declaration.expression.getNodeLabel());
        }
        if (declaration.expression.getClass() == Operation.class){
            declaration.expression = checkOperation(((Operation)declaration.expression));
        }
    }


    private Literal checkOperation(Operation operation){
        if (operation.lhs.getClass() == Operation.class){
            operation.lhs = checkOperation((Operation) operation.lhs);
        }
        if (operation.lhs.getClass() == VariableReference.class){
            operation.lhs = checkVariableReference(operation.lhs.getNodeLabel());
        }
        if (operation.rhs.getClass() == Operation.class){
            operation.rhs = checkOperation((Operation) operation.rhs);
        }
        if (operation.rhs.getClass() == VariableReference.class){
            operation.rhs = checkVariableReference(operation.rhs.getNodeLabel());
        }

        switch (operation.operator){
            case PLUS:
                if (operation.lhs.getClass() == PixelLiteral.class){
                    return new PixelLiteral(((PixelLiteral)operation.lhs).value + ((PixelLiteral)operation.rhs).value);
                }
                if (operation.lhs.getClass() == PercentageLiteral.class){
                    return new PercentageLiteral(((PercentageLiteral)operation.lhs).value + ((PercentageLiteral)operation.rhs).value);
                }
            case MIN:
                if (operation.lhs.getClass() == PixelLiteral.class){
                    return new PixelLiteral(((PixelLiteral)operation.lhs).value - ((PixelLiteral)operation.rhs).value);
                }
                if (operation.lhs.getClass() == PercentageLiteral.class){
                    return new PercentageLiteral(((PercentageLiteral)operation.lhs).value - ((PercentageLiteral)operation.rhs).value);
                }
            case EQ:
                if (operation.lhs.getClass() == PixelLiteral.class){
                    return new BoolLiteral(((PixelLiteral)operation.lhs).value == ((PixelLiteral)operation.rhs).value);
                }
                if (operation.lhs.getClass() == PercentageLiteral.class){
                    return new BoolLiteral(((PixelLiteral)operation.lhs).value == ((PixelLiteral)operation.rhs).value);
                }
                if (operation.lhs.getClass() == ColorLiteral.class){
                    return new BoolLiteral(((ColorLiteral)operation.lhs).value.equals(((ColorLiteral)operation.rhs).value));
                }
                if (operation.lhs.getClass() == BoolLiteral.class){
                    return new BoolLiteral(((BoolLiteral)operation.lhs).value == ((BoolLiteral)operation.rhs).value);
                }
            case GT:
                if (operation.lhs.getClass() == PixelLiteral.class){
                    return new BoolLiteral(((PixelLiteral)operation.lhs).value > ((PixelLiteral)operation.rhs).value);
                }
                if (operation.lhs.getClass() == PercentageLiteral.class){
                    return new BoolLiteral(((PixelLiteral)operation.lhs).value > ((PixelLiteral)operation.rhs).value);
                }
            case LT:
                if (operation.lhs.getClass() == PixelLiteral.class){
                    return new BoolLiteral(((PixelLiteral)operation.lhs).value < ((PixelLiteral)operation.rhs).value);
                }
                if (operation.lhs.getClass() == PercentageLiteral.class){
                    return new BoolLiteral(((PixelLiteral)operation.lhs).value < ((PixelLiteral)operation.rhs).value);
                }
            case OR:
                if (operation.lhs.getClass() == BoolLiteral.class  && operation.rhs.getClass() == BoolLiteral.class){
                    return new BoolLiteral(((BoolLiteral)operation.lhs).value || ((BoolLiteral)operation.rhs).value);
                }
            case AND:
                if (operation.lhs.getClass() == BoolLiteral.class  && operation.rhs.getClass() == BoolLiteral.class){
                    return new BoolLiteral(((BoolLiteral)operation.lhs).value && ((BoolLiteral)operation.rhs).value);
                }
            default:
                return null;
        }
    }

    private Literal checkVariableReference(String variablereference) {
        if (symboltable.get(variablereference).expression.getClass() == Operation.class){
            return checkOperation(((Operation)symboltable.get(variablereference).expression));
        }
        if (symboltable.get(variablereference).expression.getClass() == VariableReference.class){
            return checkVariableReference(symboltable.get(variablereference).expression.getNodeLabel());
        }
        if (symboltable.get(variablereference).expression.getClass() == PixelLiteral.class){
            return (PixelLiteral)symboltable.get(variablereference).expression;
        }
        if (symboltable.get(variablereference).expression.getClass() == PercentageLiteral.class){
            return (PercentageLiteral)symboltable.get(variablereference).expression;
        }
        if (symboltable.get(variablereference).expression.getClass() == ColorLiteral.class){
            return (ColorLiteral)symboltable.get(variablereference).expression;
        }
        if (symboltable.get(variablereference).expression.getClass() == BoolLiteral.class){
            return (BoolLiteral)symboltable.get(variablereference).expression;
        }
        return null;
    }
}
