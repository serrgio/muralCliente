package muralufg.fabrica.inf.ufg.br.centralufg.ouvidoria.models;

/**
 * Classe que representa os dados que devem ser enviados para a ouvidoria da CentralUFG
 */
public class Ouvidoria {

    String titulo;
    String descricao;

    public Ouvidoria(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("titulo: ").append(getTitulo()).append("\n");
        builder.append("descricao: ").append(getDescricao());
        return builder.toString();
    }
}
