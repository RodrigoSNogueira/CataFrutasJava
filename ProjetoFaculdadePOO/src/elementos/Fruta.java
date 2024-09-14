package elementos;

import java.util.Random;

public class Fruta extends ElementoDinamico{
	Random random = new Random();
	
	private String tipoFruta;
	private boolean bichada = random.nextBoolean();
	
	
	public Fruta(String tipo) {
		this.tipoFruta = tipo;
	}

	public String getTipoFruta() {
		return tipoFruta;
	}

	public void setTipoFruta(String tipoFruta) {
		this.tipoFruta = tipoFruta;
	}

	
	public void aplicarEfeito() {
		if (this.tipoFruta == "Maracuja") {
			// ouro
			if (this.bichada == true) {
				
			}
		}
		else if (this.tipoFruta == "Coco") {
			if (this.bichada == true) {
				
			}
		}
        else if (this.tipoFruta == "Acerola") {
        	if (this.bichada == true) {
				
			}
		}
        else if (this.tipoFruta == "Laranja") {
        	if (this.bichada == true) {
				
			}
		}
        else if (this.tipoFruta == "Abacate") {
        	if (this.bichada == true) {
				
			}
		}
        else if (this.tipoFruta == "Goiaba") {
        	if (this.bichada = true) {
				
			}
		}
        else if (this.tipoFruta == "Amora") {
        	if (this.bichada == true) {
				
			}
		}
        // else retorne nulo, pois a fruta não existe no jogo 
	}
}
