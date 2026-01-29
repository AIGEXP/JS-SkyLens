package com.jumia.skylens.http.in.converters.paginations;

import com.jumia.skylens.domain.catalog.pages.PageRequest;
import com.jumia.skylens.http.in.configurations.PaginationConfiguration;
import com.jumia.skylens.http.in.requests.pagination.Pageable;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PageRequestConverterTest {

    @Test
    void convertWithMaxLimitReturnsMaxLimit() {

        //Given
        final PaginationConfiguration paginationConfiguration = new PaginationConfigurationImpl(5, 20, 20);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convertExportItems(50, 0);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isZero();
        assertThat(pageRequest.getLimit()).isEqualTo(20);
    }

    @Test
    void convertWithMinLimitReturnsMaxLimit() {

        //Given
        final PaginationConfiguration paginationConfiguration = new PaginationConfigurationImpl(5, 20, 20);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convertExportItems(19, 0);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isZero();
        assertThat(pageRequest.getLimit()).isEqualTo(19);
    }

    @Test
    void convert_whenHasNullValues_returnsDefaultOffsetAndPagination() {

        //Given
        final PaginationConfiguration paginationConfiguration = new PaginationConfigurationImpl(5, 20, 20);
        final Pageable pageable = new PageableImpl(null, null);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(pageable);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isZero();
        assertThat(pageRequest.getLimit()).isEqualTo(5);
    }

    @Test
    void convert_whenLimitIsHigherThanAllowed_returnsMaxLimit() {

        //Given
        final PaginationConfiguration paginationConfiguration = new PaginationConfigurationImpl(5, 20, 20);
        final Pageable pageable = new PageableImpl(0, 50);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(pageable);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isZero();
        assertThat(pageRequest.getLimit()).isEqualTo(20);
    }

    @Test
    void convert_whenNullOffset_returnsWithOffset0AndRequestedLimit() {

        //Given
        final PaginationConfiguration paginationConfiguration = new PaginationConfigurationImpl(5, 20, 20);
        final Pageable pageable = new PageableImpl(null, 10);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(pageable);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isZero();
        assertThat(pageRequest.getLimit()).isEqualTo(10);
    }

    @Test
    void convert_whenNullPage_returnsNull() {

        //Given
        final PaginationConfiguration paginationConfiguration = new PaginationConfigurationImpl(5, 20, 20);
        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(null);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isZero();
        assertThat(pageRequest.getLimit()).isEqualTo(5);
    }

    @Test
    void convert_whenValidValues_returnsRequestedPagination() {

        //Given
        final PaginationConfiguration paginationConfiguration = new PaginationConfigurationImpl(5, 20, 20);
        final Pageable pageable = new PageableImpl(10, 10);

        final PageRequestConverter subject = new PageRequestConverter(paginationConfiguration);

        //When
        final PageRequest pageRequest = subject.convert(pageable);

        //Then
        assertThat(pageRequest).isNotNull();
        assertThat(pageRequest.getOffset()).isEqualTo(10);
        assertThat(pageRequest.getLimit()).isEqualTo(10);
    }

    private record PageableImpl(Integer offset, Integer limit) implements Pageable {

        @Override
        public Integer getLimit() {

            return limit;
        }

        @Override
        public Integer getOffset() {

            return offset;
        }
    }

    private record PaginationConfigurationImpl(Integer defaultLimit, Integer maxLimit, Integer maxExportLimit)
            implements PaginationConfiguration {

        @Override
        public Integer getDefaultLimit() {

            return defaultLimit;
        }

        @Override
        public Integer getMaxExportLimit() {

            return maxLimit;
        }

        @Override
        public Integer getMaxLimit() {

            return maxExportLimit;
        }
    }
}
