package CadastroPet;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PetView extends JFrame {

    private JTextField nomeField;
    private JTextField racaField;
    private JTextField idadeField;
    private JTextField donoField;
    private JCheckBox vacinaRaiva;
    private JCheckBox vacinaPolivalente;
    private JCheckBox vacinaVermifugo;
    private JCheckBox vacinaOutra;

    private JComboBox<String> categoriaCombo;
    private JTextField categoriaOutraField;


    private JButton salvarButton;
    private JButton editarButton;
    private JButton verPetsButton;
    private JButton removerButton;
    private JButton abrirCSVButton;

    public PetView() {
        aplicarTemaNimbus();

        setTitle("🐾 Cadastro de Pets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600); 
        setLocationRelativeTo(null);
        setResizable(false);

        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 15);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        painelPrincipal.setBackground(new Color(248, 248, 255));

        nomeField = criarCampoTexto();
        racaField = criarCampoTexto();
        idadeField = criarCampoTexto();
        donoField = criarCampoTexto();
        
        // Vacinas
        vacinaRaiva = new JCheckBox("Raiva");
        vacinaPolivalente = new JCheckBox("Polivalente");
        vacinaVermifugo = new JCheckBox("Vermífugo");
        vacinaOutra = new JCheckBox("Outra");

        // Categoria
        categoriaCombo = new JComboBox<>(new String[]{"Cachorro", "Gato", "Outro"});
        categoriaCombo.setFont(fontePadrao);
        categoriaCombo.setPreferredSize(new Dimension(250, 30));

        categoriaOutraField = new JTextField();
        categoriaOutraField.setFont(fontePadrao);
        categoriaOutraField.setPreferredSize(new Dimension(250, 30));
        categoriaOutraField.setVisible(false); // só aparece se escolher "Outro"

        categoriaCombo.addActionListener(e -> {
            String selecionado = (String) categoriaCombo.getSelectedItem();
            categoriaOutraField.setVisible("Outro".equals(selecionado));
            SwingUtilities.getWindowAncestor(categoriaCombo).pack(); // redimensiona
        });

        categoriaCombo.setFont(fontePadrao);
        categoriaCombo.setPreferredSize(new Dimension(250, 30));

        salvarButton = criarBotao("Salvar", new Color(46, 139, 87));
        editarButton = criarBotao("Editar", new Color(100, 149, 237));
        verPetsButton = criarBotao("Ver Pets", new Color(70, 130, 180));
        removerButton = criarBotao("Remover", new Color(178, 34, 34));
        abrirCSVButton = criarBotao("Abrir CSV", new Color(60, 179, 113));

        painelPrincipal.add(criarLinha("Nome do pet:", nomeField, fontePadrao));
        painelPrincipal.add(criarLinha("Raça:", racaField, fontePadrao));
        painelPrincipal.add(criarLinha("Idade:", idadeField, fontePadrao));
        painelPrincipal.add(criarLinha("Dono:", donoField, fontePadrao));
        painelPrincipal.add(criarCheckGroup("Vacinas:", new JCheckBox[]{vacinaRaiva, vacinaPolivalente, vacinaVermifugo, vacinaOutra}, fontePadrao));
        painelPrincipal.add(criarLinha("Categoria:", categoriaCombo, fontePadrao));
        painelPrincipal.add(categoriaOutraField);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botoesPanel.setBackground(painelPrincipal.getBackground());
        botoesPanel.add(salvarButton);
        botoesPanel.add(editarButton);
        botoesPanel.add(verPetsButton);
        botoesPanel.add(removerButton);
        botoesPanel.add(abrirCSVButton);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        painelPrincipal.add(botoesPanel);

        add(painelPrincipal);
        setVisible(true);
    }

    private void aplicarTemaNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus não disponível, usando padrão.");
        }
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(250, 30));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return campo;
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setPreferredSize(new Dimension(100, 35));
        return botao;
    }

    private JPanel criarLinha(String texto, JComponent campo, Font fonte) {
        JLabel label = new JLabel(texto);
        label.setFont(fonte);
        label.setPreferredSize(new Dimension(120, 25)); 

        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        linha.setBackground(new Color(248, 248, 255));
        linha.add(label);
        linha.add(campo);

        return linha;
    }

    private JPanel criarCheckGroup(String titulo, JCheckBox[] checkboxes, Font fonte) {
        JLabel label = new JLabel(titulo);
        label.setFont(fonte);
        label.setPreferredSize(new Dimension(120, 25));

        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        linha.setBackground(new Color(248, 248, 255));
        linha.add(label);

        JPanel grupo = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        grupo.setBackground(linha.getBackground());
        for (JCheckBox cb : checkboxes) {
            cb.setFont(fonte);
            grupo.add(cb);
        }

        linha.add(grupo);
        return linha;
    }


    // Getters para o Controller
    public String getNome() { return nomeField.getText(); }
    public String getRaca() { return racaField.getText(); }
    public String getIdade() { return idadeField.getText(); }
    public String getDono() { return donoField.getText(); }
    public String getVacinas() {
    StringBuilder sb = new StringBuilder();
    if (vacinaRaiva.isSelected()) sb.append("Raiva,");
    if (vacinaPolivalente.isSelected()) sb.append("Polivalente,");
    if (vacinaVermifugo.isSelected()) sb.append("Vermífugo,");
    if (vacinaOutra.isSelected()) sb.append("Outra,");
    if (sb.length() > 0) sb.setLength(sb.length() - 1); // remove vírgula final
    return sb.toString();
}

public String getCategoria() {
    if ("Outro".equals(categoriaCombo.getSelectedItem())) {
        return categoriaOutraField.getText();
    }
    return (String) categoriaCombo.getSelectedItem();
}

public void preencherCampos(Pet pet) {
    nomeField.setText(pet.getNome());
    racaField.setText(pet.getRaca());
    idadeField.setText(String.valueOf(pet.getIdade()));
    donoField.setText(pet.getDono());
    // Aqui você pode adicionar lógica para marcar os checkboxes com base no texto de vacinas
    categoriaCombo.setSelectedItem(pet.getCategoria());
}

public void limparCampos() {
    nomeField.setText("");
    racaField.setText("");
    idadeField.setText("");
    donoField.setText("");
    categoriaOutraField.setText("");
    categoriaCombo.setSelectedIndex(0); // volta para o primeiro item da lista

    vacinaRaiva.setSelected(false);
    vacinaPolivalente.setSelected(false);
    vacinaVermifugo.setSelected(false);
    vacinaOutra.setSelected(false);
}

    public JButton getSalvarButton() { return salvarButton; }
    public JButton getEditarButton() { return editarButton; }
    public JButton getVerPetsButton() { return verPetsButton; }
    public JButton getRemoverButton() { return removerButton; }
    public JButton getAbrirCSVButton() { return abrirCSVButton; }

    public void exibirJanelaEdicao(Pet pet, Consumer<Pet> onSalvar) {
        JFrame frame = new JFrame("Editar Pet: " + pet.getNome());
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(8, 2, 5, 5));
        frame.setLocationRelativeTo(null);

        JTextField idField = new JTextField(String.valueOf(pet.getId()));
        idField.setEditable(false);

        JTextField nomeField = new JTextField(pet.getNome());
        JTextField racaField = new JTextField(pet.getRaca());
        JTextField idadeField = new JTextField(String.valueOf(pet.getIdade()));
        JTextField donoField = new JTextField(pet.getDono());
        JTextField vacinasField = new JTextField(pet.getVacinas());
        JTextField categoriaField = new JTextField(pet.getCategoria());

        frame.add(new JLabel("ID:")); frame.add(idField);
        frame.add(new JLabel("Nome:")); frame.add(nomeField);
        frame.add(new JLabel("Raça:")); frame.add(racaField);
        frame.add(new JLabel("Idade:")); frame.add(idadeField);
        frame.add(new JLabel("Dono:")); frame.add(donoField);
        frame.add(new JLabel("Vacinas:")); frame.add(vacinasField);
        frame.add(new JLabel("Categoria:")); frame.add(categoriaField);

        JButton salvar = new JButton("Salvar Alterações");
        salvar.addActionListener(e -> {
            try {
                pet.setNome(nomeField.getText().trim());
                pet.setRaca(racaField.getText().trim());
                pet.setIdade(Integer.parseInt(idadeField.getText().trim()));
                pet.setDono(donoField.getText().trim());
                pet.setVacinas(vacinasField.getText().trim());
                pet.setCategoria(categoriaField.getText().trim());

                onSalvar.accept(pet);
                JOptionPane.showMessageDialog(frame, "Pet atualizado com sucesso!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage());
            }
        });

        frame.add(new JLabel()); frame.add(salvar);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        PetView view = new PetView();
        PetController controller = new PetController(view);
        });
    }
}
