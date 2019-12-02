package com.example.BarHop.apicall;

public class Location {
	public String address;
	public String crossStreet;
	public double lat;
	public double lng;
	public String toString() {
		return "{address:"+address+"crossStreet"+crossStreet+"lat:"+lat+"lng"+lng+"}";
	}
}
