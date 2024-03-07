package br.dev.hebio.mdacessoconnector;

import br.dev.hebio.mdacessoconnector.util.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MdacessoConnectorApplication {

    @Autowired
    private DatabaseConnection databaseConnection;

    public static void main(String[] args) {
        SpringApplication.run(MdacessoConnectorApplication.class, args);
    }

}
