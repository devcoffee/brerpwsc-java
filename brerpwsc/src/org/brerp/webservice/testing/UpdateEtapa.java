package org.brerp.webservice.testing;

import org.brerp.webservice.client.base.DataRow;
import org.brerp.webservice.client.base.Enums.WebServiceResponseStatus;
import org.brerp.webservice.client.base.LoginRequest;
import org.brerp.webservice.client.net.WebServiceConnection;
import org.brerp.webservice.client.request.UpdateDataRequest;
import org.brerp.webservice.client.response.StandardResponse;

public class UpdateEtapa {

	/**
	 * Seta os parâmetros para login (autenticação)
	 * @return
	 */
	public static LoginRequest getLogin() {
		LoginRequest login = new LoginRequest();
		/**
		 * Substituir com as informações da sua base de dados
		 */
		login.setUser("superuser @ brerp.com.br");
		login.setPass("developer");
		login.setClientID(1000000);
		login.setRoleID(1000000);
		login.setWarehouseID(5000025);
		login.setOrgID(5000000);
		return login;
	}

	/**
	 * @return String com a url do servidor
	 */
	public static String getUrlBase() {
		//Sbustituir com o link da sua base de dados
		return "http://dev06.devcoffee.com.br";
	}

	/**
	 * Seta as configurações da conexão
	 * @return classe contendo os parâmetros da conexão
	 */
	public static WebServiceConnection getClient() {
		WebServiceConnection client = new WebServiceConnection();
		client.setAttempts(3);
		client.setTimeout(5000);
		client.setAttemptsTimeout(5000);
		client.setUrl(getUrlBase());
		client.setAppName("Atualizando etapa da agenda");
		return client;
	}


	public static void main(String[] args) {
		//RecordID a ser marcado
		int recordID = 5000000;
		String flag = "Y";
		
		//Cria uma operação do tipo update (vamos inserir um BP no sistema)
		UpdateDataRequest updateEtapaAgenda = new UpdateDataRequest();
		updateEtapaAgenda.setWebServiceType("SetZ_AgendaLinha");
		//Pega as informações de login
		updateEtapaAgenda.setLogin(getLogin());
		updateEtapaAgenda.setRecordID(recordID);
		//Passa os dados do registro a ser inserido
		DataRow data = new DataRow();
		data.addField("Z_Completa", flag);
		updateEtapaAgenda.setDataRow(data);

		// Pega as inforamções da conexão
		WebServiceConnection client = getClient();

		try {
			//Envia a operação, que nesse caso é um criar, e armazena a resposta enviada pelo server
			StandardResponse response = client.sendRequest(updateEtapaAgenda);
			
			System.out.println("XML ENVIADO AO SERVIDOR");
			client.writeRequest(System.out);
			System.out.println();
			System.out.println("XML DE RESPOSTA DO SERVIDOR");
			client.writeResponse(System.out);
			System.out.println();

			// Verifica se ocorreu algum erro ao executar a operação e exibe o erro
			if (response.getStatus() == WebServiceResponseStatus.Error) {
				System.out.println(response.getErrorMessage());
				System.out.println(response.getErrorType());
			} 
			
			System.out.println("--------------------------");
			System.out.println("Web Service: UpdateEtapa");
			System.out.println("Attempts: " + client.getAttemptsRequest());
			System.out.println("Time: " + client.getTimeRequest());
			System.out.println("--------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}