package projeto;

public class ElementoEstatico {
	private int pedra;
	private int grama = 0;
	private String[] tipoArvore = {"laranjeira", "abacateiro", "coqueiro", "PéDeAcerola", "PéDeAmora", "goiabeira"};
	
	public ElementoEstatico(int QuantPedra) {
		this.pedra = QuantPedra;
	}
	
	public void Arvore(String tipoArvore) {
		if(tipoArvore == this.tipoArvore[0]) { // Laranjeira
			Frutas fruta = new Frutas(1);
		}
		else if(tipoArvore == this.tipoArvore[1]) { // Abacateiro
			Frutas fruta = new Frutas(2);
		}
		else if(tipoArvore == this.tipoArvore[2]) { // Coqueiro
			Frutas fruta = new Frutas(3);
		}
		else if(tipoArvore == this.tipoArvore[3]) { // Pé de acerola
			Frutas fruta = new Frutas(4);
		}
		else if(tipoArvore == this.tipoArvore[4]) { // Pé de amora
			Frutas fruta = new Frutas(5);
		}
		else { // Goiabera
			Frutas fruta = new Frutas(6);
		}
	}

	public Object getTipo() {
		// TODO Auto-generated method stub
		return null;
	}
}
