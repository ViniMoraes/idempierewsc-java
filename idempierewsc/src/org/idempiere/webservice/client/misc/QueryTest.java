/**
 * 
 */
package org.idempiere.webservice.client.misc;

import org.idempiere.webservice.client.base.DataRow;
import org.idempiere.webservice.client.base.Enums.Language;
import org.idempiere.webservice.client.base.Enums.WebServiceResponseStatus;
import org.idempiere.webservice.client.base.Field;
import org.idempiere.webservice.client.base.LoginRequest;
import org.idempiere.webservice.client.net.WebServiceConnection;
import org.idempiere.webservice.client.request.QueryDataRequest;
import org.idempiere.webservice.client.response.WindowTabDataResponse;

/**
 * @author vini
 *
 */
public class QueryTest {
	//CREDENTIALS
	public static final String USER_NAME = null;
	public static final String PASSWORD = null;
	public static final String URL_BASE = null;

	
	public static LoginRequest getLogin() {
        LoginRequest login = new LoginRequest();
        login.setUser(USER_NAME);
        login.setPass(PASSWORD);
        login.setClientID(1000000);
        login.setRoleID(1000000);
        login.setOrgID(1000001);
        login.setWarehouseID(5000005);
        login.setLang(Language.pt_BR);
        return login;
    }

    public static String getUrlBase() {
        return URL_BASE;
    }

    public static WebServiceConnection getClient() {
        WebServiceConnection client = new WebServiceConnection();
        client.setAttempts(3);
        client.setTimeout(5000);
        client.setAttemptsTimeout(5000);
        client.setUrl(getUrlBase());
        client.setAppName("Java Test WS Client");
        return client;
    }
    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {


		QueryDataRequest ws = new QueryDataRequest();
		ws.setWebServiceType("GetOP");
		ws.setLogin(getLogin());
		
		DataRow data = new DataRow();
		data.addField("DocumentNo", "1000061");
		ws.setDataRow(data);

		WebServiceConnection client = getClient();

		try {
			WindowTabDataResponse response = client.sendRequest(ws);

			if (response.getStatus() == WebServiceResponseStatus.Error) {
				System.out.println(response.getErrorMessage());
			} else {
				System.out.println("Total rows: " + response.getTotalRows());
				System.out.println("Num rows: " + response.getNumRows());
				System.out.println("Start row: " + response.getStartRow());
				System.out.println();
				for (int i = 0; i < response.getDataSet().getRowsCount(); i++) {
					System.out.println("Row: " + (i + 1));
					for (int j = 0; j < response.getDataSet().getRow(i).getFieldsCount(); j++) {
						Field field = response.getDataSet().getRow(i).getFields().get(j);
						System.out.println("Column: " + field.getColumn() + " = " + field.getStringValue());
					}
					System.out.println();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
