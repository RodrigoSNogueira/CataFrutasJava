package projeto;

import java.util.ArrayList;
import java.util.Random;

public class Jogador extends ElementoDinamico{
	private String nome;
	private int forca ;
	private int pontosMov ;
	private int x, y; // Posição do jogador na floresta
	private ArrayList<Frutas> mochila = new ArrayList<>();
	private Floresta floresta;
	private int frutasOuro;
    private Frutas frutas;
    private boolean frutaDeForcaUsada = false;
	
	public Jogador(String nome, Floresta floresta, int x, int y) {
		this.nome = nome;
		this.floresta = floresta;
		this.x = x;
		this.y = y;
		this.forca = 1;
        this.pontosMov = 0;
        this.frutasOuro = 0;
	}
	
	public void consumirFruta() {
		if(!mochila.isEmpty()) {
			Frutas fruta = mochila.remove(0);
			fruta.efeitoFruta(fruta.getNome());
			System.out.println(this.nome + " consumiu uma fruta " + fruta.getNome());
			if (fruta.getNome() == "coco") {
				this.frutaDeForcaUsada = true;
			}
		}
		else {
			System.out.println("Mochila vazia!");
		}
	}
	
	public void catarFruta(Frutas fruta) {
		mochila.add(fruta);
		System.out.println(this.nome + " coletou uma fruta " + fruta.getNome());
		if (fruta.equals("ouro")) {
			this.frutasOuro++;
		}
	}
	
	public void rolarDados() {
		Random random = new Random();
		int dado1 = random.nextInt(6) + 1;
		int dado2 = random.nextInt(6) + 1;
		pontosMov = dado1 + dado2;
	}
	
	@Override
	public void mover(int novaX, int novaY) {
		if(Math.abs(novaX - x) + Math.abs(novaY - 1) == 1) {
			ElementoEstatico elemento = floresta.getElemento(novaX, novaY);
			if(this.pontosMov > 0) {
				if(elemento.getTipo().equals("grama")) {
					moverPara(novaX, novaY, 1);
				}
				else if(elemento.getTipo().equals("pedra")) {
					if(this.pontosMov >= 3) {
						moverPara(novaX, novaY, 1);
					}
					else System.out.println("Não há pontos suficentes para passar pela pedra");
				}
				else if(elemento.getTipo().equals("arvore")) {
					moverPara(novaX, novaY, 1);
				}
				else if(elemento.isTemFruta()) {
					moverPara(novaX, novaY, 1);
                    catarFruta(new Frutas("Fruta", 0));
				}
			}
		}
	}
	

	public void moverPara(int novaX, int novaY, int custo) {
		this.x = novaX;
		this.y = novaY;
		pontosMov -= custo;
	}
	
	public void encontrarJogador(Jogador outroJogador) {
        int fa = frutaDeForcaUsada ? 2 * (mochila.size() - 1) : mochila.size();
        int fd = outroJogador.mochila.size();
        
        int empurrao = (int) (Math.round(Math.log(fa + 1) / Math.log(2)) - Math.round(Math.log(fd + 1) / Math.log(2)));
        int frutasDerrubadas = Math.max(0, empurrao);
        
        if (frutasDerrubadas > 0) {
            System.out.println(nome + " empurrou " + outroJogador.nome + " e derrubou " + frutasDerrubadas + " frutas.");
            outroJogador.perderFrutas(frutasDerrubadas);
        } 
        else {
            System.out.println(nome + " tentou empurrar " + outroJogador.nome + " mas não conseguiu derrubar frutas.");
        }
        frutaDeForcaUsada = false; 
    }
	
	public void perderFrutas(int quantidade) {
        for (int i = 0; i < Math.min(quantidade, mochila.size()); i++) {
            Frutas frutaDerrubada = mochila.remove(mochila.size() - 1); // Remove frutas da mochila
            System.out.println(nome + " perdeu uma fruta: " + frutas.getNome(frutaDerrubada));
        }
    }
	
	 public boolean verificarVitoria(int frutasOuroNecessarias) {
	    return frutasOuro >= frutasOuroNecessarias;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getForca() {
		return forca;
	}

	public void setForca(int forca) {
		this.forca = forca;
	}

	public int getPontosMov() {
		return pontosMov;
	}

	public void setPontosMov(int pontosMov) {
		this.pontosMov = pontosMov;
	}

}
