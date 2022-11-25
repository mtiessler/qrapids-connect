/* Copyright (C) 2019 Fraunhofer IESE
 * You may use, distribute and modify this code under the
 * terms of the Apache License 2.0 license
 */

package connect.sonarCloud;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Kafka SonarCloud Connector
 * @author Max Tiessler
 *
 */
public class SonarSourceConnector extends SourceConnector {
	
	private final Logger log = LoggerFactory.getLogger(SonarSourceConnector.class);
	private ArrayList<String> cloudTokens;
	private ArrayList<String> cloudProjectKeys;
	private String cloudMetricKeys;
	private String sonarMeasureTopic;
	private String sonarIssueTopic;
	private String sonarInterval;
	private String sonarSnapshotDate;

	private void getCloudTokens() {
		Stream<String> stream = Arrays
				.stream(SonarSourceConfig.SONAR_TOKEN_CONFIG.split(","));
		cloudTokens = stream.collect(Collectors.toCollection(ArrayList::new));
		log.info("Tokens: {}", cloudTokens);
		if (cloudTokens.isEmpty())
			throw new ConnectException("SonarCloudSourceConnector configuration must include 'sonar.tokens' setting");
	}


	private void getProjectKeys() {
		Stream<String> stream = Arrays
				.stream(SonarSourceConfig.SONAR_PROJECT_KEYS_CONFIG.split(","));
		cloudProjectKeys = stream.collect(Collectors.toCollection(ArrayList::new));
		log.info("Project Keys: {}", cloudProjectKeys);
		if (cloudProjectKeys.isEmpty()) {
			throw new ConnectException("SonarCloudSourceConnector configuration must include 'sonar.project.keys' setting");
		}
	}

	@Override
	public String version() {
		return "0.0.1";
	}

	@Override
	public void start(Map<String, String> props) {
		String propsAux = props.toString();
		log.info("properties: {}", propsAux);
		getCloudTokens();
		getProjectKeys();
		cloudMetricKeys = props.get(SonarSourceConfig.SONAR_METRIC_KEYS_CONFIG);
		sonarMeasureTopic = props.get(SonarSourceConfig.SONAR_MEASURE_TOPIC_CONFIG);
		sonarIssueTopic = props.get(SonarSourceConfig.SONAR_ISSUE_TOPIC_CONFIG);
		sonarInterval = props.get(SonarSourceConfig.SONAR_INTERVAL_SECONDS_CONFIG);
		sonarSnapshotDate = props.get(SonarSourceConfig.SONAR_SNAPSHOTDATE_CONFIG);

	}


	@Override
	public Class<? extends Task> taskClass() {
		return SonarSourceTask.class;
	}

	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		//ExecutorService executorService = Executors.newFixedThreadPool(maxTasks);
		ArrayList<Map<String, String>> configs = new ArrayList<>();
		for(int i = 0; i < maxTasks; ++i) {
			Map<String, String> config = new HashMap<>();
			config.put(SonarSourceConfig.SONAR_TOKEN_CONFIG, cloudTokens.get(i));
			config.put(SonarSourceConfig.SONAR_PROJECT_KEYS_CONFIG, cloudProjectKeys.get(i));
			config.put(SonarSourceConfig.SONAR_MEASURE_TOPIC_CONFIG, sonarMeasureTopic);
			config.put(SonarSourceConfig.SONAR_METRIC_KEYS_CONFIG, cloudMetricKeys);
			config.put(SonarSourceConfig.SONAR_ISSUE_TOPIC_CONFIG, sonarIssueTopic);
			config.put(SonarSourceConfig.SONAR_INTERVAL_SECONDS_CONFIG, "" + sonarInterval);
			config.put(SonarSourceConfig.SONAR_SNAPSHOTDATE_CONFIG, sonarSnapshotDate);
			configs.add(config);
		}
		return configs;
	}

	@Override
	public void stop() {
		//Nothing for the moment
	}

	@Override
	public ConfigDef config() {
		return SonarSourceConfig.DEFS;
	}

}
