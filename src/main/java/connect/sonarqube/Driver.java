/* Copyright (C) 2019 Fraunhofer IESE
 * You may use, distribute and modify this code under the
 * terms of the Apache License 2.0 license
 */

package connect.sonarqube;

import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.source.SourceRecord;

import util.PropertyFile;


/**
 * Run the SonarqubeSourceTask without Kafka
 * @author Axel Wickenkamp
 *
 */
public class Driver {
	
	public static void main(String[] args) throws InterruptedException {
		
		Map<String,String> config = PropertyFile.get("config/connect-sonar.properties");
		SonarqubeSourceTask st = new SonarqubeSourceTask();
		st.start(config);
		
		while(true) {
			List<SourceRecord> l = st.poll();
			
			for (SourceRecord sr : l) {
				System.out.println(sr);
			}
		}
		
	}

}

