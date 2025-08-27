package Entrega.CapstoneRodrigoAraujoVieira.Celulas;

public class Arvore extends CelulaAbstrata {
    public Arvore() {
        super(1);
    }

    @Override
    public CelulaAbstrata processarNovaGeracao(int x, int y, CelulaAbstrata[][] tabuleiro, int geracao, int direcao) {
        // Árvore sobrevive se houver pelo menos uma água na vizinhança imediata ou externa
        boolean temAgua = Regras.temVizinhoInternoOuExterno(tabuleiro, x, y, 3);
        return temAgua ? new Arvore() : new Vazio();
    }
}