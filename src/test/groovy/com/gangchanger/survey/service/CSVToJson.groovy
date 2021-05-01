package com.gangchanger.survey.service

import com.gangchanger.survey.service.dto.Software
import groovy.util.logging.Slf4j
import jdk.nashorn.internal.ir.annotations.Ignore
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@Slf4j
@SpringBootTest
class CSVToJson {

	@Test
	void convert() {
//		String csv = new File('testing/category.csv').text;
//		String[] line = StringUtils.splitByWholeSeparatorPreserveAllTokens(csv, "\n");
//		List<Software> softwares = [];
//		for(int i = 1; i < line.length - 1; i++){
//			String[] fields = StringUtils.splitByWholeSeparatorPreserveAllTokens(line[i], ",");
//			if(fields.size() >= 6) {
//				softwares.add(new Software(category: fields[2], imgSrc: fields[4], name: fields[5], rate: Double.parseDouble(fields[6])));
//			}
//		}

	}

}
