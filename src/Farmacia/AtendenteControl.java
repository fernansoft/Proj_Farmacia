package Farmacia;

import javafx.beans.property.*;

public class AtendenteControl {
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty cpf = new SimpleStringProperty("");
    private StringProperty cargo = new SimpleStringProperty("");
    private StringProperty senha = new SimpleStringProperty("");
    private StringProperty confSenha = new SimpleStringProperty("");

    private DAOAtendente daoAtend = new DAOAtendenteImplem();

    private boolean validation = false;

    public AtendenteEntity getEntity() {
        AtendenteEntity a = new AtendenteEntity();
        a.setNome(nome.get().toLowerCase());
        a.setCpf(cpf.get());
        a.setCargo(cargo.get());
        a.setSenha(senha.get());
        return a;
    }

    public String cadastrar() {
        validation = false;
        String msg;
        if (!senha.get().equals(confSenha.get())){
            msg = "Campo senha e repetir senha não conferem!";
            validation = false;
            return msg;
        }else if (!daoAtend.confAtends(nome.get())){
            AtendenteEntity a = getEntity();
            daoAtend.cadastrar(a);
            msg = "Cadastro realizado com sucesso!";
            validation = true;
            return msg;
        }
        msg = "Nome de usuário já existente!";
        validation = false;
        return msg;
    }

    public boolean logar() {
        return daoAtend.confLog(nome.get(), senha.get());
    }

    public boolean validation(){
        return validation;
    }

    public void limpar(){
        nome.setValue("");
        cpf.setValue("");
        cargo.setValue("");
        senha.setValue("");
        confSenha.setValue("");
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getCpf() {
        return cpf.get();
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public String getCargo() {
        return cargo.get();
    }

    public StringProperty cargoProperty() {
        return cargo;
    }

    public String getSenha() {
        return senha.get();
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public String getConfSenha() {
        return confSenha.get();
    }

    public StringProperty confSenhaProperty() {
        return confSenha;
    }
}