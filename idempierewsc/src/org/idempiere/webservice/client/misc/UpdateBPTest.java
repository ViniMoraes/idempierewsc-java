package org.idempiere.webservice.client.misc;

import org.idempiere.webservice.client.base.DataRow;
import org.idempiere.webservice.client.base.Enums.WebServiceDefinition;
import org.idempiere.webservice.client.base.Enums.WebServiceResponseStatus;
import org.idempiere.webservice.client.exceptions.XMLWriteException;
import org.idempiere.webservice.client.base.LoginRequest;
import org.idempiere.webservice.client.net.WebServiceConnection;
import org.idempiere.webservice.client.request.UpdateDataRequest;
import org.idempiere.webservice.client.response.StandardResponse;

public class UpdateBPTest {
	private LoginRequest login;
	private WebServiceConnection client;
	//login credentials
	public static final int RECORD_ID = 5000005;
	public static final String SUPER_USER = "superuser @ brerp.com.br";
	public static final String PASSWORD = "developer";
	public static final int CLIENT_ID = 1000000;
	public static final int ROLE_ID = 1000000;
	public static final int ORG_ID = 1000001;
	public static final int WAREHOUSE_ID = 1000002;
	public static final int STAGE = 0;
	public static final String URL_BASE = "http://atemoia.devcoffee.com.br:5700";
	//client credentials
	public static final int ATTEMPTS = 3;
	public static final int TIMEOUT = 5000;
	public static final int ATTEMPTS_TIMEOUT = 5000;
	public static final String APP_NAME = "testingUpdate";
	
	public static void main(String[] args) {
		UpdateBPTest ubpt = new UpdateBPTest();
		
	}
	
	public UpdateBPTest() {
		login = new LoginRequest();
		login.setUser(SUPER_USER);
		login.setPass(PASSWORD);
		login.setClientID(CLIENT_ID);
		login.setRoleID(ROLE_ID);
		login.setOrgID(ORG_ID);
		login.setWarehouseID(WAREHOUSE_ID);
		login.setStage(STAGE);
		
		client = new WebServiceConnection();
		client.setAttempts(ATTEMPTS);
		client.setTimeout(TIMEOUT);
		client.setAttemptsTimeout(ATTEMPTS_TIMEOUT);
		client.setUrl(getUrlBase());
		client.setAppName(APP_NAME);
		runTest();
		
	}
	
	public  void update() {
		UpdateDataRequest updateData = new UpdateDataRequest();
		updateData.setLogin(getLogin());
		updateData.setWebServiceType(getWebServiceType());
		updateData.setRecordID(RECORD_ID);
		
		DataRow data = new DataRow();
		data.addField("Description","Teste de descrição com a classe UpdateBPTest");
		data.addField("Name2","Teste de nome fantasia com a classe UpdateBPTest");
		updateData.setDataRow(data);
		
		WebServiceConnection client = getClient();
		
		try {
			StandardResponse response = client.sendRequest(updateData);
			
			if (response.getStatus() == WebServiceResponseStatus.Error) {
				System.out.println(response.getErrorMessage());
			} else {
				System.out.println("RecordID: " + response.getRecordID());
				System.out.println();
				
				for (int i = 0; i < response.getOutputFields().getFieldsCount(); i++) {
					System.out.println("Column" + (i + 1) + ": " + response.getOutputFields().
							getField(1).getColumn() + " = " + response.getOutputFields().getField(i).getValue());
				}
				System.out.println();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void runTest() {
		update();
		saveRequestResponse();
		printTotal();
		System.out.println();
	}
	
	private void printTotal() {
		try {
			getClient().writeRequest("../documents/" + getWebServiceType() + "_request.xml");
			getClient().writeResponse("../documents/" + getWebServiceType() + "_response.xml");
		} catch (XMLWriteException e) {
			e.printStackTrace();
		}
	}

	private void saveRequestResponse() {
		try {
			getClient().writeRequest(System.out);
			System.out.println();
			System.out.println();
			getClient().writeResponse(System.out);
		} catch (XMLWriteException e) {
			e.printStackTrace();
		}
		
	}

	public String getWebServiceType() {
		return "updatebp"; //return the WS value
	}
	
	public LoginRequest getLogin() {
		return login;
	}
	
	public WebServiceConnection getClient() {
		return client;
	}
		
	public String getUrlBase() {
		return URL_BASE;
	}


}
