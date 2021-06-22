package Farmacia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOMedicamentoImplem implements DAOMedicamento {
    private static final String URL = "jdbc:mariadb://localhost:3306/farmaciaDB";
    private static final String USER = "root";
    private static final String PASSWORD = "D0bm";

    @Override
    public void adicionar(MedicamentoEntity m) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "insert into medicamento " +
                    "(nome, preco, fabricante, bula, quantidade) " +
                    "values (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, m.getNome());
            statement.setDouble(2, m.getPreco());
            statement.setString(3, m.getFabricante());
            statement.setString(4, m.getBula());
            statement.setInt(5, m.getQuantidade());
            int i = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(String id) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "delete from medicamento where id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, id);
            int i = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MedicamentoEntity> pesquisarPorNome(String nome) {
        List<MedicamentoEntity> medicamentoEntityList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "select * from medicamento where nome like ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, "%" + nome + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MedicamentoEntity m = new MedicamentoEntity();
                m.setId(resultSet.getLong("id"));
                m.setNome(resultSet.getString("nome"));
                m.setPreco(resultSet.getDouble("preco"));
                m.setFabricante(resultSet.getString("fabricante"));
                m.setBula(resultSet.getString("bula"));
                m.setQuantidade(resultSet.getInt("quantidade"));
                medicamentoEntityList.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentoEntityList;
    }
}
