package organize_files;

public class DocumentType {
	
	public static String valueType;
	public static String[] itens;
	public static boolean subTypeDisable;
	
	public static String[] types() {
		String[] type = new String[] {
			"Outros",
			"Férias",
			"Concessão",
			"Licença",
			"Portaria",
			"Gozo de Licença-Prêmio",
			"Majoração de Licença-Prêmio",
			"Averbação de Tempo de Serviço",
			"Progressão por Titulação",
			"Abono",
			"Incorporação de Função",
			"Documentos Pessoais",
			"Afastamento", 
			"Indenização",
			"Aposentadoria por Invalidez",
			"Diárias"
		};
		return type;
	}
	
	public static void subTypes(String typeDoc) {
		subTypeDisable = false;
		switch (typeDoc) {
		case "Abono":
			valueType = "de Permanência";
			itens = new String[] {"de Faltas", "de Permanência"};
			break;
		case "Afastamento":
			valueType = "do Cargo";
			itens = new String[] {"do Cargo", "para Concorrer ao Pleito Eleitoral", "para Mandato Sindical", "sem Justificativa"};
			break;
		case "Concessão":
			valueType = "de Licença-Prêmio";
			itens = new String[] {"de Finate", "de Licença-Prêmio"};
			break;
		case "Indenização":
			valueType = "de Licença-Prêmio";
			itens = new String[] {"de Licença-Prêmio", "de Férias e 13 Salário", "Outras"};
			break;
		case "Licença":
			valueType = "Médica";
			itens = new String[] {"Adoção", "para Acompanhamento do Conjuge", "Médica", "para Acompanhar pessoa da famíla", "para Exercício de Mandato Eletivo", "para Trato de Interesse Particular", "Paternidade"};
			break;
		case "Portaria":
			valueType = "Concessão de Licença-Prêmio";
			itens = new String[] {"Concessão de Licença-Prêmio", "Concessão de Licença Médica", "Cumprimento", "Designação", "Dispensa", "Lotar", "Remoção", "Outras"};
			break;
		default:
			subTypeDisable = true;
			break;
		}
	}
	
}
