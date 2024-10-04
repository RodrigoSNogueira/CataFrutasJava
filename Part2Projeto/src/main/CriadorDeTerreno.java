package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class CriadorDeTerreno extends JFrame {
    
    public CriadorDeTerreno() {
        setTitle("Criador de Terreno");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        add(painelPrincipal);

        // Configurações
        JPanel configPanel = new JPanel(new GridBagLayout());
        configPanel.setBorder(BorderFactory.createTitledBorder("CONFIGURAÇÕES:"));
        painelPrincipal.add(configPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  
        gbc.fill = GridBagConstraints.BOTH;       
        gbc.weightx = 1.0;                        
        gbc.weighty = 0.2;                       
        // Adicionar campos de texto e rótulos
        gbc.gridx = 0;
        gbc.gridy = 0;
        configPanel.add(new JLabel("Dimensão:"), gbc);
        gbc.gridx = 1;
        JTextField dimensaoFieldX = new JTextField(10);
        configPanel.add(dimensaoFieldX, gbc);

        gbc.gridx = 2; 
        configPanel.add(new JLabel("Capacidade da Mochila:"), gbc);
        gbc.gridx = 3;
        JTextField mochilaField = new JTextField(10);
        configPanel.add(mochilaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        configPanel.add(new JLabel("Quantidade de Pedras:"), gbc);
        gbc.gridx = 1;
        JTextField pedrasField = new JTextField(10);
        configPanel.add(pedrasField, gbc);

        gbc.gridx = 2;
        configPanel.add(new JLabel("Chance de bichadas (%):"), gbc);
        gbc.gridx = 3;
        JTextField bichadasField = new JTextField(10);
        configPanel.add(bichadasField, gbc);

        JPanel frutasPanel = new JPanel(new GridLayout(7, 5, 3, 3));
        frutasPanel.setBorder(BorderFactory.createTitledBorder("FRUTAS E ÁRVORES: "));
        painelPrincipal.add(frutasPanel);

        // Frutas e campos
        String[] frutas = {"maracuja", "laranja", "abacate", "coco", "acerola", "amora", "goiaba"};

        ArrayList<JTextField> arvoresFields = new ArrayList<>();
        ArrayList<JTextField> chaoFields = new ArrayList<>();

        for (String fruta : frutas) {
            frutasPanel.add(new JLabel(fruta + ":"));         
            
            JTextField chaoField = new JTextField();
            chaoFields.add(chaoField); 
            frutasPanel.add(chaoField);
            
            JTextField arvoresField = new JTextField();
            arvoresFields.add(arvoresField); 
            frutasPanel.add(arvoresField);
        }

        // Painel inferior com botões
        JPanel botoesPanel = new JPanel();
        painelPrincipal.add(botoesPanel);

        JButton importarButton = new JButton("Importar Terreno");
        botoesPanel.add(importarButton);
        
        importarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              // Abrir um arquivo existente
              JFileChooser fileChooser = new JFileChooser();
              fileChooser.setDialogTitle("Selecionar Configurações do Terreno");
              fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
              // Opção para escolher o arquivo
              int userSelection = fileChooser.showOpenDialog(null);
              if (userSelection == JFileChooser.APPROVE_OPTION) {
                 File fileToOpen = fileChooser.getSelectedFile();
                 Path caminhoArquivo = Paths.get(fileToOpen.getAbsolutePath());
                 // Lê as configurações do arquivo
                 try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo.toFile()))) {
                   String line;
                   int linhas = 0, quantPedras = 0 , chanceBichadas , capacidadeMochila ;
                    ArrayList<Integer> quantArvores = new ArrayList<>();
                    ArrayList<Integer> quantFrutas = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("dimensao")) {
                        	linhas = Integer.parseInt(line.split(" ")[1].trim());
                        	if (linhas < 3) {
                                JOptionPane.showMessageDialog(null, "A dimensão mínima permitida é 3. Por favor, insira um valor maior ou igual a 3.");
                                return; 
                            }
                        } 
                        else if (line.startsWith("pedras")) {
                        	quantPedras = Integer.parseInt(line.split(" ")[1].trim());
                        } 
                        else if (line.startsWith("bichadas")) {
                            chanceBichadas = Integer.parseInt(line.split(" ")[1].trim());
                        } else if (line.startsWith("mochila")) {
                            capacidadeMochila = Integer.parseInt(line.split(" ")[1].trim());
                        }
                        else {
                            String[] partes = line.split(" ");
                            String fruta = partes[0];
                            int arvores = Integer.parseInt(partes[1].trim());
                            int frutasNoChao = Integer.parseInt(partes[2].trim());
                            if (fruta.equals("maracuja")) { // Para o maracujá, o primeiro valor representa a quantidade total no jogo e o segundo valor quantas nascem no início
                                quantArvores.add(0);
                                quantFrutas.add(Math.max(1, frutasNoChao)); // Deve ter pelo menos 1 fruta no chão
                            } 
                            else {
                                quantArvores.add(arvores);
                                quantFrutas.add(frutasNoChao);
                            }
                        }
                   }              
                   dispose();
                   JFrame window = new JFrame();
                   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   window.setResizable(false);
                   window.setTitle("2D Adventure");
                    
                   int[] quantArvoresArray = quantArvores.stream().mapToInt(i -> i).toArray();
                   int[] quantFrutasArray = quantFrutas.stream().mapToInt(i -> i).toArray();

                   Painel painel = new Painel(linhas, quantPedras, quantArvoresArray, quantFrutasArray);
                   window.add(painel);
                   window.pack();
                   window.setLocationRelativeTo(null);
                   window.setVisible(true);
                   painel.startGameThread();
                  } 
                  catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + ex.getMessage());
                  } 
                  catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo: formato inválido.");
                  }
        }}});

        JButton exportarButton = new JButton("Exportar Terreno");
        botoesPanel.add(exportarButton);
        
        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(); // Opção para salvar o arquivo
                fileChooser.setDialogTitle("Salvar Configurações do Terreno");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);     
                int userSelection = fileChooser.showSaveDialog(null); // Opção para escolher onde salvar o arquivo
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                  File fileToSave = fileChooser.getSelectedFile();
                  Path caminhoArquivo = Paths.get(fileToSave.getAbsolutePath());
                  try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo.toFile()))) {
                    writer.write("dimensao " + dimensaoFieldX.getText().trim());
                    writer.newLine();
                    writer.write("pedras " + pedrasField.getText().trim());
                    writer.newLine();
                    for (int i = 0; i < frutas.length; i++) {
                         writer.write(frutas[i] + " " + arvoresFields.get(i).getText().trim() + " " + chaoFields.get(i).getText().trim());
                         writer.newLine();
                    }
                    writer.write("bichadas " + bichadasField.getText().trim());
                    writer.newLine();
                    writer.write("mochila " + mochilaField.getText().trim());
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "Configurações exportadas com sucesso!");
                 } 
                   catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo: " + ex.getMessage());
                   }
               }
           }
        });

        JButton testarButton = new JButton("Testar Terreno");
        botoesPanel.add(testarButton);

        testarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
                    dispose();
                    JFrame window = new JFrame();
                    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                    window.setResizable(false);
                    window.setTitle("2D Adventure"); 
                    int linhas = Integer.parseInt(dimensaoFieldX.getText().trim());
                    if (linhas < 3) {
                        JOptionPane.showMessageDialog(null, "A dimensão mínima permitida é 3. Por favor, insira um valor maior ou igual a 3.");
                        return; 
                    }
                    int quantPedras = Integer.parseInt(pedrasField.getText().trim());
                    int[] quantArvores = new int[arvoresFields.size()];
                    int[] quantFrutas = new int[chaoFields.size()];
                    for (int i = 0; i < arvoresFields.size(); i++) {
                        if (!frutas[i].equals("maracujá")) {
                        	quantArvores[i] = Integer.parseInt(arvoresFields.get(i).getText().trim());
                        }
                    }
                    for (int i = 0; i < chaoFields.size(); i++) {
                        if (!frutas[i].equals("maracujá")) {
                        	quantFrutas[i] = Integer.parseInt(chaoFields.get(i).getText().trim());
                        }
                    }
                    
                    for (int i = 0; i < frutas.length; i++) {
                        if (frutas[i].equals("maracuja")) {
                            int totalMaracuja = Integer.parseInt(arvoresFields.get(i).getText().trim());
                            int inicialNoChao = Integer.parseInt(chaoFields.get(i).getText().trim());
                            if (inicialNoChao < 1) {
                                inicialNoChao = 1;
                            }
                            quantArvores[i] = totalMaracuja; 
                            quantFrutas[i] = inicialNoChao;   
                            break;  
                        }
                    }
                                       
                    Painel painel = new Painel(linhas, quantPedras, quantArvores, quantFrutas);
                    window.add(painel);
                    window.pack();
                    window.setLocationRelativeTo(null);
                    window.setVisible(true);
                    painel.startGameThread();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos corretamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + ex.getMessage());
                }
            }
        });
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CriadorDeTerreno().setVisible(true);
            }
        });
    }
}