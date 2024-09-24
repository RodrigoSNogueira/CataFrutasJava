package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CriadorDeTerreno extends JFrame {
    // cria parametros da criação de terreno
    private String quantidadePedras;
    private String capacidadeMochila;
    private String chanceBichadas;
    private String dimensao;
    private String[] frutasChao = new String[7];
    private String[] frutasArvore = new String[7];


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
        int tamanho = 7;
        JTextField[] arvoresField = new JTextField[tamanho];
        JTextField[] chaoField = new JTextField[tamanho];
        int j = 0;

        for (String fruta : frutas) {
            frutasPanel.add(new JLabel(fruta + ":"));
            arvoresField[j] = new JTextField();
            frutasPanel.add(arvoresField[j]);
            chaoField[j] = new JTextField();
            frutasPanel.add(chaoField[j]);
            j++;
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
                // atualiza os parametros do criador do terreno
                quantidadePedras = pedrasField.getText();
                capacidadeMochila = mochilaField.getText();
                dimensao = dimensaoField.getText();
                chanceBichadas = bichadasField.getText();
                for(int i = 0; i < tamanho; i++) {
                    frutasChao[i] = chaoField[i].getText();
                    frutasArvore[i] = arvoresField[i].getText();
                }
                JOptionPane.showMessageDialog(null, "Terreno testado!");
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
