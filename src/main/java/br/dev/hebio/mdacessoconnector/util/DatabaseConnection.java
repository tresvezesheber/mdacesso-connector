package br.dev.hebio.mdacessoconnector.util;

import br.dev.hebio.mdacessoconnector.model.pessoa.Pessoa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

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


    public void insertPessoa(Pessoa pessoa) {
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO pessoa (cd_pessoa, " +
                    "nu_matricula, " +
                    "nm_pessoa, " +
                    "cd_situacao_pessoa, " +
                    "cd_estrutura_organizacional, " +
                    "cd_estrutura_org_empresa, " +
                    "nu_cpf) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, pessoa.cd_pessoa());
            statement.setLong(2, pessoa.nu_matricula());
            statement.setString(3, pessoa.nm_pessoa());
            statement.setInt(4, pessoa.cd_situacao_pessoa());
            statement.setInt(5, pessoa.cd_estrutura_organizacional());
            statement.setInt(6, pessoa.cd_estrutura_org_empresa());
            statement.setString(7, pessoa.nu_cpf());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePessoa(Pessoa pessoa) {
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE pessoa SET cd_situacao_pessoa = ? WHERE nu_matricula = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setByte(1, pessoa.cd_situacao_pessoa());
            statement.setLong(2, pessoa.nu_matricula());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}