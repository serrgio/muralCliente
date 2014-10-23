package muralufg.fabrica.inf.ufg.br.ouvidoria;

public class ItemAnexo {

    public enum Media{
        IMAGEM,AUDIO,VIDEO;
    }

    private String nome;
    private String uri;
    private Media media;

    public ItemAnexo(String nome, String uri, Media media){
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
