package com.ufcg.psoft.mercadofacil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

import jakarta.servlet.ServletException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes da classe controladora de Produtos")

public class ProdutoV1ControllerTests {
    @Autowired
    MockMvc driver;

    @Autowired
    ProdutoRepository produtoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Produto produto;

    @BeforeEach
    void setup() {
        produto = produtoRepository.find(10L);
    }

    @AfterEach
    void tearDown() {
        produto = null;
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de ID numérica (Long)")
    class ProdutoValidacaoID {
    	
    	@Test
    	@DisplayName("Altera o ID de um Produto com um valor válido")
    	void testeAlteracaoIdValido() throws Exception{
    		// Arrange
    		produto.setId(56L);
    		
    		// Act
    		String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(produto)))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn().getResponse().getContentAsString();

    		Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();
    		
    		// Assert
    		assertEquals(56L,resultado.getId());
    	}
    	
    	@Test
    	@DisplayName("Tenta alterar a ID de um produto com um valor inválido (zero)")
    	void testeValorInvalidoZero() throws Exception{
    		// Arrange
    		produto.setId(0L);
    		
    		ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
    	}
    	
    	@Test
    	@DisplayName("Tenta alterar a ID de um produto com um valor inválido (negativo)")
    	void testeValorInvalidoNegativo() throws Exception{
    		// Arrange
    		produto.setId(-963L);
    		
    		ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
    	}
    	
    	@Test
    	@DisplayName("Tenta alterar a ID de um produto com um valor inválido (null)")
    	void testeValorInvalidoNull() throws Exception{
    		// Arrange
    		produto.setId(null);
    		
    		ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
    	}
    	
    }
    
    
    @Nested
    @DisplayName("Conjunto de casos de verificação dos campos nome e nomeFabricante (String)")
    class ProdutoValidacaoCamposObrigatorios {

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados válidos")
        void quandoAlteramosNomeDoProdutoValido() throws Exception {
            // Arrange
            produto.setNome("Produto Dez Alterado");

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals("Produto Dez Alterado",resultado.getNome());
        }
        
        @Test
        @DisplayName("Nome do produto inválido (vazio)")
        void testeNomeProdutoVazio() {
        	// Arrange
        	produto.setNome("");
        	
        	// Act
        	ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());        
        }
        
        @Test
        @DisplayName("Nome do produto inválido (em branco)")
        void testeNomeProdutoInvalido() {
        	// Arrange
        	produto.setNome("      ");
        	
        	// Act
        	ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        
        }
        
        @Test
        @DisplayName("Nome do produto inválido (null)")
        void testeNomeProdutoInvalidoNull() {
        	// Arrange
        	produto.setNome(null);
        	
        	// Act
        	ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        
        }
        
        @Test
        @DisplayName("Nome do fabricante válido")
        void testeNomeFabricanteValido() throws Exception {
        	// Arrange
            produto.setNome("Fubica Patience Foundry");

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals("Fubica Patience Foundry",resultado.getNome());
        
        }
        
        @Test
        @DisplayName("Nome do fabricante inválido (vazio)")
        void testeNomeFabricanteInvalidoVazio() {
        	// Arrange
        	produto.setFabricante("");
        	
        	// Act
        	ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        
        }
        
        @Test
        @DisplayName("Nome do fabricante inválido (em branco)")
        void testeNomeFabricanteInvalidoEmBranco() {
        	// Arrange
        	produto.setFabricante(" 	");
        	
        	// Act
        	ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        
        }
        
        @Test
        @DisplayName("Nome do fabricante inválido()")
        void testeNomeFabricanteInvalidoNull() {
        	// Arrange
        	produto.setFabricante(null);
        	
        	// Act
        	ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
        	
        	// Assert
        	assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        
        }

    }

    
    @Nested
    @DisplayName("Conjunto de casos de verificação de preço (Double)")
    class ProdutoValidacaoRegrasDoPreco {
    	
    	@Test
        @DisplayName("Determina um preço inválido (valor igual a zero) para um Produto")
        void testePrecoInvalidoZero() throws Exception {
            //Arrange
            produto.setPreco(0.0);
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }
    	
    	@Test
        @DisplayName("Determina um preço inválido (valor menor que zero) para um Produto")
        void testePrecoInvalidoNegativo() throws Exception {
            //Arrange
            produto.setPreco(-500.00);
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }

        @Test
        @DisplayName("Determina um preço válido para um Produto")
        void testePrecoValido() throws Exception {
            //Arrange
            produto.setPreco(500.00);
            
            //Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();
            
            //Assert
            assertEquals(500.00, resultado.getPreco());
        }
    }

    
    @Nested
    @DisplayName("Conjunto de casos de verificação da validação do código de barras (String)")
    class ProdutoValidacaoCodigoDeBarras {
    	
    	@Test
        @DisplayName("Código de barras de acordo com o padrão EAN-13 E as especificações do projeto")
        void testeCBValido() throws Exception {
            //Arrange
            produto.setCodigoBarra("7899137500104");
            
            //Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();
            
            //Assert
            assertEquals("7899137500104", resultado.getCodigoBarra());
        }

        @Test
        @DisplayName("Código de barras com tamanho inferior ao estabelecido no padrão EAN-13")
        void testeCBMenor() throws Exception {
            //Arrange
            produto.setCodigoBarra("123456789876");
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }
        
        @Test
        @DisplayName("Código de barras com tamanho superior ao estabelecido no padrão EAN-13")
        void testeCBMaior() throws Exception {
            //Arrange
            produto.setCodigoBarra("12345678987654");
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }
        
        @Test
        @DisplayName("Código de barras é válido, mas o código do país não é o do Brasil")
        void testeCodigoPaisErrado() throws Exception {
            //Arrange
            produto.setCodigoBarra("9879137500104");
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }

        @Test
        @DisplayName("Código de barras é válido, mas o código da empresa está errado")
        void testeCodigoEmpresaErrado() throws Exception {        	
            //Arrange
            produto.setCodigoBarra("7893197500104");
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }

        @Test
        @DisplayName("Todo o código de barras está correto, à exceção do dígito verificador")
        void testeDigitoVerificadorErrado() {
            //Arrange
            produto.setCodigoBarra("7899137513132");
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }
        
        @Test
        @DisplayName("Código de barras informado é inválido ()")
        void testeInvalidoBranco() {
            //Arrange
            produto.setCodigoBarra("			");
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }
        
        @Test
        @DisplayName("Código de barras informado é inválido (vazio)")
        void testeInvalidoVazio() {
            //Arrange
            produto.setCodigoBarra("");
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }
        @Test
        @DisplayName("Código de barras informado é inválido (null)")
        void testeInvalidoNull() {
            //Arrange
            produto.setCodigoBarra(null);
            
            //Act
            ServletException thrown = assertThrows(
                    ServletException.class,
                    () -> driver.perform(put("/v1/produtos/" + produto.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(produto)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString());
            
            //Assert
            assertEquals("Request processing failed: java.lang.RuntimeException: Argumento Inválido!", thrown.getMessage());
        }
    }
}
