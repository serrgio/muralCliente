package muralufg.fabrica.inf.ufg.br.centralufg.ouvidoria.models;

/**
 * Classe que representa o arquivo de mídia para ser enviado à ouvidoria
 */
public class OuvidoriaItemAnexo {

    public enum Media {
        IMAGEM, AUDIO, VIDEO;
    }

    private String nome;
    private String uri;
    private Media media;

    public OuvidoriaItemAnexo(String nome, String uri, Media media) {
        this.setNome(nome);
        this.setUri(uri);
        this.setMedia(media);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
