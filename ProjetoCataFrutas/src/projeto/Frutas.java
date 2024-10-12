package projeto;

import java.util.Random;

public class Frutas extends ElementoDinamico{
	private int chanceBichada;
	private String[] frutas = {"maracuja", "laranja", "abacate", "coco", "acerola", "amora", "goiaba"};
	private Random random;
	
	public Frutas(int bichada) {
		this.chanceBichada = bichada;
		this.random = new Random();
	}
	
	public boolean isBichada() {
        double chance = random.nextDouble() * 100;
        return chance <= chanceBichada;
    }
	
	public void efeitoFruta(String fruta) {
		if(fruta == this.frutas[1]) { // Laranja, anula o efeito de uma bichada
			
		}
		else if(fruta == this.frutas[2]) { // Abacate, dobra o número de pontos de movimento
			
		}
		else if(fruta == this.frutas[3]) { // Coco, dobra o número de força do jogador
			
		}		
	}
	
	@Override
	public void mover(int novaX, int novaY) {
		// TODO Auto-generated method stub
		
	}
	
	 public String getNome(int i) {
		 if (i >= 0 && i < frutas.length) {
	            return frutas[i];
	     }
		 return "Fruta desconhecida";
	    }
}
