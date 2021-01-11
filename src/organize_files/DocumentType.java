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
			"Averba��o", 
			"Concess�o", 
			"Di�rias",
			"F�rias", 
			"Gozo", 
			"Indeniza��o", 
			"Licen�a", 
			"Majora��o", 
			"Outros",
			"Portaria", 
			"Progress�o"
		};
		return type;
	}
	
	public static void subTypes(String typeDoc) {
		subTypeDisable = false;
		switch (typeDoc) {
		case "Abono":
			valueType = "de Perman�ncia";
			itens = new String[] {"de Faltas", "de Perman�ncia"};
			break;
		case "Afastamento":
			valueType = "do Cargo";
			itens = new String[] {"do Cargo", "para Concorrer ao Pleito Eleitoral", "para Mandato Sindical", "sem Justificativa"};
			break;
		case "Aposentadoria":
			valueType = "por Invalidez";
			itens = new String[] {"por Invalidez"};
			break;
		case "Averba��o":
			valueType = "de Tempo de Servi�o";
			itens = new String[] {"de Tempo de Servi�o"};
			break;
		case "Concess�o":
			valueType = "de Licen�a-Pr�mio";
			itens = new String[] {"de Finate", "de Licen�a-Pr�mio"};
			break;
		case "Di�rias":
			subTypeDisable = true;
			break;
		case "F�rias":
			subTypeDisable = true;
			break;
		case "Gozo":
			valueType = "de Licen�a-Pr�mio";
			itens = new String[] {"de Licen�a-Pr�mio"};
			break;
		case "Indeniza��o":
			valueType = "de Licen�a-Pr�mio";
			itens = new String[] {"de Licen�a-Pr�mio", "de F�rias e 13 Sal�rio", "Outras"};
			break;
		case "Licen�a":
			valueType = "M�dica";
			itens = new String[] {"Ado��o", "para Acompanhamento do Conjuge", "M�dica", "para Acompanhar pessoa da fam�la", "para Exerc�cio de Mandato Eletivo", "para Trato de Interesse Particular", "Paternidade"};
			break;
		case "Majora��o":
			valueType = "de Licen�a-Pr�mio";
			itens = new String[] {"de Licen�a-Pr�mio"};
			break;
		case "Outros":
			subTypeDisable = true;
			break;
		case "Portaria":
			valueType = "Concess�o de Licen�a-Pr�mio";
			itens = new String[] {"Concess�o de Licen�a-Pr�mio", "Concess�o de Licen�a M�dica", "Cumprimento", "Designa��o", "Lotar", "Remo��o", "Outras", "Dispensa"};
			break;
		case "Progress�o":
			valueType = "por Titula��o";
			itens = new String[] {"por Titula��o"};
			break;
		default:
			break;
		}
	}
	
}
