package Farmacia;

import java.util.List;

public interface DAOMedicamento {
    void adicionar(MedicamentoEntity m);
    void remover(String id);
    List<MedicamentoEntity> pesquisarPorNome(String nome);
}