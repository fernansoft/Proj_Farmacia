package Farmacia;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.List;


public class MedicamentoControl {
    private ObservableList<MedicamentoEntity> medicamentos = FXCollections.observableArrayList();
    private TableView<MedicamentoEntity> tableMedicamentos = new TableView<>();

    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private DoubleProperty preco = new SimpleDoubleProperty(0.0);
    private StringProperty fabricante = new SimpleStringProperty("");
    private StringProperty bula = new SimpleStringProperty("");
    private IntegerProperty quantidade = new SimpleIntegerProperty(0);

    private DAOMedicamento daoMed = new DAOMedicamentoImplem();

    public void setEntity(MedicamentoEntity m) {
        if (m != null) {
            id.set(m.getId());
            nome.set(m.getNome());
            preco.set(m.getPreco());
            fabricante.set(m.getFabricante());
            bula.set(m.getBula());
            quantidade.set(m.getQuantidade());
        }
    }

    public MedicamentoEntity getEntity() {
        MedicamentoEntity m = new MedicamentoEntity();
        m.setId(id.get());
        m.setNome(nome.get());
        m.setPreco(preco.get());
        m.setFabricante(fabricante.get());
        m.setBula(bula.get());
        m.setQuantidade(quantidade.get());
        return m;
    }

    public void generatedTable() {
        TableColumn<MedicamentoEntity, String> colId = new TableColumn<>("Id");
        TableColumn<MedicamentoEntity, String> colNome = new TableColumn<>("Nome");
        TableColumn<MedicamentoEntity, Double> colPreco = new TableColumn<>("Preço");
        TableColumn<MedicamentoEntity, String> colFabricante = new TableColumn<>("Fabricante");
        TableColumn<MedicamentoEntity, String> colBula = new TableColumn<>("Bula");
        TableColumn<MedicamentoEntity, Integer> colQuantidade = new TableColumn<>("Quantidade");
        TableColumn<MedicamentoEntity, String> colAcao = new TableColumn<>("Ação");

        Callback<TableColumn<MedicamentoEntity, String>, TableCell<MedicamentoEntity, String>> cellFactory = col ->
                new TableCell<MedicamentoEntity, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            final Button btnApagar = new Button("Apagar");
                            btnApagar.setOnAction((e) -> {
                                daoMed.remover(String.valueOf(medicamentos.get(getIndex()).getId()));
                                medicamentos.remove(getIndex());
                            });
                            setGraphic(btnApagar);
                            setText(null);
                        }
                    }
                };
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colFabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
        colBula.setCellValueFactory(new PropertyValueFactory<>("bula"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colAcao.setCellFactory(cellFactory);

        tableMedicamentos.getColumns().addAll(colId, colNome, colPreco, colFabricante, colBula, colQuantidade, colAcao);
        tableMedicamentos.getSelectionModel().selectedItemProperty().addListener((obj, antigo, novo) -> {
            setEntity(antigo);
            setEntity(novo);
        });
        tableMedicamentos.setItems(medicamentos);

        tableMedicamentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void adicionar(){
        MedicamentoEntity m = getEntity();
        daoMed.adicionar(m);
    }

    public boolean remover() {
        for (MedicamentoEntity m : medicamentos) {
            if (m.getNome().toLowerCase().contains(nome.get().toLowerCase())) {
                medicamentos.remove(m);
                return true;
            }
        }
        return false;
    }

    public boolean pesquisarPorNome(){
        List<MedicamentoEntity> medListPesquisa = daoMed.pesquisarPorNome(nome.get());
        if (medListPesquisa==null){
            return false;
        }
        medicamentos.clear();
        medicamentos.addAll(medListPesquisa);
        return true;
    }

    public void limpar() {
        MedicamentoEntity mEmpty = new MedicamentoEntity();
        setEntity(mEmpty);
    }

    public TableView<MedicamentoEntity> getTable() {
        return tableMedicamentos;
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public double getPreco() {
        return preco.get();
    }

    public DoubleProperty precoProperty() {
        return preco;
    }

    public String getFabricante() {
        return fabricante.get();
    }

    public StringProperty fabricanteProperty() {
        return fabricante;
    }

    public String getBula() {
        return bula.get();
    }

    public StringProperty bulaProperty() {
        return bula;
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }
}