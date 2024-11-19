import java.io.*;
import java.util.*;

public class Bankrupt {
    public static void main(String[] args) {
        try {
            simulateGames(300);  // Simula 300 jogos
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void simulateGames(int numGames) throws IOException {
        int timeOuts = 0;
        int totalTurns = 0;
        Map<String, Integer> victories = new HashMap<>();
        victories.put("impulsivo", 0);
        victories.put("exigente", 0);
        victories.put("cauteloso", 0);
        victories.put("aleatorio", 0);

        for (int i = 0; i < numGames; i++) {
            Game game = new Game("C:\\Users\\aluno\\Desktop\\DesafioPOO\\src\\gameConfig.txt");
            game.addPlayer("impulsivo");
            game.addPlayer("exigente");
            game.addPlayer("cauteloso");
            game.addPlayer("aleatorio");

            Player winner = game.playGame(1000);  // Joga o jogo até 1000 rodadas
            int turns = game.playGame(1000) == winner ? 1000 : 0;

            totalTurns += turns;
            victories.put(winner.behavior, victories.get(winner.behavior) + 1);

            if (turns == 1000) {
                timeOuts++;
            }
        }

        System.out.println("Partidas que terminaram por time out: " + timeOuts);
        System.out.println("Turnos médios por partida: " + (double) totalTurns / numGames);

        System.out.println("Porcentagem de vitórias por comportamento:");
        for (String behavior : victories.keySet()) {
            double percentage = (victories.get(behavior) * 100.0) / numGames;
            System.out.printf("%s: %.2f%%\n", behavior, percentage);
        }

        String mostVictorious = Collections.max(victories.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("O comportamento que mais venceu foi: " + mostVictorious);
    }
}
