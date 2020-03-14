package view;

import controller.Controller;
import model.MyException;

public class RunExampleAllStep extends Command {

    private Controller controller;

    public RunExampleAllStep(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            this.controller.allStep();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
