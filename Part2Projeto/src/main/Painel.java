package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JPanel;

public class Painel extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    private static final int FPS = 60; // Frames por segundo

    // CONFIGURAÇÕES DA TELA
    final int originalTileSize = 16; // Tamanho original de um bloco (16x16)
    final int scale = 3; // Escala para aumentar o tamanho dos blocos
    final int tileSize = originalTileSize * scale; // Tamanho do bloco após escala (48x48)
    int maxScreenCol; // Número máximo de colunas na tela
    int maxScreenRow; // Número máximo de linhas na tela
    int screenWidth = tileSize * maxScreenCol; // Largura total da tela (768 pixels)
    int screenHeight = tileSize * maxScreenRow; // Altura total da tela (576 pixels)

    // Manipulador de teclado
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // Thread do jogo

    // Posição e velocidade do jogador
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    private int[] quantArvores;
    private int quantPedras;
    private int[] quantFrutas;
    ArrayList<int[]> arvores = new ArrayList<>();
    ArrayList<int[]> pedras = new ArrayList<>();
    ArrayList<int[]> frutas = new ArrayList<>();
    ArrayList<String> tiposArvores = new ArrayList<>();
    ArrayList<String> tiposFrutas = new ArrayList<>();
    HashSet<String> ocupados = new HashSet<>(); // Conjunto para rastrear posições ocupadas

    public Painel(int linhas, int quantPedras, int[] quantArvores, int[] quantFrutas) {
        // Configurações do painel de jogo
    	this.maxScreenCol = linhas;
    	this.maxScreenRow = linhas;
        this.quantPedras = quantPedras;
        this.quantArvores = quantArvores;
        this.quantFrutas = quantFrutas;
        // Agora calculamos screenWidth e screenHeight após a inicialização das colunas e linhas
        this.screenWidth = tileSize * maxScreenCol;
        this.screenHeight = tileSize * maxScreenRow;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Define o tamanho do painel
        this.setBackground(Color.black); // Define a cor de fundo como preta
        this.setDoubleBuffered(true); // Otimiza a renderização dos gráficos
        this.addKeyListener(keyH); // Adiciona o manipulador de teclado ao painel
        this.setFocusable(true); // Permite que o painel receba foco para detectar entradas de teclado
        gerarTerreno();
    }

    // Inicia a thread do jogo
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Inicia a execução da thread
    }
    
    // Função para gerar uma string única para uma posição (x, y) a ser usada no HashSet
    public String posicaoChave(int x, int y) {
        return x + "," + y;
    }
    
    public void gerarTerreno() {
        Random random = new Random();

        String[] tiposA = {"maracuja", "laranjeira", "abacateiro", "coqueiro", "PéDeAcerola", "PéDeAmora", "goiabeira"}; // O maracuja aqui está no mesmo campo, mas não é sua árvore, mas a quantidade que vai nascer no chão
        for (int i = 0; i < quantArvores.length; i++) {
        	for (int j = 0; j < quantArvores[i]; j++) {
                int x, y;
                do {
                   x = random.nextInt(screenWidth / tileSize) * tileSize;
                   y = random.nextInt(screenHeight / tileSize) * tileSize;
                } while (ocupados.contains(posicaoChave(x, y))); // Garante que a posição não esteja ocupada
                arvores.add(new int[]{x, y});
                tiposArvores.add(tiposA[i]); // Usa o índice para selecionar o tipo correto de árvore
                ocupados.add(posicaoChave(x, y)); 
            }
        }

        for (int i = 0; i < quantPedras; i++) {
            int x, y;
            do {
                x = random.nextInt(screenWidth / tileSize) * tileSize;
                y = random.nextInt(screenHeight / tileSize) * tileSize;
            } while (ocupados.contains(posicaoChave(x, y))); // Garante que a posição não esteja ocupada
            pedras.add(new int[]{x, y});
            ocupados.add(posicaoChave(x, y)); // Marca a posição como ocupada
        }
        
        String[] tiposF = {"totalMaracuja", "laranja", "abacate", "coco", "acerola", "amora", "goiaba"}; // O totalMaracuja guarda o valor total de maracujás no jogo
        for (int i = 0; i < quantFrutas.length; i++) {
        	for (int j = 0; j < quantFrutas[i]; j++) {
                int x, y;
                do {
                   x = random.nextInt(screenWidth / tileSize) * tileSize;
                   y = random.nextInt(screenHeight / tileSize) * tileSize;
                } while (ocupados.contains(posicaoChave(x, y))); // Garante que a posição não esteja ocupada
                frutas.add(new int[]{x, y});
                tiposFrutas.add(tiposF[i]); // Usa o índice para selecionar o tipo correto de fruta
                ocupados.add(posicaoChave(x, y)); 
            }
        }
    }

    @Override
    public void run() {
        // Ciclo principal do jogo
        double drawInterval = 1000000000 / FPS; // Intervalo de tempo para desenhar um frame
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // Atualiza o estado do jogo e redesenha a tela quando o intervalo for atingido
            if (delta >= 1) {
                update(); // Atualiza a posição do jogador
                repaint(); // Re-desenha o painel
                delta--;
            }
        }
    }

    // Atualiza a posição do jogador de acordo com as teclas pressionadas
    public void update() {
    	if (keyH.upPressed) {
          if (playerY - playerSpeed >= 0) {
                playerY -= playerSpeed;
            }} 
    	else if (keyH.downPressed) {
            if (playerY + tileSize + playerSpeed <= screenHeight) {
                playerY += playerSpeed;
            }} 
    	else if (keyH.leftPressed) {
            if (playerX - playerSpeed >= 0) {
                playerX -= playerSpeed;
            }} 
    	else if (keyH.rightPressed) {
            if (playerX + tileSize + playerSpeed <= screenWidth) {
                playerX += playerSpeed;
            }}
    }

    // Desenha o jogador e outros gráficos no painel
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Chama o método pai para garantir que a tela seja limpa
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.green); // Desenhar a grama em toda tela antes dos outros.
        for (int row = 0; row < maxScreenRow; row++) {
            for (int col = 0; col < maxScreenCol; col++) {
                int x = col * tileSize;
                int y = row * tileSize;
                g2.fillRect(x, y, tileSize, tileSize);
            }
        }

        // Desenha o pedra como um retângulo verde
        g2.setColor(Color.gray);
        for (int[] pos : pedras) {
            g2.fillRect(pos[0], pos[1], tileSize, tileSize);
        }
   
        for (int i = 0; i < arvores.size(); i++) {
            int[] pos = arvores.get(i);
            String tipoArvore = tiposArvores.get(i);
            if(tipoArvore == "maracuja") { // Implementação dos maracujás que nascem no chão
            	g2.setColor(Color.yellow);
            	g2.fillOval(pos[0], pos[1], tileSize, tileSize);
            }
            else {
            switch (tipoArvore) {
                case "laranjeira":
                    g2.setColor(Color.orange); 
                    break;
                case "abacateiro":
                    g2.setColor(Color.blue); 
                    break;
                case "coqueiro":
                    g2.setColor(Color.DARK_GRAY); 
                    break;
                case "PéDeAcerola":
                    g2.setColor(Color.red); 
                    break;
                case "PéDeAmora":
                    g2.setColor(Color.magenta); 
                    break;
                case "goiabeira":
                    g2.setColor(Color.pink); 
                    break;
                default: 
                	break;
            }
            	g2.fillRect(pos[0], pos[1], tileSize, tileSize); 
          }
        }    
        
        for (int i = 0; i < frutas.size(); i++) {
            int[] pos = frutas.get(i);
            String tipoFruta = tiposFrutas.get(i);
            if(tipoFruta == "totalMaracuja") {
            	continue;
            }
            else {
            switch (tipoFruta) {
                case "laranja":
                    g2.setColor(Color.orange);
                    break;
                case "abacate":
                    g2.setColor(Color.blue);
                    break;
                case "coco":
                    g2.setColor(Color.DARK_GRAY);
                    break;
                case "acerola":
                    g2.setColor(Color.red);
                    break;
                case "amora":
                    g2.setColor(Color.magenta);
                    break;
                case "goiaba":
                    g2.setColor(Color.pink);
                    break;
                default: 
                	break;
            }
            g2.fillOval(pos[0], pos[1], tileSize, tileSize); 
          }
        }    
        
       // Desenha o jogador como um retângulo branco
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        
        g2.dispose(); // Libera os recursos gráficos
    }
    
  
}
