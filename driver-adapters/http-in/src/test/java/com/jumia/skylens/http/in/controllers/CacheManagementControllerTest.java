package com.jumia.skylens.http.in.controllers;

import com.jumia.skylens.http.in.model.CacheResponseInner;
import com.jumia.skylens.http.in.services.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.jumia.skylens.http.in.controllers.CacheManagementApi.PATH_CLEAR_CACHE;
import static com.jumia.skylens.http.in.controllers.CacheManagementApi.PATH_LIST_CACHE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CacheManagementController.class)
class CacheManagementControllerTest extends BaseControllerTest {

    @MockitoBean
    private CacheService cacheService;

    @Test
    void listCache_whenCalled_thenReturn200() throws Exception {

        // Given
        final List<CacheResponseInner> cacheEntries = List.of(
                CacheResponseInner.builder().key("SP-001").value("PT").build(),
                CacheResponseInner.builder().key("SP-002").value("NG").build());

        when(cacheService.listCache()).thenReturn(cacheEntries);

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_LIST_CACHE));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].key").value("SP-001"))
                .andExpect(jsonPath("$[0].value").value("PT"))
                .andExpect(jsonPath("$[1].key").value("SP-002"))
                .andExpect(jsonPath("$[1].value").value("NG"));

        verify(cacheService).listCache();
    }

    @Test
    void listCache_whenCacheIsEmpty_thenReturnEmptyArray() throws Exception {

        // Given
        when(cacheService.listCache()).thenReturn(List.of());

        // When
        final ResultActions resultActions = mvc.perform(get(PATH_LIST_CACHE));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(cacheService).listCache();
    }

    @Test
    void clearCache_whenCalled_thenReturn204() throws Exception {

        // When
        final ResultActions resultActions = mvc.perform(delete(PATH_CLEAR_CACHE));

        // Then
        resultActions
                .andExpect(status().isNoContent());

        verify(cacheService).clearCache();
    }
}
