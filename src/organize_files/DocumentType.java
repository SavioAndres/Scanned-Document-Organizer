package organize_files;

public class DocumentType {
	
	public static String valueType;
	public static String[] itens;
	public static boolean subTypeDisable;
	
	public static String[] types() {
		String[] type = new String[] {
			"Outros",
			"F�rias",
			"Concess�o",
			"Licen�a",
			"Portaria",
			"Gozo de Licen�a-Pr�mio",
			"Majora��o de Licen�a-Pr�mio",
			"Averba��o de Tempo de Servi�o",
			"Progress�o por Titula��o",
			"Abono",
			"Incorpora��o de Fun��o",
			"Documentos Pessoais",
			"Afastamento", 
			"Indeniza��o",
			"Aposentadoria por Invalidez",
			"Di�rias"
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
		case "Concess�o":
			valueType = "de Licen�a-Pr�mio";
			itens = new String[] {"de Finate", "de Licen�a-Pr�mio"};
			break;
		case "Indeniza��o":
			valueType = "de Licen�a-Pr�mio";
			itens = new String[] {"de Licen�a-Pr�mio", "de F�rias e 13 Sal�rio", "Outras"};
			break;
		case "Licen�a":
			valueType = "M�dica";
			itens = new String[] {"Ado��o", "para Acompanhamento do Conjuge", "M�dica", "para Acompanhar pessoa da fam�la", "para Exerc�cio de Mandato Eletivo", "para Trato de Interesse Particular", "Paternidade"};
			break;
		case "Portaria":
			valueType = "Concess�o de Licen�a-Pr�mio";
			itens = new String[] {"Concess�o de Licen�a-Pr�mio", "Concess�o de Licen�a M�dica", "Cumprimento", "Designa��o", "Dispensa", "Lotar", "Remo��o", "Outras"};
			break;
		default:
			subTypeDisable = true;
			break;
		}
	}
	
}
