package com.glaiss.core.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicosTest {

    @Test
    void entao_deve_conter_todos_os_servicos_esperados() {
        // Dado / Quando / Então
        assertEquals(3, Servicos.values().length);
        assertNotNull(Servicos.valueOf("LISTA"));
        assertNotNull(Servicos.valueOf("USERS"));
        assertNotNull(Servicos.valueOf("GATEWAY"));
    }
}
