package benicio.soluces.marioscar.model;

public class VeiculoModel {
    String id, idCliente, placa, anoFab, anoMode,
    kmAtual, combustivel, nomeCliente, marca, modelo, cor, renavam,
    chassi;

    public VeiculoModel() {
    }

    public VeiculoModel(String id, String idCliente, String placa, String anoFab, String anoMode, String kmAtual, String combustivel, String nomeCliente, String marca, String modelo, String cor, String renavam, String chassi) {
        this.id = id;
        this.idCliente = idCliente;
        this.placa = placa;
        this.anoFab = anoFab;
        this.anoMode = anoMode;
        this.kmAtual = kmAtual;
        this.combustivel = combustivel;
        this.nomeCliente = nomeCliente;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.renavam = renavam;
        this.chassi = chassi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAnoFab() {
        return anoFab;
    }

    public void setAnoFab(String anoFab) {
        this.anoFab = anoFab;
    }

    public String getAnoMode() {
        return anoMode;
    }

    public void setAnoMode(String anoMode) {
        this.anoMode = anoMode;
    }

    public String getKmAtual() {
        return kmAtual;
    }

    public void setKmAtual(String kmAtual) {
        this.kmAtual = kmAtual;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    @Override
    public String toString() {
        return  "Placa: " + placa + '\n' +
                "Ano Fab: " + anoFab + '\n' +
                "Ano Mode: " + anoMode + '\n' +
                "km Atual: " + kmAtual + '\n' +
                "Combustivel: " + combustivel + '\n' +
                "Nome cliente: " + nomeCliente + '\n' +
                "Marca: " + marca + '\n' +
                "Modelo: " + modelo + '\n' +
                "Cor: " + cor + '\n' +
                "Renavam: " + renavam + '\n' +
                "Chassi: " + chassi + '\n' ;
    }
}
