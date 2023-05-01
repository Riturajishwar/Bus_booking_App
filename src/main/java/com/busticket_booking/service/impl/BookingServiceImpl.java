package com.busticket_booking.service.impl;

import com.busticket_booking.entities.Booking;
import com.busticket_booking.entities.Bus;
import com.busticket_booking.entities.Customer;
import com.busticket_booking.exception.ResourceNotFoundException;
import com.busticket_booking.payload.BookingDto;
import com.busticket_booking.repositories.BookingRepository;
import com.busticket_booking.repositories.BusRepository;
import com.busticket_booking.repositories.CustomerRepository;
import com.busticket_booking.service.BookingService;
import com.busticket_booking.utility.MailService;
import com.busticket_booking.utility.PdfGenerator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private MailService mailService;
    @Autowired
    private HttpServletRequest request;


    private BusRepository busRepository;
    private CustomerRepository customerRepository;
    private BookingRepository bookingRepository;
    private ModelMapper mapper;

    public BookingServiceImpl(BusRepository busRepository, CustomerRepository customerRepository, BookingRepository bookingRepository, ModelMapper mapper) {
        this.busRepository = busRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }




    @Override
    public void bookBusTicket(BookingDto bookingDto, long busId, long customerId) {
        String filePath = "C:\\crud\\busticket_booking\\src\\main\\resources\\bookedTicket\\";
        Bus bus = busRepository.findById(busId).orElseThrow(
                () -> new RuntimeException("bus Not found by busId"));
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("Customer not found by customerId"));

        List<Booking> passengerReservations = bookingRepository.findByCustomer(customer);

        if (passengerReservations != null) {
            for (Booking existingReservation : passengerReservations) {
                if (existingReservation.getBus().equals(bus)) {
                    throw new RuntimeException
                            ("Passenger with id " + customerId + " has already booked a reservation for this Bus on BusId" + busId);
                }
            }
        }

        Booking booking = mapper.map(bookingDto, Booking.class);
        booking.setTotalCost(bus.getFare() + 110);
        booking.setCustomer(customer);
        booking.setBus(bus);
        booking.setStatus("CNF");

        long numSeatsLeft = bus.getAvailSeats();
        if (numSeatsLeft == 0) {
            throw new RuntimeException("This Bus is fully booked");
        }
        bus.setAvailSeats(numSeatsLeft - 1);


        bookingRepository.save(booking);
        busRepository.save(bus);

        PdfGenerator pdf = new PdfGenerator();
        pdf.generatePDF(filePath + booking.getBookingId() + ".pdf", customer.getFirstName(), customer.getLastName()
                , customer.getMobile(), customer.getAddress(), bus.getBusName(), String.valueOf(bus.getDeparture())
                , bus.getBusFrom(), bus.getRouteTo(), booking.getTotalCost());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();


        // send email with PDF attachment using MailService
        try {
            mailService.sendEmailWithPDF(email, "Booking Confirmation",
                    "Dear " + customer.getFirstName() + " " + customer.getLastName() + ",\n\nPlease find your booking confirmation attached.\n\nThank you for choosing our service.",
                    filePath + booking.getBookingId() + ".pdf");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<BookingDto> findAllBooking() {
        List<Booking> allBookings = bookingRepository.findAll();
        List<BookingDto> allBookingDtos = allBookings.stream()
                .map(booking -> mapper.map(booking, BookingDto.class))
                .collect(Collectors.toList());

        // Set the file path where the Excel file will be saved
        String filePath = "C:\\crud\\busticket_booking\\src\\main\\resources\\ExcelFile\\AllBooking.xlsx";
        File file = new File(filePath);

        try {
            // Create any necessary parent directories
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            Workbook workbook = new XSSFWorkbook();
            FileOutputStream outputStream = new FileOutputStream(file);

            Sheet sheet = workbook.createSheet("Bookings");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Booking ID");
            headerRow.createCell(1).setCellValue("Seat From");
            headerRow.createCell(2).setCellValue("Seat To");
            headerRow.createCell(3).setCellValue("Status");
            headerRow.createCell(4).setCellValue("Total Cost");

            int rowNum = 1;
            for (BookingDto bookingDto : allBookingDtos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(bookingDto.getBookingId());
                row.createCell(1).setCellValue(bookingDto.getSeatFrom());
                row.createCell(2).setCellValue(bookingDto.getSeatTo());
                row.createCell(3).setCellValue(bookingDto.getStatus());
                row.createCell(4).setCellValue(bookingDto.getTotalCost());
            }

            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allBookingDtos;
    }


    @Override
    public void deleteBookingById(long bookingId) {

        bookingRepository.deleteById(bookingId);

    }

    @Override
    public BookingDto findBookingById(long id) {
        bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id"));


        return null;
    }

    BookingDto mapToDto(Booking booking) {
        BookingDto map = mapper.map(booking, BookingDto.class);

        return map;
    }

}
