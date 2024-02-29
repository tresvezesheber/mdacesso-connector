package br.dev.hebio.mdacessoconnector;

import br.dev.hebio.mdacessoconnector.model.pessoa.Pessoa;
import br.dev.hebio.mdacessoconnector.util.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MdacessoConnectorApplication implements CommandLineRunner {

    @Autowired
    private DatabaseConnection databaseConnection;

    public static void main(String[] args) {
        SpringApplication.run(MdacessoConnectorApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        Pessoa pessoa = new Pessoa(81810l, 81810l, "Astrofoboldo Guilhermino", 10, 2, 1, "12345678901");
//        databaseConnection.insertPessoa(pessoa);
//        databaseConnection.readAllFromPessoa();
    }
}
