package muralufg.fabrica.inf.ufg.br.centralufg.ouvidoria.services;

import org.json.JSONException;
import org.json.JSONObject;

import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.util.ServiceCompliant;
import muralufg.fabrica.inf.ufg.br.centralufg.util.SimpleConnection;

/**
 * Classe resposável por comunicar com o serviço de Ouvidoria
 */
public class OuvidoriaService extends SimpleConnection {

    private static final String URL = "http://private-385b-centralufgouvidoria.apiary-mock.com/ouvidoria";

    public OuvidoriaService(ServiceCompliant handler) {
        super(handler, URL);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        switch (getHttpStatus()) {
            case OK:
                notificarRespostaOk(getResponse());
                break;
            case ERROR:
                handler.handleError("Ocorreu um erro");
                break;

            default:
                handler.handleError(handler.getContextActivity().getResources().getString(
                        R.string.alerta_server_error));
                break;
        }
    }

    /**
     * Notificar para quem solicitou o servico a resposta do servidor
     *
     * @param response recebida pela requisição
     */
    private void notificarRespostaOk(String response) {
        try {
            // Capturar os dados do json de resposta
            JSONObject object = new JSONObject(getResponse());
            String status = object.getString("status");
            String mensagem = object.getString("mensagem");

            // Enviar o resultado da resposta
            Resposta resposta = new Resposta(status, mensagem);
            handler.readObject(resposta);
        } catch (JSONException e) {
            handler.handleError("Ocorreu um erro com " + getResponse() + ": " + e.getLocalizedMessage());
        }
    }

    /**
     * Classe utilizada para mostrar as repostas da ouvidoria
     */
    public class Resposta {

        private String status;
        private String mensagem;

        public Resposta(String status, String mensagem) {
            this.status = status;
            this.mensagem = mensagem;
        }

        /**
         * Status da requisição
         *
         * @return "Ok" ou "Erro"
         */
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Mensagem de reposta do servidor
         *
         * @return
         */
        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}
