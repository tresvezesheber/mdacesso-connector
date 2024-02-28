package br.dev.hebio.mdacessoconnector.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DatabaseConnection {

    @Value("${postgresql.datasource.url}")
    private String databaseUrl;

    @Value("${postgresql.datasource.username}")
    private String username;

    @Value("${postgresql.datasource.password}")
    private String password;

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    public void readAllFromPessoa() {
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pessoa");

            while (resultSet.next()) {
                String nome = resultSet.getString("nm_pessoa");
                String matricula = resultSet.getString("nu_matricula");
                String cpf = resultSet.getString("nu_cpf");


                System.out.println("Nome: " + nome + " Matricula: " + matricula + " CPF: " + cpf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}