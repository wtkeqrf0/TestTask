package GoldChanges;

//примерный экземпляр сущности клана
public final class Clan {

    private final String name;
    private final int gold;

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public Clan(int gold, String name) {
        this.gold = gold;
        this.name = name;
    }
}