import controller.Controller;
import model.ExeStack.MyIStack;
import model.ExeStack.MyStack;
import model.Expressions.*;
import model.FileTable.MyFileTable;
import model.FileTable.MyIFileTable;
import model.HeapTable.MyHeap;
import model.HeapTable.MyIHeap;
import model.MyException;
import model.Out.MyIList;
import model.Out.MyList;
import model.ProgramState;
import model.Statements.*;
import model.SymTable.MyDictionary;
import model.SymTable.MyIDictionary;
import model.Types.*;
import model.Values.BoolValue;
import model.Values.IntValue;
import model.Values.StringValue;
import model.Values.Value;
import org.junit.jupiter.api.Test;
import repository.Repository;
import view.ExitCommand;
import view.RunExampleAllStep;
import view.TextMenu;

import java.io.BufferedReader;
import java.util.List;

public class testProgram {

    @Test
    public void testTypechecker() {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        /*menu.addCommand(new RunExampleAllStep("1",program1.toString(),controller1));
        menu.addCommand(new RunExampleAllStep("2",program2.toString(),controller2));
        menu.addCommand(new RunExampleAllStep("3",program3.toString(),controller3));
        menu.addCommand(new RunExampleAllStep("4",program4.toString(),controller4));
        menu.addCommand(new RunExampleAllStep("5",program5.toString(),controller5));
        menu.addCommand(new RunExampleAllStep("6",program6.toString(),controller6));
        menu.addCommand(new RunExampleAllStep("7",program7.toString(),controller7));
        menu.addCommand(new RunExampleAllStep("8",program8.toString(),controller8));
        */

        //int a; a = true (Simple example)
        MyIDictionary<String, IType> typeEnvironment1 = new MyDictionary<>();
        IStatement testTypeChecker1 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new AssignmentStatement("a", new ValueExpression(new BoolValue(true))));
        try {
            testTypeChecker1.typecheck(typeEnvironment1);
            MyIStack<IStatement> ExeStack1 = new MyStack<>();
            MyIDictionary<String, Value> SymbolTable1 = new MyDictionary<>();
            MyIList<Value> OutList1 = new MyList<>();
            MyIFileTable<StringValue, BufferedReader> FileTable1 = new MyFileTable<>();
            MyIHeap<Value> HeapTable1 = new MyHeap<>();
            ProgramState programType1 = new ProgramState(ExeStack1, SymbolTable1, OutList1, FileTable1, HeapTable1, testTypeChecker1);
            Repository repository1 = new Repository(programType1, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
            Controller controller1 = new Controller(repository1);
            menu.addCommand(new RunExampleAllStep("1",programType1.toString(),controller1));
            //menu.show();
        } catch(MyException e) {
            System.out.println("1:" + e.getMessage());
        }


        //Bool b; int a; if a then print b else print 0 (Simple example)
        MyIDictionary<String, IType> typeEnvironment2 = new MyDictionary<>();
        IStatement testTypeChecker2 = new CompoundStatement(new VariableDeclarationStatement("b", new BoolType()), new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new IfStatement(new VariableExpression("a"),new PrintStatement(new VariableExpression("b")),new PrintStatement(new ValueExpression(new IntValue(0))))));
        try {
            testTypeChecker2.typecheck(typeEnvironment2);
            MyIStack<IStatement> ExeStack2 = new MyStack<>();
            MyIDictionary<String, Value> SymbolTable2 = new MyDictionary<>();
            MyIList<Value> OutList2 = new MyList<>();
            MyIFileTable<StringValue, BufferedReader> FileTable2 = new MyFileTable<>();
            MyIHeap<Value> HeapTable2 = new MyHeap<>();
            ProgramState programType2 = new ProgramState(ExeStack2, SymbolTable2, OutList2, FileTable2, HeapTable2, testTypeChecker2);
            Repository repository2 = new Repository(programType2, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
            Controller controller2 = new Controller(repository2);
            menu.addCommand(new RunExampleAllStep("1",programType2.toString(),controller2));
            //menu.show();
        } catch(MyException e) {
            System.out.println("2:" + e.getMessage());
        }


        //Int a; a = 1; (while (a>0) int c; a = a - 1 ); print c (Intermediate example)
        MyIDictionary<String, IType> typeEnvironment3 = new MyDictionary<>();
        IStatement testTypeChecker3 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                new CompoundStatement( new whileStatement( new RelationalExpression(new VariableExpression("a"), new ValueExpression(new IntValue(0)),">") , new VariableDeclarationStatement("c", new IntType())),new PrintStatement(new VariableExpression("c")) )));
        try {
            testTypeChecker3.typecheck(typeEnvironment3);
            MyIStack<IStatement> ExeStack3 = new MyStack<>();
            MyIDictionary<String, Value> SymbolTable3 = new MyDictionary<>();
            MyIList<Value> OutList3 = new MyList<>();
            MyIFileTable<StringValue, BufferedReader> FileTable3 = new MyFileTable<>();
            MyIHeap<Value> HeapTable3 = new MyHeap<>();
            ProgramState programType3 = new ProgramState(ExeStack3, SymbolTable3, OutList3, FileTable3, HeapTable3, testTypeChecker3);
            Repository repository3 = new Repository(programType3, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
            Controller controller3 = new Controller(repository3);
            //menu.addCommand(new RunExampleAllStep("1",programType3.toString(),controller3));
            //menu.show();
        } catch(MyException e) {
            System.out.println("3:" + e.getMessage());
        }

        /*
        MyIDictionary<String, IType> typeEnvironment4 = new MyDictionary<>();
        MyIStack<IStatement> ExeStack4 = new MyStack<>();
        MyIDictionary<String, Value> SymTable4 = new MyDictionary<>();
        MyIList<Value> Out4 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable4 = new MyFileTable<>();
        MyIHeap<Value> HeapTable4 = new MyHeap<>();

        //int v;v=true;Print(v)
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new BoolValue(true))), new PrintStatement(new VariableExpression("v"))));

        try{
            ex1.typecheck(typeEnvironment4);
        } catch (MyException e) {
            System.out.println("4:" + e.getMessage());
        }
        ProgramState program4 = new ProgramState(ExeStack4,SymTable4,Out4,FileTable4,HeapTable4,ex1);
        IRepository repository4 = new Repository(program4,"E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller4 = new Controller(repository4);


        //program2
        MyIDictionary<String, IType> typeEnvironment5 = new MyDictionary<>();
        MyIStack<IStatement> ExeStack5 = new MyStack<>();
        MyIDictionary<String, Value> SymTable5 = new MyDictionary<>();
        MyIList<Value> Out5 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable5 = new MyFileTable<>();
        MyIHeap<Value> HeapTable5 = new MyHeap<>();

        //int a;int b; a=2+true*5;b=a+1;Print(b)
        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
                                new ValueExpression(new IntValue(2)), new ArithmeticExpression('*',
                                new ValueExpression(new BoolValue(true)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+',
                                        new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))))));

        try{
            ex2.typecheck(typeEnvironment5);
        } catch(MyException e) {
            System.out.println("5:" + e.getMessage());
        }

        ProgramState program5 = new ProgramState(ExeStack5,SymTable5,Out5,FileTable5,HeapTable5,ex2);
        IRepository repository5 = new Repository(program5, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller5 = new Controller(repository5);


        //program3
        MyIDictionary<String, IType> typeEnvironment6 = new MyDictionary<>();
        MyIStack<IStatement> ExeStack6 = new MyStack<>();
        MyIDictionary<String, Value> SymTable6 = new MyDictionary<>();
        MyIList<Value> Out6 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable6 = new MyFileTable<>();
        MyIHeap<Value> HeapTable6 = new MyHeap<>();

        //bool a; int v; a=true;(If v Then v=2 Else v=3);Print(v)
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("v"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        try {
            ex3.typecheck(typeEnvironment6);
        } catch (MyException e) {
            System.out.println("6:" + e.getMessage());
        }

        ProgramState program6 = new ProgramState(ExeStack6,SymTable6,Out6,FileTable6,HeapTable6,ex3);
        IRepository repository6 = new Repository(program6, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller6 = new Controller(repository6);


        //program4
        MyIDictionary<String, IType> typeEnvironment7 = new MyDictionary<>();
        MyIStack<IStatement> ExeStack7 = new MyStack<>();
        MyIDictionary<String, Value> SymbolTable7 = new MyDictionary<>();
        MyIList<Value> OutList7 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable7 = new MyFileTable<>();
        MyIHeap<Value> HeapTable7 = new MyHeap<>();

        // String varf; varf="test.in"; openRFile(varf); int varc; readFile(0,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("E:\\IdeaProjects\\Assignment2\\src\\test.in"))),
                        new CompoundStatement(new openReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new readFileStatement(new ValueExpression(new IntValue(0)),"varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new readFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new closeReadFileStatement(new VariableExpression("varf"))))))))));

        try {
            ex4.typecheck(typeEnvironment7);
        } catch(MyException e) {
            System.out.println("7:" + e.getMessage());
        }

        ProgramState program7 = new ProgramState(ExeStack7, SymbolTable7, OutList7, FileTable7,HeapTable7, ex4);
        Repository repository7 = new Repository(program7,"E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller7 = new Controller(repository7);


        //program5 test while statement
        MyIDictionary<String, IType> typeEnvironment8 = new MyDictionary<>();
        MyIStack<IStatement> ExeStack8 = new MyStack<>();
        MyIDictionary<String, Value> SymbolTable8 = new MyDictionary<>();
        MyIList<Value> OutList8 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable8 = new MyFileTable<>();
        MyIHeap<Value> HeapTable8 = new MyHeap<>();

        //int v; v=4; (while (v) print(v);v=v-1);print(v)

        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new whileStatement(new VariableExpression("v"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('-',
                                        new VariableExpression("v"),new ValueExpression(new IntValue(1)))))), new PrintStatement(new VariableExpression("v")))));

        try {
            ex5.typecheck(typeEnvironment8);
        } catch (MyException e) {
            System.out.println("8:" + e.getMessage());
        }

        ProgramState program8 = new ProgramState(ExeStack8, SymbolTable8, OutList8, FileTable8, HeapTable8, ex5);
        Repository repository8 = new Repository(program8, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller8 = new Controller(repository8);


        //program6 test garbage collector
        MyIDictionary<String, IType> typeEnvironment9 = new MyDictionary<>();
        MyIStack<IStatement> ExeStack9 = new MyStack<>();
        MyIDictionary<String, Value> SymbolTable9 = new MyDictionary<>();
        MyIList<Value> OutList9 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable9 = new MyFileTable<>();
        MyIHeap<Value> HeapTable9 = new MyHeap<>();

        //Ref int v; new(v,20); Ref Ref string a; new(a,v); new(v,30); new(v,40); print(rH(rH(a)))

        IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new newStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new StringType()))),
                                new CompoundStatement(new newStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new newStatement("v", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new newStatement("v", new ValueExpression(new IntValue(40))),
                                                        new PrintStatement(new rHExpression(new rHExpression(new VariableExpression("a"))))))))));

        try{
            ex6.typecheck(typeEnvironment9);
        } catch(MyException e) {
            System.out.println("9:" + e.getMessage());
        }
        ProgramState program9 = new ProgramState(ExeStack9, SymbolTable9, OutList9, FileTable9, HeapTable9, ex6);
        Repository repository9 = new Repository(program9, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller9 = new Controller(repository9);

        //program7 test wH
        MyIDictionary<String, IType> typeEnvironment10 = new MyDictionary<>();
        MyIStack<IStatement> ExeStack10 = new MyStack<>();
        MyIDictionary<String, Value> SymbolTable10 = new MyDictionary<>();
        MyIList<Value> OutList10 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable10 = new MyFileTable<>();
        MyIHeap<Value> HeapTable10 = new MyHeap<>();

        //Ref int v; new(v,20); print(rH(v); wH(v,true); print(rH(v)+5);

        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new newStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new rHExpression(new VariableExpression("v"))),
                                new CompoundStatement(new wHStatement("v", new ValueExpression(new BoolValue(true))),
                                        new PrintStatement(new ArithmeticExpression('+', new rHExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

        try{
            ex7.typecheck(typeEnvironment10);
        } catch (MyException e) {
            System.out.println("10:" + e.getMessage());
        }

        ProgramState program10 = new ProgramState(ExeStack10, SymbolTable10, OutList10, FileTable10, HeapTable10, ex7);
        Repository repository10 = new Repository(program10, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller10 = new Controller(repository10);

        //int v; Ref int a; v = 10; new(a,22); fork(wH(a,30); v=32; print(v): print(rH(a))); print(v); print(rH(a))
        MyIDictionary<String, IType> typeEnvironment11 = new MyDictionary<>();
        MyIStack<IStatement> ExeStack11 = new MyStack<>();
        MyIDictionary<String, Value> SymbolTable11 = new MyDictionary<>();
        MyIList<Value> OutList11 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable11 = new MyFileTable<>();
        MyIHeap<Value> HeapTable11 = new MyHeap<>();

        IStatement forkex88 = new CompoundStatement(new wHStatement("a", new ValueExpression(new IntValue(50))),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(12))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new rHExpression(new VariableExpression("a"))))));


        IStatement forkex8 = new CompoundStatement( new forkStatement(forkex88) ,new CompoundStatement(new wHStatement("a", new ValueExpression(new IntValue(30))),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new rHExpression(new VariableExpression("a")))))));

        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement( new newStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new forkStatement(forkex8),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement( new rHExpression(new VariableExpression("a")))))))));

        try {
            MyIDictionary<String, IType> typeEnvironment = new MyDictionary<>();
            ex8.typecheck(typeEnvironment);
        } catch (MyException e) {
            System.out.println("11" + e.getMessage());
        }
        ProgramState.initializeId();
        ProgramState program11 = new ProgramState(ExeStack11, SymbolTable11, OutList11, FileTable11, HeapTable11, ex8);
        Repository repository11 = new Repository(program11, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller11 = new Controller(repository11);
        */

    }

    @Test
    public void testFileStatements(){
        MyIStack<IStatement> ExeStack = new MyStack<>();
        MyIDictionary<String, Value> SymbolTable = new MyDictionary<>();
        MyIList<Value> OutList = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable = new MyFileTable<>();
        MyIHeap<Value> HeapTable = new MyHeap<>();

        IStatement forkex = new CompoundStatement(new wHStatement("a", new ValueExpression(new IntValue(30))),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new PrintStatement(new rHExpression(new VariableExpression("a"))))));

        IStatement ex = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement( new newStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new forkStatement(forkex),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement( new rHExpression(new VariableExpression("a")))))))));

        ProgramState program = new ProgramState(ExeStack, SymbolTable, OutList, FileTable, HeapTable, ex);
        Repository repository = new Repository(program, "E:\\IdeaProjects\\Assignment2\\src\\logFileTest.txt");
        Controller controller = new Controller(repository);

        try{
            controller.allStep();
            List<ProgramState> programList = repository.getProgramList();
            assert  programList.size() == 0;
            } catch(MyException e) {
            System.out.println(e.getMessage());
        }

    }

}
