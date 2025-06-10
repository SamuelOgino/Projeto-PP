package CadastroPet;

public class Pet {

    private static int proximoId = 1; // contador de IDs pros pets cadastrados

    private int id;
    private String nome;
    private String raca;
    private int idade;
    private String dono;
    private String vacinas;
    private String categoria;

    // Construtor para novos pets
    public Pet(String nome, String raca, int idade, String dono, String vacinas, String categoria) {
        this.id = proximoId++; //id gerado automaticamente
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.vacinas = vacinas;
        this.categoria = categoria;
    }

    // Construtor para carregar do arquivo (que já tem id)
    public Pet(int id, String nome, String raca, int idade, String dono, String vacinas, String categoria) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.vacinas = vacinas;
        this.categoria = categoria;

        // Atualiza o contador se necessário e garante q o id não repita
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }

    // Getters e setters para acessar e alterar os atributos dos pets
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

    // Transforma os dados do pet em uma linha de texto pro arquivo CSV
    public String toCSV() {
    	// Essa parte é um tratamento do campo vacinas, que estava ficando com aspas duplicadas
        String vacinasTratadas = "\"" + vacinas.replace("\"", "") + "\""; 
        return id + "," + nome + "," + raca + "," + idade + "," + dono + "," + vacinasTratadas + "," + categoria;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " (" + categoria + ")";
    }
}
