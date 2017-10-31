package nl.han.ica.icss.ast;

import java.util.ArrayList;

public class Operation extends Expression {
    public enum Operator {
        PLUS("+"),
        MIN("-"),
        OR("||"),
        AND("&&"),
        EQ("="),
        LT("<"),
        GT(">");
        private String operator;

        Operator(String text) {
            this.operator = text;
        }
        public String getOperator(){
           return this.operator;
        }
        public static Operator fromString(String text){
            for (Operator o : Operator.values()){
                if (o.operator.equals(text)){
                    return o;
                }
            }
            return null;
        }

    }

    public Operator operator;
    public Expression lhs;
    public Expression rhs;


    public Operation(Operator operator){
        this.operator = operator;
    }

    @Override
    public String getNodeLabel() {
        return "Operation (" + operator.toString() + ")";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(lhs);
        children.add(rhs);
        return children;
    }

    @Override
    public void addChild(ASTNode child) {
        if(lhs == null) {
            lhs = (Expression) child;
        } else if(rhs == null) {
            rhs = (Expression) child;
        }
    }
}
