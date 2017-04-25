/*****************************************************************************
* Produto: BrERP Plataforma Gestão Empresarial (http://brerp.com.br)         *
* Copyright (C) 2016  devCoffee Sistemas de Gestão Integrada                 *
*                                                                            *
* Este arquivo é parte do BrERP que é software livre; você pode              *
* redistribuí-lo e/ou modificá-lo sob os termos da Licença Pública Geral GNU,*
* conforme publicada pela Free Software Foundation; tanto a versão 2 da      *
* Licença como (a seu critério) qualquer versão mais nova.                   *
*                                                                            *
* A marca BrERP é propriedade da devCoffee Sistema de Gestão Integrada       *
* CNPJ 13.823.508/0001-31, processo registrado o INPI - Intituto Nacional de *
* Propriedade Intelectual número 909512558, portanto ao distribuir ou        *
* modificar este arquivo ou recompilá-lo, a marca BrERP não poderá ser       *
* utilizada, por ser tratar de propriedade da devCoffee Sistemas de Gestão   *
* Integrada conforme estabelecido na Lei n. 9.279/96.                        *
*                                                                            *
* Este programa é distribuído na expectativa de ser útil, mas SEM            *
* QUALQUER GARANTIA; sem mesmo a garantia implícita de                       *
* COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM                    *
* PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais           *
* detalhes.                                                                  *
*                                                                            *
* Você deve ter recebido uma cópia da Licença Pública Geral GNU              *
* junto com este programa; se não, escreva para a Free Software              *
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA                   *
* 02111-1307, USA  ou para devCoffee Sistemas de Gestão Integrada,           *
* Rua Paulo Rebessi 665 - Cidade Jardim - Leme/SP.                           *
 ****************************************************************************/
package org.brerp.webservice.testing;

import org.brerp.webservice.client.base.Enums.WebServiceResponseStatus;
import org.brerp.webservice.client.base.LoginRequest;
import org.brerp.webservice.client.net.WebServiceConnection;
import org.brerp.webservice.client.request.RunProcessRequest;
import org.brerp.webservice.client.response.RunProcessResponse;

/**
 * @author leo
 * 
 * Classe criada para demonstrar o funcionamento do webservice
 * Essa classe cria um novo parceiro de negócio no BrERP
 *
 */
public class RunProcess {

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
		login.setWarehouseID(1000002);
		login.setOrgID(1000001);
		return login;
	}

	/**
	 * @return String com a url do servidor
	 */
	public static String getUrlBase() {
		//Sbustituir com o link da sua base de dados
		return "http://atemoia.devcoffee.com.br:5700";
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
		client.setAppName("Atualizando parceiro de negócio");
		return client;
	}


	public static void main(String[] args) {
		RunProcessRequest runProcess = new RunProcessRequest();
		runProcess.setWebServiceType("RunProcess");
		runProcess.setLogin(getLogin());

		// Pega as inforamções da conexão
		WebServiceConnection client = getClient();

		try {
			//Envia a operação, que nesse caso é um criar, e armazena a resposta enviada pelo server
			RunProcessResponse response = client.sendRequest(runProcess);
			
			System.out.println("XML ENVIADO AO SERVIDOR");
			client.writeRequest(System.out);
			System.out.println();
			System.out.println("XML DE RESPOSTA DO SERVIDOR");
			client.writeResponse(System.out);
			System.out.println();

			// Verifica se ocorreu algum erro ao executar a operação e exibe o erro
			if (response.getStatus() == WebServiceResponseStatus.Error) {
				System.out.println(response.getErrorMessage());
			} 
			
			System.out.println("--------------------------");
			System.out.println("Web Service: RunProcess");
			System.out.println("Attempts: " + client.getAttemptsRequest());
			System.out.println("Time: " + client.getTimeRequest());
			System.out.println("--------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
