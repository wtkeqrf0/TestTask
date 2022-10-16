package GoldChanges;

import Enums.Cause;
import Exceptions.ClanNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//сервис взаимодействия с казной клана
public final class Treasury {

    //сервис кланов
    private final List<Clan> clans = new ArrayList<>();

    //сервис транзакций
    private final List<Transaction> changeLog = Collections.synchronizedList(new LinkedList<>());

    //взаимодействие с казной клана
    public void createTransaction(int received, String initiator, String clanName, Cause cause) throws ClanNotFoundException {
        final int sum;
        final Clan clan;
        synchronized (this) {

            //поиск клана в таблице
            clan = clans.stream().filter(e -> e.getName().equals(clanName))
                    .findAny().orElseThrow(ClanNotFoundException::new);

            //обновление значения казны клана
            clans.set(clans.indexOf(clan), new Clan(sum = clan.getGold() + received, clanName));
        }
        //сохранение транзакции
        changeLog.add(new Transaction(clan.getGold(), received, sum, initiator, clanName,
                cause, LocalDateTime.now()));
    }

    //получение всех транзакций с казной кланов
    public List<Transaction> getChangeLog() {
        return changeLog;
    }

    //для удобства чтения (не обязателен)
    private void createClan(int gold, String name) {
        clans.add(new Clan(gold, name));
    }

    //заполнение таблицы кланов
    public Treasury() {
        createClan(0, "0");
        createClan((int) (Math.random() * 1000), "rand");
    }
}