import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import model.Expressions.*;
import model.MyException;
import model.Statements.*;
import model.SymTable.MyDictionary;
import model.SymTable.MyIDictionary;
import model.Types.*;
import model.Values.BoolValue;
import model.Values.IntValue;
import model.Values.StringValue;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ControllerGui {


    @FXML
    private ListView<IStatement> programStateListView;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        List<IStatement> programs = new LinkedList<>();
        //int a; a = 1; Print(a) (Simple example)
        MyIDictionary<String, IType> typeEnvironment1 = new MyDictionary<>();
        IStatement program1 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))), new PrintStatement(new VariableExpression("a"))));

        programs.add(program1);


        //int a; a = true; Print(a) (Simple example error on type check)
        MyIDictionary<String, IType> typeEnvironment2 = new MyDictionary<>();
        IStatement program2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()), new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))), new PrintStatement(new VariableExpression("a"))));

        programs.add(program2);


        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        MyIDictionary<String, IType> typeEnvironment3 = new MyDictionary<>();
        IStatement program3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        programs.add(program3);

        //int v; Ref int a; v = 10; new(a,22); fork(wH(a,30); v=32; print(v): print(rH(a))); print(v); print(rH(a))
        MyIDictionary<String, IType> typeEnvironment4 = new MyDictionary<>();
        IStatement program4 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new newStatement("a",new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new forkStatement(new CompoundStatement(new wHStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new rHExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new rHExpression(new VariableExpression("a")))))))));


        programs.add(program4);

        // String varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        MyIDictionary<String, IType> typeEnvironment5 = new MyDictionary<>();
        IStatement program5 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("E:\\IdeaProjects\\Assignment2\\src\\test.in"))),
                        new CompoundStatement(new openReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new readFileStatement(new VariableExpression("varf"),"varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new readFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new closeReadFileStatement(new VariableExpression("varf"))))))))));

        programs.add(program5);


        IStatement program6 = new CompoundStatement(new VariableDeclarationStatement("a",new RefType(new IntType()))
                ,
                new CompoundStatement(new VariableDeclarationStatement("b", new RefType(new IntType()))
                        ,
                        new CompoundStatement(new VariableDeclarationStatement("v", new IntType())
                                ,
                        new CompoundStatement(new newStatement("a",new ValueExpression(new IntValue(0)))
                                ,
                        new CompoundStatement(new newStatement("b", new ValueExpression(new IntValue(0)))
                                ,
                        new CompoundStatement(new wHStatement("a", new ValueExpression(new IntValue(1)))
                                ,
                        new CompoundStatement(new wHStatement("b", new ValueExpression(new IntValue(2)))
                                ,
                        new CompoundStatement(new conditionalAssingment("v",new RelationalExpression(new rHExpression(new VariableExpression("a")),new rHExpression(new VariableExpression("b")),"<"),new ValueExpression(new IntValue(100)),new ValueExpression(new IntValue(200)))
                                ,
                        new CompoundStatement(new PrintStatement(new VariableExpression("v"))
                                ,
                        new CompoundStatement(new conditionalAssingment("v",new RelationalExpression(new ArithmeticExpression('-',new rHExpression(new VariableExpression("b")),new ValueExpression(new IntValue(2))),new rHExpression(new VariableExpression("a")),">"),new ValueExpression(new IntValue(100)),new ValueExpression(new IntValue(200)))
                                ,
                        new PrintStatement(new VariableExpression("v"))))))))))));

        programs.add(program6);
        //populate the list
        this.programStateListView.getItems().addAll(programs);
        //can only select one item at a time
        this.programStateListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.programStateListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showExecutionOfProgam() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        int index = this.programStateListView.getSelectionModel().getSelectedIndex();
        dialog.setTitle("Execute program " + (index + 1));
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("executionwindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        ControllerGuiDialog controllerGuiDialog = fxmlLoader.getController();
        try {
            MyIDictionary<String, IType> typeEnvironment = new MyDictionary<>();
            this.programStateListView.getSelectionModel().getSelectedItem().typecheck(typeEnvironment);
            controllerGuiDialog.setProgram(this.programStateListView.getSelectionModel().getSelectedItem());
            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent()) {
                this.programStateListView.getSelectionModel().selectFirst();
            }
        } catch (MyException er) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Type Error");
            alert.setHeaderText(er.toString());
            alert.setContentText(er.getMessage());
            alert.showAndWait();
        }

    }
}
