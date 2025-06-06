package CadastroPet;

public class Pet {

	private String nome;
    private String raca;
    private int idade;
    private String dono;
    private String especie; // Ex: Gato, Cachorro
    private String vacinas;   // Lista de vacinas separadas por vírgula

    // Construtor vazio
    public Pet() {
    }

    // Construtor parametrizado
    public Pet(String nome, String raca, int idade, String dono, String especie, String vacinas) {
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.especie = especie;
        this.vacinas = vacinas;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getVacinas() {
        return vacinas;
    }

    public void setVacinas(String vacinas) {
        this.vacinas = vacinas;
    }

    // Pra exportar em csv
    public String toCSV() {
        return nome + "," + raca + "," + idade + "," + dono + "," + especie + "," + vacinas;
    }

    // Pra exibir os dados formatados
    @Override
    public String toString() {
        return "Nome: " + nome + ", Raça: " + raca + ", Idade: " + idade +
               ", Dono: " + dono + ", Categoria: " + especie +
               ", Vacinas: " + vacinas;
    }
}
