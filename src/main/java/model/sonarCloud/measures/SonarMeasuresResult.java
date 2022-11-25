/* Copyright (C) 2019 Fraunhofer IESE
 * 
 * You may use, distribute and modify this code under the
 * terms of the Apache License 2.0 license
 */

package model.sonarCloud.measures;

import model.sonarqube.Paging;
import model.sonarqube.measures.Component;

/**
 * SonarCloudMeasuresResult API result
 * @author Max Tiessler & Axel Wickenkamp
 *
 */
public class SonarMeasuresResult {
	
	public Paging paging;
	
	public Component baseComponent;
	public Component components[];
	

}
