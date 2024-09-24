package src.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Painel extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    private static final int FPS = 60; // Frames por segundo

    // CONFIGURAÇÕES DA TELA
    final int originalTileSize = 16; // Tamanho original de um bloco (16x16)
    final int scale = 3; // Escala para aumentar o tamanho dos blocos
    final int tileSize = originalTileSize * scale; // Tamanho do bloco após escala (48x48)
    final int maxScreenCol = 16; // Número máximo de colunas na tela
    final int maxScreenRow = 12; // Número máximo de linhas na tela
    final int screenWidth = tileSize * maxScreenCol; // Largura total da tela (768 pixels)
    final int screenHeight = tileSize * maxScreenRow; // Altura total da tela (576 pixels)

    // Manipulador de teclado
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // Thread do jogo

    // Posição e velocidade do jogador
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    int quantArvores;
    int quantPedras;
    ArrayList<int[]> arvores = new ArrayList<>();
    ArrayList<int[]> pedras = new ArrayList<>();

    public Painel(int quantPedras, int quantArvores, int dimensao, int[] Frutas, int[] Arvores) {
        // declarações dovalores iniciais
        this.quantArvores = quantArvores;
        this.quantPedras = quantPedras;
        // Configurações do painel de jogo
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
    
    public void gerarTerreno() {
        Random random = new Random();

        // Gerar árvores
        this.quantArvores = quantArvores;
        for (int i = 0; i < quantArvores; i++) {
            int x = random.nextInt(screenWidth / tileSize) * tileSize;
            int y = random.nextInt(screenHeight / tileSize) * tileSize;
            arvores.add(new int[]{x, y});
        }

        // Gerar pedras
        for (int i = 0; i < quantPedras; i++) {
            int x = random.nextInt(screenWidth / tileSize) * tileSize;
            int y = random.nextInt(screenHeight / tileSize) * tileSize;
            pedras.add(new int[]{x, y});
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
        

        // Desenha a árvore como um retângulo verde
        g2.setColor(Color.black);
        for (int[] pos : arvores) {
            g2.fillRect(pos[0], pos[1], tileSize, tileSize);
        }
        
       // Desenha o jogador como um retângulo branco
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        
        g2.dispose(); // Libera os recursos gráficos
    }
    
  
}
