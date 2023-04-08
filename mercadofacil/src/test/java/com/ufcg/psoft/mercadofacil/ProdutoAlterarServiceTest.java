package com.ufcg.psoft.mercadofacil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

public class ProdutoAlterarServiceTest {
	
	@Autowired
    ProdutoAlterarService driver;
	
	@MockBean
    ProdutoRepository produtoRepository;
    Produto produto;

    @BeforeEach
    void setup() {
        Mockito.when(produtoRepository.find(10L))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        
        produto = produtoRepository.find(10L);
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Nome Produto Alterado")
                        .fabricante("Nome Fabricante Alterado")
                        .preco(500.00)
                        .build()
                );
    }

    // Os métodos da classe Utils são testados indiretamente a partir deste ponto
    // Testes são feitos na ordem do contrutor de Produto: ID (Long), nome (String), preço (Double), código de barras (String), e nome de fabricante (String)
    
    
    // Testes de ID //
    @Test
    @DisplayName("ID do produto é alterada com dados válidos")
    void testeIdValida() {
    	// Arrange
        produto.setId(10L);
        
        // Act
        Produto resultado = driver.alterar(produto);
        
        // Assert
        assertEquals(10L, resultado.getId());
    }
    
    @Test
    @DisplayName("ID do produto é alterada com dados inválidos")
    void testeIdInvalida() {
    	// Arrange
        produto.setId(null);
        
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
        
        // Assert
        assertEquals("Argumento Inválido!", thrown.getMessage());
    	
    }
    
    // Testes de nome //
    
    @Test
    @DisplayName("Nome do produto é laterado com dados válidos")
    void testeAlteracaoNomeDoProduto() {
    	// Arrange
    	produto.setNome("Nome Produto Alterado");
    	
    	// Act
    	Produto resultado = driver.alterar(produto);
    	
    	// Assert
    	assertEquals("Nome Produto Alterado", resultado.getNome());
    }
    
    @Test
    @DisplayName("Nome inválido (string vazia) é fornecido para um Produto")
    void testeNomeVazio() {
    	// Arrange - String Vazia
    	produto.setNome("");
    	
    	// Act
    	RuntimeException thrown1 = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
    	
    	// Assert
    	assertEquals("Argumento Inválido!", thrown1.getMessage());
    }
    
    @Test
    @DisplayName("Nome inválido (string em branco) é fornecido para um Produto")
    void testeNomeBranco() {
    	// Arrange
    	produto.setNome(" 	 ");
    	
    	// Act
    	RuntimeException thrown2 = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
    	
    	// Assert
    	assertEquals("Argumento Inválido!", thrown2.getMessage());
    
    }
    
    @Test
    @DisplayName("Nome inválido (null) é fornecido para um Produto")
    void testeNomeNull() {
    	// Arrange
    	produto.setNome(null);
    	
    	// Act
	    RuntimeException thrown3 = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
    	
    	// Assert
	    assertEquals("Argumento Inválido!", thrown3.getMessage());
    
    }
    
    
    // Testes de preço //
    
    @Test
    @DisplayName("Preço fornecido é inválido (igual a zero)")
    void testePrecoIgualAZero() {
        // Arrange
        produto.setPreco(0.0);
        
        // Act
        RuntimeException thrown1 = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
        
        // Assert
        assertEquals("Argumento Inválido!", thrown1.getMessage());
    }
    
    @Test
    @DisplayName("Preço frnecido é inválido (menor que zero)")
    void testePrecoNegativo() {
    	// Arrange
    	produto.setPreco(-3.14);
    	
    	// Act
	    RuntimeException thrown2 = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
    	
    	// Assert
	    assertEquals("Argumento Inválido!", thrown2.getMessage());
    
    }
       
    @Test
    @DisplayName("Preço fornecido é válido (maior que zero)")
    void testePrecoValido() {
    	// Arrange
    	produto.setPreco(500.00);
    	
    	// Act
    	Produto resultado = driver.alterar(produto);
    	
    	// Assert
    	assertEquals(500.00,resultado.getPreco());
    }
    
    // Testes do código de barras
    @Test
    @DisplayName("Código de barras fornecido é válido e dentro dos padrões estabelecidos para o projeto")
    void testeCBCompletamenteValido() {
    	// Arrange
        produto.setCodigoBarra("7899137500104");
    	
        // Act
        Produto resultado = driver.alterar(produto);
        
        // Assert
        assertEquals("7899137500104",resultado.getCodigoBarra());
        
    }
    
    @Test
    @DisplayName("Código de barras fornecido é válido, mas não é brasileiro")
    void testeCBValidoMasInternacional() {
    	// Arrange
        produto.setCodigoBarra("9879137513131");
    	
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
        
        // Assert
        assertEquals("Argumento Inválido!",thrown.getMessage());
        
    }
    
    @Test
    @DisplayName("Código de barras fornecido é válido, mas não é da empresa")
    void testeCBValidoMasPertencenteAOutraEmpresa() {
    	// Arrange
        produto.setCodigoBarra("7893197513131");
    	
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
        
        // Assert
        assertEquals("Argumento Inválido!",thrown.getMessage());
        
    }
    
    @Test
    @DisplayName("Código de barras fornecido é brasileiro e da empresa, mas o código identificador de produto é inválido")
    void testeCodigoProdutoInvalido() {
    	// Arrange
        produto.setCodigoBarra("7893197500001");
    	
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
        
        // Assert
        assertEquals("Argumento Inválido!",thrown.getMessage());
        
    }
    
    // Os testes à seguir levam em consideração a adoção de códigos de barras no padrão EAN-13
    @Test
    @DisplayName("Código de barras fornecido é inválido (menor que 13 dígitos)")
    void testeCBInvalidoCurto() {
    	// Arrange
    	produto.setCodigoBarra("401234567890");
        
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));

    	
        // Assert
    	assertEquals("Argumento Inválido!", thrown.getMessage());
    }
    
    @Test
    @DisplayName("Código de barras fornecido é inválido (maior que 13 dígitos)")
    void testeCBInvalidoLongo() {
    	// Arrange
    	produto.setCodigoBarra("40123456789015");
        
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));

    	
        // Assert
    	assertEquals("Argumento Inválido!", thrown.getMessage());
    }
    
    @Test
    @DisplayName("Código de barras fornecido é inválido (não respeita o algoritmo de formação/verificação)")
    void testeCBInvalidoErrado() {
    	// Arrange
    	produto.setCodigoBarra("5252525252525");
        
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));

    	
        // Assert
    	assertEquals("Argumento Inválido!", thrown.getMessage());
    }
    
    // Testes do fabricante //
    
    @Test
    @DisplayName("Nome de fabricante válido é provido para um produto")
    void testeNomeFabricanteValido() {
    	// Arrange
        produto.setFabricante("Nome Fabricante Alterado");
        
        // Act
        Produto resultado = driver.alterar(produto);
        
        // Assert
        assertEquals("Nome Fabricante Alterado", resultado.getNome());
    }
    
    @Test
    @DisplayName("Nome de fabricante inválido é provido para um produto")
    void testeNomeFabricanteInvalidoVazio() {
    	// Arrange
        produto.setFabricante("");
    	
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));
    	
        // Assert
        assertEquals("Nome de Fabricante Inválido",thrown.getMessage());
    	
    }
    
    @Test
    @DisplayName("Nome de fabricante inválido é provido para um produto")
    void testeNomeFabricanteInvalidoBranco() {
    	// Arrange
    	produto.setFabricante("\n");
    	
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));

    	
        // Assert
        assertEquals("Nome de Fabricante Inválido",thrown.getMessage());
    	
    }
    
    @Test
    @DisplayName("Nome de fabricante inválido é provido para um produto")
    void testeNomeFabricanteInvalidoNull() {
    	// Arrange
    	produto.setFabricante(null);
    	
        // Act
        RuntimeException thrown = assertThrows(RuntimeException.class,() -> driver.alterar(produto));

        // Assert
        assertEquals("Nome de Fabricante Inválido",thrown.getMessage());
    	
    }
    
    @Test
    @DisplayName("")
    void teste() {
    	// Arrange
    
    	
    	// Act
    
    	
    	// Assert
    	
    
    }
    
}
