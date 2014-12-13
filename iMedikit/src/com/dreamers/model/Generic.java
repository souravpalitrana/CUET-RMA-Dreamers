package com.dreamers.model;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "generic")
public class Generic extends Model {
	
	@Column (name="generic_name")
	public String genericName;
	@Column (name="generic_code")
	public String genericCode;
	
	@Column (name="generic_dose")
	public String genericDose;
	
	
	
	
	public Generic()
	{
		super();
	}
	
	public Generic(String gn, String gc,String dose)
	{    
		super();
	
		genericName=gn;
		genericCode=gc;
		genericDose=dose;
	}
	

}
