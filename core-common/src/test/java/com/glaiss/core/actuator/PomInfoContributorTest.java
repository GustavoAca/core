package com.glaiss.core.actuator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.info.Info;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PomInfoContributorTest {

    @Nested
    class Dado_uma_configuracao_de_app_valida {

        @Test
        @SuppressWarnings("unchecked")
        void entao_deve_adicionar_detalhes_no_info_builder() {
            // Dado
            PomInfoContributor contributor = new PomInfoContributor();
            ReflectionTestUtils.setField(contributor, "nomeProjeto", "core-lib");
            ReflectionTestUtils.setField(contributor, "projectVersion", "1.1.0");

            Info.Builder builder = new Info.Builder();

            // Quando
            contributor.contribute(builder);
            Info info = builder.build();

            // Então
            Map<String, Object> details = info.getDetails();
            assertNotNull(details.get("app"));
            
            Map<String, String> appDetails = (Map<String, String>) details.get("app");
            assertEquals("core-lib", appDetails.get("id"));
            assertEquals("1.1.0", appDetails.get("version"));
        }
    }
}
