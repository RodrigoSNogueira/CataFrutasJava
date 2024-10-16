package projeto;

public class ElementoEstatico {
	private String tipo;
    private boolean temFruta;
    private Frutas fruta;
	private String[] tipoArvore = {"laranjeira", "abacateiro", "coqueiro", "PéDeAcerola", "PéDeAmora", "goiabeira"};
	
	public ElementoEstatico(String tipo, boolean temFruta) {
		this.tipo = tipo;
        this.temFruta = temFruta;
        if (isArvore(tipo)) {
            alocarFruta(tipo);
        }
	}
	
	private boolean isArvore(String tipo) {
		for (String arvore : tipoArvore) {
			if(arvore.equals(tipo)) {
				return true;
			}
		}
		return false;
	}

	public void alocarFruta(String tipoArvore) {
		if(tipoArvore == this.tipoArvore[0]) { // Laranjeira
			Frutas fruta = new Frutas("laranja");
		}
		else if(tipoArvore == this.tipoArvore[1]) { // Abacateiro
			Frutas fruta = new Frutas("abacate");
		}
		else if(tipoArvore == this.tipoArvore[2]) { // Coqueiro
			Frutas fruta = new Frutas("coco");
		}
		else if(tipoArvore == this.tipoArvore[3]) { // Pé de acerola
			Frutas fruta = new Frutas("acerola");
		}
		else if(tipoArvore == this.tipoArvore[4]) { // Pé de amora
			Frutas fruta = new Frutas("amora");
		}
		else if(tipoArvore == this.tipoArvore[5]){ // Goiabera
			Frutas fruta = new Frutas("goiaba");
		}
		else System.out.println("Essa árvore não existe");
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isTemFruta() {
		return temFruta;
	}
	
	public void setTemFruta(boolean temFruta) {
        this.temFruta = temFruta; 
    }
	
	public Frutas getFruta() {
		return fruta;
	}
}
