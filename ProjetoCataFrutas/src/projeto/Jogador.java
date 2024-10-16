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
    private boolean bichado = false;
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
	
	public void aplicarBichada() {
	    this.bichado = true;
	    System.out.println(this.nome + " está bichado! Não poderá se mover na próxima rodada.");
	}
	
	public void consumirFruta() {
		if(!mochila.isEmpty()) {
			Frutas fruta = mochila.remove(0);
			fruta.efeitoFruta(fruta.getNome()); 
			System.out.println(this.nome + " consumiu uma fruta " + fruta.getNome()); 
			if (fruta.isBichada()) {
				aplicarBichada();
			}
			else if (fruta.getNome().equals("laranja")) {
				this.bichado = false;
			}
			else if (fruta.getNome().equals("abacate")) {
				this.pontosMov = pontosMov * 2;
			}
			else if (fruta.getNome().equals("coco")) { 
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
		if (fruta.getNome().equals("maracuja")) {
			this.frutasOuro++;
		}
		this.forca++;
	}
	
	public void rolarDados() {
		Random random = new Random();
		int dado1 = random.nextInt(6) + 1;
		int dado2 = random.nextInt(6) + 1;
		pontosMov = dado1 + dado2;
	}
	
	@Override
	public void mover(int novaX, int novaY) {
		if (bichado) {
			boolean temLaranja = mochila.stream().anyMatch(fruta -> fruta.getNome().equals("laranja"));
			if (temLaranja) {
				System.out.println("Você está bichado, mas possui uma laranja na mochila que pode consumir para anular o efeito, você deseja fazer isso? "); 
				// Aplicar um JBottom, se o usuário clicar no sim, ativar o efeito da laranja e remove-lá da mochila
			}
			else {
				System.out.println("Você está  bichado, logo não pode se mover , a menos que consuma uma laranja!");
				this.bichado = false;
				return;
			}
		}
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
                    catarFruta(new Frutas("Fruta"));
				}
			}
			else {
				System.out.println("Sem pontos de movimento restantes!");
			}
		}
		else {
			System.out.println("Movimento inválido. Escolha uma célula adjacente.");
		}
	}
	

	public void moverPara(int novaX, int novaY, int custo) {
		this.x = novaX;
		this.y = novaY;
		pontosMov -= custo;
	}
	
	public void finalizarJogada() {
        System.out.println(nome + " finalizou sua jogada.");
        pontosMov = 0; 
        // Implementar botão para finalizar a jogada
    }

	private boolean estaAdjacente(Jogador outroJogador) {
	    int dx = Math.abs(this.x - outroJogador.getX());
	    int dy = Math.abs(this.y - outroJogador.getY());
	    return dx + dy == 1; 
	}
	
	public void encontrarJogador(Jogador outroJogador) {
		if (estaAdjacente(outroJogador)) {
        int fa = frutaDeForcaUsada ? 2 * (this.forca - 1) : this.forca;
        int fd = outroJogador.forca;
        
        int empurrao = (int) (Math.round(Math.log(fa + 1) / Math.log(2)) - Math.round(Math.log(fd + 1) / Math.log(2)));
        int frutasDerrubadas = Math.max(0, empurrao);
        
        if (frutasDerrubadas > 0) {
            System.out.println(nome + " empurrou " + outroJogador.nome + " e derrubou " + frutasDerrubadas + " frutas.");
            outroJogador.espalharFrutas(floresta, frutasDerrubadas);
            outroJogador.perderFrutas(frutasDerrubadas);
        } 
        else {
            System.out.println(nome + " tentou empurrar " + outroJogador.nome + " mas não conseguiu derrubar frutas.");
        }
        frutaDeForcaUsada = false;
		}
    }
	
	public void espalharFrutas(Floresta floresta, int quantidade) {
		int frutasEspalhadas = 0;
		for (int dx = -1; dx <= 1 && frutasEspalhadas < quantidade; dx++) {
			for (int dy = -1; dy <= 1 && frutasEspalhadas < quantidade; dy++) {
				int novoX = this.x + dx;
				int novoY = this.y + dx;
				if (floresta.posicaoValida(novoX, novoY)) {
					ElementoEstatico elemento = floresta.getElemento(novoX, novoY);
	                if (elemento.getTipo().equals("grama") && !elemento.isTemFruta()) {
	                	elemento.setTemFruta(true); // Coloca a fruta na célula
	                    frutasEspalhadas++;
	                }
				}
			}
		}
	}
	
	public void perderFrutas(int quantidade) {
        for (int i = 0; i < Math.min(quantidade, mochila.size()); i++) {
            Frutas frutaDerrubada = mochila.remove(mochila.size() - 1); // Remove frutas da mochila
            System.out.println(nome + " perdeu uma fruta: " + frutas.getNome()); 
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
