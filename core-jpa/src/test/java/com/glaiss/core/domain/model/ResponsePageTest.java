package com.glaiss.core.domain.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponsePageTest {

    @Nested
    class Dado_uma_lista_de_itens {

        @Test
        void entao_deve_instanciar_via_construtor_json() {
            // Dado
            List<String> content = List.of("item1", "item2");
            int page = 0;
            int size = 10;
            long total = 2;

            // Quando
            ResponsePage<String> responsePage = new ResponsePage<>(content, page, size, total);

            // Então
            assertEquals(content, responsePage.getContent());
            assertEquals(page, responsePage.getNumber());
            assertEquals(size, responsePage.getSize());
            assertEquals(total, responsePage.getTotalElements());
        }

        @Test
        void entao_deve_instanciar_via_objeto_page() {
            // Dado
            List<String> content = List.of("item1");
            Page<String> page = new PageImpl<>(content, PageRequest.of(0, 10), 1);

            // Quando
            ResponsePage<String> responsePage = new ResponsePage<>(page);

            // Então
            assertEquals(content, responsePage.getContent());
            assertEquals(1, responsePage.getTotalElements());
        }
    }
}
