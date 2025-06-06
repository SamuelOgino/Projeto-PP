package CadastroPet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PetController {

    private PetView view;
    private ArrayList<Pet> listaDePets;
    private final String caminhoArquivo = "pets.csv";

    public PetController(PetView view) {
        this.view = view;
        this.listaDePets = new ArrayList<>();

        // Cria arquivo com cabeçalho se não existir
        inicializarArquivo();

        // Ações dos botões
        this.view.getSalvarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarPet();
            }
        });

        this.view.getEditarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarPet(); // por enquanto só exibe mensagem
            }
        });

        this.view.getVerPetsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarTabelaPets();
            }
        });

        this.view.getRemoverButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerPet();
            }
        });

        this.view.getAbrirCSVButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCSV();
            }
        });

    }

    private void carregarPetsDoArquivo() {
        listaDePets.clear();
        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(caminhoArquivo))) {
            scanner.nextLine(); // pula o cabeçalho
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                String[] partes = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                for (int i = 0; i < partes.length; i++) {
                    partes[i] = partes[i].replace("\"", "").trim();
                }
                // Verifica se tem 6 partes antes de criar o Pet
                if (partes.length == 6) {
                    String nome = partes[0];
                    String raca = partes[1];
                    int idade = Integer.parseInt(partes[2]);
                    String dono = partes[3];
                    String vacinas = partes[4];
                    String categoria = partes[5];

                    Pet pet = new Pet(nome, raca, idade, dono, vacinas, categoria);
                    listaDePets.add(pet);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar pets: " + e.getMessage());
        }
    }

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

    private void salvarPet() {
        try {
            String nome = view.getNome().trim();
            String raca = view.getRaca().trim();
            int idade = Integer.parseInt(view.getIdade().trim());
            String dono = view.getDono().trim();
            String vacinas = view.getVacinas().trim();
            String categoria = view.getCategoria();

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

            salvarNoArquivo(novoPet);
            JOptionPane.showMessageDialog(null, "🐶 Pet cadastrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "⚠️ Idade deve ser um número inteiro.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar pet: " + ex.getMessage());
        }
    }

    private void salvarNoArquivo(Pet pet) {
        try (FileWriter writer = new FileWriter(caminhoArquivo, true)) {
            writer.write(pet.toCSV() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "❌ Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    private void inicializarArquivo() {
        try {
            java.io.File arquivo = new java.io.File(caminhoArquivo);
            if (!arquivo.exists()) {
                FileWriter writer = new FileWriter(caminhoArquivo);
                writer.write("Nome,Raça,Idade,Dono,Vacinas,Categoria\n");
                writer.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar arquivo CSV: " + e.getMessage());
        }
    }

    private void editarPet() {
        carregarPetsDoArquivo(); // garante lista atualizada

        String nomeDigitado = view.getNome().trim();

        Pet petExistente = null;
        for (Pet p : listaDePets) {
            System.out.println("Comparando: '" + p.getNome().trim() + "' com '" + nomeDigitado.trim() + "'");
            if (p.getNome().trim().equalsIgnoreCase(nomeDigitado.trim())) {
                petExistente = p;
                break;
            }
        }

        if (petExistente == null) {
            JOptionPane.showMessageDialog(null, "Pet com nome '" + nomeDigitado + "' não encontrado.");
            return;
        }

        try {
            // Coleta os dados preenchidos na View
            String raca = view.getRaca().trim();
            int idade = Integer.parseInt(view.getIdade().trim());
            String dono = view.getDono().trim();
            String vacinas = view.getVacinas().trim();
            String categoria = view.getCategoria().trim();

            // ✅ Validações
            if (raca.isEmpty() || dono.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios: Raça e Dono.");
                return;
            }

            if (idade < 0 || idade > 50) {
                JOptionPane.showMessageDialog(null, "Idade deve estar entre 0 e 50.");
                return;
            }

            if ("Outro".equals(view.getCategoria()) && categoria.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite a categoria personalizada.");
                return;
            }

            // ✅ Atualiza os dados do objeto existente
            petExistente.setRaca(raca);
            petExistente.setIdade(idade);
            petExistente.setDono(dono);
            petExistente.setVacinas(vacinas);
            petExistente.setCategoria(categoria);

            // ✅ Reescreve o arquivo CSV com a lista modificada
            reescreverArquivo();

            JOptionPane.showMessageDialog(null, "🐾 Pet atualizado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "⚠️ Idade deve ser um número inteiro.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar pet: " + ex.getMessage());
        }
    }


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

    private void removerPet() {
        carregarPetsDoArquivo();
        String nome = view.getNome().trim();

        Pet encontrado = null;
        for (Pet p : listaDePets) {
            if (p.getNome().trim().equalsIgnoreCase(nome)) {
                encontrado = p;
                break;
            }
        }

        if (encontrado == null) {
            JOptionPane.showMessageDialog(null, "Pet não encontrado.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Deseja remover o pet '" + encontrado.getNome() + "'?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            listaDePets.remove(encontrado);
            reescreverArquivo();
            JOptionPane.showMessageDialog(null, "Pet removido com sucesso.");
        }
    }

    private void abrirCSV() {
        try {
            java.awt.Desktop.getDesktop().open(new java.io.File(caminhoArquivo));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + e.getMessage());
        }
    }

}
