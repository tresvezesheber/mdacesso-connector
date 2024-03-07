package br.dev.hebio.mdacessoconnector.util;

import br.dev.hebio.mdacessoconnector.model.credencial.Credencial;
import br.dev.hebio.mdacessoconnector.model.pessoa.Pessoa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class DatabaseConnection {

    @Value("${mssql.datasource.url}")
    private String databaseUrl;

    @Value("${mssql.datasource.username}")
    private String username;

    @Value("${mssql.datasource.password}")
    private String password;

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    public void readAllFromPessoa() {
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PESSOA");

            while (resultSet.next()) {
                String codigoPessoa = resultSet.getString("cd_pessoa");
                String matricula = resultSet.getString("nu_matricula");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertPessoa(Pessoa pessoa) {
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO PESSOA (nu_matricula, " +
                    "nm_pessoa, " +
                    "cd_situacao_pessoa, " +
                    "cd_estrutura_organizacional, " +
                    "cd_estrutura_org_empresa, " +
//                    "nu_cpf" +
                    "cd_perfil_acesso," +
                    "fl_reentrada," +
                    "fl_dispensa_senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, pessoa.nu_matricula());
            statement.setString(2, pessoa.nm_pessoa());
            statement.setInt(3, pessoa.cd_situacao_pessoa());
            statement.setInt(4, pessoa.cd_estrutura_organizacional());
            statement.setInt(5, pessoa.cd_estrutura_org_empresa());
//            statement.setString(6, pessoa.nu_cpf());
            statement.setInt(6, pessoa.cd_perfil_acesso());
            statement.setByte(7, pessoa.fl_reentrada());
            statement.setByte(8, pessoa.fl_dispensa_senha());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updatePessoa(Pessoa pessoa) {
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE PESSOA SET cd_situacao_pessoa = ? WHERE nu_matricula = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setByte(1, pessoa.cd_situacao_pessoa());
            statement.setLong(2, pessoa.nu_matricula());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCredencial(Credencial credencial) {
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO CREDENCIAl (cd_tipo_credencial, " +
                    "cd_estrutura_organizacional, " +
                    "tp_tecnologia, " +
                    "nu_credencial, " +
                    "fl_bloqueada, " +
                    "fl_credencial_publica)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setByte(1, credencial.cd_tipo_credencial());
            statement.setInt(2, credencial.cd_estrutura_organizacional());
            statement.setByte(3, credencial.tp_tecnologia());
            statement.setLong(4, credencial.nu_credencial());
            statement.setByte(5, credencial.fl_bloqueada());
            statement.setByte(6, credencial.fl_credencial_publica());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPessoaCredencialRelation(Long nu_matricula) {
        DataSource dataSource = getDataSource();
        Long codigoPessoa = getCdPessoa(nu_matricula);
        Long matricula = getCdCredencial(nu_matricula);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO CREDENCIAL_PESSOA (cd_credencial, " +
                    "cd_pessoa, " +
                    "dt_inicio, " +
                    "fl_permanente, " +
                    "tp_tecnologia) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, matricula);
            statement.setLong(2, codigoPessoa);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setBoolean(4, true);
            statement.setByte(5, (byte) 1);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long getCdPessoa(Long matricula) {
        Long cdPessoa = 0L;
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT cd_pessoa FROM PESSOA WHERE nu_matricula = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, matricula);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cdPessoa = resultSet.getLong("cd_pessoa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cdPessoa;
    }

    public Long getCdCredencial(Long matricula) {
        Long cdCredencial = 0L;
        DataSource dataSource = getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT cd_credencial FROM CREDENCIAL WHERE nu_credencial = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, matricula);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cdCredencial = resultSet.getLong("cd_credencial");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cdCredencial;
    }


}