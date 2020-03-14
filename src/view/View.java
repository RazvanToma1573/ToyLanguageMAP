package view;

import controller.Controller;
import model.ExeStack.MyIStack;
import model.ExeStack.MyStack;
import model.Expressions.ArithmeticExpression;
import model.Expressions.ValueExpression;
import model.Expressions.VariableExpression;
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
import model.Types.BoolType;
import model.Types.IntType;
import model.Types.StringType;
import model.Values.BoolValue;
import model.Values.IntValue;
import model.Values.StringValue;
import model.Values.Value;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.Scanner;

public class View {

    private Controller controller;

    public void printTheMenu() {
        System.out.println("Choose from the following functionalities:");
        System.out.println("\t\t0 - print the menu.");
        System.out.println("\t\t1 - input a program.");
        System.out.println("\t\t2 - one-step evaluation of a program.");
        System.out.println("\t\t3 - complete evaluation of a program.");
        System.out.println("\t\t4 - quit.");
        System.out.print("Choice:");
    }

    public void inputAProgram() {
        //program1

        MyIStack<IStatement> ExeStack1 = new MyStack<>();
        MyIDictionary<String, Value> SymTable1 = new MyDictionary<>();
        MyIList<Value> Out1 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable1 = new MyFileTable<>();
        MyIHeap<Value> HeapTable1 = new MyHeap<>();

        //int v;v=2;Print(v)
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));

        ProgramState program1 = new ProgramState(ExeStack1,SymTable1,Out1,FileTable1,HeapTable1,ex1);
        IRepository repository1 = new Repository(program1,"E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller1 = new Controller(repository1);


        //program2

        MyIStack<IStatement> ExeStack2 = new MyStack<>();
        MyIDictionary<String, Value> SymTable2 = new MyDictionary<>();
        MyIList<Value> Out2 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable2 = new MyFileTable<>();
        MyIHeap<Value> HeapTable2 = new MyHeap<>();

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
                                new ValueExpression(new IntValue(2)), new ArithmeticExpression('*',
                                new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+',
                                        new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))))));

        ProgramState program2 = new ProgramState(ExeStack2,SymTable2,Out2,FileTable2,HeapTable2,ex2);
        IRepository repository2 = new Repository(program2, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller2 = new Controller(repository2);


        //program3

        MyIStack<IStatement> ExeStack3 = new MyStack<>();
        MyIDictionary<String, Value> SymTable3 = new MyDictionary<>();
        MyIList<Value> Out3 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable3 = new MyFileTable<>();
        MyIHeap<Value> HeapTable3 = new MyHeap<>();

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));


        ProgramState program3 = new ProgramState(ExeStack3,SymTable3,Out3,FileTable3,HeapTable3,ex3);
        IRepository repository3 = new Repository(program3, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller3 = new Controller(repository3);

        //program4

        MyIStack<IStatement> ExeStack4 = new MyStack<>();
        MyIDictionary<String, Value> SymbolTable4 = new MyDictionary<>();
        MyIList<Value> OutList4 = new MyList<>();
        MyIFileTable<StringValue, BufferedReader> FileTable4 = new MyFileTable<>();
        MyIHeap<Value> HeapTable4 = new MyHeap<>();

        // string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        IStatement program4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("E:\\IdeaProjects\\Assignment2\\src\\test.in"))),
                        new CompoundStatement(new openReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new readFileStatement(new VariableExpression("varf"),"varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new readFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new closeReadFileStatement(new VariableExpression("varf"))))))))));

        ProgramState programState4 = new ProgramState(ExeStack4, SymbolTable4, OutList4, FileTable4,HeapTable4, program4);
        Repository repository4 = new Repository(programState4,"E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
        Controller controller4 = new Controller(repository4);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose a program:");
        System.out.println("\t\t 1 - <int v;v=2;Print(v)>");
        System.out.println("\t\t 2 - <int a;int b;a=2+3*5;b=a+1;Print(b)>");
        System.out.println("\t\t 3 - <bool a;int v;a=true;(If a Then v=2 Else v=3);Print(v)>");
        System.out.println("\t\t 4 - <string varf; varf=\"test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)>");
        int userChoice = -1;
        boolean choiceDone = true;
        while(choiceDone) {
            userChoice = scanner.nextInt();
            if (userChoice == 1) {
                this.controller = controller1;
                System.out.println("We will execute:");
                System.out.println("\t\t<int v;v=2;Print(v)>");
                choiceDone = false;
            } else if (userChoice == 2) {
                this.controller = controller2;
                System.out.println("We will execute:");
                System.out.println("\t\t<int a;int b;a=2+3*5;b=a+1;Print(b)>");
                choiceDone = false;
            } else if (userChoice == 3) {
                this.controller = controller3;
                System.out.println("We will execute:");
                System.out.println("\t\t<bool a;int v;a=true;(If a Then v=2 Else v=3);Print(v)>");
                choiceDone = false;
            } else if ( userChoice == 4) {
                this.controller = controller4;
                System.out.println("We will execute:");
                System.out.println("\t\t<string varf; varf=\"test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)>");
                choiceDone = false;
            } else {
                System.out.println("ViewError: No program corresponds with the entry you chose.");
            }
        }
    }

    public void oneStepEvaluation() {
        /*(try {
            this.controller.oneStep(this.controller.getCrtProgramController());
            System.out.println(this.controller.getCrtProgramController());
            System.out.println();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }*/
    }

    public void completeEvaluation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one of the following options:");
        System.out.println("\t\t1 - set display flag on.");
        System.out.println("\t\t0 - set display flag off.");
        int userChoice = -1;
        boolean choiceMade = true;
        while (choiceMade) {
            userChoice = scanner.nextInt();
            if (userChoice == 1) {
                try {
                    this.controller.allStep();
                } catch (MyException e) {
                    System.out.println(e.getMessage());
                }
                choiceMade = false;
            } else if (userChoice == 0) {
                try {
                    this.controller.allStep();
                } catch (MyException e) {
                    System.out.println(e.getMessage());
                }
                choiceMade = false;
            } else {
                System.out.println("ViewError: There is no option that corresponds to your choice.");
            }
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello!");
        printTheMenu();
        boolean func = true;
        boolean ok = false;
        int userChoice;
        while (func) {
            if (scanner.hasNextInt()) {
                userChoice = scanner.nextInt();
                if (userChoice == 0) {
                    printTheMenu();
                } else if (userChoice == 1) {
                    inputAProgram();
                    System.out.print("Choice:");
                    ok = true;
                } else if (userChoice == 2) {
                    if (ok) {
                        oneStepEvaluation();
                        System.out.print("Choice:");
                    } else {
                        System.out.println("ViewError: Input a program first.");
                    }
                } else if (userChoice == 3) {
                    if (ok) {
                        completeEvaluation();
                        System.out.print("Choice:");
                    } else {
                        System.out.println("ViewError: Input a program first.");
                    }
                } else if (userChoice == 4) {
                    func = false;
                } else {
                    System.out.println("ViewError: No functionality corresponds to the introduced value.");
                }
            }
            else {
                System.out.println("ViewError: No functionality corresponds to the introduced value.");
            }
        }
    }
}
