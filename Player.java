import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Player {
    int coins;
    String behavior;
    List<Property> properties;

    public Player(String behavior) {
        this.coins = 300;
        this.behavior = behavior;
        this.properties = new ArrayList<>();
    }

    public int rollDice() {
        return new Random().nextInt(6) + 1;
    }

    public void buyProperty(Property property) {
        if (this.coins >= property.price) {
            this.coins -= property.price;
            this.properties.add(property);
            property.owner = this;
        }
    }

    public void payRent(Property property) {
        if (property.owner != null && property.owner != this) {
            this.coins -= property.rent;
            property.owner.coins += property.rent;
        }
    }

    public boolean shouldBuy(Property property) {
        switch (this.behavior) {
            case "impulsivo":
                return true;
            case "exigente":
                return property.rent > 50;
            case "cauteloso":
                return this.coins - property.price >= 80;
            case "aleatorio":
                return new Random().nextBoolean();
            default:
                return false;
        }
    }
}
