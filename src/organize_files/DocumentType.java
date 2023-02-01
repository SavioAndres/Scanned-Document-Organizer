package organize_files;

public class DocumentType {

	public static String valueType;
	public static String[] itens;
	public static boolean subTypeDisable;

	public static String[] types() {
		String[] type = new String[] { "Outros", "Ferias", "Oficio", "Concessao", "Licenca", "Portaria", "Gozo de Licenca-Premio",
				"Majoracao de Licenca-Premio", "Averbacao de Tempo de Servico", "Atestado Medico", "Progressao por Titulacao", "Abono",
				"Incorporacao de Funcao", "Checklist de Documentos", "Comissao de Julgamento de 1ª Instancia", "Afastamento", "Indenizacao",
				"Aposentadoria", "Decreto", "Periculosidade", "Certidao de Tempo de Servico", "Contrato de Trabalho", "Declaracao", "Ficha Cadastral",
				"Diarias", "Remocao", "Enquadramento", "Frequencia" };
		return type;
	}

	public static void subTypes(String typeDoc) {
		subTypeDisable = false;
		switch (typeDoc) {
		case "Concessao":
			valueType = "de Licenca-Premio";
			itens = new String[] { "de Finate", "de Licenca-Premio" };
			break;
		case "Licenca":
			valueType = "Medica";
			itens = new String[] { "Medica", "Outras" };
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
