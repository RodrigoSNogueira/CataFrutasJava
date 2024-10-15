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
		int chanceBichada = 0; // Ver como definir esse valor, como forme o passado pelo usuário, para que seja o mesmo para frutas que nascem no chão e nas árvores
		if(tipoArvore == this.tipoArvore[0]) { // Laranjeira
			Frutas fruta = new Frutas("laranja", chanceBichada);
		}
		else if(tipoArvore == this.tipoArvore[1]) { // Abacateiro
			Frutas fruta = new Frutas("abacate", chanceBichada);
		}
		else if(tipoArvore == this.tipoArvore[2]) { // Coqueiro
			Frutas fruta = new Frutas("coco", chanceBichada);
		}
		else if(tipoArvore == this.tipoArvore[3]) { // Pé de acerola
			Frutas fruta = new Frutas("acerola", chanceBichada);
		}
		else if(tipoArvore == this.tipoArvore[4]) { // Pé de amora
			Frutas fruta = new Frutas("amora", chanceBichada);
		}
		else { // Goiabera
			Frutas fruta = new Frutas("goiaba", chanceBichada);
		}
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isTemFruta() {
		return temFruta;
	}
	
	public Frutas getFruta() {
		return fruta;
	}
}
