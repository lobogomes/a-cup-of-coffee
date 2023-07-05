package br.com.alterdata.vendas.integracao.produto;

import br.com.alterdata.vendas.VendasApplication;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

@Slf4j
@SpringBootTest(
        classes = {VendasApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integracao")
public class ListarTodosProdutos {

    @Autowired
    private WebApplicationContext webAppContextSetup;
    @Autowired private EntityManager em;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.webAppContextSetup(webAppContextSetup);
    }

    @Sql("/seeds/produtos.sql")
    @Test
    @DisplayName("Deveria listar todos os produtos")
    public void deveriaListarTodosProdutos() {

        when().get("/produtos")
                .then()
                .statusCode(200)
                .body("size()", is(3))
                .body(
                        "id",
                        hasItems(
                                1,
                                2,
                                3));
    }
}
