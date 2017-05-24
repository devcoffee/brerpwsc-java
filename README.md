![](https://devcoffee.com.br/wp-content/uploads/2016/11/logo_brerp-300x86.png)        

O BrERP WSC facilita muito as requisições SOAP para os webservices do BrERP em sua aplicação java. Com uma arquitetura model oriented, não é necessário tratar o XML de request e response na mão. Com essa biblioteca podemos fazer coisas como:

  - CRUD em qualquer tabela do sistema
  - Trazer informações de views
  - Executar o "ação do documento" em qualquer documento do BrERP
  - Execução de processo
  - Compatível com Android

# Documentação

## Exemplo prático: Criando um parceiro de negócios

Dentro do pacote de testes deste repositório existe uma classe chamada CreateBPartner. Como o nome sugere, ela cria um parceiro de negócios no BrERP via webservice. Basta que você troque as informações do seu servidor/login e que configure os webservices no sistema. Abaixo, segue abaixo as configurações feitas na base de desenvolvimento, basta adaptar para sua empresa:

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

### Cadastrando parceiros de negócio e Consultando em um dispositivo android
A biblioteca também é compatível para projetos nativos android. A pasta sandbox_android contem um simples projeto no android studio para demonstrar a funcionalidade, e você mesmo pode rodar! Não esquecer de configurar os webservices na sua base do BrERP! :)

![](/documents/tela_brerp.png)

### Configurando os Webservices
Para expor os dados do brerp, nenhum código precisa ser feito. Basta que sejam feitas as configurações na janela de Segurança de Serviços Web.

#### Serviços de Segurança Web
Quando dizemos que os webservices são model oriented, isso quer dizer que eles são orientados a classes de modelo, então podemos entender que são orientados a tabelas do banco de dados. É claro que é possível também expor processos, que não estão vinculados a nenhuma tabela no sistema, mas na maioria das situações será seguidos esse padrão. Vamos imaginar que iremos expor os dados da tabela de parceiro de negócios, para que seja uma consulta igual demonstrado no aplicativo da seção à cima.
Na janela de Serviços de Segurança Web então, devemos selecionar no campo Serviço Web que se trata de um "Model Oriented Web Services". No método de serviço web, é onde selecionamos qual a operação que queremos fazer, se é uma query (fazer uma pesquisar), insert, update, delete, rodar um processo (runProcess), etc.No nosso caso, ficaremos com a opção "Query".
No campo tabela, deve ser selecionada qual a tabela a ser exposta 

#### Parâmetros de Serviço Web
A janela parâmetros de Serviço Web, é uma aba filha do serviços de segurança web. Obrigatóriamente para o funcionamento do Web Service Type, eu precisso declarar três parâmetros:
- TableName, que é um tipo de parâmetro constante e atribuimos o nome da tabela do banco dados no seu valor constante
- RecordID, que é um tipo de parâmetro free, ou seja, pode ser passado qualquer valor na requisição
- Action, que é constante e contem o nome da operação. Como vamos fazer uma Query, o valor da constant é "Query". Se fosse update, seria update.

#### Entrada de Serviço Web
É onde passamos qual informação (coluna) será levada na requisição. Será os campos filtros da query.

#### Resultado de Serviço Web
São as colunas que são retornadas da operação, no nosso exemplo, as colunas que serão retornadas da query

#### Acesso de Serviço Web
São os perfis que tem permissão para fazer essa requisição. Lembrando que, no BrERP, toda requisição necessita ser autenticada (Login)

Segue abaixo, o print do web service configurado:
![](/documents/QueryBPartnerTest1.png)Mundo do Café S/A Admin

### Tipos de Serviço Web
Na janela Segurança de Serviços web, existe um campo chamado Serviço Web. Que podem ser CompositeInterface ou Model Oriented Web Services. Os model Oriented Web Services, são as requisições de fato, são o mesmo tipo do exempo á cima. O CompositeInterface serve apenas para agrupar multiplas requisições e enviar ao servidor tudo de uma vez. Ao fazer uma requisição do tipo CompositeInterface, tudo já está dentro de uma transação, estão não precisamos nos preocupar com isso.
Vamos imaginar o cenário onde além de cadastrar o parceiro de negócios, gostaria de cadastrar um endereço para ele. Basta que o cliente agrupe as duas requisições dentro de um composite e siga adiante! (Temos um exemplo parecido com parceiro de negócio + imagem usando o composite dentro do projeto android aqui do repositório)
- O composite não precisa de parâmetros, não precisa de tabela, apenas libere o acesso de serviço Web para o perfil na aba e está pronto para uso!

![](http://2px.com.br/deve/wp-content/uploads/2016/09/logordp.png) 
