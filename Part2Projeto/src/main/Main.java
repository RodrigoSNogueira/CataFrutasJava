package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Criação da janela do jogo
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configura a ação de fechar a janela
        window.setResizable(false); // Impede que a janela seja redimensionada
        window.setTitle("2D Adventure"); // Define o título da janela
        
        // Cria um novo Painel (a área de jogo) e o adiciona à janela
        Painel painel = new Painel();
        window.add(painel);
        
        window.pack(); // Ajusta o tamanho da janela de acordo com os componentes adicionados

        window.setLocationRelativeTo(null); // Centraliza a janela na tela
        window.setVisible(true); // Torna a janela visível
        
        // Inicia o ciclo do jogo
        painel.startGameThread();
    }
}
