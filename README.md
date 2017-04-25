# BrERP WSC

![](http://2px.com.br/deve/wp-content/uploads/2016/09/logordp.png)

O BrERP WSC facilita muito as requisições SOAP em sua aplicação java. Com uma arquitetura model oriented, não é necessário tratar o XML de request e response na mão. Os webservices do BrERP seguem a arquitetura SOAP, e com ele podemos fazer coisas como:

  - CRUD em qualquer tabela do sistema
  - Trazer informações de views
  - Executar o "ação do documento" em qualquer documento do BrERP
  - Execução de processo
  - Compatível com Android

# Exemplo prático: Criando um parceiro de negócios

Existe uma classe chamada CreateBPartner. Como o nome sugere, ela cria um parceiro de negócios no BrERP via webservice. Basta que você troque as informações do seu servidor/login e que configure os webservices no sistema. Abaixo, segue abaixo as configurações feitas na base de desenvolvimento, basta adaptar para sua empresa:

### Configurando Webservice no BrERP

![](/documents/wst1.png)
![](/documents/wst2.png)
![](/documents/wst3.png)

### Código java 

```java
public class CreateBPartner {

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
		//Cria uma operação do tipo create (vamos inserir um BP no sistema)
		CreateDataRequest createBpartner = new CreateDataRequest();
		createBpartner.setWebServiceType("CreateBPartner");
		//Pega as informações de login
		createBpartner.setLogin(getLogin());

		//Passa os dados do registro a ser inserido
		DataRow data = new DataRow();
		data.addField("Value", "TESTING");
		data.addField("Name", "Leonardo Antunes Coelho");
		data.addField("Name2", "antunesleo");
		data.addField("Description", "Criado por brerpwsc: " + System.currentTimeMillis());
		createBpartner.setDataRow(data);

		// Pega as inforamções da conexão
		WebServiceConnection client = getClient();

		try {
			//Envia a operação, que nesse caso é um criar, e armazena a resposta enviada pelo server
			StandardResponse response = client.sendRequest(createBpartner);
			
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
			System.out.println("Web Service: CreateBPartner");
			System.out.println("Attempts: " + client.getAttemptsRequest());
			System.out.println("Time: " + client.getTimeRequest());
			System.out.println("--------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
```
### Console
```xml
XML ENVIADO AO SERVIDOR
<soapenv:Envelope xmlns:_0="http://idempiere.org/ADInterface/1_0" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
  <soapenv:Header/>
  <soapenv:Body>
    <_0:createData>
      <_0:ModelCRUDRequest>
        <_0:ModelCRUD>
          <_0:serviceType>CreateBPartner</_0:serviceType>
          <_0:DataRow>
            <_0:field column="Value">
              <_0:val>TESTING</_0:val>
            </_0:field>
            <_0:field column="Name">
              <_0:val>Leonardo Antunes Coelho</_0:val>
            </_0:field>
            <_0:field column="Name2">
              <_0:val>antunesleo</_0:val>
            </_0:field>
            <_0:field column="Description">
              <_0:val>Criado por brerpwsc: 1493050349793</_0:val>
            </_0:field>
          </_0:DataRow>
        </_0:ModelCRUD>
        <_0:ADLoginRequest>
          <_0:user>superuser @ brerp.com.br</_0:user>
          <_0:pass>developer</_0:pass>
          <_0:ClientID>1000000</_0:ClientID>
          <_0:RoleID>1000000</_0:RoleID>
          <_0:OrgID>1000001</_0:OrgID>
          <_0:WarehouseID>1000002</_0:WarehouseID>
        </_0:ADLoginRequest>
      </_0:ModelCRUDRequest>
    </_0:createData>
  </soapenv:Body>
</soapenv:Envelope>

XML DE RESPOSTA DO SERVIDOR
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <ns1:createDataResponse xmlns:ns1="http://idempiere.org/ADInterface/1_0">
      <StandardResponse xmlns="http://idempiere.org/ADInterface/1_0" RecordID="5008267"/>
    </ns1:createDataResponse>
  </soap:Body>
</soap:Envelope>

--------------------------
Web Service: CreateBPartner
Attempts: 1
Time: 1093
--------------------------
```

### Consultando o BP cadastrado em um dispositivo android
A biblioteca também é compatível para projetos nativos android. A pasta sandbox_android contem um simples aplicativo para demonstrar a funcionalidade, e você mesmo pode rodar! Não esquecer de configurar os webservices na sua base do BrERP! :)

![](/documents/android-query.png)
