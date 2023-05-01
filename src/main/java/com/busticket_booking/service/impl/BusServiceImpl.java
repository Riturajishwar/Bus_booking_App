package com.busticket_booking.service.impl;

import com.busticket_booking.entities.Bus;
import com.busticket_booking.exception.ResourceNotFoundException;
import com.busticket_booking.payload.BusDto;
import com.busticket_booking.repositories.BusRepository;
import com.busticket_booking.service.BusService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {
    private BusRepository busRepository;
    private ModelMapper mapper;

    public BusServiceImpl(BusRepository busRepository, ModelMapper mapper) {
        this.busRepository = busRepository;
        this.mapper = mapper;
    }


    @Override
    public BusDto addBus(BusDto busDto) {

        Bus bus = mapToEntity(busDto);

        Bus savedBus = busRepository.save(bus);
        return mapToDto(savedBus);
    }

    @Override
    public List<BusDto> findAllBus() {

        List<Bus> allBus = busRepository.findAll();

        List<BusDto> collectAllBus = allBus.stream().map(i -> mapToDto(i)).collect(Collectors.toList());

        return collectAllBus;
    }

    @Override
    public BusDto findBusById(long busId) {

        Bus bus = busRepository.findById(busId).orElseThrow(() -> new ResourceNotFoundException("Bus not found with id" ));
        return mapToDto(bus);
    }

    @Override
    public BusDto updateBusById(long id, BusDto busDto) {

        Bus bus = busRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bus not found with id" ));
        bus.setBusFrom(busDto.getBusFrom());
        bus.setBusName(bus.getBusName());
        bus.setBusType(busDto.getBusType());
        bus.setFare(busDto.getFare());
        bus.setArrival(busDto.getArrival());
        bus.setAvailSeats(busDto.getAvailSeats());
        bus.setDeparture(busDto.getDeparture());
        bus.setRouteTo(busDto.getRouteTo());
        bus.setTotalSeats(busDto.getTotalSeats());
        Bus saved = busRepository.save(bus);
        return mapToDto(saved);
    }

    @Override
    public void deleteBusById(long id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bus not found with id " ));
         busRepository.deleteById(id);
    }

    Bus mapToEntity(BusDto busDto) {
        Bus bus = mapper.map(busDto, Bus.class);

        return bus;
    }

    BusDto mapToDto(Bus bus) {
        BusDto busDto = mapper.map(bus, BusDto.class);

        return busDto;

    }


}
