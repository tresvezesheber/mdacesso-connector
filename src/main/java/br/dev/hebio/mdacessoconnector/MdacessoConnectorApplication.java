package br.dev.hebio.mdacessoconnector;

import br.dev.hebio.mdacessoconnector.util.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MdacessoConnectorApplication implements CommandLineRunner {

    @Autowired
    private DatabaseConnection databaseConnection;

    public static void main(String[] args) {
        SpringApplication.run(MdacessoConnectorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        databaseConnection.readAllFromPessoa();
    }
}
