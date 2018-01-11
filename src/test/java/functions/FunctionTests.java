package functions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;



//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//@AutoConfigureMockMvc
public class FunctionTests {

	@Autowired
	MockMvc mvc;
	@Autowired
	Function function;

	//@Test
	public void testApply() throws Exception {
		FileSystemResourceLoader r = new FileSystemResourceLoader();
		byte[] json = StreamUtils.copyToByteArray(r.getResource("event.json").getInputStream());
		mvc.perform(post("/function").content(json)).andExpect(status().isOk());
	}

}
