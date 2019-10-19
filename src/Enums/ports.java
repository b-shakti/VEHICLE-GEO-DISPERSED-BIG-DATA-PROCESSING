package Enums;

public enum ports
{
	 cloud_server(5000), cloudlet(5001);

	private int port;

	private ports(int port_no)
	{

		port = port_no;	

	}

	public int get_port()
	{

		return port;

	}

}
