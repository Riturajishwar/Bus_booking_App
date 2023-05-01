package com.busticket_booking.service;

import com.busticket_booking.payload.BusDto;

import java.util.List;

public interface BusService {

    BusDto addBus(BusDto busDto);

    List<BusDto> findAllBus();

    BusDto findBusById(long busId);

    BusDto updateBusById(long id, BusDto busDto);

    void deleteBusById(long id);
}
