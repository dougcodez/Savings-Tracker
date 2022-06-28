package dev.savingstracker;

import dev.savingstracker.database.SavingsTrackerDatabaseInit;
import dev.savingstracker.database.setup.StatementAPI;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SavingsTrackerMainApplication {

    @Getter
    private static StatementAPI statementAPI;

    public static void main(String[] args){
            statementAPI = new StatementAPI();
            new SavingsTrackerDatabaseInit().init(statementAPI);
        SpringApplication.run(SavingsTrackerMainApplication.class, args);

    }

}
