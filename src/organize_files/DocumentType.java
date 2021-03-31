package organize_files;

public class DocumentType {

	public static String valueType;
	public static String[] itens;
	public static boolean subTypeDisable;

	public static String[] types() {
		String[] type = new String[] { "Outros", "Ferias", "Concessao", "Licenca", "Portaria", "Gozo de Licenca-Premio",
				"Majoracao de Licenca-Premio", "Averbacao de Tempo de Servico", "Progressao por Titulacao", "Abono",
				"Incorporacao de Funcao", "Documentos Pessoais", "Afastamento", "Indenizacao",
				"Aposentadoria", "Diarias" };
		return type;
	}

	public static void subTypes(String typeDoc) {
		subTypeDisable = false;
		switch (typeDoc) {
		case "Abono":
			valueType = "de Permanencia";
			itens = new String[] { "de Faltas", "de Permanencia" };
			break;
		case "Afastamento":
			valueType = "do Cargo";
			itens = new String[] { "do Cargo", "para Concorrer ao Pleito Eleitoral", "para Mandato Sindical",
					"sem Justificativa" };
			break;
		case "Aposentadoria":
			valueType = "";
			itens = new String[] { "", "por Invalidez" };
			break;
		case "Concessao":
			valueType = "de Licenca-Premio";
			itens = new String[] { "de Finate", "de Licenca-Premio" };
			break;
		case "Indenizacao":
			valueType = "de Licenca-Premio";
			itens = new String[] { "de Licenca-Premio", "de Ferias e 13 Salario", "Outras" };
			break;
		case "Licenca":
			valueType = "Medica";
			itens = new String[] { "Adocao", "para Acompanhamento do Conjuge", "Medica",
					"para Acompanhar pessoa da famila", "para Exercicio de Mandato Eletivo",
					"para Trato de Interesse Particular", "Paternidade" };
			break;
		case "Portaria":
			valueType = "Concessao de Licenca-Premio";
			itens = new String[] { "Concessao de Licenca-Premio", "Concessao de Licenca Medica", "Cumprimento",
					"Designacao", "Dispensa", "Lotar", "Remocao", "Outras" };
			break;
		default:
			subTypeDisable = true;
			break;
		}
	}

}
