package GoldChanges;

import Enums.Cause;

import java.time.LocalDateTime;

//экземпляр сущности транзакции
//нужен для хранения информации об изменениях золота в казне клана
public final class Transaction {
    private final int initial, received, totalGold;
    private final Cause cause;  //переменная может быть типа String
    private final String initiator, clanName;
    private final LocalDateTime created;

    public Transaction(int initial, int received, int totalGold, String initiator, String clanName, Cause cause, LocalDateTime created) {
        this.initial = initial;
        this.received = received;
        this.initiator = initiator;
        this.clanName = clanName;
        this.cause = cause;
        this.totalGold = totalGold;
        this.created = created;
    }

    //геттеры созданы для сортировки выподимых данных (не обязательны)
    public LocalDateTime getCreated() {
        return created;
    }

    public int getInitial() {
        return initial;
    }

    public String toString() {
        return "Initial - " + initial +
                " | Received - " + received +
                " | Total - " + totalGold +
                " | Request Initiator - " + initiator +
                " | Clan Name - " + clanName +
                " | Cause - " + cause +
                " | Time Received - " + created + "\n";
    }
}