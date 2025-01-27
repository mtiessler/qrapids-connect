/* Copyright (C) 2019 Fraunhofer IESE
 * 
 * You may use, distribute and modify this code under the
 * terms of the Apache License 2.0 license
 */

package model.sonarqube.measures;

/**
 * BaseComponent API result
 * @author Max Tiessler & Axel Wickenkamp
 *
 */
public class BaseComponent {
	
	public String id;
	public String key;
	public String name;
	public String qualifier;
	
	public Measure measures[];
	

}
