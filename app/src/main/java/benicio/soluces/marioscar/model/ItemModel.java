package benicio.soluces.marioscar.model;

public class ItemModel {
    String nomeProduto;
    float valor;
    float quantidade = 0.0f;

    public ItemModel() {
    }

    public ItemModel(String nomeProduto, float valor) {
        this.nomeProduto = nomeProduto;
        this.valor = valor;
    }

    public ItemModel(String nomeProduto, float valor, float quantidade) {
        this.nomeProduto = nomeProduto;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        String quantidadeString = "";

        if (quantidade > 0){
            quantidadeString  = " Quantidade: " + quantidade;
        }
        return  "Produto: " + nomeProduto + "\nValor: R$" + valor + quantidadeString;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }
}
