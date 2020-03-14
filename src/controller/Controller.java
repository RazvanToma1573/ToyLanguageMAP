package controller;

import javafx.collections.ObservableList;
import model.ExeStack.MyIStack;
import model.MyException;
import model.ProgramState;
import model.Statements.IStatement;
import model.SymTable.MyIDictionary;
import model.Values.RefValue;
import model.Values.Value;
import repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller {


    private IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public List<ProgramState> removeCompletedPrograms (List<ProgramState> inProgressList) {
        return inProgressList.stream().filter(program -> program.isNotCompleted()).collect(Collectors.toList());
    }

    public List<ProgramState> getProgramList() {
        return this.repository.getProgramList();
    }

    public void oneStepForAllPrograms(List<ProgramState> programs) throws InterruptedException {
        //print into the log file
        //programs.forEach(program -> repository.logProgramStateExecution(program));

        // run concurrently one step for each of the existing programState

        //prepare the list of callables
        List<Callable<ProgramState>> callableList = programs.stream().map((ProgramState program) -> (Callable<ProgramState>)(() -> {return program.oneStep();})).collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created ProgramStates(namely threads)

        List<ProgramState> newPrograms = executor.invokeAll(callableList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                            return null;
                }
                )
                .filter(program -> program != null)
                .collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        programs.addAll(newPrograms);

        //after the execution, print the programState list into the log file
        programs.forEach(program->repository.logProgramStateExecution(program));

        //save the current programs in the repository
        repository.setProgramList(programs);

    }

    public void setRepositoryPrograms() {
        this.repository.setProgramList(this.removeCompletedPrograms(this.repository.getProgramList()));
    }

    public void setExecutor(ExecutorService e) {
        this.executor = e;
    }

    public void allStep() throws MyException{
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> programs = removeCompletedPrograms(repository.getProgramList());
        while(programs.size() > 0) {
            programs.get(0).getHeapTable().setContent(conservativeGarbageCollector(programs, programs.get(0).getHeapTable().getContent()));
            try {
                oneStepForAllPrograms(programs);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            //remove the completed programs
            programs = removeCompletedPrograms(repository.getProgramList());
        }
        executor.shutdownNow();
        //here the repository still contains at least one completed program
        // and its list<ProgramState> is not empty. Note that oneStepForAllProgram calld the method
        // setProgramList of the repository in order to change the repository

        //update the repository state
        repository.setProgramList(programs);
    }

    public Map<Integer, Value> conservativeGarbageCollector (List<ProgramState> programs, Map<Integer, Value> heap) {
        List<Integer> symbolTableAddressesAllPrograms = programs.stream().map(e -> e.getSymbolTable().getContent().values()).map(e -> getAddressesFromSymbolTable(e)).reduce(Stream.of(0).collect(Collectors.toList()), (s1, s2) -> { s1.addAll(s2); return s1;});
        Map<Integer, Value> map = safeGarbageCollector(symbolTableAddressesAllPrograms, getAddressesFromHeap(heap.values()), heap);
        return map;
    }

    Map<Integer, Value> safeGarbageCollector (List<Integer> symbolTableAddresses, List<Integer> heapTableAddresses,  Map<Integer, Value> heap) {
        Map<Integer, Value> map = heap.entrySet().stream().filter(e->symbolTableAddresses.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for (Integer addr : heapTableAddresses) {
            map.put(addr, heap.get(addr));
        }
        return map;
    }

    List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues) {
        return symbolTableValues.stream().filter(v->v instanceof RefValue).map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}).collect(Collectors.toList());
    }

    List<Integer> getAddressesFromHeap(Collection<Value> heapValues) {
        return heapValues.stream().filter(v->v instanceof RefValue).map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}).collect(Collectors.toList());
    }
}
