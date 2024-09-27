package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CriadorDeTerreno extends JFrame {
    
    public CriadorDeTerreno() {
        setTitle("Criador de Terreno");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        add(painelPrincipal);

        // Configurações
        JPanel configPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        configPanel.setBorder(BorderFactory.createTitledBorder("CONFIGURAÇÕES:"));
        painelPrincipal.add(configPanel);

        // Adicionar campos de texto e rótulos
        configPanel.add(new JLabel("Dimensão:"));
        JTextField dimensaoField = new JTextField();
        configPanel.add(dimensaoField);

        configPanel.add(new JLabel("Quantidade de Pedras:"));
        JTextField pedrasField = new JTextField();
        configPanel.add(pedrasField);

        configPanel.add(new JLabel("Chance de bichadas(%):"));
        JTextField bichadasField = new JTextField();
        configPanel.add(bichadasField);

        configPanel.add(new JLabel("Capacidade da Mochila:"));
        JTextField mochilaField = new JTextField();
        configPanel.add(mochilaField);

        // Frutas
        JPanel frutasPanel = new JPanel(new GridLayout(7, 5, 3, 3));
        frutasPanel.setBorder(BorderFactory.createTitledBorder("FRUTAS E ÁRVORES: "));
        painelPrincipal.add(frutasPanel);

        // Frutas e campos
        String[] frutas = {"Maracujá", "Laranja", "Abacate", "Coco", "Acerola", "Amora", "Goiaba"};

        for (String fruta : frutas) {
            frutasPanel.add(new JLabel(fruta + ":"));
            JTextField arvoresField = new JTextField();
            frutasPanel.add(arvoresField);
            JTextField chaoField = new JTextField();
            frutasPanel.add(chaoField);
        }

        // Painel inferior com botões
        JPanel botoesPanel = new JPanel();
        painelPrincipal.add(botoesPanel);

        // Botão "Importar Terreno"
        JButton importarButton = new JButton("Importar Terreno");
        botoesPanel.add(importarButton);

        // Botão "Exportar Terreno"
        JButton exportarButton = new JButton("Exportar Terreno");
        botoesPanel.add(exportarButton);

        // Botão "Testar Terreno"
        JButton testarButton = new JButton("Testar Terreno");
        botoesPanel.add(testarButton);

        // Ação do botão "Testar Terreno"
        testarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
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
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CriadorDeTerreno().setVisible(true);
            }
        });
    }
}
