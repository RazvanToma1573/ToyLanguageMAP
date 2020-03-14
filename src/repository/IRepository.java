package repository;

import model.MyException;
import model.ProgramState;

import java.util.List;

public interface IRepository {

    void logProgramStateExecution(ProgramState programState);

    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> pr);
}
