package com.demo;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.demo.domain.SupportItem;
import com.demo.repository.SupportItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	
	protected MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	SupportItemRepository repository;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document("{method-name}"))
				.build();
	}
	
	@Test
	public void testFindAll() throws Exception {
		
		mockMvc.perform(post("/api/findAll"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testFindAllPage() throws Exception {
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", "0");
		
		mockMvc.perform(post("/api/findAllPage")
				.content(objectMapper.writeValueAsString(m))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteItem() throws Exception {
		
		SupportItem supportItem = SupportItem
				.builder()
				.itemName("test1")
				.itemPrice("12000")
				.itemImg("").build();
		
		repository.save(supportItem);
		
		mockMvc.perform(post("/api/deleteItem")
				.content(objectMapper.writeValueAsString(supportItem))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testSaveItem() throws Exception {
		
		SupportItem supportItem = SupportItem
				.builder()
				.itemName("test1")
				.itemPrice("12000")
				.itemImg("").build();
		
		
		mockMvc.perform(post("/api/saveItem")
				.content(objectMapper.writeValueAsString(supportItem))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
}