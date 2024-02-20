package benicio.soluces.marioscar.model;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

import benicio.soluces.marioscar.utils.MathUtils;

public class OSModel {

    Boolean deletado = false;
    String id, idCarro, idCliente, placaCarro, descricao, descricaoPeca, valorTotal, valorService, desconto, total, obs, valorTotalPecas;
    List<String> fotos = new ArrayList<>();
    List<ItemModel> itens = new ArrayList<>();
    List<ItemModel> servicos = new ArrayList<>();

    String numeroOs;
    String data;
    Boolean cabecote, mancaisCabecote, comando, gaiola, vela, bloco, mancaisBloco, virabrequim, biela, motorMontado;

    VeiculoModel veiculoModel;
    UsuarioModel usuarioModel;

    String aguardandoOrcamentoHoraData = "";
    String aguardandoAutorizacaoHoraData = "";
    String servicoAutorizadoHoraData = "";
    String servicoEmExecucaoHoraData = "";
    String servicoConcluidoHoraData = "";
    String saiuParaEntregaHoraData = "";
    String entregueHoraData = "";

    public OSModel() {
    }

    public OSModel(String id,
                   String idCarro,
                   String idCliente,
                   String placaCarro,
                   String descricao,
                   String descricaoPeca,
                   String valorTotal,
                   String valorService,
                   String desconto,
                   String total,
                   String obs,
                   String valorTotalPecas,
                   List<String> fotos,
                   List<ItemModel> itens,
                   List<ItemModel> servicos,
                   String numeroOs,
                   String data,
                   Boolean cabecote,
                   Boolean mancaisCabecote,
                   Boolean comando, Boolean gaiola,
                   Boolean vela,
                   Boolean bloco,
                   Boolean mancaisBloco,
                   Boolean virabrequim,
                   Boolean biela,
                   Boolean motorMontado,
                   VeiculoModel veiculoModel,
                   UsuarioModel usuarioModel,
                   String aguardandoOrcamentoHoraData,
                   String aguardandoAutorizacaoHoraData,
                   String servicoAutorizadoHoraData,
                   String servicoEmExecucaoHoraData,
                   String servicoConcluidoHoraData,
                   String saiuParaEntregaHoraData,
                   String entregueHoraData
    ) {
        this.id = id;
        this.idCarro = idCarro;
        this.idCliente = idCliente;
        this.placaCarro = placaCarro;
        this.descricao = descricao;
        this.descricaoPeca = descricaoPeca;
        this.valorTotal = valorTotal;
        this.valorService = valorService;
        this.desconto = desconto;
        this.total = total;
        this.obs = obs;
        this.valorTotalPecas = valorTotalPecas;
        this.fotos = fotos;
        this.itens = itens;
        this.servicos = servicos;
        this.numeroOs = numeroOs;
        this.data = data;
        this.cabecote = cabecote;
        this.mancaisCabecote = mancaisCabecote;
        this.comando = comando;
        this.gaiola = gaiola;
        this.vela = vela;
        this.bloco = bloco;
        this.mancaisBloco = mancaisBloco;
        this.virabrequim = virabrequim;
        this.biela = biela;
        this.motorMontado = motorMontado;
        this.veiculoModel = veiculoModel;
        this.usuarioModel = usuarioModel;
        this.aguardandoOrcamentoHoraData = aguardandoOrcamentoHoraData;
        this.aguardandoAutorizacaoHoraData = aguardandoAutorizacaoHoraData;
        this.servicoAutorizadoHoraData = servicoAutorizadoHoraData;
        this.servicoEmExecucaoHoraData = servicoEmExecucaoHoraData;
        this.servicoConcluidoHoraData = servicoConcluidoHoraData;
        this.saiuParaEntregaHoraData = saiuParaEntregaHoraData;
        this.entregueHoraData = entregueHoraData;
    }

    public String getAguardandoOrcamentoHoraData() {
        return aguardandoOrcamentoHoraData;
    }

    public void setAguardandoOrcamentoHoraData(String aguardandoOrcamentoHoraData) {
        this.aguardandoOrcamentoHoraData = aguardandoOrcamentoHoraData;
    }

    public String getAguardandoAutorizacaoHoraData() {
        return aguardandoAutorizacaoHoraData;
    }

    public void setAguardandoAutorizacaoHoraData(String aguardandoAutorizacaoHoraData) {
        this.aguardandoAutorizacaoHoraData = aguardandoAutorizacaoHoraData;
    }

    public String getServicoAutorizadoHoraData() {
        return servicoAutorizadoHoraData;
    }

    public void setServicoAutorizadoHoraData(String servicoAutorizadoHoraData) {
        this.servicoAutorizadoHoraData = servicoAutorizadoHoraData;
    }

    public String getServicoEmExecucaoHoraData() {
        return servicoEmExecucaoHoraData;
    }

    public void setServicoEmExecucaoHoraData(String servicoEmExecucaoHoraData) {
        this.servicoEmExecucaoHoraData = servicoEmExecucaoHoraData;
    }

    public String getServicoConcluidoHoraData() {
        return servicoConcluidoHoraData;
    }

    public void setServicoConcluidoHoraData(String servicoConcluidoHoraData) {
        this.servicoConcluidoHoraData = servicoConcluidoHoraData;
    }

    public String getSaiuParaEntregaHoraData() {
        return saiuParaEntregaHoraData;
    }

    public void setSaiuParaEntregaHoraData(String saiuParaEntregaHoraData) {
        this.saiuParaEntregaHoraData = saiuParaEntregaHoraData;
    }

    public String getEntregueHoraData() {
        return entregueHoraData;
    }

    public void setEntregueHoraData(String entregueHoraData) {
        this.entregueHoraData = entregueHoraData;
    }

    public Boolean getDeletado() {
        return deletado;
    }

    public void setDeletado(Boolean deletado) {
        this.deletado = deletado;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {

        StringBuilder itensString = new StringBuilder("");
        StringBuilder servicosString = new StringBuilder("");

        if (!itens.isEmpty()) {
            itensString.append("\nDescrição peça:").append('\n');
            for (ItemModel item : itens) {
                Float valorTotal = (float) (MathUtils.converterParaDouble(item.getValor()) * item.getQuantidade());
                itensString.append(String.format("%s R$ %s X%.2f R$ %.2f", item.getNomeProduto(), item.getValor(), item.getQuantidade(), valorTotal)).append('\n');
            }
        }

        if (!servicos.isEmpty()) {
            servicosString.append("\nDescrição serviço:").append('\n');
            for (ItemModel item : servicos) {
                servicosString.append(String.format("%s R$ %s ", item.getNomeProduto(), item.getValor())).append('\n');
            }
        }
        StringBuilder dataAndHoras = new StringBuilder();
        dataAndHoras.append("\n\n")
                .append("Aguardando Orçamento: ").append(this.aguardandoOrcamentoHoraData)
                .append("\n")
                .append("Aguardando Autorização: ").append(this.aguardandoAutorizacaoHoraData)
                .append("\n")
                .append("Serviço Autorizado: ").append(this.servicoAutorizadoHoraData)
                .append("\n")
                .append("Serviço em Execução: ").append(this.servicoEmExecucaoHoraData)
                .append("\n")
                .append("Serviço Concluído: ").append(this.servicoConcluidoHoraData)
                .append("\n")
                .append("Saiu para Entrega: ").append(this.saiuParaEntregaHoraData)
                .append("\n")
                .append("Entregue: ").append(this.entregueHoraData)
        ;


        return "Número da OS: " + numeroOs + '\n' + "Data: " + data + '\n' + itensString + servicosString + "\nValor total das peças: R$" + valorTotalPecas + '\n' + "Valor total dos serviços: R$ " + valorService + '\n' + "Desconto: R$ " + desconto + '\n' + "Total: R$ " + total + '\n' + "Obs: " + obs + '\n' + "\nCabeçote: " + (cabecote ? "Sim" : "Não") + "\nMancais do cabeçote: " + (mancaisCabecote ? "Sim" : "Não") + "\nComando: " + (comando ? "Sim" : "Não") + "\nGaiola: " + (gaiola ? "Sim" : "Não") + "\nVela: " + (vela ? "Sim" : "Não") + "\nBloco: " + (bloco ? "Sim" : "Não") + "\nMancais do bloco: " + (mancaisBloco ? "Sim" : "Não") + "\nVirabrequim: " + (virabrequim ? "Sim" : "Não") + "\nBiela: " + (biela ? "Sim" : "Não") + "\nMotor montado: " + (motorMontado ? "Sim" : "Não") + dataAndHoras;
    }

    public VeiculoModel getVeiculoModel() {
        return veiculoModel;
    }

    public void setVeiculoModel(VeiculoModel veiculoModel) {
        this.veiculoModel = veiculoModel;
    }

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public List<ItemModel> getServicos() {
        return servicos;
    }

    public void setServicos(List<ItemModel> servicos) {
        this.servicos = servicos;
    }

    public List<ItemModel> getItens() {
        return itens;
    }

    public void setItens(List<ItemModel> itens) {
        this.itens = itens;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValorTotalPecas() {
        return valorTotalPecas;
    }

    public void setValorTotalPecas(String valorTotalPecas) {
        this.valorTotalPecas = valorTotalPecas;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
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

    public Boolean getCabecote() {
        return cabecote;
    }

    public void setCabecote(Boolean cabecote) {
        this.cabecote = cabecote;
    }

    public Boolean getMancaisCabecote() {
        return mancaisCabecote;
    }

    public void setMancaisCabecote(Boolean mancaisCabecote) {
        this.mancaisCabecote = mancaisCabecote;
    }

    public Boolean getComando() {
        return comando;
    }

    public void setComando(Boolean comando) {
        this.comando = comando;
    }

    public Boolean getGaiola() {
        return gaiola;
    }

    public void setGaiola(Boolean gaiola) {
        this.gaiola = gaiola;
    }

    public Boolean getVela() {
        return vela;
    }

    public void setVela(Boolean vela) {
        this.vela = vela;
    }

    public Boolean getBloco() {
        return bloco;
    }

    public void setBloco(Boolean bloco) {
        this.bloco = bloco;
    }

    public Boolean getMancaisBloco() {
        return mancaisBloco;
    }

    public void setMancaisBloco(Boolean mancaisBloco) {
        this.mancaisBloco = mancaisBloco;
    }

    public Boolean getVirabrequim() {
        return virabrequim;
    }

    public void setVirabrequim(Boolean virabrequim) {
        this.virabrequim = virabrequim;
    }

    public Boolean getBiela() {
        return biela;
    }

    public void setBiela(Boolean biela) {
        this.biela = biela;
    }

    public Boolean getMotorMontado() {
        return motorMontado;
    }

    public void setMotorMontado(Boolean motorMontado) {
        this.motorMontado = motorMontado;
    }
}
