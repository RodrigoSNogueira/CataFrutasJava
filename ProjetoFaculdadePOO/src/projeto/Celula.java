package projeto;
import elementos.ElementoDinamico;
import elementos.ElementoEstatico;

public class Celula {
	private ElementoEstatico elementoE;
	private ElementoDinamico elementoD;
	private int x, y;
	
	public ElementoEstatico getElementoE() {
		return elementoE;
	}
	public void setElementoE(ElementoEstatico elementoE) {
		this.elementoE = elementoE;
	}
	
	public ElementoDinamico getElementoD() {
		return elementoD;
	}
	public void setElementoD(ElementoDinamico elementoD) {
		this.elementoD = elementoD;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
