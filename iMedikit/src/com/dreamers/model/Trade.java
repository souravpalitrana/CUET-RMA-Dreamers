package com.dreamers.model;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "trade")
public class Trade extends Model {
	
	
	@Column (name="trade_name")
	public String tradeName;
	
	@Column (name="generic_code")
	public String genericCode;
	
	@Column (name="brand_name")
	public String brandName;
	
	@Column (name="description")
	public String description;
	@Column (name="dose_information")
	public String doseInformation;	
	
	public Trade()
	{
		super();
	}
	
	public Trade(String tn, String gn,String bn , String des, String dose)
	{    
		super();
	
		tradeName=tn;
		genericCode=gn;
		brandName=bn;
		description=des;
		doseInformation=dose;
		
	}
	

}
