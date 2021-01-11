package organize_files;

public class DocumentType {
	
	public static String valueType;
	public static String[] itens;
	public static boolean subTypeDisable;
	
	public static String[] types() {
		String[] type = new String[] {
			"Abono", 
			"Afastamento", 
			"Aposentadoria", 
			"Averbação", 
			"Concessão", 
			"Diárias",
			"Férias", 
			"Gozo", 
			"Indenização", 
			"Licença", 
			"Majoração", 
			"Outros",
			"Portaria", 
			"Progressão"
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
		case "Aposentadoria":
			valueType = "por Invalidez";
			itens = new String[] {"por Invalidez"};
			break;
		case "Averbação":
			valueType = "de Tempo de Serviço";
			itens = new String[] {"de Tempo de Serviço"};
			break;
		case "Concessão":
			valueType = "de Licença-Prêmio";
			itens = new String[] {"de Finate", "de Licença-Prêmio"};
			break;
		case "Diárias":
			subTypeDisable = true;
			break;
		case "Férias":
			subTypeDisable = true;
			break;
		case "Gozo":
			valueType = "de Licença-Prêmio";
			itens = new String[] {"de Licença-Prêmio"};
			break;
		case "Indenização":
			valueType = "de Licença-Prêmio";
			itens = new String[] {"de Licença-Prêmio", "de Férias e 13 Salário", "Outras"};
			break;
		case "Licença":
			valueType = "Médica";
			itens = new String[] {"Adoção", "para Acompanhamento do Conjuge", "Médica", "para Acompanhar pessoa da famíla", "para Exercício de Mandato Eletivo", "para Trato de Interesse Particular", "Paternidade"};
			break;
		case "Majoração":
			valueType = "de Licença-Prêmio";
			itens = new String[] {"de Licença-Prêmio"};
			break;
		case "Outros":
			subTypeDisable = true;
			break;
		case "Portaria":
			valueType = "Concessão de Licença-Prêmio";
			itens = new String[] {"Concessão de Licença-Prêmio", "Concessão de Licença Médica", "Cumprimento", "Designação", "Lotar", "Remoção", "Outras", "Dispensa"};
			break;
		case "Progressão":
			valueType = "por Titulação";
			itens = new String[] {"por Titulação"};
			break;
		default:
			break;
		}
	}
	
}
