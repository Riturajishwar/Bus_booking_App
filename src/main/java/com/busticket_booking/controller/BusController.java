package com.busticket_booking.controller;

import com.busticket_booking.payload.BusDto;
import com.busticket_booking.service.BusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus")
public class BusController {

    private BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BusDto>addBus(@RequestBody BusDto busDto){

        BusDto saved = busService.addBus(busDto);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<BusDto>> findAllBus(){

        List<BusDto> allBus = busService.findAllBus();

        return new ResponseEntity<>(allBus,HttpStatus.OK);
    }
    @GetMapping("/{busId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BusDto> findBusById(@PathVariable("busId") long busId){

        BusDto busDto = busService.findBusById(busId);

        return new ResponseEntity<>(busDto,HttpStatus.OK);
    }
    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BusDto> updateBusById(@PathVariable("id")long id,@RequestBody BusDto busDto){

        BusDto savedBusDto = busService.updateBusById(id, busDto);

        return new ResponseEntity<>(savedBusDto,HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteBusById(@PathVariable("id")long id){

        busService.deleteBusById(id);

        return new ResponseEntity<>("Bus Deleted! "+id,HttpStatus.OK);
    }

}
