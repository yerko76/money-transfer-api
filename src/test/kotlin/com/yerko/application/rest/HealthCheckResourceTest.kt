package com.yerko.application.rest

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class HealthCheckResourceTest {

    @Test
    fun `Should return OK`() {
        val healthCheckResource = HealthCheckResource()

        val result = healthCheckResource.health()

        assertThat(result).isEqualTo("OK")
    }
}
