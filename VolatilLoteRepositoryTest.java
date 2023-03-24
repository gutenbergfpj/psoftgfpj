package MercadoFacil;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class VolatilLoteRepositoryTest {


   @Autowired
   VolatilLoteRepository driver;


   Lote lote;
   Lote resultado;
   Produto produto;


   @BeforeEach
   void setup() {
       produto = Produto.builder()
               .id(1L)
               .nome("Produto Base")
               .codigoBarra("123456789")
               .fabricante("Fabricante Base")
               .preco(125.36)
               .build();
       lote = Lote.builder()
               .id(1L)
               .numeroDeItens(100)
               .produto(produto)
               .build();
   }


   @AfterEach
   void tearDown() {
       produto = null;
       driver.deleteAll();
   }


   @Test
   @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
   void salvarPrimeiroLote() {
       resultado = driver.save(lote);


       assertEquals(driver.findAll().size(),1);
       assertEquals(resultado.getId().longValue(), lote.getId().longValue());
       assertEquals(resultado.getProduto(), produto);
   }


   @Test
   @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
   void salvarSegundoLoteOuPosterior() {
       Produto produtoExtra = Produto.builder()
               .id(2L)
               .nome("Produto Extra")
               .codigoBarra("987654321")
               .fabricante("Fabricante Extra")
               .preco(125.36)
               .build();
       Lote loteExtra = Lote.builder()
               .id(2L)
               .numeroDeItens(100)
               .produto(produtoExtra)
               .build();
       driver.save(lote);


       resultado = driver.save(loteExtra);


       assertEquals(driver.findAll().size(),2);
       assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
       assertEquals(resultado.getProduto(), produtoExtra);
   }

   // Produto p = Produto.builder().id().nome().codigoBarra().fabricante().preco().build();
   // Lote l = Lote.builder().id().numeroDeItens().produto().build();

   @Test
   @DisplayName("Encontra lotes de acordo com suas IDs únicas")
   // find(Long id) retorna um Lote de acordo com sua ID, que deveria ser o índice do mesmo no repositório de acordo com a implementação do método
   void testFind(){
        Produto p2 = Produto.builder().id(2L).nome("Produto 2").codigoBarra("222222222").fabricante("Fábrica 2").preco(50.25).build();
        Produto p8 = Produto.builder().id(8L).nome("Produto 8").codigoBarra("888888888").fabricante("Manufatura 8").preco(7.41).build();
        Produto p10 = Produto.builder().id(10L).nome("Café IndexOutOfBoundsException").codigoBarra("-1").fabricante("Santa Clara").preco(Double.MIN_VALUE).build();

        // ArrayLists que usam o construtor não-parametrizado têm capacidade default de 10
        Lote lote2 = Lote.builder().id(2L).numeroDeItens(75).produto(p2).build();
        Lote lote8 = Lote.builder().id(8L).numeroDeItens(33).produto(p8).build(); 
        Lote lote10 = Lote.builder().id(10L).numeroDeItens(-555).produto(p10).build();

        driver.save(lote);
        driver.save(lote2);
        driver.save(lote8);
        driver.save(lote10);

        List<Lote> armazenados = driver.findAll();

        assertEquals(armazenados.size(),4);

        assertEquals(lote,armazenados.get(0));
        assertEquals(lote,driver.find(lote.getId().longValue()));

        assertEquals(lote2,armazenados.get(1));
        assertEquals(lote2,driver.find(lote2.getId().longValue()));

        assertEquals(lote8, armazenados.get(2));
        assertEquals(null,armazenados.get(8L));
        assertEquals(null,driver.find(lote8.getId().longValue()));

        assertEquals(lote10, armazenados.get(3));

        boolean getError = false;
        try{
            armazenados.get(10L);
        } catch (IndexOutOfBoundsException e) {
            getError = true;
        }
        assertTrue(getError);

        boolean findError = false;
        try {
            driver.find(lote10.getId().longValue());
        } catch (IndexOutOfBoundsException e){
            findError = true;
        }
        assertTrue(findError);

   }

   @Test
   @DisplayName("Retorna uma cópia da lista de lotes")
   // findAll() retorna uma cópia da lista "lotes" do repositório
   void testFindAll(){

    assertEquals(driver.findAll().size(),0);

    driver.save(lote);
    List<Lote> l1 = driver.findAll();
    List<Lote> l2 = new ArrayList<>();
    l2.add(lote);
    assertEquals(l1.size(),1);
    assertIterableEquals(l1,l2);

    driver.save(lote);
    driver.save(lote);
    l1 = driver.findAll();
    l2.add(lote);
    l2.add(lote);
    assertEquals(l1.size,3);
    assertIterableEquals(l1,l2);

   }

   @Test
   @DisplayName("Atualiza valores de lotes de acordo com sua ID")
   // update(Lote lote) esvazia o repositório, adiciona o Lote fornecido como argumento, e retorna o 1º lote do repositório (ou null caso esteja vazio)
   void testUpdate(){

    assertEquals(driver.findAll().size(),0);

    driver.save(lote);
    assertEquals(driver.findAll().size(),1);
    
    driver.save(lote);
    driver.save(lote);
    assertEquals(driver.findAll().size(),3);

    driver.update(lote);
    resultado = driver.find(lote);
    assertEquals(lote, resultado);

    resultado = driver.update(null);
    assertNull(resultado);

   }

   @Test
   @DisplayName("Apaga lotes de acordo com sua ID")
   // delete(Lote lote) esvazia o repositório, similar a deleteAll, mas recebe um argumento
   void testDelete(){

    assertEquals(driver.findAll().size(),0);

    driver.save(lote);
    assertEquals(driver.findAll().size(),1);
    
    driver.save(lote);
    driver.save(lote);
    assertEquals(driver.findAll().size(),3);

    driver.delete(lote);
    assertEquals(driver.findAll().size(),0);

   }

   @Test
   @DisplayName("Esvazia o repositório")
   // deleteAll() esvazia o repositório
   void testDeleteAll(){

    assertEquals(driver.findAll().size(),0);

    driver.save(lote);
    assertEquals(driver.findAll().size(),1);
    
    driver.save(lote);
    driver.save(lote);
    assertEquals(driver.findAll().size(),3);

    driver.deleteAll();
    assertEquals(driver.findAll().size(),0);

   }

}
