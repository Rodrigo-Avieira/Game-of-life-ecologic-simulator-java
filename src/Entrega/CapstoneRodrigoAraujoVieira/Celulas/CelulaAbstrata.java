package Entrega.CapstoneRodrigoAraujoVieira.Celulas;

public abstract class CelulaAbstrata {
    protected int tipo; // 0=vazio, 1=árvore, 2=animal, 3=água

    public CelulaAbstrata(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public abstract CelulaAbstrata processarNovaGeracao(int x, int y, CelulaAbstrata[][] tabuleiro, int geracao, int direcao);

    public boolean isVazio() {

        return tipo == 0;
    }
    public boolean isArvore() {

        return tipo == 1;
    }
    public boolean isAnimal() {

        return tipo == 2;
    }
    public boolean isAgua() {

        return tipo == 3;
    }
}


