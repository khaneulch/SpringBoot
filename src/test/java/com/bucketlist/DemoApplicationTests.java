package com.bucketlist;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.repository.SupportItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
public class DemoApplicationTests {
	
	protected MockMvc mockMvc;
	
	@Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	SupportItemRepository repository;
	
	@Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(documentationConfiguration(this.restDocumentation))	
        .alwaysDo(document("{method-name}"
        		, preprocessRequest(prettyPrint())
        		, preprocessResponse(prettyPrint()))) 
        .build();
    }
	
	@Test
	public void testRestDocs() throws Exception {
		
		mockMvc.perform(get("/")
				.param("itemImg", "")
				.param("itemName", "item222")
				.param("itemPrice", "2323"))
		.andExpect(status().isOk());
	}
}