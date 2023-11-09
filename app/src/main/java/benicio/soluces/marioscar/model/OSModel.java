package benicio.soluces.marioscar.model;

import java.util.ArrayList;
import java.util.List;

public class OSModel {
    String id, idCarro, descricao, descricaoPeca, valorTotal, valorService, desconto, total,obs;
    List<String>  fotos = new ArrayList<>();
    Boolean bateria, alarme, buzina, trava, vidro, tapete, chaveRoda, macaco, triangulo,
    extintor, som;

    public OSModel() {
    }

    public OSModel(String id, String idCarro, String descricao, String descricaoPeca, String valorTotal, String valorService, String desconto, String total, String obs, List<String> fotos, Boolean bateria, Boolean alarme, Boolean buzina, Boolean trava, Boolean vidro, Boolean tapete, Boolean chaveRoda, Boolean macaco, Boolean triangulo, Boolean extintor, Boolean som) {
        this.id = id;
        this.idCarro = idCarro;
        this.descricao = descricao;
        this.descricaoPeca = descricaoPeca;
        this.valorTotal = valorTotal;
        this.valorService = valorService;
        this.desconto = desconto;
        this.total = total;
        this.obs = obs;
        this.fotos = fotos;
        this.bateria = bateria;
        this.alarme = alarme;
        this.buzina = buzina;
        this.trava = trava;
        this.vidro = vidro;
        this.tapete = tapete;
        this.chaveRoda = chaveRoda;
        this.macaco = macaco;
        this.triangulo = triangulo;
        this.extintor = extintor;
        this.som = som;
    }

    @Override
    public String toString() {

        return "Descrição: " + descricao + '\n' +
                "\nDescrição peça: " + descricaoPeca + '\n' +
                "\nValor Total: R$ " + valorTotal + '\n' +
                "\nValor Serviço: R$ " + valorService + '\n' +
                "\nDesconto: R$ " + desconto + '\n' +
                "\nTotal: R$ " + total + '\n' +
                "\nObs: " + obs + '\n' +
                "\nBateria: " + (bateria ? "Sim" : "Não") +
                "\nAlarme: " + (alarme ? "Sim" : "Não") +
                "\nBuzina: " + (buzina ? "Sim" : "Não") +
                "\nTrava: " + (trava ? "Sim" : "Não") +
                "\nVidro: " + (vidro ? "Sim" : "Não") +
                "\nTapete: " + (tapete ? "Sim" : "Não") +
                "\nChave de Roda: " + (chaveRoda ? "Sim" : "Não") +
                "\nMacaco: " + (macaco ? "Sim" : "Não") +
                "\nTriângulo: " + (triangulo ? "Sim" : "Não") +
                "\nExtintor: " + (extintor ? "Sim" : "Não") +
                "\nSom: " + (som ? "Sim" : "Não");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(String idCarro) {
        this.idCarro = idCarro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoPeca() {
        return descricaoPeca;
    }

    public void setDescricaoPeca(String descricaoPeca) {
        this.descricaoPeca = descricaoPeca;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValorService() {
        return valorService;
    }

    public void setValorService(String valorService) {
        this.valorService = valorService;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public Boolean getBateria() {
        return bateria;
    }

    public void setBateria(Boolean bateria) {
        this.bateria = bateria;
    }

    public Boolean getAlarme() {
        return alarme;
    }

    public void setAlarme(Boolean alarme) {
        this.alarme = alarme;
    }

    public Boolean getBuzina() {
        return buzina;
    }

    public void setBuzina(Boolean buzina) {
        this.buzina = buzina;
    }

    public Boolean getTrava() {
        return trava;
    }

    public void setTrava(Boolean trava) {
        this.trava = trava;
    }

    public Boolean getVidro() {
        return vidro;
    }

    public void setVidro(Boolean vidro) {
        this.vidro = vidro;
    }

    public Boolean getTapete() {
        return tapete;
    }

    public void setTapete(Boolean tapete) {
        this.tapete = tapete;
    }

    public Boolean getChaveRoda() {
        return chaveRoda;
    }

    public void setChaveRoda(Boolean chaveRoda) {
        this.chaveRoda = chaveRoda;
    }

    public Boolean getMacaco() {
        return macaco;
    }

    public void setMacaco(Boolean macaco) {
        this.macaco = macaco;
    }

    public Boolean getTriangulo() {
        return triangulo;
    }

    public void setTriangulo(Boolean triangulo) {
        this.triangulo = triangulo;
    }

    public Boolean getExtintor() {
        return extintor;
    }

    public void setExtintor(Boolean extintor) {
        this.extintor = extintor;
    }

    public Boolean getSom() {
        return som;
    }

    public void setSom(Boolean som) {
        this.som = som;
    }
}
