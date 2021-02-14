package student.adventure;

public class Adventure {
    public static void adventure() {
        State.startState();

        while (!State.winCondition()) {
            String[] input = Input.userInput();
            
            if (Input.isGo(input[0])) {
                State.goState(input);
            } else if (Input.isExamine(input[0])) {
                State.examineState();
            } else if (Input.isQuit(input[0])) {
                System.exit(0);
            }
        }
    }
}
