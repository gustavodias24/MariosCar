package benicio.soluces.marioscar.model;

import benicio.soluces.marioscar.utils.MathUtils;

public class ItemModel {
    String nomeProduto;
    String valor;
    int quantidade = 1;

    public ItemModel() {
    }

    public String getValorPecaMultipl(){
        return  MathUtils.formatarMoeda(MathUtils.converterParaDouble(valor) * quantidade);
    }
    public ItemModel(String nomeProduto, String valor) {
        this.nomeProduto = nomeProduto;
        this.valor = valor;
    }

    public ItemModel(String nomeProduto, String valor, int quantidade) {
        this.nomeProduto = nomeProduto;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        String quantidadeString = "";

        if (quantidade > 1){
            quantidadeString  = MathUtils.padWithZeros(quantidade + "", 2) ;
        }
        return  quantidadeString +" " + nomeProduto + "\nValor: R$" + MathUtils.converterParaDouble(valor) * quantidade ;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
