package com.ufcg.psoft.mercadofacil;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

import com.ufcg.psoft.mercadofacil.Utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Testes do repositório de produtos")
public class ProdutoRepositoryTests {
    
    @Autowired
    ProdutoRepository driver;
    Produto produto;


    @BeforeEach
    void setup(){
        produto = TestUtils.criarProduto(10L, "Produto Dez", "Fabricante Dez", "12345678901236",100.00);
    }

    @Test
    @DisplayName("Criação de novo produto com dados válidos")
    void testCriacaoProduto(){
        // Arrange

        // Act
        Produto resultado = driver.save(produto);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Produto Dez", resultado.getNome());
        assertEquals("Fabricante Dez", resultado.getFabricante());
        assertEquals("1234567890123",resultado.getCodigoBarra());
        assertEquals(100.00,resultado.getPreco());
    }
}
