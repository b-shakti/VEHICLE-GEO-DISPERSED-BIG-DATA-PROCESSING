package Enums;

public enum IpAddress
{

	cloud_server("localhost"), cloudlet("localhost");

	private String ip_address;

	private IpAddress(String ip)
	{
		ip_address = ip;
	}

	public String get_ip()
	{
		return ip_address;
	}

}
