package projeto;

import java.util.Random;

public class Jogo {
    private Jogador jogador1;
    private Jogador jogador2;
    private int turnoAtual; // 0 = jogador1, 1 = jogador2
    private int frutasOuroNecessarias;

    public Jogo(Jogador jogador1, Jogador jogador2, int frutasOuroNecessarias) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.turnoAtual = 0; 
        this.frutasOuroNecessarias = frutasOuroNecessarias;
    }

    public void iniciarRodada() {
        Jogador jogadorAtual = (turnoAtual == 0) ? jogador1 : jogador2;
        jogadorAtual.rolarDados(); 
        System.out.println("Vez do competidor: " + jogadorAtual.getNome());
        if (jogadorAtual.verificarVitoria(frutasOuroNecessarias)) {
            System.out.println(jogadorAtual.getNome() + " venceu o jogo!");
            return;
        }

        // Lógica de movimentação do jogador e ações na rodada
        // Aqui você deve chamar o método de movimentação de acordo com os comandos do jogador (via teclado ou mouse)
        // jogadorAtual.mover(...);

        // Em algum momento, quando o jogador quiser finalizar sua jogada:
        // jogadorAtual.finalizarJogada(); // Apenas um esqueleto, você vai implementar o botão depois

        turnoAtual = (turnoAtual == 0) ? 1 : 0; // Passa para o próximo turno (próximo jogador)
    }

    public void encerrarRodada() {
        System.out.println("Fim da rodada do jogador atual.");
        iniciarRodada(); 
    }
}