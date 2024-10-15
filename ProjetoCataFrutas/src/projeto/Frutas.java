package projeto;

import java.util.Random;

public class Frutas extends ElementoDinamico{
	private String nome;
	private int chanceBichada;
	private Random random;
	
	public Frutas(String nome,int bichada) {
		this.nome = nome;
		this.chanceBichada = bichada;
		this.random = new Random();
	}
	
	public boolean isBichada() {
        double chance = random.nextDouble() * 100;
        return chance <= chanceBichada;
    }
	
	public void efeitoFruta(String fruta) {
		switch (nome) {
		case "laranja": // Laranja, anula o efeito de uma bichada		
			break;
		case "abacate": // Abacate, dobra o número de pontos de movimento
			break;
		case "coco": // Coco, dobra a força do jogador
			break; 
		}
	}
	
	@Override
	public void mover(int novaX, int novaY) {
		
	}
	
	 public String getNome() {
		 return nome;
    }
}
