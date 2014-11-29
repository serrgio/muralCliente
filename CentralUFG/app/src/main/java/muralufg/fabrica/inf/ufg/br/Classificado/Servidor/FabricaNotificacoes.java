package br.ufg.inf.mobile2014.projetoufg.Servidor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import br.ufg.inf.mobile2014.projetoufg.Notificacao;

/**
 * Classe criada para fabricar exemplos de notificação. Como não temos servidor,
 * essa classe simula a criação das notificações, escolhendo os dados
 * aleatóriamente, a partir de algumas opções predefinidas.
 * 
 * @author eric
 */
public class FabricaNotificacoes {

	/**
	 * Construtor da classe.
	 * 
	 * @param servidor
	 *            instância da classe que implementa LoginServidor
	 * @param quantidade
	 *            quantidade de notificações a serem criadas
	 */
	public FabricaNotificacoes(LoginServidorImpl servidor, int quantidade) {
		for (int i = 0; i < quantidade; i++) {
			String titulo = tituloAleatorio();
			String remetente = remetenteAleatorio();
			String conteudo = conteudoAleatorio()+"\n";
			String tipo;
			if (remetente.contains("Disciplina")) {
				tipo = "Privado";
			} else {
				tipo = tipoAleatorio();
			}
			String dataHora = getDateTime();

			Notificacao notificacao = new Notificacao(titulo, remetente,
					conteudo, tipo, dataHora);
			servidor.notificacaoFromServidor(notificacao);
		}
	}

	/**
	 * Método que sorteia o título.
	 * 
	 * @return String título
	 */
	public String tituloAleatorio() {
		String texto;
		Random gerador = new Random();
		int numero = gerador.nextInt(10);

		switch (numero) {
		case 0:
			texto = "Cupcake";
			break;
		case 1:
			texto = "Donut";
			break;
		case 2:
			texto = "Eclair";
			break;
		case 3:
			texto = "FroYo";
			break;
		case 4:
			texto = "Gingerbread";
			break;
		case 5:
			texto = "Honeycomb";
			break;
		case 6:
			texto = "Ice Cream Sandwich";
			break;
		case 7:
			texto = "Jelly Bean";
			break;
		case 8:
			texto = "KitKat";
			break;
		default:
			texto = "A e B?";
			break;
		}

		return texto;
	}

	/**
	 * Método que sorteia o remetente.
	 * 
	 * @return String remetente
	 */
	public String remetenteAleatorio() {
		String texto;
		Random gerador = new Random();
		int numero = gerador.nextInt(11);

		switch (numero) {
		case 0:
			texto = "Coordenação do Curso";
			break;
		case 1:
			texto = "Diretor de Unidade";
			break;
		case 2:
			texto = "Biblioteca";
			break;
		case 3:
			texto = "Pró-Reitoria";
			break;
		case 4:
			texto = "Reitoria";
			break;
		case 5:
			texto = "Docentes";
			break;
		case 6:
			texto = "Disciplina 1";
			break;
		case 7:
			texto = "Disciplina 2";
			break;
		case 8:
			texto = "Disciplina 3";
			break;
		case 9:
			texto = "Disciplina 4";
			break;
		default:
			texto = "Disciplina 5";
			break;
		}

		return texto;
	}

	/**
	 * Método que sorteia o conteúdo.
	 * 
	 * @return String conteúdo
	 */
	public String conteudoAleatorio() {
		String texto;
		Random gerador = new Random();
		int numero = gerador.nextInt(5);

		switch (numero) {
		case 0:
			texto = "Ouviram do Ipiranga as margens plácidas "
					+ "\nDe um povo heróico o brado retumbante, "
					+ "\nE o sol da liberdade, em raios fúlgidos, "
					+ "\nBrilhou no céu da pátria nesse instante."
					+ "\n\nhttp://pt.wikipedia.org/wiki/Brasil";
			break;
		case 1:
			texto = "O Canada! Our home and native land! "
					+ "\nTrue patriot love in all thy sons command. "
					+ "\nWith glowing hearts we see thee rise, "
					+ "\nThe True North strong and free!"
					+ "\n\nhttp://pt.wikipedia.org/wiki/Canad%C3%A1";
			break;
		case 2:
			texto = "Einigkeit und Recht und Freiheit "
					+ "\nFür das deutsche Vaterland! "
					+ "\nDanach lasst uns alle streben "
					+ "\nBrüderlich mit Herz und Hand!"
					+ "\n\nhttp://pt.wikipedia.org/wiki/Alemanha";
			break;
		case 3:
			texto = "Dievs, svētī Latviju, " + "\nMūs' dārgo tēviju, "
					+ "\nSvētī jel Latviju, " + "\nAk svētī jel to! "
					+ "\n\nhttp://pt.wikipedia.org/wiki/Let%C3%B3nia";
			break;
		default:
			texto = "Kur latvju meitas zied, " + "\nKur latvju dēli dzied, "
					+ "\nLaid mums tur laimē diet, " + "\nMūs Latvijā."
					+ "\n\nhttp://pt.wikipedia.org/wiki/Let%C3%B3nia";
			break;
		}

		return texto;
	}

	/**
	 * Método que sorteia o tipo (público ou privado)
	 * 
	 * @return String tipo
	 */
	public String tipoAleatorio() {
		String texto;
		Random gerador = new Random();
		boolean bool = gerador.nextBoolean();

		if (bool == true) {
			texto = "Público";
		} else {
			texto = "Privado";
		}

		return texto;
	}

	/**
	 * Método para pegar a data e a hora da criação da mensagem.
	 * 
	 * @return String dataHora
	 */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy \nHH:mm",
				Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
