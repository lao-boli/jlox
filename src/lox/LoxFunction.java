package lox;

import java.util.List;

public class LoxFunction implements LoxCallable {

    private final String name;
    private final Environment closure;
    private final Expr.Function declaration;

    LoxFunction(String name, Expr.Function declaration,Environment closure) {
        this.name = name;
        this.declaration = declaration;
        this.closure = closure;
    }

    @Override
    public Object call(Interpreter interpreter,
                       List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.parameters.size(); i++) {
            environment.define(declaration.parameters.get(i).lexeme,
                               arguments.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch (Return returnValue) {
            return returnValue.value;
        }
        return null;
    }

    @Override
    public int arity() {
        return declaration.parameters.size();
    }

    @Override
    public String toString() {
        if (name == null){
            return "<fn>";
        }
        return "<fn " + name + ">";
    }

}
