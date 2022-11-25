package com.example.demo.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.BatchCsvImportApplication;
import com.example.demo.domain.model.Employee;

@SpringBatchTest
@ContextConfiguration(classes = {BatchCsvImportApplication.class})
@DisplayName("CsvImportJobのIntegrationTest")
class CsvImportJobIntegrationTest {
	
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** 実行結果の確認用SQL */
	private static String SQL = "SELECT id, name, age, gender FROM employee ORDER BY id";
	
	private RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);

	@Test
	@DisplayName("ユーザーがインポートされていること")
	void jobTest() throws Exception {
		
		// テスト
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		
		// 検証
		// Stepのステータス
		jobExecution.getStepExecutions().forEach(stepExecution -> assertThat(ExitStatus.COMPLETED).isEqualTo(stepExecution.getExitStatus()));
		
		// Jobのステータス
		assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
		
		// DBからレコード取得
		List<Employee> resultList = jdbcTemplate.query(SQL, rowMapper);
		
		// レコード件数
		assertThat(resultList.size()).isEqualTo(2);
		
		// Employee1
		Employee employee1 = resultList.get(0);
		assertThat(employee1.getName()).isEqualTo("テストユーザー1");
		
		// Employee2
		Employee employee2 = resultList.get(1);
		assertThat(employee2.getName()).isEqualTo("テストユーザー2");
	}

}
