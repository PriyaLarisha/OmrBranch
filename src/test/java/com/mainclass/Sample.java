package com.mainclass;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.pojo.AddAddress_Input_pojo;
import com.pojo.AddAddress_Output_pojo;
import com.pojo.DeleteAddress_Input_pojo;
import com.pojo.GetAddress_Ouput_pojo;
import com.pojo.Login_Output_pojo;
import com.pojo.UpdateAddress_Input_pojo;

import com.pojo.UpdateAndDeleteAddress_Output_pojo;

import endpoints.Endpoint;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class Sample extends BaseClass {
	String logtoken;
	String address_id;

	@Test(priority=1)

	//login
	private void login() {

		addheader("accept", "application/json");
		basicAuth("priyaprabhakaran145@gmail.com","Dhanam@14");
		Response response = requestType("POST",Endpoint.POSTMANBASICAUTHLOGIN);

		int resposneCode = getResponseCode(response);
		System.out.println(resposneCode);
		Assert.assertEquals(resposneCode, 200,"Verify status code");

		Login_Output_pojo login_Output_pojo = response.as(Login_Output_pojo.class);
		logtoken = login_Output_pojo.getData().getLogtoken();

		String first_name = login_Output_pojo.getData().getFirst_name();
		String last_name = login_Output_pojo.getData().getLast_name();
		System.out.println(first_name);
		System.out.println(last_name);
		Assert.assertEquals(first_name, "Priya","Verify first name");
		
		
	}

	@Test(priority=2)
	//AddAddress
	private void addUserAddress() {

		List<Header> h=new ArrayList<>();
		Header h1=new Header("accept","application/json");
		Header h2=new Header("Authorization","Bearer "+ logtoken);
		Header h3=new Header("Content-Type","application/json");

		h.add(h1);
		h.add(h2);
		h.add(h3);

		Headers headers = new Headers(h);
		addHeaders(headers);

		//2.payload

		AddAddress_Input_pojo addAddress_Input_pojo = new AddAddress_Input_pojo("Priya", "P", "6380191288", "Hiranandini", 20, 3378, 101, "202020", "Anna Salai", "home");
		addBody(addAddress_Input_pojo);

		//3.RequestType
		Response response = requestType("POST",Endpoint.ADDUSERADDRESS);
		int responseCode = getResponseCode(response);
		System.out.println(responseCode);
		Assert.assertEquals(responseCode, 200,"Verify response code");
		

		String resBodyAsPrettyString = getResBodyAsPrettyString();
		System.out.println(resBodyAsPrettyString);
		
		AddAddress_Output_pojo addAddress_Output_pojo = response.as(AddAddress_Output_pojo.class);
		String message = addAddress_Output_pojo.getMessage();
		Assert.assertEquals(message, "Address added successfully","Verify Address added successfully");

		int add = addAddress_Output_pojo.getAddress_id();
		String address_id= String.valueOf(add);

	}	
	@Test(priority=3)
	private void updateAddress() {
		
		List<Header> h=new ArrayList<>();
		Header h1=new Header("accept","application/json");
		Header h2=new Header("Authorization","Bearer "+ logtoken);
		Header h3=new Header("Content-Type","application/json");

		h.add(h1);
		h.add(h2);
		h.add(h3);

		Headers headers = new Headers(h);
		addHeaders(headers);

		//payload
		UpdateAddress_Input_pojo updateAddress_Input_pojo = new UpdateAddress_Input_pojo("14905", "Priya", "Larisha", "6380191288", "Hiranandini", 20, 3378, 101, "202020", "Anna Salai", "home");
		addBody(updateAddress_Input_pojo);
		
		//REquest type
		Response response = requestType("PUT",Endpoint.UPDATEUSERADDRESS);
	    int responseCode = getResponseCode(response);
	    Assert.assertEquals(responseCode, 200,"Address updated successfully");
	    System.out.println(responseCode);
	    
	    UpdateAndDeleteAddress_Output_pojo updateAddress_Output_pojo = response.as(UpdateAndDeleteAddress_Output_pojo.class);
	    String message = updateAddress_Output_pojo.getMessage();
	    Assert.assertEquals(message, "Address updated successfully","Verify Address updated successfully");
	}
	
	
	@Test(priority=4)
	private void deleteAddress() {
		

		List<Header> h=new ArrayList<>();
		Header h1=new Header("accept","application/json");
		Header h2=new Header("Authorization","Bearer "+ logtoken);
		Header h3=new Header("Content-Type","application/json");

		h.add(h1);
		h.add(h2);
		h.add(h3);

		Headers headers = new Headers(h);
		addHeaders(headers);
		
		//payload
		
		DeleteAddress_Input_pojo deleteAddress_Input_pojo = new DeleteAddress_Input_pojo(address_id);
		addBody(deleteAddress_Input_pojo);
		
		Response response = requestType("DELETE",Endpoint.DELETEADDRESS);
		int responseCode = getResponseCode(response);
		System.out.println(responseCode);
		
		UpdateAndDeleteAddress_Output_pojo updateAndDeleteAddress_Output_pojo = response.as(UpdateAndDeleteAddress_Output_pojo.class);
		String message = updateAndDeleteAddress_Output_pojo.getMessage();
		Assert.assertEquals(message, "Address deleted successfully","Verify Address deleted successfully");
		
	}
	
	
	@Test(priority=5)
	
	private void getAddress() {
		
		List<Header> h=new ArrayList<>();
		Header h1=new Header("accept","application/json");
		Header h2=new Header("Authorization","Bearer "+ logtoken);
		Header h3=new Header("Content-Type","application/json");

		h.add(h1);
		h.add(h2);
		h.add(h3);

		Headers headers = new Headers(h);
		addHeaders(headers);
		
		Response response = requestType("GET", Endpoint.DELETEADDRESS);
		int responseCode = getResponseCode(response);
		System.out.println(responseCode);
		Assert.assertEquals(responseCode,200,"Verify status code");
		
		GetAddress_Ouput_pojo getAddress_Ouput_pojo = response.as(GetAddress_Ouput_pojo.class);
	    String message = getAddress_Ouput_pojo.getMessage();
		Assert.assertEquals(message, "OK","Verify get address retrieved?");
		

	}
	


}
