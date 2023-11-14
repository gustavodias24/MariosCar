package benicio.soluces.marioscar.model;

public class UsuarioModel {
    String id ,nome, nome2,rua, bairro, cidade, uf , cep,telefone, whatsapp, email, documento, data;

    public UsuarioModel() {
    }

    public UsuarioModel(String id, String nome, String nome2, String rua, String bairro, String cidade, String uf, String cep, String telefone, String whatsapp, String email, String documento, String data) {
        this.id = id;
        this.nome = nome;
        this.nome2 = nome2;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.telefone = telefone;
        this.whatsapp = whatsapp;
        this.email = email;
        this.documento = documento;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome2() {
        return nome2;
    }

    public void setNome2(String nome2) {
        this.nome2 = nome2;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return  "Nome: " + nome + '\n' +
                "Nome fantasia: " + nome2 + '\n' +
                "Rua: " + rua + '\n' +
                "Bairro: " + bairro + '\n' +
                "Cidade: " + cidade + '\n' +
                "UF: " + uf + '\n' +
                "Cep: " + cep + '\n' +
                "Telefone: " + telefone + '\n' +
                "Whatsapp: " + whatsapp + '\n' +
                "Email: " + email + '\n' +
                "Documento: " + documento + '\n' +
                "Nascimento: " + data ;
    }
}
