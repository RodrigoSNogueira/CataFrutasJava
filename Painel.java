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
    int screenWidth;  // Largura total da tela
    int screenHeight; // Altura total da tela

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

    // Construtor
    public Painel(int linhas, int colunas, int quantPedras, int[] quantArvores, int[] quantFrutas) {
        this.maxScreenCol = colunas;
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
        gerarTerreno(); // Gera o terreno ao inicializar
    }

    // Método para iniciar a thread do jogo
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // Inicia a execução da thread
    }

    // Função para gerar uma string única para uma posição (x, y) a ser usada no HashSet
    public String posicaoChave(int x, int y) {
        return x + "," + y;
    }

    // Geração do terreno
    public void gerarTerreno() {
        Random random = new Random();

        String[] tiposA = {"PéDeMaracuja", "laranjeira", "abacateiro", "coqueiro", "PéDeAcerola", "PéDeAmora", "goiabeira"};
        for (int i = 0; i < quantArvores.length; i++) {
            int x, y;
            do {
                x = random.nextInt(maxScreenCol) * tileSize;
                y = random.nextInt(maxScreenRow) * tileSize;
            } while (ocupados.contains(posicaoChave(x, y))); // Garante que a posição não esteja ocupada
            arvores.add(new int[]{x, y});
            tiposArvores.add(tiposA[random.nextInt(tiposA.length)]);
            ocupados.add(posicaoChave(x, y)); // Marca a posição como ocupada
        }

        for (int i = 0; i < quantPedras; i++) {
            int x, y;
            do {
                x = random.nextInt(maxScreenCol) * tileSize;
                y = random.nextInt(maxScreenRow) * tileSize;
            } while (ocupados.contains(posicaoChave(x, y))); // Garante que a posição não esteja ocupada
            pedras.add(new int[]{x, y});
            ocupados.add(posicaoChave(x, y)); // Marca a posição como ocupada
        }

        String[] tiposF = {"maracuja", "laranja", "abacate", "coco", "acerola", "amora", "goiaba"};
        for (int i = 0; i < quantFrutas.length; i++) {
            int x, y;
            do {
                x = random.nextInt(maxScreenCol) * tileSize;
                y = random.nextInt(maxScreenRow) * tileSize;
            } while (ocupados.contains(posicaoChave(x, y))); // Garante que a posição não esteja ocupada
            frutas.add(new int[]{x, y});
            tiposFrutas.add(tiposF[random.nextInt(tiposF.length)]); // Escolhe um tipo aleatório
            ocupados.add(posicaoChave(x, y)); // Marca a posição como ocupada
        }
    }

    // Método principal do jogo, chamado pela thread
    @Override
    public void run() {
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
            }
        } else if (keyH.downPressed) {
            if (playerY + tileSize + playerSpeed <= screenHeight) {
                playerY += playerSpeed;
            }
        } else if (keyH.leftPressed) {
            if (playerX - playerSpeed >= 0) {
                playerX -= playerSpeed;
            }
        } else if (keyH.rightPressed) {
            if (playerX + tileSize + playerSpeed <= screenWidth) {
                playerX += playerSpeed;
            }
        }
    }

    // Desenha o jogador e outros gráficos no painel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Chama o método pai para garantir que a tela seja limpa
        Graphics2D g2 = (Graphics2D) g;

        // Desenha a grama em toda tela antes dos outros elementos
        g2.setColor(Color.green);
        for (int row = 0; row < maxScreenRow; row++) {
            for (int col = 0; col < maxScreenCol; col++) {
                int x = col * tileSize;
                int y = row * tileSize;
                g2.fillRect(x, y, tileSize, tileSize);
            }
        }

        // Desenha pedras
        g2.setColor(Color.gray);
        for (int[] pos : pedras) {
            g2.fillRect(pos[0], pos[1], tileSize, tileSize);
        }

        // Desenha árvores
        for (int i = 0; i < arvores.size(); i++) {
            int[] pos = arvores.get(i);
            String tipoArvore = tiposArvores.get(i);

            switch (tipoArvore) {
                case "PéDeMaracuja":
                    g2.setColor(Color.darkGray);
                    break;
                case "laranjeira":
                    g2.setColor(Color.orange);
                    break;
                case "abacateiro":
                    g2.setColor(Color.green);
                    break;
                case "coqueiro":
                    g2.setColor(Color.cyan);
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
                    g2.setColor(Color.black); // Cor padrão para árvores desconhecidas
            }
            g2.fillRect(pos[0], pos[1], tileSize, tileSize);
        }

        // Desenha frutas
        for (int i = 0; i < frutas.size(); i++) {
            int[] pos = frutas.get(i);
            String tipoFruta = tiposFrutas.get(i);

            switch (tipoFruta) {
                case "maracuja":
                    g2.setColor(Color.yellow);
                    break;
                case "laranja":
                    g2.setColor(Color.orange);
                    break;
                case "abacate":
                    g2.setColor(Color.green);
                    break;
                case "coco":
                    g2.setColor(Color.white);
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
                    g2.setColor(Color.black); // Cor padrão para frutas desconhecidas
            }
            g2.fillRect(pos[0], pos[1], tileSize, tileSize);
        }

        // Desenha o jogador
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize); // Desenha o jogador como um quadrado branco

        g2.dispose(); // Libera os recursos do gráfico após a renderização
    }
}
