package Farmacia;

import java.util.List;

public interface DAOAtendente {
    void cadastrar(AtendenteEntity a);
    boolean confAtends(String nome);
    boolean confLog(String nome, String pass);
}
