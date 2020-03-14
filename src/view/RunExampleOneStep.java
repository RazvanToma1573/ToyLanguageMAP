package view;

import controller.Controller;

public class RunExampleOneStep extends Command {

    private Controller controller;

    public RunExampleOneStep(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        /*try{
            this.controller.oneStep(this.controller.getCrtProgramController());
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }*/
    }
}
