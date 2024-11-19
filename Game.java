import java.io.*;
import java.util.*;

public class Game {
    List<Property> board;
    List<Player> players;

    public Game(String configFile) throws IOException {
        this.board = new ArrayList<>();
        this.players = new ArrayList<>();
        loadConfig(configFile);
    }

    public void loadConfig(String configFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            int price = Integer.parseInt(parts[0]);
            int rent = Integer.parseInt(parts[1]);
            board.add(new Property(price, rent));
        }
        reader.close();
    }

    public void addPlayer(String behavior) {
        players.add(new Player(behavior));
    }

    public void playRound() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int diceRoll = player.rollDice();
            Property currentProperty = board.get(diceRoll % 20);

            if (currentProperty.owner == null) {
                if (player.shouldBuy(currentProperty)) {
                    player.buyProperty(currentProperty);
                }
            } else {
                player.payRent(currentProperty);
            }

            // Se o jogador ficou com saldo negativo, ele é eliminado
            if (player.coins < 0) {
                players.remove(i);
                i--;  // Ajusta o índice após a remoção
            }
        }
    }

    public Player checkWinner() {
        if (players.size() == 1) {
            return players.get(0);
        }
        return null;
    }

    public Player playGame(int maxRounds) {
        int rounds = 0;
        while (rounds < maxRounds) {
            playRound();
            rounds++;
            Player winner = checkWinner();
            if (winner != null) {
                return winner;
            }
        }
        return players.stream().max(Comparator.comparingInt(p -> p.coins)).orElse(null);
    }
}
