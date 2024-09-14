package elementos;

public class Arvore extends Grama  {
	private String tipoArvore;
	
	public Arvore(String tipo) {
		this.tipoArvore = tipo;
	}
	
	public void setArvore(String tipo) {
		this.tipoArvore = tipo;
	}
	public String getArvore() {
		return this.tipoArvore;
	}
	public void gerarFruta() {
		if (this.tipoArvore == "Maracujazeiro") {
			Fruta maracujazeiro = new Fruta("Maracuja");
		}
		else if (this.tipoArvore == "Coqueiro") {
			Fruta coqueiro = new Fruta("Coco");
		}
        else if (this.tipoArvore == "PeDeAcerola") {
			Fruta acerola = new Fruta("Acerola");
		}
        else if (this.tipoArvore == "Laranjeira") {
			Fruta laranjeira = new Fruta("Laranja");
		}
        else if (this.tipoArvore == "Abacateiro") {
			Fruta abacateiro = new Fruta("Abacate");
		}
        else if (this.tipoArvore == "Goiabera") {
			Fruta goiabera = new Fruta("Goiaba");
		}
        else if (this.tipoArvore == "PeDeAmora") {
        	Fruta amora = new Fruta("Amora");
		}
        // else retorne nulo, pois a árvore não existe no jogo 
	}
}
