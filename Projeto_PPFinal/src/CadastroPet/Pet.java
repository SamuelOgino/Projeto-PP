package CadastroPet;

public class Pet {

    private static int proximoId = 1; // contador de IDs

    private int id;
    private String nome;
    private String raca;
    private int idade;
    private String dono;
    private String vacinas;
    private String categoria;

    // Construtor para novos pets
    public Pet(String nome, String raca, int idade, String dono, String vacinas, String categoria) {
        this.id = proximoId++;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.vacinas = vacinas;
        this.categoria = categoria;
    }

    // Construtor para carregar do arquivo (com ID fixo)
    public Pet(int id, String nome, String raca, int idade, String dono, String vacinas, String categoria) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.vacinas = vacinas;
        this.categoria = categoria;

        // Atualiza o contador se necessário
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public String getDono() {
        return dono;
    }

    public String getVacinas() {
        return vacinas;
    }

    public String getCategoria() {
        return categoria;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public void setVacinas(String vacinas) {
        this.vacinas = vacinas;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String toCSV() {
        return id + "," + nome + "," + raca + "," + idade + "," + dono + ",\"" + vacinas + "\"," + categoria;
    }


    @Override
    public String toString() {
        return id + " - " + nome + " (" + categoria + ")";
    }
}
