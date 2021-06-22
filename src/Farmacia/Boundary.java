package Farmacia;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import javax.swing.*;
import java.util.Objects;
import java.util.PrimitiveIterator;

public class Boundary extends Application {
    public static Stage stgStage;
    public static Scene scnMedicamento, scnMenu, scnCadastro, scnLogin;

    private final TextField medTxtId = new TextField();
    private final TextField medTxtNome = new TextField();
    private final TextField medTxtPreco = new TextField();
    private final TextField medTxtFabricante = new TextField();
    private final TextField medTxtBula = new TextField();
    private final TextField medTxtQuantidade = new TextField();

    private final TextField cadTxtNome = new TextField();
    private final TextField cadTxtCPF = new TextField();
    private final TextField cadTxtCargo = new TextField();
    private final PasswordField cadTxtPass = new PasswordField();
    private final PasswordField cadTxtPass2 = new PasswordField();

    private final TextField logTxtAtendente = new TextField();
    private final PasswordField logTxtPass = new PasswordField();

    private final Button medBtnCadastrar = new Button("Cadastrar");
    private final Button medBtnPesquisar = new Button("Pesquisar");
    private final Button medBtnLimpar = new Button("Limpar");
    private final Button medBtnInicio = new Button("Inicio");
    private final Button medBtnMostrarItens = new Button("Mostrar todos");

    private final Label medLblId = new Label("ID:");
    Label medTitle = new Label("");

    private final MedicamentoControl medControl = new MedicamentoControl();
    private final AtendenteControl atendControl = new AtendenteControl();

    public Parent telaMenuInicial() {
        BorderPane menuPanePrincipal = new BorderPane();
        VBox menuPaneConteudo = new VBox();
        menuPanePrincipal.setPrefSize(300, 200);

        menuPanePrincipal.getStylesheets().add(Objects.requireNonNull(Boundary.class.getResource("style.css")).toExternalForm());

        Label menuTitle = new Label("Menu Farmácia");
        menuTitle.getStyleClass().add("titulo");

        Button menuBtnPesquisar = new Button("Pesquisar Medicamento");
        Button menuBtnCadastrar = new Button("Cadastrar Medicamento");

        menuPanePrincipal.setTop(menuTitle);
        menuPanePrincipal.setCenter(menuPaneConteudo);
        BorderPane.setAlignment(menuTitle, Pos.CENTER);

        menuPaneConteudo.getChildren().addAll(menuBtnPesquisar, menuBtnCadastrar);
        menuPaneConteudo.setAlignment(Pos.CENTER);
        menuPaneConteudo.setSpacing(25);

        menuBtnCadastrar.setOnAction((e) -> {
            medBtnPesquisar.setVisible(false);
            medBtnLimpar.setVisible(false);
            medTxtId.setVisible(false);
            medLblId.setVisible(false);
            medBtnCadastrar.setVisible(true);
            medTxtNome.requestFocus();
            medTitle.setText("Cadastro de Medicamentos");
            stgStage.setScene(scnMedicamento);
            medControl.pesquisarPorNome();
            stgStage.centerOnScreen();
        });
        menuBtnPesquisar.setOnAction((e) -> {
            medBtnCadastrar.setVisible(false);
            medBtnLimpar.setVisible(true);
            medBtnPesquisar.setVisible(true);
            medTxtId.setVisible(true);
            medLblId.setVisible(true);
            medTitle.setText("Pesquisa de Medicamentos");
            medTxtNome.requestFocus();
            stgStage.setScene(scnMedicamento);
            medControl.pesquisarPorNome();
            stgStage.centerOnScreen();
        });

        return menuPanePrincipal;
    }

    public Parent telaMedicamento() {
        BorderPane medPanePrincipal = new BorderPane();
        VBox medPaneConteudo = new VBox();
        GridPane medPaneForm = new GridPane();
        FlowPane medPaneButton = new FlowPane();
        medPanePrincipal.setPrefSize(600, 750);

        medPanePrincipal.getStylesheets().add(Objects.requireNonNull(Boundary.class.getResource("style.css")).toExternalForm());

        medTitle.getStyleClass().add("titulo");

        medPaneForm.add(medLblId, 0, 0);
        medPaneForm.add(new Label("Nome:"), 0, 1);
        medPaneForm.add(new Label("Preço:"), 0, 2);
        medPaneForm.add(new Label("Fabricante:"), 0, 3);
        medPaneForm.add(new Label("Bula:"), 0, 4);
        medPaneForm.add(new Label("Quantidade:"), 0, 5);
        medPaneForm.add(medTxtId, 1, 0);
        medPaneForm.add(medTxtNome, 1, 1);
        medPaneForm.add(medTxtPreco, 1, 2);
        medPaneForm.add(medTxtFabricante, 1, 3);
        medPaneForm.add(medTxtBula, 1, 4);
        medPaneForm.add(medTxtQuantidade, 1, 5);
        medPaneForm.setVgap(10);
        medPaneForm.setHgap(5);
        medPaneForm.setAlignment(Pos.CENTER);

        medPaneButton.getChildren().addAll(medBtnCadastrar, medBtnPesquisar, medBtnLimpar, medBtnMostrarItens, medBtnInicio);
        medPaneButton.setHgap(10);
        medPaneButton.setVgap(5);
        medPaneButton.setAlignment(Pos.CENTER);

        medPaneConteudo.getChildren().addAll(medTitle, medPaneForm, medPaneButton);
        medPaneConteudo.setSpacing(15);
        medPaneConteudo.setAlignment(Pos.CENTER);

        medControl.generatedTable();

        medPanePrincipal.setTop(medPaneConteudo);

        Insets medInsets = new Insets(25);
        Node medCenterNode = medControl.getTable();
        medPanePrincipal.setCenter(medCenterNode);
        BorderPane.setMargin(medCenterNode, medInsets);

        medBtnCadastrar.setOnAction((p) -> {
            medControl.adicionar();
            medControl.limpar();
        });
        medBtnPesquisar.setOnAction((p) -> {
            if (!medControl.pesquisarPorNome()) {
                JOptionPane.showMessageDialog(null, "Medicamento não encontrado!");
            }
        });
        medBtnLimpar.setOnAction((p) -> medControl.limpar());
        medBtnInicio.setOnAction((e) -> {
            stgStage.setScene(scnMenu);
            stgStage.centerOnScreen();
        });
        medBtnMostrarItens.setOnAction((e) -> {
            medControl.limpar();
            medControl.pesquisarPorNome();
        });

        StringConverter medIntegerToStringConverter = new IntegerStringConverter();
        StringConverter medDoubleStringConverter = new DoubleStringConverter();
        StringConverter medLongStringConverter = new LongStringConverter();

        Bindings.bindBidirectional(medTxtId.textProperty(), medControl.idProperty(), medLongStringConverter);
        Bindings.bindBidirectional(medTxtNome.textProperty(), medControl.nomeProperty());
        Bindings.bindBidirectional(medTxtPreco.textProperty(), medControl.precoProperty(), medDoubleStringConverter);
        Bindings.bindBidirectional(medTxtFabricante.textProperty(), medControl.fabricanteProperty());
        Bindings.bindBidirectional(medTxtBula.textProperty(), medControl.bulaProperty());
        Bindings.bindBidirectional(medTxtQuantidade.textProperty(), medControl.quantidadeProperty(), medIntegerToStringConverter);

        medBtnPesquisar.managedProperty().bind(medBtnPesquisar.visibleProperty());
        medBtnCadastrar.managedProperty().bind(medBtnCadastrar.visibleProperty());
        medBtnLimpar.managedProperty().bind(medBtnLimpar.visibleProperty());
        medTxtId.managedProperty().bind(medTxtId.visibleProperty());
        medLblId.managedProperty().bind(medLblId.visibleProperty());

        return medPanePrincipal;
    }

    public Parent telaCadastro() {
        BorderPane cadPanePrincipal = new BorderPane();
        GridPane cadPaneForm = new GridPane();
        FlowPane cadPaneButton = new FlowPane();
        cadPanePrincipal.setPrefSize(400, 350);

        cadPanePrincipal.getStylesheets().add(Objects.requireNonNull(Boundary.class.getResource("style.css")).toExternalForm());

        Label cadTitle = new Label("Cadastrar Atendente");
        cadTitle.getStyleClass().add("titulo");

        Button cadBtnVoltar = new Button("Voltar");
        Button cadBtnCadastrar = new Button("Cadastrar");

        cadPaneForm.add(new Label("Nome:"), 0, 0);
        cadPaneForm.add(new Label("CPF:"), 0, 1);
        cadPaneForm.add(new Label("Cargo:"), 0, 2);
        cadPaneForm.add(new Label("Senha:"), 0, 3);
        cadPaneForm.add(new Label("Repetir senha:"), 0, 4);
        cadPaneForm.add(cadTxtNome, 1, 0);
        cadPaneForm.add(cadTxtCPF, 1, 1);
        cadPaneForm.add(cadTxtCargo, 1, 2);
        cadPaneForm.add(cadTxtPass, 1, 3);
        cadPaneForm.add(cadTxtPass2, 1, 4);
        cadPaneForm.setVgap(10);
        cadPaneForm.setHgap(5);
        cadPaneForm.setAlignment(Pos.CENTER);

        cadPaneButton.getChildren().addAll(cadBtnVoltar, cadBtnCadastrar);
        cadPaneButton.setHgap(10);
        cadPaneButton.setVgap(5);
        cadPaneButton.setAlignment(Pos.CENTER);

        cadPanePrincipal.setTop(cadTitle);
        cadPanePrincipal.setCenter(cadPaneForm);
        cadPanePrincipal.setBottom(cadPaneButton);

        Insets cadInsets = new Insets(20);
        BorderPane.setAlignment(cadTitle, Pos.CENTER);
        BorderPane.setMargin(cadPanePrincipal.getTop(), cadInsets);
        BorderPane.setMargin(cadPanePrincipal.getBottom(), cadInsets);

        cadBtnCadastrar.setOnAction((p) -> {
            JOptionPane.showMessageDialog(null, atendControl.cadastrar());
            if (atendControl.validation()) {
                atendControl.limpar();
                stgStage.setScene(scnLogin);
                stgStage.centerOnScreen();
            }
        });
        cadBtnVoltar.setOnAction((p) -> {
            stgStage.setScene(scnLogin);
            logTxtAtendente.setText("");
            logTxtPass.setText("");
            stgStage.centerOnScreen();
        });

        Bindings.bindBidirectional(cadTxtNome.textProperty(), atendControl.nomeProperty());
        Bindings.bindBidirectional(cadTxtCPF.textProperty(), atendControl.cpfProperty());
        Bindings.bindBidirectional(cadTxtCargo.textProperty(), atendControl.cargoProperty());
        Bindings.bindBidirectional(cadTxtPass.textProperty(), atendControl.senhaProperty());
        Bindings.bindBidirectional(cadTxtPass2.textProperty(), atendControl.confSenhaProperty());

        return cadPanePrincipal;
    }

    public Parent telaLogin() {
        BorderPane logPanePrincipal = new BorderPane();
        GridPane logPaneForm = new GridPane();
        FlowPane logPaneButton = new FlowPane();
        logPanePrincipal.setPrefSize(400, 350);

        logPanePrincipal.getStylesheets().add(Objects.requireNonNull(Boundary.class.getResource("style.css")).toExternalForm());

        Label logTitle = new Label("Farmácia");
        logTitle.getStyleClass().add("titulo");

        Button logBtnCadastrar = new Button("Cadastrar");
        Button logBtnLogin = new Button("Login");

        logPaneForm.add(new Label("Atendente:"), 0, 0);
        logPaneForm.add(new Label("Senha:"), 0, 1);
        logPaneForm.add(logTxtAtendente, 1, 0);
        logPaneForm.add(logTxtPass, 1, 1);
        logPaneForm.setVgap(10);
        logPaneForm.setHgap(5);
        logPaneForm.setAlignment(Pos.CENTER);

        logPaneButton.getChildren().addAll(logBtnLogin, logBtnCadastrar);
        logPaneButton.setHgap(10);
        logPaneButton.setVgap(5);
        logPaneButton.setAlignment(Pos.CENTER);

        logPanePrincipal.setTop(logTitle);
        logPanePrincipal.setCenter(logPaneForm);
        logPanePrincipal.setBottom(logPaneButton);

        Insets logInsets = new Insets(20);
        BorderPane.setAlignment(logTitle, Pos.CENTER);
        BorderPane.setMargin(logPanePrincipal.getTop(), logInsets);
        BorderPane.setMargin(logPanePrincipal.getBottom(), logInsets);

        logBtnCadastrar.setOnAction((p) -> {
            stgStage.setScene(scnCadastro);
            stgStage.centerOnScreen();
        });
        logBtnLogin.setOnAction((p) -> {
            boolean result = atendControl.logar();
            if (result) {
                stgStage.setScene(scnMenu);
                stgStage.centerOnScreen();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
            }
        });

        Bindings.bindBidirectional(logTxtAtendente.textProperty(), atendControl.nomeProperty());
        Bindings.bindBidirectional(logTxtPass.textProperty(), atendControl.senhaProperty());

        return logPanePrincipal;
    }

    @Override
    public void start(Stage stage) {
        stgStage = stage;

        scnMedicamento = new Scene(telaMedicamento());
        scnMenu = new Scene(telaMenuInicial());
        scnCadastro = new Scene(telaCadastro());
        scnLogin = new Scene(telaLogin());

        stgStage.setScene(scnLogin);
        stgStage.setTitle("Farmacia");
        stgStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}