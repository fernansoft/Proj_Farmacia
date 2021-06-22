package Farmacia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DAOAtendenteImplem implements DAOAtendente {
    private static final String URL = "jdbc:mariadb://localhost:3306/farmaciaDB";
    private static final String USER = "root";
    private static final String PASSWORD = "D0bm";

    @Override
    public void cadastrar(AtendenteEntity a) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "insert into atendente " +
                    "(nome, cargo , CPF, senha) " +
                    "values (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, a.getNome());
            statement.setString(2, a.getCargo());
            statement.setString(3, a.getCpf());
            statement.setString(4, a.getSenha());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean confAtends(String nome) {
        List<AtendenteEntity> atendenteEntityList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "select * from atendente where nome = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AtendenteEntity a = new AtendenteEntity();
                a.setNome(resultSet.getString("nome"));
                atendenteEntityList.add(a);
                if (atendenteEntityList.get(0).getNome().equalsIgnoreCase(nome)){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean confLog(String nome, String pass) {
        if (!confAtends(nome)){
            return false;
        }else {
            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
                List<AtendenteEntity> atendenteEntityList = new ArrayList<>();
                String sql = "select * from atendente where lower(nome) = lower(?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, nome);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    AtendenteEntity a = new AtendenteEntity();
                    a.setSenha(resultSet.getString("senha"));
                    atendenteEntityList.add(a);
                    if (atendenteEntityList.get(0).getSenha().equals(pass)){
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}