public class smartphone {
    private String n_seriale;
    private String nome_modello
    private int anno;

    public smartphone (String n_seriale, String nome_modello, int anno) 
    {
        this.n_seriale = n_seriale;
        this.nome_modello = nome_modello;
        this.anno = anno;
    }

    public String getN_seriale() {return n_seriale;}
    public String getNome_modello() {return nome_modello;}
    public int getAnno() {return anno;}

    @override
    public String toString() 
    {
        return "smartphone{" + "n_seriale= " + n_seriale +
                                ", nome_modello= " + nome_modello + "\'"
                                ", anno= " + anno + "\'" + "}"
    }

    
}