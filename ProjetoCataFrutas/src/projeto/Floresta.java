package projeto;

import java.util.Iterator;
import java.util.Random;

public class Floresta {
	private ElementoEstatico matriz[][];
	private int tamanho;
	private Random random = new Random();
	
	public Floresta(int tamanho) {
		this.tamanho = tamanho;
		this.matriz = new ElementoEstatico[tamanho][tamanho];
		inicializarFloresta();
	}
	
	public void inicializarFloresta() {
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				matriz[i][j] = new ElementoEstatico("grama", false); // Grana sen fruta
			}
		}
		alocarElementos();
	}
	
	public void alocarElementos() {
		 for (int i = 0; i < tamanho / 4; i++) {
	         int x = random.nextInt(tamanho);
	         int y = random.nextInt(tamanho);
	         matriz[x][y] = new ElementoEstatico("pedra", false);
	     }
		 for (int i = 0; i < tamanho / 3; i++) {
	         int x = random.nextInt(tamanho);
	         int y = random.nextInt(tamanho);
	        matriz[x][y] = new ElementoEstatico("arvore", true);
	     }
		 for (int i = 0; i < tamanho / 2; i++) {
	         int x = random.nextInt(tamanho);
	         int y = random.nextInt(tamanho);
	        matriz[x][y] = new ElementoEstatico("grama", true); // Grama com frutas
	     }
	}
	
	 public ElementoEstatico getElemento(int x, int y) {
	        if (posicaoValida(x, y)) {
	            return matriz[x][y];
	        }
	        return null; 
	    }

	    public boolean posicaoValida(int x, int y) {
	        return x >= 0 && x < tamanho && y >= 0 && y < tamanho;
	    }

	    public int getTamanho() {
	        return tamanho;
	    }
}
