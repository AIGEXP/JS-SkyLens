package com.jumia.skylens.acceptance.context;

import com.jumia.skylens.acceptance.dto.PackageResponseDTO;
import io.cucumber.spring.ScenarioScope;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
@Data
public class EventTestContext {

    private ResponseEntity<PackageResponseDTO[]> stopsResponseEntity;
}
