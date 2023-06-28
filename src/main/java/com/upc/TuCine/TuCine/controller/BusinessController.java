package com.upc.TuCine.TuCine.controller;

import com.upc.TuCine.TuCine.dto.ActorDto;
import com.upc.TuCine.TuCine.dto.BusinessDto;
import com.upc.TuCine.TuCine.dto.BusinessTypeDto;
import com.upc.TuCine.TuCine.dto.ShowtimeDto;
import com.upc.TuCine.TuCine.dto.save.Business.BusinessSaveDto;
import com.upc.TuCine.TuCine.service.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Business", description = "API de Businesses")
@RequestMapping("/api/TuCine/v1")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }
    //URL: http://localhost:8080/api/TuCine/v1/businesses
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/businesses")
    @Operation(summary = "Obtener lista de todos los Businesses")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de businesses",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = BusinessDto.class))
                    }),
    })
    public ResponseEntity<List<BusinessDto>> getAllBusinesses() {
        return new ResponseEntity<>(businessService.getAllBusiness(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/businesses/{id}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/businesses/{id}")
    @Operation(summary = "Obtener un business por su id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se obtuvo el business",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = BusinessDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "No se encontro el business",
                    content = @Content)
    })
    public ResponseEntity<BusinessDto> getBusinessById(@PathVariable(value = "id") Integer id) {
        BusinessDto businessDto = businessService.getBusinessById(id);
        if (businessDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(businessDto, HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/businesses/{id}/businessTypes
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/businesses/{id}/businessTypes")
    @Operation(summary = "Obtener un businessType mediante el id del Business")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se obtuvo el businessType",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = BusinessTypeDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "No se encontro el business",
                    content = @Content)
    })
    public ResponseEntity<BusinessTypeDto> getBusinessTypeByBusinessId(@PathVariable("id") Integer id) {
        BusinessTypeDto businessTypeDto = businessService.getBusinessTypeByBusinessId(id);
        if (businessTypeDto == null) {
            return ResponseEntity.notFound().build(); // Manejar casos en los que no se encuentre el business
        }
        return new ResponseEntity<>(businessTypeDto, HttpStatus.OK);
    }
    //Get all Showtimes by Business Id
    //URL: http://localhost:8080/api/TuCine/v1/businesses/{id}/showtimes
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/businesses/{id}/showtimes")
    @Operation(summary = "Obtener todos los showtimes mediante el id del Business")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de showtimes",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = ShowtimeDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "No se encontro el business",
                    content = @Content)
    })
    public ResponseEntity<List<ShowtimeDto>> getAllShowtimesByBusinessId(@PathVariable("id") Integer id) {
        List<ShowtimeDto> showtimeDtoList = businessService.getAllShowtimesByBusinessId(id);
        if (showtimeDtoList == null) {
            return ResponseEntity.notFound().build(); // Manejar casos en los que no se encuentre el business
        }
        return new ResponseEntity<>(showtimeDtoList, HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/businesses
    //Method: POST
    @Transactional
    @PostMapping("/businesses")
    @Operation(summary = "Crear un business")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Se creo el business",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = BusinessDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "No se pudo crear el business",
                    content = @Content)
    })
    public ResponseEntity<BusinessDto> createBusiness(@RequestBody BusinessSaveDto businessSaveDto){
        return new ResponseEntity<>(businessService.createBusiness(businessSaveDto), HttpStatus.CREATED);
    }
}
