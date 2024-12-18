import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        Deque<Integer> frontHalf = new LinkedList<>(); //привилегия
        Deque<Integer> backHalf = new LinkedList<>();  //обычный

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < n; i++) {
            String command = reader.readLine();

            if (command.startsWith("+")) {
                // Обычный гоблин встает в конец очереди
                int goblin = Integer.parseInt(command.split(" ")[1]);
                backHalf.addLast(goblin);
            } else if (command.startsWith("*")) {
                // Привилегированный гоблин встает в середину очереди
                int goblin = Integer.parseInt(command.split(" ")[1]);
                frontHalf.addLast(goblin);
            } else if (command.equals("-")) {
                // Первый гоблин из очереди уходит к шаманам
                if (frontHalf.isEmpty()) {
                    output.append(backHalf.removeFirst()).append("\n");
                } else {
                    output.append(frontHalf.removeFirst()).append("\n");
                }
            }


            if (frontHalf.size() < backHalf.size()) {
                frontHalf.addLast(backHalf.removeFirst());
            } else if (frontHalf.size() > backHalf.size() + 1) {
                backHalf.addFirst(frontHalf.removeLast());
            }
        }


        System.out.print(output);
    }
}
