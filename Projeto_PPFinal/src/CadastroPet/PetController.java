package CadastroPet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class PetController {

    private PetView view;
    private ArrayList<Pet> listaDePets; //Lista pros pets cadastrados
    private final String caminhoArquivo = "pets.csv";

    public PetController(PetView view) {
        this.view = view;
        this.listaDePets = new ArrayList<>();

        // Cria arquivo CSV se não existir
        inicializarArquivo();

        // Ações dos botões
        this.view.getSalvarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarPet();
            }
        });

        this.view.getEditarButton().addActionListener(e -> mostrarTabelaParaSelecao("Editar"));

        this.view.getVerPetsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTabelaPets();
            }
        });

        this.view.getRemoverButton().addActionListener(e -> mostrarTabelaParaSelecao("Remover"));

        this.view.getAbrirCSVButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCSV();
            }
        });

    }

    // Lê os dados do CSV e preenche a lista de pets
    private void carregarPetsDoArquivo() {
        listaDePets.clear();
        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(caminhoArquivo))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // pula o cabeçalho
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                
                String[] partes = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (partes.length == 7) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    String raca = partes[2];
                    int idade = Integer.parseInt(partes[3]);
                    String dono = partes[4];
                    String vacinas = partes[5];
                    String categoria = partes[6];

                    Pet pet = new Pet(id, nome, raca, idade, dono, vacinas, categoria);
                    listaDePets.add(pet);
                } else {
                    System.err.println("⚠️ Linha inválida no CSV: " + linha);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar pets: " + e.getMessage());
        }
    }

    // Reescreve o arquivo CSV com os pets da lista
    private void reescreverArquivo() {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.write("Nome,Raça,Idade,Dono,Vacinas,Categoria\n");
            for (Pet p : listaDePets) {
                writer.write(p.toCSV() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao reescrever arquivo: " + e.getMessage());
        }
    }

    //Verifica e salva um novo pet
    private void salvarPet() {
        try {
            String nome = view.getNome().trim();
            String raca = view.getRaca().trim();
            int idade = Integer.parseInt(view.getIdade().trim());
            String dono = view.getDono().trim();
            String vacinas = view.getVacinas().trim();
            String categoria = view.getCategoria();

            // Validacões dos campos obrigatórios
            if (nome.isEmpty() || raca.isEmpty() || dono.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios: Nome, Raça e Dono.");
                return;
            }

            if (idade < 0 || idade > 50) {
                JOptionPane.showMessageDialog(null, "Idade deve estar entre 0 e 50.");
                return;
            }

            if ("Outro".equals(view.getCategoria()) && view.getCategoria().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite a categoria personalizada.");
                return;
            }

            // CRIA O PET
            Pet novoPet = new Pet(nome, raca, idade, dono, vacinas, categoria);
            listaDePets.add(novoPet);

            salvarNoArquivo(novoPet); // Adiciona o novo pet ao arquivo CSV
            JOptionPane.showMessageDialog(null, "🐶 Pet cadastrado com sucesso!");
            
            view.limparCampos(); //Limpa os campos da tela

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "⚠️ Idade deve ser um número inteiro.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar pet: " + ex.getMessage());
        }
    }

    //Método que adiciona um pet novo no CSV
    private void salvarNoArquivo(Pet pet) {
        try (FileWriter writer = new FileWriter(caminhoArquivo, true)) {
            writer.write(pet.toCSV() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    //método para criar o arquivo CSV se não existir
    private void inicializarArquivo() {
        try {
            java.io.File arquivo = new java.io.File(caminhoArquivo);
            if (!arquivo.exists()) {
                FileWriter writer = new FileWriter(caminhoArquivo);
                writer.write("ID,Nome,Raça,Idade,Dono,Vacinas,Categoria\n");
                writer.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar arquivo CSV: " + e.getMessage());
        }
    }

    //Mostra todos os pets salvos em uma tabela na tela
    private void mostrarTabelaPets() {
        carregarPetsDoArquivo();

        String[] colunas = {"Nome", "Raça", "Idade", "Dono", "Vacinas", "Categoria"};
        String[][] dados = new String[listaDePets.size()][6];

        for (int i = 0; i < listaDePets.size(); i++) {
            Pet p = listaDePets.get(i);
            dados[i][0] = p.getNome();
            dados[i][1] = p.getRaca();
            dados[i][2] = String.valueOf(p.getIdade());
            dados[i][3] = p.getDono();
            dados[i][4] = p.getVacinas();
            dados[i][5] = p.getCategoria();
        }

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);

        JFrame frameTabela = new JFrame("🐾 Lista de Pets Cadastrados");
        frameTabela.setSize(700, 300);
        frameTabela.setLocationRelativeTo(null);
        frameTabela.add(scrollPane);
        frameTabela.setVisible(true);
    }

    //Mostra uma tabela para o usuario escolher um pet para editar ou remover
    private void mostrarTabelaParaSelecao(String acao) {
        carregarPetsDoArquivo();

        String[] colunas = {"ID", "Nome", "Raça", "Idade", "Dono", "Vacinas", "Categoria"};
        String[][] dados = new String[listaDePets.size()][7];

        for (int i = 0; i < listaDePets.size(); i++) {
            Pet p = listaDePets.get(i);
            dados[i][0] = String.valueOf(p.getId());
            dados[i][1] = p.getNome();
            dados[i][2] = p.getRaca();
            dados[i][3] = String.valueOf(p.getIdade());
            dados[i][4] = p.getDono();
            dados[i][5] = p.getVacinas();
            dados[i][6] = p.getCategoria();
        }

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JFrame frame = new JFrame("Selecionar Pet para " + acao);
        frame.setSize(700, 300);
        frame.setLocationRelativeTo(null);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton confirmar = new JButton(acao);
        confirmar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row != -1) {
                int idSelecionado = Integer.parseInt((String) tabela.getValueAt(row, 0));
                Pet selecionado = listaDePets.stream()
                    .filter(p -> p.getId() == idSelecionado)
                    .findFirst()
                    .orElse(null);

                if (selecionado != null) {
                    if (acao.equals("Remover")) {
                        int confirm = JOptionPane.showConfirmDialog(frame,
                            "Remover o pet " + selecionado.getNome() + "?",
                            "Confirmação", JOptionPane.YES_NO_OPTION);

                        if (confirm == JOptionPane.YES_OPTION) {
                            listaDePets.remove(selecionado);
                            reescreverArquivo();
                            carregarPetsDoArquivo();
                            JOptionPane.showMessageDialog(frame, "Pet removido com sucesso.");
                            frame.dispose();
                        }
                    } else if (acao.equals("Editar")) {
                    	view.exibirJanelaEdicao(selecionado, pet -> atualizarPet(pet));
                    	frame.dispose();

                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(confirmar);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    //Atualiza as infos de um pet editado
    public void atualizarPet(Pet petAtualizado) {
        reescreverArquivo(); // Sobrescreve o CSV com os dados atualizados da lista
        carregarPetsDoArquivo();
    }

    // Abre o arquivo CSV usando o programa padrão do PC (excel no meu caso)
    private void abrirCSV() {
        try {
            java.awt.Desktop.getDesktop().open(new java.io.File(caminhoArquivo));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + e.getMessage());
        }
    }

}
