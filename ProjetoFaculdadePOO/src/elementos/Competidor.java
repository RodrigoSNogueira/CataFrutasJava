package elementos;

import java.util.ArrayList;

public class Competidor extends ElementoDinamico{
	private String nome;
	private int pontoMovimento;
	private int forca = 0;
	private int capacidadeMochila;
	private ArrayList<Fruta> mochila;
	private int x, y; // Posição do joador
	
	public Competidor(String nome) {
		this.nome = nome;
	}
	
	public void mover() {
		
	}
	public void coletarFruta() {
		
	}
	public void consumirFruta() {
		
	}
	public void empurrar() {
		
	}
	
	
	public int getPontoMovimento() {
		return pontoMovimento;
	}
	public void setPontoMovimento(int pontoMovimento) {
		this.pontoMovimento = pontoMovimento;
	}
	
	
	public int getForca() {
		return forca;
	}
	public void setForca(int forca) {
		this.forca = forca;
	}
	
	
	public int getCapacidadeMochila() {
		return capacidadeMochila;
	}
	public void setCapacidadeMochila(int capacidadeMochila) {
		this.capacidadeMochila = capacidadeMochila;
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
