import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.ExeStack.MyIStack;
import model.ExeStack.MyStack;
import model.FileTable.MyFileTable;
import model.FileTable.MyIFileTable;
import model.HeapTable.MyHeap;
import model.HeapTable.MyIHeap;
import model.Out.MyIList;
import model.Out.MyList;
import model.ProgramState;
import model.Statements.IStatement;
import model.SymTable.MyDictionary;
import model.SymTable.MyIDictionary;
import model.Values.StringValue;
import model.Values.Value;
import repository.IRepository;
import repository.Repository;
import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ControllerGuiDialog {

    private ObservableList<ProgramState> programStates = FXCollections.observableArrayList();
    private ObservableList<VariableHeap> heapContent = FXCollections.observableArrayList();
    private ObservableList<Variable> symbolContent = FXCollections.observableArrayList();
    private ObservableList<IStatement> stackContent = FXCollections.observableArrayList();
    private ObservableList<Value> outContent = FXCollections.observableArrayList();
    private ObservableList<Integer> idContent = FXCollections.observableArrayList();
    private ObservableList<Map.Entry<StringValue, BufferedReader>> fileTableContent = FXCollections.observableArrayList();
    private MyIList<Value> Out = new MyList<>();
    private MyIFileTable<StringValue, BufferedReader> FileTable = new MyFileTable<>();
    private MyIHeap<Value> HeapTable = new MyHeap<>();
    private Controller controller;
    private ExecutorService executorService;

    @FXML
    private TextField programStatesTextField;

    @FXML
    private TableView<VariableHeap> heapTableView;
    @FXML
    private TableColumn<VariableHeap, Integer> addressColumn;
    @FXML
    private TableColumn<VariableHeap, Value> heapValueColumn;
    @FXML
    private ListView<Value> outListView;
    @FXML
    private ListView<Map.Entry<StringValue, BufferedReader>> fileTableListView;
    @FXML
    private ListView<Integer> identifiersListView;
    @FXML
    private TableView<Variable> symbolTableView;
    @FXML
    private TableColumn<Variable, String> nameColumn;
    @FXML
    private TableColumn<Variable, Value> symbolValueColumn;
    @FXML
    private ListView<IStatement> stackListView;
    @FXML
    private Button runButton;

    @FXML
    public void initialize () {
        this.addressColumn.setCellValueFactory(new PropertyValueFactory<VariableHeap, Integer>("address"));
        this.heapValueColumn.setCellValueFactory(new PropertyValueFactory<VariableHeap, Value>("value"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
        this.symbolValueColumn.setCellValueFactory(new PropertyValueFactory<Variable, Value>("value"));
    }

    @FXML
    public void setProgram(IStatement p) {
        if (!p.toString().equals("")) {
            this.runButton.setDisable(false);
            MyIStack<IStatement> ExeStack = new MyStack<>();
            MyIDictionary<String, Value> SymbolTable = new MyDictionary<>();
            ProgramState programState = new ProgramState(ExeStack, SymbolTable, this.Out, this.FileTable, this.HeapTable, p);
            IRepository repository = new Repository(programState, "E:\\IdeaProjects\\Assignment2\\src\\logFile.txt");
            this.symbolTableView.setItems(this.symbolContent);
            this.stackListView.setItems(this.stackContent);
            this.heapTableView.setItems(this.heapContent);
            this.outListView.setItems(this.outContent);
            this.fileTableListView.setItems(this.fileTableContent);
            this.controller = new Controller(repository);
            this.executorService = Executors.newFixedThreadPool(2);
            this.controller.setExecutor(this.executorService);
            this.programStates.setAll(this.controller.getProgramList());
            for (ProgramState pr : this.programStates) {
                idContent.add(pr.getProgramId());
            }
            this.identifiersListView.setItems(this.idContent);
            this.identifiersListView.getSelectionModel().selectFirst();
            this.setControls();
        }

    }

    @FXML
    private void setControls() {
        this.heapContent.clear();
        this.HeapTable.getContent().forEach((key, value) -> this.heapContent.add(new VariableHeap(key, value)));
        this.programStates.setAll(this.controller.getProgramList());
        this.programStatesTextField.setText(Integer.toString(this.programStates.size()));
        this.outContent.clear();
        this.outContent.addAll(this.Out.getOutList());
        this.fileTableContent.clear();
        this.fileTableContent.addAll(this.FileTable.getContent().entrySet());
        for (ProgramState pr : this.programStates) {
            if (!this.idContent.contains(pr.getProgramId())) {
                this.idContent.add(pr.getProgramId());
            }
        }

        for (ProgramState p : this.programStates) {
            if (p.getProgramId() == this.identifiersListView.getSelectionModel().getSelectedItem()){
                this.symbolContent.clear();
                this.stackContent.clear();
                p.getSymbolTable().getContent().forEach((key, value) -> this.symbolContent.add(new Variable(key, value)));
                p.getStack().getContent().forEach(v -> this.stackContent.add(0, v));
                break;
            }
        }
        if (this.programStates.isEmpty()) {
            this.runButton.setDisable(true);
        }
    }

    @FXML
    public void runButtonClicked() {
        try {
            if (this.programStates.size() > 0) {
                this.controller.oneStepForAllPrograms(this.controller.getProgramList());
                this.setControls();
                this.controller.setRepositoryPrograms();
                this.programStatesTextField.setText(Integer.toString(this.programStates.size()));
                this.idContent.clear();
                this.programStates.forEach(v -> this.idContent.add(v.getProgramId()));
                this.identifiersListView.getSelectionModel().selectFirst();
                this.HeapTable.setContent(this.controller.conservativeGarbageCollector(this.controller.getProgramList(),this.HeapTable.getContent()));

            } else {
                this.executorService.shutdownNow();
                this.controller.setExecutor(this.executorService);
            }
        } catch (InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setHeaderText(e.toString());
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    public void mouseClickID () {
        this.setControls();
    }
}
